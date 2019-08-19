
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.validator._

import org.scalatest.FlatSpec

class LatinNodeSpec extends FlatSpec {
  "A LatinNode" should "have a URN" in {
    val urn = CtsUrn("urn:cts:latinLit:phi0959.phi006:1.1.1")
    val cn = CitableNode(urn,"In")
    val indeclForm = IndeclinableForm("ls.n22111", "latcommon.n22111","latcommon.indeclinfl1", Preposition)

    val latinToken = LatinToken(cn, LexicalToken, Vector(indeclForm))

    assert(latinToken.urn == urn)
    assert(latinToken.text == "In")
    //assert
  }
}
