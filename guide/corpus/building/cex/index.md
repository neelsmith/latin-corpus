---
title: Building from a CEX serialization
layout: page
parent: Building a LatinCorpus
grand_parent: Using a LatinCorpus
nav_order: 2
---

**Version @VERSION@**

# Building a `LatinCorpus` from CEX serialization

```scala mdoc:silent
import edu.holycross.shot.latincorpus._
val url = "https://raw.githubusercontent.com/LinguaLatina/analysis/master/data/hyginus/hyginus-latc.cex"
val latinCorpus = LatinCorpus.fromUrl(url)
```


The resulting `LatinCorpus` is citable by individual token, and associates a (possibly empty) list of analyses with each token.

Number of tokens in the corpus:


```scala mdoc
println(latinCorpus.size)
```


- fromFile
- fromURL
or apply method
