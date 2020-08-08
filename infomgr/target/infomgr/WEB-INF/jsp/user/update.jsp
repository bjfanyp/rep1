<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <%@include file="/includes/head.jsp"%>
</head>
<body>
<div class="main-body">
  <%@include file="/includes/header.jsp"%>
  <div class="main-content">
    <div class="main-content-div">
      <p class="main-content-div-index">修改用户<span class="page-error">${errorMsg}</span></p>
      <form:form action="${ctx}/user" method="post" modelAttribute="user">
        <input type="hidden" name="_method" value="PUT">
        <div class="form-group">
          <div class="col-md-8">
            <input name="userID" value="${user.userID}" type="hidden">
            <label for="userName">用户名</label>
            <input name="userName" id="userName" class="form-control"  value="${user.userName}" type="text" readonly="readonly">
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="userTrueName">真实姓名</label><span class="static-error">*</span><form:errors path="userTrueName" cssClass="form-error"></form:errors>
            <input name="userTrueName" id="userTrueName" class="form-control" value="${user.userTrueName}" type="text" required>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="dep.depID" class="bold">部门</label>
            <select id="dep.depID" name="dep.depID" class="form-control">
              <c:forEach items="${depList}" var="obj">
                <option value="${obj.depID}" <c:if test="${user.dep!=null && user.dep.depID==obj.depID}">selected</c:if>  >${obj.depName}</option>
              </c:forEach>
            </select>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="roleID">用户角色</label>
            <select id="roleID" name="roleID" class="form-control">
              <c:if test="${userRole==null&&roleID==null}"><option selected></option></c:if>
              <c:forEach items="${roleList}" var="obj">
                <option value="${obj.roleID}"  <c:if test="${roleID==obj.roleID||(userRole!=null&& userRole.role.roleID==obj.roleID)}">selected</c:if>>${obj.roleName}</option>
              </c:forEach>
            </select>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="userTel">手机号码</label><form:errors path="userTel" cssClass="form-error"></form:errors>
            <input name="userTel" id="userTel"  class="form-control" value="${user.userTel}" type="tel" >
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="userEffective">账号有效期</label>
            <input name="userEffective" id="userEffective" class="form-control" type="date" value=<fmt:formatDate value="${user.userEffective}" pattern="yyyy-MM-dd"/>>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <shiro:hasPermission name="rest[user:put]">
              <input class="btn btn-success" type="submit" value="修改">
            </shiro:hasPermission>
            <a href="${ctx}/users"><button type="button" class="btn btn-secondary">返回</button></a>
          </div>
        </div>
      </form:form>
    </div>
  </div>
  <%@include file="/includes/foot.jsp"%>
  <%@include file="/includes/nav.jsp"%>
</div>
</body>
</html>
