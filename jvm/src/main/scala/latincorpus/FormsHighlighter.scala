package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.histoutils._


import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter

// association of tagging strings for
// some set of properties
case class FormsHighlighter(
  properties: Vector[ClassifiedValue],
  highlight: String,
  booleanAnd : Boolean = true
) extends Highlighter{

  def addHighlight(token: LatinParsedToken): Boolean = {
    if (booleanAnd) {
      token.andMorphMatches(properties)
    } else {
      token.orMorphMatches(properties)
    }
  }

  def highlightString = highlight


}
