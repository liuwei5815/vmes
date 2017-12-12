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
	$('#jstree_div').jstree({
		"core" : {
			"data" : JSON.parse($("#tree").html()),
			"themes" : {
				"variant" : "large",// 加大
				"ellipsis" : true
			},
				"check_callback" : true
			},
			"checkbox":{
				"three_state":false,
				
			},
		  	"plugins" : [ "themes","checkbox"],  
		}).on("select_node.jstree",function(event,obj){
			 var ref = $('#jstree_div').jstree(true);
             var nodes = ref.get_checked();  //使用get_checked方法
             putChecks(tree,nodes);
		}).on("deselect_node.jstree",function(event,obj){
			 var ref = $('#jstree_div').jstree(true);
             var nodes = ref.get_checked();  //使用get_checked方法
             putChecks(tree,nodes);
		});
		function putChecks(tree,nodes){
			$("#callBak").html("");
     		$.each(nodes,function(i,nd){
     			var name = $('#jstree_div').jstree("get_node", nd).text;
           		var checkBox = $("#templateCheckBox").clone();
           		checkBox.removeAttr("id");
           		checkBox.attr("name","pkValue");
           		checkBox.attr("checked","checked");
           		checkBox.attr("value",nd);
           		checkBox.attr("lable",name);
           		$("#callBak").append(checkBox); 
           	});
		}	
}) 
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
	<div style="display: none;" id="callBak">
	</div>
    <div id="tree" style="display:none">${tree}</div>
    <div id="jstree_div"></div>
</body>
</html>





