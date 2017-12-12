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
<title>数据表</title>
<script >

</script>

</head>
<body>
<input type="hidden" name="dateList" id="dateList" value="${dateList} "/>
<input type="hidden" name="listLength" id="listLength" value="${list.size()} "/>
<c:forEach items="${list}" var="cell" varStatus="st">
	<c:forEach items="${cell}" var="map" varStatus="sta">
		<input type="hidden" equipment="${map.key }" name="fault_${st.index }" id="fault_${st.index }" value="${map.value } "/>
	</c:forEach>
</c:forEach> 
<div id="container" style="width: 750px; height: 550px; margin: 0 auto">
 
</div>
<script language="JavaScript">
$(document).ready(function() { 
	var listLength = $("#listLength").val();
	var record = new Array();
	var sun = listLength;
	for(var i=0;i<listLength;i++){
		var value = $("#fault_"+i+"").val().split("-");
		var data = new Array();
		for(var j=0;j<value.length;j++){
			data[j]= parseFloat(value[j]);
		}
		var obj1={"type": 'column',"name": $("#fault_"+i+"").attr("equipment"),"data": data};
		var obj2={"type": 'spline',"name": $("#fault_"+i+"").attr("equipment"),"data": data};
		record[i]=obj1;
		record[parseInt(sun)]=obj2;
		sun++;
	}
	//时间
	var dateList = $("#dateList").val().split("##");
	var describe = dateList[0]+"至"+dateList[dateList.length-1]+"设备产能利用率";
	var dateCategories = new Array();
	for(var i=0;i<dateList.length;i++){
		dateCategories[i]= dateList[i];
	}
	var credits = {  
	           enabled:false  
	};
   var title = {
      text: describe   
   };
   var xAxis = {
      categories: dateCategories,
      labels: {
          rotation: -45,
          style: {
              fontSize: '13px',
              fontFamily: 'Verdana, sans-serif'
          }
      }
   };
   var yAxis = {
       title: {
           text: '产能利用率占比'
       },
       labels: {
           formatter: function () {
               return this.value + '%';
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
  var series= record;      
      
   var json = {}; 
   json.credits = credits;
   json.title = title;   
   json.xAxis = xAxis;
   json.yAxis = yAxis;
   json.labels = labels;  
   json.series = series;
   json.colors=colors;
   $('#container').highcharts(json);  
});
</script>
</body>


</html>





