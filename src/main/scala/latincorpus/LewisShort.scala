package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.validator._
import scala.io.Source

object LewisShort {
  val indexUrl = "https://raw.githubusercontent.com/Eumaeus/cex_lewis_and_short/master/ls_indexData.txt"
  lazy val lemmaIndex = Source.fromURL(indexUrl).getLines.toVector
  val lsIdMap = for (ln <- lemmaIndex) yield {
    val parts = ln.split("#")
    s"ls.${parts(0)}" -> parts(1)
  }
  val idMap = lsIdMap.toMap


  def label(s: String) = {
    try {
      s + ":" + idMap(s)
    } catch {
      case t: Throwable => s
    }
  }

}
