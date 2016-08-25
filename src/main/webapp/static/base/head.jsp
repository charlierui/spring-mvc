
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/static/jquery/jquery-1.8.3.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx}/static/easyui1.4.5/jquery.easyui.min.js"></script>  
 <link rel="stylesheet" type="text/css" href="${ctx}/static/easyui1.4.5/themes/default/easyui.css">  
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui1.4.5/themes/icon.css">  
<script type="text/javascript" src="${ctx}/static/easyui1.4.5/locale/easyui-lang-zh_CN.js"></script>  



<script type="text/javascript" charset="utf-8" src="${ctx}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/ueditor/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/ueditor/lang/zh-cn/zh-cn.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/ueditor/ueditor.parse.min.js"></script>
<LINK rel="stylesheet" href="${ctx}/static/ueditor/themes/default/css/ueditor.css"> 

<link rel="stylesheet" href="${ctx}/static/css/default.css" type="text/css" />
<script type="text/javascript" src="${ctx}/static/jquery/formatDate.js" charset="UTF-8"></script>

<script type="text/javascript" src="${ctx}/static/uploadify/jquery.uploadify.js" charset="UTF-8"></script>
<LINK rel="stylesheet" href="${ctx}/static/uploadify/uploadify.css"> 

<script type="text/javascript" charset="UTF-8">
//屏蔽右键
//document.oncontextmenu=function(e){return false;}
//屏蔽状态栏
//window.status='欢迎使用！';

