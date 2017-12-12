<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"
	scope="request" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/admin/frame/js/jquery-1.4.js"></script>
<script type="text/javascript" src="${ctx}/admin/frame/js/framework.js"></script>
<link href="${ctx}/admin/frame/css/import_basic.css" rel="stylesheet"
	type="text/css" prePath="${ctx}/admin/frame/" />
<link rel="stylesheet" type="text/css" id="skin"
	prePath="${ctx}/admin/frame/" />
<!--框架必需end-->
<script type="text/javascript" src="${ctx}/admin/frame/js/nav/ddaccordion.js"></script>
<script type="text/javascript" src="${ctx}/admin/frame/js/text/text-overflow.js"></script>
<style>
a {
	behavior: url(${ctx}/admin/frame/js/method/focus.htc)
}

.categoryitems span {
	width: 160px;
}
</style>

<script>
	//打开内页时出现进度条
	$(function() {
		$(".categoryitems a[target*=frmright]").click(function() {
			showProgressBar();
		});
	});
</script>
</head>
<body leftFrame="true">
<div id="scrollContent">
<div class="arrowlistmenu">
<c:forEach items="${treeList}" var="item">
				<div class="menuheader expandable">
					<span class="${item.icon}"></span>${item.name}
				</div>
				<ul class="categoryitems">
					<c:forEach items="${item.childNode}" var="node">
						<c:if test="${ node.supiorId == item.id}">
							<li>
							  <a href="${ctx}/admin/${node.url}" target="frmright" >
								<span style="margin-left: 20px;" class="text_slice">${node.name}</span>
							  </a>
						    </li>
						</c:if>
					</c:forEach>
			    </ul>
			</c:forEach>
			</div>
</div>				
</body>
</html>