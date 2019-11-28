const config = {
  host: "http://localhost:18080",
  api: {
    add: "/picture/add",
    search: "/picture/search"
  }
};

for (let key in config.api) {
  config.api[key] = `${config.host}${config.api[key]}`;
}
