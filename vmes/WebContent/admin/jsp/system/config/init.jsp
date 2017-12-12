<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统配置</title>
<script>
function openAddWin(){
	var diag = new top.Dialog();
	diag.Title = "添加";
	diag.URL = "${ctx}/admin/config!preAdd.action";
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
	document.forms[0].action = "${ctx}/admin/config!list.action";
	document.forms[0].submit();
}

</script>
</head>
<body>
	<div class="position">
		<div class="center">
			<div class="left">
				<div class="right">
					<span>当前位置：系统管理  >>系统配置 </span>		
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
        	<td nowrap="nowrap">配置名：</td>
        	<td><input id="name" name="tables.name" value="${tables.name }" class="validate[length[0,15]]"/></td>
        </tr>
      </table>
      <table>
        <tr>
          <td><button onclick='return doQueryTab();' type="button"><span class="icon_find">查询</span></button></td>
          <td><button onclick='return openAddWin();' type="button"><span class="icon_add">新增</span></button></td>
        </tr>
      </table>
      </s:form>
    </div>
      
<div id="scrollContent">
	<iframe scrolling="no" width="100%"  frameBorder=0 id="querytable" name="querytable" src="${ctx}/admin/config!list.action" onload="iframeHeight('querytable')"  allowTransparency="true"></iframe>
</div>
<pt:page action="${ctx}/admin/config!list.action" target="querytable"></pt:page>
</body>
</html>





