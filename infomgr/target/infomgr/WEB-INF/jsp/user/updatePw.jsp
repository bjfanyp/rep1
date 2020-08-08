<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="/includes/head.jsp"%>
</head>
	<body>
	<h3 class="updatePwd-title">修改密码</h3>
	<form class="form-horizontal">
		<div class="form-group">
			<div class="col-md-6 content-center">
				<label for="oldPw" class="font-border">旧密码</label>
				<input type="password" class="form-control" id="oldPw" name="oldPw" required>
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-6 content-center">
				<label for="newPw" class="font-border">新密码</label>
				<input type="password" class="form-control" id="newPw" name="newPw" required>
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-6 content-center">
				<label for="dfPw" class="font-border">确认密码</label>
				<input type="password" class="form-control" id="dfPw" name="dfPw" required>
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-6 content-center">
				<button type="button" id="uptPwd" class="btn btn-success">修改</button>
				<button type="button" class="btn btn-default"  onclick="javascript:window.location.href='${ctx}/main';" >返回</button>
			</div>
		</div>
	</form>

	<script type="text/javascript">
		$(function() {
			$('#uptPwd').click(
				function(){
					var oldPw =  $('#oldPw').val();
					var newPw =  $('#newPw').val();
					var dfPw = $('#dfPw').val();
					if(oldPw==null||oldPw=="")
					{
						alert("旧密码不能为空，请重新输入");
						return;
					}
					if(newPw==null||newPw=="")
					{
						alert("新密码不能为空，请重新输入");
						return;
					}
					if(dfPw==null||dfPw=="")
					{
						alert("确认密码不能为空，请重新输入");
						return;
					}
					if(newPw!=dfPw)
					{
						alert("确认密码不一致，请重新输入");
						return;
					}
					var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$/;
					if(!reg.test(newPw)){
						alert("新密码长度要大于6位，由数字和字母组成，不含特殊字符");
						return;
					}
					$.ajax({
						url:'${ctx}/updatePw',
						type:'post',
						contentType:'application/json',
						data:JSON.stringify({oldPw:oldPw,newPw:newPw,dfPw:dfPw}),
						success:function(data)
						{
							if(data.code==true)
							{
								alert("修改完成，请重新登录");
								window.location.href="${ctx}/logout";
							}
							else
							{
								alert(data.msg);
							}
						}
					})
				}
			);
		})
	</script>
	</body>
</html>


