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
        <%--<legend>检索条件</legend>--%>
        <form class="form-horizontal" action="${ctx}/users" method="post">
          <shiro:hasPermission name="users">
            <div class="form-group">
              <div class="row">
                <div class="col-md-4">
                  <label for="userName" class="font-border">用户名</label>
                  <input type="text" class="form-control" id="userName" name="userName" value="${userQuery.userName}">
                </div>
                <div class="col-md-4">
                  <label class="font-border">部门</label>
                  <select name="depID"  class="form-control">
                    <option value="0" <c:if test="${userQuery.depID==null}">selected</c:if>>全部</option>
                    <c:forEach items="${depList}" var="obj">
                      <option value="${obj.depID}"  <c:if test="${userQuery.depID!=null&&userQuery.depID==obj.depID}">selected</c:if>>${obj.depName}</option>
                    </c:forEach>
                  </select>
                  <input type="hidden" id="pageNum" name="pageNum">
                </div>
              </div>
            </div>
          </shiro:hasPermission>
          <div class="form-group">
            <shiro:hasPermission name="users">
              <button type="submit" class="btn btn-default">查询</button>
            </shiro:hasPermission>
            <shiro:hasPermission name="rest[user:read]">
              <a href="${ctx}/user"><button type="button" class="btn btn-primary">新建</button></a>
            </shiro:hasPermission>
          </div>
        </form>
      </fieldset>
      <fieldset>
        <%--<legend>操作</legend>--%>
        <table class="table table-bordered">
          <thead>
          <tr>
            <th>用户编号</th>
            <th>用户名</th>
            <th>部门名称</th>
            <th>用户状态</th>
            <th>账号有效期</th>
            <th>更新时间</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${pageInfo.list}" var="obj">
            <tr>
              <td>${obj.userID}</td>
              <td>${obj.userName}</td>
              <td>${obj.dep.depName}</td>
              <td>${obj.userStatus}</td>
              <td><fmt:formatDate value="${obj.userEffective}" pattern="yyyy-MM-dd" /></td>
              <td><fmt:formatDate value="${obj.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
              <td>
                <shiro:hasPermission name="rest[user:read]">
                  <a href="${ctx}/user/${obj.userID}"><button type="button" class="btn-sm btn-primary">修改</button></a>
                </shiro:hasPermission>
                <shiro:hasPermission name="reset">
                  <button type="button"  uid="${obj.userID}"  class="btn-sm btn-success dataReset">重置密码</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="rest[user:delete]">
                  <button type="button"  uid="${obj.userID}"  class="btn-sm btn-danger dataDel">删除</button>
                </shiro:hasPermission>
              </td>
            </tr>
          </c:forEach>
          </tbody>
          <thfoot>
            <tr>
              <shiro:hasPermission name="users">
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
              </shiro:hasPermission>
            </tr>
          </thfoot>
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
  $(".dataDel").click(
          function()
          {
            var msg = "是否删除数据？";
            if (confirm(msg)==true){
              var uid=$(this).attr('uid');
              $.ajax({
                url:'${ctx}/user/'+uid,
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
          }
  );
  $(".dataReset").click(
          function ()
          {
            var msg = "是否重置密码";
            if (confirm(msg)==true){
              var uid=$(this).attr('uid');
              $.ajax({
                url:'${ctx}/reset/'+uid,
                type:'post',
                contentType:'application/json',
                success:function(data){
                  if(data.code==true)
                  {
                    alert("重置成功");
                    $('#form').submit();
                  }
                  else{
                    alert("重置失败");
                  }
                }
              })
              return true;
            }else{
              return false;
            }
          }
  )
</script>
</body>
</html>
