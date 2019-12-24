function getChooseFiles(input) {
  if (input.files) {
    saveRequest("add", input.files.length).then(function(request) {
      $("#showFileName").html("");
      $("#searchList").html("");
      rederSearchList(input.files, request.data.id);
    });
  }
}

function rederSearchList(files, requestId) {
  let filenameStr = "";
  for (let i = 0; i < files.length; i++) {
    filenameStr += `<p class="ml-3 mr-3">${files[i].name}</p>`;
    $("#searchList")
      .append(`<div class="d-flex flex-column flex-nowrap justify-content-center search-result shadow p-3 mb-5 bg-white rounded searching" id="search_${i}">
                <div class="d-flex flex-row flex-nowrap justify-content-start source">
                    <div class="resend-area">
                        <button type="button" class="btn btn-primary resend" onclick="resend(this);">Resend</button>
                    </div>
                    <div
                        class="title d-flex flex-column flex-nowrap justify-content-center mr-3 pr-3 pl-3 font-weight-bold flex-shrink-0">
                        目标图片</div>
                    <div class="d-flex flex-row flex-wrap justify-content-start flex-grow-1">
                        <figure class="figure mr-3" style="max-width: 35%;">
                            <img src="${window.URL.createObjectURL(
                              files[i]
                            )}" class="figure-img img-fluid rounded" alt="...">
                            <figcaption class="figure-caption text-center">${
                              files[i].name
                            }</figcaption>
                        </figure>
                    </div>
                </div>
                <div class="d-flex flex-row">
                    <div
                        class="title d-flex flex-column flex-nowrap justify-content-center mr-3 pr-3 pl-3 font-weight-bold flex-shrink-0">
                        命中图片</div>
                    <div class="d-flex flex-row flex-wrap justify-content-start flex-grow-1" id="result_${i}">
                    </div>
                </div>
            </div>`);
    search(files[i], i, requestId);
  }
  $("#showFileName").html(filenameStr);
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
  search(
    _el.data("photo"),
    _el.data("index"),
    _el.data("requestId"),
    _el.data("count"),
    _el.data("requestImageId")
  );
}

function search(photo, index, requestId, count, requestImageId) {
  let accurate = location.href.indexOf("accurate") > -1;
  let formData = new FormData();
  formData.append("photo", photo);
  formData.append("requestId", requestId);
  let url = accurate ? config.api.searchAccurate : config.api.searchFast;

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
  $("#search_" + index)
    .removeClass("searching")
    .removeClass("failed")
    .addClass("searching");
  if (requestImageId) {
    url = config.api.resend;
    formData.append("requestImageId", requestImageId);
  }
  // resend end

  axios
    .post(url, formData)
    .then(function(response) {
      $(`#result_${index}`).html("");
      $("#search_" + index).removeClass("searching");
      console.log(response);
      if (response.status == 200) {
        $("#search_" + index)
          .find(".resend")
          .eq(0)
          .data("index", index)
          .data("requestId", requestId)
          .data("count", false)
          .data("requestImageId", response.data.id)
          .data("photo", photo);
        let result = JSON.parse(response.data.result);
        if (result.class == "vs.vscommon.ImageSearchResultSet") {
          let imgCount = 0;
          for (let i = 0; i < result.resultList.length; i++) {
            if (imgCount > 3) break;
            let searchImg = result.resultList[i];
            if (searchImg.imgUrl) {
              imgCount++;
              $(`#result_${index}`)
                .append(`<figure class="figure mr-3" style="width: 150px;">
                            <img src="${config.host +
                              searchImg.imgUrl}" class="figure-img img-fluid rounded" alt="...">
                            <figcaption class="figure-caption text-center">${
                              searchImg.score
                            }</figcaption>
                            <a href="${config.host}/del/image?type=${
                accurate ? "accurate" : "fast"
              }&imgName=${searchImg.imgName}"} target="_black">deleteImg</a>
                        </figure>`);
            }
          }
        } else {
          $("#search_" + index)
            .removeClass("failed")
            .addClass("failed");
          if (count) {
            search(photo, index, requestId, count, response.data.id);
          }
        }
      }
    })
    .catch(function(error) {
      console.log(error);
    });
}
