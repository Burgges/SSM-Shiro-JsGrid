/**
 *
 * Created by huiyu.chen on 2017/7/17.
 *
 */
$(function(){

    $('.form_datetime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii'
    });

    initJsgridList();
    $("#logsList").jsGrid({
        controller:{
            loadData: function(filter) {
                return $.ajax({
                    type: "GET",
                    url: "api/logs",
                    data: filter,
                    dataType: "json"
                });
            },
            deleteItem: function(item) {
                console.log(item.userId+"_delete_"+item);
                var d = $.Deferred();
                item._method = "DELETE";
                return $.ajax({
                    type: "DELETE",
                    url: "api/logs/" + item.logId,
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
        onItemDeleting: function(args) {
            previousItem = args.previousItem;
            grid = args.grid;
        },

        width: "80%",
        height : "auto",

        confirmDeleting: true,
        deleteConfirm: "Are you sure delete this log?",

        heading: true,
        inserting: false,
        editing: false,
        sorting: false,
        deleting: true,
        paging: true,
        pageLoading: false,
        autoload: true,

        pagerContainer: null,
        pageIndex: 1,
        pageSize: 5,
        pageButtonCount: 15,
        pagerFormat: "Pages: {first} {prev} {pages} {next} {last}    {pageIndex} of {pageCount}",
        pagePrevText: "Prev",
        pageNextText: "Next",
        pageFirstText: "First",
        pageLastText: "Last",
        pageNavigatorNextText: "...",
        pageNavigatorPrevText: "...",

        fields: [
            { name: "logId", css: "hide"},
            { name: "userName", title: "User Name",  type: "text", editing: false, inserting: false, width: 32},
            { name: "fun", title:"Fun", type:"text", editing: false, inserting: false, width:80, css:"jsgrid-cell-params"},
            { name: "logDescription", title: "Log Description", type: "text", editing: false, inserting: false, width:55},
            { name: "params", title: "Params", type: "text", editing: false, inserting: false, css:"jsgrid-cell-params"},
            { name: "ipAddress", title: "IP Address", type: "text", editing: false, inserting: false, width:35},
            { name: "operationTimes", title: "Operation Time(MS)", type: "text", editing: false, inserting: false, width:40},
            { name: "operationTime", title: "Creation Date", type: "text", editing: false, inserting: false, width:62},
            { type: "control",  editButton: false,  deleteButton: true, width:20 }
        ]
    });

});


$(document).ready(function() {
    $("#searchBtn").click(function() {
        initJsgridList();
    });
    $("#clearBtn").click(function() {
        $("#searchContent").val("");
        $('#operationTimeStart').val("");
        $('#operationTimeEnd').val("");
    });
});

function initJsgridList() {
    var searchContent = $("#searchContent").val();
    var operationTimeStart = $('#operationTimeStart').val();
    var operationTimeEnd = $('#operationTimeEnd').val();
    var params = {
        "userName" : searchContent,
        "operationTimeStart" : operationTimeStart,
        "operationTimeEnd" : operationTimeEnd
    };
    $("#logsList").jsGrid("loadData", params);
}