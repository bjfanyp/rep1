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
      <p class="main-content-div-index">新建角色<span class="page-error">${errorMsg}</span></p>
      <form:form action="${ctx}/role" method="post" modelAttribute="role">
        <div class="form-group">
          <div class="col-md-8">
            <label for="roleName">角色名称</label><span class="static-error">*</span><form:errors path="roleName" cssClass="form-error"></form:errors>
            <input name="roleName" id="roleName" value="${role.roleName}" class="form-control" type="text" required>
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
            <input name="permChoice" id="permChoice" type="hidden" value="${permChoice}" >
            <ul class="ztree" id="permList"></ul>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <shiro:hasPermission name="rest[role:post]">
              <input id="addBtn" class="btn btn-success" type="submit" value="新建">
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
      var setting = {
        async : {
          enable : true, // 设置 zTree是否开启异步加载模式
          url:'${ctx}/findMenuStr/0',
          autoParam : [ "id" ]	// 异步加载时自动提交父节点属性的参数,假设父节点 node = {id:1, name:"test"}，异步加载时，提交参数 zId=1
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
        $.fn.zTree.init($("#permList"), setting);
      });
    }
  function zTreeOnAsyncSuccess() {
    var perChoice = $("#permChoice").val();
    var item = perChoice.split(",");
    if(item!=""&&item.length>0)
    {
      var zTree = $.fn.zTree.getZTreeObj("permList"), nodes = zTree.getNodes();
      var node = zTree.transformToArray(nodes); //获取树所有节点
      for(var j=0;j<item.length;j++) {
        for (var i = 0, m = node.length; i < m; i++) {
          if (node[i].level == 3 && node[i].id==Number(item[j])) {
            node[i].checked = true;
            zTree.updateNode(node[i]);
            continue;
          }
        }
      }
    }
  }
  function onCheck(e, treeId, treeNode) {
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
  $(function(){
    initMenu();
    $('#roleName').blur(
      function(){
        $(".page-error").html("");
        $(".form-error").html("");
        var roleName=$("#roleName").val();
        if(roleName!=null&&roleName!="")
        { $.ajax({
          url:'${ctx}/roleCheck',
          type:'post',
          contentType:'application/json;charset=utf-8',
          data:JSON.stringify({roleName:roleName}),
          success:function(data)
          {
            if(data.code==false)
            {
              $("#roleName").val('');
              $(".page-error").html(data.msg);
            }
          }
        })
        }
      }
    );
  })
</script>
</body>
</html>
