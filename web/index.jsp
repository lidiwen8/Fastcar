<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2018/9/18
  Time: 10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.css">
<link rel="stylesheet" href="bootstrapvalidator/css/bootstrapValidator.css">
<script src="jquery/jquery-2.2.4.min.js" type="text/javascript"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
<!-- 表单验证 -->
<script src="bootstrapvalidator/js/bootstrapValidator.js" type="text/javascript"></script>
  <head>
    <title>主页面</title>
    <script type="text/javascript">
        $(function(){
            $("#1").click(function(){
                window.location.href='drlogin.jsp';
            });
            $("#2").click(function(){
                window.location.href='palogin.jsp';
            });
            $("#3").click(function(){
                window.location.href='login.jsp';
            });
        })
    </script>
  </head>
  <body>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-2 col-xs-6">
      <button class="btn btn-success" type="submit" id="1">司机业务</button>
    </div>
    <div class="col-sm-offset-2 col-sm-2 col-xs-6">
      <button class="btn btn-primary" type="submit" id="2">乘客业务</button>
    </div>
    <div class="col-sm-offset-2 col-sm-2 col-xs-6">
      <button class="btn btn-info" type="submit" id="3">平台管理员</button>
    </div>
  </div>

  </body>
</html>
