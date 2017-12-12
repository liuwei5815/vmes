package com.xy.cms.action.system.common;

import java.io.File;

import com.xy.cms.common.CacheFun;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.FileUtil;
import com.xy.cms.common.SysConfig;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionAjaxTemplate;
import com.xy.cms.utils.CutImage;
import com.xy.cms.utils.UploadFileUtils;

/**
 * Jcrop 图片裁剪 action
 * 
 * @author yl
 *
 */
public class JcropAction extends BaseAction {
	private Double x;
	private Double y;
	private Double height;// 裁剪高度
	private Double width;// 裁剪宽度
	private String imagePath;// 图片路径
	private Double swidth;
	private Double sheight;

	public String init() {

		return SUCCESS;
	}

	public String jcrop() {
		this.ajaxTemplate(new BaseActionAjaxTemplate() {

			@Override
			public Object execute() throws Exception {
				String root = CacheFun.getConfig("pic_path");
				String newFilePath = UploadFileUtils.getPathByYearAndMonth();
				new File(root + File.separator + newFilePath).mkdirs();
				imagePath = CommonFunction.getImageValue(imagePath, "bg");
				System.out.println("----------imagePath:"+imagePath);
				String newFileName = UploadFileUtils.createNewFileName()+ imagePath.substring(imagePath.lastIndexOf("."));
				System.out.println("-----------oldpath:"+root + File.separator + imagePath);
				System.out.println("-----------newpath:"+root+File.separator + newFilePath + File.separator+ "bg_"+newFileName);
				CutImage bgcutImage = new CutImage((int) x.doubleValue(), (int) y
						.doubleValue(), (int) width.doubleValue(), (int) height
						.doubleValue(), root + File.separator + imagePath, root+File.separator + newFilePath + File.separator+ "bg_"+newFileName);
				bgcutImage.cut();
				FileUtil.createSImg(root+File.separator + newFilePath + File.separator+ "bg_"+newFileName,
						root + File.separator + newFilePath + File.separator + "s_"+newFileName,(int) swidth.doubleValue(), (int) sheight
						.doubleValue());
				return (newFilePath + File.separator + newFileName).replace("\\", "/");
			}
		});
		return NONE;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getSwidth() {
		return swidth;
	}

	public void setSwidth(Double swidth) {
		this.swidth = swidth;
	}

	public Double getSheight() {
		return sheight;
	}

	public void setSheight(Double sheight) {
		this.sheight = sheight;
	}

}
