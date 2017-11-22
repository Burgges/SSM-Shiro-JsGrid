<%--
  Created by IntelliJ IDEA.
  User: hand
  Date: 9/21/2017
  Time: 10:42 AM
  To change this template use File | Settings | File Templates.
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"
            +request.getServerName()+":"+request.getServerPort()
            +path+"/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <BASE href="<%=basePath%>">
    <title>Role Assignment</title>
    <link href="css/jsgrid.min.css" rel="stylesheet">
    <link href="css/jsgrid-theme.min.css" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jsgrid.min.js"></script>
    <script src="js/common.js"></script>
    <script src="js/userRoles.js"></script>
</head>
<body>
    <jsp:include page="commonHead.jsp" />
    <div class="page-body col-xs-10" id="main-body" style="width:100%; height: 600px; margin-top: 12px;">
        <ul style="line-height: 36px;list-style: none;border-bottom: 1px solid #eee; width: 80%; margin: 4px auto; padding-left: 0px;">
            <li style="margin-top: 5px;">
                <label for="searchContent">Search Content</label><br>
                <input style="width: 180px; margin-bottom: 20px; height: 35px;
                border-radius: 2px; border: 1px #e9e9e9 solid; text-indent: 5px;"
                       id="searchContent">
            </li>
            <li style="text-align: left; float: left; margin-top: -54px; margin-left: 30%;">
                <button style="margin-left: 0px;margin-bottom: 1px" type="button" class="btn btn-info" id="searchBtn">Search</button>
                <button style="margin-left: 50px;margin-bottom: 1px" type="button" class="btn btn-info" id="clearBtn">Clear</button>
            </li>
        </ul>
        <div id="userRoles" style="margin: 0px auto;"></div>
        <div style="clear: both"></div>
    </div>
    <div class="footer">
        <jsp:include page="commonFoot.jsp" />
    </div>

    <!-- 模态框（Modal） -->
    <div class="modal fade"  id="myModal"  tabindex="-1"  role="dialog"   aria-labelledby="myModalLabel"  aria-hidden="true"  style="width:100%;">
        <!-- /.modal start-->
        <div class="modal-dialog" style="position: relative; margin: 50px auto;">
            <!-- /.modal-content  start-->
            <div class="modal-content" style="width: 350px; height: 380px; border-radius: 6px;">
                <div class="modal-header" style="border-radius: 5px 5px 0px 0px;">
                    <button type="button" class="close"   data-dismiss="modal" aria-hidden="true" style="color: white;"> &times;  </button>
                    <h4 class="modal-title" id="myModalLabel" style="color: white;"> Role List </h4>
                </div>
                <div id="role-list" contentEditable="false" style="border:0px;  height:260px; width:350px; padding: 20px;">
                </div>
                <input type="hidden" id="hid-user-id">
                <div class="modal-footer" style="margin-top: 0px;">
                    <button type="button" class="btn btn-default"  data-dismiss="modal">Cancel </button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="chooseOkey()"> Okey </button>
                </div>
            </div>
            <!-- /.modal-content  end-->
        </div>
        <!-- /.modal end-->
    </div>
</body>
</html>
