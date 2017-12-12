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
<title>行政区划管理</title>
<script>
function doQuery() {
	
}
function openAddWin(){
	var diag = new top.Dialog();
	diag.Title = "新增行政区划";
	diag.URL = "area!preAddRegion.action";
	diag.Height=200;
	diag.Width=650;
	diag.show();
	return false;
}
$(function(){
	var ctxmenus = function(o, cb) {
		var items = {
			 "edit" : {
				"separator_before" : false,
				"separator_after" : false,
				"label" : "修改",
				"action" : function(data) {
					edit(o.id);
				}
			}, 
			"remove" : {
				"separator_before" : true,
				"separator_after" : false,
				"label" : "删除",
				"action" : function(data) {
					del(o.id);
				}
			}
		};
		return items;
	};
	$('#jstree_div').jstree({
		"core" : {
			"data" : eval($("#tree").val()),
			"themes" : {
				"variant" : "large",// 加大
				"ellipsis" : true
			
			},
			"check_callback" : true
		},
		"plugins" : [ "wholerow", "themes","overmenu"],
		"contextmenu" : {
			"items" : ctxmenus
		}
		
	}).on('select_node.jstree', function(event, data) {
		$("#unitId").val(data.node.id);
/* 		$("#unitNam").text(data.node.text); */
		
	});
	function edit(id){
		var diag = new top.Dialog();
		diag.Title = "修改行政区划类型";
		diag.URL = "area!preEdit.action?id="+id;
		diag.Height=400;
		diag.Width=650;
		diag.show();
		return false;
	}
	function del(id){
		top.Dialog.confirm("确定要删除这个行政区划类吗？",function(){
			$.ajax({
			     type: "POST",
			     url: "${ctx}/admin/area!delete.action",
			     cache: false,
			     dataType:"json",
			     data:{"id":id},
			     success: function(data){
		     		if(data.successflag=="1"){
		     			$("#frm").submit();
		     			//刷新页面
		     			top.Dialog.alert("删除成功")
		     		}
		     		else{
		     			top.Dialog.alert("删除失败");}
			     }
			});	
		});
	}
})
</script>
</head>
<body>
<div class="position">
<div class="center">
<div class="left">
<div class="right"><span>当前位置：数据字典管理 >>行政区划管理 </span></div>
</div>
</div>
</div>
<div class="box2" panelTitle="行政区划管理" roller="false">
<form id="frm" method="post"  action="">
<input type="hidden" id="unitId" />	
<table>
	<tr>
		<td>
			<button onclick='return openAddWin();' type="button">
				<span class="icon_add">新增</span>
			</button>
		</td>
	</tr>
</table>
</form>
</div>

<div id="scrollContent" >
<input type="hidden" id="tree" value="${tree}" />
<div id="jstree_div" style="margin-left: 50px;width: 300px">	
</div>
	
</div>

</body>
</html>





