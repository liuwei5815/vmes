<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/admin/jsp/common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=eYf9sA6yVTFHlh9ytU4a0EYY"></script>
<script type="application/javascript" src="${ctxAdmin}/js/jstree/jstree.js?v=4.0"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑员工信息</title>
<script src="${ctx}/admin/frame/js/jquery.validationEngine.js?v=4.3" type="text/javascript"></script>
<script src="${ctx}/admin/frame/js/jquery.validationEngine-zh_CN.js?v=4.3" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctxAdmin}/js/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctxAdmin}/js/ueditor.all.min.js"> </script>
<script type="text/javascript">
function doSubmit(){
	if ( $('#employeeSerialNo').val() == "") {
		top.Dialog.alert("员工号为空");
		return;
	}
	if ($('#employeeName').val() == "") {
		top.Dialog.alert("员工姓名为空");
		return;
	}
	if ($('#employeeBirthday').val() == "") {
		top.Dialog.alert("出生日期为空");
		return;
	}
	if ($('#employeePhoneNum').val == "") {
		top.Dialog.alert("手机号为空");
		return;
	}
	if ($('#employeeIdcard').val == "") {
		top.Dialog.alert("身份证号为空");
		return;
	}
	var regPhoneNum = /^1[0-9]{10}$/;
	if (!regPhoneNum.test($('#employeePhoneNum').val())) {
		top.Dialog.alert("手机号码格式不正确");
		return;
	}
	//验证身份证号码
	 var regIdCard=/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
	 if(!regIdCard.test($("#employeeIdcard").val())) { 
		 	top.Dialog.alert("请输入正确的身份证号码！");
			return; 
	}
	var yuangEmployeeSerialNo = $("#yuangEmployeeSerialNo").val();
	var employeeSerialNo = $("#employeeSerialNo").val();
	if(yuangEmployeeSerialNo != employeeSerialNo){
		provingEmployeeSerialNo(employeeSerialNo);
	}else{
		$("#frm").submit();
	}
}

function provingEmployeeSerialNo(employeeSerialNo){
	$.ajax({
	     type: "POST",
	     url: "${ctx}/admin/departmentAction!provingEmployeeSerialNo.action",
	     cache: false,
	     dataType:"json",
	     data:{"employeeSerialNo":employeeSerialNo},
	     success: function(data){
  			if(data.successflag=="1"){
  				$("#frm").submit();
	     	}
  			else{
  				top.Dialog.alert("员工号已经存在");
  			}
	     }
	});	
}

function doSubmitAdmin(){
	console.log($('#adminAccount').val());
	 if(!$('#adminAccount').is(':checked')){
		top.Dialog.confirm("是否取消web账号?",function(){
			$("#frmAdmin").submit();	
		})
	}else{
		$("#frmAdmin").submit();	
	}
}

function doSubmitAppUser(){
	if(!$('#userAccount').is(':checked')){
		top.Dialog.confirm("是否取消终端账号?",function(){
			$("#frmAppUser").submit();	
		})
	}else{
		$("#frmAppUser").submit();	
	}
}

function cancel(){
	top.Dialog.close();
}

function unload(){
    if(document.frm.successflag.value == "1"){//执行成功
    	top.Dialog.alert("保存成功",function(){
    		this.parent.frmright.doSubmit();
    		cancel();
    	});
    }else{
		if(document.forms[0].message.value !=""){
			top.Dialog.alert(document.forms[0].message.value, function() {
				top.Dialog.close();
			});
	    	document.forms[0].message.value = "";
	    }
	}
}

$(document).ready(function(){ 
	unload();
	 $('#adminAccount').on('click',function(e){
		 if ($('#adminAccount').attr('checked')) {
			 	$("#adminAccount").val(1);
			    $("#adminRole").show();
			    $("#adminPwd").show();
			}else{
				$("#adminAccount").val(0);
				$("#adminRole").hide();
				$("#adminPwd").hide();
			}
	    });
	 
	 $('#userAccount').on('click',function(e){
		 if ($('#userAccount').attr('checked')) {
			 	$("#userAccount").val(1);
			    $("#userRole").show();
			    $("#userPwd").show();
			}else{
				$("#userAccount").val(0);
				$("#userRole").hide();
				$("#userPwd").hide();
			}
	    });
});

/*
$(function(){
	 var useNum = ${useNum };
	 var appNum = ${appNum };
	 console.log(useNum); 
	 console.log(appNum); 
	 if (useNum <= 0 && useNum != -127) {
		$('#adminAccount').hide();
		$("#adminRole").hide();
		$("#adminPwd").hide();
		$("#useSave").hide();
	 };
	 if (appNum <= 0 && appNum != -127) {
		$('#userAccount').hide();
		$("#userRole").hide();
		$("#userPwd").hide();
		$("#appSave").hide();
	 };
});
*/

