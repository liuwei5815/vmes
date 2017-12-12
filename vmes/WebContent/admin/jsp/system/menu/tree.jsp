<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<%@taglib prefix="xtree" uri="/WEB-INF/tld/tree.tld"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
<script language="javascript" src="${ctx}/admin/js/xtree.js"></script>
<script language="javascript" src="${ctx}/admin/js/map.js"></script>
<script language="javascript" src="${ctx}/admin/js/checkboxTreeItem.js"></script>
<link href="${ctx}/admin/css/xtree.css" rel="stylesheet" type="text/css"></link>
<script type="">
function action(parentId){
	window.parent.treeClick(parentId);
}
</script>
</head>
<body>
	<div id="scrollContent">
		<table width="100%" border="0">
		<tr>
		<td align="left" valign="top" width="20%">
			<xtree:tree key="tree1" rootName="菜单管理" list="listTree" 
						imagePath="/admin/image/tree/" action="action('0');" />				
		</td>
		</tr>
		</table>
	</div>
</body>
</html>

