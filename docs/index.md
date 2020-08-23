---
title: The latincorpus library
layout: page
nav_order: 0
---



**Version 7.0.0-preview**

# The `latincorpus` library

## Three central concepts

The library works with a hierarchy of three central models:  the parsed token, the sequence of parsed tokens, and the collection of parsed token sequences.

### 1. [The `LatinParsedToken`](./parsedTokens/)

The foundational unit for all other structures in the `latincorpus` library is the `LatinParsedToken`.

A `LatinParsedToken` is a categorized token, with an associated list of morphological analyses.  The  `LatinParsedToken` is citable by a CTS URN that extends the canonical citation scheme for the text by one level to create a canonical citation for individual tokens.

See more about [the `LatinParsedToken`](./parsedTokens/)


### 2. The `LatinParsedTokenSequence` trait


The `LatinParsedTokenSequence` trait defines behaviors for an ordered series of `LatinParsedToken`s.

It is implemented by:

- `LatinCorpus` views an entire corpus as a single sequence of `LatinParsedToken`s.  This is useful for surveying the contents of an entire corpus.  To compare different corpora of Latin texts (grouped into corpora according to whatever principle you choose), you could create a `ParsedSequenceCollection` containing multiple instances of `LatinCorpus`.
- `LatinCitableUnit` is a sequence of tokens ...
Group from any parsed token sequence: `citableUnits`
- `LatinSentence`..... group from any parsed token sequence: `sentences`
- `LatinNGram`... group fromv any parsed token sequence: `ngram(n)`



### 3. The `ParsedSequenceCollection`

A `ParsedSequenceCollection` works with a Vector of `LatinParsedTokenSequence`s.


## Analytical operations: matching and filtering

This hierarchy of classes makes it possible to match individual tokens, and apply filters to tokens organized in different ways.

-  Token sequences can Boolean matching on individual tokens to filter sets of tokens.  Example: extract all verb forms from a `LatinCorpus` (an implementation of the `LatinParsedTokenSequence` trait).
- Sequence collections can Boolean matching on token sequences  to filter sequences of tokens.  Example:  in a `ParsedSequenceCollection` made up of a series of `LatinSentence`s (an implementation of the `LatinParsedTokenSequence` trait), identify all sentences including verbs in the subjunctive mood, or all sentences using any form a specified lexeme.

Matching and filtering may apply to the string value of a token or token sequence's textual content, or to the morphological analyses associated with a token or sequence of tokens.

You can work with morphological analyses by:

- matching and filtering on lexeme identifiers
- matching and filtering on morphological properties
- matching and filtering on functional labels, a higher-order construct derived from morphological properties

## Analytical operations: profiling a data set

- TBA
