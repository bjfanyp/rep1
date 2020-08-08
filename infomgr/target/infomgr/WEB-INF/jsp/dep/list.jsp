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
        <form class="form-horizontal" action="${ctx}/deps" method="post">
          <shiro:hasPermission name="deps">
            <div class="form-group">
              <div class="row">
                <div class="col-md-4">
                  <label for="depName" style="font-weight: bolder">部门名称</label>
                  <input type="text" class="form-control" id="depName" name="depName" value="${depQuery.depName}">
                  <input type="hidden" id="pageNum" name="pageNum">
                </div>
                <div class="col-md-4">
                  <label for="depName" style="font-weight: bolder">部门类型</label>
                  <select name="depType" id="depType" class="form-control">
                    <option value="0" <c:if test="${depQuery.depType=='0'}||${depQuery.depType==null}">selected</c:if>>全部</option>
                    <option value="10"<c:if test="${depQuery.depType=='10'}">selected</c:if>>直营店</option>
                    <option value="20"<c:if test="${depQuery.depType=='20'}">selected</c:if>>一般渠道</option>
                    <option value="30"<c:if test="${depQuery.depType=='30'}">selected</c:if>>北京五车网</option>
                    <option value="40"<c:if test="${depQuery.depType=='40'}">selected</c:if>>天津八斗</option>
                    <option value="50"<c:if test="${depQuery.depType=='50'}">selected</c:if>>杭州八斗</option>
                    <option value="60"<c:if test="${depQuery.depType=='60'}">selected</c:if>>垫资公司</option>
                  </select>
                </div>
              </div>
            </div>
          </shiro:hasPermission>
          <div class="form-group">
            <shiro:hasPermission name="deps">
              <button type="submit" class="btn btn-default">查询</button>
            </shiro:hasPermission>
            <shiro:hasPermission name="rest[dep:read]">
              <a href="${ctx}/dep"><button type="button" class="btn btn-primary">新建</button></a>
            </shiro:hasPermission>
          </div>
        </form>
      </fieldset>
      <fieldset>
        <%--<legend>操作</legend>--%>
        <table class="table table-bordered">
          <thead>
          <tr>
            <th>部门编号</th>
            <th>部门名称</th>
            <th>部门类型</th>
            <th>渠道服务费(%)</th>
            <th>更新时间</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${pageInfo.list}" var="obj">
            <tr>
              <td>${obj.depID}</td>
              <td>${obj.depName}</td>
              <td>
                <c:if test="${obj.depType=='10'}">直营店</c:if>
                <c:if test="${obj.depType=='20'}">一般渠道</c:if>
                <c:if test="${obj.depType=='30'}">北京五车网</c:if>
                <c:if test="${obj.depType=='40'}">天津八斗</c:if>
                <c:if test="${obj.depType=='50'}">杭州八斗</c:if>
                <c:if test="${obj.depType=='60'}">垫资公司</c:if>
              </td>
              <td>
                <c:if test="${obj.depQdfwf<=0}">--</c:if>
                <c:if test="${obj.depQdfwf>0}">${obj.depQdfwf}</c:if>
              </td>
              <td><fmt:formatDate value="${obj.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
              <td>
                <shiro:hasPermission name="rest[dep:read]">
                  <a href="${ctx}/dep/${obj.depID}"><button type="button" class="btn-sm btn-primary">修改</button></a>
                </shiro:hasPermission>
                <shiro:hasPermission name="rest[dep:delete]">
                  <button type="button"  uid="${obj.depID}"  class="btn-sm btn-danger dataDel">删除</button>
                </shiro:hasPermission>
              </td>
            </tr>
          </c:forEach>
          </tbody>
          <thfoot>
            <shiro:hasPermission name="deps">
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
            </shiro:hasPermission>
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
          }
  );
  $(".dataDel").click(
          function()
          {
            var msg = "是否删除数据？";
            if (confirm(msg)==true){
              var uid=$(this).attr('uid');
              $.ajax({
                url:'${ctx}/dep/'+uid,
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
  )
</script>
</body>
</html>
