package com.xy.cms.utils;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class CutImage {
	// ===源图片路径名称如：c:\1.jpg  
    private String srcpath;  
  
    // ===剪切图片存放路径名称。如：c:\2.jpg  
    private String subpath;  
    // ===剪切点x坐标  
    private int x;  
  
    private int y;  
  
    // ===剪切点宽度  
    private int width;  
    private int height;  
  
  
    public CutImage(int x, int y, int width, int height,String srcpath,String subpath) {  
        this.x = x;  
        this.y = y;  
        this.width = width;  
        this.height = height;
        this.srcpath=srcpath;
        this.subpath=subpath;
    }  
  
    /** 
     *  
     * 对图片裁剪，并把裁剪完的新图片保存 。 
     */  
  
    public void cut() throws IOException {  
    	FileInputStream is = null;  
        ImageInputStream iis = null;  
    
            // 读取图片文件  
            is = new FileInputStream(srcpath);  
  
            // 获取文件格式  
            String ext = srcpath.substring(srcpath.lastIndexOf(".") + 1);  
  
            // ImageReader声称能够解码指定格式  
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(ext);  
            ImageReader reader = it.next();  
  
            // 获取图片流  
            iis = ImageIO.createImageInputStream(is);  
  
            // 输入源中的图像将只按顺序读取  
            reader.setInput(iis, true);  
  
            // 描述如何对流进行解码  
            ImageReadParam param = reader.getDefaultReadParam();  
  
            // 图片裁剪区域  
            Rectangle rect = new Rectangle(x, y, width, height);  
  
            // 提供一个 BufferedImage，将其用作解码像素数据的目标  
            param.setSourceRegion(rect);  
  
            // 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象  
            BufferedImage bi = reader.read(0, param);  
  
            // 保存新图片  
            File tempOutFile = new File(subpath);  
            if (!tempOutFile.exists()) {  
                tempOutFile.mkdirs();  
            }  
            ImageIO.write(bi, ext, new File(subpath));  
           
       
  
    }  
    
}
