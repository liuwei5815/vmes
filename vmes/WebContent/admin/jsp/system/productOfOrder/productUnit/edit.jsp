<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=eYf9sA6yVTFHlh9ytU4a0EYY"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加产品</title>
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
    	this.parent.frmright.window.location.href="${ctx}/admin/product!queryUint.action";	
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

//单位树
function chooseUnit(showDomId,no){
	var diag = new top.Dialog();
	diag.ID="choose";
	diag.Title = "选择产品";
	diag.URL = "${ctx}/admin/orders!queryUint.action";
	diag.Height=500;
	diag.Width=600;
	diag.OKEvent=function(){
		var winContent= diag.innerFrame.contentWindow.document; 
		var dom=$(winContent);
		if($(winContent).find("#querytable").size()>0){
			dom=dom.find("#querytable").contents();
		}
		var checked =   dom.find("[name=pkValue]:checked");
		if(checked.size()==0){
			top.Dialog.alert("请至少选择一项");
			return false;
		}
		var type = $(winContent).find("#type").val();
			if(type!='3'){
				if(checked.size()>1){
					top.Dialog.alert("只能选择一项");
					return false;
				}
				if(no>0){
					$("#"+showDomId+no+"").val(checked.val());
					$("#"+showDomId+"_lable"+no).val(checked.attr("lable"));
				}else{
					$("#"+showDomId+"").val(checked.val());
					$("#"+showDomId+"_lable").val(checked.attr("lable"));
				}
				
			}
			else{
				
			}
			diag.close();
		};
	diag.show();
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
<div style="padding: 0 20px 0 20px;">
	<s:form action="admin/product!editUnit.action" method="post" theme="simple" id="frm">
	<input type="hidden" name="message" id="message" value="${message}" /> 
	<input type="hidden" name="successflag" id="successflag" value="${successflag}" /> 
	<input type="hidden" name="department.pid" value="${pid }" />
	<input name="pu.id" value="${pu.id }" type="hidden"/>
	 <table width="100%" class="tableStyle" transmode="true">
		<tr class="validate">
			<td>单位名称：</td>
			<td>
				<input type="text"name="pu.name" value="${pu.name}" lable="产品单位类型" dataType="2"class="validate[required,length[0,15]]" />
				<span class="star"> *</span>
			</td>
		</tr>
		 <tr  style="border: medium none; background-color: transparent;" yzid="2">
				<td style="text-align: right; padding-top: 3px; padding-bottom: 3px; border: medium none;">所属单位类型：</td>
					<td style="padding-top: 3px; padding-bottom: 3px; border: medium none;">
						<div style="float: left;"><input class="textinput simple" value="${ppu.name}" id="Type_1_lable" readonly="readonly" type="text">
						<input id="Type_1" name="pu.pid" type="hidden" value="${pu.pid}"></div>
						<span class="icon_find" onclick="chooseUnit('Type_1',0)">&nbsp; </span>
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
<%@include file="/admin/jsp/common/image_upload.jsp"%>
<%@include file="/admin/jsp/system/model/common/model.jsp" %>
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




