---
title: Building a LatinCorpus
layout: page
parent: Using a LatinCorpus
nav_order: 0
has_children: true
---

**Version 5.3.0**

# Building a `LatinCorpus`

Other than programmatically constructing a Vector of `ParsedLatinToken`s, there are two ways to build a `LatinCorpus` from external data:

1. using a tokenizable corpus + SFST output from parsing a word list covering that corpus
2. using the CEX serialization of the parsed token data model

Note that if you build a `LatinCorpus` using the first method, you can then use the `cex()` method of the `LatinCorpus` class to create a CEX serialization of the parsed corpus, so that in the future you can use the faster and more convenient second method.
