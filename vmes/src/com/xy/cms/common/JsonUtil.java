package com.xy.cms.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson; 
import com.xy.cms.common.taglib.Tree;

public class JsonUtil {
	
	public static void main(String[] args) throws IOException {
		/*String a= "var tree\\dvr4".replace("\\","");
		System.out.println(a);*/
		getTree();
	}
	public static String rep(String id){
		return id.replace("=","").replace("\\","");
	}
	public static List getTree(){
		List<Tree> treeList = null;
		BufferedReader bufferedReader = null;
		try {
			Reader reader = new FileReader(new File("D://json.txt"));
			bufferedReader = new BufferedReader(reader);
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				sb.append(str);
			}
 			JSONObject json = new JSONObject(sb.toString());
			treeList = new ArrayList<Tree>();
			Tree tree = new Tree();
	
			
			
			String rootId = rep(json.getString("oid"));
			String rootName = json.getString("name");
			tree.setId("00");
			tree.setText(rootName);
			treeList.add(tree);
			JSONArray jsonArray = json.getJSONArray("child");
			int two = 0;
			int three = 0;
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				two++;
				String id = rep(object.getString("oid"));
				String name = object.getString("name");
				Tree child = new Tree();
				child.setId(id);
				child.setText(name);
				child.setParentId(rootId);
				treeList.add(child);
				JSONArray childArray = object.getJSONArray("child");
				for (int j = 0; j < childArray.length(); j++) {
					JSONObject o = childArray.getJSONObject(j);
					three++;
					String chiid = rep(o.getString("oid"));
					String chiname = o.getString("name");
					Tree chi = new Tree();
					chi.setId(chiid);
					chi.setText(chiname);
					chi.setParentId(id);
					treeList.add(chi);
				}

			}
			System.out.println(treeList.size());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return treeList;
	}
	

	/**
	 * 将json字符串解析成bean 对象
	 * 
	 * @param jsonStr
	 *            json String
	 * @param bean
	 *            对象
	 * @return
	 */
	public static <T> T json2Bean(String jsonStr, Class<T> bean) {
		Gson gson = new Gson();

		// 过滤
		boolean empty = isEmpty(jsonStr);
		if (empty) {
			return null;
		}
		T t = null;
		try {
			t = gson.fromJson(jsonStr, bean);
		} catch (com.google.gson.JsonSyntaxException e) {
			// throw new RuntimeException("Json解析异常：");
		}
		return t;
	}

	/**
	 * 将json字符串解析成list
	 * 
	 * @param jsonStr
	 * @param type
	 *            泛型类型
	 * @return
	 */
	public static <T> List<T> json2List(String jsonStr, Type type) {

		Gson gson = new Gson();
		return gson.fromJson(jsonStr, type);

	}

	public static boolean isEmpty(String s) {
		if (null == s)
			return true;
		if (s.length() == 0)
			return true;
		if (s.trim().length() == 0)
			return true;
		return false;
	}

}
