---
title: The latincorpus library
layout: page
nav_order: 0
---



**Version 7.0.0-preview**

# The `latincorpus` library

## Central concepts


### The `LatinParsedToken`

The foundational unit for all other structures in the `latincorpus` library is the `LatinParsedToken`.

A `LatinParsedToken` is a categorized token, with an associated list of morphological analyses.  The  `LatinParsedToken` is citable by a CTS URN that extends the canonical citation scheme for the text by one level to create a canonical citation for individual tokens.

See more about [the `LatinParsedToken`](./parsedTokens/)








### The `LatinParsedTokenSequence` trait

Classes implementing the `LatinParsedTokenSequence` trait have an ordered sequence of `LatinParsedToken`s.  

Some implementations:

- `LatinCorpus` views an entire corpus as a single sequence of `LatinParsedToken`s.  This is useful for surveying the contents of an entire corpus.  To compare different corpora of Latin texts (grouped into corpora according to whatever principle you choose), you could create a `ParsedSequenceCollection` containing multiple instances of `LatinCorpus`.
- `LatinCitableUnit` is a sequence of tokens ...
Group from any parsed token sequence: `citableUnits`
- `LatinSentence`..... group from any parsed token sequence: `sentences`
- `LatinNGram`... group fromv any parsed token sequence: `ngram(n)`

Using a `LatinCorpus`

- Old notes on [some things you can do](./corpus/) with a `LatinCorpus`


### The `ParsedSequenceCollection`

The `ParsedSequenceCollection` works with a Vector of `LatinParsedTokenSequence`s.

## Analytical operations: matching and filtering

Overview:

- Boolean matching on individual tokens.  Token sequences can use these methods to filter sets of tokens.
- Boolean matching on token sequences.  Sequence collections can use these to filter sequences of tokens.


Types of matching and filtering:

- substring matching on a token sequence
- token text (equality)
- (labelled) lexeme matching and filtering
- function strings
- morphological properties




## Technical overview

- [Overview of data structures](./libraries/)
