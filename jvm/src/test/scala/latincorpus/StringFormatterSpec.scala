
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._


import org.scalatest.FlatSpec

class StringFormatterSpec extends FlatSpec {

  val chapterFile = "jvm/src/test/resources/c108a.cex"
  val chapter = LatinCorpus.fromFile(chapterFile, cexHeader = false)

  "The StringFormatter object" should "compose an HTML style attribute for spans matching morphological properties" in {

    val hl = "color: blue;"
    val properties = Vector(MorphologicalValue.indicative)
    val highlighter = FormsHighlighter(properties,hl)

    val formatted = StringFormatter.tokensFormStyled(chapter.tokens, highlighter, unanalyzedStyle = "")
    //println(formatted)
  }

  it should "compose an HTML style attribute for spans matching lexical items" in {
    val hl = "color: blue;"
    val transfero = "ls.n48775"
    val lexemes = Vector(transfero)
    val highlighter = LexemesHighlighter(lexemes,hl)

// tokens: Vector[LatinParsedToken],
  //highlighter: LexemesHighlighter,
  //lexemesIds: Vector[String],
    val formatted = StringFormatter.tokensLexemeStyled(
      chapter.tokens,
      highlighter,
      lexemes,
      unanalyzedStyle = "")
    println(formatted)
  }

  it should "compose an HTML  style attribute for spans matching part of speech" in pending

  it should "compose an an HTML class attribute for spans matching morphological properties" in pending

  it should "compose an HTML class attribute for spans matching lexical items" in pending

  it should "compose an HTML  class attribute for spans matching part of speech" in pending


  it should "compose hover-over HTML for spans matching morphological properties" in pending

  it should "compose hover-over HTML for spans matching lexical items" in pending

  it should "compose hover-over HTML for spans matching part of speech" in pending

  it should "define default CSS styling for morphological ambiguity" in {
    val expected = "text-decoration-line: underline; text-decoration-style: wavy;"
    assert(StringFormatter.defaultFormAmbiguityStyle == expected)
  }
  it should "define default CSS styling for lexical ambiguity" in {
    val expected = "border-bottom: solid;  padding: 3px;"
    assert(StringFormatter.defaultLexicalAmbiguityStyle == expected)
  }
  it should  "define default CSS styling for unanalyzed tokens" in {
    val expected = "border-bottom: dotted;"
    assert(StringFormatter.defaultUnanalyzedStyle == expected)
  }


}
