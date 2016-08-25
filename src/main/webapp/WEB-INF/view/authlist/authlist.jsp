<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/base/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${systemOption.systemTitle}</title>
<%@ include file="/static/base/head.jsp"%>
<script type="text/javascript" charset="UTF-8">
	$(function() {
		$('#userlist').datagrid({
			onClickRow : function(rowIndex, rowData) {
				$('#userId').val(rowData.id);
				$('#navIds').val(rowData.navids);
				$("#authlist").treegrid({
					title : ' 《' + rowData.name + '》的权限',
					url : '${ctx}/navigation/initNavsq'
				});
				//$("#authlist").treegrid('load');
			}

		});
		$('#authlist').treegrid({
			onLoadSuccess:function(row, data){
				$("#authlist").treegrid('unselectAll');
			       var navsStr=$('#navIds').val();
			       var arr=navsStr.split(",");
			       //console.info("arr.length:"+arr.length);
			       for(var i=0;i<arr.length;i++){
			           var idstr=arr[i];
			           if(idstr!=''){
			               var id=parseInt(idstr);
			              // console.info("-----"+id);
			               if(!isNaN(id)){
			            	//   console.info("111");
			           	   	$("#authlist").treegrid('select',idstr);
			           	   }
			           }
				   }
				   
			} 
		});

	})
	//保存
	function save_auth() {
		var arr = $("#authlist").treegrid('getSelections');
		var ids = '';
		for (var i = 0; i < arr.length; i++) {
			if(arr[i].id != undefined){
				console.info("a:"+arr[i].id);
				ids = ids + arr[i].id + ',';
			}
			
		}
		if (ids != '') {
			ids = ids.substr(0, ids.length - 1);
		}
		console.info(ids);
		$.messager.confirm('友情提示', '确定吗？', function(r) {
			if (r) {
				$.post("${ctx}/tusercon/userSave", {"id" : $('#userId').val(),"navids" : ids
				}, function(data) {
					if(data.statuscode=="1"){  
						$.messager.alert('友情提示', data.msg, 'info', function() {
							window.location.reload();
						});
					} else {
						$.messager.alert('友情提示', data.msg, 'error');
					}

				}, "json");

			}
		})
	}
	function undo_auth() {
		$("#authlist").treegrid('reload');
	}
	function doQuery() {
		var queryParams = $('#userlist').datagrid('options').queryParams;
		queryParams.name = $('#nameSch').val();
		queryParams.deptid = $('#deptIdSch').combotree('getValue');
		$('#userlist').datagrid('load');

	}
	function doClear() {
		$('#deptIdSch').combotree('setValue','0');
		$('#nameSch').val('');
		 doQuery();
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',iconCls:'icon-user',split:true"
		style="width: 600px;">
		<table id="userlist" class="easyui-datagrid"
			data-options="fit:true,
									title:'选择用户',
									border:false,
    								rownumbers:true,
									singleSelect:true,
									striped:true,
									fitColumns:true,
									toolbar:'#tb2',
									url:'${ctx}/authcon/listAllUser'">
			<thead>
				<tr>
					<th data-options="field:'dname',align:'center'" width="60%"><strong>部门</strong></th>
					<th data-options="field:'name',align:'center'" width="40%"><strong>姓名</strong></th>
					<th data-options="field:'id',hidden:true"></th>
					<th data-options="field:'navids',hidden:true"></th>
				</tr>
			</thead>
		</table>
		<div id="tb2">

			&nbsp;&nbsp;部门：<input id="deptIdSch" class="easyui-combotree"
				data-options="url:'${ctx}/deptcon/findBypartent',value:'0'"
				style="width: 205px" /> &nbsp;&nbsp;姓名：<input id="nameSch"
				class="easyui-validatebox" type="text"> &nbsp;&nbsp;<a
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-search',plain:true" onclick="doQuery()">查询</a>
				&nbsp;&nbsp;<a
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-clear',plain:true" onclick="doClear()">清空</a>
		</div>
	</div>
	<div data-options="region:'center'">
		<!-- 主窗口 -->
		<table id="authlist" class="easyui-treegrid"
			data-options="fit:true,
									animate:true,
									fitColumns:true,
									singleSelect:false,
									toolbar:'#tb',
									idField:'id',
									treeField:'text',
									title:'权限列表'
									">
			<thead>
				<tr>

					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'text',align:'left'" width="100%"><strong>菜单</strong></th>
					<th data-options="field:'id',hidden:true"></th>
				</tr>
			</thead>
			<input id="userId" name="userId" type="hidden" />
			<input id="navIds" type="hidden" />
		</table>
		<div id="tb">
			<a href="javascript:void(0)" onclick="save_auth()"
				class="easyui-linkbutton"
				data-options="iconCls:'icon-save',plain:true">保存</a><span
				class="vline">|</span> <a href="javascript:void(0)"
				onclick="undo_auth()" class="easyui-linkbutton"
				data-options="iconCls:'icon-undo',plain:true">取消</a>
		</div>
		<!-- 主窗口结束 -->
	</div>
</body>
</html>
