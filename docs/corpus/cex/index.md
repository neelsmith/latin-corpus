---
title: Saving a LatinCorpus
layout: page
parent: Using a LatinCorpus
nav_order: 1
---

**Version 5.2.0**

# Saving a `LatinCorpus`

Maybe you have a particular corpus you're interested in working with repeatedly (e.g., a selection of material you're using in a course).  It can be convenient to save that corpus as a CEX file that you can easily and quickly reload.

Assume you've built a corpus (see building page)




```scala
// voila
val latinCorpus = LatinCorpus.fromFstLines(corpus,Latin23Alphabet, fstLines, strict=false)
```

To serialize to a CITE Collection, you need to be able to expand tabulae's abbreviated identifiers to full URNs.  You do that wit a `UrnManager` from the `tabulae` library.  We'll use one we've already defined.


```scala
import edu.holycross.shot.tabulae._
val urnManagerUrl = "https://raw.githubusercontent.com/neelsmith/tabulae/master/jvm/src/test/resources/datasets/analytical_types/urnregistry/collectionregistry.cex"
val manager = UrnManager.fromUrl(urnManagerUrl)
```

Write CEX:

```scala
val cex = latinCorpus.cex(manager)
import java.io.PrintWriter
new PrintWriter("hyginus-lc.cex"){write(cex);close;}
```
