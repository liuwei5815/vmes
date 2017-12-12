package com.xy.cms.common.licence;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xy.cms.common.ConfigBean;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class LoginLicence extends HttpServlet {
	protected void doGet(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse)
			throws ServletException, IOException {
		HttpSession localHttpSession = paramHttpServletRequest.getSession(true);
		String str1 = localHttpSession.getId();
		synchronized (str1) {
			paramHttpServletResponse.setHeader("Pragma", "No-cache");
			paramHttpServletResponse.setHeader("Cache-Control", "no-cache");
			paramHttpServletResponse.setDateHeader("Expires", 0L);
			String str2 = ConfigBean.getStringValue("licence_content_type_type");
			int i = ConfigBean.getIntValue("licence_x");
			int j = ConfigBean.getIntValue("licence_y");
			String str3 = ConfigBean.getStringValue("licence_fontColor");
			int k = ConfigBean.getIntValue("licence_fontSize");
			int l = ConfigBean.getIntValue("licence_width");
			int i1 = ConfigBean.getIntValue("licence_height");
			String str4 = _$1();
			localHttpSession.setAttribute("loginlicenceSesion", str4);
			String str5 = str4;
			paramHttpServletResponse.setContentType(str2);
			BufferedImage localBufferedImage = new BufferedImage(l, i1, 1);
			Graphics localGraphics = localBufferedImage.getGraphics();
			localGraphics.setColor(new Color(255, 255, 255));
			localGraphics.fillRect(0, 0, l, i1);
			int i2 = (int) (Math.random() * 200.0D);
			int[] arrayOfInt1 = new int[i2];
			int[] arrayOfInt2 = new int[i2];
			for (int i3 = 0; i3 < arrayOfInt1.length; ++i3) {
				arrayOfInt1[i3] = (int) (Math.random() * 200.0D);
				arrayOfInt2[i3] = (int) (Math.random() * 200.0D);
			}
			localGraphics.setColor(_$2());
			localGraphics.drawPolyline(arrayOfInt1, arrayOfInt2,arrayOfInt1.length);
			localGraphics.setColor(new Color(102, 102, 102));
			localGraphics.drawRect(0, 0, l - 1, i1 - 1);
			localGraphics.setColor(new Color(Integer.parseInt(str3, 16)));
			Font localFont1 = MyFont.getFont();
			Font localFont2 = localFont1.deriveFont(0, k);
			localGraphics.setFont(localFont2);
			localGraphics.drawString(str5, i, j);
			ServletOutputStream localServletOutputStream = paramHttpServletResponse.getOutputStream();
			JPEGImageEncoder localJPEGImageEncoder = JPEGCodec.createJPEGEncoder(localServletOutputStream);
			localJPEGImageEncoder.encode(localBufferedImage);
			localServletOutputStream.close();
		}
	}

	Color _$1(int paramInt1, int paramInt2) {
		Random localRandom = new Random();
		if (paramInt1 > 255)
			paramInt1 = 255;
		if (paramInt2 > 255)
			paramInt2 = 255;
		int i = paramInt1 + localRandom.nextInt(paramInt2 - paramInt1);
		int j = paramInt1 + localRandom.nextInt(paramInt2 - paramInt1);
		int k = paramInt1 + localRandom.nextInt(paramInt2 - paramInt1);
		return new Color(i, j, k);
	}

	Color _$2() {
		Random localRandom = new Random();
		int i = localRandom.nextInt(3);
		i = localRandom.nextInt(3);
		int[][] arrayOfInt = { { 0, 204, 255 }, { 153, 255, 0 },{ 255, 102, 255 }, { 255, 51, 51 }, { 255, 255, 0 } };
		return new Color(arrayOfInt[i][0], arrayOfInt[i][1], arrayOfInt[i][2]);
	}

	String _$1() {
		Random localRandom = new Random();
		String str = "";
		for (int i = 0; i < 4; ++i)
			str = str + localRandom.nextInt(10);
		return str;
	}

	public static String getTheLicence(HttpServletRequest request) {
		String str = (String)request.getSession().getAttribute("loginlicenceSesion");
		request.getSession().removeAttribute("loginlicenceSesion");
		if (str == null)
			return "";
		return str;
	}

	public static void main(String[] paramArrayOfString) {
		Random localRandom = new Random();
		int i = localRandom.nextInt(3);
		System.out.println(i);
	}
}