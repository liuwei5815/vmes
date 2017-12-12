package com.xy.admx.common;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class FileUtil {

	private static final int BUFFER_SIZE = 1024 * 1024;
	/***
	 * 得到文件后缀名
	 * @param fileName
	 * @return
	 */
	public static String getSuffixName(String fileName){
		return fileName.substring(fileName.lastIndexOf(".")+1);
	} 
	/**
	 * 拷贝文件
	 * 
	 * @param src
	 *            原文件
	 * @param dst
	 *            目标文件
	 * @throws IOException
	 */
	public static void copy(File src, File dst) throws IOException {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst),BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int i = -1;
			while ((i = in.read(buffer)) != -1) {
				out.write(buffer, 0, i);
			}
		} finally {
			if (null != in) {
				in.close();
				in = null;
			}
			if (null != out) {
				out.close();
				out = null;
			}
		}
	}
	
	/**
	 * 拷贝文件MVC
	 * 
	 * @param src
	 *            原文件
	 * @param dst
	 *            目标文件
	 * @throws IOException
	 */
	public static void copyMVC(InputStream src, File dst) throws IOException {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(src, BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst),BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int i = -1;
			while ((i = in.read(buffer)) != -1) {
				out.write(buffer, 0, i);
			}
		} finally {
			if (null != in) {
				in.close();
				in = null;
			}
			if (null != out) {
				out.close();
				out = null;
			}
		}
	}

	/**
	 * 移动文件
	 * 
	 * @param src
	 * @param dst
	 * @throws IOException
	 */
	public static void moveFile(File src, String dst) throws IOException {
		if (dst != null && !dst.equals("")) {
			String[] dsts = dst.replace("\\", "/").split("/");
			String filePath = dsts[0];
			for (int i = 1; i < dsts.length - 1; i++) {
				filePath += "/" + dsts[i];
				File file = new File(filePath);
				if (!file.exists()) {
					file.mkdir();
				}
			}
			File dstFile = new File(dst);
			copy(src, dstFile);
			src.delete();
		}
	}


	/**
	 * 创建缩略图
	 * @param relativeSrcPath 	原图片相对位置
	 * @param relativeOutPath	缩略图相对位置
	 * @param width 图片宽度
	 * @param Height 图片高度
	 * @throws Exception
	 */
	public static void createSImg(String srcPath, String outPath,String width,String height)
	throws Exception {
		try {  
			File srcfile = new File(srcPath);  
			if (!srcfile.exists()) {  
				return; 
			}
			int k = Integer.parseInt(width);
			int l = Integer.parseInt(height);
			Image src = javax.imageio.ImageIO.read(srcfile);  
			BufferedImage tag= new BufferedImage(k,l,  BufferedImage.TYPE_INT_RGB);  

			tag.getGraphics().drawImage(src.getScaledInstance(k, l,  Image.SCALE_SMOOTH), 0, 0,  null);  

			FileOutputStream out = new FileOutputStream(new File(outPath));  
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
			encoder.encode(tag);  
			out.close();  

		} catch (IOException ex) {  
			ex.printStackTrace();  
		}
		/*FileOutputStream os=null;
		try{
			File localFile = new File(srcPath);
			BufferedImage image1 = ImageIO.read(localFile);
			int k = Integer.parseInt(width);
			int l = Integer.parseInt(height);
			BufferedImage images = new BufferedImage(k, l, 1);
			images.getGraphics().drawImage(image1, 0, 0, k, l, null);
			os = new FileOutputStream(outPath);
			JPEGImageEncoder localJPEGImageEncoder = JPEGCodec
			.createJPEGEncoder(os);
			localJPEGImageEncoder.encode(images);
		}
		finally{
			if(os!=null){
				os.close();
			}
		}
		 */
	}

	/**
	 * 创建缩略图
	 * @param relativeSrcPath 	原图片相对位置
	 * @param relativeOutPath	缩略图相对位置
	 * @param width 图片宽度
	 * @param Height 图片高度
	 * @throws Exception
	 */
	public static void createSImg(String srcPath, String outPath,int width,int height)
	throws Exception {
		/*FileOutputStream os=null;
		try{
			File localFile = new File(srcPath);
			BufferedImage image1 = ImageIO.read(localFile);
			int k = width;
			int l = height;
			BufferedImage images = new BufferedImage(k, l, 1);
			images.getGraphics().drawImage(image1, 0, 0, k, l, null);
			os = new FileOutputStream(outPath);
			JPEGImageEncoder localJPEGImageEncoder = JPEGCodec
			.createJPEGEncoder(os);
			localJPEGImageEncoder.encode(images);
		}
		finally{
			if(os!=null){
				os.close();
			}
		}*/
		try {  
			File srcfile = new File(srcPath);  
			if (!srcfile.exists()) {  
				return; 
			}
			Image src = javax.imageio.ImageIO.read(srcfile);  
			BufferedImage tag= new BufferedImage(width,height,  BufferedImage.TYPE_INT_RGB);  

			tag.getGraphics().drawImage(src.getScaledInstance(width,height,  Image.SCALE_SMOOTH), 0, 0,  null);  

			FileOutputStream out = new FileOutputStream(new File(outPath));  
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
			encoder.encode(tag);  
			out.close();  

		} catch (IOException ex) {  
			ex.printStackTrace();  
		}

	}
	public static void main(String[] args) throws Exception {
		File f= new File("D:\\2016");
		Stack<File> stacks= new Stack<File>();
		stacks.add(f);
		while(!stacks.isEmpty()){
			File file  = stacks.pop();
		
			if(file.isDirectory()){
				File[] fs = file.listFiles();
				for(int i =fs.length-1;i>=0;i--){
					stacks.add(fs[i]);
				}
			}
			System.out.println(file.getName());
		
			
		}
	}
	/**
	 * 创建缩略图
	 * @param relativeSrcPath 	原图片相对位置
	 * @param relativeOutPath	缩略图相对位置
	 * @param size 图片宽*高 
	 * @throws Exception
	 */
	public static void createSImg(String srcPath, String outPath,String size)
	throws Exception {
		FileOutputStream os=null;

		String sizes[]=size.split("\\*");
		try{
			File localFile = new File(srcPath);
			BufferedImage image1 = ImageIO.read(localFile);
			int k = Integer.parseInt(sizes[0]);
			int l = Integer.parseInt(sizes[1]);
			BufferedImage images = new BufferedImage(k, l, 1);
			images.getGraphics().drawImage(image1, 0, 0, k, l, null);
			os = new FileOutputStream(outPath);
			JPEGImageEncoder localJPEGImageEncoder = JPEGCodec
			.createJPEGEncoder(os);
			JPEGEncodeParam jep=	localJPEGImageEncoder.getDefaultJPEGEncodeParam(images);
			jep.setQuality(100f, true);


			localJPEGImageEncoder.encode(images,jep);


		}
		finally{
			if(os!=null){
				os.close();
			}
		}

	}

	/**
	 * 创建水印
	 * @param srcPicPath	要加盖水印的图片相对位置
	 * @param waterPath		水印图片相对位置
	 * @param outPath		合成图片的输出位置
	 * @throws Exception
	 */
	public static synchronized void createWaterMark(String srcPicPath,
			String waterPath, String outPath, int watermark_pos) throws Exception{
		BufferedImage image1 = null;
		int i = 0;
		int j = 0;
		BufferedImage image2 = null;
		int k = watermark_pos;
		image1 = ImageIO.read(new File(srcPicPath));
		image2 = ImageIO.read(new File(waterPath));
		if (k == 0) {
			i = image1.getWidth() / 2 - (image2.getWidth() / 2);
			j = image1.getHeight() / 2 - (image2.getHeight() / 2);
		} else if (k == 1) {
			i = 10;
			j = 10;
		} else if (k == 2) {
			i = image1.getWidth() - image2.getWidth() - 10;
			j = 10;
		} else if (k == 3) {
			i = 10;
			j = image1.getHeight() - image2.getHeight() - 10;
		} else if (k == 4) {
			i = image1.getWidth() - image2.getWidth() - 10;
			j = image1.getHeight() - image2.getHeight() - 10;
		}
		Graphics localGraphics = image1.getGraphics();
		localGraphics.drawImage(image2, i, j, null);
		FileOutputStream localFileOutputStream = new FileOutputStream(outPath);
		JPEGImageEncoder localJPEGImageEncoder = JPEGCodec.createJPEGEncoder(localFileOutputStream);
		localJPEGImageEncoder.encode(image1);
		localFileOutputStream.close();
	}
	
	public static void base642Img(String base64ImgStr, String destFile) throws Exception{
		if (base64ImgStr == null || base64ImgStr.trim().equals("")) {
			throw new IllegalArgumentException("参数错误,base64ImgStr");
		}
		if (destFile == null || destFile.trim().equals("")) {
			throw new IllegalArgumentException("参数错误,destFile");
		}
		OutputStream out = null;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			if(base64ImgStr.contains("data:image/jpeg;base64")){
				base64ImgStr = base64ImgStr.substring(base64ImgStr.indexOf(",")+1);
			}
			byte[] bytes = decoder.decodeBuffer(base64ImgStr);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}

			// 生成jpeg图片
			out = new FileOutputStream(destFile);
			out.write(bytes);
			out.flush();
		} finally{
			if(out != null){
				out.close();
			}
		}
	}
	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true,否则返回false
	 */
	public static boolean delFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			System.out.println("删除单个文件" + fileName + "成功！");
			return true;
		} else {
			System.out.println("删除单个文件" + fileName + "失败！");
			return false;
		}
	}

	/**
	 * 删除一个目录下所有文件
	 * @param filepath
	 * @throws IOException
	 */
	public static void delDir(File dir) throws IOException{
		if(dir.exists()){
			File filelist[] = dir.listFiles();
			int listlen = filelist.length;
			for (int i = 0; i < listlen; i++) {
				if (filelist[i].isDirectory()) {
					delDir(filelist[i]);
				} else {
					filelist[i].delete();
				}
			}
			dir.delete();// 删除当前目录
		}
	}

	/**
	 *  取得文件大小
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSizes(File f) throws IOException {
		long s = 0;
		if (f.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(f);
			s = fis.available();
		}
		return s;
	}

	/**
	 * 取得指定目录下的所有文件列表，包括子目录.
	 * 
	 * @param baseDir
	 *            File 指定的目录
	 * @return 包含java.io.File的List
	 */
	public static List getSubFiles(File baseDir) {
		List ret = new ArrayList();
		File[] tmp = baseDir.listFiles();
		for (int i = 0; i < tmp.length; i++) {
			if (tmp[i].isFile()) {
				ret.add(tmp[i]);
			}
			if (tmp[i].isDirectory()) {
				ret.addAll(getSubFiles(tmp[i]));
			}
		}
		return ret;
	}

	/**
	 * 将一个文本文件读成String
	 * @param filePaht
	 * @param charsetName
	 * @return
	 * @throws Exception
	 */
	public static final String readFile2String(String filePaht,
			String charsetName) throws Exception {
		StringBuffer buff = new StringBuffer("");
		String line = "";
		BufferedReader br = null;
		InputStreamReader is = null;
		File file = null;
		try {
			file = new File(filePaht);
			is = new InputStreamReader(new FileInputStream(file), charsetName);
			br = new BufferedReader(is);
			while ((line = br.readLine()) != null) {
				buff.append(line).append("\n\r");
			}
		} finally{
			if(is != null){
				is.close();
			}
			if(br != null){
				br.close();
			}
			file = null;
		}
		return buff.toString();
	}



}
