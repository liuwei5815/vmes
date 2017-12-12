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
	diag.Width=980;
	diag.Height=900;
	diag.ShowButtonRow=true;
	diag.OKEvent=function(){
		 diag.innerFrame.contentWindow.doSubmit();
	};
	diag.show();
	return false;
}

function doQueryTab(){
	document.forms[0].action = "${ctx}/admin/sys_tables!queryTable.action";
	document.forms[0].submit();
}
</script>
</head>
<body>
	<div class="position">
		<div class="center">
			<div class="left">
				<div class="right">
					<span>当前位置：系统管理  >>${table.menuName } </span>		
				</div>	
			</div>	
		</div>
	</div>	
	<div class="box2" panelTitle="${table.menuName }" roller="false">
    <form method="post" target="querytable" action="admin/tab!query.action">
	    <input type="hidden" name="currPage" id="currPage" value="${currpage}" />			
		<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}" />
	  <%-- <table>
        <tr>
        	<td nowrap="nowrap">表名：</td>
        	<td><input id="name" name="tables.name" value="${tables.name }"/></td>
        	<!--
        	<td nowrap="nowrap">类型：</td>
        	<td>
        		<select id="type" name="type">
        			<option value=" ">--请选择--</option>
        			<s:iterator value="@com.xy.common.CacheFun@getCodeList('type_news')" var="typeNews">
        			 <option value="<s:property value="key"/>"><s:property value="value"/></option>
        			</s:iterator>
        		</select>
        	</td>
        	-->
        	<td nowrap="nowrap">发布时间：</td>
          	<td nowrap="nowrap"><input class="date"  id="beginDate" name="beginDate"/></td>
          	<td align="center"> 到 </td>
          	<td><input class="date"  id="endDate" name="endDate"/></td>
        </tr>
      </table> --%>
      <table>
        <tr>
          <!-- <td><button onclick='return doQuery();' type="button"><span class="icon_find">查询</span></button></td> -->
          <td><button onclick='return openAddWin();' type="button"><span class="icon_add">新增</span></button></td>
        </tr>
      </table>
      </form>
    </div>
      
<div id="scrollContent">
	<iframe scrolling="no" width="100%"  frameBorder=0 id="querytable" name="querytable" src="${ctx}/admin/tab!query.action?table.id=${tableId}" onload="iframeHeight('querytable')"  allowTransparency="true"></iframe>
</div>
<%-- <pt:page action="${ctx}/admin/tab!query.action?tableId=<%=request.getParameter("tableId") %>" target="querytable"></pt:page> --%>
</body>
</html>





