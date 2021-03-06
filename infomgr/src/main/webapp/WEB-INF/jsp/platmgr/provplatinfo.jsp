<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String banner_path = request.getContextPath();
    String banner_basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + banner_path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>五车网业务管理系统</title>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <jsp:include page="/includes/head.jsp"></jsp:include>
    <div class="layui-body">
        <div style="padding: 15px;">
            <div align="center"><span><h1>资源省份检索</h1></span></div>
            <form>
                <select id ='pt'>
                    <c:forEach items="${ptlist}" var="obj">
                        <option value ="${obj.id}">${obj.name}</option>
                    </c:forEach>
                </select>
                <select id ='pro'>
                    <c:forEach items="${provlist}" var="obj">
                        <option value ="${obj.dmz}">${obj.dmsm1}</option>
                    </c:forEach>
                </select>
                <input class="layui-btn layui-btn-normal" type="button" id="queryvalue" value="查询">
            </form>
            <script type="text/javascript">
                var select='';
                var selectvalue='';
                var selectpt='';
                var selectptvalue='';
                $(function() {
                    $('#queryvalue').click(
                            function()
                            {
                                select=$("#pro option:selected").val();
                                selectvalue=($("#pro option:selected").text());
                                selectpt=$("#pt option:selected").val();
                                selectptvalue=($("#pt option:selected").text());

                                $("#proname").html(selectvalue);
                                $("#ptname").html(selectptvalue);
                                $.ajax({
                                    url:'${ctx}/frm/citylist/'+select,
                                    contentType: 'application/json;charset=utf-8',
                                    type:'post',
                                    success:function(data)
                                    {
                                        var str = "";
                                        $.ajax({
                                            url:'${ctx}/regmgr/queryByPrvid/'+select,
                                            contentType: 'application/json;charset=utf-8',
                                            type:'post',
                                            success:function(rescom)
                                            {
                                                if(rescom.length>0)
                                                {
                                                    var i=0;
                                                    var j=0;
                                                    for(j=0;j<data.length;j++)
                                                    {
                                                        for(i=0;i<rescom.length;i++) {
                                                            if (data[j].dmz  == rescom[i].cityid ) {
                                                                str = str + "<input name='regcity' disabled='disabled'  type='checkbox' checked='checked'  value=" + data[j].dmz + ">" + data[j].dmsm1;
                                                                str = str + "<br>";
                                                                break;
                                                            }
                                                            else {
                                                                if (i == (rescom.length - 1)) {
                                                                    str = str + "<input name='regcity' disabled='disabled' type='checkbox' value=" + data[j].dmz + ">" + data[j].dmsm1;
                                                                    str = str + "<br>";
                                                                }
                                                                continue;
                                                            }
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    var k=0;
                                                    for(k=0;k<data.length;k++)
                                                    {
                                                        str =str + "<input name='regcity' disabled='disabled' type='checkbox'  value="+data[k].dmz+">"+data[k].dmsm1;
                                                        str=str+"<br>";
                                                    }
                                                }
                                                tbody.innerHTML = str;
                                            }
                                        });

                                        var strub = "";
                                        var strwc = "";
                                        $.ajax({
                                            url:'${ctx}/ubaccess/queryByPrvid/'+selectpt+'/'+select,
                                            contentType: 'application/json;charset=utf-8',
                                            type:'post',
                                            success:function(ubdata)
                                            {
                                                $.ajax({
                                                    url:'${ctx}/fcaraccess/queryByPrvid/'+selectpt+'/'+select,
                                                    contentType: 'application/json;charset=utf-8',
                                                    type:'post',
                                                    success:function(fcardata) {
                                                        if(ubdata.length>0)
                                                        {
                                                            var i=0;
                                                            var j=0;
                                                            var m=0;
                                                            for(j=0;j<data.length;j++)
                                                            {
                                                                for(i=0;i<ubdata.length;i++) {

                                                                    if (data[j].dmz  == ubdata[i].cityid ) {
                                                                        //此外为平台准入城市
                                                                        strub = strub + "<input name='ptjrcity' disabled='disabled' type='checkbox' checked='checked' value=" + data[j].dmz + ">" + data[j].dmsm1;
                                                                        strub = strub + "<br>";
                                                                        //为五车准入城市
                                                                        if(fcardata.length>0)
                                                                        {
                                                                            for(m=0;m<fcardata.length;m++) {
                                                                                if (ubdata[i].cityid==fcardata[m].cityid) {
                                                                                    strwc = strwc + "<input name='wjjrcity' disabled='disabled' type='checkbox' checked='checked' value=" + data[j].dmz + ">" + data[j].dmsm1;
                                                                                    strwc = strwc + "<br>";
                                                                                    break;
                                                                                }
                                                                                else
                                                                                {
                                                                                    if (m == (fcardata.length - 1)) {
                                                                                        strwc = strwc + "<input name='wjjrcity' disabled='disabled' type='checkbox' value=" + data[j].dmz + ">" + data[j].dmsm1;
                                                                                        strwc = strwc + "<br>";
                                                                                    }
                                                                                    continue;
                                                                                }
                                                                            }
                                                                        }
                                                                        else
                                                                        {
                                                                            strwc=strwc+ "<input name='wjjrcity' disabled='disabled' type='checkbox' value=" + data[j].dmz + ">" + data[j].dmsm1;
                                                                            strwc = strwc + "<br>";
                                                                        }
                                                                        break;
                                                                    }
                                                                    else {
                                                                        if (i == (ubdata.length - 1)) {
                                                                            strub = strub + "<input name='ptjrcity' disabled='disabled' type='checkbox' value=" + data[j].dmz + ">" + data[j].dmsm1;
                                                                            strub = strub + "<br>";
                                                                        }
                                                                        continue;
                                                                    }
                                                                }
                                                            }

                                                        }
                                                        else
                                                        {
                                                            var k=0;
                                                            for(k=0;k<data.length;k++)
                                                            {
                                                                strub =strub + "<input name='ptjrcity' disabled='disabled' type='checkbox' value="+data[k].dmz+">"+data[k].dmsm1;
                                                                strub=strub+"<br>";
                                                            }
                                                        }
                                                        cszr.innerHTML = strub;
                                                        wczr.innerHTML = strwc;}
                                                })
                                            }
                                        });
                                    }
                                });
                            }
                    );
                })
            </script>
            <table class="layui-table">
                <thead>
                <tr>
                    <th>平台</th>
                    <th>省份</th>
                    <th>注册城市</th>
                    <th>城市接入(备案)</th>
                    <th>五车接入</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><label id="ptname"></label>
                    <td><label id="proname"></label>
                    </td>
                    <td id="tbody">
                    </td>
                    </td>
                    <td id="cszr">
                    </td>
                    </td>
                    <td id="wczr">
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <jsp:include page="/includes/foot.jsp"></jsp:include>
</div>
</body>
</html>