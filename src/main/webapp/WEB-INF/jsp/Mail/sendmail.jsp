<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 使用forEach标签时需要在JSP页面中引入JSTL标签库 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=basePath%>css/mail/table.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>bootstrap/3.3.7/css/bootstrap.min.css">
<script type="text/javascript"
	src="<%=basePath%>jQuery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet"
	href="<%=basePath%>kindeditor/themes/default/default.css" />
<script charset="utf-8" src="<%=basePath%>kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="<%=basePath%>kindeditor/kindeditor-min.js"></script>
<script charset="utf-8"
	src="<%=basePath%>kindeditor/kindeditor-model.js"></script>
<script charset="utf-8" src="<%=basePath%>kindeditor/lang/zh_CN.js"></script>
<link rel="stylesheet"
	href="<%=basePath%>kindeditor/plugins/code/prettify.css" />
<script charset="utf-8"
	src="<%=basePath%>kindeditor/plugins/code/prettify.js"></script>
<style>
.leftarea {
	float: left;
	width: 50%;
	height: 50%;
}

.rightarea {
	float: right;
	width: 50%;
	height: 50%;
}
</style>
<script type="text/javascript">
	function send_data() {
		//直接获取富文本中的内容
		var str = editor.html();
		if (str.indexOf('请在此处自行填写本次上线需求...') != -1) {
			alert('请填写本次上线的需求列表信息！');
			return false;
		} else {
			$("input#txt_info").val(str);
			return true;
		}
	}
</script>
</head>
<title>发送邮件</title>
<body style="background-color: #eff3f6">
	<div class="mailinfo">
		<form method="post" action="sendmailAction">
			<table class="table table-condensed">
				<tbody>
				<thead>
					<tr>
						<th>邮件信息</th>
						<th>
							<button id="send_mail" type="submit" style="width: 80px; height: 35px;" align="center" class="btn btn-primary"
								onclick="if(!send_data()){return false;}">发送邮件</button>
						</th>
					</tr>
				</thead>
				<tr class="active">
					<th>收件人：</th>
					<td><input style="height: 30px; width: 80%" placeholder="收件人"
						id="to_mail" name="to_mail" class="text" value="${to_mail}"></td>
				</tr>
				<tr class="active">
					<th scope="row">抄送人：</th>
					<td><input style="height: 30px; width: 80%" placeholder="抄送人"
						id="cc_mail" name="cc_mail" class="text" value="${cc_mail}"></td>
				</tr>
				<tr class="active">
					<th scope="row">邮件标题：</th>
					<td><input style="height: 30px; width: 80%" placeholder="邮件标题"
						id="subject" name="subject" value="${subject}" class="text"></td>
				</tr>
				<tr class="active">
					<th scope="row">邮件内容：</th>
					<td><textarea id="richtext" name="content"
							style="width: 84%; height: 670px; resize: none; overflow: hidden;">
						${richtext}
						</textarea> <input type="hidden" id="txt_info" value="${content}" /></td>
				</tr>
				<tr class="active">
					<th scope="row">温馨提示：</th>
					<td><span
						style="color: #ff0000; font-weight: bold; height: 30px; width: 80%;">
							当前版本最新部署包信息汇总，请测试人员按此部署。<br>
							测试通过后准备上准生产前，测试人员需要对比这里的汇总信息与svn上最新部署信息进行对比。<br>
							如存在差异需要找对应的开发负责人沟通确认最后的应用包及版本，并补充录入及修改最终确认的应用包版本，测试人员不能直接在生成的邮件文本中修改应用包及版本。<br>
							以上工作均完成后，测试人员补充写入需求列表直接点击发送邮件即可完成上线。<br>
							如果上准生产后有更新，测试人员无须再次汇总，单独给运维回复列出修改的应用包即可。<br></td>
				</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>

</html>
