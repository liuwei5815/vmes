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
var addproduct = function() {
	var diag = new top.Dialog();
	diag.Title = "新增产品信息";
	diag.URL = "product!preAdd.action";
	diag.Height=400;
	diag.Width=650;
	diag.show();
	return false;
}
function doSubmit(){
	$("#frm").submit();	
}
/**
 * 导入产品
 */
 function importProduct(){
		var diag = new top.Dialog();
		diag.ID="model";
		diag.Title = "批量导入产品";
		diag.URL = "${ctx}/admin/product!preImportProduct.action";
		diag.Width=500;
		diag.Height=300;
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
					<span>当前位置：基础数据管理>>产品信息配置</span>
				</div>
			</div>
		</div>
	</div>
	<div class="box2" panelTitle="产品信息配置" roller="false">
		<s:form method="post" target="querytable" action="admin/product!query.action" id="frm">
		    <input type="hidden" name="totalCount" id="totalCount" value="${totalCount}"/>
		  	<input type="hidden" name="totalPage" id="totalPage" value="${totalPage}"/>
		  	<input type="hidden" name="currPage" id="currPage" value="${currPage}"/>
			<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}"/> 
			<table >
				<!-- <tr>
					<td>产品名称：</td>
					<td><input name="productName" type="text" class="validate[length[0,15]]"/></td>
					<td>系统产品编号：</td>
					<td><input name="productCode" type="text" class="validate[length[0,15]]"/></td>
					<td>用户产品编号：</td>
					<td><input name="userProductCode" type="text" class="validate[length[0,15]]"/></td>
					<td>
						<button type="button" onclick="return doSubmit()"><span class="icon_find">查询</span></button>
						<button onclick="addproduct()" type="button"><span class="icon_add">新增</span></button>
						<button onclick='return importProduct()' type="button"><span class="icon_add">批量导入</span></button>
					</td>
				</tr>-->
				<tr>
				<td>
				  <input name="productName" type="text" class="validate[length[0,15]]" placeholder="产品"/>
				
				  <button type="button" onclick="return doSubmit()"><span class="icon_find">查询</span></button>
				</td>
			    <td>	
				 <button onclick="addproduct()" type="button"><span class="icon_add">新增</span></button>
				
					<button onclick='return importProduct()' type="button"><span class="icon_add">模板导入</span></button>
				</td>
			</tr>
			</table>		
		</s:form>
	</div>
	<div id="scrollContent">
		<iframe scrolling="no" width="100%" frameBorder=0 id="querytable"
			name="querytable" src="${ctx}/admin/product!query.action"
			onload="iframeHeight('querytable')" allowTransparency="true"></iframe>
	</div>
	<pt:page action="${ctx}/admin/product!query.action"	target="querytable"></pt:page>
</body>
</html>