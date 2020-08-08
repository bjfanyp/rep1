<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String banner_path = request.getContextPath();
    String banner_basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + banner_path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>五车网业务管理系统</title>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <jsp:include page="/includes/head.jsp"></jsp:include>
    <div class="layui-body">
        <div style="padding: 15px;">
            <div align="center"><span><h1>平台基本信息管理</h1></span></div>
               <form  action='${ctx}/platbasic/addpage'><input class="layui-btn layui-btn-normal" type="submit" value="新建"></form>
            <div>
                <table class="layui-table">
                    <thead>
                    <tr>
                        <td>ID</td>
                        <td>资源名称</td>
                        <td>操作</td>
                    </tr>
                    </thead>
                    <c:forEach items="${pageInfo.list}" var="obj">
                        <tr>
                            <td>${obj.id}</td>
                            <td>${obj.name}</td>
                            <td>
                                <a style="color: #FF0000" href="${ctx}/platbasic/updatepage/${obj.id}">修改</a>
                                <a style="color: #FF0000" href="${ctx}/platbasic/delete/${obj.id}">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                  <thfoot>
                      <tr>
                          <td colspan="3">
                              <div>
                                  当前 ${pageInfo.pageNum }页,总${pageInfo.pages}页,总 ${pageInfo.total } 条记录
                               <a href="${ctx}/platbasic/platPage?pageNo=${pageInfo.firstPage}">首页</a>
                              <c:if test="${pageInfo.hasPreviousPage }">
                                  <a href="${ctx}/platbasic/platPage?pageNo=${pageInfo.pageNum-1}">上一页</a>
                              </c:if>
                              <c:if test="${pageInfo.hasNextPage }">
                                  <a href="${ctx}/platbasic/platPage?pageNo=${pageInfo.pageNum+1}">下一页</a>
                              </c:if>
                              <a href="${ctx}/platbasic/platPage?pageNo=${pageInfo.lastPage}">未页</a>

                              </div>
                          </td>
                      </tr>
                  </thfoot>
                </table>
            </div>
        </div>
    </div>
    <jsp:include page="/includes/foot.jsp"></jsp:include>
</div>
</body>
</html>

