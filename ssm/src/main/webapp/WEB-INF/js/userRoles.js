/**
 * Created by huiyu.chen on 2017/7/6.
 *
 */
$(function(){
    initJsgridList();
    $("#userRoles").jsGrid({
        controller:{
            loadData: function(filter) {
                return $.ajax({
                    type: "GET",
                    url: "api/userRoles",
                    data: filter,
                    dataType: "json"
                });
            },
            insertItem: function(item) {
                var d = $.Deferred();
                return $.ajax({
                    type: "POST",
                    url: "api/userRoles",
                    data: item,
                    dataType: "json"
                }).done(function(response) {
                    if(500 == response.code){
                        alert("Insert failure,"+response.message);
                    }else{
                        alert(response.message);
                    }
                    d.resolve(response);
                    grid.loadData();
                }).fail(function() {
                    grid.loadData();
                    d.resolve(previousItem);
                });
            },
            updateItem: function(item) {
                var d = $.Deferred();
                item._method = "PUT";
                return $.ajax({
                    type: "POST",
                    url: "api/userRoles/" + item.roleId,
                    data: item,
                    dataType: "json"
                }).done(function(response) {
                    console.log(response);
                    d.resolve(response);
                    if(500 == response.code){
                        alert("Update failure,"+response.message);
                    }else{
                        alert(response.message);
                    }
                    grid.loadData();
                }).fail(function() {
                    grid.loadData();
                    d.resolve(previousItem);
                });
            },
        },
        onItemInserting: function(args) {
            previousItem = args.previousItem;
            grid = args.grid;
        },

        onItemUpdating: function(args) {
            previousItem = args.previousItem;
            grid = args.grid;
        },

        onItemDeleting: function(args) {
            previousItem = args.previousItem;
            grid = args.grid;
        },

        width: "80%",
        height : "auto",

        confirmDeleting: true,
        deleteConfirm: "Are you sure delete this role?",

        inserting: false,
        editing: false,
        sorting: false,
        deleting: false,
        paging: true,
        autoload: true,

        pagerContainer: null,
        pageIndex: 1,
        pageSize: 10,
        pageButtonCount: 15,
        pagerFormat: "Pages: {first} {prev} {pages} {next} {last}    {pageIndex} of {pageCount}",
        pagePrevText: "Prev",
        pageNextText: "Next",
        pageFirstText: "First",
        pageLastText: "Last",
        pageNavigatorNextText: "...",
        pageNavigatorPrevText: "...",

        fields: [
            { name: "roleId", css: "hide"},
            { name: "userId", css: "hide"},
            { name: "userName", title: "User Name",  type: "text", editing: false, valueField: "abbreviation", width: 40 },
            { name: "userMail", title: "User Mail",  type: "text", editing: false, width: 70 },
            { name: "enabledFlag", title:"Enabled", type:"select",items:enabledFlag, editing: false, valueField:"id",textField:"name", width: 30},
            { name: "roles", title: "Role List", type: "text", editing: false, inserting: false,
                itemTemplate: function (value, item) {
                    var roleList = "";
                    if(item.roleList.length > 0 && item.roleList[0].roleId != null){
                        for(var i = 0; i < item.roleList.length; i++){
                            roleList = item.roleList[i].roleName + "," + roleList;
                        }
                        roleList = roleList.substring(0,roleList.length-1);
                    }else {
                        roleList = "Unassigned role!";
                    }
                    return roleList;
                }},
            { name: "role", title: "Role Assign", type: "text", editing: false, inserting: false, width: 40,
                itemTemplate: function (value, item) {
                    var roleList = "<button class='btn btn-info'  data-toggle='modal' onclick='chooseRole("+item.userId+")'  data-target='#myModal' >选择角色</button>";
                    return roleList;
                }}
        ]
    });

});


var enabledFlag = [
    {name:"Y", id: true},
    {name:"N", id: false}
];

$(document).ready(function() {
    $("#searchBtn").click(function() {
        initJsgridList();
    });
    $("#clearBtn").click(function() {
        $("#searchContent").val("");
    });

});

/**
 * role data init
 */
function initJsgridList() {
    var searchContent = $("#searchContent").val();
    var params = {
        "roleName" : searchContent,
        "userName" : searchContent
    };
    $("#userRoles").jsGrid("loadData", params);
}

//选择角色
function chooseRole(userId) {
    $("#hid-user-id").val(userId);
    var roleArr = findUserRoles(userId);
    $.ajax({
        type: "GET",
        url: "api/roles",
        async: true,
        success: function(response) {

            if (response != null) {
                var roleStr = "<p><input type='checkbox' class='all-role' id='all-role' name='roleName' style='cursor: pointer' >  全选</p>";
                for (var i = 0; i < response.length; i++) {
                    roleStr += "<p><input type='checkbox' id='"+response[i].roleId+"' class='role' " +
                        "name='"+response[i].roleName+"' value='"+response[i].roleId+"' style='cursor: pointer'>  "+response[i].roleName+"</p>";
                    if(roleArr.indexOf(response[i].roleId) != -1){
                        $("#"+response[i].roleId).prop("checked",true);
                    }
                }
                $("#role-list").html(roleStr);
            }

            $("#all-role").click(function () {
                if ($("#all-role").prop("checked") != true) {
                    $(".role").prop("checked",false);
                } else {
                    $(".role").prop("checked",true);
                }
            });

            // $(".role").click(function () {
            //     if () {
            //
            //     }
            // });
        }
    });
}

//给用户分配权限
function chooseOkey() {
    var allRoles = $(".role");
    var roleIdStr = "";
    for(var i = 0; i < allRoles.length; i++){
        if(allRoles[i].checked == true){
            roleIdStr += allRoles[i].value +",";
        }
    }
    console.log(roleIdStr);
    $.ajax({
        type: "POST",
        url: "api/userRoles",
        data: {
            roleIdStr : roleIdStr,
            userId : $("#hid-user-id").val()
        },
        async: false,
        success: function(response){
            console.log(response);
            initJsgridList();
            $(".role").prop("checked",false);
        }
    });
}

/**
 * get role by user id
 * @param userId
 * @returns {Array}
 */
function findUserRoles(userId) {
    var roleArr = [];
    $.ajax({
        type: "GET",
        url: "api/userRoles/"+userId,
        async: false,
        success: function(response){
            console.log(response);
            if(response[0].roleList.length > 0 && response[0].roleList[0].roleId != null){
                for(var i = 0; i < response[0].roleList.length; i++){
                    roleArr.push(response[0].roleList[i].roleId);
                }
            }
        }
    });
    return roleArr;
}