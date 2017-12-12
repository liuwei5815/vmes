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
	$("#save").hide();
	winScrollContent("choose");
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
function addproduct(){
	$("#save").show();
	$("#add").hide();
}
function saveProduct(){
	var value=$("#cp").val();
	$.ajax({
	     type: "POST",
	     url: " ${ctx}/admin/product!addUnitQu.action",
	     cache: false,
	     dataType:"json",
	     data:{"value":value},
	     success: function(data){
	    	 if(data.successflag=="1"){
	     			top.Dialog.alert("保存成功",function(){
	     				location.href="${ctx}/admin/orders!queryUint.action";
	    	    	});
		     	}
	     		else{
	     			top.Dialog.alert("保存失败"); 
		     }
	     }
	});	
}
</script>
</head>
<body>
	<input type="checkbox"  id="templateCheckBox" style="display: none"  name="pkValue" value=""/>
	<input type="hidden" id="selectedNodeId" value=""/>
	<input type="hidden" id="selectedNodeName" value=""/>
	<div style="display: none;" id="callBak">
	</div>
	<button id="add" onclick="addproduct()" type="button"><span class="icon_add">新增</span></button>
	<span id="save">
		单位名称：<input class="textinput" id="cp" type="text"/>
		<button onclick="saveProduct()" type="button"><span class="icon_save">保存</span></button>
	</span>
	<!-- <div id="save">
		单位名称：
		<input class="input text" type="text"/>
		<span  onclick='return saveNum()'  class="icon_save">保存</span>
	</div> -->
    <div id="tree" style="display:none">${tree}</div>
    <div id="jstree_div"></div>
</body>
</html>





