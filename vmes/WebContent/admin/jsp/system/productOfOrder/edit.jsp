<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=eYf9sA6yVTFHlh9ytU4a0EYY"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑产品</title>
<script type="text/javascript" charset="utf-8"
	src="${ctxAdmin}/js/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctxAdmin}/js/ueditor.all.min.js"> </script>
<script type="text/javascript">
function doSubmit(){
	$("#frm").submit();	
}

function cancel(){
	top.Dialog.close();
}

function unload(){
    if(document.frm.successflag.value == "1"){//执行成功
    	top.Dialog.alert("保存成功",function(){
    		cancel();
    	});
    	this.parent.frmright.window.location.href="${ctx}/admin/product!init.action";	
	}else{
		if(document.forms[0].message.value !=""){
			top.Dialog.alert(document.forms[0].message.value);
	    	document.forms[0].message.value = "";	   
	    }
	}
}

$(document).ready(function(){ 
	unload();
});
function chooseShip(tableId,showDomId,nameCn,gxId,pkId){
  	var pkVal ="";
	if(gxId!=""){
		var dom = $("[targetId='"+gxId+"']");
		var pkVal = dom.val();
		if(pkVal==""){
			top.Dialog.alert("请先选择"+dom.attr("lable"));
			return false;
		}
	}
	var diag = new top.Dialog();
	diag.ID="choose";
	diag.Title = "选择"+nameCn;
	diag.URL = "shipAction!init.action?shipId="+tableId+"&pkVal="+pkVal+"&pkId="+pkId;
	diag.Height=400;
	diag.Width=300;
	diag.OKEvent=function(){
		var treenode= diag.innerFrame.contentWindow.getTreeNode();
		if(!treenode){
			top.Dialog.alert("请至少选择一项");	
			return false;
		}
		if(treenode.parent=='#'){
			top.Dialog.alert("请正确选择类型");
			return false;
		}
		$("#"+showDomId+"").val(treenode.id);
		$("#"+showDomId+"_lable").val(treenode.text);
		diag.close();
	};
	diag.show();
}
</script>

</head>
<body>
<div class="static_box1">
<div class="box1_topcenter2">
<div class="box1_topleft2">
<div class="box1_topright2"></div>
</div>
</div>
<div class="box1_middlecenter">
<div class="box1_middleleft2">
<div class="box1_middleright2">
<div style="padding: 0 20px 0 20px;">
	<s:form action="admin/product!edit.action" method="post" theme="simple" id="frm">
	<input type="hidden" name="message" id="message" value="${message}" /> 
	<input type="hidden" name="successflag" id="successflag" value="${successflag}" /> 
	<input type="hidden" name="product.id" value="${product.id}" />
	<table width="100%" class="tableStyle" transmode="true">
	<tr class="validate">
	    <td>系统产品编号：</td>
		<td>
			C0001
		</td>
		<td>用户产品编号：</td>
		<td>
			<input type="text" name="product.userProductCode"  value="${product.userProductCode}"  class="validate[length[0,15]]" />
		</td>
	</tr>
	<tr class="validate">
		<td>产品名称：</td>
		<td>
			<input type="text" name="product.name" value="${product.name}" class="validate[required,length[0,15]]"/>
			<span class="star"> *</span>
		</td>
	</tr>
	<tr class="validate">
		<td>规格/型号：</td>
		<td>
			<input type="text" name="product.typespec" value="${product.typespec}" class="validate[required,length[0,15]]"/>
			<span class="star"> *</span>
		</td>
	</tr>
	<!-- <tr  style="border: medium none; background-color: transparent;" yzid="2">
		<td style="text-align: right; padding-top: 3px; padding-bottom: 3px; border: medium none;">货品类型：</td>
			<td style="padding-top: 3px; padding-bottom: 3px; border: medium none;">
				<div style="float: left;"><input class="textinput simple validate[required,length[0,6]]" value="${maTpye}" id="type_lable" readonly="readonly" type="text">
				<input id="type" targetid="84" name="product.type" value="${product.type}" not-null="1" lable="货品类型" datatype="1" type="hidden"></div>
				<span class="icon_find" onclick="chooseShip('3','type','物料类型','','')">&nbsp; </span>
			</td>
	</tr>
	<tr class="validate">
		<td>产品备注：</td>
		<td>
			<input type="text" name="product.dsc" value="${product.dsc}" class="validate[length[0,20]]" />
		</td>
	</tr> -->
	<tr>
		<td colspan="2"></td>
	</tr>
	</table>
	</s:form>
</div>
</div>
</div>
</div>
<div class="box1_bottomcenter2">
<div class="box1_bottomleft2">
<div class="box1_bottomright2"></div>
</div>
</div>
</div>
<table width="100%" class="tableStyle" transmode="true">
	<tr>
		<td colspan="2">
		  <p>
		    <button onclick="return doSubmit();" type="button" id="preserve"><span class="icon_save">保 存</span> </button>
            <button onclick="top.Dialog.close();" type="button"><span class="icon_no">关 闭</span> </button>
			</p>
		</td>
	</tr>
</table>
</body>
</html>




