/**
 * Created by huiyu.chen on 2017/7/6.
 *
 */
$(function(){
    initJsgridList();
    $("#rolesList").jsGrid({
        controller:{
            loadData: function(filter) {
                return $.ajax({
                    type: "GET",
                    url: "api/roles",
                    data: filter,
                    dataType: "json"
                });
            },
            insertItem: function(item) {
                var d = $.Deferred();
                return $.ajax({
                    type: "POST",
                    url: "api/roles",
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
                    url: "api/roles/" + item.roleId,
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
                var d = $.Deferred();
                item._method = "DELETE";
                return $.ajax({
                    type: "DELETE",
                    url: "api/roles/" + item.roleId,
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
        deleteConfirm: "Are you sure delete this role?",

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
            { name: "roleId", css: "hide"},
            { name: "roleName", title: "Role Name",  type: "text", validate: "required", valueField: "abbreviation", width:70 },
            { name: "roleDescription", title: "Description",  type: "text" },
            // { name: "authorityCode", title:"Authority Code", type:"text", validate:"required"},
            { name: "enabledFlag", title:"Enabled", type:"select",items:enabledFlag, valueField:"id",textField:"name", width: 65},
            { name: "creationDate", title: "Created On", type: "text", editing: false, inserting: false, width:70},
            { name: "lastUpdateDate", title: "Last Updated On", type: "text", editing: false, inserting: false, width:80},
            { type: "control",  editButton: true,  deleteButton: true, width:50 }
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
        "roleName" : searchContent
    };
    $("#rolesList").jsGrid("loadData", params);
}