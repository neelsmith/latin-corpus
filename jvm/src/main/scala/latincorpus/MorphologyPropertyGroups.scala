package edu.holycross.shot.latincorpus

import edu.holycross.shot.tabulae._


case class PersonNumber(person: Person, grammaticalNumber: GrammaticalNumber) {
  override def toString: String = s"${person} ${grammaticalNumber}"
}

case class TenseMood(tense: Tense, mood: Mood) {
  override def toString: String = s"${tense} ${mood}"
}
