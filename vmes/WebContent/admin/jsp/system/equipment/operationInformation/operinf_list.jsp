<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息列表</title>
<%@ include file="/admin/jsp/common/common.jsp"%>
<script type='text/javascript' src="${ctx }/admin/js/page.js"></script>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="${ctxAdmin}/js/jquery.tmpl.min.js"></script>
<script type="application/javascript" src="${ctxAdmin}/js/jstree/jstree.js?v=4.0"></script>
<link rel="stylesheet" href="${ctxAdmin}/js/jstree/themes/default/style.min.css" />
<script type="application/javascript" src="${ctxAdmin}/js/jstree/overmenu/jstree.plugin.overmenu.min.js"></script>
<link rel="stylesheet" href="${ctxAdmin}/js/jstree/overmenu/overmenu.min.css" />
<script src="${ctxAdmin}/js/third-party/highcharts/highcharts.js"></script>
<style>
.tableStyle tr th,td{
 height:24px;
 text-align: center;
 white-space: nowrap;
 word-wrap: break-word;
 overflow: hidden;
}
</style>
<script>

function doSubmit(){
	$("#frm").submit();	
}
$(document).ready(function(){ 
	loadPage();
	$(".img_add2").click(function(){
		
		var height = $(document).height();
		window.parent.iframeHeight('querytable');
	}) 
});

//查看设备当天的运行情况
function queryRecord(id){
	var dia = new top.Dialog();
	dia.Title = "设备24小时运行数据";
	//饼状图
	dia.URL = "${ctx}/admin/equipment!queryRecordInit.action?equipmentId="+id;
	dia.ID = "sbyxlyls";
	dia.CancelButtonText = "&nbsp;关 &nbsp;&nbsp;&nbsp;&nbsp;闭&nbsp;";
	dia.Height = 425;
	dia.Width = 700;
	dia.CancelEvent = function() {
		dia.close();
	};
	dia.show();
}

