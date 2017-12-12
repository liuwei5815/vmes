<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>预算导入</title>
<script>
	$(document).ready(function(){ 
		unload();
	});
	
	function cancel(){
		top.Dialog.close();
		return false;
	}

	function unload(){
		//启用按钮
	    if($("#successflag").val() == "1"){//执行成功
	    	top.Dialog.alert("保存成功",function(){
	    		this.parent.frmright.doSubmit();
	    		cancel();
	    	});
	    	/* this.parent.frmright.window.location.href="${ctx}/admin/departmentAction!init.action";	 */
		}else{
			if(document.forms[0].message.value !=""){
				top.Dialog.alert(document.forms[0].message.value);
		    	document.forms[0].message.value = "";	   
		    }
		}
	}
	

	/**
	*
	**/
	function impContract(){
		var ysFile = $('#file').val();
		if(ysFile.trim() == ""){
			top.Dialog.alert("请上传Excel文件");
			return false;
		}
		var ext = ysFile.substring(ysFile.lastIndexOf("."),ysFile.length)
		if(ext.toLowerCase() != ".xls" && ext.toLowerCase() != ".xlsx"){
			top.Dialog.alert("请上传Excel文件");
			return false;
		}
		$("#frm").submit();
	}
</script>
</head>
<body>
<div class="padding5">
<form action="${ctx}/admin/material!importMaterial.action" name="form1" method="post" id="frm" style="margin: 0" enctype="multipart/form-data">
  	<input name="successflag" id="successflag" value="${successflag}" type="hidden"/>
  	<input name="message" id="message" value="${message}" type="hidden"/>
	<input type="hidden" name="departmentId" value="${departmentId }" />
	<table >
		<tr>
          	<td>上传文件：</td>
          	<td>
          		<input type="file" name="file" id="file"/>
          	</td>
          	<td>&nbsp;</td>
          	<td align="right">
        		<button onclick="return impContract();" type="button" id="add">导入</button>
        		<button onclick="return cancel();" type="button">取消</button>
        	</td>
        </tr>
        <tr>
            <td colspan="4" align="left">
	       	<span class="blue">
	       		说明:<br>
	       		1.<a href="${ctx }/excels/Materialtemplet.xls" style="color:red">点击此处下载模板;</a><br>
	       		2.请先将Excel模板中示例信息删除;<br>
	       		3.按示例填写要导入的物料信息;<br>
	       		4.上传过程中不要多次提交或刷新页面。<br>
	       	</span>
	     	</td>
	     </tr>
	</table>
</form>    
</div>
</body>
</html>
