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
	$("#frm").submit();	
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
		<s:form action="admin/produceplanAction!editYesOrderOne.action" method="post" theme="simple" id="frm">
		<input type="hidden" name="produceplan.id" id="produceplanId"  value="${obj[13] }" />
		<input type="hidden" name="message" id="message" value="${message}" /> 
		<input type="hidden" name="successflag" id="successflag" value="${successflag}" /> 
		<div style="padding:0 20px 0 20px;">
      		<fieldset>
				<legend>销售订单基本信息</legend>
    			<table width="100%" class="tableStyle" transmode="true">
    				<tr>
						<td>订单编号：</td>
						<td>
							${obj[0] }
						</td>
						<td>客户编号：</td>
						<td>
							${obj[1] }
						</td>
						<td>客户名称：</td>
						<td>
							${obj[2] }
						</td>
					</tr>
					<tr>
						<td>产品编号：</td>
						<td>
							${obj[3] }
						</td>
						<td>产品名称：</td>
						<td>
							${obj[4] }
						</td>
						<td>规格型号：</td>
						<td>
							${obj[5] }
						</td>
					</tr>
					<tr>
						<td>产品单位：</td>
						<td>
							${obj[6] }
						</td>
						<td>生产数量：</td>
						<td>
							${obj[7] }
						</td>
						<td>交货期：</td>
						<td>
							<s:date name="#request.obj[8]" format="yyyy-MM-dd" />
						</td>
					</tr>
					<tr></tr>
			</table>
		</fieldset>
		
		<fieldset>
				<legend>生产计划基本信息</legend>
    			<table width="100%" class="tableStyle" transmode="true">
    				<tr>
						<td>生产计划编号：</td>
						<td>
							<input type="text" name="produceplan.manufactureCode" id="produceplanManufactureCode" class="validate[length[15,30],custom[noSpecialCaracters]]" value="${obj[9] }" class="textinput" placeholder="如果不填系统自动生成"></input>
						</td>
						<td>生产计划数：</td>
						<td>
							<input type="text" value="${obj[10] }" style="width: 118px; height: 100%" disabled />
						</td>
					</tr>
					<tr>
						<td>计划开始日期：</td>
						<td>
							<input type="text" name="produceplan.startDate" id="produceplanStartDate" value="<s:date name="#request.obj[11]" format="yyyy-MM-dd" />" class="date hid validate[required]" datefmt="yyyy-MM-dd"/>
							<span class="star"> *</span>
						</td>
						<td>计划结束日期：</td>
						<td>
							<input type="text" name="produceplan.endDate" id="produceplanEndDate" value="<s:date name="#request.obj[12]" format="yyyy-MM-dd" />" class="date hid validate[required]" datefmt="yyyy-MM-dd"/>
							<span class="star"> *</span>
						</td>
					</tr>
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
			       		<button id="save" onclick="return doSubmit();" type="button"><span class="icon_save">保 存</span></button>
			            <button onclick="top.Dialog.close();" type="button"><span class="icon_no">关 闭</span> </button>
			       </p>
			       </td>
			    </tr>
		    </table> 
</body>
</html>





