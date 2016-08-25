<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/base/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>${systemOption.systemTitle}</title>
    <%@ include file="/static/base/head.jsp"%>
    <script type="text/javascript" charset="UTF-8">
    
      //分页查询
	function doQuery(){
	  	var queryParams=$('#logBookList').datagrid('options').queryParams;
	  	queryParams.username=$('#userNameSch').val(); 	
	  	queryParams.fromDateSch=$('#fromDateSch').datebox('getValue');
	  	queryParams.toDateSch=$('#toDateSch').datebox('getValue');
	  	//重置当前页数为1
	  	resetDG('#logBookList');

	} 
	function doClear(){
		$('#userNameSch').val()
		$('#fromDateSch').datebox('setValue','')
		$('#toDateSch').datebox('setValue','')
		doQuery();
	}
	
	  function delete_logbook(){
			//var node = $('#stock').datagrid('getSelected');
			var arr=$('#logBookList').datagrid('getSelections');
			if (arr.length==0){
				$.messager.alert('友情提示','请选择要删除的数据','error');
			}else{
				
				var idsdelete='';
		 		for(var i=0;i<arr.length;i++){
		 			idsdelete=idsdelete+arr[i].id+',';	 		
		 		}
				$.messager.confirm('友情提示','确定要删除吗？',function(r){   
					    if (r){
					       $.post("${ctx}/tlogbookcon/deletelogbook", {"id": idsdelete },
						   function(data){
					    	   if (data.statuscode == '1') {
									$.messager.alert('友情提示', data.msg, 'info');
									resetDG('#logBookList');
								} else {
									$.messager.alert('友情提示', data.msg, 'error');
								}
						   }, "json");   
					    }   
					});
			
			}
		}
	  
	//增加
		function add_user() {
			
			$('#userAddWnd').show();
			$('#userAddWnd').dialog({
				iconCls : 'icon-user-add',
				title : '增加用户',				
				modal : false,
				onOpen:function(){$(".window").css("z-index","1001");$(".window-shadow").css("z-index","900");},
				onResize:function(){$(".window").css("z-index","1001");$(".window-shadow").css("z-index","900");},
				onMove:function(){$(".window").css("z-index","1001");$(".window-shadow").css("z-index","900");},
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
    </script>
  </head>
  
  <body>
        
<!-- 主窗口 ,formatter:-->  	 
    <table id="logBookList" class="easyui-datagrid" data-options="fit:true,
    								title:'系统日志',
    								iconCls:'icon-page',
    								rownumbers:true,
									striped:true,
									fitColumns:true,
									toolbar:'#tb',
									pagination:true,
									singleSelect:false,
									pageSize:20,
									loadMsg : '数据正在路上', 
									pageList:[20,30,50,100],
									url:'${ctx}/tlogbookcon/logbooklist'">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',hidden:true" width="5%"><strong>ID</strong></th>
				<th data-options="field:'username',align:'center'" width="10%"><strong>用户名</strong></th>
				<th data-options="field:'ip',align:'center'" width="10%"><strong>地址IP</strong></th>				
				<th data-options="field:'requesturi',align:'center'" width="25%"><strong>访问路径</strong></th>
				<th data-options="field:'mark',align:'center'" width="20%"><strong>方法耗时</strong></th>
				<th data-options="field:'description',align:'center'" width="25%" ><strong>描述</strong></th>
				<th data-options="field:'ldate',align:'center',formatter:formatDateBoxFull" width="15%" ><strong>时间</strong></th>
				
			</tr>
		</thead>
		
	</table>
<!-- Datagrid工具栏 -->
<div id="tb">
	<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
	   <td style="padding:2px">
			用户名：<input id="userNameSch"  type="text">
			
			&nbsp;&nbsp;时间：<input id="fromDateSch" style="width:150px" class="easyui-datetimebox" />至<input id="toDateSch" style="width:150px" class="easyui-datetimebox"/>
			&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
			onclick="doQuery()">查询</a>
			&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
			onclick="doClear()">清空</a>
			&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
			onclick="add_user()">ueditor</a>
			<c:if test="${user.username=='admin'}">
			<a href="javascript:void(0)" onclick="delete_logbook()"
							class="easyui-linkbutton"
							data-options="iconCls:'icon-user-delete',plain:true">日志清理</a><span
							class="vline">|</span></c:if>
		</td>
	</tr>
	</table>
</div>    
<!--增加窗口-->
	<div id="userAddWnd"
		style="width: 1000px; height: 320px; padding: 5px; background: #fafafa; display: none">
		<form id="userAddForm" method="post"
			action="${ctx }/tusercon/userSave">
			<table cellpadding=3>
				<tr>
					<textarea id="hcontents22222" name="hcontents"></textarea>
				<script type="text/javascript">
					UE.getEditor('hcontents22222');
				</script>
				</tr>
				
			</table>
  </body>
</html>
