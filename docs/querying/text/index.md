---
title: Matching textual contents
layout: page
nav_order: 1
parent: Matching and filtering data sets
---

> *Documentation compiled with version* **7.0.0-pr6**

# Matching text


We've loaded a `LatinCorpus` of the *Fabulae* of Hyginus. (See [an example of how to do that](../../datamodels/parsedTokenSequence/).)


These are crude and should be abstracted in a higher-order methods.

Two occurrences of string `amasse` in two different sentences.

```scala
val amasseTokens = hyginus.tokens.filter(t => t.text == "amasse")
val amasseSentences = hyginus.sentences.sequences.filter(s => s.lexicalText.contains("amasse"))
```


`Amo` in Hyginus: 7 distinct forms, in 23 occurrences.
```scala
val amo = hyginus.tokenLabelledLexemeIndex("amasse")
val amoPassages = hyginus.passagesForLexeme("ls.n2280")
val amoForms = hyginus.lexemeTokenIndex("ls.n2280")
```

22 different sentences:

```scala
val amoSentences = hyginus.sentences.sequences.filter(s => s.matchesLexeme("ls.n2280"))
```
