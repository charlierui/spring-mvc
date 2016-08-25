<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/base/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>${systemOption.systemTitle}</title>
    <%@ include file="/static/base/head.jsp"%>
    <script type="text/javascript" charset="UTF-8">
    	//增加
    	function add_nav(){
    			$('#navAddWnd').show();
                $('#navAddWnd').dialog({
                	iconCls:'icon-add',
                	title:'增加菜单',
                	modal:true,
                	buttons: [{
						text:'确定',
						iconCls:'icon-ok',
						handler:function(){
							$('#navAddForm').form('submit',{  
							    success: function(data){   
							    	var a = eval('(' + data + ')');
							        if(a.statuscode=='1'){
							            $.messager.alert('友情提示',a.msg,'info'); 
							            $('#navlist').treegrid('reload'); 
							            $('#navparentId').combotree('reload');
							            $('#navparentId2').combotree('reload'); 
							        }else{
							        	$.messager.alert('友情提示',a.msg,'error'); 
							        } 
							        $('#navAddWnd').dialog('close');
							    }  
							});
						}
					},{
						text:'取消',
						iconCls:'icon-cancel',
						handler:function(){
							$('#navAddWnd').dialog('close');
						}
				}]
                });                
    			$('#navAddForm').form('reset');
    	
    	}
    	
    	//修改
    	function edit_nav(){
    		var row = $('#navlist').datagrid('getSelected');
			if (!row){
				$.messager.alert('友情提示','请选择菜单','error');
			}else{
				$('#navUpdateWnd').show();				
				$('#navUpdateWnd').dialog({
                	iconCls:'icon-edit',
                	title:'修改菜单',
                	modal:true,
                	buttons: [{
						text:'确定',
						iconCls:'icon-ok',
						handler:function(){
							$('#navUpdateForm').form('submit',{  
							    success: function(data){   
							    	var a = eval('(' + data + ')');
							        if(a.statuscode=='1'){
							            $.messager.alert('友情提示',a.msg,'info'); 
							            $('#navlist').treegrid('reload'); 
							            $('#navparentId').combotree('reload');
							            $('#navparentId2').combotree('reload');
							        }else{
							        	$.messager.alert('友情提示',a.msg,'error'); 
							        } 
							        $('#navUpdateWnd').dialog('close');
							    }  
							});
						}
					},{
						text:'取消',
						iconCls:'icon-cancel',
						handler:function(){
							$('#navUpdateWnd').dialog('close');
						}
				}]
                });
                //加载数据
                $.post("${ctx}/navigation/loadTNav", {"id": row.id },
					   function(data){
					    if(data){  
							$('#navId').val(data.id);
							$('#navtext').val(data.text);
							$('#navparentId').combotree('setValue',data.parentid);
							$('#naviconCls').val(data.iconcls);
							$('#navurl').val(data.url);
							$('#navsortNum').numberspinner('setValue',data.sortnum);
						}else{
							$.messager.alert('友情提示','加载失败','error'); 
						}
					   }, "json");
				
				
			}
    	
    	} 
    	//删除
    	function remove_nav(){
    		var row = $('#navlist').datagrid('getSelected');
			if (!row){
				$.messager.alert('友情提示','请选择菜单','error');
			}else{
				$.messager.confirm('友情提示','将删除该菜单，确定么？',function(r){   
				    if (r){   
				       $.post("${ctx}/navigation/deleteTNavigation", {"id": row.id },
					   function(data){
					    if(data.statuscode=="1"){  
							$.messager.alert('友情提示',data.msg,'info'); 
							$('#navlist').treegrid('reload'); 
							$('#navparentId').combotree('reload');
							$('#navparentId2').combotree('reload');
						}else{
							$.messager.alert('友情提示',data.msg,'error'); 
						}
					   }, "json");   
				    }   
				});
			
			}
    	}
$(function(){
    	$('#navparentId').combotree({
    		onSelect: function(node){
    			var nid=$('#navId').val();
    			var oldv=$('#navparentId').combotree('getValue');
    			if(node.id==nid){
    				$('#navparentId').combotree('setValue',oldv);
    				$.messager.alert('友情提示','上级菜单不能与当前菜单相同!','error');
    			}
			}
    		
    	});
    	
})   	
    	
    </script>
  </head>
  	
  <body>

