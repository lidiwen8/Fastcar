
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>五星评价</title>
    <style>
        @font-face {
            font-family: 'iconfont';  /* project id 247957 */
            src: url('//at.alicdn.com/t/font_wkv6intmx8cnxw29.eot');
            src: url('//at.alicdn.com/t/font_wkv6intmx8cnxw29.eot?#iefix') format('embedded-opentype'),
            url('//at.alicdn.com/t/font_wkv6intmx8cnxw29.woff') format('woff'),
            url('//at.alicdn.com/t/font_wkv6intmx8cnxw29.ttf') format('truetype'),
            url('//at.alicdn.com/t/font_wkv6intmx8cnxw29.svg#iconfont') format('svg');
        }
        .iconfont {
            font-family:"iconfont" !important;
            font-size:16px;
            font-style:normal;
            -webkit-font-smoothing: antialiased;
            -webkit-text-stroke-width: 0.2px;
            -moz-osx-font-smoothing: grayscale;
        }
        body{
            font-family: "microsoft yahei";
        }
        ul,li{
            list-style: none;
            padding:0;
            margin:0;
        }
        a{
            text-decoration: none;
        }
        .clearfix:after {
            content: ".";
            display: block;
            height: 0;
            clear: both;
            visibility: hidden;
        }
        .order-evaluation {
            width: 827px;
            border: 2px solid #E84E40;
            background: #fff;
            z-index: 200;
            margin:50px auto;
        }
        .order-evaluation{
            padding:22px;
        }
        .order-evaluation h4{
            font-size:18px;
            color:#333333;
            padding-bottom:20px;
            border-bottom:1px dashed #dbdbdb;
        }
        .order-evaluation p{
            font-size:14px;
            color:#999;
            line-height:45px;
            margin-bottom:0;
        }
        .order-evaluation .order-evaluation-text{
            font-size:16px;
            color:#333;
            line-height:40px;
            width:809px;
            padding-left:15px;
            background:#f3f3f3;
            margin-bottom:25px;
            margin-top:20px;
        }
        .order-evaluation-checkbox ul li{
            width:142px;
            height:43px;
            border:1px solid #e8e8e8;
            text-align: center;
            background: #fff;
            font-size:14px;
            color:#333333;
            line-height:43px;
            margin-right:25px;
            margin-bottom:25px;
            float:left;
            cursor: pointer;
            overflow: hidden;
            position:relative;
        }
        .order-evaluation-checkbox ul li.checked i{
            display: block;
        }
        .order-evaluation-checkbox ul li.checked{
            border:1px solid #e84c3d;
        }
        .order-evaluation .order-evaluation-textarea{
            position:relative;
            width: 784px;
            height: 210px;
        }
        .order-evaluation .order-evaluation-textarea textarea{
            width:793px;
            height:178px;
            border:1px solid #e8e8e8;
            position:absolute;
            top:0;
            left:0;
            line-height:22px;
            padding:15px;
            color:#666;
        }
        .order-evaluation .order-evaluation-textarea span{
            position:absolute;
            bottom:10px;
            font-size:12px;
            color:#999;
            right:10px;
        }
        .order-evaluation .order-evaluation-textarea span em{
            color:#e84c3d;
        }
        .order-evaluation>a{
            width:154px;
            height:48px;
            border-radius: 6px;
            display: block;
            text-align: center;
            line-height:48px;
            background:#f36a5a;
            float:right;
            margin-top:20px;
            color:#fff;
            font-size:14px;
        }
        .order-evaluation-checkbox ul li i {
            display: none;
            color: #e84c3d;
            position: absolute;
            right: -4px;
            bottom: -14px;
            font-size: 20px;
        }
        .order-evaluation>a:hover{
            background: #e84c3d;
        }
        .block li label,.con span{ font-size: 18px; margin-right: 20px; line-height: 23px;}
        .block li span{display: inline-block; vertical-align: middle; cursor: pointer;}
        .block li span img{margin-right: -5px; }
        .level{color:#e84c3d;font-size:16px;margin-left:15px;position: relative;top: 3px;}
        .dmlei_tishi_info{
            height:70px;border-radius: 10px;background: rgba(0,0,0,0.5);font-size:18px;color:#fff;text-align: center;line-height: 70px;position:fixed;
            left: 48%;
            display: none;
            margin-left: -128px;
            top: 40%;
            margin-top: -35px;
            padding: 0 15px;
            z-index: 1000;
        }
    </style>
</head>
<body>
<div class="order-evaluation clearfix">
    <h4>给“司机”进行的评价</h4>
    <p>请严肃认真对待此次评价哦！您的评价对我们真的真的非常重要！</p>
    <div class="block">
        <ul>
            <li data-default-index="0">
                        <span>
                            <img src="Images/1.png">
                            <img src="Images/1.png">
                            <img src="Images/1.png">
                            <img src="Images/1.png">
                            <img src="Images/1.png">
                        </span>
                <em class="level" id="evaluate"></em>
            </li>
        </ul>
    </div>
    <div class="order-evaluation-text">
        本次行程结束，亲，摸摸头 司机给您留下了什么印象呢？
    </div>
    <div class="order-evaluation-checkbox">
        <ul class="clearfix">
            <li class="order-evaluation-check" data-impression="1">车内整洁<i class="iconfont icon-checked"></i></li>
            <li class="order-evaluation-check" data-impression="2">活地图认路准<i class="iconfont icon-checked"></i></li>
            <li class="order-evaluation-check" data-impression="3">驾驶平稳<i class="iconfont icon-checked"></i></li>
            <li class="order-evaluation-check" data-impression="4">态度好服务棒<i class="iconfont icon-checked"></i></li>
            <li class="order-evaluation-check" data-impression="5">服务态度恶劣<i class="iconfont icon-checked"></i></li>
            <li class="order-evaluation-check" data-impression="6">故意绕路<i class="iconfont icon-checked"></i></li>
            <li class="order-evaluation-check" data-impression="6">未送达指定终点<i class="iconfont icon-checked"></i></li>
        </ul>
    </div>
    <div class="order-evaluation-textarea">
        <textarea name="content" id="TextArea1" onkeyup="words_deal();" ></textarea>
        <span>还可以输入<em id="textCount">140</em>个字</span>
    </div>
    <a href="javascript:;" id="order_evaluation">评价完成</a>
</div>

<div id="order_evaluate_modal" class="dmlei_tishi_info"></div>

</body>
<script src="//cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
<script>
   var orderid;
    $(function (){
        orderid =GetQueryString("orderid");
    });
    //正则
    function GetQueryString(name)
    {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null)return  unescape(r[2]); return null;
    }
    /*
     * 根据index获取 str
     * **/
    function byIndexLeve(index){
        var str ="";
        switch (index)
        {
            case 0:
                str="差评";
                break;
            case 1:
                str="较差";
                break;
            case 2:
                str="一般";
                break;
            case 3:
                str="中等";
                break;
            case 4:
                str="好评";
                break;
        }
        return str;
    }
    //  星星数量
    var stars = [
        ['2.png', '1.png', '1.png', '1.png', '1.png'],
        ['2.png', '2.png', '1.png', '1.png', '1.png'],
        ['2.png', '2.png', '2.png', '1.png', '1.png'],
        ['2.png', '2.png', '2.png', '2.png', '1.png'],
        ['2.png', '2.png', '2.png', '2.png', '2.png'],
    ];
    $(".block li").find("img").hover(function(e) {
        var obj = $(this);
        var index = obj.index();
        if(index < (parseInt($(".block li").attr("data-default-index")) -1)){
            return ;
        }
        var li = obj.closest("li");
        var star_area_index = li.index();
        for (var i = 0; i < 5; i++) {
            li.find("img").eq(i).attr("src", "Images/" + stars[index][i]);//切换每个星星
        }
        $(".level").html(byIndexLeve(index));
    }, function() {
    })

    $(".block li").hover(function(e) {
    }, function() {
        var index = $(this).attr("data-default-index");//点击后的索引
        index = parseInt(index);
        console.log("index",index);
        $(".level").html(byIndexLeve(index-1));
        console.log(byIndexLeve(index-1));
        $(".order-evaluation ul li:eq(0)").find("img").attr("src","Images/1.png");
        for (var i=0;i<index;i++){

            $(".order-evaluation ul li:eq(0)").find("img").eq(i).attr("src","Images/2.png");
        }
    })
    $(".block li").find("img").click(function() {
        var obj = $(this);
        var li = obj.closest("li");
        var star_area_index = li.index();
        var index1 = obj.index();
        li.attr("data-default-index", (parseInt(index1)+1));
        var index = $(".block li").attr("data-default-index");//点击后的索引
        index = parseInt(index);
        console.log("index",index);
        $(".level").html(byIndexLeve(index-1));
        console.log(byIndexLeve(index-1));
        $(".order-evaluation ul li:eq(0)").find("img").attr("src","Images/1.png");
        for (var i=0;i<index;i++){
            $(".order-evaluation ul li:eq(0)").find("img").eq(i).attr("src","Images/2.png");
        }

    });
    //印象
    $(".order-evaluation-check").click(function(){
        if($(this).hasClass('checked')){
            //当前为选中状态，需要取消
            $(this).removeClass('checked');
        }else{
            //当前未选中，需要增加选中
            $(this).addClass('checked');
        }
    });
    //评价字数限制
    function words_deal()
    {
        var curLength=$("#TextArea1").val().length;
        if(curLength>140)
        {
            var num=$("#TextArea1").val().substr(0,140);
            $("#TextArea1").val(num);
            alert("超过字数限制，多出的字将被截断！" );
        }
        else
        {
            $("#textCount").text(140-$("#TextArea1").val().length);
        }
    }
    $("#order_evaluation").click(function(){

        $("#order_evaluate_modal").html("感谢您的评价！么么哒(* ￣3)(ε￣ *)").show().delay(3000).hide(500);
        var evaluate=$("#evaluate").text();
        var evaluateinfo=$("#TextArea1").val();
        //异步提交评价信息
        $.ajax({
            url : "passengerServlet?action=passengerevaluate",// 请求地址
            type : "POST", // 请求类型
            async : "true", // 是否异步方式
            data: {"action": "passengerevaluate", "evaluate": evaluate,"evaluateinfo": evaluateinfo,"orderid": orderid},
            dataType : "json",
            success : function(data) {
                if (data.res == 1) {
                    alert("评价成功");
                    window.setTimeout("window.location='passenger/index.jsp'",1000);
                    // window.location.replace("passenger/index.jsp");
                }else if(data.res == 2){
                    alert(data.info);
                    window.setTimeout("window.location='passenger/index.jsp'",1000);
                }
                else {
                    alert(data.info);
                }
            }
        });

    })

</script>
</html>