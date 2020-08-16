package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._


/**
*
*
*/
case class CitableForm(urn: CtsUrn, form: Option[ValidForm], text: String) {


  def functionString: String = {
    if (form == None) {
      "not analyzed"
    } else {
      form.get match {
        case noun: ValidNounForm => ("noun: " + noun.grammaticalCase).toLowerCase
        case pronoun: ValidPronounForm => ("pronoun: " + pronoun.grammaticalCase).toLowerCase
        case verb: ValidFiniteVerbForm => ("verb: " + verb.mood).toLowerCase
        case participle: ValidParticipleForm => ("participle: " + participle.grammaticalCase).toLowerCase
        case uninflected: ValidUninflectedForm => uninflected.indeclinablePoS.toString.toLowerCase
        case infinitive: ValidInfinitiveForm => "infinitive"
        case gerundive: ValidGerundiveForm => ("gerundive: " + gerundive.grammaticalCase).toLowerCase
        case gerund: ValidGerundForm => ("gerund: " + gerund.grammaticalCase).toLowerCase


        case supine: ValidSupineForm => ""
        case adj: ValidAdjectiveForm => ""
        case adv: ValidAdverbForm => ""

      }
    }
  }
}
