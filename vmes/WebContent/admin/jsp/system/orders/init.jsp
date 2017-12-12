<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>销售订单管理</title>
<script>
/*
	document.documentElement.clientWidth：取得浏览器页面可视区域的宽度
	document.documentElement.clientHeight：取得浏览器页面可视区域的高度
	screen.width：取得屏幕宽度
	screen.height：取得屏幕高度
	screen.availWidth：取得除任务栏外的屏幕宽度
	screen.availHeight取得除任务栏外的屏幕高度
*/
var addOrders = function() {
	var diag = new top.Dialog();
	diag.ID="addorder";
	diag.Title = "新增销售订单";
	diag.URL = "orders!preAdd.action";
	//document.documentElement.clientHeight 获取浏览器可视页面的高度
	diag.Height=document.documentElement.clientHeight*0.8;
	diag.Width=950;
	diag.show();
	return false;
}

function doSubmit(){
	$("#frm").submit();	
}

function customer(){
	
	console.log("222222222222222")
}
</script>
</head>
<body>
<div class="position">
		<div class="center">
			<div class="left">
				<div class="right">
					<span>当前位置：销售管理>>销售订单生成</span>
				</div>
			</div>
		</div>
	</div>
	<div class="box2" panelTitle="销售订单管理" roller="false">
		<form method="post" target="querytable" action="admin/orders!queryOrder.action" id="frm">
			<input type="hidden" name="totalCount" id="totalCount" value="${totalCount}"/>
		  	<input type="hidden" name="totalPage" id="totalPage" value="${totalPage}"/>
		  	<input type="hidden" name="currPage" id="currPage" value="${currPage}"/>
			<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}"/>
			<table>
				<tr>
					<td>订单编号：</td>
					<td><input name="orderCode" type="text" class="validate[length[0,15]]"/></td>
					<td>客户名称：</td>
					<td><input name="cusName" type="text" class="validate[length[0,15]]"/></td>
					<td>订单状态：</td>
					<td>
						<select name="orderState" class="validate[required]">
							<option value="">全部</option>
		              		<option value="0">待排产</option>
		              		<option value="1">生产中</option>
		              		<option value="2">生产已完成</option>
		              		<option value="-1">已取消</option>
	          			</select>
          			</td>
					<td colspan="4">
						<button type="button" onclick="return doSubmit()"><span class="icon_find">查询</span></button>&nbsp;&nbsp;
						<button onclick="addOrders()" type="button"><span class="icon_add">新增</span></button>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="scrollContent">
		<iframe scrolling="no" width="100%" frameBorder=0 id="querytable"
			name="querytable" src="${ctx}/admin/orders!queryOrder.action"
			onload="iframeHeight('querytable')" allowTransparency="true"></iframe>
	</div>
	<pt:page action="${ctx}/admin/orders!queryOrder.action"	target="querytable"></pt:page>
</body>
</html>