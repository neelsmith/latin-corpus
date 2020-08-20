---
title: Navigating token sequences
layout: page

---

# Navigating token sequences

Navigation is by CTS URN.  The parsed sequence extends canonical citation by one tier in order to cite at the level of a token.

We can cluster sequences of tokens to model different kinds of objects:

- a corpus clustered by citable units
- a sequence of sentences
- arbitrary n-grams



## Create a `LatinParsedTokenSequence`

We'll load a corpus, then break it into sentences since that's the logical unit we want to work with.

```scala mdoc:silent
import edu.holycross.shot.latincorpus._
import edu.holycross.shot.tabulae._

val hyginusUrl = "https://raw.githubusercontent.com/LinguaLatina/analysis/master/data/hyginus/hyginus-latc.cex"
val hyginus = LatinCorpus.fromUrl(hyginusUrl)
val sentences = LatinSentence(hyginus)
```


## Navigate by canonical citation

## Navigate by sentence

## Navigate by n-gram
