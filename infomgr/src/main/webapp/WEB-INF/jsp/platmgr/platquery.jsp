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
            <div class="layui-inline">
                <label class="layui-form-label"></label>
                <div class="layui-input-inline">
                    <input name="phone" class="layui-input" type="tel" autocomplete="off" lay-verify="required|phone">
                    <label>选择平台</label>
                    <select id ='pt'>
                        <c:forEach items="${ptlist}" var="obj">
                            <option value ="${obj.id}">${obj.name}</option>
                        </c:forEach>
                    </select>
                    <label>选择省份</label>
                    <select id ='pro'>
                        <c:forEach items="${provlist}" var="obj">
                            <option value ="${obj.dmz}">${obj.dmsm1}</option>
                        </c:forEach>
                    </select>
                    <button class="layui-btn layui-btn-normal" id="queryvalue">查询</button>
                </div>
             </div>

                <table class="layui-table">
                    <thead>
                    <tr>
                        <td>平台</td>
                        <td>省份</td>
                        <td>注册城市</td>
                        <td>城市接入</td>
                        <td>五车接入</td>
                    </tr>
                    </thead>
                    <c:forEach items="${pageInfo.list}" var="obj">
                        <tr>
                            <td>${obj.id}</td>
                            <td>${obj.name}</td>
                            <td>
                                <a style="color: #FF0000" href="/platbasic/updatepage/${obj.id}">修改</a>
                                <a style="color: #FF0000" href="/platbasic/delete/${obj.id}">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                  <thfoot>
                      <tr>
                          <td colspan="3">
                              <div>
                                  当前 ${pageInfo.pageNum }页,总${pageInfo.pages}页,总 ${pageInfo.total } 条记录
                               <a href="/platbasic/platPage?pageNo=${pageInfo.firstPage}">首页</a>
                              <c:if test="${pageInfo.hasPreviousPage }">
                                  <a href="/platbasic/platPage?pageNo=${pageInfo.pageNum-1}">上一页</a>
                              </c:if>
                              <c:if test="${pageInfo.hasNextPage }">
                                  <a href="/platbasic/platPage?pageNo=${pageInfo.pageNum+1}">下一页</a>
                              </c:if>
                              <a href="/platbasic/platPage?pageNo=${pageInfo.lastPage}">未页</a>

                              </div>
                          </td>
                      </tr>
                  </thfoot>
                </table>
            </div>
        </div>
    </div>
    <jsp:include page="../../../includes/foot.jsp"></jsp:include>
</div>
</body>
</html>

