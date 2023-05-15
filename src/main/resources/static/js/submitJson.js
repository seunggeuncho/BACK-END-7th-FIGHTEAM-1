function submitJson(){
    var charge = $('select[name="charge-amount"]').val();
    console.log(charge)
    var json = {
        "charge" : charge
    };
    console.log(json);
    $.ajax({
        type: "POST",
        url: "/charge/pay",
        data: JSON.stringify(json),
        contentType: "application/json",
        success: function(response) {
            location.href = response.next_redirect_pc_url;
            },
        error: function() {
            alert("충전에 실패했습니다.");
        }
    });
}
