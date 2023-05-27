function fill_postings_by_user(postingDtoList, param, tag){
    var postingRow;
    if (postingDtoList != undefined && postingDtoList.length>0 ) {
        for (var i in postingDtoList) {
            console.log(postingDtoList[i].postingNumber);
            postingRow = '<tr>';
            postingRow += `<td> <a href="/postings/show/${postingDtoList[i].postingNumber}" target="_blank">
                                  ${postingDtoList[i].postingNumber}</a>
                            </td>`;
            postingRow += `<td> ${postingDtoList[i].tariffName} </td>`;
            if (param === 'sender') {
                postingRow += `<td> ${postingDtoList[i].receiverName} </td>`;
            } else {
                postingRow += `<td> ${postingDtoList[i].senderName} </td>`;
            }
            postingRow += `<td> ${postingDtoList[i].officeFromName} </td>`;
            postingRow += `<td> ${postingDtoList[i].officeToName} </td>`;
            postingRow += `<td> ${postingDtoList[i].status} </td>`;
            postingRow += `<td> ${moment(postingDtoList[i].createdAt).format('Do MMM YYYY')} </td>`;
            postingRow += `<td> 
                            <a href="/postings/delete/${postingDtoList[i].postingNumber}" class = "btn btn-danger">Delete</a> </td>`;
            postingRow += '</tr>';
            $(`#${tag}`).append(postingRow);
        }
    }
    else{
        $(`${tag}`).append('<td colspan="3">Нет данных</td>');
    }
}