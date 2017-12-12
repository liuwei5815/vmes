<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/tags/core" prefix="c"%> 
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request" /> 
<%@page import="java.io.StringWriter"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="org.apache.log4j.Logger"%>

<%@page import="com.opensymphony.xwork2.util.ValueStack"%>

<%@page import="com.xy.cms.common.CommonFunction"%>
<html xmlns='http://www.w3.org/1999/xhtml'>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<link href="${ctx}/css/errorPage.css" rel="stylesheet" type="text/css" />
<title>500</title>
</head>
<script type="text/javascript">
    var n=0;
    function report2Manager(){
       var msg=$(".info:eq(0) pre:eq(0)").text();
       if(n<1){
         $.ajax({
           url:"${ctx}/sendMail!sendEmail.action?msg="+msg,
           type:"post",
           dataType:"json",
           success:function(data){
             if(data.code==1){
               n++;
               top.Dialog.alert("已报告给管理员");
             }else{
               top.Dialog.alert("服务器异常，请稍后再发送");
             }
           }
       });
       } 
       if(n>=1){
         top.Dialog.alert("已经报告给管理员了，请不要重复操作");
       }
    }
</script>
<body>
<!--start-wrap--->
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
		//e = exception;
	}

	//Exception from JSP didn't log yet ,should log it here.
	String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
	Logger logger = Logger.getLogger(this.getClass());
	logger.error(e.getMessage(),e);
	StringWriter  sw = new StringWriter();
	
%>
		<div class="wrap">

			<!--start-content------>
			<div class="content">
				<img src="${ctx }/images/error-img2.png" title="error" />
				<div style="margin: 36px 0px 32px 0px;">
                <a href="javascript:void(0)" style="margin-right:100px;" onclick="report2Manager()">报告管理员</a>
                <a href="#">返回首页</a>
                </div>
              <div class="info">
                <pre>
 			
 				  
 				   <%=CommonFunction.getExceptionStackTrace(e) %>
                </pre>
                </div>
   			</div>
			<!--End-Cotent------>
		</div>
		<!--End-wrap--->


</body>
</html>