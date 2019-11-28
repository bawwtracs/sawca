function getChooseFiles(input) {
  if (input.files) {
    $("#showFileName").html();
    $("#processList").html();
    rederFileList(input.files);
  }
}

function rederFileList(files) {
  let filenameStr = "";
  for (let i = 0; i < files.length; i++) {
    filenameStr += `<p class="ml-3 mr-3">${files[i].name}</p>`;
    $("#processList")
      .append(`<li class="list-group-item d-flex flex-row flex-nowrap justify-content-between loading" id="process_${i}">
                <p class="file-name mr-3">${files[i].name}</p>
                <p class="result" id="result_${i}">xx m/s</p>
              </li>`);
    process(files[i], i);
  }
  $("#showFileName").html(filenameStr);
}

function process(photo, index) {
  let formData = new FormData();
  formData.append("photo", photo);
  formData.append("fast", true);
  formData.append("scalingg", 800);
  axios
    .post(config.api.add, formData)
    .then(function(response) {
      // console.log(response);
      if (response.status == 200) {
        let result = JSON.parse(response.data.result);
        if (result.data == "IMAGE_INDEX_CODE_ERROR_IMAGE_SIZE_TOO_BIG") {
          $("#process_" + index)
            .removeClass("loading")
            .addClass("too-big");
        } else if (result.data == "OK") {
          $("#process_" + index).removeClass("loading");
          $("#result_" + index).html(
            response.data.responseTime - response.data.requestTime + " m/s"
          );
        }
      } else {
        $("#process_" + index)
          .removeClass("loading")
          .addClass("failes");
      }
    })
    .catch(function(error) {
      console.log(error);
      $("#process_" + index)
        .removeClass("loading")
        .addClass("too-big");
    });
}
