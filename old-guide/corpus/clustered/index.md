---
title: Clustering citable units
layout: page
parent: Using a LatinCorpus
---

**Version @VERSION@**

# Clustering tokens in citable units


NOT IN 7.0.0

```scala
import edu.holycross.shot.latincorpus._
val hyginusUrl = "https://raw.githubusercontent.com/LinguaLatina/analysis/master/data/hyginus/hyginus-latc.cex"
val hyginus = LatinCorpus.fromUrl(hyginusUrl)

val clustered = hyginus.clusterByCitation
```