<!-- 主窗口 -->  	 
    <table id="navlist" class="easyui-treegrid" data-options="fit:true,
    								rownumbers:true,
									singleSelect:true,
									striped:true,
									animate:true,
									loadMsg:'数据正在路上',
									fitColumns:true,
									toolbar:'#tb',
									idField:'id',
									treeField:'text',
									url:'${ctx}/navigation/navigationList'">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'text',align:'left'" width="30%"><strong>菜单名称</strong></th>
				<th data-options="field:'url',align:'left'" width="40%"><strong>链接URL</strong></th>
				<th data-options="field:'iconCls',align:'left'" width="20%"><strong>图标</strong></th>
				<th data-options="field:'sortNum',align:'center'" width="10%" ><strong>排序</strong></th>
				<th data-options="field:'id',hidden:true"></th>
			</tr>
		</thead>
		
	</table>
<!-- Datagrid工具栏 -->
<div id="tb">
	<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
	   <td class="toolDiv">  		
	  		<a href="javascript:void(0)" onclick="add_nav()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">增加菜单</a><span class="vline">|</span>
			<a href="javascript:void(0)" onclick="edit_nav()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改菜单</a><span class="vline">|</span>			
			<a href="javascript:void(0)" onclick="remove_nav()" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除菜单</a>
		</td>
	</tr>
	</table>
</div>

<!--增加窗口-->
    <div id="navAddWnd"  style="width: 310px; height: 260px; padding: 5px; background: #fafafa;display: none">
    	<form id="navAddForm" method="post" action="${ctx }/navigation/saveTNava">
                <table cellpadding=3>
                	<tr>
                        <td>菜单名称：</td>
                        <td><input id="firstBox" name="text" class="easyui-validatebox"  data-options="required:true" style="width:180px"/></td>
                    </tr>
                    <tr>
                        <td>上级菜单：</td>
                        <td><input id="navparentId2" name="parentid" class="easyui-combotree" value="0" data-options="url:'${ctx}/navigation/navigationCombox',lines: true" style="width:185px"/></td>
                    </tr>
                    <tr>
                        <td>菜单图标：</td>
                        <td><input name="iconcls" class="easyui-validatebox" style="width:180px"/></td>
                    </tr>
                    <tr>
                        <td>链接URL：</td>
                        <td><input name="url" class="easyui-validatebox" style="width:180px"/></td>
					</tr>
					<tr>
                        <td>排序：</td>
                        <td><input name="sortnum" class="easyui-numberspinner"  data-options="value:0,min:0,max:100,editable:false" style="width:185px"/></td>
					</tr>                   
                </table>
        </form>
    </div>
<!--修改窗口-->
    <div id="navUpdateWnd"  style="width: 310px; height: 260px; padding: 5px; background: #fafafa;display: none">
    	<form id="navUpdateForm" method="post" action="${ctx }/navigation/saveTNava">
    		<input id="navId" name="id" type="hidden"/>
                <table cellpadding=3>
                	<tr>
                        <td>菜单名称：</td>
                        <td><input id="navtext" name="text" class="easyui-validatebox"  data-options="required:true" style="width:180px"/></td>
                    </tr>
                    <tr>
                        <td>上级菜单：</td>
                        <td><input id="navparentId" name="parentid" class="easyui-combotree"  data-options="url:'${ctx}/navigation/navigationCombox',lines: true" style="width:185px"/></td>
                    </tr>
                    <tr>
                        <td>菜单图标：</td>
                        <td><input id="naviconCls" name="iconcls" class="easyui-validatebox"  style="width:180px"/></td>
                    </tr>
                    <tr>
                        <td>链接URL：</td>
                        <td><input id="navurl" name="url" class="easyui-validatebox"  style="width:180px"/></td>
					</tr>
					<tr>
                        <td>排序：</td>
                        <td><input id="navsortNum" name="sortnum" class="easyui-numberspinner"  data-options="min:0,max:100,editable:false" style="width:185px"/></td>
					</tr>
				  </select>                    
                </table>
        </form>
    </div> 
  </body>
</html>

