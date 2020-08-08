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
      <p class="main-content-div-index">新建车辆商品库<span class="page-error">${errorMsg}</span></p>
      <form:form action="${ctx}/vehicleProduct" method="post">
        <div class="form-group">
          <div class="col-md-8">
            <label>品牌/车型/车款</label>
            <div class="input-group">
                <select name="pinPaiID" id="pinPaiID" class="form-control">
                    <c:forEach items="${pinPai}" var="obj">
                        <option value="${obj.pinPaiID}">${obj.pinPai}</option>
                    </c:forEach>
              </select>
              <select name="cheXingID" id="cheXingID" class="form-control">
                <c:forEach items="${cheXing}" var="obj">
                  <option value="${obj.cheXingID}">${obj.cheXing}</option>
                </c:forEach>
              </select>
              <select name="cheKuanID" id="cheKuanID" class="form-control">
                <c:forEach items="${cheKuan}" var="obj">
                  <option value="${obj.cheKuanID}">${obj.cheKuan}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>

        <div class="form-group">
          <div class="col-md-8">
            <label>颜色/指导价/销售价格</label>
            <div class="input-group">
              <select name="colorCode" id="colorCode" class="form-control">
                <c:forEach items="${vehicleInfo}" var="obj">
                  <option value="${obj.colorCode}">${obj.color}</option>
                </c:forEach>
              </select>
              <input type="number" name="zhidaojia" id="zhidaojia" class="form-control" value="${vehicleInfo.get(0).zhidaojia}" readonly="readonly"/>
              <input type="number" name="sellPrice" id="sellPrice" class="form-control" value="10000" />
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label>数量/定金/有效期</label>
            <div class="input-group">
              <input type="number" name="pCount" id="pCount" class="form-control" value="6" />
              <input type="number" name="advancePrice" id="advancePrice" class="form-control" value="1000" />
              <input type="date" name="yxq" id="yxq" class="form-control" value="<fmt:formatDate value='${yxq}' pattern='yyyy-MM-dd'/>"/>
          </div>
        </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="allowArea">可售城市</label>
            <div class="input-group">
              <input name="allowArea" id="allowArea" type="hidden" />
              <ul class="ztree" id="allowAreaList"></ul>
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <%--<shiro:hasPermission name="rest[baseVehicle:put]">--%>
              <input id="addBtn" class="btn btn-success" type="submit" value="新建">
            <%--</shiro:hasPermission>--%>
            <a href="${ctx}/vehicleProductList"><button type="button" class="btn btn-secondary">返回</button></a>
          </div>
        </div>
      </form:form>
    </div>
  </div>
  <%@include file="/includes/foot.jsp"%>
  <%@include file="/includes/nav.jsp"%>
