package com.xy.cms.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;

public class DBBackupUtil {
	
	private static Logger logger = Logger.getLogger(DBBackupUtil.class);

	/**
	 * 备份mysql数据�?
	 * @param backup_path	备份�?件路�?
	 * @param backup_name 备份文件�?
	 * @param host	数据库主机地�?
	 * @param dbuser	数据库账�?
	 * @param dbpwd		数据库密�?
	 * @param dbname	数据库名
	 * @throws Exception
	 */
	public static void backupMySql(String backup_path,String backup_name,String host,String dbuser,String dbpwd,String dbname) throws Exception{
		FileOutputStream out = null;
		 OutputStreamWriter writer = null;
		 InputStreamReader input = null;
		 InputStream in = null;
		 BufferedReader br = null;
		try{
			File backup_path_file = new File(backup_path);
			if(!backup_path_file.exists()){
				backup_path_file.mkdirs();
			}
			Runtime rt = Runtime.getRuntime();
	        String fPath= backup_path + File.separator + backup_name;
	        String command= SysConfig.getStrValue("mysql_bin_path") +  "mysqldump -h" + host +" -u" + dbuser + " -p" + dbpwd + " " + dbname;
	        logger.info("command:" + command);
	        //调用 mysql的cmd:     
	        Process child = rt.exec(command);// 设置导出编码为utf8。这里必须是utf8     
	        //把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件�?注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行     
	        in = child.getInputStream();// 控制台的输出信息作为输入�?    
	        input = new InputStreamReader(in,"utf8");// 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码                   
	        String inStr;     
	        StringBuffer sb = new StringBuffer("");     
	        String outStr;     
	        //组合控制台输出信息字符串     
	        br = new BufferedReader(input);     
	        while ((inStr = br.readLine()) != null) {     
	            sb.append(inStr + "\r\n");     
	        }     
	        outStr = sb.toString();                
	        //要用来做导入用的sql目标文件�?    
	        out = new FileOutputStream(fPath);     
	        writer = new OutputStreamWriter(out, "utf8");     
	        writer.write(outStr);     
	        //这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避�?    
	        writer.flush();     
	        //关闭输入输出�?    
		}finally{
			if(in != null){
				in.close();    
			}
			if(input != null){
				input.close();    
			}
			if(br != null){
				br.close();
			}
	        if(writer != null){
				writer.close();
			}
			if(out != null){
				out.close();
			}
		}
	}
	
	/**
	 * MySql还原
	 * @param backupFilePath
	 * @param host
	 * @param dbuser
	 * @param dbpwd
	 * @param dbname
	 * @throws Exception
	 */
	public static void restoreMySql(File backupFile,String host,String dbuser,String dbpwd,String dbname) throws Exception{
		OutputStream out = null;
		BufferedReader br = null;
		OutputStreamWriter writer = null;
		try {     
	           Runtime rt = Runtime.getRuntime();
	           String command= SysConfig.getStrValue("mysql_bin_path") + "mysql -h" + host +" -u" + dbuser + " -p" + dbpwd + " " + dbname;
	           logger.info("command:" + command);
	           // 调用mysql的cmd     
	           Process child = rt.exec(command);     
	           out = child.getOutputStream();//控制台的输入信息作为输出�?    
	           String inStr;     
	           StringBuffer sb = new StringBuffer("");     
	           String outStr;     
	           br = new BufferedReader(new InputStreamReader(new FileInputStream(backupFile), "utf8"));     
	           while ((inStr = br.readLine()) != null) {     
	               sb.append(inStr + "\r\n");     
	           }     
	           outStr = sb.toString();     
	           writer = new OutputStreamWriter(out,"utf8");     
	           writer.write(outStr);     
	           //这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避�?    
	           writer.flush();     
	       }finally{
	    	   //关闭输入输出�?    
	    	   if(out != null){
	    		   out.close();    
	    	   }
	    	   if(br != null){
	    		   br.close();    
	    	   }
	    	   if(writer != null){
	    		   writer.close(); 
	    	   }
	       }
	}
}
