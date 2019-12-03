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

function search(photo, index, requestId) {
  let accurate = location.href.indexOf("accurate") > -1;
  let formData = new FormData();
  formData.append("photo", photo);
  formData.append("requestId", requestId);
  axios
    .post(
      accurate ? config.api.searchAccurate : config.api.searchFast,
      formData
    )
    .then(function(response) {
      $("#search_" + index).removeClass("loading");
      console.log(response);
      if (response.status == 200) {
        let data = response.data;
        if (data) {
          let result = JSON.parse(response.data.result);
          console.log(result);
          let count = 0;
          for (let i = 0; i < result.resultList.length; i++) {
            if (count > 3) break;
            let searchImg = result.resultList[i];
            if (searchImg.imgUrl) {
              count++;
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
        }
      } else {
        $("#search_" + index).addClass("failed");
      }
    })
    .catch(function(error) {
      console.log(error);
    });
}