var chart = null;
function showVideo(equmentId,date){
	var sign = "container_"+equmentId;
	$.ajax({
	     type: "POST",
	     url: "${ctx}/admin/equipment!oper_dialog.action",
	     cache: false,
	     dataType:"json",
	     data:{
	    	 "equmentId":equmentId,
	    	 "date":date
	     },
	     success: function(data){
	    	//加工数量
	    		var number = data.number;
	    		var text = {text:'今日加工件数</br>'+number+'个'};
	    		var boot = data.boot;
	    		var valueBoot = boot.split("-");
	    		var dataBoot = new Array();
	    		for(var i=0;i<valueBoot.length;i++){
	    			console.log()
	    			if(i%2==0){//偶数
	    				dataBoot[i]= {name:'关机时间',y:parseFloat(valueBoot[i]),color:'white'};
	    			}else{
	    				dataBoot[i]={name:'开机时间',y:parseFloat(valueBoot[i]),color:'skyblue'};
	    			}
	    		};	
	    		var work = data.work;
	    		var valueWork = work.split("-");
	    		var dataWork = new Array();
	    		for(var i=0;i<valueWork.length;i++){
	    			console.log()
	    			if(i%2==0){//偶数
	    				dataWork[i]= {name:'其他时间',y:parseFloat(valueWork[i]),color:'white'};
	    			}else{
	    				dataWork[i]={name:'工作时间',y:parseFloat(valueWork[i]),color:'peachpuff'};
	    			}
	    		};
	    	    $('#'+sign+'').highcharts({
	    	    	credits: {  
	    	            enabled:false  
	    	  		}, 
	    	        chart: {
	    	            plotBackgroundColor: null,
	    	            plotBorderWidth: null,
	    	            plotShadow: false,
	    	            //spacing : [100, 0 , 40, 0],
	    	            plotBackgroundImage: '${ctxAdmin}/image/temp_back.png'
	    	        },
	    	        title:text,
	    	        tooltip: {
	    	           pointFormat: '{series.name}: <b>{point.y}%</b>'
	    	        },
	    	        plotOptions: {
	    	            pie: {
	    	                allowPointSelect: false,
	    	                cursor: 'pointer',
	    	                dataLabels: {
	    	                    enabled: false,
	    	                    //format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	    	                    style: {
	    	                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	    	                    }
	    	                }
	    	            }
	    	        },
	    	        series: [{
	    	        	type: 'pie',
	    	            size: '80%',
	    	            name: '占比',
	    	            innerSize: '70%',
	    	            data:dataBoot,
	    	            	/*  [
	    	                   {name:'开机时间',y:40,color:'red'},
	    	                   {name:'关机时间',y:60,color:'black'}
	    	                 ] */
	    	                 
	    	               },
	    	            {type: 'pie',
	    	            size: '50%',
	    	            name: '占比',
	    	            innerSize: '40%',
	    	            data:dataWork
	    	            	/* [
	    	                   {name:'工作时间',y:25,color:'yellow'},
	    	                   {name:'其他时间',y:5,color:'white'},
	    	                   {name:'工作时间',y:8,color:'yellow'},
	    	                   {name:'其他时间',y:20,color:'white'},
	    	                   {name:'工作时间',y:20,color:'yellow'},
	    	                   {name:'其他时间',y:22,color:'white'}
	    	                   ] */
	    	        }]
	    	    }, function(c) {
	    	        // 环形图圆心
	    	        var centerY = c.series[0].center[1],
	    	            titleHeight = parseInt(c.title.styles.fontSize);
	    	        c.setTitle({
	    	            y:centerY + titleHeight/2
	    	        });
	    	        chart = c;
	    	    });
	     }
	});	
}
</script>
</head>
<body>
<!-- <div id="scrollContent" class="border_gray"> -->
<form action="${ctx}/admin/equipment!query.action" method="post" target="querytable" id="frm">
<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" />
<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" />
<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
<table class=""  style="align:center;width:100%" >
	<tr>
		<c:if test="${fn:length(list) == 0}">
			<td colspan="3" align="center" >没有找到符合条件的设备</td>
		</c:if>
		<s:iterator value="list" status="st" var="cell">
		<c:if test="${st.index%2==0}"> 
			<tr>
			</tr>
		</c:if>
		<td width="50%">
			<table class="tableStyle">
				<tr>
					<td  style="text-align: left;width: 35%">名称：${cell.name }</td>
					<td  style="text-align: left;width: 30%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;工作小时：</td>
					<td width="35%" rowspan="8" align="left">
					<%-- <img src="${ctx}/admin/image/temp133.png" style="width: 100px;height: 100px" onclick="yxSearch('${cell.equmentId}')" /> --%>
						<div id="container_${cell.equmentId }" style="width: 100%;height: 100%;">
							<script>
      								$(function(){
    	  								showVideo('${cell.equmentId }','${beginDate}');
     						 		})
							</script>
						</div>
					</td>
				</tr>
				<tr >
					<td style="text-align: left;width: 30%">型号：${cell.model }</td>
					<td style="text-align: left;width: 30%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${runtime/3600 }小时</td>
				</tr>
				<tr align="left">
					<td width="30%" rowspan="6" align="left">
					<img onclick="return queryRecord('${cell.equmentId}')" <c:if test="${ empty cell.image }">src="${ctx}/admin/image/Screenshot-3.png"</c:if><c:if test="${not empty cell.image }">src="${rfile }/${cell.image}"</c:if> style="width: 195px;height: 150px"/>
					</td>
					<td width="30%" style="text-align: left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;设备上电时间：</td>
				</tr>
				<tr>
					<td width="30%" style="text-align: left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${electrifytime/30 }分钟</td>
				</tr>
				<tr >
					<td width="30%" style="text-align: left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最后采集时间：</td>
				</tr>
				<tr >
					<td width="30%" style="text-align: left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${receivetime}" pattern="hh:mm:ss" /></td>
				</tr>
				<tr >
					<td width="30%" style="text-align: left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最后开机日：</td>
				</tr>
				<tr >
					<td width="30%" style="text-align: left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${receivetime}" pattern="MM-dd" /></td>
				</tr>
			</table>
		</td>
		<c:if test="${fn:length(list) == 1}"> 
			<td width="30%">
			</td>
		</c:if>
		</s:iterator>
	</tr>
</table>



</form>
</body>
</html>