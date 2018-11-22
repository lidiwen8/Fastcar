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
        function receiptOrderByid(orderid) {
            if (confirm("确认选择当前订单接单吗？")) {
                $.ajax({
                    url: "driverServlet?action=receiptOrder",
                    type: "post",
                    async: "true",
                    data: {"action": "receiptOrder", "id": orderid},
                    dataType: "json",
                    success: function (data) {
                        if (data.res == 1) {
                            alert(data.info);//接单成功，跳转至当前订单进行页面
                            // window.location.replace("orderServlet?action=queryOrderByid&id="+orderid);
                            window.location.replace("orderServlet?action=queryOrderbydrivername");
                        }
                        else if(data.res == 2){
                            alert(data.info);
                            window.location.replace("orderServlet?action=queryOrderbydrivername");
                        }else if(data.res==3){
                            alert(data.info);
                            window.location.replace("driver/application.jsp");
                        }else if(data.res==4){
                            alert(data.info);
                            window.location.replace("driverServlet?action=queryDriver");
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
    <p class="navbar-text">司机接单信息</p>
</nav>
<div class="container">
    <h2 class="sub-header">可选择接单的订单信息分页列表</h2><br>
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

            <c:forEach items="${pb2.beanList}" var="cstm">
                <tbody>
                <tr>
                    <td>${cstm.orderid}</td>
                    <td>${cstm.username}</td>
                    <td>${cstm.origin}</td>
                    <td>${cstm.destination}</td>
                    <td>${cstm.passengernumber}</td>
                    <td>${cstm.taximode}</td>
                    <td>${cstm.createtime}</td>
                    <td>未接单</td>
                        <td>
                            <button class="btn-success" onclick="javascript:window.location.href='orderServlet?action=queryOrderByid&id=${cstm.orderid}'">详情</button>
                            <button class="btn btn-primary btn-sm edit_btn" onclick="receiptOrderByid(${cstm.orderid})"><span class="glyphicon glyphicon-pencil">选择订单</span></button>
                        </td>
                </tr>
                </tbody>
            </c:forEach>
        </table>
        <br/>
        <center>
            <ul class="pagination">
                <li><a>第${pb2.pc}页/共${pb2.tp}页</a></li>
                <li><a href="${pb2.url}&pc=1">首页</a></li>
                <c:if test="${pb2.pc>1}">
                    <li><a href="${pb2.url}&pc=${pb2.pc-1}">上一页</a></li>
                </c:if>

                <c:choose>
                    <c:when test="${pb2.tp<=10}">
                        <c:set var="begin" value="1"/>
                        <c:set var="end" value="${pb2.tp}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="begin" value="${pb2.pc-5}"/>
                        <c:set var="end" value="${pb2.pc+4}"/>
                        <%--头溢出--%>
                        <c:if test="${begin<1}">
                            <c:set var="begin" value="1"/>
                            <c:set var="end" value="10"/>
                        </c:if>
                        <%--尾溢出--%>
                        <c:if test="${end>pb2.tp}">
                            <c:set var="end" value="${pb2.tp}"/>
                            <c:set var="begin" value="${pb2.tp-9}"/>
                        </c:if>
                    </c:otherwise>
                </c:choose>

                <%--循环遍历页码列表--%>
                <c:forEach var="i" begin="${begin}" end="${end}">
                    <c:choose>
                        <c:when test="${i eq pb2.pc}">
                            <li><a href="${pb2.url}&pc=${i}">${i}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="${pb2.url}&pc=${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>

                </c:forEach>
                <c:if test="${pb2.pc<pb2.tp}">
                    <li><a href="${pb2.url}&pc=${pb2.pc+1}">下一页</a></li>
                </c:if>
                <li><a href="${pb2.url}&pc=${pb2.tp}">尾页</a></li>
                <li><a href="driver/index.jsp">返回</a></li>
            </ul>
        </center>
    </div>
</div>
</body>
</html>

