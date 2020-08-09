package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.histoutils._

import java.time.LocalDate
import java.time.format.DateTimeFormatter


import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter

trait LatinParsedTokenSequence extends LogSupport {
  //Logger.setDefaultLogLevel(LogLevel.WARN)

  /** Tokens contained in this sequence.*/
  def tokens: Vector[LatinParsedToken]

  /** Concordance of all lexical tokens in corpus.*/
  def tokenConcordance : Map[String, Vector[CtsUrn]] = {
    tokens.map(t => (t.text, t.urn)).groupBy(_._1).toVector.map{ case(k,v) => (k, v.map(_._2)) }.toMap
  }


  /** Index of tokens to Vector of identifiers for lexeme.*/
  def tokenLexemeIndex : Map[String,Vector[String]] = {
    val analyzedForms = this.analyzed.map(t => (t.text, t.analyses.map(_.lemmaId).distinct))
    analyzedForms.groupBy(_._1).map{ case (s,v) => (s, v.map(_._2).flatten.distinct)}
  }
  /** Index of lexemes to Vector of tokens.*/
  def lexemeTokenIndex  ={ //: Map[String,Vector[String]] = {
    val reversedIndex = tokenLexemeIndex.toVector.map{ case (s,v) => v.map(el => (s,el))}.flatten
    val indexVector = reversedIndex.groupBy(_._1).toVector.map{ case (s,v) => (s, v.map(_._2))}
    indexVector.map{ case (s,v) => s -> v }.toMap
  }


  /** Create a histogram of LemmatizedForms.*/
  def formHistogram : Histogram[LemmatizedForm] = {
    val freqs = this.analyzed.flatMap(_.analyses).groupBy(f => f).map{ case(k,v) => Frequency(k, v.size) }
    Histogram(freqs.toVector).sorted
  }
  /** True if one or more tokens matches any one or more
  * lexeme in a given list.
  *
  * @param lexemes List of lexemes to look for.
  */
  def matchesLexeme(lexemes: Vector[String]): Boolean = {
    LatinParsedTokenSequence.matchesLexeme(tokens, lexemes)
  }

  /** True if one or more tokens matches a given lexeme.
  *
  * @param lexeme Lexeme to test for.
  */
  def matchesLexeme(lexeme: String) : Boolean = {
    LatinParsedTokenSequence.matchesLexeme(tokens, lexeme)
  }

  /** Format time stamps with underscores as separators to
  * faciliate use in URNs.
  */
  val formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd")
  /** Time stamp to use in unique identifiers for automatically
  * generated records.
  */
  val todayFormatted = LocalDate.now.format(formatter)



  /** Represent this corpus as a sequence of CEX lines.
  *
  * @param umgr UrnManager to expand abbreviated identifiers.
  */
  def analysisUrns(umgr: UrnManager): Vector[LemmatizedFormUrns] = tokens.flatMap(t => t.analysisUrns(umgr: UrnManager))
  def cexLines(umgr: UrnManager, separator: String = "#") : Vector[String] = analysisUrns(umgr).map(_.cex(separator))

  def citeCollectionLines(umgr: UrnManager, urnBase: String = "urn:cite2:linglat:tkns.v1:", separator: String = "#") = {
    val citable = for ( (ln, i) <- cexLines(umgr, separator).zipWithIndex) yield {
      val recordId = todayFormatted + "_" + i
      val urnStr = urnBase + recordId
      val label = "Record " + recordId
      urnStr + "#" + label + "#" + ln + "#" + i
    }
    citable
  }

  def cex(umgr: UrnManager, urnBase: String = "urn:cite2:linglat:tkns.v1:", separator: String = "#") : String = {
    val header = "urn#label#passage#token#lexeme#form#category#sequence\n"
    header + citeCollectionLines(umgr, urnBase, separator).mkString("\n")
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
    StringFormatter.highlightPoS(tokens, label, hlOpen, hlClose)
  }

  def formatSingleAnalysis(text: String, analysis: LemmatizedForm, highlighters: Vector[Highlighter]) : String =  {
    StringFormatter.formatSingleAnalysis(tokens, text, analysis, highlighters)
  }


  def highlight(tkn: LatinParsedToken, highlighters: Vector[Highlighter]) : String  = {
    StringFormatter.highlight(tokens, tkn, highlighters)
  }


  def highlightForms(highlighters: Vector[Highlighter]) : String = {
    StringFormatter.highlightForms(tokens, highlighters)
  }


  /*def highlightForms(mf : MorphologyFilter,
    hlOpen : String = "**",
    hlClose : String = "**") : String= {
      */


