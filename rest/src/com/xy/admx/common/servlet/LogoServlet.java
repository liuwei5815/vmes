package com.xy.admx.common.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.xy.admx.common.CacheFun;
import com.xy.admx.common.CommonFunction;

public class LogoServlet extends HttpServlet{

	private static final Logger logger = Logger
			.getLogger(LogoServlet.class);

			/**
			 * Constructor of the object.
			 */
			public LogoServlet() {
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
				String type = request.getParameter("type");
				String picPath=request.getParameter("path");
				String imgType = request.getParameter("imgType");
				if(imgType!=null && imgType.equals("sl")){
					String begin = fileName.substring(0,fileName.lastIndexOf("\\")+1);
					String end = fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
					end = "sl_"+end;
					fileName = begin + end;
				}
				if(picPath==null||picPath==""){
					picPath="pic_path";
				}
				String path=null;
				if(CommonFunction.isNotNull(type)){
					path=CacheFun.getConfig(picPath)+File.separator+CacheFun.getConfig(type);
				}else{
					path=CacheFun.getConfig(picPath);
				}
				File downloadFile = new File(path+File.separator+fileName);
				if (downloadFile.exists()) {
					resp.setContentType("application/x-msdownload");
					Long length = downloadFile.length();
					resp.setContentLength(length.intValue());
					ServletOutputStream servletOutputStream = resp.getOutputStream();
					FileInputStream fileInputStream = new FileInputStream(downloadFile);
					BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
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
