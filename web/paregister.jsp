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
    <title>乘客注册</title>
    <script type="text/javascript">
        $(function(){
            validateForm();
        });

        function validateForm(){
            // 验证表单
            $("#registerform").bootstrapValidator({
                message: 'This value is not valid',
                feedbackIcons: {/*输入框不同状态，显示图片的样式*/
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                /*验证*/
                fields: {
                    username: {/*键名username和input name值对应*/
                        message: 'The username is not valid',
                        validators: {
                            notEmpty: {
                                message: '用户名不能为空',

                            },
                            stringLength: {/*长度提示*/
                                min: 2,
                                max: 8,
                                message: '用户名长度必须在2到8之间'

                            },
                        }
                    },
                    password : {
                        message : '密码无效',
                        validators : {
                            notEmpty : {
                                message : '密码不能为空'
                            },
                            regexp : {
                                regexp : /^[a-zA-Z0-9_\.]+$/,
                                message : '密码不合法, 请重新输入'
                            },
                            stringLength : {
                                min : 6,
                                max : 30,
                                message : '密码长度必须在6到30之间'
                            }
                        }
                    },
                    password2 : {
                        messaage : 'The two password must be consistent',
                        validators : {
                            notEmpty : {
                                message : '确认密码不能为空'
                            },
                            identical : {
                                field : 'password',
                                message : '两次密码必须一致'
                            }
                        }
                    },
                    number : {
                        messaage : 'The two password must be consistent',
                        validators : {
                            notEmpty : {
                                message : '手机号码不能为空'
                            },
                            stringLength : {
                                min : 11,
                                max : 11,
                                message : '手机号必须是11位'
                            },
                            regexp : {
                                regexp: /^1[3|5|8]{1}[0-9]{9}$/,
                                message : '手机号不合法, 请重新输入'
                            }
                        }
                    },
                    sex : {
                        messaage : 'The sex is not valid',
                        validators : {
                            notEmpty : {
                                message : '性别不能为空'
                            }

                        }
                    }
                }
            });
        }

        function register() {
            // 异步注册用户
            $.ajax({
                url : "passengerServlet?action=register",// 请求地址
                type : "POST", // 请求类型
                async : "true", // 是否异步方式
                data : $("#registerform").serialize(), // 表单的序列化
                dataType : "json",
                success : function(data) {
                    if (data.res == 1) {
                        alert(data.info);
                        window.location.replace("palogin.jsp");
                    } else if(data.res == 3){
                        alert(data.info);
                        $(".text-warning").text("尊敬的用户:用户账号不能为空，请重新输入！");
                        $("input[name='username']").focus();
                    }else if(data.res == 9){
                        alert(data.info);
                        $("input[name='password2']").val("");
                        $("input[name='password2']").focus();
                        $(".text-warning").text("尊敬的用户:确认密码长度必须在6到30之间，且要跟新密码一致，请重新输入！");
                    }else if(data.res == 5) {
                        alert(data.info);
                        $("input[name='password2']").val("");
                        $("input[name='password2']").focus();
                        $(".text-warning").text("尊敬的用户:确认密码跟新密码不匹配，请重新输入确认密码！");
                    }else if(data.res == 19) {
                        alert(data.info);
                        $(".text-warning").text("你输入的账号已经存在!注册失败，请换一个其它账号呗！");
                    }else if(data.res == 28) {
                        alert(data.info);
                        $(".text-warning").text("请选择你的性别！");
                    }
                    else {
                        alert(data.info);
                    }
                }
            });
            return false;
        }
        // // ajax校验用户名
        // function validate_empName(ele) {
        //     //发送ajax请求校验用户名是否可用
        //     var username = $(ele).val();
        //     $.ajax({
        //         url: "userServlet",
        //         type: "POST",
        //         async: "true",
        //         data: {"action": "checkUsername", "username": username},
        //         dataType: "json",
        //         success: function (data) {
        //             if (data.res==1) {
        //                 show_validate_msg(ele, "success", "该账号可用");
        //                 $("#btn btn-success btn-block").attr("ajax-va", "success");
        //             } else if(data.res==-1){
        //                 show_validate_msg(ele, "error", "账号已存在");
        //                 $("#btn btn-success btn-block").attr("ajax-va", "error");
        //             }
        //         }
        //     });
        // }
        function gg() {
            var bootstrapValidator=$("#registerform").data("bootstrapValidator");
            //触发验证
            bootstrapValidator.validate();
            //如果验证通过，则调用login方法
            if(bootstrapValidator.isValid()){
                register();
            }
        }
    </script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-offset-3 col-sm-6 text-center">
            <h3>乘客注册-快车系统</h3>
        </div>
    </div>
    <form class="form-horizontal col-sm-offset-3" id="registerform">
        <div class="form-group">
            <label for="username" class="col-sm-2 control-label">账号：</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" name="username" id="username" placeholder="请输入账号">
                <span class="help-block1" style="color: #a94442"></span>
            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-2 control-label">密码：</label>
            <div class="col-sm-4">
                <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
            </div>
        </div>
        <div class="form-group">
            <label for="password2" class="col-sm-2 control-label">确认密码：</label>
            <div class="col-sm-4">
                <input type="password" class="form-control" id="password2" name="password2" placeholder="请确认密码">
            </div>
        </div>
        <div class="form-group">
            <label for="password2" class="col-sm-2 control-label">手机号码：</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="number" name="number" placeholder="请输入手机号码">
            </div>
        </div>
        <div class="form-group">
            <label for="sex" class="col-sm-2 control-label">性别：</label>
            <div class="col-sm-4">
                <label class="radio-inline">
                    <input type="radio" name="sex" value="男" checked> 男
                </label>
                <label class="radio-inline">
                    <input type="radio" name="sex" value="女"> 女
                </label>
            </div>
        </div>
        <div class="form-group has-error">
            <div class="col-sm-offset-2 col-sm-4 col-xs-6 ">
                <span class="text-warning" style="color: #a94442"></span>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-4 col-xs-12">
                <button class="btn btn-success btn-block" id="btn btn-success btn-block" onclick="gg()">注册</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>