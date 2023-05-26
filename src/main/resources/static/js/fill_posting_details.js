var postingNumber = window.location.pathname.split('/')[3];
$.ajax({
    url: '/getPostingDto/' + postingNumber,
    type: 'GET',
    dataType: 'json',
    success: function(postingDto) {
        var postingDict = {};

        postingDict["postingNumber"] = '<td> ' + postingDto.postingNumber + '</td>';
        postingDict["tariffName"] = '<td> ' + postingDto.tariffName + '</td>';
        postingDict["senderEmail"] = '<td> ' + postingDto.senderEmail + '</td>';
        postingDict["senderName"] = '<td> ' + postingDto.senderName + '</td>';
        postingDict["receiverEmail"] = '<td> ' + postingDto.receiverEmail + '</td>';
        postingDict["receiverName"] = '<td> ' + postingDto.receiverName + '</td>';
        postingDict["officeFromName"] = '<td> ' + postingDto.officeFromName + '</td>';
        postingDict["officeFromID"] = '<td> ' + postingDto.officeFromID + '</td>';
        postingDict["officeToName"] = '<td> ' + postingDto.officeToName + '</td>';
        postingDict["officeToID"] = '<td> ' + postingDto.officeToID + '</td>';
        postingDict["createdAt"] = '<td> ' + moment(postingDto.createdAt).format('LLLL') + '</td>';
        var tagName;
        for(var key in postingDict) {
            tagName = '#'+key
            $(tagName).append(postingDict[key]);
        }
    },
    error: function() {
        alert('Error getting posting details');
    }
});
