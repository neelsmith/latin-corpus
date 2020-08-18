package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._
import scala.io.Source

import edu.holycross.shot.histoutils._

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter


/** A morphologically parsed Latin corpus citable at the level
* of classified tokens.
*
* @param tokens Ordered list of [[LatinParsedTokene]]s making u this corpus.
*/
case class LatinCorpus(tokens: Vector[LatinParsedToken]) extends LatinParsedTokenSequence {
  //Logger.setDefaultLogLevel(LogLevel.WARN)

  /** Concordance of all lexical tokens in corpus.*/
  def tokenConcordance : Map[String, Vector[CtsUrn]] = {
    // cluster token's text and urn, group by text
    tokens.map(t => (t.text, t.urn)).groupBy(_._1).toVector.map{ case(k,v) => (k, v.map(_._2)) }.toMap
  }


  def passagesForLexeme(lexId: String) : Vector[CtsUrn] = {
    try {
      val tokens = lexemeTokenIndex(lexId)
      tokens.flatMap(t => tokenConcordance(t))

    } catch {
      case t: Throwable => {
        println(t)
        throw t
      }
    }
  }
  /** Map lexemes to an (unsorted) list of passages where the lexeme occurs.*/
  lazy val lexemeConcordance : Map[String, Vector[CtsUrn]]= {
    val pairings = for (l <- lexemes) yield {
      (l -> passagesForLexeme(l))
    }
    pairings.toMap
  }

  def labelledLexemeConcordance:  Map[String, Vector[CtsUrn]]= {
    lexemeConcordance.toVector.map{ case (lex,psgs) => (LewisShort.label(lex), psgs)}.toMap
  }

  /** Map ValidForms to passages where they occur.*/
  def formConcordance = {
    analyzed.flatMap(t => t.analyses.map(a =>
      (ValidForm(a.formUrn), t.urn))).groupBy(_._1).toVector.map{ case(k,v) => (k, v.map(_._2)) }.toMap
  }


  /**

  def multipleLexemesHistogram :  Histogram[String]= {
    val ambiguous: Vector[Frequency[String]] = multipleLexemes.map(l =>
      {
      val pct = dblPercent(lexTokenHistogram.countForItem(l.text), tokens.size)
      val item = l.text + s" (${pct}%): " +
      l.analyses.map (a => LewisShort.label(a.lemmaId)).distinct.mkString(", ")
      val count = lexTokenHistogram.countForItem(l.text)
      Frequency(item, count)
    }
    ).distinct
    Histogram(ambiguous)
  }
  */



  /** Cluster  into [[LatinCitableUnit]]s all [[LatinParsedToken]]s with common CTS URNs for the parent level of the passage hierarchy.
  */
  def clusterByCitation : Vector[LatinCitableUnit] = {
    val zipped = tokens.zipWithIndex
    val grouped = zipped.groupBy(_._1.urn.collapsePassageBy(1))
    val ordered = grouped.toVector.sortBy(_._2.head._2)
    //val tidy = ordered.map{ case (u,v) => (u, v.sortBy(_._2).map(_._1.text).mkString(" "))}

    ordered.map{ case (u,v) => LatinCitableUnit(v.sortBy(_._2).map(_._1)) }
  }


  /** Segment the sequence of tokens into [[LatinPhrase]]s based on punctuation
  ** tokens.
  */
  def segmentByPhrase(
    source: Vector[LatinParsedToken] = tokens,
    collected: Vector[LatinParsedToken] =   Vector.empty[LatinParsedToken],
    phrases : Vector[LatinPhrase] = Vector.empty[LatinPhrase]
  ): Vector[LatinPhrase] = {
    debug("Segmenting phrases: source size " + source.size)
    if (source.isEmpty) {
      phrases
    } else {

      if (source.head.category.toString == "PunctuationToken") {
        source.head.text match {
          case "." => {
            val phrase = LatinPhrase(collected :+ source.head)
            segmentByPhrase(source.tail, Vector.empty[LatinParsedToken], phrases :+ phrase)
          }
          case _ => segmentByPhrase(source.tail, collected :+ source.head, phrases)
        }


      } else {
        segmentByPhrase(source.tail, collected :+ source.head, phrases)
      }
    }
  }
}


object LatinCorpus extends LogSupport {

