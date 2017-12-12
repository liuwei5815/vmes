package com.xy.cms.common.servlet;
	
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xy.cms.common.CacheFun;
import com.xy.cms.common.CachePool;



public class TracingPicServlet extends HttpServlet {


	/**
	 * Constructor of the object.
	 */
	public TracingPicServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */

	public void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		StringBuffer sb = new StringBuffer(CacheFun.getConfig("tracing_plan_img_path"));
		sb.append(fileName);
		File downloadFile = new File(sb.toString());
		if (downloadFile.exists()) {
			resp.setContentType("application/x-msdownload");
			Long length = downloadFile.length();
			resp.setContentLength(length.intValue());
			ServletOutputStream servletOutputStream = resp
					.getOutputStream();
			FileInputStream fileInputStream = new FileInputStream(	
					downloadFile);
			BufferedInputStream bufferedInputStream = new BufferedInputStream(
					fileInputStream);
			int size = 0;
			byte[] b = new byte[4096];
			while ((size = bufferedInputStream.read(b)) != -1) {
				servletOutputStream.write(b, 0, size);
			}
			servletOutputStream.flush();
			servletOutputStream.close();
			bufferedInputStream.close();
		
		}
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
