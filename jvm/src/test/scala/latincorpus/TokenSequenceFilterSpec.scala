
package edu.holycross.shot.latincorpus



import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.latin._

import org.scalatest.FlatSpec

class TokenSequenceFilterSpec extends FlatSpec {

  val f = "jvm/src/test/resources/c108a.cex"
  val lc = LatinCorpus.fromFile(f)
  val citableUnits = lc.clusterByCitation
  val c108a = citableUnits.head

  "A TokenSequenceFilter" should "extract distinct values of grammatical case" in {
    val expected = Set(Nominative, Accusative, Genitive, Dative, Ablative, Vocative)
    assert(c108a.valuesForCategory(GrammaticalCaseValues).toSet == expected)
  }

  it should "extract distinct values of gender" in {
    val expected =    Set(Masculine, Feminine, Neuter)
    assert(c108a.valuesForCategory(GenderValues).toSet == expected)
  }

  //it should "extract distinct values of number" in pending
  //it should "extract distinct values of degree" in pending
  it should "extract distinct values of person" in {
    val expected = Set(Second, Third)
    assert(c108a.valuesForCategory(PersonValues).toSet == expected)
  }
  it should "extract distinct values of tense" in {
    val expected = Set(Present, Imperfect, Perfect, Pluperfect)
    assert(c108a.valuesForCategory(TenseValues).toSet == expected)
  }
  it should "extract distinct values of mood" in {
    val expected = Set(Indicative, Subjunctive, Imperative)
    assert(c108a.valuesForCategory(MoodValues).toSet == expected)
  }
  it should "extract distinct values of voice" in {
    val expected = Set(Passive, Active)
    assert(c108a.valuesForCategory(VoiceValues).toSet == expected)
  }


}
