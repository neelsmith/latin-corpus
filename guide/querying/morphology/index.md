---
title: Matching and filtering morphological properties
layout: page
nav_order: 2
parent: Matching and filtering data sets
---


# Matching and filtering morphological properties


```scala mdoc:invisible
import edu.holycross.shot.latincorpus._
val hyginusUrl = "https://raw.githubusercontent.com/LinguaLatina/analysis/master/data/hyginus/hyginus-latc.cex"
val hyginus = LatinCorpus.fromUrl(hyginusUrl)
```

We've loaded a `LatinCorpus` of the *Fabulae* of Hyginus. (See [an example of how to do that](../../datamodels/parsedTokenSequence/).)



## Filtering tokens for a morphological value

For morphological filtering and querying, we need the `tabulae` library.

```scala mdoc:silent
import edu.holycross.shot.tabulae._
```

We define one or more `ClassifiedValue`s, a pairing of a specific value belonging to a specific class.  The `tabulae` library's `Imperfect` object belongs to the `TenseValues` class, for example.

```scala mdoc:silent
val imperfect = ClassifiedValue(TenseValues, Imperfect)
```

We can filter tokens for a classified value using the `morphologyMatches` method.

```scala mdoc:silent
val imperfectTokens = hyginus.tokens.filter(_.morphologyMatches(imperfect))
```

Lots of imperfects in Hyginus! 667 of them.

```scala mdoc
imperfectTokens.size
```

## Filtering for a list of morphological values

We can also work with Vectors of classified values.  We can separate out imperfect subjunctives from imperfect indicatives, for example, using a Vector listing both a mood value and a tense value.

```scala mdoc:silent
val indicative = ClassifiedValue(MoodValues, Indicative)
val subjunctive = ClassifiedValue(MoodValues, Subjunctive)
val imperfectIndicative = Vector(imperfect, indicative)
val imperfectSubjunctive = Vector(imperfect, subjunctive)
```


The `andMorphMatches` method applies logical `and` to require that the token matches *all* the listed properties.


```scala mdoc:silent
val imperfectIndicativeTokens =  hyginus.tokens.filter(t => t.andMorphMatches(imperfectIndicative))
val imperfectSubjunctiveTokens = hyginus.tokens.filter(t => t.andMorphMatches(imperfectSubjunctive))
```

So our result is 135 indicatives versus 532 subjunctives.  (Teach the imperfect subjunctive early and often.)

```scala mdoc
imperfectIndicativeTokens.size
imperfectSubjunctiveTokens.size
```




## Chaining filters with `and`ing and `or`ing

As with any filtering operation in Scala, we can chain results together to further filters.  To find all forms that are *either* present indicative passive, *or* impferfect indicative passive, we could apply a parallel `orMorphMathces` method first, to select all tokens that are *either* in the perfect or imperfect tense:



or-ing the requirements:
```scala mdoc:silent
val perfect = ClassifiedValue(TenseValues, Perfect)
val perfectOrImperfect = hyginus.tokens.filter(t => t.orMorphMatches(Vector(perfect, imperfect)))
```

(And they are legion: almost 3600.)
```scala mdoc
perfectOrImperfect.size
```

And then applying to that set of tokens a second filter `and`ing a requirement that the tokens be indicative mood and passive voice.


```scala mdoc:silent
val passive = ClassifiedValue(VoiceValues, Passive)
val indicativePassive = Vector(indicative, passive)
val perfectOrimperfectIndicativePassive =  perfectOrImperfect.filter(t => t.andMorphMatches(indicativePassive))
```

Only 63 of those!
```scala mdoc
perfectOrimperfectIndicativePassive.size
```
