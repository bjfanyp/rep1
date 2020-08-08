<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<div class="main-nav">
    <div class="main-nav-div">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="link-nav <c:if test="${currentMenu=='main'}">active</c:if>" href="${ctx}/main">
                    <span data-feather="home"></span>
                    <span style="font-size: 12px;font-weight: bolder">首页</span>
                </a>
            </li>
        </ul>
        <c:forEach items="${menuParentList}" var="obj">
            <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
                <span>${obj.menuName}</span>
            </h6>
            <ul class="nav flex-column">
                <c:forEach items="${menuList}" var="menu">
                    <c:if test="${menu.parentMenu.menuID==obj.menuID}">
                        <li class="nav-item navlink">
                                <%--<shiro:hasPermission name="${menu.menuUrl}">--%>
                            <a href="${ctx}/${menu.menuUrl}" class="link-nav <c:if test="${currentMenu==menu.menuUrl}">active</c:if>">
                                <c:if test="${menu.menuIcon!=null&&menu.menuIcon!=''}"><span data-feather="${menu.menuIcon}"></span></c:if>
                                <span style="font-size: 12px;font-weight: bolder">${menu.menuName}</span>
                            </a>
                                <%--</shiro:hasPermission>--%>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
        </c:forEach>
    </div>
    <script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
    <script>
        feather.replace();
    </script>
</div>