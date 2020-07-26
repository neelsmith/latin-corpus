package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._


case class LatinPhrase(tokens: Vector[LatinParsedToken]) extends LatinParsedTokenSequence {

  /** Create an OHCO2 CitableNode for the tokens in this sequence.
  */
  def canonicalNode :  CitableNode = {
    val urn = tokens(0).cn.urn.collapsePassageBy(1)
    val text = for (t <- tokens) yield {
      t.category.toString match {
        case "PunctuationToken" => {
          t.cn.text.trim
        }
        case _ => " " + t.cn.text
      }
    }
    CitableNode(urn,text.mkString.trim)
  }

  def indicatives = {

  }

  def conjunctions: Boolean = {
    val tokenIndecls = for (t <- tokens) yield {
      val indecls = t.analyses.map (a => a.indeclinablePartOfSpeech).flatten.map(_.toString)
      if (indecls.contains("Conjunction"))  {
        println(t.text + " " + indecls)
        true
      } else {
        false
      }
    }
    tokenIndecls.toVector.contains(true)
  }

}
