let portoal = "http";
let host = "localhost";
let port = "18080";

let api = {
  origins: () => {
    return `${portoal}://${host}:${port}/api/vocabulary/origins`;
  },
  origin: originId => {
    if (!originId) {
      return `${portoal}://${host}:${port}/api/vocabulary/origin`;
    }
    return `${portoal}://${host}:${port}/api/vocabulary/origin/${originId}`;
  },
  words: () => {
    return `${portoal}://${host}:${port}/api/vocabulary/words`;
  },
  word: wordId => {
    if (!wordId) {
      return `${portoal}://${host}:${port}/api/vocabulary/word`;
    }
    return `${portoal}://${host}:${port}/api/vocabulary/word/${wordId}`;
  }
};

export default api;
