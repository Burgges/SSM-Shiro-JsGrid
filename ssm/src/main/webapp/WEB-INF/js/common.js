/**
 * Created by huiyu.chen on 2017/7/18.
 */
$(document).ready(function(){

    // getMenu();

    $("#logOut-li").click(function(){
        window.location.href = "api/logout";
    });
    $("#homePage-li").click(function(){
        window.location.href = "#";
    });
    $("#users-li").click(function(){
        window.location.href = "api/users/index";
    });
    $("#userRole-li").click(function(){
        window.location.href = "api/toRoles";
    });
    $("#lookup-li").click(function(){
        window.location.href = "api/toLookup";
    });
    $("#logs-li").click(function(){
        window.location.href = "api/logs/page";
    });
    $("#role-assign-li").click(function(){
        window.location.href = "api/userRoles/page";
    });
    $("#permission-li").click(function(){
        window.location.href = "api/permissions/page";
    });
    $("#permission-assign-li").click(function(){
        window.location.href = "api/rolePermissions/page";
    });

    $("#loginUser-li").click(function(){
        window.location.href = "#";
    });
});

/**
 * get menu
 */
function getMenu() {
    $.ajax({
        type : "GET",
        url : "api/getMenu",
        async : false,
        cache : false,
        success : function (response) {
            if(response != "error"){
                $("#menu-ul").prepend(response);
            }
        },
        error : function (errorData) {
            console.log(errorData);
            window.location.href = "api/logout";
        }
    });
}

/**
 * get user access
 */
function getAccess() {
    $.ajax({
        type : "GET",
        url : "api/getAccess",
        async : true,
        cache : false,
        success : function (response) {
            if(response == "error"){

            } else{
                $(".jsgrid-grid-body").find(".jsgrid-table")
                    .find("td.jsgrid-cell.jsgrid-control-field.jsgrid-align-center")
                    .find("input")
                    .attr("disabled",true);
                $("input.jsgrid-button.jsgrid-mode-button.jsgrid-insert-mode-button.jsgrid-mode-on-button")
                    .attr("disabled",true);

                /*$(".jsgrid-grid-body").find(".jsgrid-table")
                    .find("td.jsgrid-cell.jsgrid-control-field.jsgrid-align-center")
                    .find("input")
                    .css("display","none");
                $("input.jsgrid-button.jsgrid-mode-button.jsgrid-insert-mode-button.jsgrid-mode-on-button")
                    .css("display","none");*/
            }
        },
        error : function (errorData) {
            // console.log(errorData);
            // window.location.href = "api/logout";
        }
    });
}