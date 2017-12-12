<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻信息列表</title>
<%@ include file="/admin/jsp/common/common.jsp"%>
<script type='text/javascript' src="${ctx }/admin/js/page.js"></script>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${ctxAdmin}/js/jquery-2.1.1.min.js"></script>

<script type="application/javascript" src="${ctxAdmin}/js/jstree/jstree.js?v=4.0"></script>
<link rel="stylesheet" href="${ctxAdmin}/js/jstree/themes/default/style.min.css" />
<script type="application/javascript" src="${ctxAdmin}/js/jstree/overmenu/jstree.plugin.overmenu.min.js"></script>
<link rel="stylesheet" href="${ctxAdmin}/js/jstree/overmenu/overmenu.min.css" />
<style>

</style>
<script>
$(function(){
	top.Dialog.alert($(document).height());
$('#jstree_div').jstree({
	"core" : {
		"data" : eval($("#tree").val()),
		"themes" : {
			"variant" : "large",// 加大
			"ellipsis" : true
		
		},
		"check_callback" : true
	},
	"plugins" : [ "wholerow", "themes" ],
	
}));

})
</script>
</head>
<body>

<div id="scrollContent" class="border_gray">
<input type="hidden" id="tree" value="${tree }" />
	<div id="jstree_div" ></div>
</div>
</body>
</html>