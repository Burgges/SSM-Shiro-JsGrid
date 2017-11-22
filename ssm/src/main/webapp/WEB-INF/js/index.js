/**
 * Created by huiyu.chen on 2017/7/6.
 *
 */

var visible = [
    {name:"W", id:"W"},
    {name:"M", id:"M"}
];

var enabledFlag = [
    {name:"Y", id: true},
    {name:"N", id: false}
];

var roles = [];

$(function(){
    // getRoleList();
    initJsgridList();
    $("#userList").jsGrid({
        controller:{
            loadData: function(filter) {
                return $.ajax({
                    type: "GET",
                    url: "api/users",
                    data: filter,
                    dataType: "json"
                });
            },
            insertItem: function(item) {
                console.log(item.userId+"_insert_"+item);
                var d = $.Deferred();
                return $.ajax({
                    type: "POST",
                    url: "api/users",
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
                console.log(item.userId+"_update_"+item);
                var d = $.Deferred();
                item._method = "PUT";
                return $.ajax({
                    type: "POST",
                    url: "api/users/" + item.userId,
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
            deleteItem: function(item) {
                console.log(item.userId+"_delete_"+item);
                var d = $.Deferred();
                item._method = "DELETE";
                return $.ajax({
                    type: "DELETE",
                    url: "api/users/" + item.userId,
                    data: item,
                    dataType: "json"
                }).done(function(response) {
                    d.resolve(response);
                    grid.loadData();
                }).fail(function() {
                    grid.loadData();
                    d.resolve(previousItem);
                });
            }
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
        deleteConfirm: "Are you sure delete this user?",

        inserting: true,
        editing: true,
        sorting: false,
        deleting: true,
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
            { name: "userId", css: "hide"},
            { name: "userName", title: "User Name",  type: "text", validate: "required", valueField: "abbreviation", width:70 },
            { name: "password", title: "Password", css: "hide", type: "text",  value: 123456 },
            { name: "userSex", title:"Sex", type:"select",items:visible, validate:"required", valueField:"name",textField:"name", width: 35},
            { name: "userMail", title: "Mail", type: "text"},
            // { name: "roleId", title:"User Role", type:"select",items:roles, valueField:"id",textField:"name", width: 60},
            { name: "enabledFlag", title:"Enabled", type:"select", items:enabledFlag, validate:"required", valueField:"id",textField:"name", width: 50},
            { name: "creationDate", title: "Created On", type: "text", editing: false, inserting: false, width:70},
            { name: "lastUpdateDate", title: "Last Updated On", type: "text", editing: false, inserting: false, width:80},
            { type: "control",  editButton: true,  deleteButton: true, width:50 }
        ]
    });

});

$(document).ready(function() {
    $("#searchBtn").click(function() {
        var pathStr = "";
        pathStr = $("#fileupload").val();
        $.ajax({
            type : "DELETE",
            url : "api/testFile/"+pathStr,
            async : false,
            success : function(response){
                console.log(response);
            }

        });

        // initJsgridList();
    });
    $("#clearBtn").click(function() {
        $("#searchContent").val("");
    });

/*    $('#fileupload').fileupload({
        dataType: 'json',
        add: function (e, data) {
            data.context = $('<button/>').text('Upload')
                .appendTo(document.body)
                .click(function () {
                    $(this).replaceWith($('<p/>').text('Uploading...'));
                    data.submit();
                });
        },
        done: function (e, data) {
            data.context.text('Upload finished.');
        }
    });*/

});

/**
 * 初始化用户列表
 */
function initJsgridList() {
    var searchContent = $("#searchContent").val();
    var params = {
        "userName" : searchContent,
        "userSex" : searchContent,
        "userMail" : searchContent
    };
    $("#userList").jsGrid("loadData", params);
}

/**
 * Select user role list
 */
function getRoleList () {
    $.ajax({
        type : "GET",
        url : "api/roles",
        data : {},
        async : false,
        cache : false,
        success : function (response) {
            console.log(response);
            if (response != null) {
                for (var i = 0; i < response.length; i++) {
                    roles.push(new dataToJson(response[i]));
                }
            }
        }
    });
}

/**
 * role data format
 * @param data
 */
function dataToJson(data) {
    this.id = data.roleId;
    this.name = data.roleName;
}
