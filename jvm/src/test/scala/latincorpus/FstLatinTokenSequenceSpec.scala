
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.latin._


import org.scalatest.FlatSpec

class LatinParsedTokenSequenceSpec extends FlatSpec {

  val c196aUrl = "https://raw.githubusercontent.com/neelsmith/latin-corpus/master/jvm/src/test/resources/sect196a.cex"
  val hyginusSelection = LatinCorpus.fromUrl(c196aUrl)
  val c196a = hyginusSelection.clusterByCitation.head

  "A LatinTokenSequence" should "do a lot" in {
    //val tknSeq = 
  }
}
