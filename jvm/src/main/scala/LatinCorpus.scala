package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._

import edu.holycross.shot.histoutils._

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter


/** A morpholgoically parsed Latin corpus citable at the level
* of classified tokens.
*
* @param tokens Ordered list of [[LatinTokene]]s making u this corpus.
* @param tcorpus
*/
case class LatinCorpus(tokens: Vector[LatinToken], tcorpus: TokenizableCorpus) extends LatinTokenSequence {
  //Logger.setDefaultLogLevel(LogLevel.WARN)
  val formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd")
  val todayFormatted = LocalDate.now.format(formatter)

  def analysisUrns(umgr: UrnManager): Vector[LemmatizedFormUrns] = tokens.map(_.analysisUrns(umgr: UrnManager)).flatten

  def analysisCex(umgr: UrnManager, separator: String = "#") : Vector[String] = analysisUrns(umgr).map(_.cex(separator))

  def citeCollectionLines(umgr: UrnManager, urnBase: String = "urn:cite2:linglat:tkns.v1:", separator: String = "#") = {
    val citable = for ( (ln, i) <- analysisCex(umgr, separator).zipWithIndex) yield {
      val recordId = todayFormatted + "_" + i
      val urnStr = urnBase + recordId
      val label = "Record " + recordId
      urnStr + "#" + label + "#" + ln
    }
    citable
  }





  /*
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
  }*/

  /** Compute percent as a Double to a given scale.
  *
  * @param n Number.
  * @param total Total of which n is some percent.
  * @param scale Scale for resulting Double.
  */
  def dblPercent(n: Int, total: Int, scale: Int = 2) : Double = {
    val flt = ((n / total.toDouble) * 100).toDouble

    BigDecimal(flt).setScale(scale, BigDecimal.RoundingMode.HALF_UP).toDouble
  }


  /** Compute percent as an Int.
  *
  * @param n Number.
  * @param total Total of which n is some percent.
  */
  def percent(n: Int, total: Int) : Int = {
    ((n / total.toDouble) * 100).toInt
  }

  /** Flat list of every combination of individual lexeme
  * with individual token. */
  def lexemeTokenPairings = {
    val idx = this.tokenLexemeIndex
    val lemmaVectorsWithTokens = this.analyzed.map(t => (idx(t.text), t.text))
    val distinctLemmasPlusTokens = lemmaVectorsWithTokens.map{ case (v, t) => v.map(id => (id, t)) }.flatten.distinct
    distinctLemmasPlusTokens
  }

  /** Map lexemes to an (unsorted) list of passages where the lexeme occurs.*/
  def lexemeConcordance : Map[String, Vector[CtsUrn]]= {
    val distinctLemmasPlusTokens = lexemeTokenPairings
    val concData = distinctLemmasPlusTokens.map{ case (lex,tkn) => (lex, tcorpus.concordance(tkn) ) }
    concData.toMap
  }


  /** Safe lookup in lexeme concordance.  Returns empty
  * Vector if lexeme not found.
  *
  * @param lexId ID for lexeme to look up.
  */
  def passagesForLexeme(lexId: String) : Vector[CtsUrn] = {
    try {
      lexemeConcordance(lexId)
    } catch {
      case nsee: NoSuchElementException => Vector.empty[CtsUrn]
      case t: Throwable => throw t
    }
  }

  /** Create a histogram of lexemes.
  def lexemeHistogram : Histogram[String] = {
    // avoid repeating function call: generate this map once
    val lemmaWithGroupedCounts = lexemeTokenPairings.map{
      case (lemma,token) => (lemma, tcorpus.lexHistogram.countForItem(token))
    }groupBy(_._1)
    //val grouped = lemmaWithCounts.
    val totaled =  lemmaWithGroupedCounts.map { case (k,v) => Frequency(k, v.map(_._2).sum) }
    //val mapped = totaled.toVector.sortBy(_._2).reverse.map{ case (lemma, count) => lemma -> count }
    //mapped
    Histogram(totaled.toVector).sorted
  }*/

  /** Create a histogram of lexemes using labelled ID strings as the counted items.
  def labelledLexemeHistogram: Histogram[String] = {
    val labelled = lexemeHistogram.frequencies.map(fr => Frequency( LewisShort.label(fr.item),fr.count ))
    Histogram(labelled).sorted
  }
*/
  /** Create a histogram of lexical tokens.
  def lexTokenHistogram : Histogram[String] = {
    val lexHist : Histogram[String] = tcorpus.lexHistogram
    lexHist.sorted
  }
*/
  /** Concordance of all lexical tokens in corpus.*/
  def tokenConcordance  = {
    tcorpus.concordance
  }

  /** Index of tokens to Vector of identifiers for lexeme.*/
  def tokenLexemeIndex : Map[String,Vector[String]] = {
    val analyzedForms = this.analyzed.map(t => (t.text, t.analyses.map(_.lemmaId).distinct))
    analyzedForms.groupBy(_._1).map{ case (s,v) => (s, v.map(_._2).flatten.distinct)}
  }

  /** Index of lexemes to Vector of tokens.*/
  def lexemeTokenIndex  ={ //: Map[String,Vector[String]] = {

    val reversedIndex = tokenLexemeIndex.toVector.map{ case (s,v) => v.map(el => (s,el))}.flatten
    val indexVector = reversedIndex.groupBy(_._1).toVector.map{ case (s,v) => (s, v.map(_._2))}
    indexVector.map{ case (s,v) => s -> v }.toMap
  }


  /** Create a histogram of LemmatizedForms.*/
  def formHistogram : Histogram[LemmatizedForm] = {
    val freqs = this.analyzed.flatMap(_.analyses).groupBy(f => f).map{ case(k,v) => Frequency(k, v.size) }
    Histogram(freqs.toVector).sorted
  }


  def formConcordance = {
    val urnPlusAnalyses = this.tokens.map(t => (t.urn, t.analyses))
    val pairings = urnPlusAnalyses.flatMap{ case (k,v)  => v.map(f => (f,k))}
    pairings.groupBy(_._1).map{ case (k,v) => k -> v.map(_._2) }

  }

  /** Cluster  into [[LatinCitableUnit]]s all [[LatinToken]]s with common CTS URNs for the parent level of the passage hierarchy.
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
    source: Vector[LatinToken] = tokens,
    collected: Vector[LatinToken] =   Vector.empty[LatinToken],
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
            segmentByPhrase(source.tail, Vector.empty[LatinToken], phrases :+ phrase)
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
    val latinTokens  = for (tkn <- tokenizableCorpus.tokens) yield {

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
        val lattkn = Some(LatinToken(tkn.citableNode, tkn.tokenCategory.get, forms))
        debug("Produced LatinToken for " + tkn.citableNode)
        lattkn
      } catch {
        case th : Throwable => {
          val msg = "Failed on token category opt " + tkn.tokenCategory +  ".  Citable node was \n\t" + tkn.citableNode
          if (strict) {
            throw new Exception(msg)

          } else {
            warn(msg)
            None
          }

        }
      }
    }
    LatinCorpus(tokens = latinTokens.toVector.flatten, tokenizableCorpus)
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
