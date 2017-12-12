<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/tags/core" prefix="c"%> 
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request" /> 
<%@ page isELIgnored="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<html xmlns='http://www.w3.org/1999/xhtml'>
<head>
<!--frame start-->
<script type='text/javascript' src="${ctx}/admin/frame/js/jquery-1.4.js"></script>
<script type='text/javascript' src="${ctx}/admin/frame/js/framework.js"></script>
<link href="${ctx}/admin/frame/css/import_basic.css" rel='stylesheet' type='text/css' prePath="${ctx}/admin/frame/"/>
<link id='skin' type='text/css' rel='stylesheet' prePath="${ctx}/admin/frame/" />
<!--frame end-->
<base href="${ctx}/" />
<!--截取文字start-->
<script type="text/javascript" src="${ctx}/admin/frame/js/text/text-overflow.js"></script>
<!--截取文字end-->
<script src="${ctx}/admin/frame/js/form/validationEngine-cn.js" type="text/javascript"></script>
<script src="${ctx}/admin/frame/js/form/validationEngine.js" type="text/javascript"></script>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<title>404</title>
<link href="${ctx}/user/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
<link href="${ctx}/user/font-awesome/css/font-awesome.css?v=4.3.0" rel="stylesheet">
<link href="${ctx}/user/css/animate.css" rel="stylesheet">
<link href="${ctx}/user/css/style.css?v=2.2.0" rel="stylesheet">
</head>
<body class="gray-bg">
    <div class="middle-box text-center animated fadeInDown">
        <h1>404</h1>
        <h3 class="font-bold">页面未找到！</h3>
        <div class="error-desc">
           	 抱歉，页面好像去火星了~
        </div>
        <div class="content">
			<a href="javascript:history.go(-1);" class="btn btn-primary m-t">返回</a>
   		</div>
    </div>
    <!-- Mainly scripts -->
    <script src="${ctx}/user/js/jquery-2.1.1.min.js"></script>
    <script src="${ctx}/user/js/bootstrap.min.js?v=3.4.0"></script>
</body>
</html>
