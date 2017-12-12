<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ taglib uri="/tags/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request" />
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/admin/frame/js/jquery-1.4.js"></script>
<script type="text/javascript" src="${ctx}/admin/frame/js/framework.js"></script>
<link href="${ctx}/admin/frame/css/import_basic.css" rel="stylesheet" type="text/css" prePath="${ctx}/admin/frame/"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="${ctx}/admin/frame/"/>
<!--框架必需end-->

<!--让ie6支持透明png图片start-->
<script type="text/javascript" src="${ctx}/admin/frame/js/method/pngFix/supersleight.js"></script>
<!--让ie6支持透明png图片end-->
</head>
<body >

<div id="scrollContent">
	<div align="center">
		 <img src="${ctx}/admin/image/welcome.png" alt="" width="700px"/>
	</div>	
</div>
</body>
</html>