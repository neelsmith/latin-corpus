package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.validator._
import edu.holycross.shot.histoutils._
import edu.holycross.shot.latin._


/**
*
* @param tokenCollection A Vector of [[LatinPhrase]]s or [[LatinCitableUnit]]s.
*/
case class MorphologyCollectionsFilter(tokenSequences: Vector[LatinTokenSequence]) {



  def limitSubstantiveCase(caseList: Vector[GrammaticalCase]) {
    println("We'll find sequences with only these cases: " + caseList)
    /*val matchingSequences = for (s <- tokenSequences) yield {
      val filter = TokenSequenceFilter(s)
      println(s"seq of ${s.size} nodes limited to  ${caseList}? " + filter.limitedToCases(caseList))
    }*/
    val matchingSequences = tokenSequences.filter(TokenSequenceFilter(_).limitedToCases(caseList))
    //matchingSequences.tokens.map(_.cn)
  }

  def limitVerbTenseMood(tenseMood: Vector[TenseMood]) = {}

  def limitVerbPersonNumber(personNumberList: Vector[PersonNumber]) = {}
}
