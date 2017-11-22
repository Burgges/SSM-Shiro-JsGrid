$(document).ready(function() {
    $("#img-captcha").click(function (){
        console.log("switch captcha");
        $("#img-captcha").attr("src",getCaptcha());
    });

    $("#btn-submit").click(function (){
        var finalRes = $('#login_form').serializeArray().reduce(function(result, item){
            result[item.name] = item.value;
            return result;
        }, {});
        console.log(finalRes);
        $.ajax({
            type : "POST",
            url : "login",
            data : finalRes,
            async : true,
            cache : false,
            success : function (response) {
                $("#errorMessage").text("");
                console.log(response);
                if (response.message == "userIndex") {
                    window.location.href = "api/users/index";
                } else {
                    $("#p").val("");
                    $("#c").val("");
                    $("#img-captcha").attr("src",getCaptcha());
                    $("#errorMessage").text(response.message);
                }
            }

        });
    });
});

/**
 * get captcha
 * @returns {string}
 */
function getCaptcha() {
	return "getCaptcha?time="+new Date().getTime();
}