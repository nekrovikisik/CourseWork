function fill_officeFromTo(param) {
    var region_inp = document.getElementById(`region${param}`).value;
    console.log(region_inp);
    $.ajax({
        url: '/getOfficesByRegion/' + region_inp,
        type: 'GET',
        dataType: 'json',

        success: function (officeDtoList) {
            var officeRow;
            $(`#office${param}`).html("");
            for (var i in officeDtoList) {
                officeRow = `<option value="${officeDtoList[i].fullOfficeName}">
                                           ${officeDtoList[i].fullOfficeName} 
                             </option>`;
                $(`#office${param}`).append(officeRow);
            }
        },
        error: function () {
            alert('Произошла ошибка, иди нахуй');
        }
    });
}