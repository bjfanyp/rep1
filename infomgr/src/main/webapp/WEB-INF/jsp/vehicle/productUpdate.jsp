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
      <p class="main-content-div-index">修改车辆商品库<span class="page-error">${errorMsg}</span></p>
      <form:form action="${ctx}/vehicleProduct"  method="post">
        <input type="hidden" name="_method" value="put">
        <div class="form-group">
          <div class="col-md-8">
            <label>品牌/车型/车款</label>
            <div class="input-group">
              <input type="hidden" name="PID" id="PID" class="form-control" value="${productInfo.PID}"  readonly="readonly"/>
              <input type="text" name="pinPai" id="pinPai" class="form-control"  value="${productInfo.info.pinPai}" readonly="readonly"/>
              <input type="text" name="cheXing" id="cheXing" class="form-control"  value="${productInfo.info.cheXingID}"  readonly="readonly"/>
              <input type="text" name="cheKuan" id="cheKuan" class="form-control"  value="${productInfo.info.cheKuan}"  readonly="readonly"/>
            </div>
          </div>
        </div>

        <div class="form-group">
          <div class="col-md-8">
            <label>颜色/指导价/销售价格</label>
            <div class="input-group">
              <input type="text" name="color" id="color" class="form-control"  value="${productInfo.info.color}"  readonly="readonly"/>
              <input type="number" name="zhidaojia" id="zhidaojia" class="form-control"  value="${productInfo.info.zhidaojia}" readonly="readonly"/>
              <input type="number" name="sellPrice" id="sellPrice" class="form-control"  value="${productInfo.sellPrice}"/>
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label>数量/定金/有效期</label>
            <div class="input-group">
              <input type="number" name="pCount" id="pCount" class="form-control"  value="${productInfo.pCount}" />
              <input type="number" name="advancePrice" id="advancePrice" class="form-control"  value="${productInfo.advancePrice}" />
              <input type="date" name="yxq" id="yxq" class="form-control" value="<fmt:formatDate value='${productInfo.yxq}' pattern='yyyy-MM-dd'/>" >
            </div>
        </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="allowArea">可售城市</label>
            <div class="input-group">
              <input name="allowArea" id="allowArea" type="hidden"  value="${productInfo.allowArea}"/>
              <ul class="ztree" id="allowAreaList"></ul>
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <%--<shiro:hasPermission name="rest[baseVehicle:put]">--%>
              <input id="addBtn" class="btn btn-success" type="submit" value="修改">
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
    function initMenu()
    {
      var cityCode=$("#allowArea").val();
      var setting = {
        async : {
          enable : true, // 设置 zTree是否开启异步加载模式
          url:'${ctx}/getUpdateCity?city='+cityCode,
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
      var items = $("#allowArea").val();
      if (items == null||items=='') {
        var zTree = $.fn.zTree.getZTreeObj("allowAreaList"), nodes = zTree.getCheckedNodes(true), v = "";
        for (var i = 0, l = nodes.length; i < l; i++) {
          if (nodes[i].level == 2) {
            v += nodes[i].id + ",";
          }
        }
        if (v.length > 0)
        {    v = v.substring(0, v.length - 1);
          $("#allowArea").attr("value", v);
        }
      }
      else
      {
        var item = items.split(",");
        if(item!=""&&item.length>0)
        {
          var zTree1 = $.fn.zTree.getZTreeObj("allowAreaList"), nodes1 = zTree1.getNodes();
          var node1 = zTree1.transformToArray(nodes1); //获取树所有节点
          for(var j=0;j<item.length;j++) {
            for (var i = 0, m = node1.length; i < m; i++) {
              if (node1[i].level == 2 && node1[i].id==Number(item[j])) {
                node1[i].checked = true;
                zTree1.updateNode(node1[i]);
                continue;
              }
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
    window.onload = function(){
      initMenu();
    }
</script>
</body>
</html>
