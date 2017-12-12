package com.xy.admx.common.springmvc;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 重写ObjectMapper
 * 
 * @author xiaojun yang
 *
 */
public class XyJsonMapper extends ObjectMapper {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULTDATEFORMATE = "yyyy-MM-dd HH:mm:ss";

	public XyJsonMapper() {

		super();
		// 允许单引号
		this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		// 字段和值都加引号
		this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		// 数字也加引号
		this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
		this.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
		// 空值处理为空串

		this.getSerializerProvider().setNullValueSerializer(new NullValueJsonSerialize());
		setDateFormat(new SimpleDateFormat(DEFAULTDATEFORMATE));

	}

	public static class NullValueJsonSerialize extends JsonSerializer<Object> {

		@Override
		public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2)
				throws IOException, JsonProcessingException {
			arg1.writeString("");

		}

	}
}
