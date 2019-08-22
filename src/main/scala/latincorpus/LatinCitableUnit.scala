package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.validator._


case class LatinCitableUnit(tokens: Vector[LatinToken]) extends LatinTokenSequence {

  def canonicalNode :  CitableNode = {
    val urn = tokens(0).cn.urn.collapsePassageBy(1)
    val text = for (t <- tokens) yield {
      t.category match {
        case PunctuationToken => t.cn.text
        case _ => " " + t.cn.text
      }
    }
    CitableNode(urn,text.mkString)
  }

}
