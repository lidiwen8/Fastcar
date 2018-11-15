<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2018/5/7
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>订单信息</title>
    <base href="<%=basePath%>">
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/bootstrap-theme.min.css" rel="stylesheet">
    <script src="/jquery/jquery-2.2.4.min.js" type="text/javascript"></script>
    <script src="/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
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
</head>
<body>
<nav class="navbar navbar-inverse">
    <p class="navbar-text">乘客订单信息</p>
</nav>
<div class="container">
    <h2 class="sub-header">订单信息分页列表</h2><br>
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

            <c:forEach items="${pb.beanList}" var="cstm">
                <tbody>
                <tr>
                    <td>${cstm.orderid}</td>
                    <td>${cstm.username}</td>
                    <td>${cstm.origin}</td>
                    <td>${cstm.destination}</td>
                    <td>${cstm.passengernumber}</td>
                    <td>${cstm.taximode}</td>
                    <td>${cstm.createtime}</td>
                    <c:if test="${cstm.states==0}">
                        <td>未接单</td>
                    </c:if>
                    <c:if test="${cstm.states==1}">
                    <td>已接单</td>
                    </c:if>
                        <td>
                            <button class="btn btn-primary btn-sm edit_btn" onclick="javascript:window.location.href='orderServlet?action=findOrderbyorderid&id=${cstm.orderid}'"><span class="glyphicon glyphicon-pencil">详情</span></button>
                            <button class="btn btn-danger btn-sm delete_btn" onclick="deleteOrderByid(${cstm.orderid})">取消订单</button>
                        </td>
                </tr>
                </tbody>
            </c:forEach>
        </table>
        <br/>
        <center>
            <ul class="pagination">
                <li><a>第${pb.pc}页/共${pb.tp}页</a></li>
                <li><a href="${pb.url}&pc=1">首页</a></li>
                <c:if test="${pb.pc>1}">
                    <li><a href="${pb.url}&pc=${pb.pc-1}">上一页</a></li>
                </c:if>

                <c:choose>
                    <c:when test="${pb.tp<=10}">
                        <c:set var="begin" value="1"/>
                        <c:set var="end" value="${pb.tp}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="begin" value="${pb.pc-5}"/>
                        <c:set var="end" value="${pb.pc+4}"/>
                        <%--头溢出--%>
                        <c:if test="${begin<1}">
                            <c:set var="begin" value="1"/>
                            <c:set var="end" value="10"/>
                        </c:if>
                        <%--尾溢出--%>
                        <c:if test="${end>pb.tp}">
                            <c:set var="end" value="${pb.tp}"/>
                            <c:set var="begin" value="${pb.tp-9}"/>
                        </c:if>
                    </c:otherwise>
                </c:choose>

                <%--循环遍历页码列表--%>
                <c:forEach var="i" begin="${begin}" end="${end}">
                    <c:choose>
                        <c:when test="${i eq pb.pc}">
                            <li><a href="${pb.url}&pc=${i}">${i}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="${pb.url}&pc=${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>

                </c:forEach>
                <c:if test="${pb.pc<pb.tp}">
                    <li><a href="${pb.url}&pc=${pb.pc+1}">下一页</a></li>
                </c:if>
                <li><a href="${pb.url}&pc=${pb.tp}">尾页</a></li>
                <li><a href="passenger/index.jsp">返回</a></li>
            </ul>
        </center>
    </div>
</div>
</body>
</html>

