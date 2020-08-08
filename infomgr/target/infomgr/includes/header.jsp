<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="main-head">
    <div class="user-oper">
        <div class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                <span class="font-white">${sessionScope.currentUser.userTrueName}</span>
            </a>
            <ul class="dropdown-menu">
                <li class="font-black"><a href="${ctx}/updatePw">修改密码</a></li>
                <li class="font-black"><a href="${ctx}/logout">注销</a></li>
            </ul>
        </div>
    </div>
    <div class="system-info">
        <span>五车网业务管理系统</span>
    </div>
</div>