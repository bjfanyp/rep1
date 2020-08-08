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
        <%--<legend>检索条件</legend>--%>
        <form class="form-horizontal" action="${ctx}/vehicleOrderList" method="post">
          <%--<shiro:hasPermission name="users">--%>
            <div class="form-group">
              <div class="row">
              <input type="hidden" id="pageNum" name="pageNum">
              <div class="col-md-4">
                <label for="pinPaiID">品牌/车型</label>
                <div class="input-group">
                  <select name="pinPaiID" id="pinPaiID" class="form-control">
                  <option value="">全部</option>
                  <c:forEach items="${pinPai}" var="pinPai">
                    <option value="${pinPai.info.pinPaiID}" <c:if test="${VehicleOrderQuery.pinPaiID!=null&&VehicleOrderQuery.pinPaiID==pinPai.info.pinPaiID}">selected</c:if>>${pinPai.info.pinPai}</option>
                  </c:forEach>
                  </select>
                  <select name="cheXingID" id="cheXingID" class="form-control">
                    <option value="">全部</option>
                    <c:forEach items="${cheXing}" var="cheXing">
                      <option value="${cheXing.info.cheXingID}" <c:if test="${VehicleOrderQuery.cheXingID!=null&&VehicleOrderQuery.cheXingID==cheXing.info.cheXingID}">selected</c:if>>${cheXing.info.cheXing}</option>
                    </c:forEach>
                  </select>
                </div>
              </div>
              </div>
            </div>
          <%--</shiro:hasPermission>--%>
          <div class="form-group">
            <%--<shiro:hasPermission name="users">--%>
              <button type="submit" class="btn btn-default">查询</button>
            <%--</shiro:hasPermission>--%>
          </div>
        </form>
      </fieldset>
      <fieldset>
        <%--<legend>操作</legend>--%>
        <table class="table table-bordered">
          <thead>
          <tr>
            <th>品牌</th>
            <th>车型</th>
            <th>车款</th>
            <th>颜色</th>
            <th>购买数量</th>
            <th>定金总额</th>
            <th>总额</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${pageInfo.list}" var="obj">
            <tr>
              <td>${obj.tuanGou.product.info.pinPai}</td>
              <td>${obj.tuanGou.product.info.cheXing}</td>
              <td>${obj.tuanGou.product.info.cheKuan}</td>
              <td>${obj.tuanGou.product.info.color}</td>
              <td>${obj.count}</td>
              <td>${obj.totalAdvance}</td>
              <td>${obj.totalPrice}</td>
              <td>${obj.ztDes}</td>
              <td>
                <c:if test="${obj.zt=='1'}"><a href="${ctx}/vehicleOrderAdvance/${obj.id}"><button type="button" class="btn-sm bth-success">定金结算</button></a></c:if>
                <c:if test="${obj.zt=='2'}"><a href="${ctx}/aliPay/json/refund//${obj.id}"><button type="button" class="btn-sm bth-success">定金退款</button></a></c:if>
                <c:if test="${obj.zt=='4'}"><a href="${ctx}/vehicleOrderPrice/${obj.id}"><button type="button" class="btn-sm bth-success">全款结算</button></a></c:if>
                <c:if test="${obj.zt=='5'}"><a href="${ctx}/vehicleOrderAllBack/${obj.id}"><button type="button" class="btn-sm bth-success">全款退款</button></a></c:if>
                <c:if test="${obj.zt=='5'}"><a href="${ctx}/vehicleOrderConfirm/${obj.id}"><button type="button" class="btn-sm bth-success">全款确认</button></a></c:if>
                <c:if test="${obj.zt=='7'}"><a href="${ctx}/vehicleOrderEnd/${obj.id}"><button type="button" class="btn-sm bth-success">订单完结</button></a></c:if>
                <c:if test="${obj.zt=='1'}"><a href="${ctx}/vehicleOrderUpdate/${obj.id}"><button type="button" class="btn-sm btn-primary">修改</button></a></c:if>
                <c:if test="${obj.zt=='1'}"><button type="button"  uid="${obj.id}"  class="btn-sm btn-danger dataDel">删除</button></c:if>
              </td>
            </tr>
          </c:forEach>
          </tbody>
          <thfoot>
            <tr>
              <%--<shiro:hasPermission name="users">--%>
                <td colspan="12">
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
              <%--</shiro:hasPermission>--%>
            </tr>
          </thfoot>
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
  $(".dataDel").click(
    function()
    {
      var msg = "是否删除数据？";
      if (confirm(msg)==true){
        var uid=$(this).attr('uid');
        $.ajax({
          url:'${ctx}/vehicleOrder/'+uid,
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
    }
  );
  $("#pinPaiID").change(
          function(){
            var pinPaiID=$("#pinPaiID").val();
            if(pinPaiID!=''){
              $.ajax({
                url:'${ctx}/vehicleProduct/getCheXing',
                type:'post',
                contentType:'application/json;charset=utf-8',
                data:JSON.stringify({pinPaiID:pinPaiID}),
                success:function(data) {
                  if(data.code==true) {
                    $("#cheXingID").empty();
                    if(data.data.length>0){
                      var cheXingObj = document.getElementById("cheXingID");
                      cheXingObj.options.add(new Option("全部",""));
                      for(var i=0;i<data.data.length;i++){
                        cheXingObj.options.add(new Option(data.data[i].info.cheXing, data.data[i].info.cheXingID));
                      }
                    }
                  }
                }
              })
            }else{
              $("#cheXingID").empty();
              var cheXingObj = document.getElementById("cheXingID");
              cheXingObj.options.add(new Option("全部",""));
            }
          }
  )
</script>
</body>
</html>
