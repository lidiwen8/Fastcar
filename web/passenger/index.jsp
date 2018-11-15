<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2018/9/18
  Time: 10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/bootstrap-3.3.7-dist/css/bootstrap.css">
<link rel="stylesheet" href="/bootstrapvalidator/css/bootstrapValidator.css">
<script src="/jquery/jquery-2.2.4.min.js" type="text/javascript"></script>
<script src="/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
<!-- 表单验证 -->
<script src="/bootstrapvalidator/js/bootstrapValidator.js" type="text/javascript"></script>
  <head>
    <title>主页面</title>
    <base href="<%=basePath%>">
    <script type="text/javascript">
        $(function(){
            $("#1").click(function(){
                window.location.href='passenger/createOrder.jsp';
            });
            $("#2").click(function(){
                window.location.href='orderServlet?action=findcloseOrder';
            });
            $("#3").click(function(){
                window.location.href='passengerServlet?action=logout';
            });
            $("#4").click(function(){
                window.location.href='orderServlet?action=currentorder';
            });
        })
        function loadmsg() {
            var msg ="<%=request.getAttribute("info")%>";
            if (msg != "null") {
                alert(msg);
            }
        }
    </script>
  </head>
  <body onload="loadmsg()">
  <form class="form-horizontal col-sm-offset-3" id="loginform" method="post">
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-2 col-xs-6">
      <button class="btn btn-success" type="button" id="1">创建新订单</button>
    </div>
  </div>
    <div class="form-group">
      <div class="col-sm-offset-2 col-sm-2 col-xs-6">
        <button class="btn btn-primary" type="button" id="4">查看进行中的订单</button>
      </div>
    </div>
    <div class="form-group">
      <div class="col-sm-offset-2 col-sm-2 col-xs-6">
        <button class="btn btn-primary" type="button" id="2">查看已结束订单</button>
      </div>
    </div>
    <div class="form-group">
      <div class="col-sm-offset-2 col-sm-2 col-xs-6">
        <button class="btn btn-warning" type="button" id="3">退出</button>
      </div>
    </div>
  </form>
  </body>
</html>
