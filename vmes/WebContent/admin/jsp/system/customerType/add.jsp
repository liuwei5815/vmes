<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=eYf9sA6yVTFHlh9ytU4a0EYY"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加设备</title>
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
    	this.parent.frmright.window.location.href="${ctx}/admin/customerType!init.action";	
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
	
	<s:form action="admin/customer!add.action" method="post" theme="simple" id="frm">
	<input type="hidden" name="message" id="message" value="${message}" /> 
	<input type="hidden" name="successflag" id="successflag" value="${successflag}" /> 
	<input type="hidden" name="department.pid" value="${pid }" />
	<table width="100%" class="tableStyle" transmode="true" style="height: 100px">
	<tr>
		<td>客户类型：</td>
		<td>
			<input type="text" name="customer.type" class="validate[required,length[0,15]]" />
			<span class="star"> *</span>
		</td>
	</tr>
	<tr class="validate">
		<td>客户说明：</td>
		<td>
			<input type="text" name="customer.description" class="validate[required,length[0,15]]" />
			<span class="star"> *</span>
		</td>
	</tr>
	<tr  style="height: 0px;display:none">
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
<%@include file="/admin/jsp/common/image_upload.jsp"%>
<%@include file="/admin/jsp/system/model/common/model.jsp" %>
<table width="100%" class="tableStyle" transmode="true" style="height: 60px">
	<tr>
		<td colspan="2">
		  <p>
		    <button onclick="return doSubmit();" type="button" id="preserve" style="margin: 0px 30px"><span class="icon_save" >保 存</span> </button>
            <button onclick="top.Dialog.close();" type="button"  style="margin: 0px 30px"><span class="icon_no">关 闭</span> </button>
			</p>
		</td>
	</tr>
</table> 
</body>
</html>




