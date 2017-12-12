<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctxAdmin}/js/jquery.tmpl.min.js"></script>
<script type="application/javascript" src="${ctxAdmin}/js/jstree/jstree.js?v=4.0"></script>
<link rel="stylesheet" href="${ctxAdmin}/js/jstree/themes/default/style.min.css" />
<script type="application/javascript" src="${ctxAdmin}/js/jstree/overmenu/jstree.plugin.overmenu.min.js"></script>
<link rel="stylesheet" href="${ctxAdmin}/js/jstree/overmenu/overmenu.min.css" />
<title>数据表</title>
</head>
<body>
<div class="box2" panelTitle="员工信息" roller="false">
 <form action="${ctx}/admin/accout_setting!queryEmployee.action" method="post" target="querytable" id="frm">
 	<input type="hidden" id="employeeId" value="" name="employeeId"/>
 	<input type="hidden" id="departmentId" value="0" name="departmentId"/>
 	<input type="hidden" id="orgtree" value='${orgtree }'/>
 	<input type="hidden" name="currPage" id="currPage" value="${currpage}" /> 
 	<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}" />
 	<input type="hidden" name="Parentid" id="Parentid" value="${Parentid}" />
 	<input type="hidden" id="type" name="type" value="${type }"/>
 	<table>
			<tr>
				<td>员工号：</td>
				<td><input type="text" name="employeeSerialNo" class="validate[length[0,15]]"/></td>
				<td>姓名：</td>
				<td><input type="text" name="employeeName" class="validate[length[0,15]]"/></td>
				<td>手机号码：</td>
				<td><input type="text" name="employeePhoneNum" class="validate[custom[mobilephone]]"/></td>
			</tr>
			<tr>
				<td>身份证号：</td>
				<td><input type="text" name="employeeIdcard" class="validate[required,funcCall[regIdCard]]"/></td>
				<td colspan="4">
					<button type="submit" ><span class="icon_find">查询</span></button>
				</td>
			</tr>
		</table>
 </form>
</div>
<div id="scrollContent">
	<table style="width: 100%;">
		<tr>
			<td style="width: 100%;" valign="top">
				<div style="overflow: auto;height: 100%;">
				<iframe scrolling="no" width="100%" frameBorder=0 id="querytable"
						name="querytable"
						src="${ctx}/admin/accout_setting!queryEmployee.action?type="+${type }
						onload="iframeHeight('querytable')" allowTransparency="true">
				</iframe>
				</div>
			</td>
		</tr>
	</table>
</div>
<pt:page action="${ctx}/admin/accout_setting!queryEmployee.action" target="querytable"></pt:page>
</body>
<script >

</script>
</html>





