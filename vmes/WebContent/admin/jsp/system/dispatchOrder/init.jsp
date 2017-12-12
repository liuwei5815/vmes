<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>派工单</title>
<script>
var addEmp = function(produceplanId) {
	var diag = new top.Dialog();
	diag.Title = "新增派工单";
	diag.URL = "dispatch!preAdd.action?produceplanId="+produceplanId;
	diag.Height=320;
	diag.Width=600;
	diag.show();
	return false;
}
var planPrint=function(){
	var id_array=new Array();  
	$("#querytable").contents().find("input[name='pId']:checked").each(function(){
		id_array.push($(this).val())
	});
	if(id_array.length==0){
		top.Dialog.alert("请选择要打印的工单");
		return false; 
	}   
	var diag = new top.Dialog();
	diag.Title = "打印派工单";
	diag.URL = "dispatch!queryPrintDis.action?idstr="+id_array;
	diag.Height=900;
	diag.Width=900;

	diag.show();
	return false;
}
</script>
</head>
<body>
	<div class="position">
		<div class="center">
			<div class="left">
				<div class="right">
					<span>当前位置：生产计划管理>>派工单管理 </span>
				</div>
			</div>
		</div>
	</div>
	<div class="box2" panelTitle="派工单管理" roller="false">
		<form method="post" id="frm" target="querytable" action="admin/dispatch!queryOrder.action">
		
			<input type="hidden" name="currPage" id="currPage"
				value="${currpage}" />
			<input type="hidden" name="perPageRows" id="perPageRows"
				value="${perPageRows}" />
			<table>
				<tr>
					<td>生产计划编号：</td>
					<td><input type="text" name="planCode" class="validate[length[0,15]]"></input></td>
					<td>状态：</td>
					<td>
						<select name="planState" class="validate[required]">
							<option value="">全部</option>
		              		<option value="0">待派工</option>
		              		<option value="1">待生产</option>
		              		<option value="2">生产中</option>
		              		<option value="3">已完成</option>
		              		<option value="-1">已取消</option>
	          			</select>
          			</td>
					<td>
						<button  type="submit">
							<span class="icon_find">查询</span>
						</button>
					</td>
					<td>
						<button onclick='planPrint()' type="button">
							<span class="icon_print" >打印</span>
						</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="scrollContent">
		<iframe scrolling="no" width="100%" frameBorder=0 id="querytable"
			name="querytable" src="${ctx}/admin/dispatch!queryOrder.action"
			onload="iframeHeight('querytable')" allowTransparency="true"></iframe>
	</div>
	<pt:page action="${ctx}/admin/dispatch!queryOrder.action"	target="querytable"></pt:page>
</body>
</html>