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
      <p class="main-content-div-index">新建部门<span class="page-error">${errorMsg}</span></p>
      <form:form action="${ctx}/dep" method="post" modelAttribute="dep">
        <div class="form-group">
          <div class="col-md-8">
            <label for="depName">部门名称</label><span class="static-error">*</span><form:errors path="depName" cssClass="form-error"></form:errors>
            <input name="depName" id="depName" class="form-control"  value="${dep.depName}" type="text" required>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="depType">部门类型</label>
            <select name="depType" id="depType" class="form-control">
              <option value="20" <c:if test="${dep.depType=='20'}">selected</c:if>>一般渠道</option>
              <option value="10" <c:if test="${dep.depType=='10'}">selected</c:if>>直营店</option>
              <option value="60" <c:if test="${dep.depType=='60'}">selected</c:if>>垫资公司</option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="depQdfwf">服务费比率(%)</label><form:errors path="depQdfwf" cssClass="form-error"></form:errors>
            <input type=text t_value="" o_value=""  name="depQdfwf" id="depQdfwf" value="${dep.depQdfwf!=null?dep.depQdfwf:0}" class="form-control"  onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}">
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="accountID">打款户名</label>
            <select name="account.accountID" id="accountID" class="form-control">
              <option value="0">无</option>
              <c:forEach items="${accountList}" var="obj">
                <option value="${obj.accountID}" <c:if test="${dep.account.accountID==obj.accountID}">selected</c:if> >${obj.accountName}</option>
              </c:forEach>
            </select>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="accountBank">打款开户行</label>
            <input id="accountBank" name="account.accountBank" class="form-control" value="${dep.account.accountBank}"  type="text" readonly="readonly">
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <label for="accountNumber">打款账号</label>
            <input id="accountNumber" name="account.accountNumber" class="form-control"  value="${dep.account.accountNumber}"  type="text" readonly="readonly">
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-8">
            <shiro:hasPermission name="rest[dep:post]">
              <input id="addBtn" class="btn btn-success" type="submit" value="新建">
            </shiro:hasPermission>
            <a href="${ctx}/deps"><button type="button" class="btn btn-secondary">返回</button></a>
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
    $('#depName').blur(
      function(){
        $(".form-error").html("");
        $(".page-error").html("");
        var depName=$("#depName").val();
        if(depName!=null&&depName!="")
        { $.ajax({
          url:'${ctx}/depCheck',
          type:'post',
          contentType:'application/json;charset=utf-8',
          data:JSON.stringify({'depName':depName}),
          success:function(data)
          {
            if(data.code==false)
            {
              $(".page-error").html(data.msg);
            }
          }
        })
        }
      }
    );

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
