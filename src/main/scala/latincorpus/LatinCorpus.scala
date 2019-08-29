package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.validator._

import edu.holycross.shot.histoutils._

case class LatinCorpus(tokens: Vector[LatinToken], tcorpus: TokenizableCorpus) extends LatinTokenSequence {


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

  /** Create a histogram of lexemes.*/
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
  }

  /** Create a histogram of lexemes using labelled ID strings as the counted items.*/
  def labelledLexemeHistogram: Histogram[String] = {
    val labelled = lexemeHistogram.frequencies.map(fr => Frequency( LewisShort.label(fr.item),fr.count ))
    Histogram(labelled).sorted
  }

  /** Create a histogram of lexical tokens.*/
  def lexTokenHistogram : Histogram[String] = {
    val lexHist : Histogram[String] = tcorpus.lexHistogram
    lexHist.sorted
  }

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
  def segmentByPhrase: Vector[LatinPhrase] = Vector.empty[LatinPhrase]
}


object LatinCorpus {

  /** Create a LatinCorpus by associating an OHCO2 Corpus in a known
  * orthographic system with morphological analyses.
  *
  * @param corpus A citable corpus of texts.
  * @param orthography An orthography to apply to the corpus. This permits
  * a classified tokenization of the corpus.
  * @param morphology Morphological analyses to associate with tokens in the
  * citable corpus.
  */
  def fromAnalyses(corpus: Corpus, orthography: MidOrthography, morphology: Vector[AnalyzedToken]) : LatinCorpus = {
    val tokenizableCorpus = TokenizableCorpus(corpus, orthography)

    val latinTokens =   for (tkn <- tokenizableCorpus.tokens) yield {
      val analyzedTokens = morphology.filter(_.token == tkn.string)
      val forms : Vector[LemmatizedForm] = if (analyzedTokens.size == 1) {
        val formVector: Vector[LemmatizedForm] = analyzedTokens(0).analyses
        formVector
      } else {
        Vector.empty[LemmatizedForm]
      }
      try {
        LatinToken(tkn.citableNode, tkn.tokenCategory.get, forms)
      } catch {
        case th : Throwable => {
          println("Failed on token category opt " + tkn.tokenCategory)
          println("Citable node was " + tkn.citableNode)
          throw(th)
        }
      }
    }
    LatinCorpus(tokens = latinTokens.toVector, tokenizableCorpus)
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
  def fromFstLines(corpus: Corpus, orthography: MidOrthography, fst: Vector[String]) : LatinCorpus = {
    val analyses = FstReader.parseFstLines(fst)
    LatinCorpus.fromAnalyses(corpus, orthography, analyses)
  }
}
