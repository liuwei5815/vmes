<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<script type='text/javascript' src="${pageContext.request.contextPath}/admin/frame/js/jquery-1.4.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script>
	$(function(){
		var w_parent=window.parent;
		if("${code}"=="1"){
	
			w_parent.uploadSuccess("${fileName}");
		}
		else{
			w_parent.uploadError("${message}");
		}
	})
</script>
</head>
<body>
	
</body>
</html>




