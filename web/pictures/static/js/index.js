function statistics() {
  axios
    .get(config.api.statistics)
    .then(function(response) {
      let data = response.data;
      console.log(data);
      $('#accurateAddCount').html(data.accurateAddCount);
      $('#accurateAddAvgTime').html(data.accurateAddAvgTime);
      $('#accurateSearchAvgTime').html(data.accurateSearchAvgTime);
      $('#fastAddCount').html(data.fastAddCount);
      $('#fastAddAvgTime').html(data.fastAddAvgTime);
      $('#fastSearchAvgTime').html(data.fastSearchAvgTime);
      $('#latestAccurateAddCount').html(data.latestAccurateAddCount);
      $('#latestAccurateAddAvgTime').html(data.latestAccurateAddAvgTime);
      $('#latestAccurateSearchAvgTime').html(data.latestAccurateSearchAvgTime);
      $('#latestFastAddCount').html(data.latestFastAddCount);
      $('#latestFastAddAvgTime').html(data.latestFastAddAvgTime);
      $('#latestFastSearchAvgTime').html(data.latestFastSearchAvgTime);
    })
    .catch(function(error) {
      console.log(error);
    });
}

$(function() {
  statistics();
});
