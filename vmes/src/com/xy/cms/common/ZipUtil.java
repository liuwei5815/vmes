package com.xy.cms.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.rarfile.FileHeader;

/**
 * 
 * @author dinggang
 * @since 2008-07-28
 */
public class ZipUtil {
	
	/**
	 * 压缩
	 * @param zipFile
	 * @param destfile
	 * @throws IOException
	 */
	public void zip(File zipFile, String destfile) throws IOException {
		ZipOutputStream out = null;
		try{
			out = new ZipOutputStream(new FileOutputStream(zipFile));
			File f = new File(destfile);
			out.setEncoding("gbk");
			zip(out, f, "");
		}finally{
			if(out != null){
				out.close();
			}
		}
	}

	private void zip(ZipOutputStream out, File f, String base)
			throws IOException {
		if (f.isDirectory()) {
			base = base + f.getName() + "/";
			out.putNextEntry(new ZipEntry(base));
			File[] fl = f.listFiles();
			for (File ff : fl) {
				zip(out, ff, base);
			}
		} else {
			base = base + f.getName();
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			int b;
			System.out.println(base);
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
		}
	}
	
	/**
	 * @param zipFileName
	 *            指定压缩文件
	 * @param destDir
	 *            指定解压目录
	 * @throws Exception
	 */
	public static void unZip(File file, String destDir) throws Exception {
		InputStream in = null;
		OutputStream os = null;
		try {
			ZipFile zipFile = new ZipFile(file);
			Enumeration<?> e = zipFile.getEntries();
			ZipEntry zipEntry = null;
			File fD = new File(destDir);
			if (!fD.exists()) {
				fD.mkdir();
			}
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				String entryName = zipEntry.getName();
				String names[] = entryName.split("/");
				int length = names.length;
				String path = destDir;
				for (int v = 0; v < length; v++) {
					if (v < length - 1) {
						path += "/" + names[v];
						new File(path).mkdir();
					} else {
						if (entryName.endsWith("/")) {
							new File(destDir + "/" + entryName).mkdir();
						} else {
							in = zipFile.getInputStream(zipEntry);
							os = new FileOutputStream(new File(destDir + "/" + entryName));
							byte[] buf = new byte[1024];
							int len;
							while ((len = in.read(buf)) > 0) {
								os.write(buf, 0, len);
							}
						}
					}
				}
			}
			zipFile.close();

		}finally{
			if(in != null){
				in.close();
			}
			if(os != null){
				os.close();
			}
		}
	}
	
	/**
	 * 解压rar格式压缩包�?
	 * 对应的是java-unrar-0.3.jar，但是java-unrar-0.3.jar又会用到commons-logging-1.1.1.jar
	 */
	public static void unRarSingleFile(File rarFile, String destDir) throws Exception {
		Archive a = null;
		FileOutputStream fos = null;
		try {
			a = new Archive(rarFile);
			FileHeader fh = a.nextFileHeader();
			while (fh != null) {
				if (!fh.isDirectory()) {
					// 1 根据不同的操作系统拿到相应的 destDirName �?destFileName
					String compressFileName = fh.getFileNameString().trim();
					String destFileName = "";
					String destDirName = "";
					// 非windows系统
					if (File.separator.equals("/")) {
						destFileName = destDir + File.separator + compressFileName.replaceAll("\\\\", "/");
						destDirName = destFileName.substring(0, destFileName.lastIndexOf("/"));
						// windows系统
					} else {
						destFileName = destDir + File.separator + compressFileName.replaceAll("/", "\\\\");
						destDirName = destFileName.substring(0, destFileName.lastIndexOf("\\"));
					}
					// 2创建文件�?
					File dir = new File(destDirName);
					if (!dir.exists() || !dir.isDirectory()) {
						dir.mkdirs();
					}
					// 3解压缩文�?
					fos = new FileOutputStream(new File(destFileName));
					a.extractFile(fh, fos);
					fos.close();
					fos = null;
				}
				fh = a.nextFileHeader();
			}
			a.close();
			a = null;
		}finally {
			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (a != null) {
				try {
					a.close();
					a = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		try {
//			ZipUtil.zip("c:\\abc", "c:\\ddd\\src.zip");
//			System.out.println(System.getProperty("user.dir"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
