package com.xy.admx.common;

import java.util.Arrays;

public class FilesUtils {
	private static String[] IMG_EXT= new String[]{"bmp","jpg","tiff","gif","pcx","tga","exif","fpx","svg","psd","cdr","pcd","dxf","ufo","eps","ai","raw"};
	private static String[] AUDIO_EXT= new String[]{"mp3","wav","wma","wav","ogg","ape","acc"};
	private static String[] VIDEO_EXT= new String[]{"avi","rm","asf","wmv","mov"};
	private static String[] DOC_EXT = new String[]{"doc","docx","md","xls","xlsx","csv","pdf"};//文档
	/**
	 * 根据 img audio video
	 */
	public static String getFilesType(String ext){
		if(Arrays.binarySearch(IMG_EXT, ext.toLowerCase())!=-1){
			return "img";
		}
		if(Arrays.binarySearch(AUDIO_EXT, ext.toLowerCase())!=-1){
			return "audio";
		}
		if(Arrays.binarySearch(VIDEO_EXT, ext.toLowerCase())!=-1){
			return "vedio";
		}
		if(Arrays.binarySearch(DOC_EXT, ext.toLowerCase())!=-1){
			return "doc";
		}
		return null;
	}
	
}