</div>
<script type="text/javascript">

    $("#pinPaiID").change(
      function(){
        var pinPaiID=$("#pinPaiID").val();
        $.ajax({
            url:'${ctx}/vehicleMgr/getCheXing',
            type:'post',
            contentType:'application/json;charset=utf-8',
            data:JSON.stringify({pinPaiID:pinPaiID}),
            success:function(data) {
              if(data.code==true) {
                $("#cheXingID").empty();
                if(data.data.length>0){
                  var cheXingObj = document.getElementById("cheXingID");
                  for(var i=0;i<data.data.length;i++){
                    cheXingObj.options.add(new Option(data.data[i].cheXing, data.data[i].cheXingID));
                  }
                }
                var cheXingID=$("#cheXingID").val();
                if(cheXingID!=null&&cheXingID!="") {
                  $.ajax({
                    url:'${ctx}/vehicleMgr/getCheKuan',
                    type:'post',
                    contentType:'application/json;charset=utf-8',
                    data:JSON.stringify({pinPaiID:pinPaiID,cheXingID:cheXingID}),
                    success:function(data) {
                      if(data.code==true) {
                        $("#cheKuanID").empty();
                        if(data.data.length>0){
                          var cheKuanObj = document.getElementById("cheKuanID");
                          for(var i=0;i<data.data.length;i++){
                            cheKuanObj.options.add(new Option(data.data[i].cheKuan, data.data[i].cheKuanID));
                          }
                          var cheKuanID=$("#cheKuanID").val();
                          $.ajax({
                            url:'${ctx}/vehicleMgr/getVehicleInfo',
                            type:'post',
                            contentType:'application/json;charset=utf-8',
                            data:JSON.stringify({pinPaiID:pinPaiID,cheXingID:cheXingID,cheKuanID:cheKuanID}),
                            success:function(data) {
                              if(data.code==true) {
                                $("#colorCode").empty();
                                if(data.data.length>0){
                                  var colorCodeObj = document.getElementById("colorCode");
                                  for(var i=0;i<data.data.length;i++){
                                    colorCodeObj.options.add(new Option(data.data[i].color, data.data[i].colorCode));
                                  }
                                  $("#zhidaojia").attr("value",data.data[0].zhidaojia);
                                }
                              }
                            }
                          })
                        }
                      }
                    }
                  })
                }
              }
            }
          })
        }
    )
    $("#cheXingID").change(
      function(){
        var pinPaiID=$("#pinPaiID").val();
        var cheXingID=$("#cheXingID").val();
        if(cheXingID!=null&&cheXingID!="") {
          $.ajax({
            url:'${ctx}/vehicleMgr/getCheKuan',
            type:'post',
            contentType:'application/json;charset=utf-8',
            data:JSON.stringify({pinPaiID:pinPaiID,cheXingID:cheXingID}),
            success:function(data) {
              if(data.code==true) {
                $("#cheKuanID").empty();
                if(data.data.length>0){
                  var cheKuanObj = document.getElementById("cheKuanID");
                  for(var i=0;i<data.data.length;i++){
                    cheKuanObj.options.add(new Option(data.data[i].cheKuan, data.data[i].cheKuanID));
                  }
                  var cheKuanID=$("#cheKuanID").val();
                  $.ajax({
                    url:'${ctx}/vehicleMgr/getVehicleInfo',
                    type:'post',
                    contentType:'application/json;charset=utf-8',
                    data:JSON.stringify({pinPaiID:pinPaiID,cheXingID:cheXingID,cheKuanID:cheKuanID}),
                    success:function(data) {
                      if(data.code==true) {
                        $("#colorCode").empty();
                        if(data.data.length>0){
                          var colorCodeObj = document.getElementById("colorCode");
                          for(var i=0;i<data.data.length;i++){
                            colorCodeObj.options.add(new Option(data.data[i].color, data.data[i].colorCode));
                          }
                          $("#zhidaojia").attr("value",data.data[0].zhidaojia);
                        }
                      }
                    }
                  })
                }
              }
            }
          })
        }
      }
    )
    $("#cheKuanID").change(
      function(){
        var pinPaiID=$("#pinPaiID").val();
        var cheXingID=$("#cheXingID").val();
        var cheKuanID=$("#cheKuanID").val();
        $.ajax({
          url:'${ctx}/vehicleMgr/getVehicleInfo',
          type:'post',
          contentType:'application/json;charset=utf-8',
          data:JSON.stringify({pinPaiID:pinPaiID,cheXingID:cheXingID,cheKuanID:cheKuanID}),
          success:function(data) {
            if(data.code==true) {
              $("#colorCode").empty();
              if(data.data.length>0){
                var colorCodeObj = document.getElementById("colorCode");
                for(var i=0;i<data.data.length;i++){
                  colorCodeObj.options.add(new Option(data.data[i].color, data.data[i].colorCode));
                }
                $("#zhidaojia").attr("value",data.data[0].zhidaojia);
              }
            }
          }
        })
      }
    )

    function initMenu()
    {
      var setting = {
        async : {
          enable : true, // 设置 zTree是否开启异步加载模式
          url:'${ctx}/getCity'
        },
        data:{
          simpleData:{
            enable:true,
            // idKey:"id",
            // pidKey:"pId",
            // rootPId : null
          }
        },
        view:{
          selectMulti:false
        },
        check:{
          enable:true,
          chkStyle:"checkbox",
          checkboxType:{p:"",s:""},
          radioType:"level"
        },
        callback:{
          onClick : function(event, treeId, treeNode, clickFlag) {
            // 判断是否父节点
            if(!treeNode.isParent){}
          },
          //捕获异步加载出现异常错误的事件回调函数 和 成功的回调函数
          // beforeAsync: beforeAsync,
          // onAsyncError : zTreeOnAsyncError,
          onAsyncSuccess : zTreeOnAsyncSuccess,
          onCheck: onCheck
        }
      };
      $(document).ready(function() {
        $.fn.zTree.init($("#allowAreaList"), setting);
      });
    }
    function zTreeOnAsyncSuccess() {
      var allowArea = $("#allowArea").val();
      var item = allowArea.split(",");
      if(item!=""&&item.length>0)
      {
        var zTree = $.fn.zTree.getZTreeObj("allowAreaList"), nodes = zTree.getNodes();
        var node = zTree.transformToArray(nodes); //获取树所有节点
        for(var j=0;j<item.length;j++) {
          for (var i = 0, m = node.length; i < m; i++) {
            if (node[i].level == 2 && node[i].id==Number(item[j])) {
              node[i].checked = true;
              zTree.updateNode(node[i]);
              continue;
            }
          }
        }
      }
    }
    function onCheck(e, treeId, treeNode) {
      var zTree = $.fn.zTree.getZTreeObj("allowAreaList"), nodes = zTree.getCheckedNodes(true), v = "";
      for (var i=0, l=nodes.length; i<l; i++) {
        if(nodes[i].level==2)
        {
          v += nodes[i].id + ",";
        }
      }
      if (v.length > 0 ) v = v.substring(0, v.length-1);
      $("#allowArea").attr("value", v);
    }
    function filter(treeId, parentNode, childNodes) {
      if (!childNodes)
        return null;
      for ( var i = 0, l = childNodes.length; i < l; i++) {
        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
      }
      return childNodes;
    }
    $(function(){
      initMenu();
    }
    )
</script>
</body>
</html>
