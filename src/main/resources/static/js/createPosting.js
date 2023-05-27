function createPosting() {
    var tariffName = document.getElementById("tariffName").value;

    console.log(`tariffName=${tariffName}`);
    var senderEmail = document.getElementById("SenderEmail").value;
    console.log(`senderEmail=${senderEmail}`);
    var receiverEmail = document.getElementById("ReceiverEmail").value;

    console.log(`receiverEmail=${receiverEmail}`);
    var officeFrom = document.getElementById("officeFrom").value;
    var officeTo = document.getElementById("officeTo").value;

    console.log(`officeTo=${officeTo}`);

    var isSenderOk = checkUserExists(senderEmail, 'Отправитель в порядке', 'Введен неверный отправитель.');
    var isReceiverOk = checkUserExists(receiverEmail, 'Получатель в порядке', 'Введен неверный получатель.');

    console.log('isSenderOk'+isSenderOk);
    console.log('isReceiverOk'+isReceiverOk);
    if (isSenderOk && isReceiverOk) {
        $.ajax({
            url: '/create-posting',
            type: 'POST',
            contentType: 'application/json',
            async: false,
            data: JSON.stringify({
                    tariffName: tariffName,
                    senderEmail: senderEmail,
                    receiverEmail: receiverEmail,
                    officeFrom: officeFrom,
                    officeTo: officeTo
                }
            ),
            success: function () {
                console.log('ура победа');
            },
            error: function (data, textStatus, xhr) {
                console.log(xhr);
                alert('Введен неверный email. Проверьте, что получатель зарегистрирован');
                receiverEmail = null;
            }
        });
    }
    {
        alert("Проверьте корректность заполненных полей")
    }


}