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
      <div align="center"><span><h1>注册信息管理</h1></span></div>
      <div align="left">
        <label>请选择省份</label>
        <select id ='pro'>
          <c:forEach items="${provlist}" var="obj">
            <option value ="${obj.dmz}">${obj.dmsm1}</option>
          </c:forEach>
        </select>
        <button class="layui-btn layui-btn-normal" id="queryvalue">查询</button>
      </div>
      <div>
        <script type="text/javascript">
          var select='';
          var selectvalue='';
          $(function() {
            $('#queryvalue').click(
                    function()
                    {
                      select=$("#pro option:selected").val();
                      selectvalue=($("#pro option:selected").text());
                      $("#proname").html(selectvalue);
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
                                      str = str + "<input name='regcity' type='checkbox' lay-skin='primary' checked='checked'  value=" + data[j].dmz + ">" + data[j].dmsm1;
                                      str = str + "<br>";
                                      break;
                                    }
                                    else {
                                      if (i == (rescom.length - 1)) {
                                        str = str + "<input name='regcity'  type='checkbox' lay-skin='primary' value=" + data[j].dmz + ">" + data[j].dmsm1;
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
                                  str =str + "<input name='regcity' type='checkbox' lay-skin='primary' value="+data[k].dmz+">"+data[k].dmsm1;
                                  str=str+"<br>";
                                }
                              }
                              tbody.innerHTML = str;
                            }
                          });
                        }
                      });
                    }
            );
            $('#tjbtn').click(
                    function()
                    {
                      obj = document.getElementsByName("regcity");
                      check_val = [];
                      for(k in obj){
                        if(obj[k].checked)
                          check_val.push({'provid':select,'cityid':obj[k].value});
                      }
                      $.ajax({
                        url:'${ctx}/regmgr/manage',
                        contentType:'application/json;charset=utf-8',
                        type:'post',
                        data:JSON.stringify(check_val),
                        success:function(data)
                        {
                          if(data==true)
                          {
                            alert("更新成功！");
                          }
                        }
                      })
                    }
            );
          })
        </script>
        <table class="layui-table">
          <thead>
          <tr>
            <th>省份</th>
            <th>注册城市</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td><label id="proname"></label>
            </td>
            <td id="tbody">
            </td>
          </tr>
          </tbody>
          <tfoot>
          <tr>
            <td colspan="2"><button class="layui-btn layui-btn-normal" id="tjbtn" >更新</button></td>
          </tr>
          </tfoot>
        </table>
      </div>
    </div>
  </div>
  <jsp:include page="/includes/foot.jsp"></jsp:include>
</div>
<script type="text/javascript">
  var select='';
  var selectvalue='';
  $(function() {
    $('#queryvalue').click(
            function()
            {
              select=$("#pro option:selected").val();
              selectvalue=($("#pro option:selected").text());
              $("#proname").html(selectvalue);
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
                              str = str + "<input name='regcity' type='checkbox' lay-skin='primary' checked='checked'  value=" + data[j].dmz + ">" + data[j].dmsm1;
                              str = str + "<br>";
                              break;
                            }
                            else {
                              if (i == (rescom.length - 1)) {
                                str = str + "<input name='regcity'  type='checkbox' lay-skin='primary' value=" + data[j].dmz + ">" + data[j].dmsm1;
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
                          str =str + "<input name='regcity' type='checkbox' lay-skin='primary' value="+data[k].dmz+">"+data[k].dmsm1;
                          str=str+"<br>";
                        }
                      }
                      tbody.innerHTML = str;
                    }
                  });
                }
              });
            }
    );
    $('#tjbtn').click(
            function()
            {
              obj = document.getElementsByName("regcity");
              check_val = [];
              for(k in obj){
                if(obj[k].checked)
                  check_val.push({'provid':select,'cityid':obj[k].value});
              }
              $.ajax({
                url:'${ctx}/regmgr/manage',
                contentType:'application/json;charset=utf-8',
                type:'post',
                data:JSON.stringify(check_val),
                success:function(data)
                {
                  if(data==true)
                  {
                    alert("更新成功！");
                  }
                }
              })
            }
    );
  })
</script>
    </div>
  </div>
</div>
</body>
</html>

