<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/base/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>${systemOption.systemTitle}</title>
    <%@ include file="/static/base/head.jsp"%>
    <script type="text/javascript" charset="UTF-8">
    	//增加
    	function add_dept(){
    			$('#deptAddWnd').show();
                $('#deptAddWnd').dialog({
                	iconCls:'icon-add',
                	title:'增加部门',
                	modal:true,
                	buttons: [{
						text:'确定',
						iconCls:'icon-ok',
						handler:function(){
							$('#deptAddForm').form('submit',{  
							    success: function(data){   
							    	
							    	var a = eval('(' + data + ')');
							        if(a.statuscode=='1'){
							            $.messager.alert('友情提示',a.msg,'info'); 
							            $('#deptlist').treegrid('reload'); 
							            $('#deptparentId').combotree('reload');
							            $('#deptparentId2').combotree('reload'); 
							        }else{
							        	$.messager.alert('友情提示',a.msg,'error'); 
							        } 
							 
							        $('#deptAddWnd').dialog('close');
							    }  
							});
						}
					},{
						text:'取消',
						iconCls:'icon-cancel',
						handler:function(){
							$('#deptAddWnd').dialog('close');
						}
				}]
                });                
    			$('#deptAddForm').form('reset');
    	
    	}
    	
    	//修改
    	function edit_dept(){
    		var row = $('#deptlist').datagrid('getSelected');
			if (!row){
				$.messager.alert('友情提示','请选择部门','error');
			}else{
				$('#deptUpdateWnd').show();				
				$('#deptUpdateWnd').dialog({
                	iconCls:'icon-edit',
                	title:'修改部门',
                	modal:true,
                	buttons: [{
						text:'确定',
						iconCls:'icon-ok',
						handler:function(){
							$('#deptUpdateForm').form('submit',{  
							    success: function(a){   
							    	var data = eval('(' + a + ')');
							        if(data.statuscode=='1'){
							            $.messager.alert('友情提示',data.msg,'info'); 
							            $('#deptlist').treegrid('reload'); 
							            $('#deptparentId').combotree('reload');
							            $('#deptparentId2').combotree('reload');
							        }else{
							        	$.messager.alert('友情提示',data.msg,'error'); 
							        } 
							        $('#deptUpdateWnd').dialog('close');
							    }  
							});
						}
					},{
						text:'取消',
						iconCls:'icon-cancel',
						handler:function(){
							$('#deptUpdateWnd').dialog('close');
						}
				}]
                });
                //加载数据
                $.post("${ctx}/deptcon/loadDept", {"id": row.id },
					   function(data){
					    if(data){ 				        
							$('#deptId').val(data.id);
							$('#deptdname').val(data.dname);
							$('#deptparentId').combotree('setValue',data.parentid);
							$('#deptbak').val(data.bak);
							$('#deptdsortNum').numberspinner('setValue',data.dsortnum);
						}else{
							$.messager.alert('友情提示','加载失败','error'); 
						}
					   }, "json");
				
				
			}
    	
    	} 
    	//删除
    	function remove_dept(){
    		var row = $('#deptlist').datagrid('getSelected');
			if (!row){
				$.messager.alert('友情提示','请选择部门','error');
			}else{
				$.messager.confirm('友情提示','将删除该部门，确定么？',function(r){   
				    if (r){   
				       $.post("${ctx}/deptcon/deleteTDept", {"id": row.id },
					   function(data){
					    if(data.statuscode=="1"){  
							$.messager.alert('友情提示',data.msg,'info'); 
							$('#deptlist').treegrid('reload'); 
							$('#deptparentId').combotree('reload');
							$('#deptparentId2').combotree('reload');
						}else{
							$.messager.alert('友情提示',data.msg,'error'); 
						}
					   }, "json");   
				    }   
				});
			
			}
    	}
