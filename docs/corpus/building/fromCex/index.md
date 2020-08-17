---
title: Building from a CEX serialization
layout: page
parent: Building a LatinCorpus
grand_parent: Using a LatinCorpus
nav_order: 2
---

**Version 5.3.0**

# Building a `LatinCorpus` from CEX serialization


There are three ways to build a `LatinCorpus` from data serialized to CEX in the parsed token data model:

1. directly from a Vector of Strings, `LatinCorpus(stringList)`
2. from a file, `LatinCorpus.fromFile(f)`
3. from a URL, `LatinCorpus.fromUrl(url)`
