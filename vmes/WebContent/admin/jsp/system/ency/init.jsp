<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菜单管理</title>
<script>
function customHeightSet(){
	$("#querytable").height($("#scrollContent").height());
}

//一级菜单被点击时显示其下属的二级菜单
function treeClick(parentId){
	$("#parentId").val(parentId);
	doQuery();
}

//查询
function doQuery() {
	$("#frm").submit();
}

//添加操作
function openAddWin(){
	var diag = new top.Dialog();
	diag.Title = "添加菜单";
	diag.URL = "agrEncy!preAdd.action";
	diag.Height=320;
	diag.Width=350;
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
					<span>当前位置：农业百科 >>农业百科 </span>
				</div>
			</div>
		</div>
	</div>
	<div class="box2" panelTitle="农业百科" roller="false">
		<s:form action="admin/agrEncy!query.action" method="post" target="main" theme="simple" id="frm">
			<input type="hidden" name="currPage" id="currPage" value="${currpage}" />
			<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}" />
			<input type="hidden" name="ency.parentId" id="parentId"/>
			<table>
				<tr>
					<td nowrap="nowrap">名称:</td>
					<td><input id="name" name="ency.name" value="${ency.name }" class="validate[length[0,15]]" /></td>
					
					<td><button onclick='return doQuery()' type="button">
					<span class="icon_find">搜索</span></button></td>
					
					<td><button onclick='return openAddWin()' type="button">
					<span class="icon_add">新增</span></button></td>
				</tr>
			</table>
		</s:form>
	</div>
	<div id="scrollContent" childScrollContent="true">
	
	<table width="100%" border="0" >
		<tr align="left" valign="top" >
		<td width="20%" >
			<iframe scrolling="no" width="100%" frameBorder=0 id="querytable" height="100%"
					name="encytree"  allowTransparency="true" src="${ctx}/admin/agrEncy!encyTree.action">		
			</iframe>
		</td>
		<td width="80%">
			<iframe frameborder="0" width="100%" id="main" name="main" height="100%"
				 	onload="iframeHeight('main')"	src="${ctx}/admin/agrEncy!query.action" scrolling="no"></iframe>
		</td>
		</tr>	
	</table>				
	</div>
	<pt:page action="${ctx}/admin/agrEncy!query.action" target="main"></pt:page>
</body>

</html>