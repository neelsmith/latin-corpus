package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.histoutils._


import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter

trait LatinParsedTokenSequence extends LogSupport {
  //Logger.setDefaultLogLevel(LogLevel.WARN)

  /** Tokens contained in this sequence.*/
  def tokens: Vector[LatinParsedToken]

  /** True if one or more tokens matches any one or more
  * lexeme in a given list.
  *
  * @param lexemes List of lexemes to look for.
  */
  def matchesLexeme(lexemes: Vector[String]): Boolean = {
    val tf = for (t <- tokens) yield {
      t.matchesAny(lexemes)
    }
    tf.contains(true)
  }

  /** True if one or more tokens matches a given lexeme.
  *
  * @param lexeme Lexeme to test for.
  */
  def matchesLexeme(lexeme: String) : Boolean = {
    val tf = for (t <- tokens) yield {
      t.matchesLexeme(lexeme)
    }
    tf.contains(true)
  }

  /** Compose a String highlighting a specified part of speech.
  *
  * @param label Labelling String identifying a part of speech
  * (more properly, an inflectional type).
  * @param hlOpen Opening (left) String marking highlighted token.
  * @param hlClose Closing (right) String marking highlighted
  * token.
  */
  def highlightPoS(label: String, hlOpen : String = "**", hlClose : String = "**") : String = {
    val hilited = tokens.map(t => {
      if (t.analyses.map(_.posLabel).contains(label)) {
        s"${hlOpen}${t.text}${hlClose}"
      } else {
        t.text
      }
    })
    hilited.mkString(" ")
  }

