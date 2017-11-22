<%--
  Created by IntelliJ IDEA.
  User: hand
  Date: 2017/7/10
  Time: 15:43
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
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/login.js"></script>
    <link href="css/login.css" rel="stylesheet" type="text/css" />
    <title>Login</title>
</head>
<body>
<h1><sup></sup></h1>

<div class="login" style="margin-top:50px;">

    <div class="web_qr_login" id="web_qr_login" style="display: block; height: 355px;">
        <div id="errorMessage"
             style="width: 100%; height: 30px; line-height: 30px; color: red; text-align: center;">
            </div>
        <!--login start-->
        <div class="web_login" id="web_login">
            <div class="login-box">
                <div class="login_form">
                    <form accept-charset="utf-8" id="login_form" class="loginForm" method="post">
                        <div class="uinArea" id="uinArea">
                            <label class="input-tips" for="u">Username：</label><br>
                            <input type="text" id="u" name="userName" required="required" class="inputstyle"
                                   placeholder="Please enter your user name"/>
                        </div>
                        <div class="pwdArea" id="pwdArea">
                            <label class="input-tips" for="p">Password：</label><br>
                            <input type="password" id="p" name="password" required="required" class="inputstyle"
                                   placeholder="Please enter your password"/>
                        </div>
                        <div class="pwdArea" id="captchaArea">
                            <label class="input-tips" for="p">Captcha：</label><br>
                            <input type="text" id="c" name="captchaCode" required="required" class="inputstyle"
                                   placeholder="Please enter captcha"/>
                            <div id="div_img">
                                <img id="img-captcha" src="getCaptcha"
                                     style="width: 100%; height: 100%; border-radius: 2px; cursor:pointer;" />
                            </div>
                        </div>

                        <div style="padding-left:50px;margin-top: 30px; background-color: #f9f9f9; height: 100px;
                        width: 320px; margin-left: -20px;">
                            <input type="button" value="Login" style="width:150px;" class="button_blue" id="btn-submit"/>
                        </div>
                    </form>
                </div>

            </div>


        </div>
        <!--login end-->
    </div>
</div>
<div class="access">© 2017 Hand 上海汉得信息技术股份有限公司</div>
</body>
</html>
