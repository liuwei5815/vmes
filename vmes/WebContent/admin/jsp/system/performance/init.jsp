<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctxAdmin}/js/jquery.tmpl.min.js"></script>
<script type="application/javascript" src="${ctxAdmin}/js/jstree/jstree.js?v=4.0"></script>
<link rel="stylesheet" href="${ctxAdmin}/js/jstree/themes/default/style.min.css" />
<script type="application/javascript" src="${ctxAdmin}/js/jstree/overmenu/jstree.plugin.overmenu.min.js"></script>
<link rel="stylesheet" href="${ctxAdmin}/js/jstree/overmenu/overmenu.min.css" />
<title>数据表</title>
</head>
<body>
<div class="position">
	<div class="center">
		<div class="left">
			<div class="right"><span>当前位置：人员绩效管理 >>人员报工统计表</span></div>
		</div>
	</div>
</div>
<div class="box2" panelTitle="人员报工统计表" roller="false">
 <form action="${ctx}/admin/performanceAction!queryEmployee.action" method="post" target="querytable" id="frm">
 	<input type="hidden" id="employeeId" value="" name="employeeId"/>
 	<input type="hidden" id="departmentId" value="0" name="departmentId"/>
 	<input type="hidden" id="orgtree" value='${orgtree }'/>
 	<input type="hidden" name="currPage" id="currPage" value="${currpage}" /> 
 	<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}" />
 	<input type="hidden" name="Parentid" id="Parentid" value="${Parentid}" />
 	<table>
			<tr>
				<td style="width: 60px;">员工号：</td>
				<td><input type="text" name="empCode" class="validate[length[0,15]]"/></td>
				<td style="width: 60px;">姓名：</td>
				<td><input type="text" name="employeeName" class="validate[length[0,15]]"/></td>
				<td style="width: 60px;">产品名称：</td>
				<td><input type="text" name="productName" class="validate[length[0,15]]"/></td>
				<td style="width: 60px;">工序：</td>
				<td><input type="text" name="presName" class="validate[length[0,15]]"/></td>
				<td style="width: 80px;">生产计划号：</td>
				<td><input type="text" name="planCode" class="validate[length[0,15]]"/></td>
			</tr>
		</table>
		<table>
			<tr>
				<td style="width: 60px;">派工单号：</td>
				<td><input type="text" name="plantodoCode" class="validate[length[0,15]]"/></td>
				<td style="width: 60px;">时间段：</td>
				<td>
					<input class="date" name="claimStart"  type="text" value="${startTime}"/>
						至
					<input class="date" name="claimEnd"  type="text" value="${endTime}"/>
				</td>
				<td colspan="4">
					<button type="submit" ><span class="icon_find">查询</span></button>&nbsp;&nbsp;
					<button type="button" onclick="exportExcel()" ><span class="icon_xls">导出Excel</span></button>&nbsp;&nbsp;
				</td>
			</tr>
		</table>
 </form>
</div>
<div id="scrollContent">
	<table style="width: 100%;">
		<tr>
			<td style="width: 20%" valign="top">
				<div style="width: 100%; overflow: auto; height: 100%;">
					<div id="jstree_div"></div>
				</div>
			</td>
			<td style="width: 80%;" valign="top">
				<div style="overflow: auto;height: 100%;">
				<iframe scrolling="no" width="100%" frameBorder=0 id="querytable"
						name="querytable"
						src="${ctx}/admin/performanceAction!queryEmployee.action"
						onload="iframeHeight('querytable')" allowTransparency="true">
				</iframe>
				</div>
			</td>
		</tr>
	</table>
</div>
<pt:page action="${ctx}/admin/performanceAction!queryEmployee.action" target="querytable"></pt:page>
</body>
<script >
/**
 * 定义右键菜单,根据不同的节点类型有选择性的去掉部分菜单
 */
var ctxmenus = function(o, cb) {
	var items = {
		"rename" : null,
		"ccp" : null,
		/* "up" : {
			"separator_before" : false,
			"separator_after" : false,
			"label" : "上移",
			"action" : function(data) {
				upOrDownDept(o.id, "up");
			}
		},
		"down" : {
			"separator_before" : true,
			"separator_after" : false,
			"label" : "下移",
			"action" : function(data) {
				upOrDownDept(o.id, "down");
			}
		}  */
	};
	if (this.get_parent(o) == "#") {// root节点
		delete items.up;
		delete items.down;
	} 
	return items;
};

$(function() {
	$("#departmentId").val(0);
	/* emp.loadEmps(); */

	$('#jstree_div').jstree({
		"core" : {
			"data" : eval($("#orgtree").val()),
			"themes" : {
				"variant" : "large",// 加大
				"ellipsis" : true
			// 文字多时省略
			},
			"check_callback" : true
		},
		"plugins" : [ "wholerow", "themes"],
	}).on('select_node.jstree', function(event, data) {
		$("#departmentId").val(data.node.id);
		$("#currDeptName").text(data.node.text);
		emp.loadEmps();
	});

});

var emp = {
	loadEmps : function() {
		$("#frm").submit();
	}
};
function doSubmit(){
	$("#frm").submit();	
}
function exportExcel(){
	var claimStart=new Date($("input[name='claimStart']").val());
	var claimEnd=new Date($("input[name='claimEnd']").val());
	if(claimStart.getTime()>claimEnd.getTime()){
		top.Dialog.alert("开始时间不能早于结束时间");
		return;
	}
	window.location.href="${ctx}/admin/performanceAction!export.action?claimStart="+$("input[name='claimStart']").val()+"&claimEnd="+$("input[name='claimEnd']").val();
}
</script>
</html>





