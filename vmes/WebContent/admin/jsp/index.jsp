<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="com.xy.cms.common.CacheFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"
	scope="request" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>智造云管家</title>
<!--框架必需start-->
<link href="${ctx}/admin/frame/css/import_basic.css" rel="stylesheet"
	type="text/css" prePath="${ctx}/admin/frame/" />
<link
	href="${ctx}/admin/frame/skins/<%=CacheFun.getConfig("frame_skinname")%>/import_skin.css"
	rel="stylesheet" type="text/css" id="skin"
	themeColor="<%=CacheFun.getConfig("frame_themecolor")%>"
	prePath="${ctx}/admin/frame/" />
<script type="text/javascript" src="${ctx}/admin/frame/js/jquery-1.4.js"></script>
<script type="text/javascript" src="${ctx}/admin/frame/js/bsFormat.js"></script>
<!--框架必需end-->

<!--引入弹窗组件start-->
<script type="text/javascript"
	src="${ctx}/admin/frame/js/attention/zDialog/zDrag.js"></script>
<script type="text/javascript"
	src="${ctx}/admin/frame/js/attention/zDialog/zDialog.js"></script>
<!--引入弹窗组件end-->

<!--修正IE6支持透明png图片start-->
<script type="text/javascript"
	src="${ctx}/admin/frame/js/method/pngFix/supersleight.js"></script>
<!--修正IE6支持透明png图片end-->

<!--弹出式提示框start-->
<script type="text/javascript"
	src="${ctx}/admin/frame/js/attention/messager.js"></script>
<script>
	
	function openMsg() {
		$.messager.lays(250, 180);
		$.messager
				.show(
						0,
						'<ul><li><a href="javascript:showDetail(1)"><span class="icon_lightOn">将到期合同：${willExpiredCC}人</span></a></li><div class="clear"></div>'
								+ '<li><a href="javascript:showDetail(3)"><span class="icon_lightOn">男60岁以上：${retirementAgeOfManCount}人</span></a></li><div class="clear"></div>'
								+ '<li><a href="javascript:showDetail(4)"><span class="icon_lightOn">女55岁以上：${retirementAgeOfWomanCount}人</span></a></li><div class="clear"></div>'
								+ '<li><a href="javascript:showDetail(5)"><span class="icon_lightOn">将到期协议：${willExpiredUC}</span></a></li><div class="clear"></div>'
								+ '<li><a href="javascript:showDetail(6)"><span class="icon_lightOn">过期协议：${outOfDateUC}</span></a></li><div class="clear"></div></ul>',
						'stay');
	}
	function showDetail(type) {
		if (type == '1') {
			frmright.location = "${ctx}/admin/contExpire!init.action";
		} else if (type == '2') {
			//frmright.location = "${ctx}/admin/contExpire!init.action?s_alarmExpire=0";
			frmright.location = "${ctx}/admin/contOOD!init.action";
		} else if (type == '3') {
			frmright.location = "${ctx}/admin/ageAlarm!init.action?sex=1";
		} else if (type == '4') {
			frmright.location = "${ctx}/admin/ageAlarm!init.action?sex=2";
		} else if (type == '5') {
			frmright.location = "${ctx}/admin/unitExpire!init.action";
		} else if (type == '6') {
			//frmright.location = "${ctx}/admin/contExpire!init.action?s_alarmExpire=0";
			frmright.location = "${ctx}/admin/unitOOD!init.action";
		}
	}
