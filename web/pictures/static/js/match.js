function getChooseFiles(input) {
  if (input.files) {
    $("#showFileName").html();
    $("#searchList").html();
    rederSearchList(input.files);
  }
}

function rederSearchList(files) {
  let filenameStr = "";
  for (let i = 0; i < files.length; i++) {
    filenameStr += `<p class="ml-3 mr-3">${files[i].name}</p>`;
    $("#searchList")
      .append(`<div class="d-flex flex-column flex-nowrap justify-content-center search-result shadow p-3 mb-5 bg-white rounded" id="search_${i}">
                <div class="d-flex flex-row flex-nowrap justify-content-start source">
                    <div
                        class="title d-flex flex-column flex-nowrap justify-content-center mr-3 pr-3 pl-3 font-weight-bold flex-shrink-0">
                        目标图片</div>
                    <div class="d-flex flex-row flex-wrap justify-content-start flex-grow-1">
                        <figure class="figure mr-3" style="max-width: 35%;">
                            <img src="${window.URL.createObjectURL(files[i])}" class="figure-img img-fluid rounded" alt="...">
                            <figcaption class="figure-caption text-center">${files[i].name}</figcaption>
                        </figure>
                    </div>
                </div>
                <div class="d-flex flex-row">
                    <div
                        class="title d-flex flex-column flex-nowrap justify-content-center mr-3 pr-3 pl-3 font-weight-bold flex-shrink-0">
                        命中图片</div>
                    <div class="d-flex flex-row flex-wrap justify-content-start flex-grow-1" id="result_${i}">
                        <figure class="figure mr-3" style="max-width: 20%;">
                            <img src="./static/images/statistics.jpg" class="figure-img img-fluid rounded" alt="...">
                            <figcaption class="figure-caption text-center">99.1%</figcaption>
                        </figure>
                    </div>
                </div>
            </div>`);
    process(files[i], i);
  }
  $("#showFileName").html(filenameStr);
}
