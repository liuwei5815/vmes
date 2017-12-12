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
<input type="hidden" id="boot" value=${boot }  />
<input type="hidden" id="work" value=${work }  />
<input type="hidden" id="number" value=${number }  />
<div id="container" style="width: 400px; height: 435px; margin: 0 auto"></div>
<script language="JavaScript">

var chart = null;
$(function () {
	//加工数量
	var number = $("#number").val();
	var text = {text:'今日加工件数</br>'+number+'个'};
	var boot = $("#boot").val();
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
	console.log(dataBoot)
	
	
	var work = $("#boot").val();
	var valueWork = boot.split("-");
	var dataWork = new Array();
	for(var i=0;i<valueWork.length;i++){
		console.log()
		if(i%2==0){//偶数
			dataWork[i]= {name:'其他时间',y:parseFloat(valueWork[i]),color:'white'};
		}else{
			dataWork[i]={name:'工作时间',y:parseFloat(valueWork[i]),color:'peachpuff'};
		}
	};
    $('#container').highcharts({
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
});
</script>
</body>


</html>





