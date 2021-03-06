<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=eYf9sA6yVTFHlh9ytU4a0EYY"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新增设备区划类型</title>
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
    	this.parent.frmright.window.location.href="${ctx}/admin/area!init.action";	
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

//类型树
function chooseMaterialType(showDomId,no){
	var diag = new top.Dialog();
	diag.ID="choose";
	diag.Title = "选择行政区划";
	diag.URL = "${ctx}/admin/area!tree.action";
	diag.Height=700;
	diag.Width=300;
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
	<s:form action="admin/area!add.action" method="post" theme="simple" id="frm">
	<input type="hidden" name="message" id="message" value="${message}" /> 
	<input type="hidden" name="successflag" id="successflag" value="${successflag}" /> 
	 <table width="100%" class="tableStyle" transmode="true">
		<tr class="validate">
			<td>行政区划名称：</td>
			<td>
				<input type="text" name="region.name" class="validate[required,length[0,15]]" value="" lable="行政区划名称" dataType="2" />
				<span class="star"> *</span>
			</td>
		</tr>
		 <tr  style="border: medium none; background-color: transparent;" yzid="2">
				<td style="text-align: right; padding-top: 3px; padding-bottom: 3px; border: medium none;">上级行政区划：</td>
					<td style="padding-top: 3px; padding-bottom: 3px; border: medium none;">
						<div style="float: left;">
						<input class="textinput simple" value="" id="Type_1_lable" readonly="readonly" type="text" placeholder="不选表示创建一级类型">
						<input id="Type_1" name="region.Parentid" type="hidden"></div>
						<span class="icon_find" onclick="chooseMaterialType('Type_1',0)">&nbsp; </span>
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




