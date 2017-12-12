package com.xy.cms.common;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.apache.poi.hslf.blip.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRcode {
	private static final int CODE_HEIGHT=300;//默认二维码高度
	private static final int CODE_WIDTH=300;//默认二维码宽度
	public static final String CODE_FORMAT="png";
	public static final  Integer MARGIN = 4;
	
	public static void create(File file,String content){
		try{
			file.getParentFile().mkdirs();
			create(new FileOutputStream(file), content);
		}
		catch(Exception e){
			throw new RuntimeException("create qrcode exception",e);
		}
	}
	public static void create(OutputStream outputStream,String content){
		create(CODE_WIDTH,CODE_HEIGHT,outputStream, content);
	}
	
	public static void create(int width,int height,OutputStream outputStream,String content){
		//定义二维码参数
		HashMap hints =  new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");//设置编码
		//设置容错等级，等级越高，容量越小
		hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN,1);//设置边距
	//生成矩阵
		BitMatrix bitMatrix = null;
		try{
			bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, width, height);
			bitMatrix = deleteWhite(bitMatrix);//删除白边
			MatrixToImageWriter.writeToStream(bitMatrix, CODE_FORMAT, outputStream);
		}
		catch(Exception e){
			throw new RuntimeException("create qrcode exception",e);
		}
	}
	 private static BitMatrix deleteWhite(BitMatrix matrix) {
	        int[] rec = matrix.getEnclosingRectangle();
	        int resWidth = rec[2] + 1;
	        int resHeight = rec[3] + 1;

	        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
	        resMatrix.clear();
	        for (int i = 0; i < resWidth; i++) {
	            for (int j = 0; j < resHeight; j++) {
	                if (matrix.get(i + rec[0], j + rec[1]))
	                    resMatrix.set(i, j);
	            }
	        }
	        return resMatrix;
	    }
	public static void main(String[] args) throws IOException {
		File file=new File("E:/tts9-v2-win-x86/tts9/apache-tomcat-7.0.67/wtpwebapps/vmesfile/qrcode/"+1+"Q"+"."+QRcode.CODE_FORMAT);
		
		create(new FileOutputStream(file),"1");
	}
	
}