function chooseShip(showDomId){
	var diag = new top.Dialog();
	diag.ID="choose";
	diag.Title = "选择部门";
	diag.URL = "${ctx}/admin/departmentAction!deptTree.action";
	diag.Height=500;
	diag.Width=600;
	diag.OKEvent=function(){
		var treenode= diag.innerFrame.contentWindow.getTreeNode();
		if(!treenode){
			top.Dialog.alert("请至少选择一项");	
			return false;
		}
		if(treenode.parent=='#'){
			top.Dialog.alert("请正确选择类型");
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
	<div id="scrollContent">
		<div class="box1">
			<fieldset> 
				<legend>用户基本信息</legend> 
				<s:form action="admin/departmentAction!editEmployee.action" method="post" theme="simple" id="frm">
					<input type="hidden" name="message" id="message" value="${message}" /> 
					<input type="hidden" name="successflag" id="successflag" value="${successflag}" /> 
					<input type="hidden" name="departmentId" value="${departmentId }" />
					<input type="hidden" name="employee.id" value="${employee.id }" />
					<table width="100%" class="tableStyle" transmode="true">
						<tr >
							<td width="40%">员工号：</td>
							<td>
								<input type="text" name="employee.serialNo" id="employeeSerialNo" class="validate[required,maxSize[15]]" value="${employee.serialNo }" class="textinput default hid" data-prompt-position="centerRight:-20"></input>
								<input type="hidden" id="yuangEmployeeSerialNo" value="${employee.serialNo }"/>
								<span class="star"> *</span>
							</td>
						</tr>
						<tr>
							<td width="40%">员工名称：</td>
							<td>
								<input type="text" name="employee.name" id="employeeName"  value="${employee.name }" class="validate[required,maxSize[10]]" class="textinput default hid" data-prompt-position="centerRight:-20"></input>
								<span class="star"> *</span>
							</td>
						</tr>
						<tr>
							<td>出生年月：</td>
							<td>
								<input type="text" name="employee.birthday" id="employeeBirthday" value="${employee.birthday }" class="date hid" datefmt="yyyy-MM"/>
								<span class="star"> *</span>
							</td>
						</tr>
						<%-- <tr>
							<td>角色：</td>
							<td>
								<select name="employee.rid"  class="validate[required]" >
									<s:iterator  value="#request.adminRoleList " var="cell" status="item">
					            		<option  value="${cell.id}" <s:if test="#request.cell.id==#request.employee.rid"> selected = "selected"</s:if> >${cell.name}</option>
					            	</s:iterator>
					            </select>
					            <span class="star"> *</span>
							</td>
						</tr> --%>
						<tr class="validate" style="border: medium none; background-color: transparent;" yzid="5">
							<td style="text-align: right; padding-top: 3px; padding-bottom: 3px; border: medium none;">所在部门：</td>
							<td style="padding-top: 3px; padding-bottom: 3px; border: medium none;">
							<div style="float: left;">
							<input class="textinput simple" value="${dept }" id="Type_1_lable" readonly="readonly" type="text">
							<input id="Type_1" targetid="85" name="employee.deptId" value="${deptId}" not-null="1" lable="所在部门" datatype="1" type="hidden"></div>
							<span class="icon_find" onclick="chooseShip('Type_1')">&nbsp; </span>
							</td>
						</tr>
						<tr>
							<td>性别：</td>
							<td>
								<label for="radio-1">男</label>
								<input type="radio" id="radio-1" name="employee.gender" value="1" <s:if test="#request.employee.gender==1"> checked = "checked"</s:if>/>
								<label for="radio-2">女</label>
								<input type="radio" id="radio-2" name="employee.gender" value="2" <s:if test="#request.employee.gender==2"> checked = "checked"</s:if>/>
							<span class="star"> *</span>
							</td>
						</tr>
						<tr>
							<td>手机号码：</td>
							<td>
								<input type="text" name="employee.phoneNum" id="employeePhoneNum"  value="${employee.phoneNum }" class="validate[required,custom[phone]]" class="textinput default hid" data-prompt-position="centerRight:-20"></input>
								<span class="star"> *</span>
							</td>
						</tr>
						<tr>
							<td>员工身份证 ：</td>
							<td>
								<input type="text" name="employee.idcard" id="employeeIdcard"  value="${employee.idcard }" class="validate[required,custom[proveIdcard]]" class="textinput default hid" data-prompt-position="centerRight:-20"></input>
								<span class="star"> *</span>
							</td>
						</tr>
						<tr>
							<td>职位 ：</td>
							<td>
								<input type="text" name="employee.jobtitle" id="employeeJobtitle"  value="${employee.jobtitle }" class="textinput default hid" data-prompt-position="centerRight:-20"></input>
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
			</fieldset>
	
	
			<fieldset> 
				<legend>WEB账号信息</legend> 
				<s:form action="admin/departmentAction!editAdmin.action" method="post" theme="simple" id="frmAdmin">
					<input type="hidden" name="message" id="message" value="${message}" /> 
					<input type="hidden" name="successflag" id="successflag" value="${successflag}" /> 
					<input type="hidden" name="departmentId" value="${departmentId }" />
					<input type="hidden" name="employee.id" value="${employee.id }" />
						<table width="100%" class="tableStyle" transmode="true">
							<tr>
								<td width="40%">是否生成WEB账号 ：</td>
								<td>
									<input name="adminAccount" id="adminAccount" type="checkbox" 
									<s:if test="#request.admin != null"> checked  value="1"</s:if>
									<s:else> value="1"</s:else> />
									<c:if test="${ empty license }">
         								<span id="webNum" v="0" style="display: show">剩余可创建Web账号数量:0个</span>
         							</c:if>
         							<c:if test="${ not empty  license}">
         								<span id="webNum" v="${license.webNum-webNum}" style="display: show">剩余可创建Web账号数量:${license.webNum-webNum}个</span>
         							</c:if>
								</td>
							</tr>
							<tr id="adminRole" <s:if test="#request.admin == null ">style="display:none;"</s:if> >
								<td>WEB账号角色 ：</td>
								<td>
									<select name="admin.roleId"  class="validate[required]" >
										<s:iterator  value="#request.adminRoleList " var="cell" status="item">
				            				<option  value="${cell.id}" <s:if test="#request.cell.id==#request.admin.roleId"> selected = "selected"</s:if> >${cell.name}</option>
				            			</s:iterator>
				            		</select>
				            		<span class="star"> *</span>
								</td>
							</tr>
							<tr id="adminPwd" <s:if test="#request.admin == null ">style="display:none;"</s:if> >
								<td>WEB账号密码 ：</td>
								<td>
									<input id="adminUserPwd" type="password" name="admin.pwd"   value="" class="textinput default hid"></input>
								</td>
							</tr>
							<tr>
				       			<td colspan="2">
				       				<p>
				            			<button id="useSave" onclick="return doSubmitAdmin();" type="button"><span class="icon_save">保 存</span> </button>
				            			<button onclick="top.Dialog.close();" type="button"><span class="icon_no">关 闭</span> </button>
				       				</p>
				       			</td>
				    		</tr>
						</table>
					</s:form>
				</fieldset>
	
				<fieldset> 
					<legend>终端账号信息</legend>
					<s:form action="admin/departmentAction!editAppUser.action" method="post" theme="simple" id="frmAppUser">
					<input type="hidden" name="message" id="message" value="${message}" /> 
					<input type="hidden" name="successflag" id="successflag" value="${successflag}" /> 
					<input type="hidden" name="departmentId" value="${departmentId }" />
					<input type="hidden" name="employee.id" value="${employee.id }" />
						<table width="100%" class="tableStyle" transmode="true">
							<tr>
								<td width="40%">是否生成终端账号 ：</td>
								<td>
									<input name="userAccount" id="userAccount" type="checkbox" 
									<s:if test="#request.appUser != null"> checked  value="1"</s:if>
									<s:else> value="1"</s:else> />
									<c:if test="${ empty license }">
         								<span id="appNum" v="0" style="display: show">剩余可创建Web账号数量:0个</span>
         							</c:if>
         							<c:if test="${ not empty  license}">
         								<span id="appNum" v="${license.appNum-appNum}" style="display: show">剩余可创建Web账号数量:${license.appNum-appNum}个</span>
         							</c:if>
								</td>
							</tr>
							
								<tr id="userRole" <s:if test="#request.appUser == null ">style="display:none;"</s:if> />
									<td>终端账号角色 ：</td>
									<td>
										<select name="appUser.roleId"  class="validate[required]" >
											<s:iterator  value="#request.appRoleList " var="cell" status="item">
							            		<option  value="${cell.id}" <s:if test="#request.cell.id==#request.appUser.roleId"> selected = "selected"</s:if> >${cell.name}</option>
							            	</s:iterator>
							        	</select>
							        	<span class="star"> *</span>
									</td>
								</tr>
								<tr id="userPwd" <s:if test="#request.appUser == null ">style="display:none;"</s:if> >
									<td>终端账号密码 ：</td>
									<td>
										<input id="appUserPwd" type="password" name="appUser.password" value=""  class="textinput default hid" ></input>
									</td>
								</tr>
							<tr>
					       		<td colspan="2">
					       			<p>
					            		<button id="appSave" onclick="return doSubmitAppUser();" type="button" ><span class="icon_save">保 存</span></button>
					            		<button onclick="top.Dialog.close();" type="button"><span class="icon_no">关 闭</span> </button>
					       			</p>
					       		</td>
					    	</tr>
						</table>
					</s:form>
				</fieldset>
	
	
		</div>
	</div>
</body>
</html>




