<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=eYf9sA6yVTFHlh9ytU4a0EYY"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新增客户</title>
<script type="text/javascript">
function doSubmit(){
	$("#frm").submit();	
}

function unload(){
	
    if(document.frm.successflag.value == "1"){//执行成功
    	top.Dialog.alert("保存成功", function() {
    		top.frames['_DialogFrame_addorder'].contentWindow.customer();
    		top.Dialog.close();
    		//this.parent.frmright.window.location.href="${ctx}/admin/orders!init.action";	
    	});
    
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
	
	<s:form action="admin/orders!addCustomer.action" method="post" theme="simple" id="frm">
	<input type="hidden" name="message" id="message" value="${message}" /> 
	<input type="hidden" name="successflag" id="successflag" value="${successflag}" /> 
	<table width="100%" class="tableStyle" transmode="true">
	<tr class="validate">
		<td>客户名称：</td>
		<td>
			<input type="text" name="customer.name" class="validate[required,length[0,20]]" />
			<span class="star"> *</span>
		</td>
	</tr>
	<tr class="validate">
		<td>联系方式：</td>
		<td>
			<input type="text" name="customer.tel"  class="validate[required,custom[mobilephone]]" />
			<span class="star"> *</span>
		</td>
	</tr>
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