  /** Attach HTML markup to effect display of form information when
  * mouse is over a token.
  *
  * @param mfs Vector of filters to apply.
  * @param color Color to use in HTML highlighting.
  */
  //def hover(mfs : Vector[MorphologyFilter],
      // color: String = "green") : String = {


  /** All tokens with at least one morphological analysis.*/
  lazy val analyzed: Vector[LatinParsedToken] = tokens.filter(_.analyses.nonEmpty)

  /** All lexical tokens.*/
  lazy val lexicalTokens: Vector[LatinParsedToken] = tokens.filter(t => t.category == LexicalToken)

  /** Total number of tokens.*/
  lazy val size : Int = tokens.size

  /** Tokens with a single morphological analysis.*/
  lazy val singleAnalysis: Vector[LatinParsedToken] = {
    tokens.filter(_.analyses.size == 1)
  }

  /** Tokens with no morphological anlayses.
  */
  lazy val noAnalysis: Vector[LatinParsedToken] = {
    tokens.filter(_.analyses.isEmpty)
  }

  /** Tokens with more than one morphological analysis.*/
  lazy val mutipleAnalyses: Vector[LatinParsedToken] = {
    tokens.filter(_.analyses.size > 1)
  }

  /** Measure of ambigutiy/uniqueness of tokens.*/
  lazy val tokenAmbiguity: Double = {
    allAnalyses.size / analyzed.size.toDouble
  }


  /** List of all morphological analyses.*/
  lazy val allAnalyses: Vector[LemmatizedForm] =  analyzed.flatMap(_.analyses)

  /** Reduce analyzed tokens to citable node + lexeme.*/
  def lexemesOnly:  Vector[(CitableNode, Vector[String])] = analyzed.map(t => (t.cn, t.analyses.map(_.lemmaId).distinct) )

  def multipleLexemes:  Vector[LatinParsedToken] = {
    analyzed.filter( _.analyses.map(_.lemmaId).distinct.size > 1)
  }

  def singleLexeme:  Vector[LatinParsedToken] = {
    analyzed.filter( _.analyses.map(_.lemmaId).distinct.size == 1)
  }


  def lexemesHistogram: Histogram[String] = {
    val freqs : Vector[Frequency[String]] = lexemesOnly.flatMap(_._2).groupBy(s => s).toVector.map{ case (k,v) => Frequency(k, v.size) }
    Histogram(freqs)
  }


  lazy val lexicalAmbiguity: Double = {
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

 /** Collect distinct values for a class of MorphologicalCategoryValues.
 *
 * @param prop Morphological property to collect values for.
 */
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

  val css = "<style>\na.hoverclass {\nposition: relative ;\n}\na.hoverclass:hover::after {\n content: attr(data-tooltip) ;\n position: absolute ;\n  top: 1.1em ;\n  left: 1em ;\n  min-width: 200px ;\n  border: 1px #808080 solid ;\n  padding: 8px ;\n  z-index: 1 ;\n  color: silver;\n  background-color: white;\n}\n</style>\n"



  /** Compute percent as a Double to a given scale.
  *
  * @param n Number.
  * @param total Total of which n is some percent.
  * @param scale Scale for resulting Double.
  */
  def dblPercent(n: Int, total: Int, scale: Int = 2) : Double = {
    val flt = ((n / total.toDouble) * 100).toDouble

    BigDecimal(flt).setScale(scale, BigDecimal.RoundingMode.HALF_UP).toDouble
  }


  /** Compute percent as an Int.
  *
  * @param n Number.
  * @param total Total of which n is some percent.
  */
  def percent(n: Int, total: Int) : Int = {
    ((n / total.toDouble) * 100).toInt
  }
}

object LatinParsedTokenSequence {

  /** True if one or more tokens matches any one or more
  * lexeme in a given list.
  *
  * @param lexemes List of lexemes to look for.
  */
  def matchesLexeme(tokens: Vector[LatinParsedToken], lexeme: String) : Boolean = {
    val tf = for (t <- tokens) yield {
      t.matchesLexeme(lexeme)
    }
    tf.contains(true)
  }

  /** True if one or more tokens matches a given lexeme.
  *
  * @param lexeme Lexeme to test for.
  */
  def matchesLexeme(tokens: Vector[LatinParsedToken], lexemes: Vector[String]) : Boolean = {
    val tf = for (t <- tokens) yield {
      t.matchesAny(lexemes)
    }
    tf.contains(true)
  }


  def matchesForm(tokens: Vector[LatinParsedToken]) : Boolean = {
    false
  }

}
