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
      <p class="main-content-div-index">提交申请单<span class="page-error">${errorMsg}</span></p>
      <form:form action="${ctx}/order" method="post" modelAttribute="order">
      <input type="hidden" name="_method" value="put">
      <div class="form-group">
        <div class="row">
          <div class="col-md-4">
            <label for="rbID">人保订单</label>
            <input name="orderID" id="orderID" class="form-control" value="${order.orderID}" type="hidden" >
            <input name="orderZt" id="orderZt" class="form-control" value="${order.orderZt}" type="hidden" >
            <input name="rbID" id="rbID" class="form-control" value="${order.rbID!=null?order.rbID:''}" type="text" readonly="readonly">
          </div>
          <div class="col-md-4">
            <label for="orderCustom" >用户姓名</label><span class="static-error">*</span><form:errors path="orderCustom" cssClass="form-error"></form:errors>
            <input name="orderCustom" id="orderCustom" class="form-control"  value="${order.orderCustom!=null?order.orderCustom:''}" type="text" <c:if test="${order.orderZt!='00'}">readonly="readonly"</c:if> required >
          </div>
          <div class="col-md-4">
            <label for="orderCustomID" >用户身份证号码</label><span class="static-error">*</span><span class="span1"><form:errors path="orderCustomID" cssClass="errorMsg"></form:errors></span>
            <input name="orderCustomID" id="orderCustomID" class="form-control" value="${order.orderCustomID!=null?order.orderCustomID:''}" type="text" <c:if test="${order.orderZt!='00'}">readonly="readonly"</c:if>  required>
          </div>
        </div>
      </div>
      <div class="form-group">
        <div class="row">
          <div class="col-md-4">
            <label for="orderPrepay" >首付款</label><span class="static-error">*</span><form:errors path="orderPrepay" cssClass="form-error"></form:errors>
            <input name="orderPrepay" id="orderPrepay" class="form-control" value="${order.orderPrepay!=null?order.orderPrepay:'0'}" type="number" <c:if test="${order.orderZt!='00'}">readonly="readonly"</c:if>  required>
          </div>
          <div class="col-md-4">
            <label for="orderLoanvalue" >贷款额</label><span class="static-error">*</span><form:errors path="orderLoanvalue" cssClass="form-error"></form:errors>
            <input name="orderLoanvalue" id="orderLoanvalue" class="form-control" value="${order.orderLoanvalue!=null?order.orderLoanvalue:'0'}" type="number" <c:if test="${order.orderZt!='00'}">readonly="readonly"</c:if>  required>
          </div>
          <div class="col-md-4">
            <label for="orderSfdk" >是否垫款</label>
            <select class="form-control"  name="orderSfdk" id="orderSfdk" <c:if test="${order.orderZt!='00'}">disabled="disabled"</c:if> >
              <option value="0" <c:if test="${order.orderSfdk!=null and order.orderSfdk=='0'}">selected</c:if>>不垫款</option>
              <option value="1" <c:if test="${order.orderSfdk!=null and order.orderSfdk=='1'}">selected</c:if>>垫款</option>
            </select>
          </div>
        </div>
      </div>
      <div class="form-group">
        <div class="row">
          <div class="col-md-4">
            <label for="orderPins" >履约险</label>
            <input name="orderPins" id="orderPins" class="form-control" value="${order.orderPins!=null?order.orderPins:'0'}" type="text" readonly="readonly">
          </div>
          <div class="col-md-4">
            <label for="orderYwins" >人身意外险</label>
            <input name="orderYwins" id="orderYwins" class="form-control" value="${order.orderYwins!=null?order.orderYwins:'0'}" type="text" readonly="readonly">
          </div>
          <div class="col-md-4">
            <label for="orderSerpay" >服务费</label>
            <input name="orderSerpay" id="orderSerpay" class="form-control"  value="${order.orderSerpay!=null?order.orderSerpay:'0'}"  type="text" readonly="readonly">
          </div>
        </div>
      </div>
      <div class="form-group">
        <div class="row">
          <div class="col-md-4">
            <label for="orderRisk" >风险保证金</label>
            <input name="orderRisk" id="orderRisk" class="form-control" value="${order.orderRisk!=null?order.orderRisk:'0'}"  type="text" readonly="readonly">
          </div>
          <div class="col-md-4">
            <label for="orderConsloan">垫款服务费</label>
            <input name="orderConsloan" id="orderConsloan" class="form-control"  value="${order.orderConsloan!=null?order.orderConsloan:'0'}" type="text" readonly="readonly">
          </div>
          <div class="col-md-4">
            <label for="orderFktotal" >打款总额</label>
            <input name="orderFktotal" id="orderFktotal" class="form-control"  value="${order.orderFktotal!=null?order.orderFktotal:'0'}" type="text" readonly="readonly">
          </div>
        </div>
      </div>
      <div class="form-group">
        <div class="row">
          <div class="col-md-8">
            <label for="accountBank" >打款开户行</label>
            <input name="accountBank" id="accountBank" class="form-control" value="${account!=null?account.accountBank:''}" type="text" readonly="readonly">
          </div>
        </div>
      </div>
      <div class="form-group">
        <div class="row">
          <div class="col-md-8">
            <label for="accountBook" >打款户名</label>
            <input name="accountBook" id="accountBook" class="form-control"  value="${account!=null?account.accountName:''}" type="text" readonly="readonly">
          </div>
          <div class="col-md-4">
            <label for="accountNumber" >打款账号</label>
            <input name="accountNumber" id="accountNumber" class="form-control" value="${account!=null?account.accountNumber:''}" type="text" readonly="readonly">
          </div>
        </div>
      </div>

      <c:forEach items='${orderPicList}' var="obj">
        <c:if test="${obj.orderZt=='00'}">
          <div class="form-group">
            <label>${obj.pic.picName}</label>
            <c:if  test="${order.orderZt=='00'}">
            <input type="file" name="${obj.pic.picID}" id="${obj.pic.picID}" accept="image/png,image/jpeg" <c:if test="${order.orderZt!='00'}">disabled="disabled"</c:if>>
            <input type="button"  value="上传"  onclick="fileUpload('${obj.pic.picID}')">
            </c:if>
            <img onclick="showImg(this.name)" id="${obj.pic.picID}img" name="${obj.pic.picUrl}" width="40px" height="40px" alt="未传" src="${ctx}/${obj.pic.picThumbUrl}">
          </div>
        </c:if>
      </c:forEach>
      <shiro:hasPermission name="rest[order:put]">
        <c:if test="${order.orderZt=='00'}">
          <input id="addBtn" class="btn btn-success" type="submit" value="提交">
        </c:if>
      </shiro:hasPermission>
        <a href="${ctx}/orders"><button type="button" class="btn btn-secondary">返回</button></a>
    </form:form>
    </div>
  </div>
  <%@include file="/includes/foot.jsp"%>
  <%@include file="/includes/nav.jsp"%>
