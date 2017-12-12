<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/admin/jsp/common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加员工</title>
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
		top.Dialog.alert("手机号码为空");
		return;
	}
	if ($('#employeeIdcard').val == "") {
		top.Dialog.alert("身份证号为空");
		return;
	}
	var regPhoneNum = /^1[3458]\d{9}$/;
	if (!regPhoneNum.test($('#employeePhoneNum').val())) {
		top.Dialog.alert("手机号码格式不正确");
		return;
	}
	//验证身份证号码
	 var regIdCard=/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
	 if(!regIdCard.test($("#employeeIdcard").val())) { 
		 	top.Dialog.alert("请输入正确的身份证号码！");
			return false; 
	}
	//验证员工号是否唯一
	var employeeSerialNo = $("#employeeSerialNo").val();
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
	/* if($('#userAccount').is(':checked') && $('#appUserPwd').val() == ""){
		top.Dialog.alert("请输入密码");
		return;
	}
	if($('#adminAccount').is(':checked') && $('#adminUserPwd').val() == ""){
		top.Dialog.alert("请输入密码");
		return;
	} */
}

function cancel(){
	top.Dialog.close();
}

function unload(){
    if($("#successflag").val() == "1"){//执行成功
    	top.Dialog.alert("保存成功",function(){
    		this.parent.frmright.doSubmit();
    		cancel();
    	});
	}else{
		if(document.forms[0].message.value !=""){
			top.Dialog.alert(document.forms[0].message.value);
	    	document.forms[0].message.value = "";	   
	    }
	}
}

