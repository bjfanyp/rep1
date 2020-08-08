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
      <p class="main-content-div-index">修改车型库<span class="page-error">${errorMsg}</span></p>
      <form:form action="${ctx}/vehicleMgr" method="post">
        <input type="hidden" name="_method" value="put">
        <input type="hidden" name="vehicleID" id="vehicleID" class="form-control" value="${vehicle.vehicleID}"/>
        <div class="form-group">
          <div class="col-md-8">
            <label for="pinPaiID">品牌</label>
            <div class="input-group">
                <select name="pinPaiID" id="pinPaiID" class="form-control" readonly="readonly" disabled="disabled">
                        <option value="${vehicle.pinPaiID}" selected>${vehicle.pinPai}</option>
              </select>
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="cheXingID">车型</label>
            <div class="input-group">
              <select name="cheXingID" id="cheXingID" class="form-control" readonly="readonly" disabled="disabled">
                  <option value="${vehicle.cheXingID}" selected>${vehicle.cheXing}</option>
              </select>
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="cheKuanID">车款</label>
            <div class="input-group">
              <select name="cheKuanID" id="cheKuanID" class="form-control" readonly="readonly" disabled="disabled">
                  <option value="${vehicle.cheKuanID}" selected>${vehicle.cheKuan}</option>
              </select>
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="colorCode">颜色</label>
            <div class="input-group">
              <select name="colorCode" id="colorCode" class="form-control">
                <c:forEach items="${color}" var="obj">
                <option value="${obj.dmz}" <c:if test="${obj.dmz==vehicle.colorCode}">selected</c:if>>${obj.dmsx}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="zhidaojia">指导价</label>
            <div class="input-group">
             <input type="number" name="zhidaojia" id="zhidaojia" class="form-control" value="${vehicle.zhidaojia}"/>
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label>外观照片</label>
            <input type="hidden" name="photoUrl" id="photoUrl" class="form-control" value="${vehicle.photoUrl}"/>
            <input type="hidden" name="thumbUrl" id="thumbUrl" class="form-control" value="${vehicle.thumbUrl}"/>
            <input type="file" name="picID" id="picID" accept="image/png,image/jpeg">
            <input type="button"  value="上传"   onclick="fileUpload()">
            <img onclick="showImg()"  src="${ctx}/${vehicle.thumbUrl}" name="picImg" id="picImg"  width="40px" height="40px">
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <%--<shiro:hasPermission name="rest[baseVehicle:put]">--%>
              <input id="addBtn" class="btn btn-success" type="submit" value="修改">
            <%--</shiro:hasPermission>--%>
            <a href="${ctx}/vehicleMgrList"><button type="button" class="btn btn-secondary">返回</button></a>
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
    var zpTmp=$('#picID').val();
    if(zpTmp==null||zpTmp=='')
    {
      alert("未选照片");
      return ;
    }
    $.ajaxFileUpload({
      url:'${ctx}/uploadPic/',
      secureuri:false,         //是否启用安全提交,默认为false
      type:'post',
      // dataType:'json',         //服务器返回的格式,可以是json或xml等
      dataType:'text',//解决IE上传文件，弹出下载框的问题
      fileElementId:'picID',        //文件选择框的id属性
      success:function(data) { //服务器响应成功时的处理函数
        data=JSON.parse(data);
        if(data.code==true)
        {
          $('#picImg').attr("src", '${ctx}/'+data.data.thumbUrl+"?time=" + new Date().getTime());
          $('#photoUrl').attr("value", data.data.photoUrl);
          $('#thumbUrl').attr("value", data.data.thumbUrl);
        }
        else {
          alert(data.msg);
        }
      }
    })
  }
</script>
</body>
</html>
