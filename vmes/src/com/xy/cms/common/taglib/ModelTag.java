package com.xy.cms.common.taglib;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.hibernate.type.DateType;

import sun.util.logging.resources.logging;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.xy.cms.bean.TargetShip;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.ServiceUtil;
import com.xy.cms.common.opt.AutoNumberOpt;
import com.xy.cms.entity.TableRelationship;
import com.xy.cms.service.TablesService;

public class ModelTag extends TagSupport {
		private String id;
		private String name;
		private String value;
		private List<String> optValue;
		private String className;
		private String dataType;
		private String notNull;
		private TargetShip ship;//当前表作为外键表对应关系        
		private String lable;
		private String defaults;//默认值
		//以下字段用于 是isFk==true的情况
		private Long targetTableId;//主键表id
		private Long sourceColunmnId;//外键字段id
		
		private Long shipId;//主外键关系id
		
		private static TablesService tablesService = (TablesService) ServiceUtil.getService("tablesService");
		
		public int doStartTag() throws JspTagException {
			return SKIP_BODY;
		}
		
		public int doEndTag() throws JspTagException {
		
				StringBuilder out = new StringBuilder();
				StringBuilder pro = new StringBuilder();
				pro.append("name=").append("\"").append(name).append("\"")
				.append(" value=").append("\"").append(value).append("\"").append(" not-null=").
				append("\"").append(notNull).append("\"").append(" lable=").append("\"").append(lable).append("\"")
				.append(" dataType=").append("\""+dataType+"\"");
				
				if(ship!=null){
					String foreignValue =null;
					if(value!=null){
						foreignValue =  tablesService.getShowColumnIdByshipIdAndPkValue(ship.getId(),value);
					}
					out.append("<div style=\"float: left;\">");
					out.append("<input type=\"text\" class=\"textinput simple\" value=\""+(foreignValue==null?"":foreignValue)+"\" id="+id+"_lable readonly=\"readonly\"");
					out.append(" />");					
					out.append("<input type=\"hidden\" id=\""+id+"\"  targetId="+ship.getTargetTableId()+"").append(" ").append(pro);
					
					out.append(" />");
					out.append("</div>");
					out.append("<span class=\"icon_find\" onclick=\"chooseShip('"+ship.getId()+"','"+id+"','"+lable+"','"+(ship.getSourceShip()==null?"":ship.getSourceShip().getTargetTableId())+"','"+(ship.getSourceShip()==null?"":ship.getSourceShip().getSourceColumnId())+"')\">&nbsp; </span>");
					
				}else{
					//日期精确到时分秒
					if(dataType.equals("3")){
						out.append("<input class=\"date hid\" type=\"text\"").append(pro)
						.append(" dateFmt=\"yyyy-MM-dd HH:mm:ss\"  />");
					}
					//大文本
					else if(dataType.equals("4")){
						out.append("<script id=\"").append(id).append("\" type=\"text/plain\"")
						.append(" style=\"width:500px;height:100px\">").append(" </script> ");
						out.append("<script>");
						out.append("UE.getEditor(\"").append(id).append("\")");
						out.append("</script>");
						out.append("<input type=\"hidden\" class=\"hid\" editorId= \"").append(id).append("\" ").append(pro)
						.append("/> ");
					}
					//下拉框
					else if(dataType.equals("7")){
						out.append("<select").append(" id=").append(id).append(" name=").append(name).append(" class=").append("validate[required]").append(">");
						
						for(int i=0;i<optValue.size();i++){
							String val = optValue.get(i);
							Map<Integer, String> map = new HashMap<Integer, String>();
							map = JSONObject.parseObject(val,Map.class);
							out.append("<option ").append("value=\"").append(map.get("state")).append("\"");
							if(map.get("state").equals(value)){
								out.append("selected=\"selected\"");
							}
							out.append(">").append(map.get("describe")).append("</option>");
						}
						out.append("</select>");
					}
					else if(dataType.equals("8")){
						for(int i=0;i<optValue.size();i++){
							String item =optValue.get(i);
							out.append(" <input type=\"checkbox\" class=\"hid\" ")
							.append(pro).append(" ").append("value=").append(item);
							if(item.equals(defaults)){
								out.append(" checked=checked ");
							}
							out.append("/>").append(item);
						}
					}
					else if(dataType.equals("9")){
						for(int i=0;i<optValue.size();i++){
							String item =optValue.get(i);
							out.append(" <input type=\"radio\" class=\"hid\" ")
							.append("name=").append("\"").append(name).append("\"")
							.append(" not-null=").
							append("\"").append(notNull).append("\"").append(" lable=").append("\"").append(lable).append("\"")
							.append(" dataType=").append("\""+dataType+"\"").append(" ").append("value=\"").append(item).append("\"");
							if(item.equals(defaults)){
								out.append(" checked=checked ");
							}
							out.append("/>").append(item);
							
						}
					}
					else if(dataType.equals("19")){
						out.append(" <input type=\"hidden\" class=\"hid\" id=").append(id).append(" ").append(pro).append(" </input>");
						out.append(" <div id=\"upfile\"><span> <input type=\"file\"  ")
						.append(" class=\"fileinput\" ").append(" id=upload_").append(id).append(" />")
						.append("</span> ");
						out.append(" <input type=\"button\" value=\"上传\" onclick=\"upload('"+id+"')\" ></input>");
					}
					else if(dataType.equals("10")){
						out.append(" <input type=\"hidden\" class=\"hid\" id=").append(id).append(" ").append(pro).append(" </input>");
						out.append(" <div id=\"upfile\"><span> <input type=\"file\"   accept=\"image/*\" ")
						.append(" class=\"fileinput\" ").append(" id=upload_").append(id).append(" />")
						.append("</span> ");
						String[] bg = CommonFunction.isNull(optValue)?null:CommonFunction.isNull(optValue.get(0).trim())?null:optValue.get(0).split("\\*");
						out.append("<span id=\"size_"+id+"\">");
						out.append(" <input type=\"hidden\" name=\"bg_width\" id=\"bg_width_").append(id).append("\" value=\"").append(CommonFunction.isNull(bg)?"":bg[0].trim()).append("\" ></input>");
						out.append(" <input type=\"hidden\" name=\"bg_height\" id=\"bg_height_").append(id).append("\" value=\"").append(CommonFunction.isNull(bg)?"":bg[1].trim()).append("\" ></input>");
						String[] sl = CommonFunction.isNull(optValue)?null:CommonFunction.isNull(optValue.get(1).trim())?null:optValue.get(1).split("\\*");
						out.append(" <input type=\"hidden\" name=\"s_width\" id=\"s_width_").append(id).append("\" value=\"").append(CommonFunction.isNull(sl)?"":sl[0].trim()).append("\" ></input>");
						out.append(" <input type=\"hidden\" name=\"s_height\" id=\"s_height_").append(id).append("\" value=\"").append(CommonFunction.isNull(sl)?"":sl[1].trim()).append("\" ></input>");
						out.append("</span>");
						//out.append(" <input type=\"hidden\" class=\"hid\" id=").append(id).append(" ").append(pro).append(" </input>");
						out.append(" <input type=\"button\" value=\"上传\" onclick=\"upload('"+id+"')\" ></input>");
						out.append(" 宽度:");
						out.append(" <input type=\"text\" ");
						out.append(" id=\"");
						out.append(id).append("_width\"");
						out.append(" value=\"").append(CommonFunction.isNull(bg)?"":Integer.valueOf(bg[0].trim())-10);
						out.append("\"  style=\"width:60px\"");
						out.append(" />");
						out.append(" 高度:");
						out.append(" <input type=\"text\" ");
						out.append(" id=\"");
						out.append(id);
						out.append("_height\"");
						out.append(" value=\"");
						out.append(CommonFunction.isNull(bg)?"":Integer.valueOf(bg[1].trim())-10);
						out.append("\" style=");
						out.append("\" width:60px\" ");
						out.append(" />");
						out.append(" <input type=\"button\" value=\"裁剪\" ");
						out.append(" onclick=\"showJcrop('"+id+"')\"");
						out.append(" />");
						out.append(" </div>");
						out.append("<div id=\"").append(id).append("_view\"").append(">").append("</div>");
					}
					//日期精确到日
					else if(dataType.equals("11")){
						out.append(" <input class=\"date hid\" type=\"text\" ").append(pro)
						.append("dateFmt=\"yyyy-MM-dd\"");
					}else if(dataType.equals("15")){//密码框
						out.append("<input type=\"password\"").append(pro).append("class=\"textinput default hid\" />");
					}else if(dataType.equals("18")){//文本域
						out.append("<textarea rows=\"5\" class=\"textarea hid\" cols=\"7\" ").append(pro).append(" \"></textarea>");
						//out.append("<input type=\"password\"").append(pro).append("class=\"default\" />");
					}else if(dataType.equals("12")){//经纬度百度地图
						out.append(" <input type=\"hidden\" class=\"hid\" id=").append(id).append(" ").append(pro).append("> </input>");
						out.append(" <input type=\"text\" id=lng_").append(id).append("  placeholder=\"请输入地址详情\" onchange=\"searchLocation()\" ").append(" </input>");
						out.append("<div id=\"").append(id).append("_lng_view\" style=\"width:500px;height:350px;margin-top:10px;\" ></div>");
						out.append(" <script>");
						out.append(" var map;var markers = new Array();$(function(){  "
								+ " map = new BMap.Map(\""+id+"_lng_view\"); map.centerAndZoom(new BMap.Point(116.404, 39.915),11);"
								+ " map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL})); "
								+ " });");
						out.append(" function searchLocation() {"
								+ " var ress = $(\"#lng_"+id+"\").val(); "
								+ " if($.trim(ress)==''){"
								+ " $(\"#"+id+"\").val(\"\"); if(markers.length>0){for(var i=0;i<markers.length;i++){map.removeOverlay(markers[i]);}}"
								+ " top.Dialog.alert(\"请输入地址详情\");return false;}else{"
								+ " var myGeo = new BMap.Geocoder();myGeo.getPoint(ress, function(point) {if (point) {"
								+ " $(\"#"+id+"\").val(point.lng+\",\"+point.lat); var xpoint = new BMap.Point(point.lng,point.lat);"
								+ " map.centerAndZoom(xpoint, 15);  var marker = new BMap.Marker(point); map.enableScrollWheelZoom(false);"
								+ " map.addOverlay(marker); markers.push(marker);marker.enableDragging();"
								+ " marker.addEventListener(\"dragend\", function(e){$(\"#"+id+"\").val(e.point.lng+\",\"+e.point.lat);}); "
								+ " }else{top.Dialog.alert(\"您输入的地址没有解析到结果!\");$(\"#"+id+"\").val(\"\");}},'中国');"
								+ " } "
								+ " }");
						out.append("</script>");
					}
					else{
						out.append("<input type=\"text\"").append(pro).append("class=\"textinput default hid\" />");
					}
				}
				try {
					pageContext.getOut().write(out.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			return EVAL_PAGE;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public List<String> getOptValue() {
			return optValue;
		}

		public void setOptValue(List<String> optValue) {
			this.optValue = optValue;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public String getDataType() {
			return dataType;
		}

		public void setDataType(String dataType) {
			this.dataType = dataType;
		}

		public String getNotNull() {
			return notNull;
		}

		public void setNotNull(String notNull) {
			this.notNull = notNull;
		}

		public TargetShip getShip() {
			return ship;
		}

		public void setShip(TargetShip ship) {
			this.ship = ship;
		}

		public String getLable() {
			return lable;
		}

		public void setLable(String lable) {
			this.lable = lable;
		}

		public String getDefaults() {
			return defaults;
		}

		public void setDefaults(String defaults) {
			this.defaults = defaults;
		}

		public Long getTargetTableId() {
			return targetTableId;
		}

		public void setTargetTableId(Long targetTableId) {
			this.targetTableId = targetTableId;
		}

		public Long getSourceColunmnId() {
			return sourceColunmnId;
		}

		public void setSourceColunmnId(Long sourceColunmnId) {
			this.sourceColunmnId = sourceColunmnId;
		}

		public Long getShipId() {
			return shipId;
		}

		public void setShipId(Long shipId) {
			this.shipId = shipId;
		}

		
		
		
		
		
		
		
		
		
		
}
