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
var addEquipment = function() {
	var diag = new top.Dialog();
	diag.Title = "新增物料信息";
	diag.URL = "material!preAdd.action";
	diag.Height=400;
	diag.Width=650;
	diag.show();
	return false;
}
function doSubmit(){
	$("#frm").submit();	
}
/**
 * 导入物料
 */
 function importMaterial(){
		var diag = new top.Dialog();
		/* var departmentId = $("#departmentId").val();
		if(departmentId==0){
			top.Dialog.alert("请选择部门");
			return false;
		} */
		diag.ID="model";
		diag.Title = "批量导入物料";
		diag.URL = "${ctx}/admin/material!preImportMaterial.action";
		diag.Width=500;
		diag.Height=300;
		diag.show();
		return false;
}
 var print=function(){
		var id_array=new Array();  
		$("#querytable").contents().find("input[name='pId']:checked").each(function(){
			id_array.push($(this).val())
		});
		if(id_array.length==0){
			top.Dialog.alert("请选择要打印的物料");
			return false; 
		}   
		var diag = new top.Dialog();
		diag.Title = "打印物料";
		diag.URL = "${ctx}/admin/material!print.action?ids="+id_array;
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
					<span>当前位置：基础数据管理>>物料信息配置</span>
				</div>
			</div>
		</div>
	</div>
	<div class="box2" panelTitle="物料信息配置" roller="false">
		<s:form method="post" target="querytable" action="admin/material!query.action" id="frm">
			<input type="hidden" name="totalCount" id="totalCount" value="${totalCount}"/>
		  	<input type="hidden" name="totalPage" id="totalPage" value="${totalPage}"/>
		  	<input type="hidden" name="currPage" id="currPage" value="${currPage}"/>
			<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}"/>
			<table>
				<tr>
					<td>物料名称：</td>
					<td><input name="maName" type="text" class="validate[length[0,15]]"/></td>
					<td>系统物料编号：</td>
					<td><input name="maCode" type="text" class="validate[length[0,15]]"/></td>
					<td>用户物料编号：</td>
					<td><input name="maUserCode" type="text" class="validate[length[0,15]]"/></td>
					<td>
						<button type="button" onclick="return doSubmit()"><span class="icon_find">查询</span></button>
						<button onclick="addEquipment()" type="button"><span class="icon_add">新增</span></button>
						<button onclick='return importMaterial()' type="button"><span class="icon_add">批量导入</span></button>
						<button onclick='print()' type="button"><span class="icon_print" >打印</span></button>
					</td>
				</tr>
			</table>
		</s:form>
	</div>
	<div id="scrollContent">
		<iframe scrolling="no" width="100%" frameBorder=0 id="querytable"
			name="querytable" src="${ctx}/admin/material!query.action"
			onload="iframeHeight('querytable')" allowTransparency="true"></iframe>
	</div>
	<pt:page action="${ctx}/admin/material!query.action"	target="querytable"></pt:page>
</body>
</html>