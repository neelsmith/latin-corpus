# `latin-corpus`

A JVM library for working with a citable corpus of morphologically parsed texts in Latin.

`latin-corpus` reads output from a morphological parser built with [tabulae](https://github.com/neelsmith/tabulae), and applies it to a citable text corpus. `latin-corpus` supports higher-level manipulation of the corpus than `tabulae`'s token-level paring. It can profile usage of arbitrary combinations of morphological features or vocabulary in a corpus, and can filter the corpus to include or exclude passages containing a specified set of features or vocabulary.


## Current versions:  6.0.0 / 7.0.0-pr6

The `latincorpus` library is undergoing active development as part of a three-year project beginning in the academic year 2020-2021 in the Classics Department at the College of the Holy Cross.  For more information about the project, see <https://lingualatina.github.io/>.

The current published release is 6.0.0. The 7.x series represents an API-breaking, substantial reworking of the library.  Binaries of prerelease versions as well as published releases are available from bintray with the version designation `7.0.0.-prN`: for maven, ivy or gradle coordinates, see [this page](https://bintray.com/neelsmith/maven/latincorpus).



See [release notes](releases.md).




## Documentation

- The user's guide is currently being updated with documentation generated by [mdoc](https://github.com/scalameta/mdoc) based on the code base of prerelease versions in the `7.0.0-prN` series: <https://neelsmith.github.io/latin-corpus/>
