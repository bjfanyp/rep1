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
            <p class="main-content-div-index"><c:choose><c:when test="${order.orderZt=='20'}">放款审核</c:when><c:otherwise>放款详情</c:otherwise></c:choose><span class="page-error">${errorMsg}</span></p>
            <form:form  method="post" action="${ctx}/advanceAudit" modelAttribute="orderOperate">
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4">
                            <input name="id" id="id" class="form-control" value="${orderOperate!=null?orderOperate.id:''}" type="hidden" readonly="readonly">
                            <input name="order.orderID" id="orderID" class="form-control" value="${order.orderID!=null?order.orderID:''}" type="hidden" readonly="readonly">
                            <label for="rbID">人保订单</label>
                            <input name="rbID" id="rbID" class="form-control" value="${order.rbID!=null?order.rbID:''}" type="text" readonly="readonly">
                        </div>
                        <div class="col-md-4">
                            <label for="orderCustom">用户姓名</label>
                            <input name="orderCustom" id="orderCustom" class="form-control"  value="${order.orderCustom!=null?order.orderCustom:''}" type="text" readonly="readonly">
                        </div>
                        <div class="col-md-4">
                            <label for="orderCustomID">用户身份证号码</label>
                            <input name="orderCustomID" id="orderCustomID" class="form-control" value="${order.orderCustomID!=null?order.orderCustomID:''}" type="text" readonly="readonly">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4">
                            <label for="orderPrepay">首付款</label>
                            <input name="orderPrepay" id="orderPrepay" class="form-control" value="${order.orderPrepay!=null?order.orderPrepay:'0'}" type="number" readonly="readonly">
                        </div>
                        <div class="col-md-4">
                            <label for="orderLoanvalue">贷款额</label>
                            <input name="orderLoanvalue" id="orderLoanvalue" class="form-control" value="${order.orderLoanvalue!=null?order.orderLoanvalue:'0'}" type="number" readonly="readonly">
                        </div>
                        <div class="col-md-4">
                            <label for="orderSfdk">是否垫款</label>
                            <select class="form-control"  name="orderSfdk" id="orderSfdk" disabled="disabled">
                                <option value="0" <c:if test="${order.orderSfdk!=null and order.orderSfdk=='0'}">selected</c:if>>不垫款</option>
                                <option value="1" <c:if test="${order.orderSfdk!=null and order.orderSfdk=='1'}">selected</c:if>>垫款</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4">
                            <label for="orderPins">履约险</label>
                            <input name="orderPins" id="orderPins" class="form-control" value="${order.orderPins!=null?order.orderPins:'0'}" type="text" readonly="readonly">
                        </div>
                        <div class="col-md-4">
                            <label for="orderYwins">人身意外险</label>
                            <input name="orderYwins" id="orderYwins" class="form-control" value="${order.orderYwins!=null?order.orderYwins:'0'}" type="text" readonly="readonly">
                        </div>
                        <div class="col-md-4">
                            <label for="orderSerpay">服务费</label>
                            <input name="orderSerpay" id="orderSerpay" class="form-control"  value="${order.orderSerpay!=null?order.orderSerpay:'0'}"  type="text" readonly="readonly">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-4">
                            <label for="orderRisk">风险保证金</label>
                            <input name="orderRisk" id="orderRisk" class="form-control" value="${order.orderRisk!=null?order.orderRisk:'0'}"  type="text" readonly="readonly">
                        </div>
                        <div class="col-md-4">
                            <label for="orderConsloan">垫款服务费</label>
                            <input name="orderConsloan" id="orderConsloan" class="form-control"  value="${order.orderConsloan!=null?order.orderConsloan:'0'}" type="text" readonly="readonly">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <%--<div class="col-md-4">--%>
                            <%--<label for="collect">收款总额</label>--%>
                            <%--<input name="collect" id="collect" class="form-control"  value="${${order.orderFktotal}}" type="text" readonly="readonly">--%>
                        <%--</div>--%>
                        <div class="col-md-4">
                            <label for="loan">打款总额</label><span class="static-error">*</span>
                            <input name="loan" id="loan" class="form-control" value="${order.orderLoanvalue}" type="text" readonly="readonly">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-8">
                            <label for="accountBank">打款开户行</label>
                            <input name="accountBank" id="accountBank" class="form-control" value="${advanceAuditSessionaccount!=null?advanceAuditSessionaccount.accountBank:''}" type="text" readonly="readonly">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-8">
                            <label for="accountBook">打款户名</label>
                            <input name="accountBook" id="accountBook" class="form-control"  value="${advanceAuditSessionaccount!=null?advanceAuditSessionaccount.accountName:''}" type="text" readonly="readonly">
                        </div>
                        <div class="col-md-4">
                            <label for="accountNumber">打款账号</label>
                            <input name="accountNumber" id="accountNumber" class="form-control" value="${advanceAuditSessionaccount!=null?advanceAuditSessionaccount.accountNumber:''}" type="text" readonly="readonly">
                        </div>
                    </div>
                </div>
                <c:forEach items='${orderPicList}' var="obj">
                    <c:if test="${obj.orderZt=='00'&&obj.pic.picType=='002'}">
                        <div class="form-group">
                            <label>${obj.pic.picName}</label>
                            <img onclick="showImg(this.name)"  name="${obj.pic.picUrl}"  width="40px" height="40px" alt="未传" src="${ctx}/${obj.pic.picThumbUrl}">
                        </div>
                    </c:if>
                </c:forEach>
                <c:forEach items='${orderPicList}' var="obj">
                    <c:if test="${obj.orderZt=='10'||obj.orderZt=='11'}">
                        <div class="form-group">
                            <label>${obj.pic.picName}</label>
                            <img onclick="showImg(this.name)"  name="${obj.pic.picUrl}"  width="40px" height="40px" alt="未传" src="${ctx}/${obj.pic.picThumbUrl}">
                        </div>
                    </c:if>
                </c:forEach>
                <c:forEach items='${orderPicList}' var="obj">
                    <c:if test="${obj.orderZt=='20'}">
                        <div class="form-group">
                            <label>${obj.pic.picName}</label>
                            <c:if test="${order.orderZt=='20'}">
                            <input type="file" name="${obj.pic.picID}" id="${obj.pic.picID}" accept="image/png,image/jpeg" <c:if test="${order.orderZt!='20'}">disabled="disabled"</c:if>>
                            <input type="button"  value="上传"   onclick="fileUpload('${obj.pic.picID}')">
                            </c:if>
                            <img onclick="showImg(this.name)" id="${obj.pic.picID}img" name="${obj.pic.picUrl}" width="40px" height="40px" alt="未传" src="${ctx}/${obj.pic.picThumbUrl}">
                        </div>
                    </c:if>
                </c:forEach>
                <c:if test="${order.orderZt=='20'}">
                    <shiro:hasPermission name="rest[advanceAudit:post]">
                        <button type="submit" class="btn btn-success">确认</button>
                        <button type="button" uid="${order.orderID}" class="btn btn-danger dataDel">驳回</button>
                    </shiro:hasPermission>
                </c:if>
                <a href="${ctx}/orders"><button type="button" class="btn btn-secondary">返回</button></a>
            </form:form>
        </div>
    </div>
    <%@include file="/includes/foot.jsp"%>
    <%@include file="/includes/nav.jsp"%>
