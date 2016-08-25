<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/base/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>${systemOption.systemTitle}</title>
<%@ include file="/static/base/head.jsp"%>
<script type="text/javascript" charset="UTF-8">
	//增加
	function add_station() {
		$('#stationAddWnd').show();
		$('#stationAddWnd').dialog({
			iconCls : 'icon-user-add',
			title : '增加岗位',
			modal : true,
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					$('#stationAddForm').form('submit', {
						success : function(data) {
							var res = eval('(' + data + ')');
							if (res.statuscode == '1') {
								$.messager.alert('友情提示', res.msg, 'info');
								resetDG('#station');
							} else {
								$.messager.alert('友情提示', res.msg, 'error');
							}
							$('#stationAddWnd').dialog('close');
						}
					});
				}
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function() {
					$('#stationAddWnd').dialog('close');
				}
			} ]
		});
		$('#stationAddForm').form('reset');
		$('#firstBox').focus();
	}
	//修改
	function edit_station() {
		var row = $('#station').datagrid('getSelected');
		if (!row) {
			$.messager.alert('友情提示', '请选择用户', 'error');
		} else {
			$('#stationUpdateWnd').show();
			$('#stationUpdateWnd').dialog({
				iconCls : 'icon-user-edit',
				title : '修改岗位',
				modal : true,
				buttons : [ {
					text : '确定',
					iconCls : 'icon-ok',
					handler : function() {
						$('#stationUpdateForm').form('submit', {
							success : function(data) {
								var res = eval('(' + data + ')');
								if (res.statuscode == '1') {
									$.messager.alert('友情提示', res.msg, 'info');
									resetDG('#station');
								} else {
									$.messager.alert('友情提示', res.msg, 'error');
								}
								$('#stationUpdateWnd').dialog('close');
							}
						});
					}
				}, {
					text : '取消',
					iconCls : 'icon-cancel',
					handler : function() {
						$('#stationUpdateWnd').dialog('close');
					}
				} ]
			});
			//加载数据
			$.post("${ctx}/stationscon/loadStation.action", {
				"id" : row.id
			}, function(data) {
				if (data) {
					$('#stationId').val(data.id);
					$('#stationStationCode').val(data.stationcode);
					$('#stationStationName').val(data.stationname);
					$('#stationStationRemark').val(data.stationremark);
				} else {
					$.messager.alert('友情提示', '加载失败', 'error');
				}
			}, "json");

		}
	}
	//删除
	function remove_stations(){
		var row = $('#station').datagrid('getSelected');
		if (!row){
			$.messager.alert('友情提示','请选择部门','error');
		}else{
			$.messager.confirm('友情提示','将删除该岗位，确定么？',function(r){   
			    if (r){   
			       $.post("${ctx}/stationscon/deleteStation", {"id": row.id },
				   function(data){
				    if(data.statuscode=="1"){  
						$.messager.alert('友情提示',data.msg,'info'); 
						resetDG('#station');
					}else{
						$.messager.alert('友情提示',data.msg,'error'); 
					}
				   }, "json");   
			    }   
			});
		
		}
	}
	//清空
	  function doClear(){
		  $('#stationCode').val('');
		  $('#stationName').val('');
		  
		   doQuery();
	  };
	//分页查询
	function doQuery() {

		var queryParams = $('#station').datagrid('options').queryParams;
		queryParams.stationcode = $('#stationCode').val();
		queryParams.stationname = $('#stationName').val();
		

		//重置当前页数为1
		resetDG('#station');

	}
</script>

</head>

<body>

	<!-- 主窗口 -->
	<table id="station" class="easyui-datagrid"
		data-options="fit:true,
    								rownumbers:true,
									singleSelect:true,
									striped:true,
									fitColumns:true,
									toolbar:'#tb',
									pagination:true,
									pageSize:20,
									pageList:[20,30,50,100],
									url:'${ctx}/stationscon/stationlist'">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'stationcode',align:'center'" width="30%"><strong>岗位编码</strong></th>
				<th data-options="field:'stationname',align:'center'" width="30%"><strong>岗位名称</strong></th>
				<th data-options="field:'stationremark',align:'center'" width="40%"><strong>备注</strong></th>
				<th data-options="field:'id',hidden:true"></th>
			</tr>
		</thead>

	</table>
	<!-- Datagrid工具栏 -->
	<div id="tb">
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td class="toolDiv"><a href="javascript:void(0)"
					onclick="add_station()" class="easyui-linkbutton"
					data-options="iconCls:'icon-user-add',plain:true">增加岗位</a><span
					class="vline">|</span> <a href="javascript:void(0)"
					onclick="edit_station()" class="easyui-linkbutton"
					data-options="iconCls:'icon-user-edit',plain:true">修改岗位</a><span
					class="vline">|</span>
					<a href="javascript:void(0)"
					onclick="remove_stations()" class="easyui-linkbutton"
					data-options="iconCls:'icon-remove',plain:true">删除岗位</a>
					<span class="vline">|</span>
					</td>
			</tr>
			<tr>
				<td class="serchDiv">岗位编码：<input id="stationCode"
					class="easyui-validatebox" type="text"> &nbsp;&nbsp;岗位名称：<input
					id="stationName" class="easyui-validatebox" type="text"> <!--  &nbsp;&nbsp;岗位备注：<input id="stationRemark" class="easyui-validatebox" type="text">-->
					&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-search',plain:true" onclick="doQuery()">查询</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-clear',plain:true" onclick="doClear()">清空</a>
				</td>
			</tr>
		</table>
	</div>
	<!--增加窗口-->
	<div id="stationAddWnd"
		style="width: 360px; height: 220px; padding: 5px; background: #fafafa; display: none">
		<form id="stationAddForm" method="post"
			action="${ctx }/stationscon/saveStation">
			<table cellpadding=3>
				<tr>
					<td>岗位编码：</td>
					<td><input id="firstBox" name="stationcode"
						class="easyui-validatebox" data-options="required:true"
						validType="remote['${ctx}/stationscon/checkStationCode','stationcode']"
						invalidMessage="岗位编码已存在!" style="width: 200px" /></td>
				</tr>
				<tr>
					<td>岗位名称：</td>
					<td><input name="stationname" class="easyui-validatebox"
						data-options="required:true" style="width: 200px" /></td>
				</tr>
				<tr>
					<td>岗位备注：</td>
					<td><textarea name="stationremark" cols="30" rows="3" /></textarea></td>
				</tr>
				</select>
			</table>
		</form>
	</div>
	<!--修改窗口-->
	<div id="stationUpdateWnd"
		style="width: 360px; height: 220px; padding: 5px; background: #fafafa; display: none">
		<form id="stationUpdateForm" method="post"
			action="${ctx }/stationscon/saveStation">
			<input id="stationId" name="id" type="text" />
			<table cellpadding=3>
				<tr>
					<td>岗位编码：</td>
					<td><input id="stationStationCode" name="stationcode"
						class="easyui-validatebox" data-options="required:true"
						validType="remote['${ctx}/stationscon/checkStationCode','stationcode']"
						invalidMessage="岗位编码已存在!" style="width: 200px" /></td>
				</tr>
				<tr>
					<td>岗位名称：</td>
					<td><input id="stationStationName" name="stationname"
						class="easyui-validatebox" data-options="required:true"
						style="width: 200px" /></td>
				</tr>
				<tr>
					<td>岗位备注：</td>
					<td><textarea id="stationStationRemark" name="stationremark"
							cols="30" rows="3" /></textarea></td>
				</tr>
				</select>
			</table>
		</form>
	</div>

</body>
</html>
