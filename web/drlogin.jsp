<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE>
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.css">
    <link rel="stylesheet" href="bootstrapvalidator/css/bootstrapValidator.css">
    <script src="jquery/jquery-2.2.4.min.js" type="text/javascript"></script>
    <script src="bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- 表单验证 -->
    <script src="bootstrapvalidator/js/bootstrapValidator.js" type="text/javascript"></script>
    <title>登录界面</title>
    <script type="text/javascript">
        function _hyz() {
            /*
             1. 获取<img>元素
             2. 给它的src指向为/tools/VerifyCodeServlet
             */
            var img = document.getElementById("imgVerifyCode");
            // 需要给出一个参数，这个参数每次都不同，这样才能干掉浏览器缓存！
            img.src = "VerifyCodeServlet?a=" + new Date().getTime();
        }

        function login() {
            var name = $("input[name='username']").val();
            var password = $("input[name='password']").val();
            var verifyCode=$("input[name='verifyCode']").val();
            if(name==null||name==""){
                alert("用户名不能为空！");
                $('#username').focus();
                return false;
            }
            if(name.length>8||name.length<2){
                alert("用户名长度必须在2到8之间!");
                $('#username').focus();
                return false;
            }
            if(password==null||password==""){
                alert("密码不能为空！");
                $('#password').focus();
                return false;
            }
            if(password.length>30||password.length<6){
                alert("密码长度必须在6到30之间!");
                $('#password').focus();
                return false;
            }
            if(verifyCode==""||verifyCode==null){
                alert("验证码不能为空!");
                $('#input1').focus();
                return false;
            }
            if(verifyCode.length!=4){
                alert("验证码长度必须为四位!");
                $('#input1').focus();
                return false;
            }
            var reg =  /^[a-zA-Z0-9]+$/;
            if(!reg.test(verifyCode)){
                alert("验证码不合法, 请重新输入!");
                $('#input1').focus();
                return false;
            }
        }
        function loadmsg() {
            var msg = "<%=request.getAttribute("info")%>";
            if (msg != "null") {
                alert(msg);
            }
        }
    </script>
</head>
<%--onloadeddata="loadmsg()"--%>
<body onload="loadmsg()" background="Images/bg.jpg"
      style=" background-repeat:no-repeat ;
background-size:100% 100%;
background-attachment: fixed;">
<div class="container">
    <div class="row">
        <div class="col-sm-offset-3 col-sm-6 text-center">
            <h3>司机登录-快车系统</h3>
        </div>
    </div>
    <%
        String username = "";
        String password = "";
        //获取当前站点的所有Cookie
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {//对cookies中的数据进行遍历，找到用户名、密码的数据
            if ("username".equals(cookies[i].getName())) {
                username = cookies[i].getValue();
            } else if ("password".equals(cookies[i].getName())) {
                password = cookies[i].getValue();
            }
        }
    %>
    <form class="form-horizontal col-sm-offset-3" id="loginform" method="post" action="driverServlet?action=login" onsubmit="return login()">
        <div class="form-group">
            <label for="username" class="col-sm-2 control-label">用户名：</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" name="username" placeholder="请输入用户名" id="username" value="<%=username%>">
            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-2 control-label">密码：</label>
            <div class="col-sm-4">
                <input type="password" class="form-control" name="password" placeholder="请输入密码" id="password" value="<%=password%>">
            </div>
        </div>
        <div class="form-group">
            <label for="input1" class="col-sm-2 control-label">验证码：</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"  name="verifyCode" id="input1" placeholder="请输入验证码"/>
                <img src="VerifyCodeServlet" id="imgVerifyCode" class="form-control" style="width: 117px;"/>
                <a href="javascript:_hyz()">换一张</a>
            </div>
        </div>
        <div class="form-group has-error">
            <div class="col-sm-offset-2 col-sm-4 col-xs-6 ">
                <span class="text-warning"></span>
                <label for="rememberme"><input name="rememberme" type="checkbox" id="rememberme" value="1" style="cursor:pointer;" checked /> 记住密码</label>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-2 col-xs-6">
                <button class="btn btn-success btn-block" type="submit">登录</button>
            </div>
            <div class="col-sm-2  col-xs-6">
                <a class="btn btn-warning btn-block" href="/drregister.jsp">注册</a>
            </div>
        </div>
    </form>
</div>
</body>
</html>