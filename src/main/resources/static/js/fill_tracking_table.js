var postingNumber = window.location.pathname.split('/')[3];
$.ajax({
    url: '/getPostingEvents/' + postingNumber,
    type: 'GET',
    dataType: 'json',
    success: function(postingEventDtoList) {
        var eventRow;
        for (var i in postingEventDtoList) {
            console.log(postingEventDtoList[i].postingNumber);
            eventRow = '<tr class="active">';
            eventRow += `<td class="track_dot">
                                <span class="track_line"></span>
                           </td>`;
            eventRow += `<td> ${((+i+1) > 9) ? (+i+1) : "0" + (+i+1).toString()} </td>`;
            eventRow += `<td> ${postingEventDtoList[i].status} </td>`;
            eventRow += `<td> ${moment(postingEventDtoList[i].createdAt).format('LLLL')} </td>`;
            eventRow += '</tr>';
            $('#eventsTbody').append(eventRow);

        }
    },
    error: function() {
        alert('Произошла ошибка, иди нахуй');
    }
});
