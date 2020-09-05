---
title: The LatinParsedTokenSequence
layout: page
nav_order: 2
has_children: true
parent: Data models
---



**Version 7.0.0-pr6**


# The `LatinParsedTokenSequence`


## Background

All textual content can be canonically cited using CTS URNs.  We may wish to analyze texts in terms of units that do not align with citation units, however.  Maintaining alignments of analytical features and citation units can be challenging if the underlying model of text is simply a sequence of code points.  The `latincorpus` library takes a different approach, and presumes that a sequence of code points can be parsed into a sequence of [semantically meaningful tokens](../parsedTokens/).  

The `LatinParsedTokenSequence` defines behaviors applying to *any* sequence of Latin tokens, whether organized by canonical citation unit, or other analytical structure.


## Aligning and converting token sequences


Since all tokens are identified with CTS URNs, they are explicitly located in a canonical citation scheme.  This makes it straightforward to align tokens in different groupings, and therefore to regroup a sequence of tokens from one implementation of the `LatinParsedTokenSequence` into another cluster.



The default implementation of the `LatinParsedTokenSequence` is a `LatinCorpus`.  Let's instantiate a `LatinCorpus` from a plain-text representation retrieved from a URL:


```scala
import edu.holycross.shot.latincorpus._
val hyginusUrl = "https://raw.githubusercontent.com/LinguaLatina/analysis/master/data/hyginus/hyginus-latc.cex"
val hyginus = LatinCorpus.fromUrl(hyginusUrl)
```

In the `LatinCorpus`, all tokens are simply a single sequence.  We can compare the total number of tokens in the corpus (more than 32,000) with the number of lexical tokens ("words") in the corpus (fewer than 28,000), for example.

```scala
hyginus.size
// res0: Int = 32463
hyginus.lexicalTokens.size
// res1: Int = 27705
```

```scala
val sections = hyginus.citableUnits
```

1229 citable sections

```scala
sections.size
// res2: Int = 1229
```



We can reorganize the corpus into a series of sentences.

```scala
val sentences = hyginus.sentences
```

The result is a `ParsedSequenceCollection`, containing a series of `LatinSentence`s, which, like the `LatinCorpus`, is an implementation of the `LatinParsedTokenSequence`.

We can see that there are a little more than 2400 sentences in the text of Hyginus (so the average sentence is a little over 10 "words" in length).

```
sentences.size
```



> other examples: TBA
>
> group a corpus into editions:
> corpus.editions
> group a corpus into ngrams:
> corpus.ngrams(2)



Likewise, any implementation can be flattened into a `LatinCorpus` model:

```
sentences.corpus
editions.corpus
ngrams.corpus
ohco2.corpus
```
