<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/base/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${systemOption.systemTitle}</title>
<%@ include file="/static/base/head.jsp"%>
<script type="text/javascript" charset="UTF-8">
	$(function() {

		// data-options="required:true,validType:'chinese'"
		//默认不选择，点击的时候选择
		//	$('#typetree').tree({
		//		onClick: function(node){
		//   			$("#valueList").datagrid({title:' '+node.text});			    					    			
		//   			$('#valueList').datagrid({url:'${ctx}/json/listDicvalue.action?id='+node.id});
		//   			$('#valueList').datagrid('load');
		//$('#addValueId').validatebox({   
		//                  min:1,
		//                  required:true,
		//                  validType:'remote["${ctx}/json/checkUniqueDicTV.action?tid='+node.id+'","typeValueCheck"]',
		//                  invalidMessage:"类型值已存在!"
		// });
		//$('#valuesoftcode').validatebox({   
		//    min:1,
		//    required:true,
		//    validType:'remote["${ctx}/json/checklxbmTV.action?tid='+node.id+'","typeValueCheck"]',
		//    invalidMessage:"类型编码已存在!"
		//});	    		
		//	}    			
		//});
		//默认选中第一个
		var addr_tree = $("#typetree")
				.tree(
						{
							url : '${ctx}/tdictypecon/listDictype',
							method : "post",
							onSelect : function(node) {
								$("#valueList").datagrid({
									title : ' ' + node.text
								});
								$('#valueList')
										.datagrid(
												{
													url : '${ctx}/tdicvaluecon/listDicvalue?tid='
															+ node.id
												});
								//$('#valueList').datagrid('load');
							},
							onLoadSuccess : function(node, data) {
								$("#typetree li:eq(0)").find("div").addClass(
										"tree-node-selected"); //设置第一个节点高亮
								var n = $("#typetree").tree("getSelected");
								if (n != null) {
									$("#typetree").tree("select", n.target); //相当于默认点击了一下第一个节点，执行onSelect方法
								}
							}
						});
	});

	//增加
	function add_type() {
		$('#typeAddWnd').show();
		$('#typeAddWnd').dialog({
			iconCls : 'icon-add',
			title : '增加字典类型',
			modal : true,
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					$('#typeAddForm').form('submit', {
						success : function(data) {
							var res = eval('(' + data + ')');
							if (res.statuscode == '1') {
								$.messager.alert('友情提示', res.msg, 'info');
								$('#typetree').tree('reload');
							} else {
								$.messager.alert('友情提示', res.msg, 'error');
							}
							$('#typeAddWnd').dialog('close');
						}
					});
				}
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function() {
					$('#typeAddWnd').dialog('close');
				}
			} ]
		});
		$('#typeAddForm').form('reset');
		$('#firstBox').focus();
	}
	//修改
	function edit_type() {
		var node = $('#typetree').tree('getSelected');
		if (!node) {
			$.messager.alert('友情提示', '请先选择要修改的【字典类型】', 'error');
		} else {
			$('#typeUpdateWnd').show();
			$('#typeUpdateWnd').dialog({
				iconCls : 'icon-edit',
				title : '修改字典类型',
				modal : true,
				buttons : [ {
					text : '确定',
					iconCls : 'icon-ok',
					handler : function() {
						$('#typeUpdateForm').form('submit', {
							success : function(data) {
								var res = eval('(' + data + ')');
								if (res.statuscode == '1') {
									$.messager.alert('友情提示', res.msg, 'info');
									$('#typetree').tree('reload');
								} else {
									$.messager.alert('友情提示', res.msg, 'error');
								}
								$('#typeUpdateWnd').dialog('close');
							}
						});
					}
				}, {
					text : '取消',
					iconCls : 'icon-cancel',
					handler : function() {
						$('#typeUpdateWnd').dialog('close');
					}
				} ]
			});
			//加载数据
			$.post("${ctx}/tdictypecon/loadDictype.action", {
				"id" : node.id
			}, function(data) {
				if (data) {
					$('#typeId').val(data.id);
					$('#typevalue').val(data.value);
					$('#typetext').val(data.text);
					$('#typesortnum').numberspinner('setValue', data.sortnum);
					$('#typebak').val(data.bak);
				} else {
					$.messager.alert('友情提示', '加载失败', 'error');
				}
			}, "json");

		}
	}
	function remove_type() {
		var node = $('#typetree').tree('getSelected');
		if (!node) {
			$.messager.alert('友情提示', '请选择字典', 'error');
		} else {
			$.messager.confirm('友情提示', '将删除该字典，确定么？', function(r) {
				if (r) {
					$.post("${ctx}/tdictypecon/removeDictype", {
						"id" : node.id
					}, function(data) {
						if (data.statuscode == "1") {
							$.messager.alert('友情提示', data.msg, 'info');
							$("#valueList").datagrid({
								title : '数据字典 '
							});
							$('#valueList').datagrid('loadData', {
								total : 0,
								rows : []
							});
							$('#typetree').tree('reload');
						} else {
							$.messager.alert('友情提示', data.msg, 'error');
						}
					}, "json");
				}
			});
		}

	}
	//增加字典值
	function add_value() {
		var node = $('#typetree').tree('getSelected');
		if (node != null) {
			$('#valueAddWnd').show();
			$('#valueAddWnd')
					.dialog(
							{
								title : '增加值',
								modal : true,
								buttons : [
										{
											text : '确定',
											iconCls : 'icon-ok',
											handler : function() {
												$('#valueAddForm')
														.form(
																'submit',
																{
																	url : '${ctx}/tdicvaluecon/saveDicvalue?tid='
																			+ node.id,
																	success : function(
																			data) {
																		var res = eval('('
																				+ data
																				+ ')');
																		if (res.statuscode == '1') {
																			$.messager
																					.alert(
																							'友情提示',
																							res.msg,
																							'info');
																			$(
																					'#valueAddWnd')
																					.dialog(
																							'close');
																		} else {
																			$.messager
																					.alert(
																							'友情提示',
																							res.msg,
																							'error');
																		}

																		$(
																				'#valueList')
																				.datagrid(
																						'reload');
																	}
																});
											}
										},
										{
											text : '取消',
											iconCls : 'icon-cancel',
											handler : function() {
												$('#valueAddWnd').dialog(
														'close')
											}
										} ]
							});
			$('#valueAddForm').form('reset');
		} else {
			$.messager.alert('友情提示', '请先选择【字典类型】', 'error');
		}

	}
	//修改字典值
	function edit_value() {
		var row = $('#valueList').datagrid('getSelected');
		if (!row) {
			$.messager.alert('友情提示', '请选择字典值', 'error');
		} else {
			$('#valueUpdateWnd').show();
			$('#valueUpdateWnd')
					.dialog(
							{
								iconCls : 'icon-edit',
								title : '修改字典类值',
								modal : true,
								buttons : [
										{
											text : '确定',
											iconCls : 'icon-ok',
											handler : function() {
												$('#valueUpdateForm')
														.form(
																'submit',
																{
																	url : '${ctx}/tdicvaluecon/saveDicvalue',
																	success : function(
																			data) {
																		var res = eval('('
																				+ data
																				+ ')');
																		if (res.statuscode == '1') {
																			$.messager
																					.alert(
																							'友情提示',
																							res.msg,
																							'info');
																			$(
																					'#valueList')
																					.datagrid(
																							'clearSelections');
																			$(
																					'#valueList')
																					.datagrid(
																							'load');
																		} else {
																			$.messager
																					.alert(
																							'友情提示',
																							res.msg,
																							'error');
																		}
																		$(
																				'#valueUpdateWnd')
																				.dialog(
																						'close');
																	}
																});
											}
										},
										{
											text : '取消',
											iconCls : 'icon-cancel',
											handler : function() {
												$('#valueUpdateWnd').dialog(
														'close');
											}
										} ]
							});
			//加载修改弹出框数据
			$('#valuevid').val(row.vid);
			$('#valuesoftcodeup').val(row.softcode);
			$('#valuetext').val(row.text);
			$('#valueid').val(row.id);
			$('#valuesortnum').numberspinner('setValue', row.sortnum);
			//$('#valuepkgroup').val(row.pkgroup);
			//$('#valuepkorg').val(row.pkorg);
			//$('#valuepkorgv').val(row.pkorgv);
			$('#valuestatus').combobox('setValue', row.status);
			$('#valuebak').val(row.bak);
			$('#valueList').datagrid('clearSelections');
		}
	}
	function remove_value() {
		var row = $('#valueList').datagrid('getSelected');

		if (!row) {
			$.messager.alert('友情提示', '请选择字典值', 'error');
		} else {
			$.messager.confirm('友情提示', '将删除该字典值，确定么？', function(r) {
				if (r) {
					$.post("${ctx}/tdicvaluecon/removeDicvalue", {
						"vid" : row.vid
					}, function(data) {
						if (data.statuscode == '1') {
							$.messager.alert('友情提示', data.msg, 'info');
							$('#valueList').datagrid('load');
						} else {
							$.messager.alert('友情提示', data.msg, 'error');
						}
					}, "json");
				}
			});
		}

	}
	//根据不同值，显示不同内容
	function formatStatus(val, row) {
		if (val == 1) {
			return '启用';
			//return '<img src="${ctx}/images/checkmark.gif"/>';
		} else if (val == 0) {
			return '禁用';
			//return '<img src="${ctx}/images/checknomark.gif"/>';
		} else {
			return '无';
		}
	}
	//分页查询
	function doQuery() {
		var node = $('#typetree').tree('getSelected');

		if (node == null) {
			$.messager.alert('友情提示', '请先选择【字典类别】', 'error');
		} else {
			var queryParams = $('#valueList').datagrid('options').queryParams;
			queryParams.softcode = $('#softcodeSch').val();
			queryParams.text = $('#textSch').val();
			queryParams.status = $("#statusSch").val();
			//queryParams.fromDateSch=$('#fromDateSch').datebox('getValue');
			//queryParams.toDateSch=$('#toDateSch').datebox('getValue');
			console.info(queryParams);
			//重置当前页数为1
			resetDG('#valueList');
		}

	}
	//清空
	function doClear() {

		var node = $('#typetree').tree('getSelected');
		if (node == null) {
			$.messager.alert('友情提示', '请先选择【字典类别】', 'error');
		} else {
			$('#softcodeSch').val('');
			$('#textSch').val('');
			$("#statusSch").val('2');
			//$('#fromDateSch').datebox('setValue','');
			//$('#toDateSch').datebox('setValue','');
			doQuery();
			/* var queryParams=$('#valueList').datagrid('options').queryParams;
			queryParams.softcodeSch=$('#softcodeSch').val(); 	
			queryParams.textSch=$('#textSch').val();
			queryParams.statusSch=$("#statusSch").combobox('getValue');
			queryParams.fromDateSch=$('#fromDateSch').datebox('getValue');
			queryParams.toDateSch=$('#toDateSch').datebox('getValue');
			resetDG('#valueList'); */
			//$("#statusSch").combobox('setValue','--请选择--');
		}
	}
	//日期格式化
	function formatRQ(val, row) {
		if (val != null) {
			var a = val.indexOf('T');
			//alert(a);
			var b = val.substring(0, a)
			//console.info(b);
			return b;
		} else {
			return '';
		}
	}
	function myformatter(date) {
		alert(date);
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
				+ (d < 10 ? ('0' + d) : d);
	}
