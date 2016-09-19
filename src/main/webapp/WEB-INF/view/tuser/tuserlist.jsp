<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/base/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${systemOption.systemTitle}</title>
<%@ include file="/static/base/head.jsp"%>
<script type="text/javascript">
	$(function() {
		//添加界面的附件管理
		$('#file_upload').uploadify(
						{
							'swf' : '${ctx}/static/uploadify/uploadify.swf', //FLash文件路径
							'buttonText' : '选择文件', //按钮文本
							'uploader' : '${ctx}/tusercon/uploadFile', //处理文件上传Action
							'queueID' : 'fileQueue', //队列的ID
							'queueSizeLimit' : 10, //队列最多可上传文件数量，默认为999
							'auto' : false, //选择文件后是否自动上传，默认为true
							'multi' : true, //是否为多选，默认为true
							'removeCompleted' : true, //是否完成后移除序列，默认为true
							'fileSizeLimit' : '10MB', //单个文件大小，0为无限制，可接受KB,MB,GB等单位的字符串值
							'fileTypeDesc' : 'Image Files', //文件描述
							'fileTypeExts' : '*.gif; *.jpg; *.png; *.bmp;*.tif;*.doc;*.xls;*.zip', //上传的文件后缀过滤器
							/* 'onQueueComplete' : function(event, data) { //所有队列完成后事件
								$("#imgshow").append("<img src=''/>");
								$.messager.alert("提示", "上传完毕！"); //提示完成           
							}, */
							'onUploadError' : function(event, queueId, fileObj,
									errorObj) {
								//alert(errorObj.type + "：" + errorObj.info);
							},
							 //上传到服务器，服务器返回相应信息到data里
			                'onUploadSuccess': function (file, data, response) {
			                	console.info("loadsuccess:"+data);
			                	 var res=eval(data);
			                	$("#imgshow").append("<img src='${ctx}/"+res+"' width='50' height='50'/>"); 
			                	$.messager.alert("提示", "上传完毕！"); //提示完成  
			                	
			                }
						});
	});
</script>
<script type="text/javascript" charset="UTF-8">

