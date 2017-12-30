<!DOCTYPE html PUBLIC "
-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/ModelTag.tld" prefix="m" %>
<%@ include file="/admin/jsp/common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">
#checkboxTable{
	table-layout:fixed;
}
#checkboxTable tr td {
	width:100px;
	white-space: nowrap;
	overflow: hidden;
	text-align: center;
	text-overflow:ellipsis;
}
#frm{
	width:95%;
	margin:0 auto;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>建表</title>

<script type="text/javascript" charset="utf-8"
	src="${ctxAdmin}/js/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctxAdmin}/js/ueditor.all.min.js"> </script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=eYf9sA6yVTFHlh9ytU4a0EYY"></script>
<script type="text/javascript">
jQuery(document).ready(function(){
	unload();
});

function unload(){
    if($('#successflag').val() == "1"){//执行成功
    	top.Dialog.alert($("#message").val(),function(){
    		top.Dialog.close();
    	});
    	parent.frmright.doQueryTab();
		
	}else{
		if($("#message").val()!=""){
	    	//top.Dialog.alert($("#message").val());
	    	$("body").html($("#message").val());
	    	$("#message").val("");
	    }
	}
	return false;
}
function doSubmit(){
	var t_number = $("#number").val();
	if(t_number == ""){
		top.Dialog.alert("请输入编码");
		return;
	}
	
	var t_name = $("#name").val();
	if(t_name == ""){
		top.Dialog.alert("请输入名称");
		return;
	}
	
	$("#frm").submit();
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

<div style="">
	<input type="hidden" name="message" id="message" value="${message}" /> 
	<input type="hidden" name="successflag" id="successflag" value="${successflag}" />
	<s:form action="admin/testwk!edit.action" method="post" theme="simple" id="frm">
	<input type="hidden" name="test_wk.id" id="id" value="${test_wk.id}" />
	<input type="hidden" name="test_wk.cdate" id="cdate" value="${test_wk.cdate}" />
	<input type="hidden" name="test_wk.state" id="state" value="${test_wk.state}" />
	<table width="100%" class="tableStyle" transmode="true">
			<tr class="validate">
				<td>编码：</td>
				<td>
					<input type="text" id="number" name="test_wk.number" value="${test_wk.number }" />
				</td>
			</tr>
			<tr class="validate">
				<td>名称：</td>
				<td>
					<input type="text" id="name" name="test_wk.name" value="${test_wk.name }" />
				</td>
			</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
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
</body>
</html>




