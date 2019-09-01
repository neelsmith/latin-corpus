
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.validator._
import edu.holycross.shot.latin._
import scala.io._

import org.scalatest.FlatSpec
import wvlet.log.LogFormatter.SourceCodeLogFormatter

class LatinCorpusObjectSpec extends FlatSpec {




  "The LatinCorpus object" should "make a LatinCorpus from fst" in {
    val fst = """> ut
<u>livymorph.indecl33</u><u>ls.n49975</u>ut<indecl><indeclconj><div><indeclconj><indecl><u>indeclinfl.2</u>
> opinor
<u>latcommon.verbn32747</u><u>ls.n32747</u><#>opin<verb><conj1><div><conj1><verb>or<1st><sg><pres><indic><pass><u>latcommon.are_conj1pres7</u>
""".split("\n").toVector
    val cn1 = CitableNode(
      CtsUrn("urn:cts:omar:stoa0179.stoa001.omar:1.4.1"),
      "ut opinor"
    )
    val corpus = Corpus(Vector(cn1))

    val ortho = Latin24Alphabet
    val lc = LatinCorpus.fromFstLines(corpus,ortho,fst)
    assert(lc.size == 2)
  }

  it should "tolerate bad input" in {

val fst = """> ut
<u>livymorph.indecl33</u><u>ls.n49975</u>ut<indecl><indeclconj><div><indeclconj><indecl><u>indeclinfl.2</u>
> opinor
<u>latcommon.verbn32747</u><u>ls.n32747</u><#>opin<verb><conj1><div><conj1><verb>or<1st><sg><pres><indic><pass><u>latcommon.are_conj1pres7</u>
""".split("\n").toVector
    // This should fail with Latin23Alphabet:
    val cn1 = CitableNode(
      CtsUrn("urn:cts:omar:stoa0179.stoa001.omar:1.4.1"),
      "VT OPINOR"
    )
    val corpus = Corpus(Vector(cn1))
    val ortho = Latin23Alphabet
    val lc = LatinCorpus.fromFstLines(corpus,ortho,fst, strict = false)
    assert(lc.size == 1)
  }

  it should "default to strict parsing" in {

val fst = """> ut
<u>livymorph.indecl33</u><u>ls.n49975</u>ut<indecl><indeclconj><div><indeclconj><indecl><u>indeclinfl.2</u>
> opinor
<u>latcommon.verbn32747</u><u>ls.n32747</u><#>opin<verb><conj1><div><conj1><verb>or<1st><sg><pres><indic><pass><u>latcommon.are_conj1pres7</u>
""".split("\n").toVector
    // This should fail with Latin23Alphabet:
    val cn1 = CitableNode(
      CtsUrn("urn:cts:omar:stoa0179.stoa001.omar:1.4.1"),
      "VT OPINOR"
    )
    val corpus = Corpus(Vector(cn1))
    val ortho = Latin23Alphabet
    try {
      val lc = LatinCorpus.fromFstLines(corpus,ortho,fst)
    } catch {
      case t: Throwable => {
        assert(t.toString.contains("Failed on token category opt None"))
      }
    }
  }

  it should "keep LatinTokens aligned with tokens of TokenizableCorpus" in {
    val corpus = CorpusSource.fromFile("src/test/resources/cex/livy-mt.cex", cexHeader=true)
    val fstLines = Source.fromFile("src/test/resources/fst/livy-mt-parsed.txt").getLines.toVector
    val latin = LatinCorpus.fromFstLines(corpus, Latin24Alphabet, fstLines, strict=false)

    assert( latin.size == latin.tcorpus.size)

    // Check last n tokens:
    val n = 30
    val max = latin.size - 1
    val start = max - n
    //val testGroup = latin.tokens.slice( (max - n), max)
    for (i <- start until max) {
      assert(latin.tokens(i).text == latin.tcorpus.tokens(i).text)
    }
  }



}
