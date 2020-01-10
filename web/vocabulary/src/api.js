let portoal = "http";
let host = "a8cdf429.ngrok.io";
let port = "80";

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
