---
title: The latincorpus library
layout: page
nav_order: 0
---



**Version @VERSION@**

# The `latincorpus` library

## Central concepts


### The `LatinParsedToken`

A `LatinParsedToken` is a categorized token, with an associated list of morphological analyses. The list will be empty for tokens that are not categorzied as `Lexical`  tokens.  It will also be empty for lexical tokens that have not been parsed.

The  `LatinParsedToken` is citable by a CTS URN that extends the canonical citation scheme for the text by one level to create a canonical citation for individual tokens.

### The `LatinParsedTokenSequence` trait

Classes implementing the `LatinParsedTokenSequence` trait have an ordered sequence of `LatinParsedToken`s.  

Some implementations:

- `LatinCorpus`
- `LatinCitableUnit`
- `LatinSentence`
- `LatinNGram`

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
