<%--
  Created by IntelliJ IDEA.
  User: 16320
  Date: 2018/11/3
  Time: 20:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/bootstrap-theme.min.css" rel="stylesheet">
    <script src="/jquery/jquery-2.2.4.min.js" type="text/javascript"></script>
    <script>
        function closeorder(orderid) {
            if (confirm("确认乘客已到达目的地，申请结束订单吗？")) {
                $.ajax({
                    url: "orderServlet?action=closeorderByid",
                    type: "post",
                    async: "true",
                    data: {"action": "closeorderByid", "id": orderid},
                    dataType: "json",
                    success: function (data) {
                        if (data.res == 1) {
                            alert(data.info);
                            window.location.replace("orderServlet?action=closeorder&id="+orderid);
                        }
                        else {
                            alert(data.info);
                        }
                    }
                });
            };
        }
        function deleteOrderByid(orderid) {
            if (confirm("确认取消当前订单吗？")) {
                $.ajax({
                    url: "orderServlet?action=driverdeleteOrderByid",
                    type: "post",
                    async: "true",
                    data: {"action": "driverdeleteOrderByid", "id": orderid},
                    dataType: "json",
                    success: function (data) {
                        if (data.res == 1) {
                            alert(data.info);
                            window.location.href='driver/index.jsp';
                        }
                        else {
                            alert(data.info);
                        }
                    }
                });
            };
        }
    </script>
    <title>当前正在进行的订单</title>
</head>
<body>
<nav class="navbar navbar-inverse">
    <p class="navbar-text">订单信息</p>
</nav>
<div class="container">
    <h2 class="sub-header">当前正在进行的订单</h2><br>
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
                <th>订单状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${order1.orderid}</td>
                <td>${order1.username}</td>
                <td>${order1.origin}</td>
                <td>${order1.destination}</td>
                <td>${order1.passengernumber}</td>
                <td>${order1.taximode}</td>
                <td>${order1.createtime}</td>
                <td>${order1.statesmean}</td>
                <td>
                    <button class="btn btn-success" onclick="javascript:window.location.href='orderServlet?action=findOrder&id=${order1.orderid}'"><span class="glyphicon glyphicon-pencil">详情</span></button>
                    <button class="btn btn-primary btn-sm edit_btn" onclick="closeorder(${order1.orderid})"><span class="glyphicon glyphicon-pencil">确认乘客已到达目的地</span></button>
                    <button class="btn btn-danger btn-sm delete_btn" onclick="deleteOrderByid(${order1.orderid})"><span class="glyphicon glyphicon-trash">取消订单</span></button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
