package com.xy.admx.core.common;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/***
 * java调用js相关
 * @author xiaojun
 *
 */
public class JsUtil {
	private JsUtil(){

	}
	public static Object eval(String eval){
		try{
			ScriptEngineManager manager = new ScriptEngineManager();  
			ScriptEngine engine = manager.getEngineByName( "JavaScript" );  
			return engine.eval(eval);
		}
		catch(ScriptException e){
			throw new RuntimeException(e);
		}
	}
}
