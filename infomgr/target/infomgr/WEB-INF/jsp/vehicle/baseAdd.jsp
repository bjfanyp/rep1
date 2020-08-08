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
      <p class="main-content-div-index">新建基础库<span class="page-error">${errorMsg}</span></p>
      <form:form action="${ctx}/baseVehicle" method="post">
        <div class="form-group">
          <div class="col-md-8">
            <label for="pinPaiText">品牌</label>
            <div class="input-group">
                <select name="pinPaiText" id="pinPaiText" class="form-control">
                    <option value="0"></option>
                    <c:forEach items="${pinPai}" var="obj">
                        <option value="${obj.baseID}">${obj.baseContent}</option>
                    </c:forEach>
              </select>
              <input name="pinPai" id="pinPai" class="form-control" type="text" value="${infomation.getPinPai()}"/>
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="baseFirstLetter">品牌首字符</label>
            <div class="input-group">
              <input name="baseFirstLetter" id="baseFirstLetter" class="form-control" type="text" />
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label>品牌图标</label>
            <input type="hidden" name="photoUrl" id="photoUrl" class="form-control"/>
            <input type="file" name="basepicID" id="basepicID" accept="image/png,image/jpeg">
            <input type="button"  value="上传"   onclick="fileUpload()">
            <img  name="picImg" id="picImg"  width="40px" height="40px" alt="未传">
          </div>
        </div>

        <div class="form-group">
          <div class="col-md-8">
            <label for="cheXingText">车型</label>
            <div class="input-group">
              <select name="cheXingText" id="cheXingText" class="form-control">
              </select>
              <input name="cheXing" id="cheXing" class="form-control" type="text" value="${infomation.getCheXing()}" />
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="cheKuanText">车款</label>
            <div class="input-group">
              <select name="cheKuanText" id="cheKuanText" class="form-control">
              </select>
              <input name="cheKuan" id="cheKuan" class="form-control" type="text" >
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="baseOrder">顺序号</label>
            <div class="input-group">
              <input name="baseOrder" id="baseOrder" class="form-control" type="text" />
            </div>
          </div>
        </div>

        <div class="form-group">
          <div class="col-md-8">
            <%--<shiro:hasPermission name="rest[baseVehicle:put]">--%>
              <input id="addBtn" class="btn btn-success" type="submit" value="新建">
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
    $("#pinPaiText").change(
      function(){
        var pinPaiID=$("#pinPaiText").val();
        var pinPaiTemp=$("#pinPaiText option:selected").text();
        $("#pinPai").attr("value", pinPaiTemp);
        if(pinPaiTemp!=null&&pinPaiTemp!="") {
          $.ajax({
            url:'${ctx}/getVehicleInfo',
            type:'post',
            contentType:'application/json;charset=utf-8',
            data:JSON.stringify({id:pinPaiID}),
            success:function(data) {
              if(data.code==true) {
                $("#cheXingText").empty();
                $("#cheKuanText").empty();
                $("#cheXing").attr("value", "");
                $("#cheKuan").attr("value", "");
                if(data.data.length>0){
                  var cheXingObj = document.getElementById("cheXingText");
                  cheXingObj.options.add(new Option("", 0));
                  for(var i=0;i<data.data.length;i++){
                    cheXingObj.options.add(new Option(data.data[i].baseContent, data.data[i].baseID));
                  }
                }
              }
            }
          })
        }else{
          $("#cheXingText").empty();
          $("#cheKuanText").empty();
          $("#cheXing").attr("value", "");
          $("#cheKuan").attr("value", "");
        }
      }
    )
    $("#cheXingText").change(
        function(){
          var cheXingID=$("#cheXingText").val();
          var cheXingTemp=$("#cheXingText option:selected").text();
          $("#cheXing").attr("value", cheXingTemp);
          if(cheXingTemp!=null&&cheXingTemp!="") {
            $.ajax({
              url:'${ctx}/getVehicleInfo',
              type:'post',
              contentType:'application/json;charset=utf-8',
              data:JSON.stringify({id:cheXingID}),
              success:function(data) {
                if(data.code==true) {
                  $("#cheKuanText").empty();
                  $("#cheKuan").attr("value", "");
                  if(data.data.length>0){
                    var cheKuanObj = document.getElementById("cheKuanText");
                    cheKuanObj.options.add(new Option("", 0));
                    for(var i=0;i<data.data.length;i++){
                      cheKuanObj.options.add(new Option(data.data[i].baseContent, data.data[i].baseID));
                    }
                  }
                }
              }
            })
          }else{
            $("#cheKuanText").empty();
            $("#cheKuan").attr("value", "");
          }
        }
    )
    $("#cheKuanText").change(
        function(){
          var cheKuanID=$("#cheKuanText").val();
          var cheKuanemp=$("#cheKuanText option:selected").text();
          $("#cheKuan").attr("value", cheKuanemp);
        }
    )
</script>
</body>
</html>
