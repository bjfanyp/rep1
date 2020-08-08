<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
      <fieldset>
        <form class="form-horizontal" action="${ctx}/orders" method="post">
          <shiro:hasPermission name="orders">
            <div class="form-group">
              <div class="row">
                <div class="col-md-4">
                  <label for="rbID" class="font-border">人保单号</label>
                  <input type="text" class="form-control" id="rbID" name="rbID" value="${autoFinanceQuery.rbID}">
                  <input type="hidden" id="pageNum" name="pageNum">
                </div>
                <div class="col-md-4">
                  <label for="orderZt" class="font-border">订单状态</label>
                  <select class="form-control" id="orderZt" name="orderZt" >
                    <option value="0"  <c:if test="${autoFinanceQuery.orderZt=='0' }||${autoFinanceQuery.orderZt==null}">selected</c:if>>全部</option>
                    <option value="00" <c:if test="${autoFinanceQuery.orderZt=='00'}">selected</c:if>>待提交</option>
                    <option value="01" <c:if test="${autoFinanceQuery.orderZt=='01'}">selected</c:if>>已提交</option>
                    <option value="10" <c:if test="${autoFinanceQuery.orderZt=='10'}">selected</c:if>>待风控审核</option>
                    <option value="11" <c:if test="${autoFinanceQuery.orderZt=='11'}">selected</c:if>>待出履约险</option>
                    <option value="20" <c:if test="${autoFinanceQuery.orderZt=='20'}">selected</c:if>>放款中</option>
                    <option value="30" <c:if test="${autoFinanceQuery.orderZt=='30'}">selected</c:if>>放款确认</option>
                    <option value="40" <c:if test="${autoFinanceQuery.orderZt=='40'}">selected</c:if>>已放款</option>
                  </select>
                </div>
              </div>
            </div>
          </shiro:hasPermission>
          <div class="form-group">
            <div class="row">
              <shiro:hasPermission name="orders">
                <c:if test="${sessionScope.currentUser.dep.depType!='10'&&sessionScope.currentUser.dep.depType!='20'}">
                <div class="col-md-4">
                  <label for="depID" class="font-border">渠道名称</label>
                  <select name="depID" id="depID" class="form-control">
                    <option value="0" <c:if test="${autoFinanceQuery.depID==null}">selected</c:if>>全部</option>
                    <c:forEach items="${depSessionList}" var="obj">
                      <option value="${obj.depID}"  <c:if test="${autoFinanceQuery.depID==obj.depID}">selected</c:if>>${obj.depName}</option>
                    </c:forEach>
                  </select>
                </div>

                <div class="col-md-4">
                  <label for="userID" class="font-border">用户</label>
                  <select class="form-control" id="userID" name="userID">
                    <option value="0"  <c:if test="${autoFinanceQuery.userID=='0' }||${autoFinanceQuery.userID==null}">selected</c:if>>全部</option>
                    <c:forEach items="${userSessionList}" var="obj">
                      <option value="${obj.userID}" <c:if test="${autoFinanceQuery.userID==obj.userID}">selected</c:if>>${obj.userName}</option>
                    </c:forEach>
                  </select>
                </div>
              </c:if>
              </shiro:hasPermission>
            </div>
          </div>
          <div class="form-group">
            <shiro:hasPermission name="orders">
              <button type="submit" class="btn btn-default">查询</button>
            </shiro:hasPermission>
            <shiro:hasPermission name="rest[order:read]">
              <a href="${ctx}/order"><button type="button" class="btn btn-primary">新建</button></a>
            </shiro:hasPermission>
          </div>
        </form>
      </fieldset>
      <fieldset>
        <%--<legend>操作</legend>--%>
        <table class="table table-bordered">
          <thead>
            <tr>
              <th>申请单号</th>
              <th>渠道</th>
              <th>提交人</th>
              <th>人保单号</th>
              <th>是否垫款</th>
              <th>状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
          <c:forEach items="${pageInfo.list}" var="obj">
            <tr>
              <td>${obj.orderID}</td>
              <td>${obj.user.getDep().getDepName()}</td>
              <td>${obj.user.getUserTrueName()}</td>
              <td>${obj.rbID}</td>
              <td>
                <c:if test="${obj.orderSfdk=='0'}">不垫款</c:if>
                <c:if test="${obj.orderSfdk=='1'}">垫款</c:if>
              </td>
              <!-//渠道端和非渠道端可以不显示同样的信息,后期增加字典表，将状态写到库中->
              <td>
                <c:if test="${obj.orderZt=='00'}">待提交</c:if>
                <c:if test="${obj.orderZt=='01'}">已提交</c:if>
                <c:if test="${obj.orderZt=='10'}">待风控审核</c:if>
                <c:if test="${obj.orderZt=='11'}">待出履约险</c:if>
                <c:if test="${obj.orderZt=='20'}">放款中</c:if>
                <c:if test="${obj.orderZt=='30'}">放款确认</c:if>
                <c:if test="${obj.orderZt=='40'}">已放款</c:if>
              </td>
              <td>
                <shiro:hasPermission name="rest[orders:read]">
                  <a href="${ctx}/orders/${obj.orderID}"><button type="button" class="btn-sm btn-primary">详情</button></a>
                </shiro:hasPermission>
                <shiro:hasPermission name="rest[order:read]">
                  <c:if test="${obj.orderZt=='00'}">
                    <a href="${ctx}/order/${obj.orderID}"><button type="button" class="btn-sm btn-warning">提交</button></a>
                  </c:if>
                </shiro:hasPermission>
                <shiro:hasPermission name="rest[confirmAudit:read]">
                  <c:if test="${obj.orderZt=='01'}">
                    <a href="${ctx}/confirmAudit/${obj.orderID}"><button type="button" class="btn-sm btn-warning">确认收款</button></a>
                  </c:if>
                  <c:if test="${obj.orderZt!='00'&&obj.orderZt!='01'}">
                    <a href="${ctx}/confirmAudit/${obj.orderID}"><button type="button" class="btn-sm btn-primary">收款详情</button></a>
                  </c:if>
                </shiro:hasPermission>

                <shiro:hasPermission name="rest[riskAudit:read]">
                  <c:if test="${obj.orderZt=='10'}">
                    <a href="${ctx}/riskAudit/${obj.orderID}"><button type="button" class="btn-sm btn-warning">风控审核</button></a>
                  </c:if>
                  <c:if test="${obj.orderZt=='11'}">
                    <a href="${ctx}/riskAudit/${obj.orderID}"><button type="button" class="btn-sm btn-warning">出履约险</button></a>
                  </c:if>
                  <c:if test="${obj.orderZt!='00'&&obj.orderZt!='01'&&obj.orderZt!='10'&&obj.orderZt!='11'}">
                    <a href="${ctx}/riskAudit/${obj.orderID}"><button type="button" class="btn-sm btn-primary">风控详情</button></a>
                  </c:if>
                </shiro:hasPermission>

                <shiro:hasPermission name="rest[advanceAudit:read]">
                  <c:if test="${obj.orderZt=='20'}">
                    <a href="${ctx}/advanceAudit/${obj.orderID}"><button type="button" class="btn-sm btn-warning">放款审核</button></a>
                  </c:if>
                  <c:if test="${obj.orderZt!='00'&&obj.orderZt!='01'&&obj.orderZt!='10'&&obj.orderZt!='11'&&obj.orderZt!='20'}">
                    <a href="${ctx}/advanceAudit/${obj.orderID}"><button type="button" class="btn-sm btn-primary">垫资详情</button></a>
                  </c:if>
                </shiro:hasPermission>

                <shiro:hasPermission name="rest[grantLoan:read]">
                  <c:if test="${obj.orderZt=='30'}">
                    <a href="${ctx}/grantLoan/${obj.orderID}"><button type="button" class="btn-sm btn-warning">放款</button></a>
                  </c:if>
                  <c:if test="${obj.orderZt=='40'}">
                    <a href="${ctx}/grantLoan/${obj.orderID}"><button type="button" class="btn-sm btn-primary">放款详情</button></a>
                  </c:if>
                </shiro:hasPermission>
                <a href="${ctx}/processAudit/${obj.orderID}"><button type="button" class="btn-sm btn-primary">订单进展</button></a>
                <shiro:hasPermission name="rest[order:delete]">
                  <c:if test="${obj.orderZt=='00'}">
                    <button type="button" uid="${obj.orderID}"  class="btn-sm btn-danger dataDel">删除</button>
                  </c:if>
                </shiro:hasPermission>
              </td>
            </tr>
          </c:forEach>
          </tbody>
          <shiro:hasPermission name="orders">
            <thfoot>
              <tr>
                <td colspan="7">
                  <div>
                    当前第 ${pageInfo.pageNum}页,总计${pageInfo.pages}页,总计 ${pageInfo.total} 条记录
                    <a href="javascript:void(0);" page="first">首页</a>
                    <c:if test="${pageInfo.hasPreviousPage}">
                      <a href="javascript:void(0);" page="prev">上一页</a>
                    </c:if>
                    <c:if test="${pageInfo.hasNextPage}">
                      <a href="javascript:void(0);" page="next">下一页</a>
                    </c:if>
                    <a href="javascript:void(0);" page="last">尾页</a>
                  </div>
                </td>
              </tr>
            </thfoot>
          </shiro:hasPermission>
        </table>
      </fieldset>
    </div>
  </div>
  <%@include file="/includes/foot.jsp"%>
  <%@include file="/includes/nav.jsp"%>
