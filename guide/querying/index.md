---
title: Matching and filtering data sets
layout: page
nav_order: 1
has_children: true
---


# Matching and filtering data sets


Matching and filtering may apply to the text content of tokens, or to their associated morphological analyses.

You can work with morphological analyses by:

- matching and filtering on lexeme identifiers
- matching and filtering on morphological properties
- matching and filtering on functional labels, a higher-order construct derived from morphological properties



## Background ideas

-  Token sequences can Boolean matching on individual tokens to filter sets of tokens.  Example: extract all verb forms from a `LatinCorpus` (an implementation of the `LatinParsedTokenSequence` trait).
- Sequence collections can use Boolean matching on token sequences to filter sequences of tokens.  Example:  in a `ParsedSequenceCollection` made up of a series of `LatinSentence`s (an implementation of the `LatinParsedTokenSequence` trait), identify all sentences including verbs in the subjunctive mood, or all sentences using any form a specified lexeme.


## Matching and filtering

Use the following links for more details.
