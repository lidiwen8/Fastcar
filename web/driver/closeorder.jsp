<%--
  Created by IntelliJ IDEA.
  User: 16320
  Date: 2018/11/3
  Time: 22:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/bootstrap-theme.min.css" rel="stylesheet">
    <title>当前结束的订单</title>
</head>
<body>
<nav class="navbar navbar-inverse">
    <p class="navbar-text">结束订单信息</p>
</nav>
<div class="container">
    <h2 class="sub-header">当前结束的订单</h2><br>
    <div class="table-responsive">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>订单id</th>
                <th>乘客</th>
                <th>起始点</th>
                <th>终点</th>
                <th>乘客联系方式</th>
                <th>叫车类型</th>
                <th>订单创建日期</th>
                <c:if test="${not empty order.endtime}">
                <th>订单结束日期</th>
                </c:if>
                <th>订单状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${order.orderid}</td>
                <td>${order.username}</td>
                <td>${order.origin}</td>
                <td>${order.destination}</td>
                <td>${order.passengernumber}</td>
                <td>${order.taximode}</td>
                <td>${order.createtime}</td>
                <c:if test="${not empty order.endtime}">
                <td>${order.endtime}</td>
                </c:if>
                <td>${order.statesmean}</td>
                <td>
                    <button class="btn btn-primary btn-sm edit_btn" onclick="javascript:window.location.href='driver/index.jsp'"><span class="glyphicon glyphicon-pencil">返回首页</span></button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</body>
</html>