</div>
<script type="text/javascript">
    function fileUpload(id){
        var zpTmp=$('#'+id).val();
        if(zpTmp==null||zpTmp=='')
        {
            alert("未选照片");
            return ;
        }
        $.ajaxFileUpload({
            url:'${ctx}/uploadPic/'+id,
            secureuri:false,         //是否启用安全提交,默认为false
            type:'post',
            // dataType:'json',         //服务器返回的格式,可以是json或xml等
            dataType:'text',//解决IE上传文件，弹出下载框的问题
            fileElementId:id,        //文件选择框的id属性
            success:function(data) { //服务器响应成功时的处理函数
                data=JSON.parse(data);
                if(data.code==true)
                {
                    $('#'+id+'img').attr("src", '${ctx}/'+data.data.picThumbUrl+"?time=" + new Date().getTime());
                    $('#'+id+'img').attr("name",data.data.picUrl);
                }
                else {
                    alert(data.msg);
                }
            }
        })
    }
    function showImg(picUrl)
    {
        if(picUrl==null||picUrl=='')
        {
            alert("还未上传图片");
            return;
        }
        top.location.href="${ctx}/picDetail.jsp?picUrl="+picUrl;
    }
    $(".dataDel").click(function () {
        var msg = "是否驳回？";
        if (confirm(msg)==true){
            var uid=$(this).attr('uid');
            $.ajax({
                url:'${ctx}/advanceAudit/'+uid,
                type:'put',
                contentType:'application/json',
                success:function(data){
                    if(data.code==true)
                    {
                        alert("驳回成功");
                        top.location.href="${ctx}/orders";
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
