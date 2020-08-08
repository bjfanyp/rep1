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
      <p class="main-content-div-index">发起团购<span class="page-error">${errorMsg}</span></p>
      <form:form method="post" action="${ctx}/vehicleTuanGou">
        <div class="form-group">
          <div class="col-md-8">
            <label>品牌/车型/车款</label>
            <div class="input-group">
              <input type="hidden" name="PID" id="PID" class="form-control"  value="${product.PID}" readonly="readonly"/>
              <input type="text" name="pinPai" id="pinPai" class="form-control"  value="${product.info.pinPai}" readonly="readonly"/>
              <input type="text" name="cheXing" id="cheXing" class="form-control"  value="${product.info.cheXing}"  readonly="readonly"/>
              <input type="text" name="cheKuan" id="cheKuan" class="form-control"  value="${product.info.cheKuan}"  readonly="readonly"/>
            </div>
          </div>
        </div>

        <div class="form-group">
          <div class="col-md-8">
            <label>指导价/颜色/销售价格</label>
            <div class="input-group">
              <input type="number" name="zhidaojia" id="zhidaojia" class="form-control"  value="${product.info.zhidaojia}" readonly="readonly"/>
              <input type="text" name="color" id="color" class="form-control"  value="${product.info.color}"  readonly="readonly"/>
              <input type="number" name="sellPrice" id="sellPrice" class="form-control"  value="${product.sellPrice}" readonly="readonly"/>
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label>定金/成团数/团购数量</label>
            <div class="input-group">
              <input type="number" name="advancePrice" id="advancePrice" class="form-control"  value="${product.advancePrice}" readonly="readonly"/>
              <input type="number" name="pCount" id="pCount" class="form-control"  value="${product.pKTCount}" readonly="readonly" />
              <input type="number" name="tgCount" id="tgCount" class="form-control"  value="6" />
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label>产品有效期/团购截至日期/提货城市</label>
            <div class="input-group">
              <input type="date" name="yxq" id="yxq" class="form-control" value="<fmt:formatDate value='${product.yxq}' pattern='yyyy-MM-dd'/>"  readonly="readonly">
              <input type="date" name="tgJZDate" id="tgJZDate"  value="<fmt:formatDate value='${product.yxq}' pattern='yyyy-MM-dd'/>"  class="form-control" />
              <select id="city.districtCode" name="city.districtCode" class="form-control" >
                <c:forEach items="${citys}" var="obj">
                  <option value="${obj.districtCode}">${obj.districtName}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <input id="addBtn" class="btn btn-success" type="submit" value="发起团购">
            <a href="${ctx}/vehicleTuanGouList"><button type="button" class="btn btn-secondary">返回</button></a>
          </div>
        </div>
      </form:form>
    </div>
  </div>
  <%@include file="/includes/foot.jsp"%>
  <%@include file="/includes/nav.jsp"%>
</div>
</body>
</html>
