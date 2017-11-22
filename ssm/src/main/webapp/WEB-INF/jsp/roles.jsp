<%--
  Created by IntelliJ IDEA.
  User: hand
  Date: 2017/7/19
  Time: 19:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"
            +request.getServerName()+":"+request.getServerPort()
            +path+"/";
%>
<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="IE=edge" >
    <BASE href="<%=basePath%>">
    <title>Roles</title>
    <link href="css/jsgrid.min.css" rel="stylesheet">
    <link href="css/jsgrid-theme.min.css" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

    <script src="js/jquery.min.js"></script>
    <script src="js/jsgrid.min.js"></script>
    <script src="js/common.js"></script>
    <script src="js/roles.js"></script>
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
    <div id="rolesList" style="margin: 0px auto;"></div>
    <div style="clear: both"></div>
</div>
<div class="footer">
    <jsp:include page="commonFoot.jsp" />
</div>
</body>
</html>
