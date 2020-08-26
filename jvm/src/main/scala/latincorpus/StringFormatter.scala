package edu.holycross.shot.latincorpus

import edu.holycross.shot.tabulae._

import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter

object StringFormatter extends LogSupport {

  val defaultFormAmbiguityStyle: String = "text-decoration-line: underline; text-decoration-style: wavy;"
  val defaultLexicalAmbiguityStyle: String = "border-left: solid;  padding: 3px;"
  val defaultUnanalyzedStyle: String = ""

  def tokenFormStyled(
    token: LatinParsedToken,
    highlighter: FormsHighlighter,
    formAmbiguityStyle: String = defaultFormAmbiguityStyle,
    lexicalAmbiguityStyle: String = defaultLexicalAmbiguityStyle
  ): String = {




    if (highlighter.addHighlight(token)) {
      "<span style =\"" + s"${highlighter.highlightString} ${formAmbiguityStyle} ${lexicalAmbiguityStyle}" + "\">" + token.text.trim + "</span>"
    } else {
      token.text.trim
    }

  }

  // Apply highlighting for forms
  def tokensFormStyled(
    tokens: Vector[LatinParsedToken],
    highlighter: FormsHighlighter,
    formAmbiguityStyle: String = defaultFormAmbiguityStyle,
    lexicalAmbiguityStyle: String = defaultLexicalAmbiguityStyle
  ) : String = {

    val hilited = tokens.map(t => {
      t.category.toString match {
        case "PunctuationToken" => {
          t.text.trim
        }
        case _ => {
          " " + tokenFormStyled(t, highlighter, formAmbiguityStyle,lexicalAmbiguityStyle)
        }
      }
    })
    hilited.mkString("").trim
  }

  def formHighlightedByClass(

  ) : String = {
    ""
  }
    /** Compose a String highlighting a specified part of speech.
    *
    * @param label Labelling String identifying a part of speech
    * (more properly, an inflectional type).
    * @param hlOpen Opening (left) String marking highlighted token.
    * @param hlClose Closing (right) String marking highlighted
    * token.

    def highlightPoS(tokens: Vector[LatinParsedToken], label: String, hlOpen : String = "**", hlClose : String = "**") : String = {
      ""

      val hilited = tokens.map(t => {
        if (t.analyses.map(_.posLabel).contains(label)) {
          s"${hlOpen}${t.text}${hlClose}"
        } else {
          t.text
        }
      })
      hilited.mkString(" ")

    }  */


/*
    def highlightForms(
      tokens: Vector[LatinParsedToken],
      highlighters: Vector[Highlighter]) : String = {

      val hilited = tokens.map(t => {
        if (t.analyses.map(_.posLabel).contains(label)) {
          s"${hlOpen}${t.text}${hlClose}"
        } else {
          t.text
        }
      })
      hilited.mkString(" ")
    }
*/
/*
    def formatSingleAnalysis(
      tokens: Vector[LatinParsedToken],
      text: String,
      analysis: LemmatizedForm,
      highlighters: Vector[FormsHighlighter]) : String =  {

        ""
        /*
      val formatted = for  (hl <- highlighters) yield {
        if (hl.mf.agrees(analysis)) {
          (true, "<TAG>" + text + "</TAG>")
          //(true, hl.opening + text + hl.closing)
        } else {
        (false, text)
        }
      }
      val flattened = formatted.filter(_._1 == true).map(_._2)

      if (flattened.isEmpty) {
        ""
      } else {
        flattened(0)
      }

    }*/

/*
    def highlight(tokens: Vector[LatinParsedToken], tkn: LatinParsedToken, highlighters: Vector[FormsHighlighter]) : String  = {


      val formatted =  for (a <- tkn.analyses) yield {
        formatSingleAnalysis(tokens, tkn.text, a, highlighters)
      }
      if (formatted.flatten.isEmpty) {
        tkn.text
      } else {
        formatted(0)
      }
    }

*/

      /*

    def highlightForms(tokens: Vector[LatinParsedToken], highlighters: Vector[FormsHighlighter]) : String = {
      ""

      //Logger.setDefaultLogLevel(LogLevel.DEBUG)
      val hilited = tokens.map(t => {
        t.category.toString match {
          case "PunctuationToken" => {
            t.text.trim
          }
          case _ => {
            " " + highlight(tokens, t, highlighters)
          }
        }
      })
      hilited.mkString("").trim */
    }
*/


    /*
    * Attach HTML markup to effect display of form information when
    * mouse is over a token.
    *
    * @param mfs Vector of filters to apply.
    * @param color Color to use in HTML highlighting.

    def hover(tokens: Vector[LatinParsedToken], mfs : Vector[MorphologyCollectionsFilter],
      color: String = "green") : String = {

      val closer = "</a>"
      val hilited = tokens.map(t => {

        val label = t.analyses.map(_.formLabel).mkString(", or ")
        val opener = s"<a href=" + "\"" + "#" + "\"" +  " data-tooltip=\"" + label + "\"" +  " class=\"hoverclass\">"
        val hls = mfs.map ( mfilt => FormsHighlighter(mfilt, opener, closer))

        val highlighted = this.highlight(t, hls)
        if (highlighted == t.text) {
          t.text
        } else {
          "<span color=\"" + color + "\">" + highlighted + "</span>"
        }

      })
      hilited.mkString(" ") + "\n\n\n" + css

    }    */

}
