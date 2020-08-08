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
      <p class="main-content-div-index">调整购物车<span class="page-error">${errorMsg}</span></p>
      <form:form method="post" action="${ctx}/vehicleOrder">
        <input type="hidden" name="_method" value="put">
        <div class="form-group">
          <div class="col-md-8">
            <label>团购产品</label>
            <div class="input-group">
              <input type="hidden" name="id" id="id" class="form-control"  value="${order.id}" readonly="readonly"/>
              <input type="hidden" name="tgID" id="tgID" class="form-control"  value="${order.tuanGou.tgID}" readonly="readonly"/>
              <input type="hidden" name="tgCount" id="tgCount" class="form-control"  value="${order.tuanGou.tgCount} " readonly="readonly"/>
              <input type="text" class="form-control"  value="${order.tuanGou.product.info.pinPai} ${order.tuanGou.product.info.cheXing} ${order.tuanGou.product.info.cheKuan} ${order.tuanGou.product.info.color}" readonly="readonly"/>

          </div>
         </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label>指导价/团购截至日期/提货城市/数量</label>
            <div class="input-group">
              <input type="number" name="zhidaojia" id="zhidaojia" class="form-control"  value="${order.tuanGou.product.info.zhidaojia}" readonly="readonly"/>
              <input type="date" name="tgJZDate" id="tgJZDate"  value="<fmt:formatDate value='${order.tuanGou.tgJZDate}' pattern='yyyy-MM-dd'/>"  class="form-control"  readonly="readonly" />
              <input type="text" name="city.districtCode" id="city.districtCode" value="${order.tuanGou.city.districtName}" class="form-control"  readonly="readonly"/>
              <input type="number" name="count" id="count" class="form-control"  value="1" />
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label>定金/定金总额</label>
            <div class="input-group">
              <input type="number" name="advancePrice" id="advancePrice" class="form-control"  value="${order.tuanGou.product.advancePrice}" readonly="readonly"/>
              <input type="number" name="totalAdvance" id="totalAdvance" class="form-control"  value="${order.totalAdvance}" readonly="readonly"/>
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label>价格/总额</label>
            <div class="input-group">
              <input type="number" name="sellPrice" id="sellPrice" class="form-control"  value="${order.tuanGou.product.sellPrice}" readonly="readonly"/>
              <input type="number" name="totalPrice" id="totalPrice" class="form-control"  value="${order.totalPrice}" readonly="readonly"/>
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <input id="addBtn" class="btn btn-success" type="submit" value="修改">
            <a href="${ctx}/vehicleOrderList"><button type="button" class="btn btn-secondary">取消</button></a>
          </div>
        </div>
      </form:form>
    </div>
  </div>
  <%@include file="/includes/foot.jsp"%>
  <%@include file="/includes/nav.jsp"%>
</div>
<script type="text/javascript">

  $("#count").change(
    function(){
      var count= parseInt($("#count").val());
      var tgCount= parseInt($("#tgCount").val());
      if(count<0){
        alert("数量不能低于1");
        return;
      }
      if(count>tgCount){
        alert("数量不能超过本此团购最大数量"+tgCount);
        return;
      }
      var advancePrice=$("#advancePrice").val();
      var sellPrice=$("#sellPrice").val();
      $("#totalAdvance").attr("value",advancePrice*count);
      $("#totalPrice").attr("value",sellPrice*count);
    }
  )
</script>
</body>
</html>
