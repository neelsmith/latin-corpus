---
title: Data models
layout: page
nav_order: 1
has_children: true
---


# Data models


### 1. [The `LatinParsedToken`](./datamodels/parsedTokens/)

The foundational unit for all other structures in the `latincorpus` library is the `LatinParsedToken`.

A `LatinParsedToken` is a categorized token, with an associated list of morphological analyses.  The  `LatinParsedToken` is citable by a CTS URN that extends the canonical citation scheme for the text by one level to create a canonical citation for individual tokens.

See more about [the `LatinParsedToken`](./parsedTokens/)


### 2. [The `LatinParsedTokenSequence` trait](./parsedTokenSequence/)


The `LatinParsedTokenSequence` trait defines behaviors for an ordered series of `LatinParsedToken`s.

The following implementations are included in the `latin-corpus` library:

- `LatinCorpus`. This is the default implementation of the  `LatinParsedTokenSequence`.  It views a collection of tokens as a single sequence of `LatinParsedToken`s.
- `LatinCitableUnit`. This represents a sequence of parsed tokens belonging to a single canonically citable unit of text.
- `LatinEdition`. This represents a sequence of tokens belonging to a single version of a single text.
- `LatinSentence`. This represents a sequence of tokens belonging to a single sentence.
- `LatinNGram`.  This represents a single n-gram extracted from a longer sequence of parsed tokens.

See more about [the `LatinParsedTokenSequence` and its implementations](./parsedTokenSequence/).

### 3. [The `ParsedSequenceCollection`](./parsedSequenceCollection/)

A `ParsedSequenceCollection` lets you work with a collection of `LatinParsedTokenSequence`s, such as a collection of sentences, citable nodes, or even a collection of whole corpora.


See more about [the `ParsedSequenceCollection`](./parsedSequenceCollection/).
