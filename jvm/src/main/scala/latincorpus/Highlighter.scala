package edu.holycross.shot.latincorpus

import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter

// association of tagging strings for
// some set of properties
trait Highlighter extends LogSupport {
  def addHighlight(token: LatinParsedToken): Boolean
  def highlightString: String

  def highlightForToken(token: LatinParsedToken):  String = {
    if (addHighlight(token)) {
      highlightString
    } else {
      ""
    }
  }
}
