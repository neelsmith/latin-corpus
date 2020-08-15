
package edu.holycross.shot.latincorpus



import org.scalatest.FlatSpec


class CorpusIndexingSpec extends FlatSpec {

  val corpusFile = "jvm/src/test/resources/hyginus-latc.cex"
  val chapterFile = "jvm/src/test/resources/c108a.cex"
  val corpus = LatinCorpus.fromFile(corpusFile)
  val chapter = LatinCorpus.fromFile(chapterFile)

  "A LatinCorpus" should "index from tokens to lexemes" in {
    val expectedSize = chapter.analyzed.map(_.text).distinct.size

    assert(chapter.tokenLexemeIndex.size == expectedSize)
    // Test relative/interrogative ambiguity:
    val expectedLexemes = 2
    assert(chapter.tokenLexemeIndex("quem").size == expectedLexemes)
  }

  it should "index from lexemes to tokens" in {
    val expectedForms = Set("sunt", "essent", "est")
    val sum = "ls.n46529"
    assert(chapter.lexemeTokenIndex(sum).toSet == expectedForms)
  }

  it should "pair lexemes and tokens" in pending /*{
    // This is OK: figure out a good test for it:
    println(chapter.lexemeTokenPairings.size)
  }*/

}
