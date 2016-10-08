<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/base/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${systemOption.systemTitle}</title>
<%@ include file="/static/base/head.jsp"%>
<script type="text/javascript" charset="UTF-8">
	$(function(){
        $("#tclx").change(function(){
        	var yunyings=$("#yunyings").val();
            var tclx = $("#tclx").val();
            $.ajax({
                type:"POST",
                url :"${ctx}/tdictypecon/listDictype",
                data:{                	
                	id:tclx
                },
                dataType:"json",
                success:function(data){
                    $("#tid").empty();
                    $("#tid").append("<option value=''>----请选择----</option>");
                    $.each(data,function(index,item){
                    	console.info("item:"+item.id);
                        $("#tid").append( "<option value='"+item.id+"'>"+item.text+"</option>");
                    });
                }
            });
        });
        
        //字典类别
        $("#tid").change(function(){        	
            var tid = $("#tid").val();            
            $.ajax({
                type:"POST",
                url :"${ctx}/tdictypecon/listDictypeAllld",
                data:{                	
                	tid:tid
                },
                dataType:"json",
                success:function(data){
                    $("#zdz").empty();
                    $("#zdz").append("<option value=''>----请选择----</option>");
                    console.info(data);
                    $.each(data,function(index,item){
                        $("#zdz").append( "<option value='"+item.id+"'>"+item.text+"</option>");
                    });
                }
            });
        });
        
    });
	
	
</script>
</head>

<body>
	<table border="1">
		<tr>
			<td>运营商</td>
			<td>
			<select id="yunyings" style="width: 100px;">
			<option value="1">联通</option>
			<option value="2">移动</option>
			</select>
			</td>
			<td>套餐类型</td>
			<td>
			<select id="tclx" style="width: 100px;">
			<option value="">请选择</option>
			<option value="7">套餐1</option>
			<option value="7">套餐2</option>
			</select>
			</td>
		</tr>
		<tr>
			<td>字典类别</td>
			<td>
			<select id="tid" style="width: 100px;"></select>	
			</td>
			<td>字典值</td>
			<td>
			<select id="zdz" style="width: 100px;"></select>	
			</td>
		</tr>
	</table>

</body>
</html>
