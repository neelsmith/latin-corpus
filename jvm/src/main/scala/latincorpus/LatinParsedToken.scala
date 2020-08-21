package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._


import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter


case class LatinParsedToken(
  cn: CitableNode,
  category: MidTokenCategory,
  analyses: Vector[LemmatizedForm] = Vector.empty[LemmatizedForm]
) {

  // Handy shorthands:
  /** CTS URN for this token's CitableNode.*/
  def urn = cn.urn
  /** Text of this token's CitableNode.*/
  def text = cn.text


  /** True if one or more analyses of this token parses
  * to any item in a given list of lexemes.
  *
  * @param lexemes List of abbreviated identifiers for the lexemes
  * to look for.d
  */
  def matchesAny(lexemes: Vector[String]) : Boolean = {
    val tf = for (lex <- lexemes) yield {
      matchesLexeme(lex)
    }
    tf.contains(true)
  }

  /** True if one or more analyses of this token parses
  * to a given lexeme.
  *
  * @param lexeme Abbreviated identifier for the lexeme
  * to look for.
  */
  def matchesLexeme(lexeme: String) :  Boolean = {
    analyses.filter(_.lemmaId == lexeme).nonEmpty
  }



  /** True if any of analyses are a finite verb form. */
  def finiteVerb: Boolean = {
    analyses.map(_.posLabel).contains("verb")
  }
  def infinitive: Boolean = {
    analyses.map(_.posLabel).contains("infinitive")
  }
  def participle: Boolean = {
    analyses.map(_.posLabel).contains("participle")
  }
  def gerund: Boolean = {
    analyses.map(_.posLabel).contains("gerund")
  }
  def gerundive: Boolean = {
    analyses.map(_.posLabel).contains("gerundive")
  }
  def supine: Boolean = {
    analyses.map(_.posLabel).contains("supine")
  }
  def verbal: Boolean = {
    finiteVerb || infinitive || participle || gerund || gerundive || supine
  }


  def noun: Boolean = {
    analyses.map(_.posLabel).contains("noun")
  }
  def pronoun: Boolean = {
    analyses.map(_.posLabel).contains("pronoun")
  }
  def adjective: Boolean = {
    analyses.map(_.posLabel).contains("adjective")
  }
  def substantive: Boolean = {
    noun || pronoun || adjective
  }

  def adverb: Boolean  = {
    analyses.map(_.posLabel).contains("adverb")
  }

  def uninflected: Boolean = {
    analyses.map(_.posLabel).contains("indeclinable")
  }

  def preposition: Boolean = {
    analyses.map(_.indeclinablePartOfSpeech).contains(Some(Preposition))
  }
  def conjunction: Boolean = {
    analyses.map(_.indeclinablePartOfSpeech).contains(Some(Conjunction))
  }


  /** True if the possible analyses of this token include
  * a specific morphological form identified by URN.
  *
  * @param formUrn URN identifying the form to look for.
  * @param umgr UrnManager for expanding abbreviated identifiers.
  */
  def hasForm(formUrn: Cite2Urn, umgr: UrnManager = LatinParsedTokenSequence.defaultUmgr): Boolean = {
    val formUrns = analysisUrns(umgr).filter(a => a.form == formUrn)
    formUrns.nonEmpty
  }

  // ///////////////////////////////////////////////
  //
  // Support special combos not defined in tabulae's
  // LemmatizedForm class

  def tenseValue(prop: MorphologicalProperty) : Tense = {
    prop match {
      case t: Tense =>  t
      case _ =>  throw new Exception(s"${prop}  is not a Tense")
    }
  }
  def moodValue(prop: MorphologicalProperty) : Mood = {
    prop match {
      case m: Mood => m
      case _ => throw new Exception(s"${prop} is not a Mood")
    }
  }
  def tenseMood: Vector[TenseMood] = {
    val options = analyses.map(a => Vector(a.verbTense,a.verbMood) )
    val noNulls = options.filterNot(v => v(0) == None || v(1) == None )
    noNulls.map(v => TenseMood(
        tenseValue(v(0).get),
        moodValue(v(1).get)
      )
    )
  }
  // ///////////////////////////////////////////////

  def valuesForCategory(prop: MorphologicalCategoryValues): Vector[MorphologicalProperty] = {
    val matches = prop.name match {

      case "case" => {
        // Collect all values for GrammaticalCase in all substantives:
        val caseValues: Vector[GrammaticalCase] = analyses.flatMap(a => a.substantiveCase).distinct
        caseValues
      }

      case "gender" => {
        val genderValues: Vector[Gender] = analyses.flatMap(a => a.substantiveGender).distinct
        genderValues
      }

      case "person" => {
        analyses.flatMap(a => a.verbPerson).distinct
      }

      case "tense" => {
        val verbTenses = analyses.flatMap( a => a.verbTense)
        val ptcplTenses = analyses.flatMap( a => a.participleTense)
        val infinTenses = analyses.flatMap(a => a.infinitiveTense)

        (verbTenses ++ ptcplTenses ++ infinTenses).distinct

        }

        case "mood" => {
          analyses.flatMap(a => a.verbMood)
        }
        case "voice" => {
          val verbVoices = analyses.flatMap( a => a.verbVoice)
          val ptcplVoices = analyses.flatMap( a => a.participleVoice)
          val infinVoices = analyses.flatMap(a => a.infinitiveVoice)

          (verbVoices ++ ptcplVoices ++ infinVoices).distinct
        }
      }
      matches
   }


  def morphologyMatches(property: MorphologicalCategoryValues, propertyValue:  MorphologicalProperty) : Boolean = {
    valuesForCategory(property).contains(propertyValue)
  }


  /** Map analyses to [[LemmatizedFormUrns]] with assitance of a
  * [[UrnManager]] to expand abbreviations fto full URNs.
  *
  * @param umgr UrnManger to use for expanding abbreviated IDs.
  */
  def analysisUrns(umgr: UrnManager) : Vector[LemmatizedFormUrns] = {
    val lexNull = Cite2Urn("urn:cite2:tabulae:ls.v1:null")
    val morphNull =  Cite2Urn("urn:cite2:tabulae:morphforms.v1:null")
    if (analyses.isEmpty) {
      val v = Vector(LemmatizedFormUrns(urn,text,lexNull, morphNull, category))
      v
    } else {
      val lines = for (a <- analyses) yield {
        umgr.urn(a.lemmaId) match {
          case None => LemmatizedFormUrns(urn,text,lexNull, a.formUrn, category)
          case Some(u) => LemmatizedFormUrns(urn,text,u,a.formUrn, category)
        }
      }
      lines
    }
  }
}

