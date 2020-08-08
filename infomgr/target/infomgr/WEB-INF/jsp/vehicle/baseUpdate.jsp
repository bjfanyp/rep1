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
      <form:form action="${ctx}/baseVehicle" method="post" modelAttribute="info">
        <input type="hidden" name="_method" value="put">

        <div class="form-group">
          <div class="col-md-8">
            <label for="pinPai">品牌</label>
            <input name="vehicleID" id="vehicleID" class="form-control"  hidden="hidden" value="${info.vehicleID}" type="text">
            <input name="pinPai" id="pinPai" class="form-control"  value="${info.pinPai}" type="text" <c:if test="${info.cheXing!=null||info.cheKuan!=null}">readonly="readonly" </c:if>>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="baseFirstLetter">品牌首字符</label>
            <div class="input-group">
              <input name="baseFirstLetter" id="baseFirstLetter" class="form-control" type="text" value="${vehicle.baseFirstLetter}" />
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label>品牌图标</label>
            <input type="hidden" name="photoUrl" id="photoUrl" class="form-control" value="${vehicle.baseImageUrl}"/>
            <input type="file" name="basepicID" id="basepicID" accept="image/png,image/jpeg">
            <input type="button"  value="上传"   onclick="fileUpload()">
            <img src="${ctx}/${vehicle.baseImageUrl}" name="picImg" id="picImg"  width="40px" height="40px">
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="cheXing">车型</label>
            <input name="cheXing" id="cheXing" class="form-control"  value="${info.cheXing}" type="text" <c:if test="${info.cheKuan!=null||info.cheXing==null}">readonly="readonly" </c:if>>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="cheKuan">车款</label>
            <input name="cheKuan" id="cheKuan" class="form-control"  value="${info.cheKuan}" type="text" <c:if test="${info.cheXing==null||info.cheKuan==null}">readonly="readonly" </c:if>>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="baseOrder">顺序号</label>
            <div class="input-group">
              <input name="baseOrder" id="baseOrder" class="form-control" type="text" value="${vehicle.baseOrder}"/>
            </div>
          </div>
        </div>

        <div class="form-group">
          <div class="col-md-8">
            <%--<shiro:hasPermission name="rest[baseVehicle:put]">--%>
              <input id="addBtn" class="btn btn-success" type="submit" value="更新">
            <%--</shiro:hasPermission>--%>
            <a href="${ctx}/baseList"><button type="button" class="btn btn-secondary">返回</button></a>
          </div>
        </div>
      </form:form>
    </div>
  </div>
  <%@include file="/includes/foot.jsp"%>
  <%@include file="/includes/nav.jsp"%>
</div>
<script type="text/javascript">
  function fileUpload(){
    var zpTmp=$('#basepicID').val();
    if(zpTmp==null||zpTmp=='')
    {
      alert("未选照片");
      return ;
    }
    $.ajaxFileUpload({
      url:'${ctx}/uploadIncon/',
      secureuri:false,         //是否启用安全提交,默认为false
      type:'post',
      // dataType:'json',         //服务器返回的格式,可以是json或xml等
      dataType:'text',//解决IE上传文件，弹出下载框的问题
      fileElementId:'basepicID',        //文件选择框的id属性
      success:function(data) { //服务器响应成功时的处理函数
        data=JSON.parse(data);
        if(data.code==true)
        {
          $('#picImg').attr("src", '${ctx}/'+data.data.photoUrl+"?time=" + new Date().getTime());
          $('#photoUrl').attr("value", data.data.photoUrl);
        }
        else {
          alert(data.msg);
        }
      }
    })
  };
</script>
</body>
</html>
