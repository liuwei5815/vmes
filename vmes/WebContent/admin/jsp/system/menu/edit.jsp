<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<title>新增菜单</title>
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
	
	var re=/^[0-9]*$/;
	if(!re.test(num)){
		top.Dialog.alert("请输入0-99之间的数字排序");
		return false;
	}
	
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
    	this.parent.frmright.window.location.href="${ctx}/admin/menu!init.action";	
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
<s:form name="frm" action="menu!edit.action" method="post" theme="simple" id="frm">
	<s:hidden name="message" id="message"/>
	<s:hidden name="menu.id" id="menuId" />
	<s:hidden name="menu.level" id="menuLevel" />
	<input name="successflag" id="successflag" value="${successflag}" type="hidden"/>
	<table width="100%" class="tableStyle" transmode="true">
	
		<tr>
            <td>菜单名称：</td>
            <td><s:textfield id="name" maxlength="50"  name="menu.name" /><span class="star"> *</span></td>
		</tr>
		
		<tr>
			<td >父级菜单：</td>		
			<td>
          	<select id="supiorId" name="menu.supiorId" >
          		<!-- 是否是一级菜单 -->
          		<s:if test="menu.supiorId==0">
          		<option value="0">这是一个一级菜单</option>          		
          		</s:if>
          		<!-- 是否是二级菜单 -->
          		<s:iterator value="list" status="st" var="cell">          		
          		<s:if test="#cell.id==menu.supiorId &&menu.supiorId!=0">
          		<option value="${cell.id}">${cell.name}</option>     
          		</s:if>              				
          		</s:iterator>
          		<s:iterator value="list" status="st" var="cell">
          		<s:if test="#cell.supiorId==0&&#cell.id!=menu.supiorId&&menu.supiorId!=0">
          		<option value="${cell.id}">${cell.name}</option>     
          		</s:if>     		
          		</s:iterator>
          		<s:if test="menu.supiorId!=0"><option value="0">无</option> </s:if>
          		        		
          	</select>
          	<span class="star">*</span>
          	</td>             	
        </tr>
        
        <tr>
            <td>URL：</td>
            <td><s:textfield id="url" maxlength="50" name="menu.url" /></td>
		</tr>
		
		<tr>
            <td>排序：</td>
            <td><s:textfield id="orderby" maxlength="2"  name="menu.orderby" /></td>
		</tr>
		
		<tr>
            <td>图标样式：</td>
            <td><s:textfield id="icon" maxlength="20"  name="menu.icon"/></td>
		</tr>
		<tr>
            <td>弹窗大小:</td>
            <td>
            宽:<input id="width" style="width:40px;" maxlength="20"  name="menuConfig.layerWidth" value="${menuConfig.layerWidth}"/>
            高:<input id="height" style="width:40px;" maxlength="20"  name="menuConfig.layerHeight" value="${menuConfig.layerHeight}"/>
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
            <td>是否菜单：</td>
            <td><select id="isMenu" name="menu.isMenu">
            	<s:if test="#cell.isMenu==0">
           		<option value="0">是</option>
          		<option value="1">否</option>    
           		</s:if>
           		<s:else>
           		<option value="1">否</option>    
           		<option value="0">是</option>
           		</s:else>   
            </select>
            </td>
		</tr>
		
		<tr>
            <td colspan="2">
            <p>
            <button onclick="return doSubmit();" type="button"><span class="icon_save">保 存</span></button>
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
</body>
</html>