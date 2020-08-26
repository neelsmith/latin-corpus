
package edu.holycross.shot.latincorpus

//import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._


import org.scalatest.FlatSpec

class LexemesHighlighterSpec extends FlatSpec {


  val chapterFile = "jvm/src/test/resources/c108a.cex"
  val chapter = LatinCorpus.fromFile(chapterFile, cexHeader = false)
  val testVerb = chapter.sentences.sequences(2).finiteVerbs(2)


  // include in HTML style element:
  val hl = "color: blue;"
  val transfero = "ls.n48775"
  val lexemes = Vector(transfero)

  "A LexemesHighlighter" should "associate a highlighting String with a Vector of lexeme IDs" in  {
    val  hiliter = LexemesHighlighter(lexemes, hl)
    val expectedString = "color: blue;"
    assert(hiliter.highlightString == expectedString)
    val expectedLexemes = 1
    assert(hiliter.lexemeIds.size == expectedLexemes)
  }


  it should "determine if a given token needs highlighting"  in  {
    val  hiliter = LexemesHighlighter(lexemes, hl)
    assert(hiliter.addHighlight(testVerb))
  }


  it should "return a highlight string for matching tokens" in {
    val  hiliter = LexemesHighlighter(lexemes, hl)
    val expectedString = "color: blue;"
    val actualString = hiliter.highlightForToken(testVerb)
    assert(actualString == expectedString)
  }

  it should "return an empty string for non-matching tokens" in  {
    val badId  = Vector("ls.notAnId")
    val  hiliter = LexemesHighlighter(badId, hl)
    val actualString = hiliter.highlightForToken(testVerb)
    assert(actualString.isEmpty)
  }



}
