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


  /** Find [[LatinTokenSequence]]s in substantives use only the
  * grammatical cases listed.
  *
  * @param caseList Limiting list of grammatical cases.
  */
  def limitSubstantiveCase(caseList: Vector[GrammaticalCase]) : Vector[LatinCitableUnit] = {
    val matchingSequences = tokenSequences.filter(TokenSequenceFilter(_).limitedToCases(caseList))
    val citableUnits = matchingSequences.map(tkn => tkn match {
      case citable: LatinCitableUnit => citable
    })
    citableUnits
  }


  def limitSubstantiveCaseNodes(caseList: Vector[GrammaticalCase]) : Vector[CitableNode] = {
    val citables = limitSubstantiveCase(caseList)
    citables.map(_.canonicalNode)
  }

  def limitSubstantiveCaseText(caseList: Vector[GrammaticalCase]) : Vector[String] = {
    val citableNodes = limitSubstantiveCaseNodes(caseList)
    citableNodes.map(_.text)
  }



  def limitVerbTenseMood(tenseMood: Vector[TenseMood]) = {}

  def limitVerbPersonNumber(personNumberList: Vector[PersonNumber]) = {}
}
