<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<title>编辑菜单</title>
<%@ include file="/admin/jsp/common/common.jsp"%>
<script type="text/javascript">
function doSubmit(){	
	var name = $("#name").val();
	var num = $("#orderby").val();
	
	
	if(name== "") {
		top.Dialog.alert("请输入菜单名称");
		name.focus();
		return false;
	}		
	
	/* var re=/^[0-9]*$/;
	if(!re.test(num)){
		top.Dialog.alert("请输入0-99之间的数字排序");
		return false;
	} */
	
	$("#frm").submit();		
}

function cancel(){
	top.Dialog.close();
}

function unload(){
    if(document.frm.successflag.value == "1"){//执行成功
    	top.Dialog.alert("修改成功",function(){
    		cancel();
    	});
    	this.parent.frmright.window.location.href="${ctx}/admin/agrEncy!init.action";	
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
<div class="box1_topright2">
</div>
</div>
</div>
<div class="box1_middlecenter">
<div class="box1_middleleft2">
<div class="box1_middleright2">
<div style="padding:0 20px 0 20px;">
<s:form name="frm" action="agrEncy!edit.action" method="post" theme="simple" id="frm">
	<s:hidden name="message" id="message"/>
	<s:hidden name="menu.id" id="menuId" />
	<s:hidden name="menu.level" id="menuLevel" />
	<input type="hidden" name="ency.id" value="${ency.id }"/>
	<input name="successflag" id="successflag" value="${successflag}" type="hidden"/>
	<table width="100%" class="tableStyle" transmode="true">
	
		<tr>
            <td>菜单名称：</td>
            <td><s:textfield id="name" maxlength="50"  name="ency.name" /><span class="star"> *</span></td>
		</tr>
		<div><span> 
		<input type="file" accept="image/*"	class="fileinput" id="upload_coverPhoto" /> </span>
		<input type="hidden" id="coverPhoto"${pro } value="${val }" ></input> 
		<input type="button" value="上传" onclick="upload('coverPhoto')" /> 
		<s:set var="size" value="#cell.optvalueList" scope="request" /> 
		宽度:<input type="text" id="coverPhoto_width" value="${size[0] }"	style="width: 60px" /> 
		高度:<input type="text" id="coverPhoto_height" value="${size[1] }" style="width: 60px" />
		<input type="button" value="裁剪" onclick="showJcrop('coverPhoto');" /></div>
		<!-- 上传完成预览div -->
		
		<div id="coverPhoto_view">						
			<image  class='image' src="<s:property  value='@com.xy.cms.common.SysConfig@getStrValue(\'picUrl\')'/>${val}" />
		</div>
		
		<tr>
			<td >父级菜单：</td>		
			<td>
          	<select id="parentId" name="ency.parentId" >
          		<!-- 是否是一级菜单 -->
          		<s:if test="ency.parentId==0">
          		<option value="0">这是一个一级菜单</option>          		
          		</s:if>
          		<!-- 是否是二级菜单 -->
          		<s:iterator value="list" status="st" var="cell">          		
          		<s:if test="#cell.id==ency.parentId &&ency.parentId!=0">
          		<option value="${cell.id}">${cell.name}</option>     
          		</s:if>              				
          		</s:iterator>
          		<s:iterator value="list" status="st" var="cell">
          		<s:if test="#cell.parentId==0&&#cell.id!=ency.parentId&&ency.parentId!=0">
          		<option value="${cell.id}">${cell.name}</option>     
          		</s:if>     		
          		</s:iterator>
          		<s:if test="ency.parentId!=0"><option value="0">无</option> </s:if>
          		        		
          	</select>
          	<span class="star">*</span>
          	</td>             	
        </tr>
			
		<tr>
           	<td>状态：</td>
           	<td><select id="state" name="menu.state" >
           		<s:if test="#cell.state==1">
           		<option value="1">正常</option>
          		<option value="0">停用</option>    
           		</s:if>
           		<s:else>
           		<option value="0">停用</option>    
           		<option value="1">正常</option>
           		</s:else>          				
          	</select>
          	</td>
		</tr>
		
		
		<tr>
            <td colspan="2">
            <p>
            <button onclick="return doSubmit();" type="button"><span class="icon_save">保 存</span> </button>
            <button onclick="top.Dialog.close();" type="button"><span class="icon_no">关 闭</span> </button>
          	</p>
          	</td>
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
<%@include file="/admin/jsp/common/image_upload.jsp"%>
</body>
</html>