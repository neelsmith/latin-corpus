---
title: Filtering citable units
layout: page
parent: Using a LatinCorpus
---

**Version @VERSION@**

# Filtering texts morphologically

You create a `MorphologyCollectionsFilter` for a clustered set of tokens, so first, you need to cluster tokens in some unit.

```scala mdoc:invisible
import edu.holycross.shot.latincorpus._
val hyginusUrl = "https://raw.githubusercontent.com/LinguaLatina/analysis/master/data/hyginus/hyginus-latc.cex"
val hyginus = LatinCorpus.fromUrl(hyginusUrl)
```

A simple example:  cluster by citation  unit.

REMOVED in 7.0.0
```REMOVED in 7.0.0
val clustered = hyginus.clusterByCitation
```
