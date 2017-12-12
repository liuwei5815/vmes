<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@page import="com.xy.cms.common.CacheFun"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"
	scope="request" />
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>物料打印</title>
<script type="application/javascript"
	src="${ctx}/admin/frame/js/jquery-1.8.0.js?v=4.0"></script>
<script type='text/javascript'
	src="${ctx}/admin/js/third-party/printJs/jquery.jqprint-0.3.js"></script>
<!-- 
如果您使用的是高版本jQuery调用下面jQuery迁移辅助插件即可
<script src="http://www.jq22.com/jquery/jquery-migrate-1.2.1.min.js"></script>
-->
<link type='text/css' rel='stylesheet'
	href="${ctx}/admin/css/print/todoprint.css?v=4.8" />

<script language="javascript">
	function printPlan() {
		$("#print").jqprint({
			debug : false, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
			importCSS : true, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
			printContainer : true, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。
			operaSupport : true
		//表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
		});
	}
</script>
<style>
.print {
	padding: 0;
}

.print table {
	table-layout: fixed;
	word-break: break-all;
	word-wrap: break-word;
	border: 0;
	border-collapse: collapse;
	padding: 0;
}

.print table tr {
	padding: 0;
}

.print table tr td {
	border: 0px;
	padding: 0px;
	line-height: 40px;
	height: 40px;
	overflow: hidden;
}

.print .left {
	width: 220px;
	text-align: left;
}
</style>


</head>

<body>
	
	<button class="button" onclick="printPlan()" style="margin-top: 10px";width: 66px; padding-left: 5px;">
		<span class="icon_print" style="cursor: default;">打印</span>
	</button>
	<div>
		<p>提示：打印时，设置合适的尺寸（80mm*50mm）及去掉浏览器页头页尾，推荐使用谷歌浏览器进行打印。</p>
	</div>
	<div id="print" style="width: 400px;">

		<s:iterator value="#request.printList" var="cell">
			<div class="print"
				style="width: 400px; height: 200px; font-size: 20px; page-break-after: always;">
				<table style="font-size: 20px; margin-top: 40px;">
					<tr>
						<td rowspan="4" style="width: 160px; height: 160px;"><img
							src="/qrcode/${cell.materialqrCode }.png" style="width: 160px;" /></td>
						<td class="left">用户物料编号:${cell.usermaterialCode }</td>

					</tr>
					<tr>
						<td class="left">物料名称:${cell.materialName }</td>

					</tr>
					<tr>
						<td class="left">规格/型号:${cell.materialSpec }</td>

					</tr>
					<tr>
						<td class="left">系统物料编号:${cell.materialCode }</td>

					</tr>
				</table>
			</div>
		</s:iterator>
	</div>




</body>
</html>
