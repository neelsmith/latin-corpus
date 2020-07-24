
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.latin._


import org.scalatest.FlatSpec

class LatinTokenSequenceSpec extends FlatSpec {


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



  "A LatinTokenSequence" should "highlight tokens matching a part of speech" in pending /*{
    println(lc.clusterByCitation(0).highlightPoS("verb"))
  }*/

  it should "allow configuration of open/close strings in highlighting" in pending /*{
    println(lc.clusterByCitation(0).highlightPoS("verb", "<span style=\"color:green\">", "</span>"))
  }*/


  it should "highlight tokens matching a morphological filter" in {
    val mf = MorphologyFilter(person = Some(First), grammaticalNumber = Some(Singular))
    println("1st sing: " + lc.clusterByCitation(0).highlightForms(mf))
  }

  it should "highlight tokens matching a Vector of Highlighters" in {
    val nhi = Highlighter(MorphologyFilter(pos = Some("noun")))
    val vhi = Highlighter(MorphologyFilter(pos = Some("verb")))
    val hiliters = Vector(nhi, vhi)
    val seq =   lc.clusterByCitation(0)
    val hl = seq.highlightForms(hiliters)


    val expectedHilights = "sed **debebatur**, ut **opinor**, **fatis** tantae **origo** **urbis**"
    assert (hl == expectedHilights)
  }

  it should "support different highlighting configurations" in {
    val blue = "<span style=\"color:blue\">"
    val green = "<span style=\"color:green\">"
    val closer = "</span>"
    val nhi = Highlighter(MorphologyFilter(pos = Some("noun")),blue, closer )
    val vhi = Highlighter(MorphologyFilter(pos = Some("verb")), green, closer)
    val hiliters = Vector(nhi, vhi)
    val seq =   lc.clusterByCitation(0)
    val hl = seq.highlightForms(hiliters)


    val expectedHtmlized = "sed <span style=\"color:green\">debebatur</span>, ut <span style=\"color:green\">opinor</span>, <span style=\"color:blue\">fatis</span> tantae <span style=\"color:blue\">origo</span> <span style=\"color:blue\">urbis</span>"

    assert(hl == expectedHtmlized)
  }


}
