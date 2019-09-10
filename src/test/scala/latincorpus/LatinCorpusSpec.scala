
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.validator._
import edu.holycross.shot.latin._
import scala.io.Source

import org.scalatest.FlatSpec

class LatinCorpusSpec extends FlatSpec {

val fst = """> sed
<u>livymorph.indecl18</u><u>ls.n43291</u>sed<indecl><indeclconj><div><indeclconj><indecl><u>indeclinfl.2</u>
> debebatur
<u>latcommon.verbn12387</u><u>ls.n12387</u><#>deb<verb><conj2><div><conj2><verb>ebatur<3rd><sg><impft><indic><pass><u>latcommon.ere_conj2impft9</u>
> ut
<u>livymorph.indecl33</u><u>ls.n49975</u>ut<indecl><indeclconj><div><indeclconj><indecl><u>indeclinfl.2</u>
> opinor
<u>latcommon.verbn32747</u><u>ls.n32747</u><#>opin<verb><conj1><div><conj1><verb>or<1st><sg><pres><indic><pass><u>latcommon.are_conj1pres7</u>
> fatis
<u>latcommon.n17799</u><u>ls.n17799</u>fat<noun><neut><us_i><div><us_i><noun>is<neut><dat><pl><u>livymorph.us_i20</u>
<u>latcommon.n17799</u><u>ls.n17799</u>fat<noun><neut><us_i><div><us_i><noun>is<neut><abl><pl><u>livymorph.us_i22</u>
> tantae
<u>latcommon.adjn47473</u><u>ls.n47473</u>tant<adj><us_a_um><div><us_a_um><adj>ae<fem><nom><pl><pos><u>latcommoninfl.us_a_um42</u>
<u>latcommon.adjn47473</u><u>ls.n47473</u>tant<adj><us_a_um><div><us_a_um><adj>ae<fem><gen><sg><pos><u>latcommoninfl.us_a_um38</u>
<u>latcommon.adjn47473</u><u>ls.n47473</u>tant<adj><us_a_um><div><us_a_um><adj>ae<fem><dat><sg><pos><u>latcommoninfl.us_a_um39</u>
<u>latcommon.adjn47473</u><u>ls.n47473</u>tant<adj><us_a_um><div><us_a_um><adj>ae<fem><voc><pl><pos><u>latcommoninfl.us_a_um48</u>
<u>livymorph.adj30</u><u>ls.n47473</u>tant<adj><us_a_um><div><us_a_um><adj>ae<fem><nom><pl><pos><u>latcommoninfl.us_a_um42</u>
<u>livymorph.adj30</u><u>ls.n47473</u>tant<adj><us_a_um><div><us_a_um><adj>ae<fem><gen><sg><pos><u>latcommoninfl.us_a_um38</u>
<u>livymorph.adj30</u><u>ls.n47473</u>tant<adj><us_a_um><div><us_a_um><adj>ae<fem><dat><sg><pos><u>latcommoninfl.us_a_um39</u>
<u>livymorph.adj30</u><u>ls.n47473</u>tant<adj><us_a_um><div><us_a_um><adj>ae<fem><voc><pl><pos><u>latcommoninfl.us_a_um48</u>
> origo
<u>latcommon.n32994</u><u>ls.n32994</u>orig<noun><fem><o_inis><div><o_inis><noun>o<fem><nom><sg><u>latcommoninfl.o_inis13</u>
<u>latcommon.n32994</u><u>ls.n32994</u>orig<noun><fem><o_inis><div><o_inis><noun>o<fem><voc><sg><u>latcommoninfl.o_inis23</u>
> urbis
<u>latcommon.n49895</u><u>ls.n49895</u>urb<noun><fem><s_is><div><s_is><noun>is<fem><acc><pl><u>latcommoninfl.s_is21b</u>
<u>latcommon.n49895</u><u>ls.n49895</u>urb<noun><fem><s_is><div><s_is><noun>is<fem><gen><sg><u>latcommoninfl.s_is14</u>
""".split("\n").toVector



  val cn1 = CitableNode(
    CtsUrn("urn:cts:omar:stoa0179.stoa001.omar:1.4.1"),
    "sed debebatur, ut opinor, fatis tantae origo urbis"
  )
  val corpus = Corpus(Vector(cn1))
  val ortho = Latin24Alphabet
  val lc = LatinCorpus.fromFstLines(corpus,ortho,fst)

  "A LatinCorpus" should "have a Vector of LatinTokens" in {
    lc.tokens(0) match {
      case lt: LatinToken => assert(true)
      case _ => fail("Did not create a LatinToken")
    }
  }

  it should "identify all analyzed tokens" in {
    val expectedTokens = 8
    assert(lc.analyzed.size == expectedTokens)
  }
  it should "find uniquely analyzed tokens" in {
    val expectedTotal = 8
    assert(lc.analyzed.size == expectedTotal)

    val expectedUnique = 4
    assert(lc.singleAnalysis.size  == expectedUnique)
  }
  it should "collect the total of all possible analyses" in {
    val expectedAnalyses = 18
    assert(lc.allAnalyses.size == expectedAnalyses)
  }

  it should "therefore be able to measure the token-level ambiguity" in  {
    val expectedAmbiguity = 2.25
    assert(lc.tokenAmbiguity == expectedAmbiguity)
  }
  it should "collect all tokens with token-level ambiguity" in  pending

  it should "be able to measure lexeme-level ambiguity" in pending/* {

    println("Lexical ambiguity: " + lc.lexicalAmbiguity)
  }*/

  it should "be able to construct a histogram of forms" in {
    val expectedMaximum = 2
    assert(lc.formsHistogram.sorted.frequencies(0).count == expectedMaximum)
  }
  it should "construct a histogram of lexemes" in {
    val expectedSize = 8
    assert(lc.lexemeHistogram.size == expectedSize)
  }

  it should "cluster a corpus into citable nodes" in  {
    val clustered = lc.clusterByCitation
    val numberClusters = 1
    assert(clustered.size == numberClusters)
  }

  it should "construct a concordance of lexemes" in  {
    val expectedPassages =  Vector(CtsUrn("urn:cts:omar:stoa0179.stoa001.omar_tkns:1.4.1"))
    assert(lc.lexemeConcordance("ls.n32747") == expectedPassages)
  }

  it should "construct a concordance for a corpus with multiple nodes" in {
    val o2corpus = CorpusSource.fromFile("src/test/resources/cex/livy-mt.cex", cexHeader=true)
    val fstLines = Source.fromFile("src/test/resources/fst/livy-mt-parsed.txt").getLines.toVector
    val corpus = LatinCorpus.fromFstLines(o2corpus, Latin24Alphabet, fstLines)
    val lex = "ls.n23674"
    println("Entries for " + LewisShort.label(lex))
    println(corpus.lexemeConcordance(lex).toVector.mkString("\n"))
  }

  it should "implement all filtering of the LatinTokenSequence trait such as verbs" in {
    val verbs = lc.verbs
    val expectedVerbs = 2
    assert(verbs.size == expectedVerbs)
  }

  it should "highlight tokens by grammatical category" in pending

  it should "support cool hover display in HTML environment" in pending

}
