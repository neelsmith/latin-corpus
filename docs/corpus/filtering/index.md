---
title: Filtering citable units
layout: page
parent: Using a LatinCorpus
---

**Version 5.3.0**

# Filtering texts morphologically

You create a `MorphologyCollectionsFilter` for a clustered set of tokens, so first, you need to cluster tokens in some unit.


A simple example:  cluster by citation  unit.

```scala
val clustered = hyginus.clusterByCitation
```

Now create a `MorphologyCollectionsFilter`

```scala
val filter = MorphologyCollectionsFilter(clustered)
```

The `MorphologyCollectionsFilter` delegates the work of filtering individual clusters to a `TokenSequenceFilter`, which works on an indivual clustered unit.


Let's take a short example before generalizing to filtering a whole collection.  One kind of clustered unit is a `LatinCitableUnit`.

```scala
val c196aUrl = "https://raw.githubusercontent.com/neelsmith/latin-corpus/master/jvm/src/test/resources/sect196a.cex"
val hyginusSelection = LatinCorpus.fromUrl(c196aUrl)
val c196a = hyginusSelection.clusterByCitation.head
val tfilter = TokenSequenceFilter(c196a)
```
