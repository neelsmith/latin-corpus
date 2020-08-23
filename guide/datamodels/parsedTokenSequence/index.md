---
title: The LatinParsedTokenSequence
layout: page
nav_order: 2
has_children: true
parent: Data models
---



**Version @VERSION@**


# The `LatinParsedTokenSequence`


## Aligning and converting implementations

All implementations of the `LatinParsedTokenSequence` can



Examples:

From a `LatinCorpus`, generate:

- a collection of sentences:

```
corpus.sentences
```

- a collection of editions:

```
corpus.editions
```

- a collection of n-grams: here, bigrams:

```
corpus.ngrams(2)
```

- a series of citable text nodes:

```
corpus.citableUnits
```

Likewise, any implementation can be flattened into a `LatinCorpus` model:

```
sentences.corpus
editions.corpus
ngrams.corpus
ohco2.corpus
```
