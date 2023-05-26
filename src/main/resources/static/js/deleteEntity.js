function deleteEntity(pathVariable) {
    var entityName = window.location.pathname.split('/')[1];
    // var pathVariable = window.location.pathname.split('/')[3];
    console.log(pathVariable);
    $.ajax({
        url: `${entityName}/delete/${pathVariable}`,
        contentType: "application/json",
        type: 'DELETE',
        dataType: 'json',

        success: function (){
            alert("Удаление проведено успешно");
        },
        error: function (e) {
            console.log(e);
        }
    });
}