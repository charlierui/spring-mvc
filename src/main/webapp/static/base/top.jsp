<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<table  width="100%"  border="0" cellspacing="0"cellpadding="0" style="height: 65px;background:#2076C3 url(../static/images/headbg.jpg) repeat-x; font-family: Verdana, 微软雅黑,黑体">
	
	<tr>
	
		<td rowspan="2"  align="right" valign="middle" width="750">
			
		</td>
		<td height="22"  valign="middle">		
				<span style="float:right;margin-right:15px;color:#ffffff;font-weight:bold;font-size: 12px"><a class="easyui-linkbutton" data-options="iconCls:'icon-user-group',plain:true"></a>&nbsp;&nbsp;欢迎您：${user.name }</span>
		</td>
	</tr>
	<tr height="31">
	
		<td align="right" >				
	  		<table  border="0" cellspacing="0" cellpadding="0">
				<tr>	
					<td>
					  	<table width="100%" height="100%" class="off" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><a id="logoutBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-exit',plain:true" style="color:#004532;font-weight:bold">安全退出</a>&nbsp;&nbsp;</td>
							</tr>
						</table>
					</td>
					<td>
						<table width="100%" height="100%" class="off" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><a id="pwdBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-lock-key',plain:true" style="color:#004532;font-weight:bold">密码修改</a>&nbsp;&nbsp;</td>
							</tr>
						</table>
					</td>		
				</tr>		
			</table>
		</td>
	</tr>		
</table>
	<!-- 快捷键菜单 -->
	<div id="closeMenu" class="easyui-menu" style="width:150px;">
		<div id="refresh">刷新</div>
		<div class="menu-sep"></div>
		<div id="close">关闭</div>
		<div id="closeall">全部关闭</div>
		<div id="closeother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="closeright">关闭右侧标签</div>
		<div id="closeleft">关闭左侧标签</div>
		<div class="menu-sep"></div>
		<!--  <div id="exit">退出</div>-->
	</div>
	    
