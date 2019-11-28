function getChooseFiles(input) {
  if (input.files) {
    rederFileList(input.files);
  }
}

function rederFileList(files) {
  // console.log(files);
  var filenameStr = "";
  for (var i = 0; i < files.length; i++) {
    filenameStr += `<p class="ml-3 mr-3">${files[i].name}</p>`;
    upload(files[i]);
  }
  $("#showFileName").html(filenameStr);
}

function upload(photo) {
  axios
    .post(api.filepreprocess, {
      apikey: api.key.fast,
      photo
    })
    .then(function(response) {
      console.log(response);
    })
    .catch(function(error) {
      console.log(error);
    });
}

// function getUserPermissions() {
//   return axios.get("/user/12345/permissions");
// }

// axios.all([getUserAccount(), getUserPermissions()]).then(
//   axios.spread(function(acct, perms) {
//     // 两个请求现在都执行完成
//   })
// );
