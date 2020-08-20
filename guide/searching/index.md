---
layout: page
title: Searching
---


# Searching: overview

Get a corpus to search.  Break it into sentences.

```scala mdoc:silent
import edu.holycross.shot.latincorpus._
import edu.holycross.shot.tabulae._

val hyginusUrl = "https://raw.githubusercontent.com/LinguaLatina/analysis/master/data/hyginus/hyginus-latc.cex"
val hyginus = LatinCorpus.fromUrl(hyginusUrl)
val sentences = LatinSentence(hyginus)
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
println("Sentences with patri/dative/total: " + (patri.size, datives.size, sentences.size).mkString("/"))
```
