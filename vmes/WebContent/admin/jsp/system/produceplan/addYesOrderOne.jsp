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
    	/* this.parent.frmright.window.location.href="${ctx}/admin/produceplanAction!init.action";	 */
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
	//验证时间
	var startDate= new Date($("input[name='produceplan.startDate']").val());
	var endDate=new Date($("input[name='produceplan.endDate']").val());
	if(startDate.getTime()>endDate.getTime()){
		top.Dialog.alert("计划开始时间不能早于计划结束时间");
		return;
	}
	$("#frm").submit();	
}
</script>
</head>
<body>
<div class="static_box1">
		<s:form action="admin/produceplanAction!addYesOrderOne.action" method="post" theme="simple" id="frm">
		<input type="hidden" name="message" id="message" value="${message}" /> 
		<input type="hidden" name="successflag" id="successflag" value="${successflag}" /> 
		<div style="padding:0 20px 0 20px;">
      		<fieldset>
				<legend>销售订单基本信息</legend>
    			<table width="100%" class="tableStyle" transmode="true">
    				<tr>
						<td>订单编号：</td>
						<td>
						<s:if test="#request.orders.orderCode!=''">
							${orders.orderCode}
						</s:if>
						<s:else>
							${orders.orderCodeAuto }
						</s:else>	
						</td>
						<td>客户编号：</td>
						<td>
							${cstomer.customerCode }
						</td>
						<td>客户名称：</td>
						<td>
							${cstomer.name }
						</td>
					</tr>
					<tr>
						<td>产品编号：</td>
						<td>
							${ordersDetail.productCode }
						</td>
						<td>产品名称：</td>
						<td>
							${ordersDetail.productName }
						</td>
						<td>规格/型号：</td>
						<td>
							${ordersDetail.productTypespec }
						</td>
					</tr>
					<tr>
						<td>产品单位：</td>
						<td>
							${ordersDetail.productUnit }
						</td>
						<td>订单数量：</td>
						<td>
							${ordersDetail.num }
						</td>
						<td>交货期：</td>
						<td>
							<s:date name="#request.ordersDetail.deliveryDate" format="yyyy-MM-dd" />
						</td>
					</tr>
					<tr>
					</tr>
			</table>
		</fieldset>
		
		<fieldset>
		<input type="hidden" name="produceplan.orderDetailId" value="${ordersDetail.id}"/>
		<input type="hidden" name="produceplan.productId" value="${product.id}"/>
				<legend>生产计划基本信息</legend>
    			<table width="100%" class="tableStyle" transmode="true">
    				<tr>
    					<td>生产计划编号</td>
    					<td>
							<input type="text" name="produceplan.manufactureCode" id="produceplanManufactureCode" class="validate[length[15,30],custom[noSpecialCaracters]]" value="" class="textinput" data-prompt-position="centerRight:-20" placeholder="如果不填系统自动生成"></input>
						</td>
						<td>生产计划数</td>
						<td>
							<input type="text" name="produceplan.num" id="produceplanNum" class="validate[required,custom[onlyNumber],length[0,6]] " value="" class="textinput default hid" data-prompt-position="centerRight:-20" ></input>
							<span class="star"> *</span>
						</td>
    				</tr>
    					
					<tr>
    					<td>计划开始日期</td>
    					<td>
							<input type="text" name="produceplan.startDate" id="produceplanStartDate" value="" class="date hid validate[required]" datefmt="yyyy-MM-dd"/>
							<span class="star"> *</span>
						</td>
						<td>计划结束日期</td>
						<td>
							<input type="text" name="produceplan.endDate" id="produceplanEndDate" value="" class="date hid validate[required]" datefmt="yyyy-MM-dd"/>
							<span class="star"> *</span>
						</td>
    				</tr>
    				<tr>
						<td colspan="4" align="center">
						</td>
					</tr>
    				<!-- <tr>
						<td width="40%">生产计划编号：</td>
						<td>
							<input type="text" name="produceplan.manufactureCode" id="produceplanManufactureCode" class="validate[maxSize[30]]" value="" class="textinput" data-prompt-position="centerRight:-20"></input>
						</td>
					</tr>
					<tr>
						<td>计划生产数量：</td>
						<td>
							<input type="text" name="produceplan.num" id="produceplanNum" class="validate[required,maxSize[6]]" value="" class="textinput default hid" data-prompt-position="centerRight:-20" ></input>
							<span class="star"> *</span>
						</td>
					</tr>
					<tr>
						<td>计划开始日期：</td>
						<td>
							<input type="text" name="produceplan.startDate" id="produceplanStartDate" value="" class="date hid validate[required]" datefmt="yyyy-MM-dd"/>
							<span class="star"> *</span>
						</td>
					</tr>
					<tr>
						<td>计划完成日期：</td>
						<td>
							<input type="text" name="produceplan.endDate" id="produceplanEndDate" value="" class="date hid validate[required]" datefmt="yyyy-MM-dd"/>
							<span class="star"> *</span>
						</td>
					</tr>
					<tr>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td colspan="2">
					    	<p>
					            <button onclick="return doSubmit();" type="button" id="add">保 存 </button>
					            <button onclick="top.Dialog.close();" type="button">关 闭 </button>
					    	</p>
						</td>
					</tr> -->
    			</table>
    	</fieldset>
	</div>
	</s:form>
</div>
<table width="100%" class="tableStyle" transmode="true">
				<tr>
			       <td colspan="2">
			       <p>
			       		<button id="save" onclick="return doSubmit();" type="button"><span class="icon_save">保 存</span></button>
			            <button onclick="top.Dialog.close();" type="button"><span class="icon_no">关 闭</span> </button>
			       </p>
			       </td>
			    </tr>
		    </table> 
</body>
</html>