</script>
<!--弹出式提示框end-->
</head>
<body>
	<!--  -->
	


	<div id="floatPanel-1"></div>
	<div id="mainFrame">
		<!--头部与导航start-->
		<div id="hbox">
			<div id="bs_bannercenter">
				<div id="bs_bannerleft">
					<div id="bs_bannerright">
						<div class="bs_banner_logo"></div>
						<div class="bs_banner_title"></div>
					</div>
				</div>
			</div>
			<div id="bs_navcenter">
				<div id="bs_navleft">
					<div id="bs_navright">
						<div class="bs_nav">
							<div class="bs_navleft">
								<li>欢迎 【
							 		${sessionBean.userName} ( 工号：${sessionBean.userCode} )
								】 ，今天是 <script>
									var weekDayLabels = new Array("星期日", "星期一",
											"星期二", "星期三", "星期四", "星期五", "星期六");
									var now = new Date();
									var year = now.getFullYear();
									var month = now.getMonth() + 1;
									var day = now.getDate();
									var currentime = year + "年" + month + "月"
											+ day + "日 "
											+ weekDayLabels[now.getDay()];
									document.write(currentime);
								</script>
								</li>
								
								<div class="clear"></div>
							</div>
							<div class="bs_navright">
								<span class="icon_btn_up hand" id="fullSrceen" hideLeft="false">全屏</span>
								<!--如果将hideLeft设为true则全屏时左侧也会被隐藏-->
								<span class="icon_mark hand"
									onclick='top.Dialog.open({URL:"${ctx}/admin/jsp/frame_skin.jsp",Title:"更换皮肤",Width:720,Height:490});'>皮肤管理</span>
								<span class="icon_edit hand"
									onclick='top.Dialog.open({URL:"${ctx}/admin/jsp/pwd.jsp",Title:"密码修改",Width:400,Height:200});'>修改密码</span>
								<span class="icon_no hand"
									onclick='top.Dialog.confirm("确定要退出系统吗",function(){window.location="login!doLogOut.action"});'>退出系统</span>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--头部与导航end-->

		<table width="100%" cellpadding="0" cellspacing="0"
			class="table_border0">
			<tr>
				<!--左侧区域start-->
				<td id="hideCon" class="ver01 ali01">
					<div id="lbox">
						<div id="lbox_topcenter">
						<div id="lbox_topleft">
						<div id="lbox_topright">
							<div class="lbox_title">操作菜单</div>
						</div>
						</div>
						</div>
						<div id="lbox_middlecenter">
						<div id="lbox_middleleft">
						<div id="lbox_middleright">
								<div id="bs_left">
								<IFRAME scrolling="no" width="100%"  frameBorder=0 id=frmleft name=frmleft src="${ctx}/admin/login!leftmenu.action"  allowTransparency="true"></IFRAME>
								</div>
								<!--更改左侧栏的宽度需要修改id="bs_left"的样式-->
						</div>
						</div>
						</div>
						<div id="lbox_bottomcenter">
						<div id="lbox_bottomleft">
						<div id="lbox_bottomright">
							<div class="lbox_foot"></div>
						</div>
						</div>
						</div>
					</div>
				</td>
				<!--左侧区域end-->

				<!--中间栏区域start-->
				<td class="main_shutiao">
					<div class="bs_leftArr" id="bs_center" title="收缩面板"></div>
				</td>
				<!--中间栏区域end-->

				<!--右侧区域start-->
				<td class="ali01 ver01" width="100%">
					<div id="rbox">
						<div id="rbox_topcenter">
							<div id="rbox_topleft">
								<div id="rbox_topright">
									<div class="rbox_title">操作内容</div>
								</div>
							</div>
						</div>
						<div id="rbox_middlecenter">
							<div id="rbox_middleleft">
								<div id="rbox_middleright">
									<div id="bs_right">
										<IFRAME scrolling="no" width="100%" frameBorder=0 id=frmright
											name=frmright src="${ctx}/admin/jsp/open.jsp"
											allowTransparency="true"></IFRAME>
									</div>
								</div>
							</div>
						</div>
						<div id="rbox_bottomcenter">
							<div id="rbox_bottomleft">
								<div id="rbox_bottomright"></div>
							</div>
						</div>
					</div>
				</td>
				<!--右侧区域end-->
			</tr>
		</table>
		<%-- 
<!--尾部区域start-->
<div id="fbox">
	<div id="bs_footcenter">
	<div id="bs_footleft">
	<div id="bs_footright">
		WEB前台界面集成框架 COPYRIGHT 2011
	</div>
	</div>
	</div>
</div>
</div>
<!--尾部区域end-->
--%>
		<!--浏览器resize事件修正start-->
		<div id="resizeFix"></div>
		<!--浏览器resize事件修正end-->

		<!--载进度条start-->
		<div class="progressBg" id="progress" style="display: none;">
			<div class="progressBar"></div>
		</div>
		<!--载进度条end-->
</body>
</html>
