package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.validator._

import edu.holycross.shot.histoutils._

case class LatinCorpus(tokens: Vector[LatinToken], tcorpus: TokenizableCorpus) extends LatinTokenSequence {





  def lexTokenHistogram : Histogram[String] = {
    val lexHist : Histogram[String] = tcorpus.lexHistogram
    lexHist.sorted
  }

  def tokenConcordance  = {
    tcorpus.concordance
  }

  def tokenLexemeIndex = {
    val forms1 = this.analyzed.map(t => (t.text, t.analyses.map(_.lemmaId).distinct))
    forms1.groupBy(_._1).map{ case (s,v) => (s, v.map(_._2).flatten)}
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
