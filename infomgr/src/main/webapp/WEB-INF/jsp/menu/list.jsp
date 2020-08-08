<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
  <fieldset>
  <form class="form-horizontal" action="${ctx}/menus" method="post">
  <shiro:hasPermission name="menus">
    <div class="form-group">
    <div class="row">
    <div class="col-md-4">
    <label for="menuName" class="font-border">菜单名称</label>
    <input type="text" class="form-control" id="menuName" name="menuName" value="${menuQuery.menuName}">
    <input type="hidden" id="pageNum" name="pageNum">
    </div>
    <div class="col-md-4">
    <label for="menuParentID" class="font-border">父级菜单</label>
    <select class="form-control" id="menuParentID" name="menuParentID" >
    <option value="0" <c:if test="${menu}==null">selected</c:if> >全部</option>
    <c:forEach items="${parentMenuList}" var="obj">
      <option value="${obj.menuID}" <c:if test="${obj.menuID==menuQuery.menuParentID}">selected</c:if> >${obj.menuName}</option>
    </c:forEach>
    </select>
    </div>
    </div>
    </div>
  </shiro:hasPermission>
  <div class="form-group">
  <shiro:hasPermission name="menus">
    <button type="submit" class="btn btn-default">查询</button>
  </shiro:hasPermission>
  <shiro:hasPermission name="rest[menu:read]">
    <a href="${ctx}/menu"><button type="button" class="btn btn-primary">新建</button></a>
  </shiro:hasPermission>
  </div>
  </form>
  </fieldset>
  <fieldset>
  <%--<legend>操作</legend>--%>
  <table class="table table-bordered">
    <thead>
      <tr>
        <th>菜单编号</th>
        <th>菜单名称</th>
        <th>父级菜单</th>
        <th>菜单地址</th>
        <th>顺序号</th>
        <th>更新时间</th>
        <th>操作</th>
      </tr>
    </thead>
  <tbody>
  <c:forEach items="${pageInfo.list}" var="obj">
    <tr>
    <td>${obj.menuID}</td>
    <td>${obj.menuName}</td>
    <td>${obj.parentMenu.menuName}</td>
    <td>${obj.menuUrl}</td>
    <td>${obj.menuSxh}</td>
    <td><fmt:formatDate value="${obj.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
    <td>
    <shiro:hasPermission name="rest[menu:put]">
      <a href="${ctx}/menu/${obj.menuID}"><button type="button" class="btn-sm btn-primary">修改</button></a>
    </shiro:hasPermission>
    <shiro:hasPermission name="rest[menu:delete]">
      <button type="button" uid="${obj.menuID}" class="btn-sm btn-danger dataDel">删除</button>
    </shiro:hasPermission>
    </td>
    </tr>
  </c:forEach>
  </tbody>
  <shiro:hasPermission name="rest[menu:read]">
    <thfoot>
    <tr>
    <td colspan="7">
    <div>
    当前第 ${pageInfo.pageNum}页,总计${pageInfo.pages}页,总计 ${pageInfo.total} 条记录
    <a href="javascript:void(0);" page="first">首页</a>
    <c:if test="${pageInfo.hasPreviousPage}">
      <a href="javascript:void(0);" page="prev">上一页</a>
    </c:if>
    <c:if test="${pageInfo.hasNextPage}">
      <a href="javascript:void(0);" page="next">下一页</a>
    </c:if>
    <a href="javascript:void(0);" page="last">尾页</a>
    </div>
    </td>
    </tr>
    </thfoot>
  </shiro:hasPermission>
  </table>
  </fieldset>
  </div>
  </div>
  <%@include file="/includes/foot.jsp"%>
  <%@include file="/includes/nav.jsp"%>
  </div>
  <script type="text/javascript">
  $('a[page]').click(
  function()
  {
  var pageNum=1;
  var currPage='${pageInfo.pageNum}';
  var totalPages='${pageInfo.pages}';
  var v=$(this).attr('page');
  switch(v){
  case "first":
  pageNum=1;
  break;
  case "prev":
  pageNum=parseInt(currPage)-1  ;
  if(pageNum<1)
  {
  pageNum=1;
  }
  break;
  case "next":
  pageNum=parseInt(currPage)+1  ;
  if(pageNum>totalPages)
  {
  pageNum=totalPages;
  }
  break;
  case "last":
  pageNum=totalPages;
  break;
  }
  $('input[name=pageNum]').val(pageNum);
  $('form').submit();
  });
  $(".dataDel").click(function () {
  var msg = "是否删除数据？";
  if (confirm(msg)==true){
  var uid=$(this).attr('uid');
  $.ajax({
  url:'${ctx}/menu/'+uid,
  type:'delete',
  contentType:'application/json',
  success:function(data){
  if(data.code==true)
  {
  alert("删除成功");
  $('form').submit();
  }
  else{
  alert(data.msg);
  }
  }
  })
  return true;
  }else{
  return false;
  }
  })
  </script>
</body>
</html>
