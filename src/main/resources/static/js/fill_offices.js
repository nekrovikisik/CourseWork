function fill_offices() {
    var region_inp = document.getElementById("region_filter").value;
    console.log(region_inp);
    if (region_inp === null) {
        region_inp = 'Все регионы России';
    }
    $.ajax({
        url: '/getOfficesByRegion/' + region_inp,
        type: 'GET',
        dataType: 'json',

        success: function (officeDtoList) {
            var officeRow;
            map.eachLayer(function (layer) {
                map.removeLayer(layer);
            });
            map.setView([officeDtoList[0].latitude, officeDtoList[0].longitude], 7);
            L.tileLayer('https://{s}.tile.osm.org/{z}/{x}/{y}.png', {
                attribution: '&copy; <a href="https://osm.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(map);
            $("#officesTbody").html("");
            for (var i in officeDtoList) {
                L.marker([officeDtoList[i].latitude, officeDtoList[i].longitude]).addTo(map).bindPopup(officeDtoList[i].fullOfficeName);

                officeRow = '<tr>';
                officeRow += `<td> ${officeDtoList[i].region} </td>`;
                officeRow += `<td> ${officeDtoList[i].municipality} </td>`;
                officeRow += `<td> ${officeDtoList[i].settlement} </td>`;
                officeRow += '</tr>';
                $('#officesTbody').append(officeRow);
            }
        },
        error: function () {
            alert('Произошла ошибка, иди нахуй');
        }
    });
}