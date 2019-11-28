const api = {
  portol: "http",
  host: "www.tudatech.com",
  port: 8086,
  url: {
    filematch: "vsearchtech/api/v1.0/apipnp_search",
    filepreprocess: "vsearchtech/api/v1.0/apipnp_additem"
  },
  key: {
    fast: "guangzhouMrHuangGongAnProj20191126H1_simph2type2",
    accurate: "guangzhouMrHuangGongAnProj20191126H1_recog"
  }
};

for (var key in api.url) {
  api[key] = `${api.portol}://${api.host}:${api.port}/${api.url[key]}`;
}
