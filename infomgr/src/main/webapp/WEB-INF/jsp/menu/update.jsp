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
        <p class="main-content-div-index">修改菜单<span class="page-error">${errorMsg}</span></p>
        <form:form action="${ctx}/menu" method="post" modelAttribute="menu">
          <input type="hidden" name="_method" value="put">
            <div class="form-group">
              <div class="col-md-8">
                <label for="menuName">菜单名称</label>
                <input name="menuID" type="hidden" value="${menu.menuID}">
                <input name="menuName" id="menuName" value="${menu.menuName}" class="form-control" type="text" readonly="readonly">
              </div>
            </div>
            <div class="form-group">
              <div class="col-md-8">
                <label for="parentMenu"  style="font-weight: bolder">父级菜单</label>
                <select name="parentMenu.menuID" id="parentMenu" class="form-control">
                <option value="0" <c:if test="${menu==null||menu.parentMenu==null}">selected</c:if>>无</option>
                <c:forEach items="${parentMenuList}" var="obj">
                  <option value="${obj.menuID}" <c:if test="${menu.parentMenu.menuID==obj.menuID}">selected</c:if> >${obj.menuName}</option>
                </c:forEach>
                </select>
              </div>
            </div>
            <div class="form-group">
              <div class="col-md-8">
                <label for="menuSxh" >菜单顺序号</label><span class="static-error">*</span><form:errors path="menuSxh" cssClass="form-error"></form:errors>
                <input id="menuSxh"  name="menuSxh" class="form-control" value="${menu.menuSxh!=null?menu.menuSxh:1}"  type="number" required>
              </div>
            </div>
            <div class="form-group">
              <div class="col-md-8">
                <label for="menuUrl">菜单地址</label><form:errors path="menuUrl" cssClass="form-error"></form:errors>
                <input id="menuUrl" name="menuUrl" class="form-control" value="${menu.menuUrl}" <c:if test="${menu.parentMenu==null||menu.parentMenu.menuID==0}">readonly="readonly"</c:if> type="text">
              </div>
            </div>
            <div class="form-group">
              <div class="col-md-8">
                <label for="menuIcon">菜单图标</label><span style="color: black" id="featherIc"></span>
                <select id="menuIcon" name="menuIcon" class="form-control" <c:if test="${menu.parentMenu==null||menu.parentMenu.menuID==0}">disabled="disabled"</c:if>>
                <option value="0" <c:if test="${menu==null||menu.menuIcon==null}">selected</c:if> >无</option>
                <c:forEach items="${enumeratorList}" var="obj">
                  <option value="${obj.dmz}" <c:if test="${obj.dmz==menu.menuIcon}">selected</c:if> >${obj.dmz}</option>
                </c:forEach>
                </select>
              </div>
            </div>
            <div class="form-group">
              <div class="col-md-8">
                <shiro:hasPermission name="rest[menu:put]">
                  <input id="addBtn" class="btn btn-success" type="submit" value="修改">
                </shiro:hasPermission>
                <a href="${ctx}/menus"><button type="button" class="btn btn-secondary">返回</button></a>
              </div>
            </div>
      </form:form>
      </div>
    </div>
  <%@include file="/includes/foot.jsp"%>
  <%@include file="/includes/nav.jsp"%>
  </div>
  <script type="text/javascript">
  if(${menu.menuIcon==null})
  {
    $("#featherIc").html("");
  }
  else
  {
    $("#featherIc").html("<span data-feather='${menu.menuIcon}'></span>");
  }
  feather.replace();
  $("#menuIcon").change(
    function() {
      if($("#menuIcon").val()!="0")
      {
        var menuIcon=$("#menuIcon").val();
        $("#featherIc").html("<span data-feather='"+menuIcon+"'></span>");
        feather.replace();
      }
      else {
        $("#featherIc").html("");
        }
      }
  );
  $("#parentMenu").change(function() {
  var item = $("#parentMenu").val();
  if(item==0) {
  $("#menuUrl").val("");
  $("#menuIcon").val(0);
  $("#featherIc").html("");
  $("#menuUrl").attr("readonly","readonly");
  $("#menuIcon").attr("disabled","disabled");
  }
  else {
  $("#menuUrl").removeAttr("readonly");
  $("#menuIcon").removeAttr("disabled");
  }
  })
  </script>
</body>
</html>
