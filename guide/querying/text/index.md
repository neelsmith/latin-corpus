---
title: Matching text
layout: page
nav_order: 1
parent: Matching and filtering data sets
---

# Matching text

```scala mdoc:invisible
import edu.holycross.shot.latincorpus._
val hyginusUrl = "https://raw.githubusercontent.com/LinguaLatina/analysis/master/data/hyginus/hyginus-latc.cex"
val hyginus = LatinCorpus.fromUrl(hyginusUrl)
```

We've loaded a `LatinCorpus` of the *Fabulae* of Hyginus. (See [an example of how to do that](../../datamodels/parsedTokenSequence/).)


These are crude and should be abstracted in a higher-order methods.

Two occurrences of string `amasse` in two different sentences.

```scala mdoc:silent
val amasseTokens = hyginus.tokens.filter(t => t.text == "amasse")
val amasseSentences = hyginus.sentences.sequences.filter(s => s.lexicalText.contains("amasse"))
```


`Amo` in Hyginus: 7 distinct forms, in 23 occurrences.
```scala mdoc:silent
val amo = hyginus.tokenLabelledLexemeIndex("amasse")
val amoPassages = hyginus.passagesForLexeme("ls.n2280")
val amoForms = hyginus.lexemeTokenIndex("ls.n2280")
```

22 different sentences:

```scala mdoc:silent
val amoSentences = hyginus.sentences.sequences.filter(s => s.matchesLexeme("ls.n2280"))
```
