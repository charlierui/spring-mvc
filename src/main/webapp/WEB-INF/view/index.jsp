<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/static/base/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<%@ include file="/static/base/head.jsp"%>
<title>登录</title>
<link href="${ctx }/static/images/tax.ico" rel="shortcut icon" />
<script type="text/javascript">
function hc(){
        var event = window.event || arguments.callee.caller.arguments[0];
        if (event.keyCode == 13)
        {
            sbm();
        }
}
</script>
</head>
<%
String name = "";
String pass = "";    
Cookie[] cookies = request.getCookies();
if(cookies != null){
	for (int i = 0; i < cookies.length; i++) {
    	if(cookies[i].getName().equals("liangyiuserName")){
    		name = cookies[i].getValue();
    		
    	}
    	if(cookies[i].getName().equals("liangyipassword")){
    		pass = cookies[i].getValue();
    	}
	}
} 
%>
<body style="background-color: #ffffff">
<table border="0" width="100%" height="100%" align="center" cellpadding="0" cellspacing="0" >
<tr>
<td height="150px"></td></tr>
<tr>
<td>

<form name="loginForm" id="loginForm" action="${ctx }/logincon/login" method="post"  target="_top">
<center>
<table border="0" width="1109px" height="335px" align="center" cellpadding="0" cellspacing="0" style="color:#346378; background: url(${ctx}/static/images/shenai_login.jpg) no-repeat center top;" > 
	<tr height="105px"><td colspan="3">&nbsp;</td></tr>
	<tr height="100px">
			<td width="315px">&nbsp;</td>
			<td width="230px">
			<table border="0" width="220px" height="100%"  cellpadding="0" cellspacing="0">
				<tr> 
				<td width="30%" align="right" height="28" style="font-size:12px">用户名：</td>
				<td width="70%">
					<input class="easyui-validatebox" data-options="required:true" missingMessage="请输入用户名" type="text" size="19" name="username" id="username" value="${username}" style="border:1px solid #A3BAD9;width:140px;" onkeydown="hc()"/>
				</td>
				</tr>
				<tr>
				<td  align="right" height="28" style="font-size:12px">密　码：</td>
				<td >
				<input class="easyui-validatebox" data-options="required:true" missingMessage="请输入密码" type="password" name="psw" id="psw" value="${passw }" style="border:1px solid #A3BAD9;width:140px;" onkeydown="hc()"/>
				</td>
				</tr>
				<tr>
				<td height="28">&nbsp;</td>
				<td align="left">
					&nbsp;<input class="button" type="button"  onclick="sbm()" value="&nbsp;登&nbsp;录&nbsp;" />
					&nbsp;<input type="checkbox" name="saveCookie" id="saveCookie" value="yes"/>&nbsp;<span style="font-size:12px">记住我<span>
				</td>
				</tr>
			</table>
			</td>
			<td width="90px">&nbsp;</td>
			</tr>
	<tr height="60px"><td colspan="3">&nbsp;</td></tr>
</table>
</center>
</form>

</td></tr>
</table>
</body>
</html>
<script type="text/javascript">

<!--
$(function(){
	if (window != top){   
		top.location.href = location.href;
	}
	$('#username').focus();	
	
})

function sbm(){	
	$('#loginForm').form('submit',{
		success: function(data){
			var a = eval('(' + data + ')');			
				if(a.statuscode=="-1"){	
		        	$.messager.alert('错误提示',a.msg,'error');
		        }else if(a.statuscode="1"){
		        	location.href="${ctx}/indexcon/main";
		        } else{
		        	$.messager.alert('错误提示','系统异常','error');
		        	location.href="${ctx}/indexcon/index";
		        }
		}
	});	
}
//-->
</script>

