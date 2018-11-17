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
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/bootstrap-theme.min.css" rel="stylesheet">
    <script src="/jquery/jquery-2.2.4.min.js" type="text/javascript"></script>
    <base href="<%=basePath%>">
    <script>
        function changeOrderByid(orderid) {
            if (confirm("确认乘客已上车吗？")) {
                $.ajax({
                    url: "orderServlet?action=changeOrderByid",
                    type: "post",
                    async: "true",
                    data: {"action": "changeOrderByid", "id": orderid},
                    dataType: "json",
                    success: function (data) {
                        if (data.res == 1) {
                            alert(data.info);
                            window.location.replace("orderServlet?action=boardingorder&id="+orderid);
                        }
                        else {
                            alert(data.info);
                            location.reload();
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
                <th>接单时间</th>
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
                    <td>${order.statesmean}</td>
                    <td>${order.singletime}</td>
                    <c:if test="${order.states==1}">
                        <td>
                            <button class="btn btn-success" onclick="javascript:window.location.href='orderServlet?action=queryOrderByid&id=${order.orderid}'">详情</button>
                            <button class="btn btn-primary btn-sm edit_btn" onclick="changeOrderByid(${order.orderid})"><span class="glyphicon glyphicon-pencil">确认乘客已上车</span></button>
                            <button class="btn btn-danger btn-sm delete_btn" onclick="deleteOrderByid(${order.orderid})"><span class="glyphicon glyphicon-trash">取消订单</span></button>
                        </td>
                    </c:if>
                    <c:if test="${order.states==3}">
                        <td>
                            <button class="btn btn-success" onclick="javascript:window.location.href='orderServlet?action=queryOrderByid&id=${order.orderid}'">详情</button>
                            <button class="btn btn-primary btn-sm edit_btn" onclick="closeorder(${order.orderid})"><span class="glyphicon glyphicon-pencil">确认乘客已到达目的地</span></button>
                            <button class="btn btn-danger btn-sm delete_btn" onclick="deleteOrderByid(${order.orderid})"><span class="glyphicon glyphicon-trash">取消订单</span></button>
                        </td>
                    </c:if>
                    <c:if test="${order.states==4}">
                        <td>
                            <button class="btn btn-success" onclick="javascript:window.location.href='orderServlet?action=queryOrderByid&id=${order.orderid}'">详情</button>
                            <button class="btn btn-primary btn-sm edit_btn" onclick="closeorder(${order.orderid})"><span class="glyphicon glyphicon-pencil">等待乘客支付中</span></button>
                            <button class="btn btn-danger btn-sm delete_btn" onclick="deleteOrderByid(${order.orderid})"><span class="glyphicon glyphicon-trash">取消订单</span></button>
                        </td>
                    </c:if>
                </tr>
                </tbody>
        </table>
    </div>
</div>
</body>
</html>
