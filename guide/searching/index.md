---
layout: page
title: Searching
---


# Searching: overview

## Need filtering sequence by URN

Basic cross-type navigation

## Create a `LatinParsedTokenSequence`

We'll load a corpus, then break it into sentences since that's the logical unit we want to work with.

```scala mdoc:silent
import edu.holycross.shot.latincorpus._
import edu.holycross.shot.tabulae._

val hyginusUrl = "https://raw.githubusercontent.com/LinguaLatina/analysis/master/data/hyginus/hyginus-latc.cex"
val hyginus = LatinCorpus.fromUrl(hyginusUrl)
val sentences = LatinSentence(hyginus)
```

## Using a `TokenSequenceFilter`

```scala mdoc:silent
val dativeObjects = sentences.filter(s => TokenSequenceFilter(s).limitCase(Vector(Dative)))
```




## The function strings approach


Get sentences with at least one token matching the function string `noun: dative`.

```scala mdoc:silent
val datives = sentences.filter(s => s.matchesFunctionStrings(Vector("noun: dative")))
```
```scala mdoc
println("Sentences with dative/total: " + datives.size + "/" + sentences.size)
```





## The lexical strings approach

Lexical strings is an easy way to search on text content of a token sequence.

```scala mdoc:silent
val patri = sentences.filter(s => s.lexicalText.contains("patri"))
```
```scala mdoc
println("Sentences with patri/dative/total: " + Vector(patri.size, datives.size, sentences.size).mkString("/"))
```

Let's format the text of those 9 sentences.

```scala mdoc:silent
val numberedStrings = patri.map(s => "1. " + s.lexicalText.mkString(" ").replaceAll("patri ", "**patri** ") + ".")
```
Here's the output of `println(numberedStrings.mkString("\n"))`:
```scala mdoc:passthrough
println(numberedStrings.mkString("\n"))
```


## Compare results

```scala mdoc:silent
val dativeStringUrns = datives.map(s => s.tokens.head.urn.collapsePassageBy(1).addVersion("hc"))
val dativeObjectUrns = dativeObjects.map(s => s.tokens.head.urn.collapsePassageBy(1).addVersion("hc"))
val diffs = dativeStringUrns diff dativeObjectUrns
val overlaps = dativeStringUrns intersect dativeObjectUrns
val combined = dativeStringUrns union dativeObjectUrns
```
