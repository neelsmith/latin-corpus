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


Import:
```scala mdoc:silent
import edu.holycross.shot.tabulae._
```

Define values you're interested in:

```scala mdoc:silent
val impft = ClassifiedValue(TenseValues, Imperfect)
val impftTokens = hyginus.tokens.filter(_.morphologyMatches(impft))
```

667 of them!
```scala mdoc
impftTokens.size
```




 Apply:
 ```scala mdoc:silent
 val indicative = ClassifiedValue(MoodValues, Indicative)
 val subjunctive = ClassifiedValue(MoodValues, Subjunctive)
 val impftIndic = Vector(impft, indicative)
 val impftSubj = Vector(impft, subjunctive)
 ```

```scala mdoc:silent
val impftIndicTokens =  hyginus.tokens.filter(t => t.andMorphMatches(impftIndic))
val impftSubjTokens = hyginus.tokens.filter(t => t.andMorphMatches(impftSubj))
```


135 indicatives, 532 subjunctives.  Teach the imperfect subjunctive big time.

```scala mdoc
impftIndicTokens.size
impftSubjTokens.size
```
