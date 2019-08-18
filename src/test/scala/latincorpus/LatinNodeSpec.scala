
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._

import org.scalatest.FlatSpec

class LatinNodeSpec extends FlatSpec {
  "A LatinNode" should "have a URN" in {
    val urn = CtsUrn("urn:cts:latinLit:phi0959.phi006:1.1")
    val cn = CitableNode(urn,"In nova fert animus mutatas dicere formas")
    val latinNode = LatinNode(cn)

    assert(latinNode.urn == urn)
  }
}
