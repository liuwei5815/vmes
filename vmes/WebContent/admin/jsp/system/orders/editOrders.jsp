<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/admin/jsp/common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>建表</title>
<script type="text/javascript">
function cancel(){
	top.Dialog.close();
}
function doSubmit(){
	$("#frm").submit();
}
/**
 * 自动变换客户名称
 */
function cselFunc(){
	$("#selName").val($("#csel").val());
	$("#cusId").value($("#csel option:selected").attr("cusId"));
}
function unload(){
	$("#selName").val($("#csel option:selected").val())
	if(document.frm.successflag.value == "1"){//执行成功
    	top.Dialog.alert("保存成功",function(){
    		this.parent.frmright.doSubmit();
    		cancel();
    	});
    	/* this.parent.frmright.window.location.href="${ctx}/admin/orders!init.action";	 */
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

</script>

</head>
<body>
	<div class="box1">
		<s:form action="admin/orders!editOrders.action" method="post" theme="simple" id="frm">
			<input type="hidden" name="orders.id" value="${orders.id}" />
			<input type="hidden" name="message" id="message" value="${message}" /> 
			<input name="successflag" id="successflag" value="${successflag}" type="hidden"/> 
			<table width="100%" class="tableStyle" transmode="true">
			<tr >
				<td>订单编号：</td>
					<td>
						<input class="textinput default hid validate[custom[noSpecialCaracters],length[15,30]]" name="orders.orderCode" type="text" value="${orders.orderCode}" />
					</td>
			</tr>
			<tr >
				<td>下单日期：</td>
				<td>
					
					<input class="date validate[required]"  type="text" value="<s:date name="#request.orders.orderDate" format="yyyy-MM-dd" />" name="orders.orderDate"/>
				</td>
			</tr>
			<tr >
				<td>交付日期：</td>
				<td>
					<input class="date validate[required]" type="text" value="${orders.deliveryDate}" name="orders.deliveryDate"/>
				</td>
			</tr>
			<tr >
				<td>公司经手人：</td>
				<td>
					<select class="textinput" style="height:24px;" name="orders.empId" >
					   	<s:iterator  value="#request.empList" var="cell" status="item">
							 <option value="${cell.id}" <s:if test="#request.cell.id==#request.orders.empId"> selected = "selected"</s:if> >${cell.name}(${cell.serialNo})</option>
						</s:iterator>
 				 	</select>
				</td>
			</tr>
			 <tr >
				<td width="40%">客户编号：</td>
				<td>
				<select  onchange='cselFunc()' id="csel" class="textinput" style="height:24px;">
				   	<s:iterator  value="#request.cusList " var="cell" status="item">
						 <option cusId="${cell[0]}" customerName="${cell[1]}" value="${cell[1]}" <s:if test="%{#cell[0]==#request.orders.customerId}"> selected="selected" 	</s:if> >${cell[2]}(${cell[1]})</option>
					</s:iterator>
 				 </select>
 				 <input id="cusId" type="hidden" name="orders.customerId"  value="${cusList[0][0]}"/>
        	 </td>
			</tr>
			<tr >
				<td>客户名称：</td>
				<td>
					<input id="selName" textinput style="width: 118px; height: 18px;" disabled="" type="text" value=""  name="customerName"/> 
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
			       		<button id="add" onclick="return doSubmit();" type="button"><span class="icon_save">保 存</span></button>
			            <button onclick="return cancel();" type="button"><span class="icon_no">关 闭</span></button>
			       </p>
			       </td>
			    </tr>
		    </table> 
</body> 
</html>




