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
<script src="${ctxAdmin}/js/third-party/highcharts/highcharts.js"></script>
<title>设备产能利用率</title>
<script >

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
	
	<s:form action="" method="post" theme="simple" id="frm">
	<input type="hidden" name="message" id="message" value="${message}" />
	<input type="hidden" name="capacity" id="capacity" value="<c:if test="${not empty capacity }">${capacity }</c:if><c:if test="${ empty capacity }">0</c:if>"/>
	<input type="hidden" name="idling" id="idling" value="<c:if test="${not empty idling }">${idling }</c:if><c:if test="${ empty idling }">0</c:if> "/>
	<input type="hidden" name="fault" id="fault" value="<c:if test="${not empty fault }">${fault }</c:if><c:if test="${ empty fault }">0</c:if> "/>
	<table width="100%" class="tableStyle" transmode="true">
	 	<div id="container" style="width: 750px; height: 550px; margin: 0 auto"></div>
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
<script language="JavaScript">
$(document).ready(function() {  
	   var capacity=$("#capacity").val();
	   var idling=$("#idling").val();
	   var fault=$("#fault").val();
	   var credits = {  
           enabled:false  
 		};
	   var chart = {
	       plotBackgroundColor: null,
	       plotBorderWidth: null,
	       plotShadow: false
	   };
	   var title = {
	      text: '设备产能利用率'   
	   };      
	   var tooltip = {
	      pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	   };
	   var plotOptions = {
	      pie: {
	         allowPointSelect: true,
	         cursor: 'pointer',
	         dataLabels: {
	            enabled: true,
	            format: '<b>{point.name}%</b>: {point.percentage:.1f} %',
	            style: {
	               color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	            }
	         }
	      }
	   };
	   var labels = {
			      items: [{
			            style: {
			               left: '50px',
			               top: '18px',
			               color: (Highcharts.theme && Highcharts.theme.textColor) || 'black'
			            }
			      }]
			   };
	   var colors=['#8B2323','#A2CD5A','#B23AEE','#87CEEB'];
	   var series= [{
	      type: 'pie',
	      name: '占比',
	      data: [
	         ['产能利用率',  parseInt(capacity)],
	         ['空载率',  parseInt(idling)],
	         ['故障率',  parseInt(fault)],
	         ['其他', 100-parseInt(capacity+idling+fault)]
	      ]
	   }];     
	      
	   var json = {};   
	   json.credits = credits;
	   json.chart = chart; 
	   json.title = title;     
	   json.tooltip = tooltip;  
	   json.series = series;
	   json.plotOptions = plotOptions;
	   json.colors=colors;
	   $('#container').highcharts(json);  
	});
</script>


</html>





