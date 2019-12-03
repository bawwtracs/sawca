const config = {
  host: "http://localhost:18080/api/",
  api: {
    addFast: "imgsvr/add/fast",
    searchFast: "imgsvr/search/fast",
    addAccurate: "imgsvr/add/accurate",
    searchAccurate: "imgsvr/search/accurate",
    request: "request",
    total: "count/img",
    statistics: "request/statistics"
  }
};

for (let key in config.api) {
  config.api[key] = `${config.host}${config.api[key]}`;
}
