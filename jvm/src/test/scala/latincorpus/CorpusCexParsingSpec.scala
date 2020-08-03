
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._

import scala.io.Source

import org.scalatest.FlatSpec

class CorpusCexParsingSpec extends FlatSpec {

  val cexLine = "urn:cite2:linglat:tkns.v1:2020_08_02_12#Record 2020_08_02_12#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.1.5#et#urn:cite2:tabulae:ls.v1:n16278#urn:cite2:tabulae:morphforms.v1:00000000A#LexicalToken#12"


  val f = "jvm/src/test/resources/sect196a.cex"
  val sectionLines = Source.fromFile(f).getLines.toVector

  "The LatinParsedToken object" should "be able to parse a CEX record of the parsed token data model into a LatinParsedToken" in {
    val lines = Vector(cexLine)
    val tkn = LatinParsedToken.fromCexLines(lines)

    val expectedUrn = CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.1")
    val expectedText = "et"
    assert(tkn.cn.urn == expectedUrn)
    assert(tkn.cn.text == expectedText)
  }

  it should "accept CEX forms in the apply method" in {
    val lines = Vector(cexLine)
    val tkn = LatinParsedToken(lines)

    val expectedUrn = CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.1")
    val expectedText = "et"
    assert(tkn.cn.urn == expectedUrn)
    assert(tkn.cn.text == expectedText)
  }

  "The LatinCorpus object" should "be able to read an integer sequence value from CEX" in {
    val expectedSequence = 12
    assert(LatinCorpus.indexCex(cexLine) == expectedSequence)
  }


  it should "group together analyses by token" in {
    val latc = LatinCorpus(sectionLines)
    val expectedTokens = 65
    assert(latc.size == expectedTokens)
  }

  it should "create a corpus from a URL" in {
    val url = "https://raw.githubusercontent.com/neelsmith/latin-corpus/master/jvm/src/test/resources/sect196a.cex"
    val latc = LatinCorpus.fromUrl(url, cexHeader = false)
    val expectedTokens = 65
    assert(latc.size == expectedTokens)
  }

  it should "create a corpus from a file" in {
    val latc = LatinCorpus.fromFile(f, cexHeader = false)
    val expectedTokens = 65
    assert(latc.size == expectedTokens)
  }

}
