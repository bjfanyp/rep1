<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
      <div align="center"><span><h1>修改平台</h1></span>
        <br><br>
        <form class="layui-form" action="${ctx}/platbasic/update/${platForm.id}" method="post">
          <label>平台名称:</label>
          <input name="name"  type="text" value=${platForm.name}>
          <input class="layui-btn layui-btn-normal" type="submit" value="修改">
        </form>
      </div>
    </div>
  </div>
  <jsp:include page="/includes/foot.jsp"></jsp:include>
</div>
</body>
</html>

