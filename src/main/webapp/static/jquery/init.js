var onlyOpenTitle = "欢迎使用";
var opentabs = 8; //允许打开的TAB数量
var menus;
$(function () {
	$.post("/spring-mvc/navigation/initNav",'',function(jsondata){
			menus=jsondata;
		    initNav();
		    //tabClose();
		},'json');

    //Tab右键菜单
    $('#closeMenu').menu({
        onClick: function (item) {
            closeTab(item.id);
        }
    });

});
function initNav() {
	AccordionTree.init();
	 $('#tabs').tabs({
	        tools: [{
	            iconCls: 'icon-reload',
	            handler: function() {
	                var tab = $('#tabs').tabs('getSelected');
	                //console.info(tab);
	                if (tab.panel('options').title != onlyOpenTitle)
	                    closeTab('refresh');//总的刷新按钮方法
	                return false;
	            }
	        }, {
	            iconCls: 'icon-cancel',
	            handler: function() {
	                if (confirm('确认要关闭所有窗口吗？')) {
	                	               		
	                		closeTab("closeall");
	                }
	            }
	        }],
	        onContextMenu: function(e, title) {
	            e.preventDefault();
	            $('#closeMenu').menu('show', {
	                left: e.pageX,
	                top: e.pageY
	            });
	            $('#tabs').tabs('select', title);
	        }
	    });
}
//手风琴 +tree
var AccordionTree = {
    init:function () {
		 $("#wnav").accordion({
	         fit: true, border: false, onAdd: function (title, index) {
			     if(index>0){
	             $('#nt' + (index-1)).tree({
	                 lines: false,
	                 animate: true,
	                 data: menus[index-1].children,
	                 onClick: function(node) {
	                     if (node.attributes.url != "" && node.attributes.url != '#') {
	                         addTab(node.text, node.attributes.url + '?navid=' + node.id, node.iconCls);
	                     } else {
	                         $('#nt' + (index-1)).tree('toggle', node.target);
	                     }
	                 }
	             });
			     
	         }
		 }
	     });
	     
	     $.each(menus, function (i, n) {
	         $('#wnav').accordion('add', {
	             title: n.text,
	             content: '<div style="padding-top:5px;"><ul id="nt' + i + '">'+i+'</ul></div>',
	             border: false,
	             iconCls: n.iconCls,
	             selected:true
	         });
	     });
	     $('#wnav').accordion('add', {title:'arui'});
	     $('#wnav').accordion('remove','arui');
    }
};
function addTab(subtitle, url, icon) {
    if (url == "" || url == "#")
        return false;
    var tabCount = $('#tabs').tabs('tabs').length;
    var hasTab = $('#tabs').tabs('exists', subtitle);
    var add = function () {
        if (!hasTab) {
            $('#tabs').tabs('add', {
                title: subtitle,
                content: createFrame(url),
                closable: true,
                icon: icon
            });
        } else {
            $('#tabs').tabs('select', subtitle);
            //closeTab('refresh'); //选择TAB时刷新页面
        }
    };
    if (tabCount > opentabs && !hasTab) {
        //var msg = '<b>您当前打开了太多的页面，如果继续打开，会造成程序运行缓慢，无法流畅操作！</b>';
       // $.messager.confirm("系统提示", msg, function (b) {
       //     if (b) add();
       //     else return false;
       // });
    	  add();
    } else {
        add();
    }
}

function createFrame(url) {
    var s = '<iframe scrolling="auto" frameborder="0"  style="width:100%;height:100%;" src="' + url + '" ></iframe>';
    return s;
}

function tabClose() {
    /*双击关闭TAB选项卡*/
    $(".tabs-inner").live('dblclick', function () {
        var subtitle = $(this).children(".tabs-closable").text();
        if (subtitle != onlyOpenTitle && subtitle != "")
            $('#tabs').tabs('close', subtitle);
    });
}

function closeTab(action) {
    var alltabs = $('#tabs').tabs('tabs');
    var currentTab = $('#tabs').tabs('getSelected');    
    //console.info(currentTab);
    var allTabtitle = [];
    $.each(alltabs, function (i, n) {
        allTabtitle.push($(n).panel('options').title);
    });
    switch (action) {
        case "refresh"://刷新
            var iframe = $(currentTab.panel('options').content);
           // console.info($(currentTab.panel('options').content));
            var src = iframe.attr('src');            
          // console.info("src:"+src);
            if(src != undefined){
            	
            $('#tabs').tabs('update', {
                tab: currentTab,
                options: {
                    content: createFrame(src)
                }
            });
            }
            break;
        case "close"://关闭
            var currtab_title = currentTab.panel('options').title;
            if (currtab_title != onlyOpenTitle) {
            $('#tabs').tabs('close', currtab_title);
            }else{
       		 alert('不允许关闭!');
             return false;
    	}
            break;
        case "closeall"://关闭所有，除了欢迎页面
            $.each(allTabtitle, function (i, n) {
            	//console.info("allTabtitle:"+n);
            	if(n!=onlyOpenTitle){//禁止关闭欢迎页面
            		$('#tabs').tabs('close', n);  
            	}
                              
            });
            break;
        case "closeother"://除此之外全部关闭
            var currtab_title = currentTab.panel('options').title;
            $.each(allTabtitle, function (i, n) {
                if (n != currtab_title && n != onlyOpenTitle) {
                    $('#tabs').tabs('close', n);
                }
            });
            break;
        case "closeright"://关闭右侧标签
            var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);

            if (tabIndex == alltabs.length - 1) {
                alert('菜单右侧没有可关闭的内容');
                return false;
            }
            $.each(allTabtitle, function (i, n) {
                if (i > tabIndex) {
                    if (n != onlyOpenTitle) {
                        $('#tabs').tabs('close', n);
                    }
                }
            });

            break;
        case "closeleft"://关闭左侧标签
            var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);
            if (tabIndex == 1) {
                alert('菜单左侧没有可关闭的内容');
                return false;
            }
            $.each(allTabtitle, function (i, n) {
                if (i < tabIndex) {
                    if (n != onlyOpenTitle) {
                        $('#tabs').tabs('close', n);
                    }
                }
            });

            break;
        case "exit":
            $('#closeMenu').menu('hide');
            break;
    }
}