<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/tags/core" prefix="c"%> 
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request" /> 
<html xmlns='http://www.w3.org/1999/xhtml'>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<title>404</title>
<link href="${ctx}/admin/css/style.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="js/jquery-1.4.js"></script>

<!--引入弹窗组件start-->
<script type="text/javascript" src="js/attention/zDialog/zDrag.js"></script>
<script type="text/javascript" src="js/attention/zDialog/zDialog.js"></script>
<!--引入弹窗组件end-->

<!--修正IE6支持透明png图片start-->
<script type="text/javascript" src="js/method/pngFix/supersleight.js"></script>
<!--修正IE6支持透明png图片end-->

<!--居中显示start-->
<script type="text/javascript" src="js/method/center-plugin.js"></script>
<script>
	$(function(){
		 $('.login_main').center();
	})
</script>
<!--居中显示end-->
<style>
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
body {background-color: #fff;}
.error_box{ width:402px; margin:0 auto; margin-top:240px;}
.error_box .left_box{ float:left; margin-right:30px;}	
.error_box dl{ float:left; margin:0;}	
.error_box dl dd{font-size:28px; line-height:30px; color:#333; margin:0px; margin-bottom:20px;}	
.error_box dl dt{font-size:14px; line-height:14px; color:#333; margin-bottom:10px;}	
.error_box dl dt .botton{ display:inline-block; height:40px; line-height:40px; text-align:center; color:#fff; background-color:#04c38e; padding:0 40px; border-radius:4px; margin-right:10px; text-decoration:none;}	
.error_box dl dt .but_green_outline{ background-color:#fff; border:1px solid #04c38e; color:#333;}
.error_box dl dt .but_green:hover{background-color:#04b685;}
.error_box dl dt .but_green_outline:hover{background-color:#04c38e; color:#fff;}
	
</style>
</head>
<body>
<div class="error_box">
    <div class="left_box">
        <img src="image/error/error_img.png" />
    </div>
    <dl>
        <dd>发生了未知异常</dd>	
        <dt style="color:#666;">不如返回主页看看，或者刷新页面试试吧</dt>
        <dt>
            <a class="botton but_green" onclick="backToHome();">返回主页</a>
            <a class="botton but_green_outline" onclick="refresh();">刷新</a>
        </dt>
    </dl>
    <div class="clear"></div>
</div>	
</body>
<script>
function backToHome() {
	window.parent.location.reload();
}

function refresh() {
	window.location.reload();
}
</script>
</html>
