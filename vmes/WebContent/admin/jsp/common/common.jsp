<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@page import="com.xy.cms.common.CacheFun"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request" />
<c:set var="ctxAdmin" value="${ctx}/admin" scope="request" />
<s:set var="rfile" value="@com.xy.cms.common.SysConfig@getStrValue('resource.file')"></s:set>
<%@ page isELIgnored="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--frame start-->
<script type="application/javascript" src="${ctxAdmin}/frame/js/jquery-1.8.0.js?v=4.0"></script>
<script type='text/javascript' src="${ctx}/admin/frame/js/framework.js?v=24.0"></script>
<link href="${ctx}/admin/frame/css/import_basic.css" rel='stylesheet' type='text/css' prePath="${ctx}/admin/frame/"/>
<link id='skin' type='text/css' rel='stylesheet' prePath="${ctx}/admin/frame/" />

<!--frame end-->
<base href="${ctx}/" />
<!--截取文字start-->
<script type="text/javascript" src="${ctx}/admin/frame/js/text/text-overflow.js"></script>


<!--截取文字end-->
<script src="${ctx}/admin/frame/js/form/validationEngine-cn.js?v=6.3" type="text/javascript"></script>
<script src="${ctx}/admin/frame/js/form/validationEngine.js" type="text/javascript"></script>

<!-- 添加日期比较插件 -->
<script src="${ctx}/admin/js/moment.js" type="text/javascript"></script>

