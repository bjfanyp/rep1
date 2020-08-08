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
      <p class="main-content-div-index">修改权限<span class="page-error">${errorMsg}</span></p>
      <form:form action="${ctx}/perm" method="post" modelAttribute="perm">
        <input type="hidden" name="_method" value="put">
        <div class="form-group">
          <div class="col-md-8">
            <input name="permID" id="permID" value="${perm.permID}" type="hidden" >
            <label for="permName" >权限名称</label>
            <input name="permName" id="permName" value="${perm.permName}" class="form-control" type="text" readonly="readonly" >
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="menuID"  >菜单</label>
            <select name="menu.menuID" id="menuID" class="form-control">
              <c:forEach items="${menuParentList}" var="obj">
                <optgroup label=${obj.menuName}>
                  <c:forEach items="${menuList}" var="menu">
                    <c:if test="${menu.parentMenu.menuID==obj.menuID}"><option value="${menu.menuID}" <c:if test="${menu.menuID==perm.menu.menuID}">selected</c:if> >${menu.menuName}</option></c:if>
                  </c:forEach>
                </optgroup>
              </c:forEach>
            </select>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="permToken" >权限</label><span class="static-error">*</span><form:errors path="permToken" cssClass="form-error"></form:errors>
            <input id="permToken" name="permToken" class="form-control" value="${perm.permToken}"  type="text" required>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="permSxh" >顺序号</label><span class="static-error">*</span><form:errors path="permSxh" cssClass="form-error"></form:errors>
            <input id="permSxh" name="permSxh" class="form-control" value="${perm.permSxh!=null?perm.permSxh:1}"  type="number" required>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <shiro:hasPermission name="rest[perm:put]">
              <input id="addBtn" class="btn btn-success" type="submit" value="修改">
            </shiro:hasPermission>
            <a href="${ctx}/perms"><button type="button" class="btn btn-secondary" >返回</button></a>
          </div>
        </div>
      </form:form>
    </div>
  </div>
  <%@include file="/includes/foot.jsp"%>
  <%@include file="/includes/nav.jsp"%>
</div>
<script type="text/javascript">
</script>
</body>
</html>
