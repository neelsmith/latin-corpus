---
title: "Functional string labels: background"
layout: page
parent: Functional string labels
---


# Functional string labels: background

Each analysis of a token can produce a `ValidForm` (from the `tabulae` library).  The `latin-corpus` library allows you to simplify these forms to string values labelling *one* aspect of a morphological analysis that is most significant for the syntactic function of the token.  The labels are:

- for indeclinable forms:  the value `conjunction` or `preposition` (or an empty string for other indeclinable forms)
- for infinitive verbs: `infinitive`
- for finite verbs: its mood
- for nouns, pronouns, gerund, gerundive, participle: its case
- for adjectives, adverbs, supines: empty string
- for unparsed lexical tokens: `??`

The format of the lower-case string label is `type: value`, e.g., `verb: indicative`, `noun: dative` or `conjunction`.
