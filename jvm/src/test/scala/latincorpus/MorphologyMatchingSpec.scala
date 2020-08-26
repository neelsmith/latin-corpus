
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._


import org.scalatest.FlatSpec

class MorphologyMatchingSpec extends FlatSpec {
  val corpusFile = "jvm/src/test/resources/hyginus-latc.cex"
  val corpus = LatinCorpus.fromFile(corpusFile)


  val chapterFile = "jvm/src/test/resources/c108a.cex"
  val chapter = LatinCorpus.fromFile(chapterFile, cexHeader = false)
  val testVerb = chapter.sentences.sequences(2).finiteVerbs(2)


  "A LatinParsedToken" should "test for a specified morphological property" in {
    val third = ClassifiedValue(PersonValues, Third)
    assert(testVerb.morphologyMatches(third))
    // plural, ind, act
  }

  it should "test for property/value combinations" in {
    assert(testVerb.morphologyMatches(TenseValues, Perfect))
    assert(testVerb.morphologyMatches(PersonValues, Third))
  }

  it should "be able test by and-ing morphological properties" in {
    val pft = ClassifiedValue(TenseValues, Perfect)
    val third = ClassifiedValue(PersonValues, Third)
    val testProps = Vector(pft, third)
    assert(testVerb.andMorphMatches(testProps))
  }

  it should "be able to test by or-ing morphological properties" in {
    val pft = ClassifiedValue(TenseValues, Perfect)
    val impft = ClassifiedValue(TenseValues, Imperfect)
    val testTenses = Vector(pft, impft)
    assert(testVerb.orMorphMatches(testTenses))
  }


  // test each class:

  it should "test on person" in {

  }


}
