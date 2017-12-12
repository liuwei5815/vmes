<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<%@taglib prefix="xtree" uri="/WEB-INF/tld/tree.tld"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx }/admin/frame/js/form/vchecks.js?v=13.4"></script>
<script type="text/javascript" src="${ctx }/admin/js/common/common.js?v=5.0"></script>
<title>终端角色权限设置</title>

<script>
function savePower(){
	var appRoleId =$("#appRoleId").val();

	var appModular=$(".appModular:checked");
	var appModularVal = new Array();
	appModular.each(function(){
		
		appModularVal.push($(this).val());
	});
	$.xyAjax({
		url:'${ctx}/admin/appRolePower!savePower.action',
		type:"post",
		traditional:true,
		data:{"id":appRoleId,"appModularIds":appModularVal},
		suc:function(){
			top.Dialog.alert("保存成功",function(){
				top.Dialog.close();
			});	
		}
	});

}
$(function(){
	$(".aa").vchecks();
})

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
							<input type="hidden" id="appRoleId" value="${appRole.id }"/>
							<table width="100%" class="tableStyle" transmode="true">
								<tr>
									<td>角色名称：</td>
									<td>${appRole.name }</td>
								</tr>
								<tr>
									<td valign="top">终端模块</td>
									<td>
										<ul class="aa" style="width: 100px;">
											<s:iterator value="@com.xy.cms.common.CacheFun@getAppModular()" var="appModular">
										
												<li><input class="appModular"  value="${appModular.id }" 	 	
												<s:if test="#request.powerModular.contains(#appModular.id)">checked</s:if> type="checkbox"/><span>${appModular.name }</span></li>
											</s:iterator>
										</ul>
									</td>
								</tr>
								<tr>
									<td colspan="2"><p>
											<button onclick="return savePower();" type="button">保
												存</button>
											<button onclick="top.Dialog.close();" type="button">关
												闭</button>
										</p></td>
								</tr>
							</table>

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





