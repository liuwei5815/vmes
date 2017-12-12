package com.xy.admx.common;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.xy.admx.common.exception.BusinessException;

public class HttpClient {
	
	public static String doGet(String baseurl, Map<String, String> headersMap, Map<String, String> param) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		URIBuilder uri = new URIBuilder(baseurl);
		if (param != null) {
			Iterator<String> iterator = param.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				uri.addParameter(key, param.get(key));
			}
		}
		try {
			HttpGet httpGet = new HttpGet(uri.build());
			if (headersMap != null) {
				Iterator<String> iterator = headersMap.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					String header = headersMap.get(key);
					httpGet.setHeader(new BasicHeader(key, header));
				}
			}
			CloseableHttpResponse response = httpclient.execute(httpGet);
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				String resstr = (entity != null ? EntityUtils.toString(entity) : null);
				return resstr;
			} else {
				throw new BusinessException("调用文件服务接口失败 " + status);
			}
		} finally {
			httpclient.close();
		}
	}
	
	public static String doPostMultipart(String baseurl, Map<String, String> headersMap, Map<String, Object> multipartEntityMap) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		URIBuilder uri = new URIBuilder(baseurl);
		try {
			HttpPost post = new HttpPost(uri.build());
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			
			if (multipartEntityMap != null) {
				Iterator<String> iterator = multipartEntityMap.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					Object object = multipartEntityMap.get(key);
					if (object instanceof String) {
						StringBody stringBody = new StringBody((String)object, ContentType.MULTIPART_FORM_DATA);
						builder.addPart(key, stringBody);
					} else if (object instanceof String[]) {
						for (String fileName : (String[])object) {
							StringBody stringBody = new StringBody(fileName, ContentType.MULTIPART_FORM_DATA);
							builder.addPart(key, stringBody);
						}
					} else if (object instanceof File) {
						FileBody fileBody = new FileBody((File)object);
						builder.addPart(key, fileBody);
					} else if (object instanceof File[]) {
						for (File file : (File[])object) {
							FileBody fileBody = new FileBody(file);
							builder.addPart(key, fileBody);
						}
					}
				}
			}
			HttpEntity entity = builder.build();
			
			post.setEntity(entity);
			if (headersMap != null) {
				Iterator<String> iterator = headersMap.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					String header = headersMap.get(key);
					post.setHeader(new BasicHeader(key, header));
				}
			}
			
			HttpResponse response = httpclient.execute(post);
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity httpEntity = response.getEntity();
				String resstr = (httpEntity != null ? EntityUtils.toString(httpEntity,"utf-8") : null);
				return resstr;
			} else {
				throw new BusinessException("调用文件服务接口失败 " + status);
			}
		} finally {
			httpclient.close();
		}
	}

	public static void main(String[] args) {
		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("accessKey", "12345");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("files", new File[]{new File("C:/cpsweb.log")});
		param.put("fileNames", new String[]{"cpsweb.log"});
		try {
			String resstr = HttpClient.doPostMultipart("http://localhost:8080/admxfile/files/upload/dir12345", headersMap, param);
			System.out.println(resstr);
//			String resstr = HttpClient.doGet("http://localhost:8080/admxfile/files/delete/6167cc4da4904691aa65f8535f066f42", headersMap, null);
//			System.out.println(resstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
