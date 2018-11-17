<%--
  Created by IntelliJ IDEA.
  User: 16320
  Date: 2018/11/3
  Time: 16:43
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
        function deleteOrderByid(orderid) {
            if (confirm("确认取消当前订单吗？")) {
                $.ajax({
                    url: "orderServlet?action=deleteOrderByid",
                    type: "post",
                    async: "true",
                    data: {"action": "deleteOrderByid", "id": orderid},
                    dataType: "json",
                    success: function (data) {
                        if (data.res == 1) {
                            alert(data.info);
                            window.location.replace("passenger/index.jsp");
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
                <c:if test="${not empty order.drviernumber}">
                    <th>司机联系方式</th>
                </c:if>
                <th>叫车类型</th>
                <c:if test="${not empty order.drivername}">
                    <th>接单司机</th>
                </c:if>
                <c:if test="${not empty order.singletime}">
                    <th>司机接单时间</th>
                </c:if>
                <th>订单创建日期</th>
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
                    <c:if test="${not empty order.drviernumber}">
                        <td>${order.drviernumber}</td>
                    </c:if>
                    <td>${order.taximode}</td>
                    <c:if test="${not empty order.drivername}">
                        <td>${order.drivername}</td>
                    </c:if>
                    <c:if test="${not empty order.singletime}">
                        <td>${order.singletime}</td>
                    </c:if>
                    <td>${order.createtime}</td>
                    <td>${order.statesmean}</td>
                    <td>
                        <button class="btn btn-primary btn-sm edit_btn" onclick="javascript:window.location.href='orderServlet?action=findOrderbyorderid&id=${order.orderid}'"><span class="glyphicon glyphicon-pencil">详情</span></button>
                        <button class="btn btn-danger btn-sm delete_btn" onclick="deleteOrderByid(${order.orderid})"><span class="glyphicon glyphicon-trash">取消订单</span></button>
                        <c:if test="${order.states==4}">
                            <button class="btn btn-primary btn-sm edit_btn" onclick="javascript:window.location.href='passengerServlet?action=passengerpayment&id=${order.orderid}'">支付</button>
                         </c:if>
                        <button type="button" class="btn btn-success" onclick="javascript:window.location.href='passenger/index.jsp'">返回</button>
                    </td>
                </tr>
                </tbody>
        </table>
    </div>
</div>
</body>
</html>
