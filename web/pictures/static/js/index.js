function getCount() {
  axios
    .get(config.api.total)
    .then(function(response) {
      $("#total").html(1000000 + response.data);
    })
    .catch(function(error) {
      console.log(error);
    });
}

function statistics() {
  axios
    .get(config.api.statistics)
    .then(function(response) {
      let arr = response.data;
      let lastAdd = 0;
      let lastSearchAvg = 0;
      let lastAddAvg = 0;
      let searchAvg = 0;
      let addAvg = 0;
      let searchCount = 0;
      let addCount = 0;
      for (let i = 0; i < arr.length; i++) {
        console.log(arr[i]);
        console.log(arr[i].opeartion);
        if (arr[i].operation == "search") {
          if (!lastSearchAvg) {
            lastSearchAvg = (arr[i].endTime - arr[i].startTime) / arr[i].total;
          }
          searchAvg += (arr[i].endTime - arr[i].startTime) / arr[i].total;
          searchCount += 1;
        }
        if (arr[i].operation == "add") {
          if (!lastAdd) {
            lastAdd = arr[i].total;
          }
          if (!lastAddAvg) {
            lastAddAvg = (arr[i].endTime - arr[i].startTime) / arr[i].total;
          }
          addAvg += (arr[i].endTime - arr[i].startTime) / arr[i].total;
          addCount += 1;
        }
      }
      $("#lastAdd").html(lastAdd);
      $("#lastSearchAvg").html(lastSearchAvg.toFixed(1) + "ms");
      $("#lastAddAvg").html(lastAddAvg.toFixed(1) + "ms");
      $("#addAvg").html((addAvg / (addCount || 1)).toFixed(1) + "ms");
      $("#searchAvg").html((searchAvg / (searchCount || 1)).toFixed(1) + "ms");
    })
    .catch(function(error) {
      console.log(error);
    });
}

$(function() {
  getCount();
  statistics();
});
