function checkUserExists(email, alertOK, alertBAD){
    var result;
    $.ajax({
        url: '/users/checkUserExists/' + email,
        type: 'GET',
        contentType: 'application/json; charset=utf-8',
        async: false,

        success: function (data, textStatus, xhr) {
            console.log(xhr);
            console.log(alertOK);
            result = true;
        },
        error: function (data, textStatus, xhr) {
            console.log(xhr);
            alert(alertBAD);
            result = false;
        }
    });
    return result;

}