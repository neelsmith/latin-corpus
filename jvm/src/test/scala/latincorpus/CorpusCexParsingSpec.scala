
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._

import org.scalatest.FlatSpec

class CorpusCexParsingSpec extends FlatSpec {

  //


  "The LatinParsedToken object" should "be able to parse a CEX record of the parsed token data model into a LatinParsedToken" in {
    val cexLine = "urn:cite2:linglat:tkns.v1:2020_08_02_12#Record 2020_08_02_12#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.1.5#et#urn:cite2:tabulae:ls.v1:n16278#urn:cite2:tabulae:morphforms.v1:00000000A#LexicalToken#12"
    val tkn = LatinParsedToken.fromCexLines(Vector(cexLine))

    val expectedUrn = CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.1")
    val expectedText = "et"
    assert(tkn.cn.urn == expectedUrn)
    assert(tkn.cn.text == expectedText)

  }
}
