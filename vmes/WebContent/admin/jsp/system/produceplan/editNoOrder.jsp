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
		<s:form action="admin/produceplanAction!editNoOrder.action" method="post" theme="simple" id="frm">
		<input type="hidden" name="produceplan.id" id="produceplanId"  value="${produceplan.id }" />
		<input type="hidden" name="message" id="message" value="${message}" /> 
		<input type="hidden" name="successflag" id="successflag" value="${successflag}" /> 
		<div style="padding:0 20px 0 20px;">
      		<fieldset>
				<legend>生产计划基本信息</legend>
    			<table width="100%" class="tableStyle" transmode="true">
					
					<tr>
						<td>产品编号：</td>
						<td>
							<input type="text" value="${produceplan.productCode }" style="width: 118px; height: 100%" disabled />
						</td>
					</tr>
					<tr>
						<td>产品名称：</td>
						<td>
							<input type="text" value="${produceplan.productName }" style="width: 118px; height: 100%" disabled />
						</td>
					</tr>
					<tr >
						<td>规格型号：</td>
						<td>
							<input type="text" value="${produceplan.productTypespec }" style="width: 118px; height: 100%" disabled />
						</td>
					</tr>
					<tr>
						<td width="40%">生产计划编号：</td>
						<td>
							<input type="text" name="produceplan.manufactureCode" id="produceplanManufactureCode" class="validate[length[15,30],custom[noSpecialCaracters]]" value="${produceplan.manufactureCode }" class="textinput"  placeholder="如果不填系统自动生成"></input>
						</td>
					</tr>
					<tr >
						<td>生产计划数：</td>
						<td>
							<input type="text" value="${produceplan.num }" style="width: 118px; height: 100%" disabled />
						</td>
					</tr>
					<tr>
						<td>计划开始日期：</td>
						<td>
							<input type="text" name="produceplan.startDate" id="produceplanStartDate" value="<s:date name="#request.produceplan.startDate" format="yyyy-MM-dd" />" class="date hid validate[required]" pattern="yyyy-MM-dd"/>
							<span class="star"> *</span>
						</td>
					</tr>
					<tr>
						<td>计划结束日期：</td>
						<td>
							<input type="text" name="produceplan.endDate" id="produceplanEndDate" value="<s:date name="#request.produceplan.endDate" format="yyyy-MM-dd" />" class="date hid validate[required]" datefmt="yyyy-MM-dd"/>
							<span class="star"> *</span>
						</td>
					</tr>
					<%-- <tr>
						<td>实际完成日期：</td>
						<td>
							<input type="text" name="produceplan.finishTime" id="produceplanFinishTime" value="${produceplan.finishTime }" class="date hid" datefmt="yyyy-MM-dd HH:mm:ss"/>
							<span class="star"> *</span>
						</td>
					</tr> --%>
					<tr>
						<td colspan="2"></td>
					</tr>
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





