<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/admin/jsp/common/common.jsp"%>
<title>新增菜单</title>
<script type="text/javascript">
function doSubmit(){
	var reg = new RegExp("^[0-9]*$");
	if(!reg.test($("#pcount").val())) { 
	 	top.Dialog.alert("只能输入数字");
		return false; 
	}
	if($("#pcount").val()==0) { 
	 	top.Dialog.alert("必须大于0");
		return false; 
	}
 	if($("#pcount").val()>30) { 
	 	top.Dialog.alert("最多30张");
		return false; 
	}
	showProgressBar("正在生成中")
	$("#frm").submit();	
}

function cancel(){
	top.Dialog.close();
}

function unload(){
	
    if(document.frm.successflag.value == "1"){//执行成功
    	top.Dialog.alert("生成成功",function(){
    	
    		$(window.parent.frmright.document).find("#frm").submit();
    		cancel();
    	});
    	
	}else{
		if(document.forms[0].message.value !=""){
			top.Dialog.alert(document.forms[0].message.value);
	    	document.forms[0].message.value = "";	   
	    }
	}
}
function validatePcount(){  
	
	console.log("test");
    var value = $("#pcount").val();  
 
    return !(parseInt(value)>=1 && parseInt(value)<=30);
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
<div class="box1_topright2">
</div>
</div>
</div>
<div class="box1_middlecenter">
<div class="box1_middleleft2">
<div class="box1_middleright2">
<div style="padding:0 20px 0 20px;">
	
<s:form name="frm" action="dispatch!add.action" method="post" theme="simple" id="frm">
	<input type="hidden" name="produceplanId" value="${produceplanId }"/>
	<s:hidden name="message" id="message"/>
	<input name="successflag" id="successflag" value="${successflag}" type="hidden"/>
	<table width="100%" class="tableStyle" transmode="true">
		
		<tr>
            <td>需要生成的派工单数量</td>
            <td><s:textfield id="pcount" class="validate[required,custom[onlyNumber],funcCall[validateP]]" name="count"/><span class="star"> *</span></td>
		</tr>
		<tr>
			<td colspan="2" style="padding-right: 30px;">
		
			说明：一次性最多能生成30张派工单</td>
		
		</tr>
	</table>        
</s:form>
</div>
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
			            <button onclick="top.Dialog.close();" type="button"><span class="icon_no">取消</span></button>
			       </p>
			       </td>
			    </tr>
		    </table> 	
</body>
</html>