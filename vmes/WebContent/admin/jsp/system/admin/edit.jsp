<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:set var="ctx" value="${request.contextPath}" scope="request" />

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改操作人员信息</title>
<script type="text/javascript">
	jQuery(document).ready(function() {
		unload();
	});

	function unload() {
		if ($('#successflag').val() == "1") {//执行成功
			this.parent.frmright.doQuery();
			top.Dialog.alert($("#message").val());
			top.Dialog.close();
		} else {
			if (document.forms[0].message.value != "") {
				top.Dialog.alert(document.forms[0].message.value);
				document.forms[0].message.value = "";
			}
		}
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
						<s:form action="admin/admin!edit.action" method="post" theme="simple"
							enctype="multipart/form-data">
							<s:hidden name="message" id="message" />
							<s:hidden name="admin.id" id="adminId" />
							<s:hidden name="admin.pwd" id="password"/>
							<s:hidden name="admin.addDate" id="addDate" />
							<input type="hidden" name="successflag" id="successflag"
								value="${successflag}" />
							<table class="tableStyle" transmode="true">
								<tr>
									<td>账号:<span class="star">*</span></td>
									<td><input type="text" id="name" name="admin.account"
										class="validate[required]"
										value="${admin.account}" style="width: 200px;" /></td>
								</tr>
								<tr>
									<td>角色名称:<span class="star">*</span></td>
									<td>
										<select id="sel" name="admin.roleId">
											<option value="">--请选择--</option>
											<s:iterator value="#request.rList" var="item">
											
												
													<option value="${item.id}" <s:if test="%{#item.id==#request.admin.roleId}"> selected="selected" 	</s:if> >${item.name}</option>
											
											</s:iterator>
										</select>
									</td>
								</tr>
								<tr>
									<td colspan="2"><p></p> 
									<input type="submit" value="提交" /> 
									<input type="reset" value="重置" /></td>
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