  def formatSingleAnalysis(text: String, analysis: LemmatizedForm, highlighters: Vector[Highlighter])  =  {
    val formatted = for  (hl <- highlighters) yield {
      if (hl.mf.agrees(analysis)) {
        (true, hl.opening + text + hl.closing)
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
  }


  def highlight(tkn: LatinParsedToken, highlighters: Vector[Highlighter]) : String  = {
    val formatted =  for (a <- tkn.analyses) yield {
      formatSingleAnalysis(tkn.text, a, highlighters)
    }
    if (formatted.flatten.isEmpty) {
      tkn.text
    } else {
      formatted(0)
    }
  }


  def highlightForms(highlighters: Vector[Highlighter]) = {
    //Logger.setDefaultLogLevel(LogLevel.DEBUG)
    val hilited = tokens.map(t => {
      t.category.toString match {
        case "PunctuationToken" => {
          t.text.trim
        }
        case _ => {
          " " + highlight(t, highlighters)
        }
      }
    })
    hilited.mkString("").trim
  }


  def highlightForms(mf : MorphologyFilter,
    hlOpen : String = "**",
    hlClose : String = "**") = {
    //Logger.setDefaultLogLevel(LogLevel.WARN)

    val hilited = tokens.map(t => {
      t.category.toString match {
        case "PunctuationToken" => {
          t.text.trim
        }
        case _ => {
          val formsMatch = t.analyses.map(mf.agrees(_))

          if (formsMatch.contains(true)) {
            s" ${hlOpen}${t.text}${hlClose}"
          } else {
            " " + t.text
          }
        }
      }
    })


    debug("Highlighted toekns: " + hilited.mkString("").trim)
    hilited.mkString("").trim
  }


  /** Attach HTML markup to effect display of form information when
  * mouse is over a token.
  *
  * @param mfs Vector of filters to apply.
  * @param color Color to use in HTML highlighting.
  */
  def hover(mfs : Vector[MorphologyFilter],
    color: String = "green") : String = {

    val closer = "</a>"
    val hilited = tokens.map(t => {

      val label = t.analyses.map(_.formLabel).mkString(", or ")
      val opener = s"<a href=" + "\"" + "#" + "\"" +  " data-tooltip=\"" + label + "\"" +  " class=\"hoverclass\">"
      val hls = mfs.map ( mfilt => Highlighter(mfilt, opener, closer))

      val highlighted = this.highlight(t, hls)
      if (highlighted == t.text) {
        t.text
      } else {
        "<span color=\"" + color + "\">" + highlighted + "</span>"
      }

    })
    hilited.mkString(" ") + "\n\n\n" + css

  }




  /** All tokens with at least one morphological analysis.*/
  lazy val analyzed = tokens.filter(_.analyses.nonEmpty)

  /** All lexical tokens.*/
  lazy val lexicalTokens = tokens.filter(t => t.category == LexicalToken)

  /** Total number of tokens.*/
  lazy val size : Int = tokens.size

  /** Tokens with a single morphological analysis.*/
  lazy val singleAnalysis = {
    tokens.filter(_.analyses.size == 1)
  }

  /** Tokens with no morphological anlayses.
  */
  lazy val noAnalysis = {
    tokens.filter(_.analyses.isEmpty)
  }

  /** Tokens with more than one morphological analysis.*/
  lazy val mutipleAnalyses = {
    tokens.filter(_.analyses.size > 1)
  }

  /** Lexical tokens without a morphological anlaysis. */
  lazy val missingAnalysis = {
    lexicalTokens.filter(_.analyses.isEmpty)
  }

  /** Measure of ambigutiy/uniqueness of tokens.*/
  lazy val tokenAmbiguity = {
    allAnalyses.size / analyzed.size.toDouble
  }


  /** List of all morphological analyses.*/
  lazy val allAnalyses =  analyzed.flatMap(_.analyses)

  /** Reduce analyzed tokens to citable node + lexeme.*/
  def lexemesOnly = analyzed.map(t => (t.cn, t.analyses.map(_.lemmaId).distinct) )

  def multipleLexemes = {
    analyzed.filter( _.analyses.map(_.lemmaId).distinct.size > 1)
  }

  def singleLexeme = {
    analyzed.filter( _.analyses.map(_.lemmaId).distinct.size == 1)
  }


  def lexemeToFormsHistogram: Histogram[String] = {
    val freqs : Vector[Frequency[String]] = lexemesOnly.flatMap(_._2).groupBy(s => s).toVector.map{ case (k,v) => Frequency(k, v.size) }
    Histogram(freqs)
  }


  lazy val lexicalAmbiguity = {
    analyzed.size / singleLexeme.size.toDouble
  }

  /** Compute frequency of individual forms.*/
  def formsHistogram  : Histogram[String] = {
    val freqs : Vector[Frequency[String]] = allAnalyses.map(_.toString).groupBy(s => s).toVector.map{ case (k,v) => Frequency(k, v.size) }
    Histogram(freqs)
  }


  /** Verb tokens only*/
  lazy val verbs = {
    analyzed.filter(_.analyses(0).posLabel == "verb")
  }


 def tenseMoodValues : Vector[TenseMood] = {
  //verbs.flatMap(_.analyses.flatMap(a => (a.verbTense, a.verbMood) )).distinct
  Vector.empty[(TenseMood)]
 }

 def valuesForCategory(prop: MorphologicalCategoryValues): Vector[MorphologicalProperty] = {

    val matches = prop.name match {

      case "case" => {
        // Collect all values for GrammaticalCase in all substantives:
        val nounCases: Vector[GrammaticalCase] = nouns.flatMap(_.analyses.flatMap(a => a.substantiveCase)).distinct
        val pronounCases = pronouns.flatMap(_.analyses.flatMap(a => a.substantiveCase)).distinct
        val adjectiveCases = adjectives.flatMap(_.analyses.flatMap(a => a.substantiveCase)).distinct

        // Add gerund and supine?
        (nounCases ++ pronounCases ++ adjectiveCases)
      }
      case "gender" => {
        val nounGenders: Vector[Gender] = nouns.flatMap(_.analyses.flatMap(a => a.substantiveGender)).distinct
        val pronounGenders = pronouns.flatMap(_.analyses.flatMap(a => a.substantiveGender)).distinct
        val adjectiveGenders = adjectives.flatMap(_.analyses.flatMap(a => a.substantiveGender)).distinct
        (nounGenders ++ pronounGenders ++ adjectiveGenders)
      }

      case "person" => {
        verbs.flatMap(_.analyses.flatMap(a => a.verbPerson))
      }

      case "tense" => {
        val verbTenses = verbs.flatMap(v => v.analyses.flatMap( a => a.verbTense))
        val ptcplTenses = participles.flatMap(p => p.analyses.flatMap( a => a.participleTense))
        val infinTenses = infinitives.flatMap(i => i.analyses.flatMap(a => a.infinitiveTense))

        (verbTenses ++ ptcplTenses ++ infinTenses).distinct
      }

      case "mood" => verbs.flatMap(_.analyses.flatMap(a => a.verbMood))

      case "voice" => {
        val verbVoices = verbs.flatMap(v => v.analyses.flatMap( a => a.verbVoice))
        val ptcplVoices = participles.flatMap(p => p.analyses.flatMap( a => a.participleVoice))
        val infinVoices = infinitives.flatMap(i => i.analyses.flatMap(a => a.infinitiveVoice))

        (verbVoices ++ ptcplVoices ++ infinVoices).distinct
      }
    }
    matches
  }

  /** Noun tokens only*/
  lazy val nouns = {
    analyzed.filter(_.analyses(0).posLabel == "noun")
  }

  /** Adjective tokens only*/
  lazy val adjectives = {
    analyzed.filter(_.analyses(0).posLabel == "adjective")
  }

  /** Adverb tokens only*/
  lazy val adverbs = {
    analyzed.filter(_.analyses(0).posLabel == "adverb")
  }

  /** Adverb tokens only*/
  lazy val pronouns = {
    analyzed.filter(_.analyses(0).posLabel == "pronoun")
  }

  /** Uninflected lexical tokens only*/
  lazy val indeclinables = {
    analyzed.filter(_.analyses(0).posLabel == "indeclinable")
  }

  /** Participle tokens only*/
  lazy val participles = {
    analyzed.filter(_.analyses(0).posLabel == "participle")
  }

  /** Infinitive tokens only*/
  lazy val infinitives = {
    analyzed.filter(_.analyses(0).posLabel == "infintive")
  }

  /** Gerundive tokens only*/
  lazy val gerundives = {
    analyzed.filter(_.analyses(0).posLabel == "gerundive")
  }

  /** Gerund tokens only*/
  lazy val gerunds = {
  analyzed.filter(_.analyses(0).posLabel == "gerund")
  }

  /** Supine tokens only*/
  lazy val supines = {
    analyzed.filter(_.analyses(0).posLabel == "supine")
  }



  val css = """
<style>
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