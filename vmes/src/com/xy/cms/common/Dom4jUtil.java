package com.xy.cms.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dom4jUtil {
	
	public static Logger logger = Logger.getLogger(Dom4jUtil.class);

	/**
	 * 根据指定的XML文档创建Document
	 * @param paramString
	 * @return
	 * @throws Exception
	 */
	public static Document getDocument(File xmlFile) throws Exception {
		try {
			SAXReader localSAXReader = new SAXReader();
			Document localDocument = localSAXReader.read(xmlFile);
			return localDocument;
		} catch (DocumentException e) {
			throw new Exception("创建Document时发生错�?file:"+xmlFile.getAbsolutePath(), e);
		}
	}
	
	/**
	 * 根据指定的XML文档创建Document
	 * @param paramString
	 * @return
	 * @throws Exception
	 */
	public static Document getDocument(String filepath) throws Exception {
		try {
			SAXReader localSAXReader = new SAXReader();
			Document localDocument = localSAXReader.read(new File(filepath));
			return localDocument;
		} catch (DocumentException e) {
			throw new Exception("创建Document时发生错�?filepath:"+filepath, e);
		}
	}
	
	/**
	 * 根据字符串XML创建Document
	 * @param strXml
	 * @return
	 * @throws Exception
	 */
	public static Document getDocumentByStrXML(String strXml) throws Exception {
		try {
			SAXReader localSAXReader = new SAXReader();
			Document localDocument = localSAXReader.read(new ByteArrayInputStream(strXml.getBytes()));
			return localDocument;
		} catch (DocumentException e) {
			throw new Exception("创建Document时发生错�?strXml:"+strXml, e);
		}
	}
	
	   

	
	/**
	 * 根据输入流创建Document
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static Document getDocument(InputStream in) throws Exception {
		try {
			SAXReader localSAXReader = new SAXReader();
			Document localDocument = localSAXReader.read(in);
			return localDocument;
		} catch (DocumentException e) {
			throw new Exception("创建Document时发生错�?", e);
		}
	}

	public static void main(String[] paramArrayOfString) throws Exception {
		String xml = "<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\"?><LoginResp><LoginRespPara Random=\"abcdfds\" /></LoginResp>";
		Document doc = Dom4jUtil.getDocumentByStrXML(xml);
		List list = doc.selectNodes("//LoginRespPara");
		Element ele = (Element)list.get(0);
		
		System.out.println(ele.attributeValue("Random"));
	}
}