</script>
</head>

<body>
	<div class="easyui-layout" fit="true">
		<div data-options="region:'west',split:true"
			style="width: 200px; border: 0">
			<div id="p" class="easyui-panel" title="字典类别"
				data-options="fit:true,iconCls:'icon-book-red'">
				<div class="toolbar">
					<a href="javascript:void(0)" onclick="add_type()"
						class="easyui-linkbutton"
						data-options="iconCls:'icon-add',plain:true">增加</a> <a
						href="javascript:void(0)" onclick="edit_type()"
						class="easyui-linkbutton"
						data-options="iconCls:'icon-edit',plain:true">修改</a> <a
						href="javascript:void(0)" onclick="remove_type()"
						class="easyui-linkbutton"
						data-options="iconCls:'icon-cancel',plain:true">删除</a>
				</div>

				<!--  <ul id="typetree" animate="true" class="easyui-tree"
						data-options="url:'${ctx}/json/listDictype.action',checked:true"></ul>-->

				<ul id="typetree" animate="true"></ul>

			</div>
		</div>
		<div data-options="region:'center'" style="border: 0">
			<table id="valueList" class="easyui-datagrid" title="数据字典"
				data-options="fit:true,
					singleSelect:true,
					toolbar:'#tb',
					fitColumns:true,
					pagination:true,
					pageSize:10,
					pageList:[10,20,30,50,100]
					">
				<thead align="center">
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'softcode',width:82" align="center">
							类型编码</th>
						<th data-options="field:'text',width:80" align="center">类型名称
						</th>
						<th data-options="field:'id',width:80" align="center">类型值</th>

						<th data-options="field:'status',width:40,formatter:formatStatus"
							align="center">状态</th>
						<th data-options="field:'sortnum',width:40" align="center">
							排序</th>
						<th data-options="field:'bak',width:250" align="center">备注</th>
						<th data-options="field:'vid',hidden:true" align="center"></th>
					</tr>

				</thead>
			</table>
		</div>
	</div>
	<!-- Datagrid工具栏 -->
	<div id="tb">
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td class="toolDiv"><a href="javascript:void(0)"
					onclick="add_value()" class="easyui-linkbutton"
					data-options="iconCls:'icon-add',plain:true">增加值</a><span
					class="vline">|</span> <a href="javascript:void(0)"
					onclick="edit_value()" class="easyui-linkbutton"
					data-options="iconCls:'icon-edit',plain:true">修改值</a><span
					class="vline">|</span> <a href="javascript:void(0)"
					onclick="remove_value()" class="easyui-linkbutton"
					data-options="iconCls:'icon-remove',plain:true">删除值</a></td>
			</tr>
			<tr>
				<td class="serchDiv">类型编码： <input id="softcodeSch"
					name="softcodeSch" class="easyui-validatebox" type="text">
					&nbsp;&nbsp;类型名称： <input id="textSch" name="textSch"
					class="easyui-validatebox" type="text"> &nbsp;&nbsp;状态： <input
					id="statusSch" class="easyui-combobox" value="0" type="text"
					data-options="
   valueField: 'value',textField: 'label',data: [{label: '--请选择--',value: '2',selected:true},{label: '启用',value: '1'},{label: '禁用',value: '0'},]
						                        	                                                      ">
					<!--  <select id="statusSch"
							 style="width: 125px;">
							<option value="2" selected="selected">
								--请选择--
							</option>
							<option value="1">
								启用
							</option>
							<option value="0">
								禁用
							</option>
						</select>
						--> <!-- &nbsp;&nbsp;时间：
						<input id="fromDateSch" class="easyui-datebox" type="text" />
						至
						<input id="toDateSch" class="easyui-datebox" /> --> 
						<a
					href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-search',plain:true" onclick="doQuery()">查询</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-clear',plain:true" onclick="doClear()">清空</a>
				</td>
			</tr>
		</table>
	</div>
	<!-- 增加字典类型窗口 -->
	<div id="typeAddWnd"
		style="width: 310px; height: 260px; padding: 5px; background: #fafafa; display: none">
		<form id="typeAddForm" method="post"
			action="${ctx }/tdictypecon/saveDictype">
			<table cellpadding=3>
				<tr>
					<td>类型名：</td>
					<td><input id="firstBox" name="text"
						class="easyui-validatebox" data-options="required:true"
						style="width: 180px" /></td>
				</tr>
				<tr>
					<td>类型值：</td>
					<td><input name="value" class="easyui-validatebox"
						data-options="required:true"
						validType="remote['${ctx}/tdictypecon/checkDictypeValue','value']"
						invalidMessage="类型值重复!" style="width: 180px" /></td>
				</tr>
				<tr>
					<td>排序：</td>
					<td><input name="sortnum" class="easyui-numberspinner"
						data-options="value:0,min:0,max:500,editable:false"
						style="width: 185px" /></td>
				</tr>

				<tr>
					<td>备注：</td>
					<td><textarea name="bak" cols="33" rows="3" /></textarea>
				</tr>
			</table>
		</form>
	</div>
	<!--修改字典类型窗口-->
	<div id="typeUpdateWnd"
		style="width: 310px; height: 260px; padding: 5px; background: #fafafa; display: none">
		<form id="typeUpdateForm" method="post"
			action="${ctx }/tdictypecon/saveDictype">
			<input id="typeId" name="id" type="hidden" />
			<table cellpadding=3>
				<tr>
					<td>类型名：</td>
					<td><input id="typetext" name="text"
						class="easyui-validatebox" data-options="required:true"
						style="width: 180px" /></td>
				</tr>
				<tr>
					<td>类型值：</td>
					<td><input id="typevalue" name="value"
						class="easyui-validatebox" data-options="required:true"
						readonly="readonly" style="width: 180px" /></td>
				</tr>
				<tr>
					<td>排序：</td>
					<td><input id="typesortnum" name="sortnum"
						class="easyui-numberspinner"
						data-options="value:0,min:0,max:500,editable:false"
						style="width: 185px" /></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td><textarea id="typebak" name="bak" cols="33" rows="3" /></textarea>
				</tr>
			</table>
		</form>
	</div>
	<!-- 增加字典值窗口 -->
	<div id="valueAddWnd"
		style="width: 580px; height: 280px; padding: 5px; background: #fafafa; display: none">
		<form id="valueAddForm" method="post">
			<table cellpadding=3>
				<tr>
					<td id="lxbm">类型编码：</td>
					<td><input id="valuesoftcode" name="softcode"
						style="width: 180px" class="easyui-validatebox"
						data-options="required:true,validType:'number'" /></td>
					<td>类型名称：</td>
					<td><input name="text" class="easyui-validatebox"
						data-options="required:true,validType:'chinese'"
						style="width: 180px" /></td>
				</tr>
				<tr>
					<td>类型值：</td>
					<td><input id="addValueId" class="easyui-validatebox"
						data-options="required:true,validType:'english'" name="id"
						style="width: 180px" /></td>
					<td>排序：</td>
					<td><input name="sortnum" class="easyui-numberspinner"
						data-options="value:0,min:0,max:500,editable:false"
						style="width: 185px" /></td>
				</tr>

				<td>状态：</td>
				<td><select id="valuestatus" class="easyui-combobox"
					name="status" style="width: 185px;">
						<option value="1">启用</option>
						<option value="0">禁用</option>
				</select></td>
				</tr>

				<tr>
					<td>备注：</td>
					<td colspan="3"><textarea name="bak" cols="60" rows="3" /></textarea>
				</tr>
			</table>
		</form>
	</div>
	<!-- 修改字典值窗口 -->
	<div id="valueUpdateWnd"
		style="width: 580px; height: 280px; padding: 5px; background: #fafafa; display: none">
		<form id="valueUpdateForm" method="post">
			<input id="valuevid" name="vid" type="hidden" />
			<table cellpadding=3>
				<tr>
					<td>类型编码：</td>
					<td><input id="valuesoftcodeup" name="softcode"
						class="easyui-validatebox" data-options="required:true"
						style="width: 180px" /></td>
					<td>类型名称：</td>
					<td><input id="valuetext" name="text"
						class="easyui-validatebox" data-options="required:true"
						style="width: 180px" /></td>
				</tr>

				<tr>
					<td>类型值：</td>
					<td><input id="valueid" name="id" class="easyui-validatebox"
						readonly="readonly" style="width: 180px" /></td>
					<td>排序：</td>
					<td><input id="valuesortnum" name="sortnum"
						class="easyui-numberspinner"
						data-options="value:0,min:0,max:500,editable:false"
						style="width: 185px" /></td>
				</tr>

				<tr>
					<td>状态：</td>
					<td><select id="valuestatus" class="easyui-combobox"
						name="status" style="width: 185px;">
							<option value="1">启用</option>
							<option value="0">禁用</option>
					</select></td>
				</tr>


				<tr>
					<td>备注：</td>
					<td colspan="3"><textarea id="valuebak" name="bak" cols="60"
							rows="3" /></textarea>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>
