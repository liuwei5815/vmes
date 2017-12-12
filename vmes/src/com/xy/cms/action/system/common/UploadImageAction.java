package com.xy.cms.action.system.common;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang3.ArrayUtils;

import com.xy.cms.common.CacheFun;
import com.xy.cms.common.FileUtil;
import com.xy.cms.common.SysConfig;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionAjaxTemplate;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.utils.UploadFileUtils;

public class UploadImageAction extends BaseAction {
	private File file;
	private String fileFileName;
	private static final Long MAX_FILE_SIZE=1024*1024*50l;//最大支持多大文件上传

	
	public String uploadImage() {
		 this.ajaxTemplate(new BaseActionAjaxTemplate() {
			@Override
			public Object execute() throws Exception {
				String rootPath = SysConfig.getStrValue("file.path");
				if(file.length()>MAX_FILE_SIZE){
					throw new BusinessException("最大支持50M文件上传");
				}
				String nowPath = UploadFileUtils.getPathByYearAndMonth();
				String picPath = rootPath + File.separator + nowPath;
				String fileName = UploadFileUtils.createNewFileName() + fileFileName.substring(fileFileName.lastIndexOf("."));
				new File(picPath).mkdirs();// 创建新的文件名
				File newFile = new File(picPath + File.separator + fileName);
				FileUtil.copy(file,newFile);
				return nowPath + File.separator + fileName;
				
			}
		});
			
		return NONE;
	}

	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	
}
