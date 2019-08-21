package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.validator._

case class LatinCorpus(tokens: Vector[LatinToken]) extends LatinTokenSequence {

  def clusterByCitation: Vector[LatinCitableUnit] = Vector.empty[LatinCitableUnit]
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
      LatinToken(tkn.citableNode, tkn.tokenCategory.get, forms)
    }
    LatinCorpus(tokens = latinTokens.toVector)
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
