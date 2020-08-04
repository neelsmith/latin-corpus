
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

  "A TokenSequenceFilter" should "have a LatinParsedTokenSequence" in {
    val patternFilter =  TokenSequenceFilter(c108a)
    patternFilter.tokenSequence match {
      case lts: LatinParsedTokenSequence => assert(true)
      case _ => fail("Woops, that's not a LatinParsedTokenSequence")
    }

  }

  it should "filter the sequence for vectors containing substantives only in a given set of cases" in pending

  /*{
    assert(patternFilter.limitCase(Vector(Nominative, Genitive)) == false)
  }*/
}
