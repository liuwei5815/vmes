<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>生产计划</title>
<script>
function cancel(){
	top.Dialog.close();
}

function unload(){
    if(document.frm.successflag.value == "1"){//执行成功
    	top.Dialog.alert("保存成功",function(){
    		this.parent.frmright.doSubmit();
    		cancel();
    	});
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

function doSubmit(){
	if($("input[name='pehWaring']").val()=="proPehWaring"){
		top.Dialog.confirm("上次调整以后，生产计划还未进行相应的调整，您确定要再次调整订单数量?",function(){
			$("#frm").submit();	
			return;
		});
	}else if($("input[name='pehWaring']").val()=="disPehWaring"){
		top.Dialog.confirm("上次调整以后，派工单还未进行相应的调整，您确定要再次调整订单数量?",function(){
			$("#frm").submit();	
			return;
		});
	}else{
		$("#frm").submit();	
	}
}
</script>
</head>
<body>
<div class="static_box1">
	<div class="box1_topcenter2">
		<div class="box1_topleft2">
			<div class="box1_topright2">
			</div>
		</div>
	</div>
	<div class="box1_middlecenter">
	<div class="box1_middleleft2">
	<div class="box1_middleright2">
		<s:form action="admin/orders!editDetailNum.action" method="post" theme="simple" id="frm">
		<input type="hidden" name="pehWaring" value="${peh}" />
		<input type="hidden" name="produceplan.id" id="produceplanId"  value="${produceplan.id }" />
		<input type="hidden" name="message" id="message" value="${message}" /> 
		<input type="hidden" name="successflag" id="successflag" value="${successflag}" /> 
		<input type="hidden" name="detailId" id="pehId"  value="${orderDetail.id}" />
		<div style="padding:0 20px 0 20px;">
      		<fieldset>
				<legend>销售订单基本信息</legend>
				<table width="100%" class="tableStyle" transmode="true">
    				<tr>
						<td>订单编号：</td>
						<td>
							${orders.orderCode }
						</td>
						<td>下单日期：</td>
						<td>
							<s:date name="#request.orders.orderDate" format="yyyy-MM-dd" />
						</td>
						<td>交付日期：</td>
						<td>
							<s:date name="#request.orders.deliveryDate" format="yyyy-MM-dd" />
						</td>
					</tr>
					<tr>
						<td>公司经手人：</td>
						<td>
							${employee.name }
						</td>
						<td>客户编号：</td>
						<td>
							${cstomer.customerCode}
						</td>
						<td>客户名称：</td>
						<td>
							${cstomer.name}
						</td>
					</tr>
					<tr></tr>
			</table>
		</fieldset>
		
		<fieldset>
		<input type="hidden" name="produceplan.orderDetailId" value="${ordersDetail.id}"/>
		<input type="hidden" name="produceplan.productId" value="${product.id}"/>
				<legend>订单详情基本信息</legend>
    			<table width="100%" class="tableStyle" transmode="true">
    				 <tr>
						<td>产品编号：</td>
						<td>
							<input style="width: 118px; height: 100%" disabled="" type="text" value="${product.productCode }">
						</td>
						<td>产品名称：</td>
						<td>
							<input style="width: 118px; height: 100%" disabled="" type="text" value="${product.name }">
						</td>
					</tr>
    				<tr>
						<td>交货日期：</td>
						<td>
							<input style="width: 118px; height: 100%" disabled="" type="text" value="<s:date name="#request.orderDetail.deliveryDate" format="yyyy-MM-dd" />">
						</td>
					<td>订单数量：</td>
						<td>
							<input type="text" name="num" id="produceplanManufactureCode" class="validate[required,custom[onlyNumber],length[0,6]] textinput" value="${orderDetail.num}" class="textinput"></input>
							<span class="star"> *</span>
						</td>
					 <tr>
						<td colspan="4"></td>
					</tr>
    			</table>
    	</fieldset>
	</div>
	</s:form>
</div>
</div>
</div>
<div class="box1_bottomcenter2">
<div class="box1_bottomleft2">
<div class="box1_bottomright2">
</div>
</div>
</div>
</div>
<table width="100%" class="tableStyle" transmode="true">
				<tr>
			       <td colspan="2">
			       <p>
			       		<button id="add" onclick="return doSubmit();" type="button"><span class="icon_save">保 存</span></button>
			            <button onclick="top.Dialog.close();" type="button"><span class="icon_no">关 闭</span></button>
			       </p>
			       </td>
			    </tr>
		    </table> 
</body>
</html>





