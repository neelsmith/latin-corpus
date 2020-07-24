
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.latin._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.histoutils._
import org.scalatest.FlatSpec
import scala.io._

class CorpusProfilingSpec extends FlatSpec {

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

  "A LatinCorpus" should "create a histogram of tokens" in pending /* {
    val expectedFrequency = 1
    assert(lc.lexTokenHistogram.countForItem("opinor") == expectedFrequency)
  }*/
  it should "index from tokens to lexemes" in {
    val expectedNumberLexemes = 1
    val expectedLexVector = Vector("ls.n32747")
    assert(lc.tokenLexemeIndex("opinor").size == expectedNumberLexemes)
    assert(lc.tokenLexemeIndex("opinor") == expectedLexVector)
  }
  it should "index from lexemes to tokens" in {
    val expectedVector = Vector("ls.n43291")
    assert(lc.lexemeTokenIndex("sed") == expectedVector)
  }

  it  should "create a histogram of lexemes" in pending /*{
    assert(lc.lexemeHistogram.countForItem("ls.n43291") == 1 )
  } */
  it  should "create a histogram of LS-labelled lexemes" in pending /*{
    assert(lc.labelledLexemeHistogram.countForItem("ls.n43291:sed1") == 1)
  }*/

  it should "create concordance of tokens" in {
    val expectedPassages = Vector(CtsUrn("urn:cts:omar:stoa0179.stoa001.omar_tkns:1.4.1"))
    assert(lc.tokenConcordance("sed") == expectedPassages)
  }
  it should "create concordance of lexemes" in {
    assert(lc.passagesForLexeme("sed") == Vector.empty[CtsUrn])

    val expectedPassages = Vector(CtsUrn("urn:cts:omar:stoa0179.stoa001.omar_tkns:1.4.1"))
    assert(lc.passagesForLexeme("ls.n43291") == expectedPassages)
  }
  it should "create a concordance of lexemes" in {
    val neutablpl = NounForm("ls.n17799", "latcommon.n17799","livymorph.us_i22", Neuter, Ablative, Plural)
    val expectedPassages = Vector(CtsUrn("urn:cts:omar:stoa0179.stoa001.omar_tkns:1.4.1.4"))
    assert(lc.formConcordance(neutablpl) == expectedPassages)
  }
  it should "create a histogram of lemmatized forms" in pending /* {
    val neutablpl = NounForm("ls.n17799", "latcommon.n17799","livymorph.us_i22", Neuter, Ablative, Plural)
    //println(lc.formHistogram.frequencies.mkString("\n"))
    assert(lc.formHistogram.countForItem(neutablpl) == 1)
  } */
  it should "create a histogram of lexemes labelled with PoS" in pending

  it should "create a histogram of tokens labelled with PoS" in pending

  it should "measure percent coverage of tokens" in pending
  it should "measure percent coverage of token occurrences" in pending

  it should "create a histogram of lexically ambiguous tokens" in pending /*{
    val o2corpus = CorpusSource.fromFile("jvm/jvm/src/test/resources/cex/livy-mt.cex", cexHeader=true)
    val fstLines = Source.fromFile("jvm/src/test/resources/fst/livy-mt-parsed.txt").getLines.toVector
    val corpus = LatinCorpus.fromFstLines(o2corpus, Latin24Alphabet, fstLines)
    val mostFrequent = corpus.multipleLexemesHistogram.sorted.frequencies(0)

    val expectedRecord =  Frequency("qui (0.48%): ls.n40242:quis1, ls.n40103:qui1",87)
    assert (mostFrequent == expectedRecord)
  } */


}
