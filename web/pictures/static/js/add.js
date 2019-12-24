function getChooseFiles(input) {
  if (input.files) {
    saveRequest("add", input.files.length).then(function(request) {
      $("#showFileName").html("");
      $("#addList").html("");
      rederFileList(input.files, request.data.id);
    });
  }
}

function rederFileList(files, requestId) {
  let accurate = location.href.indexOf("accurate") > -1;
  let filenameStr = "";
  if (accurate) {
    for (let i = 0; i < files.length; i++) {
      filenameStr += `<p class="ml-3 mr-3">${files[i].name}</p>`;
    }
    $("#showFileName").html(filenameStr);
    addSync(files, 0, requestId);
    if (files.length > 1) {
      addSync(files, 1, requestId);
    }
  } else {
    for (let i = 0; i < files.length; i++) {
      filenameStr += `<p class="ml-3 mr-3">${files[i].name}</p>`;
      $("#addList")
        .append(`<li class="list-group-item d-flex flex-row flex-nowrap justify-content-between loading" id="add_${i}">
                <p class="file-name mr-3">${files[i].name}</p>
                <p class="result" id="result_${i}">xx m/s</p>
                <button type="button" class="btn btn-primary resend" onclick="resend(this);">Resend</button>
              </li>`);
      var ele = document.getElementById("addList");
      ele.scrollTop = ele.scrollHeight;
      add(files[i], i, requestId);
    }
    $("#showFileName").html(filenameStr);
  }
}

function saveRequest(operation, imageTotal) {
  return axios.post(config.api.request, {
    operation,
    imageTotal,
    type: location.href.indexOf("accurate") > -1 ? "accurate" : "fast"
  });
}

function resend(el) {
  let _el = $(el);
  add(
    _el.data("photo"),
    _el.data("index"),
    _el.data("requestId"),
    _el.data("count"),
    _el.data("requestImageId")
  );
}

function add(photo, index, requestId, count, requestImageId) {
  let accurate = location.href.indexOf("accurate") > -1;
  let formData = new FormData();
  formData.append("photo", photo);
  formData.append("requestId", requestId);
  let url = accurate ? config.api.addAccurate : config.api.addFast;

  // resend start
  if (count) {
    if (count > config.resendLimit) {
      return;
    } else {
      count++;
    }
  } else if (count !== false) {
    count = 1;
  }
  $("#add_" + index)
    .removeClass("loading")
    .removeClass("failed")
    .removeClass("too-big")
    .addClass("loading");
  if (requestImageId) {
    url = config.api.resend;
    formData.append("requestImageId", requestImageId);
  }
  // resend end

  axios
    .post(url, formData)
    .then(function(response) {
      console.log(response);
      if (response.status == 200) {
        $("#add_" + index)
          .find(".resend")
          .eq(0)
          .data("index", index)
          .data("requestId", requestId)
          .data("count", false)
          .data("requestImageId", response.data.id)
          .data("photo", photo);
        let result = JSON.parse(response.data.result);
        if (result.data == "IMAGE_INDEX_CODE_ERROR_IMAGE_SIZE_TOO_BIG") {
          $("#add_" + index)
            .removeClass("loading")
            .addClass("too-big");
        } else if (result.data == "OK") {
          $("#add_" + index).removeClass("loading");
          $("#result_" + index).html(
            response.data.responseTime - response.data.requestTime + " m/s"
          );
        }
      } else {
        $("#add_" + index)
          .removeClass("loading")
          .addClass("failed");
        if (count) {
          add(photo, index, requestId, count, response.data.id);
        }
      }
    })
    .catch(function(error) {
      console.log(error);
    });
}

function addSync(files, index, requestId) {
  if (index > files.length - 1) {
    return;
  }
  $("#addList")
    .append(`<li class="list-group-item d-flex flex-row flex-nowrap justify-content-between loading" id="add_${index}">
          <p class="file-name mr-3">${files[index].name}</p>
          <p class="result" id="result_${index}">xx m/s</p>
        </li>`);
  var ele = document.getElementById("addList");
  ele.scrollTop = ele.scrollHeight;
  let accurate = location.href.indexOf("accurate") > -1;
  let formData = new FormData();
  formData.append("photo", files[index]);
  formData.append("requestId", requestId);
  axios
    .post(accurate ? config.api.addAccurate : config.api.addFast, formData)
    .then(function(response) {
      console.log(response);
      if (response.status == 200) {
        let result = JSON.parse(response.data.result);
        if (result.data == "IMAGE_INDEX_CODE_ERROR_IMAGE_SIZE_TOO_BIG") {
          $("#add_" + index)
            .removeClass("loading")
            .addClass("too-big");
        } else if (result.data == "OK") {
          $("#add_" + index).removeClass("loading");
          $("#result_" + index).html(
            response.data.responseTime - response.data.requestTime + " m/s"
          );
        }
      } else {
        $("#add_" + index)
          .removeClass("loading")
          .addClass("failed");
      }
      return addSync(files, index + 2, requestId);
    })
    .catch(function(error) {
      console.log(error);
      // $("#add_" + index)
      //   .removeClass("loading")
      //   .addClass("too-big");
    });
}
