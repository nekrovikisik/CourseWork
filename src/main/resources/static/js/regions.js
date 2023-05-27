$.ajax({
    url: '/getRegionList',
    type: 'GET',
    dataType: 'json',
    success: function (regionList) {
        var regionRow;
        for (var i in regionList) {
            regionRow = `<option> ${regionList[i]} </option>`;
            if (window.location.pathname.split('/')[1]==='offices') {
                $('#region_filter').append(regionRow);
            }
            else{
                $('#regionFrom').append(regionRow);
                $('#regionTo').append(regionRow);
            }
        }
    },
    error: function () {
        alert('Произошла ошибка');
    }
});
