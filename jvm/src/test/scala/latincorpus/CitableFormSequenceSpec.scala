package edu.holycross.shot.latincorpus

import edu.holycross.shot.tabulae._

import org.scalatest.FlatSpec

class CitableFormSequenceSpec extends FlatSpec {


  val chapterFile = "jvm/src/test/resources/c108a.cex"
  val chapter = LatinCorpus.fromFile(chapterFile, cexHeader = false)
  val tokens = chapter.tokens
  val sentences = LatinSentence.sentences(tokens)

  "A LatinParsedTokenSequence" should "be able to compose a Vector of citable forms per token" in pending /*{
    for ((s, i) <- sentences.zipWithIndex) {
      assert(sentences(i).citableFormsPerToken(umgr).size == sentences(i).lexicalTokens.size)
    }
  }*/

  it should "be able to compose a flat list of citable forms" in pending /*{
    for ((s, i) <- sentences.zipWithIndex) {
      val flatCount = sentences(i).analyzed.map(_.analyses.size).sum  + LatinSentence(sentences(i).lexicalTokens).noAnalysis.size
      assert(sentences(i).citableForms(umgr).size == flatCount)
    }
  }*/

  it should "be able to compose a Vector of function strings per token" in pending /* {
    for ((s, i) <- sentences.zipWithIndex) {
      assert(sentences(i).functionStringsPerToken(umgr).size == sentences(i).lexicalTokens.size)
    }
  } */
  it should "be able to compose a flat list of function strings" in pending /*{
    for ((s, i) <- sentences.zipWithIndex) {
      val flatCount = sentences(i).analyzed.map(_.analyses.size).sum  + LatinSentence(sentences(i).lexicalTokens).noAnalysis.size
      assert(sentences(i).functionStrings(umgr).size == flatCount)
    }
  }
*/



}