$(document).ready(function(){ 
	unload();
	
	 $('#adminAccount').on('click',function(e){
		 if($("#webNum").attr("v")<1){
			top.Dialog.alert("剩余可创建账号数量为0");
			return false;
		}
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
		 if($("#appNum").attr("v")<1){
			top.Dialog.alert("剩余可创建终端账号数量为0");
			return false;
		}
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

/* $(function(){
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
}); */

</script>

</head>
<body>
<div id="scrollContent">
	<div class="box1">
		<s:form action="admin/departmentAction!addEmployee.action" method="post" theme="simple" id="frm">
			<fieldset> 
			<legend>用户基本信息</legend>
			<input type="hidden" name="message" id="message" value="${message}" /> 
			<input type="hidden" name="successflag" id="successflag" value="${successflag}" /> 
			<input type="hidden" name="departmentId" value="${departmentId }" />
			<table width="100%" class="tableStyle" transmode="true">
			<tr >
				<td width="40%">员工号：</td>
				<td>
					<input type="text" name="employee.serialNo" id="employeeSerialNo" class="validate[required,maxSize[15]]" value="" class="textinput default hid" data-prompt-position="centerRight:-20"></input>
					<span class="star"> *</span>
				</td>
			</tr>
			<tr >
				<td>员工名称：</td>
				<td>
					<input type="text" name="employee.name" id="employeeName" class="validate[required,maxSize[15]]" value="" class="textinput default hid" data-prompt-position="centerRight:-20"></input>
					<span class="star"> *</span>
				</td>
			</tr>
			<tr >
				<td>出生年月：</td>
				<td>
					<input type="text" name="employee.birthday" id="employeeBirthday" value="" class="date hid" datefmt="yyyy-MM"/>
					<span class="star"> *</span>
				</td>
			</tr>
			<tr >
				<td>性别：</td>
				<td>
					<label for="radio-1">男</label>
					<input type="radio" id="radio-1" name="employee.gender" value="1" checked = "checked"/>
					<label for="radio-2">女</label>
					<input type="radio" id="radio-2" name="employee.gender" value="2"/>
				<span class="star"> *</span>
				</td>
			</tr>
			<tr >
				<td>手机号码：</td>
				<td>
					<input type="text" name="employee.phoneNum" id="employeePhoneNum" class="validate[required,custom[mobilephone]]" value="" class="textinput" ></input>
					<span class="star"> *</span>
				</td>
			</tr>
			<tr>
				<td>身份证 ：</td>
				<td>
					<input type="text" name="employee.idcard" id="employeeIdcard" class="validate[required,funcCall[regIdCard]]" value="" class="textinput" ></input>
					<span class="star"> *</span>
				</td>
			</tr>
			<tr >
				<td>职位 ：</td>
				<td>
					<input type="text" name="employee.jobtitle" id="employeeJobtitle"  value=""  class="validate[maxSize[15]]" ></input>
				</td>
			</tr>
			<tr>
				<td colspan="2"></td>
			</tr>
			</table>
			</fieldset>
			
			<fieldset> 
			<legend>WEB账号信息</legend>
				<table width="100%" class="tableStyle" transmode="true">
					<tr>
						<td width="40%">是否生成WEB账号：</td>
						<td>
							<input name="adminAccount" id="adminAccount" type="checkbox" value="0"/>
							<c:if test="${ empty license }">
         						<span id="webNum" v="0" style="display: show">剩余可创建账号数量:0个</span>
         					</c:if>
         					<c:if test="${ not empty  license}">
         						<span id="webNum" v="${license.webNum-webNum}" style="display: show">剩余可创建账号数量:${license.webNum-webNum}个</span>
         					</c:if>
							<%-- <span id="webNum" v="${license.webNum-webNum}">剩余可创建Web端账号数量:
							<c:if test="${not empty license.webNum }">${license.webNum-webNum}</c:if>
							<c:if test="${ empty license.webNum }">0</c:if>
							个</span> --%>
						</td>
					</tr>
					<tr  style="display:none;" id="adminRole">
						<td width="40%">WEB账号角色 ：</td>
						<td>
							<select name="admin.roleId"  class="validate[required]" >
								<s:iterator  value="#request.adminRoleList " var="cell" status="item">
				            		<option  value="${cell.id}" >${cell.name}</option>
				            	</s:iterator>
				            </select>
				            <span class="star"> *</span>
						</td>
					</tr>
					<tr style="display:none;" id="adminPwd">
						<td style="">WEB账号密码 ：</td>
						<td>
							<input id="adminUserPwd" type="password" name="admin.pwd" value="" class="textinput default hid" ></input>
						</td>
					</tr>
					<tr>
			      			<td colspan="2">
				       				
				       		</td>
			   		</tr>
				</table>
			</fieldset>
			
			<fieldset> 
			<legend>终端账号信息</legend>
				<table width="100%" class="tableStyle" transmode="true">
					<tr>
						<td width="40%">是否生成终端账号：</td>
						<td>
							<input name="userAccount" id="userAccount" type="checkbox" value="0" />
							<c:if test="${ empty license }">
         						<span id="appNum" v="0" style="display: show">剩余可创建账号数量:0个</span>
         					</c:if>
         					<c:if test="${ not empty  license}">
         						<span id="appNum" v="${license.appNum-appNum}" style="display: show">剩余可创建账号数量:${license.appNum-appNum}个</span>
         					</c:if>
							<%-- <span id="appNum" v="${license.appNum-appNum}">剩余可创建终端账号数量:
							<c:if test="${not empty license.appNum }">${license.appNum-appNum}</c:if>
							<c:if test="${ empty license.appNum }">0</c:if>
							个</span> --%>
						</td>
					</tr>
					<tr style="display:none;" id="userRole">
						<td>终端账号角色 ：</td>
						<td>
							<select name="appUser.roleId"  class="validate[required]" >
								<s:iterator  value="#request.appRoleList " var="cell" status="item">
					           		<option  value="${cell.id}" >${cell.name}</option>
					           	</s:iterator>
					            </select>
						</td>
					</tr>
					<tr style="display:none;" id="userPwd">
						<td>终端账号密码 ：</td>
						<td>
							<input id="appUesrPwd" type="password" name="appUser.password" value=""  class="textinput default hid" ></input>
						</td>
					</tr>
					<tr>
			      			<td colspan="2">
			      			</td>
			   		</tr>
				</table>
			</fieldset>
			
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
		</s:form>
	</div>
</div>
</body> 
</html>




