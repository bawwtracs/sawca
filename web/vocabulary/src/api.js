let portoal = "http";
let host = "192.168.0.54";
let port = "18080";

let api = {
  origins: () => {
    return `${portoal}://${host}:${port}/api/vocabulary/origins`;
  },
  origin: originId => {
    return `${portoal}://${host}:${port}/api/vocabulary/origin${
      originId ? "/" + originId : ""
    }`;
  },
  words: () => {
    return `${portoal}://${host}:${port}/api/vocabulary/words`;
  },
  word: wordId => {
    return `${portoal}://${host}:${port}/api/vocabulary/word${
      wordId ? "/" + wordId : ""
    }`;
  }
};

export default api;
