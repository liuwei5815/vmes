<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.xy.cms.common.CacheFun"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>智造云管家</title>
<script type="text/javascript" src="${ctx}/admin/frame/js/jquery-1.4.js"></script>
<script type="text/javascript" src="${ctx}/admin/frame/js/login.js"></script>
<!--框架必需start-->
<link href="${ctx}/admin/css/style.css?v=2.0" rel="stylesheet" type="text/css"/>
<link href="${ctx}/admin/frame/css/import_basic.css" rel="stylesheet"
	type="text/css" prePath="${ctx}/admin/frame/" />
<link
	href="${ctx}/admin/frame/skins/<%=CacheFun.getConfig("frame_skinname")%>/import_skin.css"
	rel="stylesheet" type="text/css" id="skin"
	themeColor="<%=CacheFun.getConfig("frame_themecolor")%>"
	prePath="${ctx}/admin/frame/" />

<script type="text/javascript" src="${ctx}/admin/frame/js/bsFormat.js"></script>
<!--框架必需end-->

<!--引入弹窗组件start-->
<script type="text/javascript" src="${ctx}/admin/frame/js/attention/zDialog/zDrag.js"></script>
<script type="text/javascript" src="${ctx}/admin/frame/js/attention/zDialog/zDialog.js"></script>
<!--引入弹窗组件end-->

<!--修正IE6支持透明png图片start-->
<script type="text/javascript" src="${ctx}/admin/frame/js/method/pngFix/supersleight.js"></script>
<!--修正IE6支持透明png图片end-->

<!--居中显示start-->
<script type="text/javascript" src="${ctx}/admin/frame/js/method/center-plugin.js"></script>
<script>
	$(function(){
		 $('.login_main').center();
	})
</script>
<!--居中显示end-->
<style type="text/css">
/*提示信息*/	
#cursorMessageDiv {
	position: absolute;
	z-index: 99999;
	border: solid 1px #cc9933;
	background: #ffffcc;
	padding: 2px;
	margin: 0px;
	display: none;
	line-height:150%;
}
/*提示信息*/
body {
	background-color: #264A84;
}

input:-webkit-autofill {
	-webkit-box-shadow: 0 0 0px 1000px white inset;
}
</style>
</head>
<body>
	<!-- 取消自动填充表单功能，解决chrome上的黄色输入框问题 -->
	<form id="frm" autocomplete="off" action="${ctx}/admin/login!doLogin.action" method="post">
	<s:hidden name="message" id="message"/>
	<div class="bg_img">
		<div class="position_box">
             <h1 class="title">智造云管家</h1>
             <div class="login_info" id="message_div" display="none"><s:property value="message"/> </div>
             <div class="img_box"></div>
             <div class="srk_center">
                  <div style="margin-bottom:30px; padding-top:20px;">
                            <span style=" font-size:20px; color:#04c38e; font-weight:500;">用户登录</span >
                            <span style="font-size:14px; color:#999; margin-left:3px;">UserLogin</span></div>
                        <div class="srk_box">
                            <input class="login_user" type="text" type="text" name="admin.account" id="name"/>
                        </div>
                        <div class="srk_box">
                            <input class="login_password" type="text" onfocus="this.type='password'" name="admin.pwd" id="password"/>
                        </div>
                        <div class="login_btn_box">
                            <a class="login_btn" onclick="doSubmit()">登录</a>
                            <a class="a_hover" style="color:#333;"><input type="checkbox" name="rememberPassWord">记住密码</input></a>
                            <a class="hover_a" style="color:#333;" onclick="forgetPwd()">忘记密码？</a>
                            <div class="clear"></div>
                        </div>
              </div> 
              <div class="clear"></div> 
		</div>			
	</div>
	<div style=" position:fixed; bottom:0px;  font-size:14px; color:#fff; width:100%; text-align:center; line-height:60px; color:#bfbfbf;">版权所有 © 顶智智能技术有限公司</div>
	</form>
<script>
if(window.location != top.location){
	top.location = window.location;
}

$(function(){
	$('.login_main').center();
	$(document).keydown(function(event) {
		if (event.keyCode == 13) {
			$(".login_btn").click();
		}
	})
});

$(document).ready(function(){ 
	top.Dialog.close();
	loadMsg();
});

function loadMsg(){
	var message_div = document.getElementById('message_div');
	var message = document.getElementById('message').value;
	if(message !=""){
		message_div.style.display = "";
		message.value="";
    }else{
    	message_div.style.display = "none";
	}
}


function doSubmit() {
	$("#frm").submit();
}

function forgetPwd() {
	top.Dialog.alert("请联系管理员重置密码！");
}
</script>
</body>
</html>
