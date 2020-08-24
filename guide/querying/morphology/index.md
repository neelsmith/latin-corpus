---
title: Matching and filtering morphological properties
layout: page
nav_order: 3
parent: Matching and filtering data sets
---


# Matching and filtering morphological properties


```scala mdoc:invisible
import edu.holycross.shot.latincorpus._
val hyginusUrl = "https://raw.githubusercontent.com/LinguaLatina/analysis/master/data/hyginus/hyginus-latc.cex"
val hyginus = LatinCorpus.fromUrl(hyginusUrl)
```

We've loaded a `LatinCorpus` of the *Fabulae* of Hyginus. (See [an example of how to do that](../../datamodels/parsedTokenSequence/).)


For morphological filtering and querying, we need the `tabulae` library.

```scala mdoc:silent
import edu.holycross.shot.tabulae._
```

We define one or more `ClassifiedValue`s, a pairing of a specific value belonging to a specific class.  The `tabulae` library's `Imperfect` object belongs to the `TenseValues` class, for example.

```scala mdoc:silent
val impft = ClassifiedValue(TenseValues, Imperfect)
```

We can filter tokens for a classified value using the `morphologyMatches` method.

```scala mdoc:silent
val impftTokens = hyginus.tokens.filter(_.morphologyMatches(impft))
```

Lots of imperfects in Hyginus! 667 of them.

```scala mdoc
impftTokens.size
```



We can also work with Vectors of classified values.  We can separate out imperfect subjunctives from imperfect indicatives, for example, using a Vector listing both a mood value and a tense value.

 ```scala mdoc:silent
val indicative = ClassifiedValue(MoodValues, Indicative)
val subjunctive = ClassifiedValue(MoodValues, Subjunctive)
val impftIndic = Vector(impft, indicative)
val impftSubj = Vector(impft, subjunctive)
```


The `andMorphMatches` method with apply logical `and` to require that the token matches *all* the listed properties.


```scala mdoc:silent
val impftIndicTokens =  hyginus.tokens.filter(t => t.andMorphMatches(impftIndic))
val impftSubjTokens = hyginus.tokens.filter(t => t.andMorphMatches(impftSubj))
```

So our result is 135 indicatives versus 532 subjunctives.  (Teach the imperfect subjunctive early and often.)

```scala mdoc
impftIndicTokens.size
impftSubjTokens.size
```

As with any filtering operation in Scala, we can chain results together to further filters.  To find all forms that are *either* present indicative passive, *or* impferfect indicative passive, we could apply a parallel `orMorphMathces` method first, to select all tokens that are *either* in the perfect or imperfect tense:



or-ing the requirements:
```scala mdoc:silent
val pft = ClassifiedValue(TenseValues, Perfect)
val pftImpft = hyginus.tokens.filter(t => t.orMorphMatches(Vector(pft, impft)))
```

(And they are legion: almost 3600.)
```scala mdoc
pftImpft.size
```

And then applying to that set of tokens a second filter `and`ing a requirement that the tokens be indicative mood and passive voice.


```scala mdoc:silent
val passive = ClassifiedValue(VoiceValues, Passive)
val indicative = ClassifiedValue(MoodValues, Indicative)
val indicPassive = Vector(indicative, passive)
```
```scala mdoc:silent
val pftOrImpftIndicPass =  pftImpft.filter(t => t.andMorphMatches(indicPassive))
```

Only 63 of those!
```scala mdoc
pftOrImpftIndicPass.size
```
