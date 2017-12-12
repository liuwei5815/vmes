package com.xy.admx.common.picvertify;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

/***
 * 验证码生成器
 * @author Administrator
 *
 */
public class ValidateCode {
	private static final int DEFAULT_WIDTH = 80;
	private static final int DEFAULT_HEIGHT = 34;
	private static final int codeCount = 4;  
	private int width = 160;  
	// 图片的高度。  
    private int height = 40;  
    // 验证码干扰线数  
    private static final  int lineCount = 120;  
    //验证码数字
    private String code;
    //构造方法
    public ValidateCode(){
    	this(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }
    public ValidateCode(int width,int height){
    	this.width=width;
    	this.height=height;
    	this.createCode();
    }
    //验证码图片Buffer
    private BufferedImage buffImg=null;  
  
    private char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',  
            'K', 'L', 'M', 'N',  'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',  
            'X', 'Y', 'Z',  '1', '2', '3', '4', '5', '6', '7', '8', '9' };  

    	public void createCode() {  
        //三原色 在干扰素以及字体颜色会用到
        int red = 0, green = 0, blue = 0;  
         
        int x = width / (codeCount +2);//每个字符的宽度  
        int fontHeight = height - 2;//字体的高度  
          
        // 图像buffer  
        buffImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        //画笔
        Graphics2D g = buffImg.createGraphics();  
        // 生成随机数  
        Random random = new Random();  
        // 将图像填充为灰色  
        g.setColor(new Color(0xDCDCDC));  
        g.fillRect(0, 0, width, height);  //4个参数 起始X坐标，起始Y坐标，宽度，高度 
        //干扰素
        for (int i = 0; i < lineCount; i++) {  
            int xs = (int) (Math.random() * width);  
            int ys = (int) (Math.random() * height);  
            red = (int) (Math.random() * 255); 
            green = (int) (Math.random() * 255);
            blue = (int) (Math.random() * 255);  
            g.setColor(new Color(red, green, blue));  
            //g.drawLine(xs, ys, 1, 0);//线   //起点X坐标，起点Y坐标  终点X坐标，终点Y坐标
            g.drawOval(xs, ys, 1, 0);//椭圆   //参数 左上角的x坐标，y坐标，宽，高
        }  
          
        // randomCode记录随机产生的验证码  
        StringBuffer randomCode = new StringBuffer();  
        // 随机产生codeCount个字符的验证码。  
        for (int i = 0; i < codeCount; i++) {  
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);  
            
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。  
            /*red = random.nextInt(255);  
            green = random.nextInt(255);  
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));*/
            
            //字设置为黑色
            g.setColor(Color.BLACK);
            //字体
            g.setFont(new Font(null, Font.ITALIC | Font.BOLD, 18));
            //字的位置
            g.drawString(strRand, (i + 1) * x, 23);  //字，X坐标，Y坐标
            //将产生的四个随机数组合在一起。  
            randomCode.append(strRand);  
        }  
        // 将四位数字的验证码保存到Session中。  
        code=randomCode.toString();    
    	}
    	
     
      
    public void write(String path) throws IOException {  
        OutputStream sos = new FileOutputStream(path);  
            this.write(sos);  
    }  
      
    public void write(OutputStream sos) throws IOException {  
            ImageIO.write(buffImg, "png", sos);  
            sos.close();  
    }  
    public BufferedImage getBuffImg() {  
        return buffImg;  
    }  
    
    public String getCode() {  
        return code;  
    }  
}  