</div>
<script type="text/javascript">
  $('a[page]').click(
          function()
          {
            var pageNum=1;
            var currPage='${pageInfo.pageNum}';
            var totalPages='${pageInfo.pages}';
            var v=$(this).attr('page');
            switch(v){
              case "first":
                pageNum=1;
                break;
              case "prev":
                pageNum=parseInt(currPage)-1  ;
                if(pageNum<1)
                {
                  pageNum=1;
                }
                break;
              case "next":
                pageNum=parseInt(currPage)+1  ;
                if(pageNum>totalPages)
                {
                  pageNum=totalPages;
                }
                break;
              case "last":
                pageNum=totalPages;
                break;
            }
            $('input[name=pageNum]').val(pageNum);
            $('form').submit();
          });
  $(".dataDel").click(function () {
    var msg = "是否删除数据？";
    if (confirm(msg)==true){
      var uid=$(this).attr('uid');
      $.ajax({
        url:'${ctx}/order/'+uid,
        type:'delete',
        contentType:'application/json',
        success:function(data){
          if(data.code==true)
          {
            alert("删除成功");
            $('form').submit();
          }
          else{
            alert(data.msg);
          }
        }
      })
      return true;
    }else{
      return false;
    }
  })

  $(".grantLoan").click(function () {
    var msg = "是否完成放款？";
    if (confirm(msg)==true){
      var uid=$(this).attr('uid');
      $.ajax({
        url:'${ctx}/grantLoan/'+uid,
        type:'delete',
        contentType:'application/json',
        success:function(data){
          if(data.code==true)
          {
            alert("放款成功");
            $('form').submit();
          }
          else{
            alert(data.msg);
          }
        }
      })
      return true;
    }else{
      return false;
    }
  })

</script>
</body>
</html>


