function refineText(source, options) {
  return [nomalizeWhiteSpaces, compactWhiteSpaces, maskBannedWords]
    .reduce((value, filter) => filter(value, options), source);
}

function maskBannedWords(source, options) {
  return options ? options.bannedWords.reduce(maskBannedWord, source) : source;
}

function maskBannedWord(source, bannedWord) {
  const mask = "*".repeat(bannedWord.length);
  return source.replace(bannedWord, mask);
}

function nomalizeWhiteSpaces(source) {
  return source.replace("\t", " ");
}

function compactWhiteSpaces(source) {
  return source.indexOf("  ") < 0 ? source : compactWhiteSpaces(source.replace("  ", " "));
}

module.exports=refineText;
