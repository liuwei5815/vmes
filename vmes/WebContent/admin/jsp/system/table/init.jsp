<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据表</title>
<script>
function openAddWin(){
	var diag = new top.Dialog();
	diag.Title = "创建新表";
	diag.URL = "${ctx}/admin/sys_tables!preAdd.action";
	diag.Width=1200;
	diag.Height=900;
	diag.ShowButtonRow=true;
	diag.OKEvent=function(){
		 diag.innerFrame.contentWindow.doSubmit();
	};
	diag.show();
	return false;
}


function doQueryTab(){
	$("#currPage").val(1);
	document.forms[0].action = "${ctx}/admin/sys_tables!queryTable.action";
	document.forms[0].submit();
}

function openAddRelationWin(){
    var num=0;
    var tableId=[];
    $(querytable.document).find("tr:gt(0)").each(function(){
        var obj= $(this).find("[name='checks']:eq(0)");
     
        if(obj.is(":checked")){
        	num++;
        	tableId.push(obj.val());
        }
    });
    if(num==0){
    	top.Dialog.alert("请选择一条记录");
    	return false;
    }
    
    if(num>1){
    	top.Dialog.alert("你只能选择一条记录");
    	return false;
    }
    
    var diag = new top.Dialog();
	diag.Title = "创建主外键关系";
	diag.URL = "${ctx}/admin/sys_tables!preAddRelation.action?tableId="+tableId[0];
	diag.Width=1000;
	diag.Height=600;
	diag.ShowButtonRow=false;
	/* diag.ShowButtonRow=true;
	diag.OKEvent=function(){
		 diag.innerFrame.contentWindow.saveRelation();
	}; */
	diag.show();
	return false;
}

function openEditRelationWin(){
	var num=0;
	var tableId=[];
    $(querytable.document).find("tr:gt(0)").each(function(){
        var obj= $(this).find("[name=checks]");
        if(obj.attr("checked")==true){
        	num++;
        	tableId.push(obj.val());
        }
    });
    if(num==0){
    	top.Dialog.alert("请选择一条记录");
    	return false;
    }
    
    if(num>1){
    	top.Dialog.alert("你只能选择一条记录");
    	return false;
    }

    var diag = new top.Dialog();
	diag.Title = "编辑主外键关系";
	diag.URL = "${ctx}/admin/sys_tables!preEditRelation.action?tableId="+tableId[0];
	diag.Width=600;
	diag.Height=400;
	diag.ShowButtonRow=true;
	diag.OKEvent=function(){
		 diag.innerFrame.contentWindow.editRelation();
	};
	diag.show();
	return false;
}
</script>
</head>
<body>
	<div class="position">
		<div class="center">
			<div class="left">
				<div class="right">
					<span>当前位置：系统管理  >>表管理 </span>		
				</div>	
			</div>	
		</div>
	</div>	
	<div class="box2" panelTitle="表管理" roller="false">
    <s:form method="post" target="querytable" action="admin/sys_tables!queryTable.action">
	    <input type="hidden" name="currPage" id="currPage" value="${currpage}" />			
		<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}" />
	  <table>
        <tr>
        	<td nowrap="nowrap">表名：</td>
        	<td><input id="name" name="tables.name" value="${tables.name }" class="validate[length[0,15]]"/></td>
        	<td nowrap="nowrap">发布时间：</td>
          	<td nowrap="nowrap"><input class="date"  id="beginDate" name="beginDate"/></td>
          	<td align="center"> 到 </td>
          	<td><input class="date"  id="endDate" name="endDate"/></td>
        </tr>
      </table>
      <table>
        <tr>
          <td><button onclick='return doQueryTab();' type="button"><span class="icon_find">查询</span></button></td>
          <td><button onclick='return openAddWin();' type="button"><span class="icon_add">新增</span></button></td>
          <!-- <td><button onclick="return delAll();" type="button"><span class="icon_delete">删除</span></button></td> -->
          <td><button onclick='return openAddRelationWin();' type="button"><span class="icon_add">配置主外键</span></button></td>
          <!-- <td><button onclick='return openEditRelationWin();' type="button"><span class="icon_edit">编辑主外键</span></button></td> -->
        </tr>
      </table>
      </s:form>
    </div>
      
<div id="scrollContent">
	<iframe scrolling="no" width="100%"  frameBorder=0 id="querytable" name="querytable" src="${ctx}/admin/sys_tables!queryTable.action" onload="iframeHeight('querytable')"  allowTransparency="true"></iframe>
</div>
<pt:page action="${ctx}/admin/sys_tables!queryTable.action" target="querytable"></pt:page>
</body>
</html>





