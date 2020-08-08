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
      <p class="main-content-div-index">修改角色<span class="page-error">${errorMsg}</span></p>
      <form:form action="${ctx}/role" method="post" modelAttribute="role">
        <input type="hidden" name="_method" value="put">
        <div class="form-group">
          <div class="col-md-8">
            <input name="roleID" id="roleID" value="${role.roleID}"  type="hidden">
            <label for="roleName">角色名称</label>
            <input name="roleName" id="roleName" value="${role.roleName}" class="form-control" type="text" readonly="readonly">
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="roleDescript">角色描述</label><form:errors path="roleDescript" cssClass="form-error"></form:errors>
            <input id="roleDescript" name="roleDescript" class="form-control" value="${role.roleDescript}"  type="text">
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="permList">选择权限</label>
            <input  name="permChoice" id="permChoice" type="hidden" value="${permChoice}">
            <ul class="ztree" id="permList"></ul>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <shiro:hasPermission name="rest[role:put]">
              <input id="addBtn" class="btn btn-success" type="submit" value="修改">
            </shiro:hasPermission>
            <a href="${ctx}/roles"><button type="button" class="btn btn-secondary">返回</button></a>
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
    var roleID=$("#roleID").val();
    var setting = {
      async : {
        enable : true, // 设置 zTree是否开启异步加载模式
        url:'${ctx}/findMenuStr/'+roleID,
        autoParam : [ "id" ]	// 异步加载时自动提交父节点属性的参数,假设父节点 node = {id:1, name:"test"}，异步加载时，提交参数 zId=1
      },
      data:{
        simpleData:{
          enable:true,
          idKey:"id",
          pidKey:"pId",
          rootPId : null
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
      $.fn.zTree.init($("#permList"), setting);
    });
  }
  function onCheck(event, treeId, treeNode, clickFlag) {
    var zTree = $.fn.zTree.getZTreeObj("permList"), nodes = zTree.getCheckedNodes(true), v = "";
    for (var i=0, l=nodes.length; i<l; i++) {
      if(nodes[i].level==3)
      {
        v += nodes[i].id + ",";
      }
    }
    if (v.length > 0 ) v = v.substring(0, v.length-1);
    $("#permChoice").attr("value", v);
  }
  function filter(treeId, parentNode, childNodes) {
    if (!childNodes)
      return null;
    for ( var i = 0, l = childNodes.length; i < l; i++) {
      childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
    }
    return childNodes;
  }
  function zTreeOnAsyncSuccess(event, treeId, treeNode, clickFlag) {
    var items = $("#permChoice").val();
    if (items == null||items=='') {
    var zTree = $.fn.zTree.getZTreeObj("permList"), nodes = zTree.getCheckedNodes(true), v = "";
    for (var i = 0, l = nodes.length; i < l; i++) {
      if (nodes[i].level == 3) {
        v += nodes[i].id + ",";
      }
    }
    if (v.length > 0)
    {    v = v.substring(0, v.length - 1);
      $("#permChoice").attr("value", v);
    }
  }
  else
    {
      var item = items.split(",");
      if(item!=""&&item.length>0)
      {
        var zTree1 = $.fn.zTree.getZTreeObj("permList"), nodes1 = zTree1.getNodes();
        var node1 = zTree1.transformToArray(nodes1); //获取树所有节点
        for(var j=0;j<item.length;j++) {
          for (var i = 0, m = node1.length; i < m; i++) {
            if (node1[i].level == 3 && node1[i].id==Number(item[j])) {
              node1[i].checked = true;
              zTree1.updateNode(node1[i]);
              continue;
            }
          }
        }
      }
    }
  }
  $(function(){
    initMenu();
  })
</script>
</body>
</html>
