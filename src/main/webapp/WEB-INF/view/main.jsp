<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>${systemOption.systemTitle}</title>
    <link href="${ctx }/static/images/tax.ico" rel="shortcut icon" />
	<%@ include file="/static/base/head.jsp"%>
	<script type="text/javascript" src="${ctx }/static/jquery/init.js"></script>
	<style type="text/css">
        .container
        {
            /*font-family: "Helvetica Neue", Helvetica, Microsoft Yahei, Hiragino Sans GB, WenQuanYi Micro Hei, sans-serif;*/
            font-size: 12px;
        }

        .title p
        {
            margin-left: 15px;
        }

        .title span
        {
            font-weight: bold;
            color: #3E6294;
        }

        .title-panle
        {
            height: 30px;
            background-color: #E0ECFF;
        }

            .title-panle p
            {
                margin-left: 15px;
                line-height: 30px;
            }

            .title-panle span
            {
                font-weight: bold;
                color: #515151;
            }

        .content-panle
        {
            margin-left: 15px;
            margin-top: 10px;
        }

        .content-panle-item
        {
            display: inline-block;
            width: 200px;
            line-height: 30px;
            text-indent: 15px;
        }

            .content-panle-item .caption
            {
                font-weight: bold;
                color: #515151;
            }

            .content-panle-item .item-data
            {
                font-weight: bold;
                color: #3E6294;
            }


        .menu-item
        {
            width: 150px;
            min-height: 1px;
            box-sizing: border-box;
            padding-right: 15px;
            padding-left: 15px;
            display: inline-block;
        }

        .menu-thumbnail
        {
            display: block;
            line-height: 1.42857143;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 4px;
            -webkit-transition: border .2s ease-in-out;
            -o-transition: border .2s ease-in-out;
            transition: border .2s ease-in-out;
            text-align: center;
        }

            .menu-thumbnail .caption
            {
                padding: 9px;
                color: #333;
                text-align: center;
                padding: 2px;
            }

            .menu-thumbnail img
            {
                height: 120px;
                width: 110px;
                display: block;
                margin: 4px auto;
                border: none;
            }

        a.menu-thumbnail
        {
            text-decoration: none;
        }

            a.menu-thumbnail.active, a.menu-thumbnail:focus, a.menu-thumbnail:hover
            {
                border-color: #337ab7;
            }
    </style>
	
    <script type="text/javascript">    
        $(function() {
            $('#pwdBtn').click(function() {
            	$('#pwdWnd').show();
                $('#pwdWnd').dialog({
                	iconCls:'icon-save',
                	title:'密码修改',
                	modal:true,
                	buttons: [{
						text:'确定',
						iconCls:'icon-ok',
						handler:function(){
							$('#pwdForm').form('submit',{  
							    success: function(data){ 
							    	var res = eval('(' + data + ')');
							        if(res.statuscode=='1'){  
							            $.messager.alert('友情提示',res.msg,'info'); 
							            $('#user').datagrid('reload');
							        }else{
							        	$.messager.alert('友情提示',res.msg,'error'); 
							        	$('#user').datagrid('reload');
							        } 
							        $('#pwdWnd').dialog('close');
							    }  
							});
						}
					},{
						text:'取消',
						iconCls:'icon-cancel',
						handler:function(){
							$('#pwdWnd').dialog('close');
						}
				}]
                });
            });

            $('#logoutBtn').click(function() {
                $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
                    if (r) {
                        location.href = '${ctx}/logincon/logout';
                    }
                });
            })
        });
        
        //用于子Tabs页调用新建tab
        function addTabfromInner(subtitle, url, icon) {
            if (url == "" || url == "#")
                return false;
            var tabCount = $('#tabs').tabs('tabs').length;
            var hasTab = $('#tabs').tabs('exists', subtitle);
            var add = function () {
            	//若已打开，则关闭后用新的参数重新打开
                if (hasTab) {
                	$('#tabs').tabs('close', subtitle);
                }
                $('#tabs').tabs('add', {
                    title: subtitle,
                    content: '<iframe scrolling="auto" frameborder="0"  style="width:100%;height:100%;" src="' + url + '" ></iframe>',
                    closable: true,
                    icon: icon
                });
            };
            if (tabCount > opentabs && !hasTab) {
                //var msg = '<b>您当前打开了太多的页面，如果继续打开，会造成程序运行缓慢，无法流畅操作！</b>';
                //$.messager.confirm("系统提示", msg, function (b) {
                //    if (b) add();
                //    else return false;
               // });
            	   add();
            } else {
                add();
            }
        }
		$(function(){
			//浏览量统计
			$.ajax({
			   type: 'post',
			   dataType:'json',
			   async:true,
			   dataType:'json',
			   url:'${ctx}/indexcon/pvnvjs',
			   success: function(msg){
				  $("#addUnitCount").text(msg.pv);
				  $("#addPCCount").text(msg.nv);
			   }
			});
			
		});
    </script>