//增加
function add_user1() {
	$('#userAddWnd1').show();
	$('#userAddWnd1').dialog({
		iconCls : 'icon-user-add',
		title : '增加用户',
		modal : false,
		buttons : [ {
			text : '确定',
			iconCls : 'icon-ok',
			handler : function() {
				$('#userAddForm1').form('submit', {
					success : function(data) {
						var res = eval('(' + data + ')');
						if (res.statuscode == '1') {
							$.messager.alert('友情提示', res.msg, 'info');
							resetDG('#user');
						} else {
							$.messager.alert('友情提示', res.msg, 'error');
							resetDG('#user');
						}
						$('#userAddWnd').dialog('close');
					}
				});
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#userAddWnd1').dialog('close');
			}
		} ]
	});

	$('#userAddForm').form('reset');
	$('#firstBox').focus();

}



	//增加
	function add_user() {
		$('#userAddWnd').show();
		$('#userAddWnd').dialog({
			iconCls : 'icon-user-add',
			title : '增加用户',
			modal : false,
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					$('#userAddForm').form('submit', {
						success : function(data) {
							var res = eval('(' + data + ')');
							if (res.statuscode == '1') {
								$.messager.alert('友情提示', res.msg, 'info');
								resetDG('#user');
							} else {
								$.messager.alert('友情提示', res.msg, 'error');
								resetDG('#user');
							}
							$('#userAddWnd').dialog('close');
						}
					});
				}
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function() {
					$('#userAddWnd').dialog('close');
				}
			} ]
		});

		$('#userAddForm').form('reset');
		$('#firstBox').focus();

	}
	//修改
	function edit_user() {
		var row = $('#user').datagrid('getSelected');
		if (!row) {
			$.messager.alert('友情提示', '请选择用户', 'error');
		} else {
			$('#userUpdateWnd').show();
			$('#userUpdateWnd').dialog({
				iconCls : 'icon-user-edit',
				title : '修改用户',
				modal : true,
				buttons : [ {
					text : '确定',
					iconCls : 'icon-ok',
					handler : function() {
						$('#userUpdateForm').form('submit', {
							success : function(data) {
								var res = eval('(' + data + ')');
								if (res.statuscode == '1') {
									$.messager.alert('友情提示', res.msg, 'info');
									resetDG('#user');
								} else {
									$.messager.alert('友情提示', res.msg, 'error');
								}
								$('#userUpdateWnd').dialog('close');
							}
						});
					}
				}, {
					text : '取消',
					iconCls : 'icon-cancel',
					handler : function() {
						$('#userUpdateWnd').dialog('close');
					}
				} ]
			});
			//加载数据
			$.post("${ctx}/tusercon/loadUser", {
				"id" : row.id
			}, function(data) {
				if (data) {
					$('#userId').val(data.id);
					$('#userUserName').val(data.username);
					$('#userName').val(data.name);
					$('#userDeptId').combotree('setValue', data.deptid);
					$('#userStationId').combobox('setValue', data.stationid);
				} else {
					$.messager.alert('友情提示', '加载失败', 'error');
				}
			}, "json");

		}
	}
	//分页查询
	function doQuery() {

		var queryParams = $('#user').datagrid('options').queryParams;
		queryParams.username = $('#userNameSch').val();
		queryParams.name = $('#nameSch').val();
		queryParams.status = $('#statusSch').combobox('getValue');
		queryParams.deptid = $('#deptIdSch').combotree('getValue');
		queryParams.stationid = $('#stationIdSch').combobox('getValue');

		//重置当前页数为1
		resetDG('#user');

	}
	//清空
	function doClear() {
		$('#userNameSch').val('');
		$('#nameSch').val('');
		$('#statusSch').val('0');
		$('#statusSch').combobox('setValue','0');

		$('#deptIdSch').combotree('setValue', '0');
		$('#stationIdSch').combobox('setValue', '0');
		doQuery();
	};
	//根据不同值，显示不同内容
	function formatStatus(val, row) {
		if (val == 1) {
			return '正常';
		} else if (val == 2) {
			return '<span style="color:red;">锁定</span>';
		} else {
			return '无';
		}
	}

	//锁定用户
	function lock_user() {
		var row = $('#user').datagrid('getSelected');
		if (!row) {
			$.messager.alert('友情提示', '请选择用户', 'error');
		} else {
			$.messager.confirm('友情提示', '您确定锁定该用户吗？', function(r) {
				if (r) {
					$.post("${ctx}/tusercon/lockUser", {
						"id" : row.id
					}, function(data) {
						//var res=eval('('+data+')');
						if (data.statuscode == '1') {
							$.messager.alert('友情提示', data.msg, 'info');
							resetDG('#user');
						} else {
							$.messager.alert('友情提示', data.msg, 'error');
						}
					}, "json");
				}
			});
		}
	}
	//解锁用户
	function unlock_user() {
		var row = $('#user').datagrid('getSelected');
		if (!row) {
			$.messager.alert('友情提示', '请选择用户', 'error');
		} else {
			if (row.status == 1) {
				$.messager.alert('友情提示', '该用户未被锁定', 'error');
			} else {
				$.messager.confirm('友情提示', '您确定解锁该用户吗？', function(r) {
					if (r) {
						$.post("${ctx}/tusercon/unlockUser", {
							"id" : row.id
						}, function(data) {

							if (data.statuscode == '1') {
								$.messager.alert('友情提示', data.msg, 'info');
								resetDG('#user');
							} else {
								$.messager.alert('友情提示', data.msg, 'error');
							}
						}, "json");
					}
				});
			}
		}
	}
	//重置密码
	function resetpwd_user() {
		var row = $('#user').datagrid('getSelected');
		if (!row) {
			$.messager.alert('友情提示', '请选择用户', 'error');
		} else {
			$.messager.confirm('友情提示', '您确定重置该用户的密码吗？', function(r) {
				if (r) {
					$.post("${ctx}/tusercon/resetPwdUser", {
						"id" : row.id
					}, function(data) {
						if (data.statuscode == "1") {
							$.messager.alert('友情提示', data.msg, 'info');
							resetDG('#user');
						} else {
							$.messager.alert('友情提示', data.msg, 'error');
						}
					}, "json");
				}
			});
		}
	}
	//删除用户
	function del_user() {
		var row = $('#user').datagrid('getSelected');
		if (!row) {
			$.messager.alert('友情提示', '请选择用户', 'error');
		} else {
			$.messager.confirm('友情提示', '您确定锁定该用户吗？', function(r) {
				if (r) {
					$.post("${ctx}/tusercon/deleteUser", {
						"id" : row.id
					}, function(data) {
						//var res=eval('('+data+')');
						if (data.statuscode == '1') {
							$.messager.alert('友情提示', data.msg, 'info');
							resetDG('#user');
						} else {
							$.messager.alert('友情提示', data.msg, 'error');
						}
					}, "json");
				}
			});
		}
	}
</script>
</head>

