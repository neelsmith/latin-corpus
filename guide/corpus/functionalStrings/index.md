---
title: Functional string labels
layout: page
parent: Using a LatinCorpus
---


# Functional string labels

- [What are they](./background/)?



## Filter token sequences for a single feature

Let's create a sequence of `LatinSentence`s from one chapter of Hyginus.


NOPE NOT FOR 7.0
```
import edu.holycross.shot.latincorpus._
val chapterUrl = "https://raw.githubusercontent.com/neelsmith/latin-corpus/master/jvm/src/test/resources/c108a.cex"
val chapter = LatinCorpus.fromUrl(chapterUrl, cexHeader = false)
val sentences = LatinSentence.sentences(chapter.tokens)
```

Select all sentences with a noun in the nominative case:

```
val nominative = Vector("noun: nominative")
val nominativeMatches = sentences.filter(s => s.matchesFunctionStrings(nominative))
```
```
println("Total sentences:  " + sentences.size)
println("With nominative noun: " + nominativeMatches.size)
```

Another way to do the same thing:

```
val matches2 = LatinParsedTokenSequence.filterFunctionStrings(sentences, nominative)
```
```
println("Method 2 matches: " + matches2.size)
```

## Filter token sequences for a sequence of features

This selects only token sequences where the tokens match all the given features, and the features appear in the token sequence in the given order.  Example: select sentences where a dative noun is followed (at a distance of 0 or more tokens) by an indicative verb.

```silent
val dativeIndicative = Vector("noun: dative", "verb: indicative")
val dativeIndicativeMatches = sentences.filter(s => s.matchesFunctionStrings(nominative))
// or alternatively
val dativeIndicativeMatches2 = LatinParsedTokenSequence.filterFunctionStrings(sentences, dativeIndicative)
```
```
println(dativeIndicativeMatches.size)
println(dativeIndicativeMatches2.size)
```


## Filter token sequences for multiple sequences of features

Select sentences with a sequence "preposition followed by accusative", which also include a dative somewhere in the sequence.


```
val conditions = Vector(
  Vector("preposition", "noun: accusative"),
  Vector("noun: dative")
)
val matches = sentences.filter(s => s.matchesFunctionStringLists(conditions))
```
```
println("Number of matches: " + matches.size)
```
