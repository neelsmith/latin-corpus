---
title: The latincorpus library
layout: page
nav_order: 0
---



**Version 7.0.0-preview**

# The `latincorpus` library

## The [foundational class hierarchy](./datamodels/)

The library works with a hierarchy of three central models:  the parsed token, a sequence of parsed tokens (such as an n-gram, or an entire edition), and a collection of parsed token sequences (for example, a collection of sentences).

See [more about these data models](./datamodels/).


## Analytical operations: [matching and filtering](./querying/)

This hierarchy of classes makes it possible to match individual tokens, and apply filters to tokens organized in different ways.  

You equally easily find examples of tokens analyzed as  verbs in the imperfect tense and passive voice, find  occurrences of all forms of the verb `interficio`, or select sentences with verbs in the imperfect subjunctive.

See [more about matching and filtering data](./querying/).


## Analytical operations: [profiling a data set](./profiling/)

Both the `LatinParsedTokenSequence` and the `ParsedSequenceCollection` include a number of methods to simplify summarizing, characterizing and comparing data sets.


See more about profiling data sets:  [TBA](./profiling/).
