<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="北京五车网电子商务有限公司">
	<title>北京五车网电子商务有限公司</title>
    <link href="${ctx}/static/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/css/dashboard.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/static/css/custom.css">
    <link rel="icon" href="${ctx}/static/image/favicon.ico">
    <script src="${ctx}/static/js/jquery-3.3.1.js"></script>
    <script src="${ctx}/static/js/popper/popper.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/bootstrap/bootstrap.js"></script>
</head>
<body>
<div class="login-container">
    <h1 class="login-title">五车网车辆团购后台管理系统</h1>
    <div class="login-pic">
        <img src="static/image/logo.png" alt="logo" width="123" height="63">
    </div>
    <form:form action="${ctx}/login" method="post"  modelAttribute="loginPara">
        <div class="form-group">
            <div class="login-parameter clearfix">
                <label for="userName" class="login-label"></label>
                <div class="login-input input-group-lg">
                    <input type="text" class="form-control"  name="userName" id="userName" value="${loginPara.userName}" placeholder="用户名" required>
                </div>
                <div class="login-error">
                    <span class="static-error">${errorMsg}<form:errors path="userName"></form:errors></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="login-parameter clearfix">
                <label for="userPassword" class="login-label"></label>
                <div class="login-input input-group-lg">
                    <input type="password" class="form-control" id="userPassword" name="userPassword" value="${loginPara.userPassword}"  placeholder="密码"  required>
                </div>
                <div class="login-error">
                    <form:errors cssClass="static-error" path="userPassword"></form:errors>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="login-parameter clearfix">
                <label for="yzm" class="login-label"></label>
                <div class="login-yzm-input input-group-lg">
                    <input type="text" class="form-control" id="yzm" name="yzm" value="${loginPara.yzm}" placeholder="验证码" >
                </div>
                <div class="login-yzm-pic">
                    <img style="width:70px;height: 70%;margin-top:6%" id="codeImg" alt="点击更换" title="点击更换">
                </div>
                <div class="login-error">
                    <span class="static-error">${yzmerrorMsg}<form:errors  path="yzm"></form:errors></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="login-submit">
                <button class="btn btn-lg  btn-primary btn-block" type="submit">登录</button>
            </div>
        </div>
    </form:form>
    <div class="form-group">
        <div class="text-dq">
            &copy;2018-2019
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        $('#userName').click(
            function () {
                $(".static-error").html("");
            }
        );
        changeCode();
        $("#codeImg").bind("click", changeCode);
    });
    function changeCode() {
        $("#codeImg").attr("src", "generate?time=" + new Date().getTime());
        $("#yzm").val("");
    }
</script>
</body>
</html>