//ValidateBox 验证扩展
$.extend($.fn.validatebox.defaults.rules, { 
	
//判断两个值相等  
    equals: {   
        validator: function(value,param){   
            return value == $(param[0]).val();   
        },   
        message: '前后密码不一致'
    },   

//判断最小长度  
    minLength: {   
        validator: function(value, param){   
            return value.length >= param[0];   
        },   
        message: '请输入至少 {0} 个字符'  
    }, 
    
//判断最大长度  
    maxLength: {   
        validator: function(value, param){   
            return value.length <= param[0];   
        },   
        message: '输入最多 {0} 个字符'  
    },   

//正则表达式 判断汉字   
    chinese: {
        validator: function(value){ 
        	var reg=/^[\u4e00-\u9faf]+$/; 
            return reg.test(value);   
        },   
        message: '请输入汉字'  
    },  
//正则表达式判断文件类型xls
	xls:{
		validator: function(value){ 
        	var reg=/\.xls$/; 
            return reg.test(value);   
        },   
        message: '请选择后缀名 xls 的文件'
	},
	//正则表达式判断文件类型xml
	xml:{
		validator: function(value){ 
        	var reg=/\.xml$/; 
            return reg.test(value);   
        },   
        message: '请选择后缀名 xml 的文件'
	},
	//正则表达式判断进程
	exe:{
		validator: function(value){ 
        	var reg=/\.exe$/; 
            return reg.test(value);   
        },   
        message: '请输入正确的进程'
	},
	english : {// 验证英语
        validator : function(value) {
		//var reg=/[a-zA-Z\u4E00-\u9FA5]+$/; 
        //return reg.test(value);  
           return /^[A-Za-z]+$/i.test(value);
        },
        message : '请输入字母'
    },
    ip : {// 验证IP地址
        validator : function(value) {
            return /\d+\.\d+\.\d+\.\d+/.test(value);
        },
        message : 'IP地址格式不正确'
    },
    mac : {// 验证Mac地址
        validator : function(value) {
            return /[A-Fa-f0-9]{2}-[A-Fa-f0-9]{2}-[A-Fa-f0-9]{2}-[A-Fa-f0-9]{2}-[A-Fa-f0-9]{2}-[A-Fa-f0-9]{2}/.test(value);
        },
        message : 'Mac地址格式不正确'
    },
    mobile: {
        validator: function (value, param) {
        return /^(?:13\d|15\d|18\d)-?\d{5}(\d{3}|\*{3})$/.test(value);
      },
      message: '手机号码不正确'
    },
    tel:{
      validator:function(value,param){
        return /^(\d{3}-|\d{4}-)?(\d{8}|\d{7})?(-\d{1,6})?$/.test(value);
      },
      message:'电话号码不正确'
    },
    mobileAndTel: {
      validator: function (value, param) {
        return /(^([0\+]\d{2,3})\d{3,4}\-\d{3,8}$)|(^([0\+]\d{2,3})\d{3,4}\d{3,8}$)|(^([0\+]\d{2,3}){0,1}13\d{9}$)|(^\d{3,4}\d{3,8}$)|(^\d{3,4}\-\d{3,8}$)/.test(value);
      },
      message: '请正确输入电话号码'
    },//数字验证
    number: {
      validator: function (value, param) {
        return /^[0-9]+.?[0-9]*$/.test(value);
      },
      message: '请输入数字'
    },//金额验证
    money:{
      validator: function (value, param) {
       	return (/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(value);
       },
       message:'请输入正确的金额'

    },
    mone:{
      validator: function (value, param) {
       	return (/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(value);
       },
       message:'请输入整数或小数'

    },
    verston:{//软件版本(目前不好使)
      validator: function (value, param) {
       	return (/^(\d\.){5}$/).test(value);
       },
       message:'请输入整数或小数'

    },
    integer:{
      validator:function(value,param){
        return /^[+]?[1-9]\d*$/.test(value);
      },
      message: '请输入最小为1的整数'
    },
    integ:{
      validator:function(value,param){
        return /^[+]?[0-9]\d*$/.test(value);
      },
      message: '请输入整数'
    },
    range:{
        validator:function(value,param){
        if(/^[1-9]\d*$/.test(value)){
          return value >= param[0] && value <= param[1]
        }else{
          return false;
        }
      },
      message:'输入的数字在{0}到{1}之间'
    },
  //select即选择框的验证
    selectValid:{
      validator:function(value,param){
        //console.info("value:"+value);
        //console.info("param:"+param[0]);
        if(value == '--请选择--'){
          return false;
        }else{
          return true;
        }
      },
      message:'请选择'
    },
    selectCurrentUser:{
      validator:function(value,param){
        //console.info("value:"+value);
        //console.info("param:"+param[0]);
       // console.info("value:"+value);
       // console.info("param:"+param[0]);
        if(value == ""){
          return false;
        }else{
          return true;
        }
      },
      message:'请选择'
    },
    selectValueRequired: {  
    	validator: function(value,param){  
    	//console.info($(param[0]).find("option:contains('"+value+"')").val()); 
    	return $(param[0]).find("option:contains('"+value+"')").val() != '';  
    	},  
    	message: '请选择'
    } ,
    idCode:{
      validator:function(value,param){
        return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value);
      },
      message: '请输入正确的身份证号'
    },
    loginName: {
      validator: function (value, param) {
        return /^[\u0391-\uFFE5\w]+$/.test(value);
      },
      message: '登录名称只允许汉字、英文字母、数字及下划线。'
    },
    englishOrNum : {// 只能输入英文和数字
        validator : function(value) {
            return /^[a-zA-Z0-9_ ]{1,}$/.test(value);
        },
        message : '请输入英文、数字、下划线或者空格'
    },
    englishOrNumOrDian : {// 只能输入英文和数字.
        validator : function(value) {
            return /^[a-zA-Z0-9. ]{1,}$/.test(value);
        },
        message : '请输入英文、数字、.'
    },
   xiaoshu:{ 
      validator : function(value){ 
      return /^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$/.test(value);
      }, 
      message : '最多保留两位小数！'    
  	},
  ddPrice:{
  validator:function(value,param){
    if(/^[1-9]\d*$/.test(value)){
      return value >= param[0] && value <= param[1];
    }else{
      return false;
    }
  },
  message:'请输入1到100之间正整数'
},
jretailUpperLimit:{
  validator:function(value,param){
    if(/^[0-9]+([.]{1}[0-9]{1,2})?$/.test(value)){
      return parseFloat(value) > parseFloat(param[0]) && parseFloat(value) <= parseFloat(param[1]);
    }else{
      return false;
    }
  },
  message:'请输入0到100之间的最多两位小数的数字'
},
rateCheck:{
  validator:function(value,param){
    if(/^[0-9]+([.]{1}[0-9]{1,2})?$/.test(value)){
      return parseFloat(value) > parseFloat(param[0]) && parseFloat(value) <= parseFloat(param[1]);
    }else{
      return false;
    }
  },
  message:'请输入0到1000之间的最多两位小数的数字'
},
TimeCheck:{
	validator: function(value, param){
	startTime2 = $(param[0]).datebox('getValue');
	//console.info("startTime2:"+startTime2);
	//console.info("value:"+value);
	var d1 = $.fn.datebox.defaults.parser(startTime2);
	var d2 = $.fn.datebox.defaults.parser(value);
	
	varify = d2 >= d1;
	return varify; 
},
    message:'结束日期要大于或者等于开始日期！'
   },/**
   * @function 将时间戳转化为日+小时+分+秒
   * @param {Date} 时间戳
   * @return {String} 时间字符串
   */
   formatTime: function(longTime) {
   	//转化为 日+小时+分+秒
   	var time = parseFloat(longTime);
   	if (time != null && time != ""){
   		if (time < 60) {
   			var s = time;
   			time = s + '秒';
   		} else if (time > 60 && time < 3600) {
   			var m = parseInt(time / 60);
   			var s = parseInt(time % 60);
   			time = m + "分钟" + s + "秒";
   		} else if (time >= 3600 && time < 86400) {
   			var h = parseInt(time / 3600);
   			var m = parseInt(time % 3600 / 60);
   			var s = parseInt(time % 3600 % 60 % 60);
   			time = h + "小时" + m + "分钟" + s + "秒";
   		} else if (time >= 86400) {
   			var d = parseInt(time / 86400);
   			var h = parseInt(time % 86400 / 3600);
   			var m = parseInt(time % 86400 % 3600 / 60)
   			var s = parseInt(time % 86400 % 3600 % 60 % 60);
   			time = d + '天' + h + "小时" + m + "分钟" + s + "秒";
   		}
   	}	
   	return time;	
   }
});
//ValidateBox方法扩展，移除验证、还原验证
$.extend($.fn.validatebox.methods, { 
    remove: function(jq, newposition){ 
        return jq.each(function(){ 
            $(this).removeClass("validatebox-text validatebox-invalid").unbind('focus.validatebox').unbind('blur.validatebox');
        }); 
    },
    reduce: function(jq, newposition){ 
        return jq.each(function(){ 
           var opt = $(this).data().validatebox.options;
           $(this).addClass("validatebox-text").validatebox(opt);
        }); 
    }  
});
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
//文本域输入汉字数量验证
function jiance(textID,tisid){
		var len = $(textID).val().length;
	   if(len > 1000){
	    $(textID).val($(textID).val().substring(0,1000));
	   }
	   var num = 1000 - len;
	   if(num<=0){
		$(tisid).text(0);
	   }else{
	   	$(tisid).text(num);
	   }
	 }
function formatNyr(val,row){
        if(val!=null){
			return val.substr(0, 10);
		}else{
			return '';
		}
}
function formatSfm(val,row){
	if(val!=null){
		var b=null;
		var a=val.indexOf('T');
		//alert(a);
		if(a==-1){
		b=val.substring(0,10);
		//console.info(b);
		}else{
		 b=val.substring(0,a);
		//console.info(b);
		}
		return b;
	}else{
		//console.info('c');
		return '';
	}	
}
function formatText(val,row) {

	if(val!=null){		
		 return  "<a href='#' title="+"'"+val+"'>"+val.substr(0,10)+"</a>"
	}else{
		 return  val;    
	}	
       
}

function getLodop(oOBJECT,oEMBED){
	/**************************
	  本函数根据浏览器类型决定采用哪个对象作为控件实例：
	  IE系列、IE内核系列的浏览器采用oOBJECT，
	  其它浏览器(Firefox系列、Chrome系列、Opera系列、Safari系列等)采用oEMBED。
	**************************/
	        var strHtml1="<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='${ctx}/js/install_lodop.exe'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
	        var strHtml2="<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='${ctx}/js/install_lodop.exe'>执行升级</a>,升级后请重新进入。</font>";
	        var strHtml3="<br><br><font color='#FF00FF'>(注：如曾安装过Lodop旧版附件npActiveXPLugin,请在【工具】->【附加组件】->【扩展】中先卸载它)</font>";
	        var LODOP=oEMBED;		
		try{		     
		     if (navigator.appVersion.indexOf("MSIE")>=0) LODOP=oOBJECT;

		     if ((LODOP==null)||(typeof(LODOP.VERSION)=="undefined")) {
			 if (navigator.userAgent.indexOf('Firefox')>=0)
	  	         document.documentElement.innerHTML=strHtml3+document.documentElement.innerHTML;
			 if (navigator.appVersion.indexOf("MSIE")>=0) document.write(strHtml1); else
			 document.documentElement.innerHTML=strHtml1+document.documentElement.innerHTML;
		     } else if (LODOP.VERSION<"6.0.1.0") {
			 if (navigator.appVersion.indexOf("MSIE")>=0) document.write(strHtml2); else
			 document.documentElement.innerHTML=strHtml2+document.documentElement.innerHTML; 
		     }
		     //*****如下空白位置适合调用统一功能:*********	     


		     //*******************************************
		     return LODOP; 
		}catch(err){
		     document.documentElement.innerHTML="Error:"+strHtml1+document.documentElement.innerHTML;
		     return LODOP; 
		}
	}
$(function(){
	
})
</script>