</head>
<body onselectstart="return false;" class="easyui-layout" style="overflow-y: hidden; "  fit="true"   scroll="no">
<!--判断浏览器-->
<noscript>
	<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
		<img src="static/images/noscript.gif" alt='抱歉，请开启脚本支持！' />
	</div>
</noscript>
<!--头部区域-->
	<div region="north" split="false" border="false" style="overflow: hidden; height: 66px;">
		<%@ include file="/static/base/top.jsp"%>
	</div>
<!--菜单区域-->	
	<div region="west" split="true" title="导航菜单" iconCls="icon-application-side-tree" style="width:180px;" id="west">
		<div id="wnav">
			<!--  手风琴导航内容 -->
		</div>
	</div>
<!--底部区域-->
    <div region="south" style="height:35px">
        <table class="main_foot" width="100%" cellpadding="2" cellspacing="0" border="0" >
		    <tr>
		        <td valign="middle">
		         版权所有：<a href="javascript:void(0)" target="_blank" style="color: #0D5798;">xxx</a>
		        </td>
		    </tr>
		</table>
    </div>
<!--主体区域-->
	<div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden" border="false">
		<div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			<div title="欢迎使用" style="padding:0px;overflow:hidden;" id="home">
				<!--  <div id="softWareSuitListAddC" class="easyui-layout" data-options="fit:true">
					<div data-options="region:'west',split:false,border:false," style="width:180px;">
		                    <div id="p" class="easyui-panel" title="软件类型"
							data-options="fit:true,iconCls:'icon-application-side-tree'">  
							    <ul id="softWareTypeTree" class="easyui-tree" data-options="
							                                        fit:true,
							                                        iconCls:'icon-book-red',
							                                        url:'${ctx}/json/dicValueList2.action'">
							                                        </ul> 
							</div> 
					</div>
					<div id="syqk" data-options="region:'center',title:'使用信息',border:false,fit:true" >
						<iframe id="rightFrame" title="rightFrame" name="rightFrame" style="border:0; width: 1230px;height: 600px;"
								src="${ctx}/system/getDeptBySoftTypeAndCompany.action"></iframe>
					</div>
				</div> -->
		<div class="title">
            <p>
                <span>欢迎访问 </span>
                <span> xxx管理平台</span>
            </p>
        </div>
	  <div class="title-panle">
            <p>
                <span>pv/uv统计</span>
            </p>
        </div> 
     <div class="content-panle">
            <div>
             
                <div class="content-panle-item">
                    <span class="caption">访问量：</span>
                    <span id="addUnitCount" class="item-data"></span>
                </div>
                <div class="content-panle-item">
                    <span class="caption">访客数：</span>
                    <span id="addPCCount" class="item-data"></span>
                </div>
                <!--  <div class="content-panle-item">
                    <span class="caption">已检查单位数：</span>
                    <span id="examUnitCount" class="item-data"></span>
                </div>-->
            </div> 
        </div>
        <!-- 
        <div class="title-panle">
            <p>
                <span>快速进入</span>
            </p>
        </div>
        <div class="content-panle">
            <div>
             
                <div class="menu-item" title="检查设置">
                    <a href="javascript: parent.addTabfromInner('检查设置', '${ctx}/system/djcrjqd.action', 'icon-leaf')" class="menu-thumbnail">
                        <img alt="检查设置" src="${ctx}/static/images/dcsz.jpg" />
                        <div class="caption">
                            <span>检查设置</span>
                        </div>
                    </a>
                </div>
                <div class="menu-item" title="正版化检查">
                    <a href="javascript: parent.addTabfromInner('正版化检查', '${ctx}/system/checkMessage.action', 'icon-leaf')" class="menu-thumbnail">
                        <img alt="正版化检查" src="${ctx}/static/images/zbhdc.jpg"/>
                        <div class="caption">
                            <span>正版化检查</span>
                        </div>
                    </a>
                </div>
                <div class="menu-item" title="正版化统计">
                    <a href="javascript: parent.addTabfromInner('正版化统计', '${ctx}/system/zbhtj.action', 'icon-leaf')" class="menu-thumbnail">
                        <img alt="正版化统计" src="${ctx}/static/images/tjfx.jpg" />
                        <div class="caption">
                            <span>正版化统计</span>
                        </div>
                    </a>
                </div>
                <div class="menu-item" title="系统设置">
                    <a href="javascript: parent.addTabfromInner('软件基本信息', '${ctx}/system/softWare.action', 'icon-leaf')" class="menu-thumbnail">
                        <img alt="系统设置" src="images/xtsz.jpg" />
                        <div class="caption">
                            <span>系统设置</span>
                        </div>
                    </a>
                </div>
            </div>
        </div>
        <div class="title-panle">
            <p>
                <span>系统信息</span>
            </p>
        </div>
		<div class="content-panle" >
			 <div>
                 <img src="" height="131px;" width="470px;" />
             </div>
            <div>
                <div class="content-panle-item" style="width: 500px;">
                    <span class="caption">版权所有：</span>
                    <span class="item-data">北京思图科技有限公司</span>
                </div>
            </div>
            <div>
                <div class="content-panle-item" style="width: 500px;">
                    <span class="caption">联系电话：</span>
                    <span class="item-data">010-88572939 / 010-51663395</span>
                </div>
            </div>
            <div>
                <div class="content-panle-item" style="width: 500px;">
                    <span class="caption">电子邮箱：</span>
                    <span class="item-data">situkeji@sina.com</span>
                </div>
            </div>
       </div>-->
    </div>
		
                  
        </div> 
			</div>
		</div>
	</div>
