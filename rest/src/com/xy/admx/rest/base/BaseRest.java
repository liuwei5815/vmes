package com.xy.admx.rest.base;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.exception.DataException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.loader.custom.NonUniqueDiscoveredSqlAliasException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.xy.admx.common.exception.BusinessException;
import com.xy.admx.common.exception.RestException;
import com.xy.admx.common.springmvc.XyJsonMapper;
import com.xy.apisql.exception.ApiSqlException;

public abstract class BaseRest {
	private Logger logger = Logger.getLogger(this.getClass());

	@ExceptionHandler({ RestException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public void restExceptionHandler(HttpServletResponse response, RestException e) {
		response.setCharacterEncoding("utf-8");
		logger.error(e.getMessage(), e);
		try {
			new XyJsonMapper().writeValue(response.getWriter(), new Response(e.getCode(), e.getMessage()));
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}
	
	@ExceptionHandler({ GenericJDBCException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public void GenericJDBCExceptionHandler(HttpServletResponse response, GenericJDBCException e) {
		response.setCharacterEncoding("utf-8");
		logger.error(e.getMessage(), e);
		try {
			new XyJsonMapper().writeValue(response.getWriter(), new Response(ResponseCode.SQL_EXCEPTION.getCode(), e.getCause().getMessage()));
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}
	
	@ExceptionHandler({ ApiSqlException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public void apiSqlExceptionHandler(HttpServletResponse response, ApiSqlException e) {
		response.setCharacterEncoding("utf-8");
		logger.error(e.getMessage(), e);
		try {
			new XyJsonMapper().writeValue(response.getWriter(), new Response(ResponseCode.SQL_EXCEPTION.getCode(), e.getMessage()));
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}
	
	@ExceptionHandler({ NonUniqueDiscoveredSqlAliasException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public void NonUniqueDiscoveredSqlAliasExceptionHandler(HttpServletResponse response, NonUniqueDiscoveredSqlAliasException e) {
		response.setCharacterEncoding("utf-8");
		logger.error(e.getMessage(), e);
		try {
			new XyJsonMapper().writeValue(response.getWriter(), new Response(ResponseCode.SQL_EXCEPTION.getCode(), e.getMessage()));
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}
	
	@ExceptionHandler({ SQLGrammarException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public void sQLGrammarExceptionHandler(HttpServletResponse response, SQLGrammarException e) {
		response.setCharacterEncoding("utf-8");
		logger.error(e.getMessage(), e);
		try {
			new XyJsonMapper().writeValue(response.getWriter(), new Response(ResponseCode.SQL_EXCEPTION.getCode(), e.getMessage() + "," + e.getCause().getMessage()));
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}
	
	@ExceptionHandler({ DataException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public void DataExceptionHandler(HttpServletResponse response, DataException e) {
		response.setCharacterEncoding("utf-8");
		logger.error(e.getMessage(), e);
		try {
			new XyJsonMapper().writeValue(response.getWriter(), new Response(ResponseCode.SQL_EXCEPTION.getCode(), e.getMessage() + "," + e.getCause().getMessage()));
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}

	@ExceptionHandler({ BusinessException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public void businessExceptionHandler(HttpServletResponse response, BusinessException e) {
		response.setCharacterEncoding("utf-8");
		logger.error(e.getMessage(), e);
		try {
			new XyJsonMapper().writeValue(response.getWriter(), new Response(ResponseCode.BUSSINESS_EXCEPTION.getCode(), e.getMessage()));
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}
	
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public void methodNotSupportedExceptionHandler(HttpServletResponse response, HttpRequestMethodNotSupportedException e) {
		response.setCharacterEncoding("utf-8");
		logger.error(e.getMessage(), e);
		try {
			new XyJsonMapper().writeValue(response.getWriter(), new Response(ResponseCode.ILLEGAL_METHOD));
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}
	@ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public void mediaTypeNotSupportedExceptionHandler(HttpServletResponse response, HttpMediaTypeNotSupportedException e) {
		response.setCharacterEncoding("utf-8");
		logger.error(e.getMessage(), e);
		try {
			new XyJsonMapper().writeValue(response.getWriter(), new Response(ResponseCode.MEDIATYPE_NOTSupported));
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}
	
	@ExceptionHandler({HttpMessageNotReadableException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public void jsonParseExceptionHandler(HttpServletResponse response, HttpMessageNotReadableException e) {
		response.setCharacterEncoding("utf-8");
		logger.error(e.getMessage(), e);
		try {
			new XyJsonMapper().writeValue(response.getWriter(), new Response(ResponseCode.ILLEGAL_BODY));
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}
	
	
	@ExceptionHandler({ Exception.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public void exceptionHandler(HttpServletResponse response, Exception e) {
		logger.error(e.getMessage(), e);
		try {
			new XyJsonMapper().writeValue(response.getWriter(),	new ErrorResponse(ResponseCode.GENERAL_ERROR));
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}
}
