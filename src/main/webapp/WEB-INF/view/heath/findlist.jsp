<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/static/base/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
    <%@ include file="/static/base/head.jsp"%>

<script type="text/javascript">  
  $(function (){

    $('#userInfo').datagrid({  
        title: "参数项列表",  
        url: 'http://localhost:8090/spring/heath/findlistfilter',  
        pageSize:10,  
        rownumbers:true,  
        loadMsg : "数据正在路上",  
        pagination : true,  
        multiSort : true,  
        striped : true,  
        collapsible : true,  
        singleSelect : true,  
        idField : "", // 支持跨页选择 
        pageSize : 10,
        pageList : [10,20,30,50,100],
        columns: [  
            [  
                {field: 'htitle', title: '参数ID', width: 180, align: "center"},  
                {field: 'haddtime', title: '参数名称', width: 180, align: "center"}
                
            ]  
        ], toolbar: [  
            {  
                text: '添加',  
                iconCls: 'icon-add',  
                handler: function () {  
                    openDialog("add_dialog", "add");  
                }  
            },  
            '-',  
            {  
                text: '修改',  
                iconCls: 'icon-edit',  
                handler: function () {  
                    openDialog("add_dialog", "edit");  
                }  
            },  
            '-',  
            {  
                text: '删除',  
                iconCls: 'icon-remove',  
                handler: function () {  
                    delAppInfo();  
                }  
            }  
        ]  
    });  
    $('#userInfo').datagrid("getPager").pagination({
    	pageSize : 10,
        displayMsg : '当前数据范围 {from} 到 {to} ，共 {total} 条记录',  
        beforePageText : '当前',  
        afterPageText : '  ,共 {pages}页'  
    });  
    
  })
  //重置DataGrid 第一页
function resetDG(dg){
 	$(dg).datagrid('options').pageNumber = 1;
    	var p = $(dg).datagrid('getPager');
    	if (p){
			$(p).pagination({
				pageNumber:1
			});
    	}
    	$(dg).datagrid('reload');
}
  //条件查询
   	function doQuery(){
	  
   	    $('#userInfo').datagrid('clearSelections');
   	    console.info($('#htitleid').val());
   	    var queryParams=$('#userInfo').datagrid('options').queryParams;
   	    queryParams.htitle=$('#htitleid').val();
	  	//queryParams.softwaveNameSch=$('#asoftwaveNameSch').val();
  	    //重置当前页数为1
  	    resetDG('#userInfo'); 
   	 //$('#userInfo').datagrid('reload');
   	}
    //清空
    function doClear(){
       $('#htitleid').val("");
	   doQuery();
    };
</script>  

</head>
<body>
  
  <div style="padding:8px;height:auto">  
    参数项名称: <input class="easyui-validatebox" type="text" id="htitleid" name="htitle" data-options="required:true">  
    创建时间: <input class="easyui-datebox" name="createTime" style="width:80px">  
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
			onclick="doQuery()">查询</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="doClear()" data-options="iconCls:'icon-clear'">添加</a>  
</div>  
<table width="98%">  
    <tr>  
        <td>  
            <table id="userInfo" title="">  
            </table>    
        </td>  
    </tr>  
</table>  

</body>
</html>