<!--修改密码窗口-->
    <div id="pwdWnd"  style="width: 280px; height: 180px; padding: 5px; background: #fafafa;display: none">
    	<form id="pwdForm" method="post" action="${ctx }/tusercon/updatePwd">
                <table cellpadding=3>
                	<tr>
                        <td>原密码：</td>
                        <td><input id="ymm" name="oldPwd" class="easyui-validatebox" type="Password" data-options="required:true" validType="remote['${ctx}/tusercon/checkPwd','oldPwd']" invalidMessage="原始密码错误,请重新输入"/></td>
                    </tr>
                    <tr>
                        <td>新密码：</td>
                        <td><input id="xmm" name="newPwd" class="easyui-validatebox" type="Password" data-options="required:true"/></td>
                    </tr>
                    <tr>
                        <td>确认密码：</td>
                        <td><input id="qrmm" class="easyui-validatebox" type="Password" data-options="required:true" validType="equals['#xmm']"/></td>
                    </tr>
                </table>
        </form>
    </div>
</body>
<script type="text/javascript">
    $(function(){
        $('#softWareTypeTree').tree({   
		    onClick: function(node){
                  //alert(node.id);
                  //window.location.href="${ctx}/system/getDeptBySoftTypeAndCompany.action?softWareType="+node.id;
                  //加载数据
					/*$.ajax({
					   type: 'post',
					   async:false,
					   url:'${ctx}/system/getDeptBySoftTypeAndCompany.action?softWareType='+node.id,
					   success: function(msg){
					     // $('#acode').val(msg);
                          $('#syqk').html(msg);
					   }
					});*/
					 document.getElementById("rightFrame").src='${ctx}/system/getDeptBySoftTypeAndCompany.action?softWareType='+node.id;
			}
   
		});  
    });
</script>
</html>