<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/admin/jsp/common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=eYf9sA6yVTFHlh9ytU4a0EYY"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctxAdmin}/js/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctxAdmin}/js/ueditor.all.min.js"> </script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑公司信息</title>

<script type="text/javascript" charset="utf-8"
	src="${ctxAdmin}/js/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctxAdmin}/js/ueditor.all.min.js"> </script>
<script type="text/javascript">
$(document).ready(function(){ 
	unload();
});

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
    	this.parent.frmright.window.location.href="${ctx}/admin/departmentAction!init.action";	
	}else{
		if(document.forms[0].message.value !=""){
			top.Dialog.alert(document.forms[0].message.value);
	    	document.forms[0].message.value = "";	   
	    }
	}
}


function queryProvince(shipId,SignId,hiddenSignId,nameCn){
	if(nameCn=="市"){
		var companyProvince = $("#companyProvince").val();
		if(companyProvince==""){
			top.Dialog.alert("请先选择省");
			return false;
		}else{
			shipId=companyProvince;
		}
	}
	var diag = new top.Dialog();
	diag.ID="choose";
	diag.Title = "选择"+nameCn;
	diag.URL = "departmentAction!queryProvinceCity.action?Parentid="+shipId+"&nameCn="+nameCn;
	diag.Height=400;
	diag.Width=900;
	diag.OKEvent=function(){
		var winContent= diag.innerFrame.contentWindow.document;
		var checked = $(winContent).find("#querytable").contents().find("[name=checks]:checked");
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
				$("#"+hiddenSignId+"").val(checked.val());
				$("#"+SignId+"").val(checked.attr("lable"));
			}
			diag.close();
		};
	diag.show();
}
function chooseShip(tableId,showDomId,nameCn,gxId,pkId){
  	var pkVal ="";
	if(gxId!=""){
		var dom = $("[targetId='"+gxId+"']");
		var pkVal = dom.val();
		if(pkVal==""){
			top.Dialog.alert("请先选择"+dom.attr("lable"));
			return false;
		}
	}
	var diag = new top.Dialog();
	diag.ID="choose";
	diag.Title = "选择"+nameCn;
	diag.URL = "shipAction!init.action?shipId="+tableId+"&pkVal="+pkVal+"&pkId="+pkId;
	diag.Height=400;
	diag.Width=500;
	diag.OKEvent=function(){
		var treenode= diag.innerFrame.contentWindow.getTreeNode();
		if(!treenode){
			top.Dialog.alert("请至少选择一项");	
			return false;
		}
		$("#"+showDomId+"").val(treenode.id);
		$("#"+showDomId+"_lable").val(treenode.text);
		diag.close();
	};
	diag.show();
}
</script>

