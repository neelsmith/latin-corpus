---
title: The latincorpus library
layout: page
nav_order: 0
---



**Version 7.0.0-preview**

# The `latincorpus` library

## The foundational class hierarchy

The library works with a hierarchy of three central models:  the parsed token, the sequence of parsed tokens, and the collection of parsed token sequences.

### 1. [The `LatinParsedToken`](./datamodels/parsedTokens/)

The foundational unit for all other structures in the `latincorpus` library is the `LatinParsedToken`.

A `LatinParsedToken` is a categorized token, with an associated list of morphological analyses.  The  `LatinParsedToken` is citable by a CTS URN that extends the canonical citation scheme for the text by one level to create a canonical citation for individual tokens.

See more about [the `LatinParsedToken`](./datamodels/parsedTokens/)


### 2. [The `LatinParsedTokenSequence` trait](./datamodels/parsedTokenSequence/)


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



## Analytical operations: [matching and filtering](./querying/)

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
