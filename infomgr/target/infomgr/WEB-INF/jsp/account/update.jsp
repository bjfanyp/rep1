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
      <p class="main-content-div-index">修改银行账户<span class="page-error">${errorMsg}</span></p>
      <form:form action="${ctx}/account" method="post" modelAttribute="account">
        <input type="hidden" name="_method" value="put">
        <div class="form-group">
          <div class="col-md-8">
            <input name="accountID" id="accountID" value="${account.accountID}" type="hidden">
            <label for="accountName">户名</label>
            <input name="accountName" id="accountName" value="${account.accountName}" class="form-control" type="text" readonly="readonly">
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="accountType">账号类型</label>
            <select name="accountType" id="accountType" class="form-control">
              <option value="0" <c:if test="${account==null||account.accountType=='0'}">selected</c:if>>打款账号</option>
              <option value="1" <c:if test="${account.accountType=='1'}">selected</c:if> >收款账号</option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="accountBank">打款开户行</label><span class="static-error">*</span><form:errors path="accountBank" cssClass="form-error"></form:errors>
            <input id="accountBank" name="accountBank" class="form-control" value="${account.accountBank}"  type="text" required>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="accountNumber">打款账号</label><span class="static-error">*</span><form:errors path="accountNumber" cssClass="form-error"></form:errors>
            <input id="accountNumber"  name="accountNumber" class="form-control" value="${account.accountNumber}"  type="text" required>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <shiro:hasPermission name="rest[account:put]">
              <input id="uptBtn" class="btn btn-success" type="submit" value="修改">
            </shiro:hasPermission>
            <a href="${ctx}/accounts"><button type="button" class="btn btn-secondary">返回</button></a>
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