</head>
<body>
<div class="static_box1">
<div style="padding: 0 20px 0 20px;">
	
	<s:form action="admin/departmentAction!editCompany.action" method="post" theme="simple" id="frm">
	<input type="hidden" name="message" id="message" value="${message}" /> 
	<input type="hidden" name="successflag" id="successflag" value="${successflag}" />
	<input type="hidden" name="company.id" id="companyId" value="${company.id}" />  
	<fieldset> 
			<legend>企业基本信息</legend>
	<table width="100%" class="tableStyle" transmode="true">
	<tr>
		<td width="40%">企业名称：</td>
		<td>
			<input type="text" name="company.name" id="companyName"  class="validate[required,maxSize[20]]" data-prompt-position="centerRight:-20" value="${company.name }" />
			<span class="star"> *</span>
		</td>
	</tr>
	<!-- <tr>
		<td>公司Logo：</td>
		<td>
		<div id="upfile">
			<input type="hidden" id="image" name="company.logo"></input>
			<input type="file" accept="image/*" class="fileinput"  id="upload_image"/>
			<span id="size_image">
				<input type="hidden" name="bg_width" id="bg_width_image"></input>
				<input type="hidden" name="bg_height" id="bg_height_image"></input>
				<input type="hidden" name="s_width" id="s_width_image"></input>
				<input type="hidden" name="s_height" id="s_height_image"></input>
			</span>
			<input type="button" class="button" style="width: 60px;" value="上传" onclick="upload('image')" />
			<span class="star"> *</span>
		</div>
		</td>
	</tr> -->
	<%-- <tr>
		<td >
		<td >
			<div id="image_view" >
				<img src="${ctxAdmin}/image/welcome.png"  width="50px" height="50px"/>
			</div>
		</td>
	</tr> --%>
	<%-- <tr>
		<td>行政区域：</td>
		<td>
			<div style="float: left;">
				<input type="text" name="company_province" id="company_province" value="${regionProvince }" class="validate[required]" class="textinput simple" readonly="readonly"/>
				<input type="hidden" name="company.province" id="companyProvince"  value="${company.province }" class="textinput simple"/>
			</div>
			<span class="star" style="float: left;">*</span>
			<span class="icon_find" onclick="queryProvince('0','company_province','companyProvince','省')">&nbsp</span>
		</td>
	</tr> --%>
	<tr class="validate" style="border: medium none; background-color: transparent;" yzid="3">
		<td style="text-align: right; padding-top: 3px; padding-bottom: 3px; border: medium none;">行政区域：</td>
			<td style="padding-top: 3px; padding-bottom: 3px; border: medium none;">
			<div style="float: left;">
				<s:if test="#request.regionPro!=null">
					<input class="textinput simple" value="${regionPro.name},${region.name}" id="Parentid_lable" readonly="readonly" type="text">
				</s:if>
				<s:else>
					<input class="textinput simple" value="${region.name}"  id="Parentid_lable" readonly="readonly" type="text">
				</s:else>
				<input id="Parentid" targetid="86" name="company.area" value="${region.id}" not-null="1" lable="行政区域" datatype="1" type="hidden">
				</div><span class="icon_find" onclick="chooseShip('20','Parentid','上级行政区域','','')">&nbsp; </span>
		</td>
	</tr>
	<%-- <tr>
		<td>所在市：</td>
		<td>
			<div style="float: left;">
				<input type="text" name="company_city" id="company_city" value="${regionCity }"  class="validate[required]" class="textinput simple" readonly="readonly"/>
				<input type="hidden" name="company.city" id="companyCity" value="${company.city }" class="textinput simple"/>
			</div>
			<span class="star" style="float: left;">*</span>
			<span class="icon_find" onclick="queryProvince('1','company_city','companyCity','市')">&nbsp</span>
		</td>
	</tr> --%>
	<tr>
		<td>地址：</td>
		<td>
			<input type="text" name="company.address" id="companyAddress" value="${company.address }" class="validate[required,maxSize[50]]"/>
			<span class="star"> *</span>
		</td>
	</tr>
	<tr>
		<td>联系人：</td>
		<td>
			<input type="text" name="company.contact" id="companyContact" value="${company.contact }" class="validate[required,maxSize[15]]"/>
			<span class="star"> *</span>
		</td>
	</tr>
	<tr>
		<td>联系电话：</td>
		<td>
			<input type="text" name="company.tel" id="companyTel"  value="${company.tel }" class="validate[required,custom[mobilephone]]"/>
			<span class="star"> *</span>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		</td>
	</tr>
	<!-- <tr>
           <td colspan="2">
           <p>
           <button onclick="return doSubmit();" type="button" id="preserve">保 存 </button>
           <button onclick="top.Dialog.close();" type="button">关 闭 </button>
         	</p>
         	</td>
       </tr> -->
	</table>
	</fieldset>
	
	<fieldset> 
			<legend>管理员账号</legend>
	<table width="100%" class="tableStyle" transmode="true">
	<input  type="hidden" name="admin.id" value="${admin.id }" ></input>
		<tr>
						<td width="40%">管理员账号：</td>
						<td>
							<input  type="text" name="admin.account" value="${admin.account }" 
							<s:if test="#request.admin.account != null"> style="width: 118px; height: 100%" disabled</s:if>
							<s:else>class="textinput" </s:else> ></input>
							<span class="star"> *</span>
						</td>
					</tr>
					<tr >
						<td style="">管理员密码 ：</td>
						<td>
							<input  type="password" name="admin.pwd" value="" class="textinput"
							<s:if test="#request.admin.pwd != null"> placeholder="如果不填则不修改"</s:if> ></input>
							<s:if test="#request.admin.pwd == null"><span class="star"> *</span></s:if>
						</td>
					</tr>
					<tr>
			      			<td colspan="2">
			      			</td>
			   		</tr>
		
	</table>
	</fieldset>
	</s:form>
</div>

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




