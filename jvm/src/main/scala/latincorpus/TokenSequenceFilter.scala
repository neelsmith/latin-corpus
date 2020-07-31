package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.histoutils._
import edu.holycross.shot.latin._

/**
*
* @param tokenCollection A Vector of [[LatinPhrase]]s or [[LatinCitableUnit]]s.
*/
case class TokenSequenceFilter(tokenSequence: LatinParsedTokenSequence) {


  /** True if all substantives in sequence can be analyzed using only
  * the listed grammatical cases.
  *
  * @param caseList Cases to look for.
  */
  def limitedToCases(caseList: Vector[GrammaticalCase]): Boolean = {
    val analyses = tokenSequence.tokens.map(_.analyses).filter(_.nonEmpty)

    // Collect a T/F evaluation of each token:
    val checkList = for (tkn <- tokenSequence.tokens) yield {
      // Find all possible case identifications for this token.
      val substantiveCaseOpts = for (analysis <- tkn.analyses) yield {
        analysis.substantiveCase
      }
      val substantiveCases = substantiveCaseOpts.flatten


       if (substantiveCases.isEmpty) {
         // not relevant, so OK
         true

       } else {
        val lexemes = tkn.analyses.map(_.lemmaId).distinct
        if (lexemes.size == 1) {
          // When only 1 lemma, we consider the analysis list a match
          // if any analysis matches any of the cases in the list
          val result = substantiveCases.intersect(caseList).nonEmpty
          result

        } else {
           // Maybe change this...?
          false
        }
      }
    }
    // Final result is true only if all tokens can be analyzed
    // using only this list of cases:
   (! checkList.contains(false))
  }

  def limitVerbTenseMood(tenseList: Vector[Tense], moodList: Vector[Mood]) = {}

  def limitVerbPersonNumber(personList: Vector[Person], numberList: Vector[GrammaticalNumber]) = {}
}
