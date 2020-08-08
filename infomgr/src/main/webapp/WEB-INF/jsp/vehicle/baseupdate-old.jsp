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
      <p class="main-content-div-index">修改基础库<span class="page-error">${errorMsg}</span></p>
      <form:form action="${ctx}/dep" method="post" modelAttribute="baseVehicle">
        <input type="hidden" name="_method" value="put">

        <div class="form-group">
          <div class="col-md-8">
            <label for="pinPai">品牌</label>
            <select name="pinPai" id="pinPai" class="form-control">
             <c:forEach items="${pinPai}" var="obj">
               <option value ="${obj.baseID}">${obj.baseContent}</option>
             </c:forEach>
            </select>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="cheXing">车型</label>
            <select name="cheXing" id="cheXing" class="form-control" <c:if test="${cheXing== null && fn:length(cheXing) ==0}">disabled="true"</c:if>>
              <c:forEach items="${cheXing}" var="obj">
                <option value ="${obj.baseID}">${obj.baseContent}</option>
              </c:forEach>
            </select>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="cheKuan">车款</label>
            <select name="cheKuan" id="cheKuan" class="form-control"    <c:if test="${cheKuan== null || fn:length(cheKuan) == 0}">disabled="true"</c:if>>

              <c:forEach items="${cheKuan}" var="obj">
                <option value ="${obj.baseID}">${obj.baseContent}</option>
              </c:forEach>
            </select>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <shiro:hasPermission name="rest[dep:put]">
              <input id="addBtn" class="btn btn-success" type="submit" value="更新">
            </shiro:hasPermission>
            <a href="${ctx}/baselist"><button type="button" class="btn btn-secondary">返回</button></a>
          </div>
        </div>
      </form:form>
    </div>
  </div>
  <%@include file="/includes/foot.jsp"%>
  <%@include file="/includes/nav.jsp"%>
</div>
<script type="text/javascript">
  $(function(){
    $("#accountID").change(
      function () {
        var accountID=$("#accountID").val();
        if(accountID=="0")
        {
          $("#accountBank").val("");
          $("#accountNumber").val("");
        }
        else
        {
          $.ajax({
            url:"${ctx}/accounts/"+accountID,
            type:"post",
            contentType:"application/json;charset=utf-8",
            success:function (data) {
              $("#accountBank").val(data.accountBank);
              $("#accountNumber").val(data.accountNumber);
            }
          });
        }
      }
    )
  })
</script>
</body>
</html>
