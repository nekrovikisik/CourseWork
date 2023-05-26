$.ajax({
    url: '/getRegionList',
    type: 'GET',
    dataType: 'json',
    success: function (regionList) {
        var regionRow;
        for (var i in regionList) {
            regionRow = `<option> ${regionList[i]} </option>`;
            $('#region_filter').append(regionRow);
        }
    },
    error: function () {
        alert('Произошла ошибка, иди нахуй');
    }
});
