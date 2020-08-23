
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._


import org.scalatest.FlatSpec

class FunctionalMatchingSpec extends FlatSpec {
  val corpusFile = "jvm/src/test/resources/hyginus-latc.cex"
  val corpus = LatinCorpus.fromFile(corpusFile)


  val chapterFile = "jvm/src/test/resources/c108a.cex"
  val chapter = LatinCorpus.fromFile(chapterFile, cexHeader = false)
  val testSentence = chapter.sentences.sequences.head



  "The LatinParsedTokenSequence object" should "test if a sequence of tokens matches a sequence of function strings" in pending /* {

    val requiredFunctions = Vector("noun: nominative")
    val testMatches = LatinParsedTokenSequence.matchesFunctionStrings(sentences.head, requiredFunctions)
    assert(testMatches)
  }*/

  it should "filter a sequence of token sequences for those matching a sequence of function strings" in pending /* {
    val sentenceTokenClusters = LatinSentence.sentences(chapter.tokens)
    val requiredFunctions = Vector("noun: nominative")

    val nominatives = LatinParsedTokenSequence.filterFunctionStrings(sentenceTokenClusters, requiredFunctions)
    val expectedMatches = 5
    assert(nominatives.size  == expectedMatches)
  }*/



  it should "test if a sequence of tokens matches multiple sequences of function strings" in  pending /*{
    val list1 = Vector("noun: nominative")
    val list2 = Vector("noun: genitive")

    val testMatches = LatinParsedTokenSequence.matchesFunctionStringLists(sentences.head, Vector(list1, list2))
    assert(testMatches ==  false)
  }*/

  it should  "filter a sequence of token sequeneces for those matching multiple sequences of function strings" in  pending /*{
    val list1 = Vector("noun: nominative")
    val list2 = Vector("noun: genitive")

    val nomPlusGen = LatinParsedTokenSequence.filterFunctionStringLists(
      sentences,
      Vector(list1, list2)
    )
    val expectedSize = 2
    assert(nomPlusGen.size == expectedSize)
  }*/

  "An implementation of the LatinParsedTokenSequence trait" should "test if its tokens' sequence matches a sequence of function strings" in pending /* {
    val requiredFunctions = Vector("noun: nominative")
    val testMatches = sentences.head.matchesFunctionStrings(requiredFunctions)
    assert(testMatches)
  } */

  it should "test if its tokens' sequence matches multiple sequences of function strings" in pending /*{
    val list1 = Vector("noun: nominative")
    val list2 = Vector("noun: genitive")

    val testMatches = sentences.head.matchesFunctionStringLists(Vector(list1, list2))
    assert(testMatches ==  false)
  }*/

}