object LatinParsedToken extends LogSupport {

  /** Create a [[LatinParsedToken]] from a series of CEX
  * lines in the parsed token data model.  The Vector should
  * include one line for each analysis of the given token.
  *
  * @param cex CEX lines, one per analysis, of the form:
  * urn#label#passage#token#lexeme#form#category#sequence
  * where each line is an analysis of the same token.
  */
  def apply(cex: Vector[String]) : LatinParsedToken = {
    fromCexLines(cex)
  }

  /** Create abbreviated identifier following tabulae
  * convention of crossing collection and object identifiers
  * of a Cite2Urn.
  *
  * @param urn Cite2Urn to represent with an abbreviated string.
  */
  def abbreviatedUrn(urn: Cite2Urn): String = {
    urn.collection + "." + urn.objectComponent
  }



  /** From a group of lines representing all analyses of a given
  * token, construct a [[LatinParsedToken]] with one analysis from
  * each CEX line.
  *
  * @param cex CEX records following parsed token data model.
  * @param separator String separating columns in CEX input.
  */
  def fromCexLines(cex: Vector[String], separator: String = "#") : LatinParsedToken = {
    val rowsByColumns = cex.map(ln => ln.split(separator))
    val first = rowsByColumns.head
    val urn = CtsUrn(first(2))//.collapsePassageBy(1)
    val text = first(3)
    val citableNode = CitableNode(urn, text)
    val tokenType: MidTokenCategory =  tokenCategory(first(6)).get

    val lemmatizedFormOpts = for (row <- rowsByColumns) yield {
      val lexeme = Cite2Urn(row(4))
      val lemmaId = abbreviatedUrn(lexeme)
      val form = Cite2Urn(row(5))
      LemmatizedForm(lemmaId, "", "", form )
    }
    val lemmatizedForms = lemmatizedFormOpts.flatten
    val token = LatinParsedToken(citableNode,tokenType,lemmatizedForms )
    debug("Made token from CEX lines: " + token)
    token
  }


  // defined in MID and latin libraries.
  // If you need other, custom types they won't be recognized.
  //
  val recognizedTokenTypes = Vector(
    LexicalToken,
    NumericToken,
    PunctuationToken,
    PraenomenToken
  )

  def tokenCategory(s: String): Option[MidTokenCategory] = {
    val candidates = recognizedTokenTypes.filter(_.toString == s)
    if (candidates.size == 1) {
      Some(candidates.head)
    } else {
      warn("LatinCorpus: could not find token type for string " + s)
      None
    }
  }

}
