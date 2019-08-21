package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.validator._

case class LatinCorpus(tokens: Vector[LatinToken]) extends LatinTokenSequence {



  lazy val size : Int = tokens.size

  lazy val singleAnalysis = {
    tokens.filter(_.analyses.size == 1)
  }
  lazy val noAnalysis = {
    tokens.filter(_.analyses.isEmpty)
  }
  lazy val mutipleAnalyses = {
    tokens.filter(_.analyses.size > 1)
  }

  /*lazy val verbs = {
    tokens.filter()
  }*/
}


object LatinCorpus {

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

  def fromFstLines(corpus: Corpus, orthography: MidOrthography, fst: Vector[String]) : LatinCorpus = {
    val analyses = FstReader.parseFstLines(fst)
    LatinCorpus.fromAnalyses(corpus, orthography, analyses)
  }



}