<body>

	<!-- 主窗口 -->
	<table id="user" class="easyui-datagrid"
		data-options="fit:true,
    								rownumbers:true,
									singleSelect:true,
									striped:true,
									fitColumns:true,
									toolbar:'#tb',
									pagination:true,
									pageSize:20,
									pageList:[20,30,50,100],
									url:'${ctx}/tusercon/userlist'">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'username',align:'center'" width="10%"><strong>用户名</strong></th>
				<th data-options="field:'name',align:'center'" width="30%"><strong>姓名</strong></th>
				<th data-options="field:'dname',align:'center'" width="20%"><strong>所属部门</strong></th>
				<th data-options="field:'stationname',align:'center'" width="20%"><strong>所属岗位</strong></th>
				<th
					data-options="field:'status',align:'center',formatter:formatStatus"
					width="10%"><strong>用户状态</strong></th>
				<th data-options="field:'id',hidden:true"></th>
			</tr>
		</thead>

	</table>
	<!-- Datagrid工具栏 -->
	<div id="tb">
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td class="toolDiv"><a href="javascript:void(0)"
					onclick="add_user()" class="easyui-linkbutton"
					data-options="iconCls:'icon-user-add',plain:true">增加用户</a><span
					class="vline">|</span> <a href="javascript:void(0)"
					onclick="edit_user()" class="easyui-linkbutton"
					data-options="iconCls:'icon-user-edit',plain:true">修改用户</a><span
					class="vline">|</span> <a href="javascript:void(0)"
					onclick="lock_user()" class="easyui-linkbutton"
					data-options="iconCls:'icon-lock',plain:true">锁定用户</a><span
					class="vline">|</span> <a href="javascript:void(0)"
					onclick="unlock_user()" class="easyui-linkbutton"
					data-options="iconCls:'icon-lock-open',plain:true">解锁用户</a><span
					class="vline">|</span> <a href="javascript:void(0)"
					onclick="resetpwd_user()" class="easyui-linkbutton"
					data-options="iconCls:'icon-key',plain:true">密码重置</a>
					<span
					class="vline">|</span> <a href="javascript:void(0)"
					onclick="del_user()" class="easyui-linkbutton"
					data-options="iconCls:'icon-key',plain:true">删除用户</a>
					<span
					class="vline">|</span> <a href="javascript:void(0)"
					onclick="add_user1()" class="easyui-linkbutton"
					data-options="iconCls:'icon-key',plain:true">文件上传</a>
					</td>
			</tr>
			<tr>
				<td class="serchDiv">用户名：<input id="userNameSch"
					class="easyui-validatebox" type="text"> &nbsp;&nbsp;姓名：<input
					id="nameSch" class="easyui-validatebox" type="text">
					&nbsp;&nbsp;状态：
					<!--  
					<select id="statusSch" style="width: 125px;">
						<option value="0">--请选择--</option>
						<option value="1">正常</option>
						<option value="2">锁定</option>
				</select> &nbsp;&nbsp;
				-->
				<input
					id="statusSch" class="easyui-combobox"  type="text"
					data-options="
   valueField: 'value',textField: 'label',data: [{label: '--请选择--',value: '0',selected:true},{label: '启用',value: '1'},{label: '锁定',value: '2'},]
						                        	        ">
				
				
				部门：<input id="deptIdSch" class="easyui-combotree"
					data-options="url:'${ctx}/deptcon/findBypartent',value:'0'"
					style="width: 205px" /> &nbsp;&nbsp;岗位：<input id="stationIdSch"
					class="easyui-combobox"
					data-options="
                        	                                                                url:'${ctx}/stationscon/selectall',
                        	                                                                value:'0',
                        	                                                                valueField:'id',
                        	                                                                textField:'stationname',
                        	                                                                panelHeight:'200'
		                                                                                    "
					style="width: 205px" /> <a href="javascript:void(0)"
					class="easyui-linkbutton"
					data-options="iconCls:'icon-search',plain:true" onclick="doQuery()">查询</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-clear',plain:true" onclick="doClear()">清空</a>
				</td>
			</tr>
		</table>
	</div>
	<!--增加窗口-->
	<div id="userAddWnd"
		style="width: 360px; height: 230px; padding: 5px; background: #fafafa; display: none">
		<form id="userAddForm" method="post"
			action="${ctx }/tusercon/userSave">
			<table cellpadding=3>
				<tr>
					<td>用户名：</td>
					<td><input id="firstBox" name="username"
						class="easyui-validatebox" data-options="required:true"
						validType="remote['${ctx}/tusercon/checkUserName','username']"
						invalidMessage="人员代码重复!" style="width: 200px" /></td>
				</tr>
				<tr>
					<td>姓名：</td>
					<td><input name="name" class="easyui-validatebox"
						data-options="required:true,validType:'chinese'"
						style="width: 200px" /></td>
				</tr>
				<tr>
					<td>所属部门：</td>
					<td><input name="deptid" class="easyui-combotree"
						data-options="url:'${ctx}/deptcon/findBypartent',lines: true"
						style="width: 205px" /></td>
				</tr>
				<tr>
					<td>所属岗位：</td>
					<td><input class="easyui-combobox" name="stationid"
						data-options="
                        	                                                      url:'${ctx}/stationscon/selectall',
                        	                                                      valueField:'id',
                        	                                                      textField:'stationname',
                        	                                                      editable:false,
                        	                                                      panelHeight:'200'"
						style="width: 205px" /></td>
				</tr>
			<!--	<tr>
					<td>文件上传：</td>
					<td>
						<div>
							<input class="easyui-validatebox" type="hidden"
								id="Attachment_GUID" name="Attachment_GUID" /> <input
								id="file_upload" name="file_upload" type="file"
								multiple="multiple"> <a href="javascript:void(0)"
								class="easyui-linkbutton" id="btnUpload"
								data-options="plain:true,iconCls:'icon-save'"
								onclick="javascript: $('#file_upload').uploadify('upload', '*')">上传</a>
							<a href="javascript:void(0)" class="easyui-linkbutton"
								id="btnCancelUpload"
								data-options="plain:true,iconCls:'icon-cancel'"
								onclick="javascript: $('#file_upload').uploadify('cancel', '*')">取消</a>

							<div id="fileQueue" class="fileQueue"></div>
							<div id="div_files"></div>
							<br />
						</div>

					</td>
				</tr>
  <tr>
					<td>文件上传：</td>
					<td>
						<div id="imgshow">
							
						</div>

					</td>
				</tr>-->
			</table>
		</form>
	</div>
	<!--修改窗口-->
	<div id="userUpdateWnd"
		style="width: 360px; height: 230px; padding: 5px; background: #fafafa; display: none">
		<form id="userUpdateForm" method="post"
			action="${ctx }/tusercon/userSave">
			<input id="userId" name="id" type="hidden" />
			<table cellpadding=3>
				<tr>
					<td>用户名：</td>
					<td><input id="userUserName" name="username"
						class="easyui-validatebox" readonly="readonly"
						style="width: 200px" /></td>
				</tr>
				<tr>
					<td>姓名：</td>
					<td><input id="userName" name="name"
						class="easyui-validatebox" data-options="required:true"
						style="width: 200px" /></td>
				</tr>

				<tr>
					<td>所属部门：</td>
					<td><input id="userDeptId" name="deptid"
						class="easyui-combotree"
						data-options="url:'${ctx}/deptcon/findBypartent',lines: true"
						style="width: 205px" /></td>
				</tr>

				<tr>
					<td>所属岗位：</td>
					<td><input class="easyui-combobox" id="userStationId"
						name="stationid"
						data-options="
                        	                                                      url:'${ctx}/stationscon/selectall',
                        	                                                      valueField:'id',
                        	                                                      textField:'stationname',
                        	                                                      editable:false,
                        	                                                      panelHeight:'200'"
						style="width: 205px" /></td>
				</tr>

			</table>
		</form>
	</div>
	
	<div id="userAddWnd1"
		style="width: 450px; height: 330px; padding: 5px; background: #fafafa; display: none">
		<form id="userAddForm" method="post"
			action="">
			<table>
				
				<tr>
					<td>文件上传：</td>
					<td>
						<div>
							<input class="easyui-validatebox" type="hidden"
								id="Attachment_GUID" name="Attachment_GUID" /> <input
								id="file_upload" name="file_upload" type="file"
								multiple="multiple"> <a href="javascript:void(0)"
								class="easyui-linkbutton" id="btnUpload"
								data-options="plain:true,iconCls:'icon-save'"
								onclick="javascript: $('#file_upload').uploadify('upload', '*')">上传</a>
							<a href="javascript:void(0)" class="easyui-linkbutton"
								id="btnCancelUpload"
								data-options="plain:true,iconCls:'icon-cancel'"
								onclick="javascript: $('#file_upload').uploadify('cancel', '*')">取消</a>

							<div id="fileQueue" class="fileQueue"></div>
							<div id="div_files"></div>
							<br />
						</div>

					</td>
				</tr>
<!--  --><tr>
					<td>文件上传：</td>
					<td>
						<div id="imgshow">
							
						</div>

					</td>
				</tr>
			</table>
		</form>
	</div>
	
	
</body>
</html>
