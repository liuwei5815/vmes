<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ page import="org.apache.commons.logging.LogFactory"%>
<%@ page import="com.xy.cms.common.CommonFunction"%>
<%@page import="com.opensymphony.xwork2.util.ValueStack"%>
<html>
<%@ include file="/admin/jsp/common/common.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>Error Page</title>
<script language="javascript">
        function showDetail()
        {
            var elm = document.getElementById('detail_system_error_msg');
            if(elm.style.display == '') {
                elm.style.display = 'none';
            }else {
                elm.style.display = '';
            }
        }
    </script>
</head>

<body>

<div id="scrollContent">
<%
	Throwable e;
	try {
		ValueStack vs = (ValueStack) request
				.getAttribute("struts.valueStack");
		e = (Exception) vs.findValue("exception");
	} catch (Exception ex) {
		e = pageContext.getException();
	}
	if (e == null) {
		e = exception;
	}
	if (e != null) {
		//Exception from JSP didn't log yet ,should log it here.
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		LogFactory.getLog(requestUri).error(e.getMessage(), e);
	}
%>
<div class="msg_icon2"></div>
<h3>对不起,发生系统内部错误,不能处理你的请求</h3>
<button onclick="history.back();">返回</button>
<br/>
<b>错误信息:</b> <%=e.getMessage()%> <br>
<br>
<div class="box2" panelTitle="详细错误信息" >
<pre>
<%=CommonFunction.getExceptionStackTrace(e)%>
</pre>
</div>
</div>

</body>
</html>