$(function(){
    	$('#deptparentId').combotree({
    		onSelect: function(node){
    			var nid=$('#deptId').val();
    			var oldv=$('#deptparentId').combotree('getValue');
    			if(node.id==nid){
    				$('#deptparentId').combotree('setValue',oldv);
    				$.messager.alert('友情提示','上级部门不能与当前部门相同!','error');
    			}
			}
    		
    	});
    	
})   	
    	
    </script>
  </head>
  	
  <body>

<!-- 主窗口 -->  	 
    <table id="deptlist" class="easyui-treegrid" data-options="fit:true,
    								rownumbers:true,
									singleSelect:true,
									striped:true,
									animate:true,
									fitColumns:true,
									toolbar:'#tb',
									idField:'id',
									treeField:'dname',
									url:'${ctx}/deptcon/deptList'">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'dname',align:'left'" width="30%"><strong>部门名称</strong></th>
				<th data-options="field:'dsortnum',align:'center'" width="10%" ><strong>排序</strong></th>				
				<th data-options="field:'bak',align:'left'" width="60%"><strong>备注</strong></th>
				<th data-options="field:'id',hidden:true"></th>
			</tr>
		</thead>
		
	</table>
<!-- Datagrid工具栏 -->
<div id="tb">
	<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
	   <td class="toolDiv">  		
	  		<a href="javascript:void(0)" onclick="add_dept()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">增加部门</a><span class="vline">|</span>
			<a href="javascript:void(0)" onclick="edit_dept()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改部门</a><span class="vline">|</span>			
			<a href="javascript:void(0)" onclick="remove_dept()" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除部门</a>
		</td>
	</tr>
	</table>
</div>

<!--增加窗口-->
    <div id="deptAddWnd"  style="width: 310px; height: 260px; padding: 5px; background: #fafafa;display: none">
    	<form id="deptAddForm" method="post" action="${ctx }/deptcon/saveTDept">
                <table cellpadding=3>
                	<tr>
                        <td>部门名称：</td>
                        <td><input id="firstBox" name="dname" class="easyui-validatebox"  data-options="required:true" style="width:180px"/></td>
                    </tr>
                    <tr>
                        <td>上级部门：</td>
                        <td><input id="deptparentId2" name="parentid" class="easyui-combotree" value="0" data-options="url:'${ctx}/deptcon/findBypartent',lines: true" style="width:185px"/></td>
                    </tr>
                    <tr>
                        <td>排序：</td>
                        <td><input name="dsortnum" class="easyui-numberspinner"  data-options="value:0,min:0,max:100,editable:false" style="width:185px"/></td>
					</tr>
                    <tr>
                        <td>备注：</td>
                        <td><textarea name="bak" cols="30" rows="3"/></textarea>
                    </tr>				                      
                </table>
        </form> 
    </div>
<!--修改窗口-->
    <div id="deptUpdateWnd"  style="width: 310px; height: 260px; padding: 5px; background: #fafafa;display: none">
    	<form id="deptUpdateForm" method="post" action="${ctx }/deptcon/saveTDept">
    		<input id="deptId" name="id" type="hidden"/>
                <table cellpadding=3>
                	<tr>
                        <td>部门名称：</td>
                        <td><input id="deptdname" name="dname" class="easyui-validatebox"  data-options="required:true" style="width:180px"/></td>
                    </tr>
                    <tr>
                        <td>上级部门：</td>
                        <td><input id="deptparentId" name="parentid" class="easyui-combotree"  data-options="url:'${ctx}/deptcon/findBypartent',lines: true" style="width:185px"/></td>
                    </tr>
					<tr>
                        <td>排序：</td>
                        <td><input id="deptdsortNum" name="dsortnum" class="easyui-numberspinner"  data-options="min:0,max:100,editable:false" style="width:185px"/></td>
					</tr>
					<tr>
                        <td>备注：</td>
                        <td><textarea id="deptbak" name="bak" cols="30" rows="3"/></textarea>
                    </tr>
				  </select>                    
                </table>
        </form>
    </div> 
  </body>
</html>

