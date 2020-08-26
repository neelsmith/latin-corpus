
package edu.holycross.shot.latincorpus

//import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._


import org.scalatest.FlatSpec

class FormsHighlighterSpec extends FlatSpec {


  val chapterFile = "jvm/src/test/resources/c108a.cex"
  val chapter = LatinCorpus.fromFile(chapterFile, cexHeader = false)
  val testVerb = chapter.sentences.sequences(2).finiteVerbs(2)


  // include in HTML style element:
  val hl = "color: blue;"
  val properties = Vector(MorphologicalValue.third, MorphologicalValue.perfect)


  "A FormsHighlighter" should "associate a highlighting String with a Vector of ClassifiedValues" in {
    val  hiliter = FormsHighlighter(properties, hl)
    val expectedString = "color: blue;"
    assert(hiliter.highlightString == expectedString)
    val expectedProperties = 2
    assert(hiliter.properties.size == expectedProperties)
  }

  it should "determine if a given token needs highlighting"  in {
    val  hiliter = FormsHighlighter(properties, hl)
    assert(hiliter.addHighlight(testVerb))
  }

  it should "support or-ing properties" in {
    val tenseOptions = Vector(MorphologicalValue.imperfect, MorphologicalValue.perfect)
    val  hiliter = FormsHighlighter(tenseOptions, hl, booleanAnd = false)
    assert(hiliter.addHighlight(testVerb))
  }

  it should "return a highlight string for matching tokens" in {
    val  hiliter = FormsHighlighter(properties, hl)
    val expectedString = "color: blue;"
    val actualString = hiliter.highlightForToken(testVerb)
    assert(actualString == expectedString)
  }

  it should "return an empty string for non-matching tokens" in {
    val imperfectSubj = Vector(MorphologicalValue.imperfect, MorphologicalValue.subjunctive)
    val  hiliter = FormsHighlighter(imperfectSubj, hl)
    val actualString = hiliter.highlightForToken(testVerb)
    assert(actualString.isEmpty)
  }


}
