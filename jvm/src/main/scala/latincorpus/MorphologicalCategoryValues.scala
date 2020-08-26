package edu.holycross.shot.latincorpus

import edu.holycross.shot.tabulae._

// Objects we can use to identify concepts defined as traits in tabulae library

case class ClassifiedValue(
  property: MorphologicalCategoryValues,
  propertyValue: MorphologicalProperty
)

  sealed trait MorphologicalCategoryValues {
    def name: String
  }
  case object GrammaticalCaseValues extends MorphologicalCategoryValues {
    def name = "case"
  }
  case object GenderValues extends MorphologicalCategoryValues {
    def name = "gender"
  }
  case object GrammaticalNumberValues extends MorphologicalCategoryValues {
    def name = "number"
  }
  case object DegreeValues extends MorphologicalCategoryValues {
    def name = "degree"
  }
  case object PersonValues extends MorphologicalCategoryValues {
    def name = "person"
  }
  case object TenseValues extends MorphologicalCategoryValues {
    def name = "tense"
  }
  case object MoodValues extends MorphologicalCategoryValues {
    def name = "mood"
  }
  case object VoiceValues extends MorphologicalCategoryValues {
    def name = "voice"
  }
  case object UninflectedValues extends MorphologicalCategoryValues {
    def name = "uninflected"
  }


object MorphologicalValue {
  // P
  val first = ClassifiedValue(PersonValues, First)
  val second = ClassifiedValue(PersonValues, First)
  val third = ClassifiedValue(PersonValues, Third)
  // N
  val singular = ClassifiedValue(GrammaticalNumberValues, Singular)
  val plural = ClassifiedValue(GrammaticalNumberValues, Plural)
  // T
  val present = ClassifiedValue(TenseValues, Present)
  val future = ClassifiedValue(TenseValues, Future)
  val imperfect = ClassifiedValue(TenseValues, Imperfect)
  val perfect = ClassifiedValue(TenseValues, Perfect)
  val pluperfect = ClassifiedValue(TenseValues, Pluperfect)
  val futureperfect = ClassifiedValue(TenseValues, FuturePerfect)
  // M
  val indicative = ClassifiedValue(MoodValues, Indicative)
  val imperative = ClassifiedValue(MoodValues, Imperative)
  val subjunctive = ClassifiedValue(MoodValues, Subjunctive)
  // V
  val active = ClassifiedValue(VoiceValues, Active)
  val passive = ClassifiedValue(VoiceValues, Passive)
  // G
  val masculine = ClassifiedValue(GenderValues, Masculine)
  val feminine = ClassifiedValue(GenderValues, Feminine)
  val neuter = ClassifiedValue(GenderValues, Neuter)
  // C
  val nominative = ClassifiedValue(GrammaticalCaseValues, Nominative)
  val genitive = ClassifiedValue(GrammaticalCaseValues, Genitive)
  val dative = ClassifiedValue(GrammaticalCaseValues, Dative)
  val accusative = ClassifiedValue(GrammaticalCaseValues, Accusative)
  val ablative = ClassifiedValue(GrammaticalCaseValues, Ablative)
  val vocative = ClassifiedValue(GrammaticalCaseValues, Vocative)
  // Degree
  val positive = ClassifiedValue(DegreeValues, Positive)
  val comparative = ClassifiedValue(DegreeValues, Comparative)
  val superlative = ClassifiedValue(DegreeValues, Superlative)
}
