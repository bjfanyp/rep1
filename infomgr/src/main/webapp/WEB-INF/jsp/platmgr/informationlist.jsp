<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>

</head>
<body>
<form>
    <select id ='pt'>
        <c:forEach items="${ptlist}" var="obj">
            <option value ="${obj.id}">${obj.name}</option>
        </c:forEach>
    </select>
    <select id ='pro' onchange="getvalue()">
        <c:forEach items="${provlist}" var="obj">
            <option value ="${obj.dmz}">${obj.dmsm1}</option>
        </c:forEach>
    </select>
    <input type="submit" value="查询">
</form>
<script type="text/javascript">
    function getvalue()
    {
        var select=$("#pro option:selected").val();
        var selectvalue=($("#pro option:selected").text());
        var selectpt=$("#pt option:selected").val();
        var selectptvalue=($("#pt option:selected").text());
        $("#proname").html(selectvalue);
        $("#ptname").html(selectptvalue);
        $.ajax({
            url:'/information/citylist/'+select,
            contentType: 'application/json;charset=utf-8',
            type:'post',
            success:function(data)
            {
                var str = "";
                $.ajax({
                    url:'/registercompany/queryByPrvid/'+select,
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
                                        str = str + "<input name='regcity'  type='checkbox' checked='checked'  value=" + data[j].dmz + "/>" + data[j].dmsm1;
                                        str = str + "<br>";
                                        break;
                                    }
                                    else {
                                        if (i == (rescom.length - 1)) {
                                            str = str + "<input name='regcity' type='checkbox' value=" + data[j].dmz + "/>" + data[j].dmsm1;
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
                                str =str + "<input name='regcity' type='checkbox'  value="+data[k].dmz+"/>"+data[k].dmsm1;
                                str=str+"<br>";
                            }
                        }
                        tbody.innerHTML = str;
                    }
                });

                var strub = "";
                var strwc = "";
                $.ajax({
                    url:'/ubaccess/queryByPrvid/'+selectpt+'/'+select,
                    contentType: 'application/json;charset=utf-8',
                    type:'post',
                    success:function(ubdata)
                    {
                        if(ubdata.length>0)
                        {
                            var i=0;
                            var j=0;
                            for(j=0;j<data.length;j++)
                            {
                                for(i=0;i<ubdata.length;i++) {
                                    if (data[j].dmz  == ubdata[i].cityid ) {
                                        //此外为平台准入城市
                                        strub = strub + "<input name='ptjrcity'  type='checkbox' checked='checked'  value=" + data[j].dmz + "/>" + data[j].dmsm1;
                                        strub = strub + "<br>";
                                        //此外为五车准入城市
                                        strwc=strwc+ "<input name='wjjrcity'  type='checkbox' value=" + data[j].dmz + "/>" + data[j].dmsm1;
                                        strwc = strwc + "<br>";
                                        break;
                                    }
                                    else {
                                        if (i == (ubdata.length - 1)) {
                                            strub = strub + "<input name='ptjrcity' type='checkbox' value=" + data[j].dmz + "/>" + data[j].dmsm1;
                                            strub = strub + "<br>";
                                        }
                                        continue;
                                    }
                                }
                            }
                            wczr.innerHTML = strwc;
                        }
                        else
                        {
                            var k=0;
                            for(k=0;k<data.length;k++)
                            {
                                strub =strub + "<input name='ptjrcity' type='checkbox' value="+data[k].dmz+"/>"+data[k].dmsm1;
                                strub=strub+"<br>";
                            }
                        }
                        cszr.innerHTML = strub;
                    }
                });

            }
        });
    };

    function regis() {
        obj = document.getElementsByName("city");
        alert($(obj.this).val());
    };

    function regisfxk()
    {
        obj = document.getElementsByName("city");
        check_val = [];
        for(k in obj){
            if(obj[k].checked)
                check_val.push(obj[k].value);
        }
        alert(check_val);
    }
</script>
<table width="800" border="1">
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
</body>
</html>
