
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.latin._

import org.scalatest.FlatSpec

class LatinTokenSequenceSpec extends FlatSpec {

  val f = "jvm/src/test/resources/c108a.cex"
  val lc = LatinCorpus.fromFile(f)
  val citableUnits = lc.citableUnits
  val c108a = citableUnits.sequences.head

  "A LatinParsedTokenSequence" should "extract noun tokens" in {
    println(c108a.size + " tokens, " + c108a.nouns.size + " nouns.")
  }
  it should "extract  verb tokens" in {
    println(c108a.size + " tokens, " + c108a.finiteVerbs.size + " finite verbs forms.")
  }
  it should "extract pronoun tokens" in {
    println(c108a.size + " tokens, " + c108a.pronouns.size + " pronouns.")
  }
  it should "extract participle tokens" in {
    println(c108a.size + " tokens, " + c108a.participles.size + " participles.")
  }
  it should "extract adjective tokens" in {
    println(c108a.size + " tokens, " + c108a.adjectives.size + " adjectives.")
  }
  it should "extract adverb tokens" in {
      println(c108a.size + " adverbs, " + c108a.adjectives.size + " adverbs.")
  }
  it should "extract gerund tokens" in pending
  it should "extract gerundive tokens" in pending
  it should "extract supine tokens" in {
    println(c108a.size + " tokens, " + c108a.supines.size + " supines.")
  }
  it should "extract uninflected tokens" in {
    println(c108a.size + " tokens, " + c108a.uninflecteds.size + " uninflected tokens.")
  }
  it should "cover all the pos tokens in the citable unit" in pending /* {
    val totals = c108a.nouns.size + c108a.finiteVerbs.size + c108a.pronouns.size + c108a.participles.size + c108a.adjectives.size + c108a.adjectives.size +  c108a.supines.size + c108a.indeclinables.size
    assert(c108a.size  == totals)
  }*/

/*
supine
*/

}
