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
      <p class="main-content-div-index">新建用户<span class="page-error">${errorMsg}</span></p>
      <form:form action="${ctx}/user" method="post" modelAttribute="user">
      <div class="form-group">
        <div class="col-md-8">
          <label for="userName">用户名</label><span class="static-error">*</span><form:errors path="userName" cssClass="form-error"></form:errors>
          <input name="userName" id="userName" class="form-control"  value="${user.userName}" type="text" required>
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
          <label for="dep.depID">部门</label>
          <select id="dep.depID" name="dep.depID" class="form-control">
            <c:forEach items="${depList}" var="obj">
              <option value="${obj.depID}" <c:choose><c:when test="${user.dep!=null && user.dep.depID==obj.depID}">selected</c:when> <c:when test="${user.dep==null && obj.depID==1002}">selected</c:when></c:choose>>${obj.depName}</option>
            </c:forEach>
          </select>
        </div>

      </div>
      <div class="form-group">
        <div class="col-md-8">
          <label for="roleID">用户角色</label>
          <select id="roleID" name="roleID" class="form-control">
            <c:forEach items="${roleList}" var="obj">
              <option value="${obj.roleID}" <c:choose><c:when test="${roleID!=null && roleID==obj.roleID}">selected</c:when> <c:when test="${roleID==null && obj.roleID==113}">selected</c:when></c:choose>>${obj.roleName}</option>
            </c:forEach>
          </select>
        </div>
      </div>
      <div class="form-group">
        <div class="col-md-8">
          <label for="userTel">手机号码</label><form:errors path="userTel" cssClass="form-error"></form:errors>
          <input name="userTel" id="userTel"  class="form-control" value="${user.userTel!=null?user.userTel:''}" type="tel" >
        </div>
      </div>
      <div class="form-group">
        <div class="col-md-8">
          <label for="userEffective">账号有效期</label>
          <input name="userEffective" id="userEffective"  class="form-control"  type="date" value=<fmt:formatDate value="${user.userEffective!=null?user.userEffective:date}" pattern="yyyy-MM-dd"/>>
        </div>
      </div>
      <div class="form-group">
        <div class="col-md-8">
          <shiro:hasPermission name="rest[user:post]">
            <input id="addBtn" class="btn btn-success" type="submit" value="新建">
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
<script type="text/javascript">
  $(function(){
    $('#userName').blur(
      function(){
        $(".page-error").html("");
        $(".form-error").html("");
        var userName=$("#userName").val();
        if(userName!=null&&userName!="")
        { $.ajax({
          url:'${ctx}/userCheck',
          type:'post',
          contentType:'application/json;charset=utf-8',
          data:JSON.stringify({userName:userName}),
          success:function(data)
          {
            if(data.code==false)
            {
              $("#userName").val('');
              $(".page-error").html(data.msg);
            }
          }
        })
        }
      }
    )
  })
</script>
</body>
</html>
