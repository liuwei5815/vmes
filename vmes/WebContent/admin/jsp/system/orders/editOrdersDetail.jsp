<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/admin/jsp/common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑订单详情</title>
<script type="text/javascript">
function cancel(){
	top.Dialog.close();
}
function doSubmit(){
	$("#frm").submit();	
}

function unload(){
	if(document.frm.successflag.value == "1"){//执行成功
    	top.Dialog.alert("保存成功",function(){
    		this.parent.frmright.doSubmit();
    		cancel();
    	});
    	this.parent.frmright.window.location.href="${ctx}/admin/orders!init.action";	
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
//单位树
function chooseUnit(showDomId,no){
	var diag = new top.Dialog();
	diag.ID="choose";
	diag.Title = "选择产品单位";
	diag.URL = "${ctx}/admin/orders!queryUint.action";
	diag.Height=500;
	diag.Width=600;
	diag.OKEvent=function(){
		var treenode= diag.innerFrame.contentWindow.getTreeNode();
		if(!treenode){
			top.Dialog.alert("请至少选择一项");	
			return false;
		}
		if(treenode.parent=='#'){
			top.Dialog.alert("请选择单位");
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
	<div class="box1">
		<s:form action="admin/orders!editDetail.action" method="post" theme="simple" id="frm">
			<input type="hidden" name="ordersDetail.id" value="${ordersDetail.id}" />
			<input name="successflag" id="successflag" value="${successflag}" type="hidden"/> 
			<input type="hidden" name="message" id="message" value="${message}" /> 
			<table width="100%" class="tableStyle" transmode="true">
			<tr >
				<td width="40%">产品编号：</td>
				<td>
	 			 <!-- 修改为不能修改产品 --> 
	 			<input style="width: 118px; height: 100%" disabled="" type="text" value="${ordersDetail.productCode}">
        	 </td>
			</tr>
			<tr >
				<td>产品名称：</td>
				<td>
			     	<input style="width: 118px; height: 100%" disabled="" type="text" value="${ordersDetail.productName}">
				</td>
			</tr>
			<tr >
				<td>规格/型号:</td>
				<td>
					<%-- <input class="textinput default hid"  name="selType" disabled type="text" value="${proList[0].typespec}"/> --%>
					<input style="width: 118px; height: 100%" disabled="" type="text" value="${ordersDetail.productTypespec}">
				</td>
			</tr>
			<%-- <tr >
				<td>单位：</td>
				<td>
					<input style="width: 118px; height: 100%" disabled="" type="text" value="${ordersDetail.productUnit}">
				</td>
				<td style="width: 80px;">
		          	<input class="textinput simple" value="${ordersDetail.productUnit}" id="Type_1_lable" readonly="readonly" type="text"/>
		          	<input id="Type_1" targetid="85" name="ordersDetail.productUnit" value="" type="hidden"/>
		          	<span  width="20px;" class="icon_find" onclick="chooseUnit('Type_1',0)">&nbsp</span>
         		 </td> 
			</tr> --%>
			<tr class="validate" style="border: medium none; background-color: transparent;" yzid="5">
				<td style="text-align: right; padding-top: 3px; padding-bottom: 3px; border: medium none;">单位：</td>
				<td style="padding-top: 3px; padding-bottom: 3px; border: medium none;">
				<div style="float: left;">
				<input  class="textinput simple validate[required]" name="ordersDetail.productUnit" value="${ordersDetail.productUnit}" id="Type_1_lable" readonly="readonly" type="text">
				<input id="Type_1" targetid="85"  value="${ordersDetail.productUnit}" not-null="1" lable="单位" datatype="1" type="hidden"></div>
				<span class="icon_find" onclick="chooseUnit('Type_1',0)">&nbsp; </span>
				</td>
			</tr>
			<tr >
				<td>数量：</td>
				<td>
					<input style="width: 118px; height: 100%" disabled="" type="text" value="${ordersDetail.num}" name="ordersDetail.num">
				</td>
			</tr>
			<tr>
				<td>交货期：</td>
				<td>
					<input class="date validate[required]" value="<s:date name="#request.ordersDetail.deliveryDate" format="yyyy-MM-dd" />" type="text" name="ordersDetail.deliveryDate"/>
				</td>
			</tr>
			<tr >
				<td>备注 ：</td>
				<td >
					<input class="textinput default hid"  value="${ordersDetail.remarks}" type="text" name="ordersDetail.remarks"/>
				</td>
			</tr>
			<tr>
				<td colspan="2"></td>
			</tr>
			</table>
		</s:form>
	</div>
	 <table width="100%" class="tableStyle" transmode="true">
				<tr>
			       <td colspan="2">
			       <p>
			       		<button id="add" onclick="return doSubmit();" type="button"><span class="icon_save">保 存</span> </button>
			            <button onclick="return cancel();" type="button"><span class="icon_no">关 闭</span> </button>
			       </p>
			       </td>
			    </tr>
		    </table> 
</body> 
</html>