</div>
<script type="text/javascript">
  function fileUpload(id){
    var zpTmp=$('#'+id).val();
    if(zpTmp==null||zpTmp=='')
    {
      alert("未选照片");
      return ;
    }
    $.ajaxFileUpload({
      url:'${ctx}/uploadPic/'+id,
      secureuri:false,         //是否启用安全提交,默认为false
      type:'post',
      // dataType:'json',         //服务器返回的格式,可以是json或xml等
      dataType:'text',//解决IE上传文件，弹出下载框的问题
      fileElementId:id,        //文件选择框的id属性
      success:function(data) { //服务器响应成功时的处理函数
        data=JSON.parse(data);
        if(data.code==true)
        {
          $('#'+id+'img').attr("src", '${ctx}/'+data.data.picThumbUrl+"?time=" + new Date().getTime());
          $('#'+id+'img').attr("name",data.data.picUrl);
        }
        else {
          alert(data.msg);
        }
      }
    })
  }
  function showImg(picUrl)
  {
    if(picUrl==null||picUrl=='')
    {
      alert("还未上传图片");
      return;
    }
    top.location.href="${ctx}/picDetail.jsp?picUrl="+picUrl;
  }
  $(function(){
    $('#orderPrepay').blur(
            function(){
              var orderPrepay=$("#orderPrepay").val();
              var orderLoanvalue=$("#orderLoanvalue").val();
              var orderSfdk=$("#orderSfdk").val();
              var jsonStr={orderPrepay:orderPrepay,orderLoanvalue:orderLoanvalue,orderSfdk:orderSfdk}
              $("#addBtn").attr("disabled","disabled");
              $.ajax({
                url:'${ctx}/orderCaculate',
                type:'post',
                contentType:'application/json;charset=utf-8',
                data:JSON.stringify(jsonStr),
                success:function(data)
                {
                  if(data.code)
                  {
                    $('#orderPrepay').val(data.data.orderPrepay);
                    $('#orderPins').val(data.data.orderPins);
                    $('#orderYwins').val(data.data.orderYwins);
                    $('#orderSerpay').val(data.data.orderSerpay);
                    $('#orderRisk').val(data.data.orderRisk);
                    $('#orderConsloan').val(data.data.orderConsloan);
                    $('#orderFktotal').val(data.data.orderFktotal);
                  }
                  else
                  {
                    $(".page-error").html(data.msg);
                  }
                  $("#addBtn").removeAttr("disabled");
                }
              })
            }
    );
    $('#orderLoanvalue').blur(
            function(){
              var orderPrepay=$("#orderPrepay").val();
              var orderLoanvalue=$("#orderLoanvalue").val();
              var orderSfdk=$("#orderSfdk").val();
              var jsonStr={orderPrepay:orderPrepay,orderLoanvalue:orderLoanvalue,orderSfdk:orderSfdk}
              $("#addBtn").attr("disabled","disabled");
              $.ajax({
                url:'${ctx}/orderCaculate',
                type:'post',
                contentType:'application/json;charset=utf-8',
                data:JSON.stringify(jsonStr),
                success:function(data)
                {
                  if(data.code)
                  {
                    $('#orderPrepay').val(data.data.orderPrepay);
                    $('#orderPins').val(data.data.orderPins);
                    $('#orderYwins').val(data.data.orderYwins);
                    $('#orderSerpay').val(data.data.orderSerpay);
                    $('#orderRisk').val(data.data.orderRisk);
                    $('#orderConsloan').val(data.data.orderConsloan);
                    $('#orderFktotal').val(data.data.orderFktotal);
                  }
                  else
                  {
                    $(".page-error").html(data.msg);
                  }
                  $("#addBtn").removeAttr("disabled");
                }
              })
            }
    );
    $('#orderSfdk').change(
            function(){
              var orderPrepay=$("#orderPrepay").val();
              var orderLoanvalue=$("#orderLoanvalue").val();
              var orderSfdk=$("#orderSfdk").val();
              var jsonStr={orderPrepay:orderPrepay,orderLoanvalue:orderLoanvalue,orderSfdk:orderSfdk}
              $("#addBtn").attr("disabled","disabled");
              $.ajax({
                url:'${ctx}/orderCaculate',
                type:'post',
                contentType:'application/json;charset=utf-8',
                data:JSON.stringify(jsonStr),
                success:function(data)
                {
                  if(data.code)
                  {
                    $('#orderPrepay').val(data.data.orderPrepay);
                    $('#orderPins').val(data.data.orderPins);
                    $('#orderYwins').val(data.data.orderYwins);
                    $('#orderSerpay').val(data.data.orderSerpay);
                    $('#orderRisk').val(data.data.orderRisk);
                    $('#orderConsloan').val(data.data.orderConsloan);
                    $('#orderFktotal').val(data.data.orderFktotal);
                  }
                  else
                  {
                    $(".page-error").html(data.msg);
                  }
                  $("#addBtn").removeAttr("disabled");
                }
              })
            }
    );
  })
</script>
</body>
</html>
