<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设备信息配置</title>
<script>
var addEquipment = function() {
	var diag = new top.Dialog();
	diag.Title = "新增设备信息";
	diag.URL = "equipment!preAdd.action";
	diag.Height=500;
	diag.Width=650;
	diag.show();
	return false;
}
function doSubmit(){
	$("#frm").submit();	
}
/**
 * 导入设备
 */
 function importMaterial(){
		var diag = new top.Dialog();
		diag.ID="model";
		diag.Title = "批量导入设备";
		diag.URL = "${ctx}/admin/equipment!preImportEquipment.action";
		diag.Width=500;
		diag.Height=300;
		diag.show();
		return false;
}

//部门树
 function chooseDept(showDomId){
 	var diag = new top.Dialog();
 	diag.ID="choose";
 	diag.Title = "选择部门";
 	diag.URL = "${ctx}/admin/departmentAction!deptTree.action";
 	diag.Height=500;
 	diag.Width=600;
 	diag.OKEvent=function(){
 		var winContent= diag.innerFrame.contentWindow.document; 
 		var dom=$(winContent);
 		if($(winContent).find("#querytable").size()>0){
 	
 			dom=dom.find("#querytable").contents();
 		}
 		var checked =   dom.find("[name=pkValue]:checked");
 		if(checked.size()==0){
 			top.Dialog.alert("请至少选择一项");
 			return false;
 		}
 		var type = $(winContent).find("#type").val();
 			if(type!='3'){
 				if(checked.size()>1){
 					top.Dialog.alert("只能选择一项");
 					return false;
 				}
 				$("#"+showDomId+"").val(checked.val());
 				$("#"+showDomId+"_lable").val(checked.attr("lable"));
 			}
 			else{
 				
 			}
 			diag.close();
 		};
 	diag.show();
 }
 var print=function(){
		var id_array=new Array();  
		$("#querytable").contents().find("input[name='pId']:checked").each(function(){
			id_array.push($(this).val())
		});
		if(id_array.length==0){
			top.Dialog.alert("请选择要打印的设备");
			return false; 
		}   
		var diag = new top.Dialog();
		diag.Title = "打印设备";
		diag.URL = "${ctx}/admin/equipment!print.action?ids="+id_array;
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
					<span>当前位置：基础数据管理>>设备信息配置</span>
				</div>
			</div>
		</div>
	</div>
	<div class="box2" panelTitle="设备信息配置" roller="false">
		<s:form method="post" target="querytable" action="admin/equipment!query.action" id="frm">
			<input type="hidden" name="totalCount" id="totalCount" value="${totalCount}"/>
		  	<input type="hidden" name="totalPage" id="totalPage" value="${totalPage}"/>
		  	<input type="hidden" name="currPage" id="currPage" value="${currPage}"/>
			<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}"/>
			<table>
				<tr>
					<td>设备名称：</td>
					<td><input style="width:120px;" name="eqName" type="text" class="validate[length[0,15]]"/></td>
					<td>所属部门：</td>
					<td>
						<input style="width:120px;"  value="" id="deptType_lable" readonly="readonly" type="text" onclick="chooseDept('deptType')" class="validate[length[0,15]]"/>
						<input id="deptType" type="hidden" name="deptName" />
					</td> 
					<td>用户设备编号：</td>
					<td><input style="width:120px;" name="eqUserCode" type="text" class="validate[length[0,15]]"/></td>
					<td>系统设备编号：</td>
					<td><input style="width:120px;" name="eqCode" type="text" class="validate[length[0,15]]"/></td>
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
			name="querytable" src="${ctx}/admin/equipment!query.action"
			onload="iframeHeight('querytable')" allowTransparency="true"></iframe>
	</div>
	<pt:page action="${ctx}/admin/equipment!query.action"	target="querytable"></pt:page>
</body>
</html>