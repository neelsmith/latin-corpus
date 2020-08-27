package edu.holycross.shot.latincorpus

import edu.holycross.shot.tabulae._

import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter

object StringFormatter extends LogSupport {

  val defaultFormAmbiguityStyle: String = "text-decoration-line: underline; text-decoration-style: wavy;"
  val defaultLexicalAmbiguityStyle: String = "border-bottom: solid;  padding: 3px;"
  val defaultUnanalyzedStyle: String = "border-bottom: dotted;"

  /** Format a single token as a string using a [[FormsHighlighter]].
  *
  * @param token Token to format.
  * @param highlighter Highlighting to apply to specified forms.
  * @param formAmbiguityStyle CSS string with style specifications for ambiguous tokens.
  * @param lexicalAmbiguityStyle CSS string with style specifications for lexically ambiguous tokens.
  * @param unanalyzedStyle CSS string with style specifications for unanalyzed tokens.
  */
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

  /** Format a series of tokens as a string using a [[FormsHighlighter]].
  *
  * @param token Token to format.
  * @param highlighter Highlighting to apply to specified forms.
  * @param formAmbiguityStyle CSS string with style specifications for ambiguous tokens.
  * @param lexicalAmbiguityStyle CSS string with style specifications for lexically ambiguous tokens.
  * @param unanalyzedStyle CSS string with style specifications for unanalyzed tokens.
  */
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



  /** Format a single token as a string using a [[LexemesHighlighter]].
  *
  * @param token Token to format.
  * @param highlighter Highlighting to apply to specified forms.
  * @param formAmbiguityStyle CSS string with style specifications for ambiguous tokens.
  * @param lexicalAmbiguityStyle CSS string with style specifications for lexically ambiguous tokens.
  * @param unanalyzedStyle CSS string with style specifications for unanalyzed tokens.
  */
  def tokenLexemeStyled(token: LatinParsedToken,
    highlighter: LexemesHighlighter,
    lexemeIds: Vector[String],
    formAmbiguityStyle: String = defaultFormAmbiguityStyle,
    lexicalAmbiguityStyle: String = defaultLexicalAmbiguityStyle,
    unanalyzedStyle: String = defaultUnanalyzedStyle) : String = {

    val unanalyzed = if (token.unanalyzed) { unanalyzedStyle } else { "" }
    val ambiguity = if (token.lexicallyAmbiguous) {
      lexicalAmbiguityStyle
    } else if (token.ambiguous) {
      formAmbiguityStyle
    } else {
      ""
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

  /** Format a series of tokens as a string using a [[LexemesHighlighter]].
  *
  * @param token Token to format.
  * @param highlighter Highlighting to apply to specified forms.
  * @param formAmbiguityStyle CSS string with style specifications for ambiguous tokens.
  * @param lexicalAmbiguityStyle CSS string with style specifications for lexically ambiguous tokens.
  * @param unanalyzedStyle CSS string with style specifications for unanalyzed tokens.
  */
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
* @param formAmbiguityStyle CSS string with style specifications for ambiguous tokens.
* @param lexicalAmbiguityStyle CSS string with style specifications for lexically ambiguous tokens.
* @param unanalyzedStyle CSS string with style specifications for unanalyzed tokens.
*/

  def summary(token: LatinParsedToken) : String = {
    if (token.unanalyzed) {
      ""
    } else {
      val byLexeme = token.analyses.groupBy(_.lemmaId)
      val html = for (lex <- byLexeme.keySet) yield {
        val short = byLexeme(lex).map(a => s"ANALYSIS: ${a.toString}")
        "LEXEME: " + LewisShort.label(lex) + " " + short.mkString(";  ")
      }
      html.mkString("  || ")
    }
  }

  def hover(token: LatinParsedToken) : String = {
    "<a href=" + "\"" + "#" + "\"" +  " data-tooltip=\"" + summary(token) + "\"" +  " class=\"hoverclass\">" + token.text + "</a>"
  }

  def labelledForm(token: LatinParsedToken,
    formAmbiguityStyle: String = defaultFormAmbiguityStyle,
    lexicalAmbiguityStyle: String = defaultLexicalAmbiguityStyle,
    unanalyzedStyle: String = defaultUnanalyzedStyle
  ): String = {


    if (token.unanalyzed) {
      "<span style =\"" + s"${unanalyzedStyle}" + "\">" + token.text.trim + "</span>"

    } else {
      val ambiguity = if (token.lexicallyAmbiguous) {
        lexicalAmbiguityStyle
      } else if (token.ambiguous) {
        formAmbiguityStyle
      } else {
        ""
      }

      val formatted = if (ambiguity.nonEmpty) {
        "<span style =\"" + s"${ambiguity}" + "\">" + hover(token) + "</span>"

      } else {
        hover(token)
      }
      formatted
    }
  }


def labelledForms(tokens: Vector[LatinParsedToken],
  formAmbiguityStyle: String = defaultFormAmbiguityStyle,
  lexicalAmbiguityStyle: String = defaultLexicalAmbiguityStyle,
  unanalyzedStyle: String = defaultUnanalyzedStyle) : String = {
  val labelled = tokens.map(t => {
    t.category.toString match {
      case "PunctuationToken" => {
        t.text.trim
      }
      case _ => {
        " " + labelledForm(t, formAmbiguityStyle, lexicalAmbiguityStyle, unanalyzedStyle )
      }
    }
  })
  labelled.mkString("").trim
}


    val tooltipCss = """<style>
a.hoverclass {
  position: relative ;
}
a.hoverclass:hover::after {
  content: attr(data-tooltip) ;
  position: absolute ;
  top: 1.1em ;
  left: 1em ;
  min-width: 200px ;
  border: 1px #808080 solid ;
  padding: 8px ;
  z-index: 1 ;
  color: silver;
  background-color: white;
}
</style>
"""

}
