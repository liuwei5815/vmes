<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="application/javascript" src="${ctxAdmin}/js/jstree/jstree.js?v=4.0"></script>
<link rel="stylesheet" href="${ctxAdmin}/js/jstree/themes/default/style.min.css" />
<script type="application/javascript" src="${ctxAdmin}/js/jstree/overmenu/jstree.plugin.overmenu.min.js"></script>
<link rel="stylesheet" href="${ctxAdmin}/js/jstree/overmenu/overmenu.min.css" />
<title>数据表</title>
<script>
$(function() {
	$('#jstree_div').jstree({
		"core" : {
			"data" : JSON.parse($("#tree").html()),
			"themes" : {
				"variant" : "large",// 加大
				"ellipsis" : true
			},
			"check_callback" : true
		},
		  	"plugins" : [ "wholerow","themes"],  
		}).on("select_node.jstree",function(event,data){
			var id=data.node.id;
			$("#selectedNodeId").val(id);
			$("#selectedNodeName").val(data.node.text);
		}) 
}) 
function getTreeNode(){
	var tree=$('#jstree_div').jstree();
	return tree.get_node(tree.get_selected());
}
</script>
</head>
<body>
	<input type="checkbox"  id="templateCheckBox" style="display: none"  name="pkValue" value=""/>
	<input type="hidden" id="selectedNodeId" value=""/>
	<input type="hidden" id="selectedNodeName" value=""/>
	<div style="display: none;" id="callBak">
	</div>
    <div id="tree" style="display:none">${tree}</div>
    <div id="jstree_div"></div>
</body>
</html>





