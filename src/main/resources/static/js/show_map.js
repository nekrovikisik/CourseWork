var postingNumber = window.location.pathname.split('/')[3];
$.ajax({
    url: '/getPostingDto/' + postingNumber,
    type: 'GET',
    dataType: 'json',
    success: function(postingDto) {

        var officeNameFrom, lonFrom, latFrom;
        officeNameFrom = postingDto.officeFromName;
        lonFrom = postingDto.officeFromLon;
        latFrom = postingDto.officeFromLat;

        var officeNameTo, lonTo, latTo;
        officeNameTo = postingDto.officeToName;
        lonTo = postingDto.officeToLon;
        latTo = postingDto.officeToLat;

        const map = L.map('map').setView([(latFrom+latTo)/2, (lonFrom+lonTo)/2], 5);
        L.tileLayer('https://{s}.tile.osm.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="https://osm.org/copyright">OpenStreetMap</a> contributors'
        }).addTo(map);

        L.marker([latFrom, lonFrom]).addTo(map).bindPopup(officeNameFrom);
        L.marker([latTo, lonTo]).addTo(map).bindPopup(officeNameTo);

    },
    error: function() {
        alert('Error getting posting details');
    }
});