  //urn#label#passage#token#lexeme#form#category#sequence
  def apply(cexLines: Vector[String], separator: String = "#") : LatinCorpus = {
    val byToken = cexLines.groupBy( ln => {
      val cols = ln.split(separator)
      cols(2)
    })
    val indexed = byToken.toVector.map{ case (k,v) => {
      val sequence = indexCex(v.head)
      (v, sequence)
    }}
    val sorted = indexed.sortBy(_._2).map(_._1)
    LatinCorpus(sorted.map(tknLines => LatinParsedToken(tknLines)))
  }



  def fromFile(f: String, separator: String = "#", cexHeader: Boolean = true): LatinCorpus = {
    if (cexHeader) {
      LatinCorpus(Source.fromFile(f).getLines.toVector.tail, separator)
    } else {
      LatinCorpus(Source.fromFile(f).getLines.toVector, separator)
    }
  }

  def fromUrl(u: String, separator: String = "#", cexHeader: Boolean = true): LatinCorpus = {
    if (cexHeader) {
      LatinCorpus(Source.fromURL(u).getLines.toVector.tail, separator)
    } else {
      LatinCorpus(Source.fromURL(u).getLines.toVector, separator)
    }
  }

  // Read index value from column 7 of CEX line
  def indexCex(cex: String, separator: String = "#") : Int = {
    val columns = cex.split(separator)
    columns(7).toInt
  }



  /** Create a LatinCorpus by associating an OHCO2 Corpus in a known
  * orthographic system with morphological analyses.
  *
  * @param corpus A citable corpus of texts.
  * @param orthography An orthography to apply to the corpus. This permits
  * a classified tokenization of the corpus.
  * @param morphology Morphological analyses to associate with tokens in the
  * citable corpus.
  */
  def fromAnalyses(corpus: Corpus, orthography: MidOrthography, morphology: Vector[AnalyzedToken], strict: Boolean = true) : LatinCorpus = {
    debug("Creating tokenizable corpus...")
    val tokenizableCorpus = TokenizableCorpus(corpus, orthography)
    debug("Done creating tokenizable corpus.")
    debug(s"Cycle through ${tokenizableCorpus.tokens.size} tokenizable tokens.")

    //Logger.setDefaultLogLevel(LogLevel.WARN)
    val LatinParsedTokens  = for (tkn <- tokenizableCorpus.tokens) yield {

      // Use lower-case version to find case-insensitive morphology:
      val analyzedTokens = morphology.filter(_.token == tkn.string.toLowerCase)
      debug("From tokenizable corpus, " + tkn)
      val forms : Vector[LemmatizedForm] = if (analyzedTokens.size == 1) {
        val formVector: Vector[LemmatizedForm] = analyzedTokens(0).analyses
        formVector
      } else {
        Vector.empty[LemmatizedForm]
      }
      try {
        val lattkn = Some(LatinParsedToken(tkn.citableNode, tkn.tokenCategory.get, forms))
        debug("Produced LatinParsedToken for " + tkn.citableNode)
        lattkn
      } catch {
        case th : Throwable => {
          val msg = "Failed on token category opt " + tkn.tokenCategory +  ".  Citable node was \n\t" + tkn.citableNode
          if (strict) {
            throw new Exception(msg)

          } else {
            //warn(msg)
            None
          }

        }
      }
    }
    LatinCorpus(tokens = LatinParsedTokens.toVector.flatten)
  }


  /** Create a LatinCorpus by associating an OHCO2 Corpus in a known
  * orthographic system with morphological analyses expressed in the notation
  * of the SFST toolkit.
  *
  * @param corpus A citable corpus of texts.
  * @param orthography An orthography to apply to the corpus. This permits
  * a classified tokenization of the corpus.
  * @param fst Lines of output from a parser built with tabulae.
  */
  def fromFstLines(corpus: Corpus, orthography: MidOrthography, fst: Vector[String], strict: Boolean = true) : LatinCorpus = {
    //Logger.setDefaultLogLevel(LogLevel.DEBUG)
    debug("Computing analyses with FstReader")
    val analyses = FstReader.parseFstLines(fst)
    debug("Done computing analyses.")
    debug("Now generate corpus from analyses.")

    LatinCorpus.fromAnalyses(corpus, orthography, analyses, strict)
  }
}
