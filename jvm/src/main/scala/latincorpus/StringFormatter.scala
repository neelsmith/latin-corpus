package edu.holycross.shot.latincorpus

import edu.holycross.shot.tabulae._

import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter

object StringFormatter extends LogSupport {

  val defaultFormAmbiguityStyle: String = "text-decoration-line: underline; text-decoration-style: wavy;"
  val defaultLexicalAmbiguityStyle: String = "border-bottom: solid;  padding: 3px;"
  val defaultUnanalyzedStyle: String = "border-bottom: dotted;"

  def tokenFormStyled(
    token: LatinParsedToken,
    highlighter: FormsHighlighter,
    formAmbiguityStyle: String = defaultFormAmbiguityStyle,
    lexicalAmbiguityStyle: String = defaultLexicalAmbiguityStyle,
    unanalyzedStyle: String = defaultUnanalyzedStyle
  ): String = {
    val unanalyzed = if (token.unanalyzed) { unanalyzedStyle } else { "" }
    val ambiguity = {
      if (token.lexicallyAmbiguous) {
        lexicalAmbiguityStyle
      } else if (token.ambiguous) {
        formAmbiguityStyle
      } else {
        ""
      }
    }
    val formHighlighting = {
      highlighter.highlightForToken(token)
    }

    if (ambiguity.nonEmpty || formHighlighting.nonEmpty || unanalyzed.nonEmpty) {
      "<span style =\"" + s"${formHighlighting} ${ambiguity} ${unanalyzed}" + "\">" + token.text.trim + "</span>"
    } else {
      token.text.trim
    }
  }

  // Apply highlighting for forms
  def tokensFormStyled(
    tokens: Vector[LatinParsedToken],
    highlighter: FormsHighlighter,
    formAmbiguityStyle: String = defaultFormAmbiguityStyle,
    lexicalAmbiguityStyle: String = defaultLexicalAmbiguityStyle,
    unanalyzedStyle: String = defaultUnanalyzedStyle
  ) : String = {

    val hilited = tokens.map(t => {
      t.category.toString match {
        case "PunctuationToken" => {
          t.text.trim
        }
        case _ => {
          " " + tokenFormStyled(t, highlighter, formAmbiguityStyle,lexicalAmbiguityStyle, unanalyzedStyle)
        }
      }
    }
    )
    hilited.mkString("").trim
  }


  def tokenLexemeStyled(token: LatinParsedToken,
    highlighter: LexemesHighlighter,
    lexemeIds: Vector[String],
    formAmbiguityStyle: String = defaultFormAmbiguityStyle,
    lexicalAmbiguityStyle: String = defaultLexicalAmbiguityStyle,
    unanalyzedStyle: String = defaultUnanalyzedStyle) : String = {
    val unanalyzed = if (token.unanalyzed) { unanalyzedStyle } else { "" }
    val ambiguity = {
      if (token.lexicallyAmbiguous) {
        lexicalAmbiguityStyle
      } else if (token.ambiguous) {
        formAmbiguityStyle
      } else {
        ""
      }
    }
    val lexemeHighlighting = {
      highlighter.highlightForToken(token)
    }
    if (ambiguity.nonEmpty || lexemeHighlighting.nonEmpty || unanalyzed.nonEmpty) {
      "<span style =\"" + s"${lexemeHighlighting} ${ambiguity} ${unanalyzed}" + "\">" + token.text.trim + "</span>"
    } else {
      token.text.trim
    }
  }

  def tokensLexemeStyled(tokens: Vector[LatinParsedToken],
    highlighter: LexemesHighlighter,
    lexemesIds: Vector[String],
    formAmbiguityStyle: String = defaultFormAmbiguityStyle,
    lexicalAmbiguityStyle: String = defaultLexicalAmbiguityStyle,
    unanalyzedStyle: String = defaultUnanalyzedStyle) : String = {
    val hilited = tokens.map(t => {
      t.category.toString match {
        case "PunctuationToken" => {
          t.text.trim
        }
        case _ => {
          " " + tokenLexemeStyled(t, highlighter,
            lexemesIds, formAmbiguityStyle,lexicalAmbiguityStyle, unanalyzedStyle)
        }
      }
    })
    hilited.mkString("").trim
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
