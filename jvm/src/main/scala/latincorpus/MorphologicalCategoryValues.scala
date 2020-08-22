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
