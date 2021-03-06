# `latincorpus`: release notes

**6.0.0**:  Adds entirely new mechanism for filtering tokens and sequences of tokens using morphological property specifications.

**5.6.0**: Adds `corpusLabelledLexemeIndex` to `LatinCorpus` class.

**5.5.0**: Adds faster `passagesForLexeme` method in `LatinCorpus` and fixes a bug in compiling `lexemeConcordance`.

**5.4.0**: Supports building a Vector of `LatinSentence`s from any `LatinParsedTokenSequence`.

**5.3.0**: Supports default value for all methods requiring a `UrnManager`.

**5.2.0**: Add methods for filtering sequences based on function strings.

**5.1.0**: Profile a `LatinCorpus` with histograms of lexical items, tokens, and morphological forms.

**5.0.0**: Simpler redefinition of `LatinCorpus` class breaks API.

**4.0.0**: API-breaking name changes simplify interoperation with library dependencies.

**3.2.1**: Fixes a bug in handling case-insensitive creation of `LatinToken`s.

**3.2.0**: Adds methods for generating CITE Collections of token analyses from a `LatinCorpus`.

**3.1.1**: Correct publication of binaries to bintray.

**3.1.0**: Adds `LemmatizedFormUrns` class.

**3.0.0**: API-breaking changes in numerous libraries.

**2.2.2**: Fixes a bug in filtering lexical tokens from a Latin token sequence.

**2.2.1**: Correctly versioned publication of changes in both 2.2.0 and 2.1.1.

**2.2.0**: Updates highlighting functions.


**2.1.1**: Updates library dependencies, and correctly uses `+` to demarcate morpheme boundaries.

**2.1.0**: Adds `hover` function to `LatinTokenSequence`s.


**2.0.0**: Removes the `LewisShort` object, instead using the object in the updated `tabulae` library.

**1.6.0**: Adds `Highlighter` class.  `LatinTokenSequence` can now highlight text from a Vector of `Highlighter`s.

**1.5.0**:  Token sequences can highlight token strings by part of speech, using configurable open/close highlighting strings.


**1.4.0**: `multipleLexemesHistogram` adds percent of total token occurences to item string.

**1.3.0**: Adds `multipleLexemesHistogram` function to `LatinCorpus`.

**1.2.1**:  Implement logging with airframe framework (issue #22).

**1.2.0**: Adds `strict` option to `fromFstLines` and `fromAnalyses` to permit continuing after failure on an individual token.

**1.1.0**:  Adds functions to `LatinCorpus` for finding histograms and concordances of tokens, lexemes, and lemmatized forms.

**1.0.0**: initial release
