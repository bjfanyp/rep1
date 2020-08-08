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
      <p class="main-content-div-index">时间进度查看<span class="page-error">${errorMsg}</span></p>
      <form action="/orders" method="post">
        <div class="form-group">
          <div class="col-md-8">
            <label for="tjTime">提交时间</label>
            <input name="tjTime" id="tjTime" class="form-control" value="${timeProcess.tjTime!=null?timeProcess.tjTime:''}" type="text" readonly="readonly">
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="qrTime">收款确认时间</label>
            <input name="qrTime" id="qrTime" class="form-control" value="${timeProcess.qrTime!=null?timeProcess.qrTime:''}" type="text" readonly="readonly">
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="ckTime" >风控确认时间</label>
            <input name="ckTime" id="ckTime" class="form-control" value="${timeProcess.ckTime!=null?timeProcess.ckTime:''}" type="text" readonly="readonly">
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="fsTime" >放款审核时间</label>
            <input name="fsTime" id="fsTime" class="form-control" value="${timeProcess.fsTime!=null?timeProcess.fsTime:''}" type="text" readonly="readonly">
          </div>
        </div>

        <div class="form-group">
          <div class="col-md-8">
            <label for="fkTime" >放款时间</label>
            <input name="fkTime" id="fkTime" class="form-control" value="${timeProcess.fkTime!=null?timeProcess.fkTime:''}" type="text" readonly="readonly">
          </div>
        </div>

        <div class="form-group">
          <div class="col-md-8">
            <input class="btn btn-secondary" type="submit" value="返回">
          </div>
        </div>
      </form>
    </div>
  </div>
  <%@include file="/includes/foot.jsp"%>
  <%@include file="/includes/nav.jsp"%>
</div>
</body>
</html>
