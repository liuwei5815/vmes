<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/admin/jsp/common/common.jsp"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.io.StringWriter"%>
<%@page import="java.io.PrintWriter"%><html xmlns='http://www.w3.org/1999/xhtml'>
<head>

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
			<a href="http://61.183.84.50:8900/xy_cms" class="btn btn-primary m-t">返回首页</a>
   		</div>
    </div>

    <!-- Mainly scripts -->
    <script src="${ctx}/user/js/jquery-2.1.1.min.js"></script>
    <script src="${ctx}/user/js/bootstrap.min.js?v=3.4.0"></script>
   

</body>

</html>
