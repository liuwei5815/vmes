package com.xy.admx.rest;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xy.admx.common.base.PageQEntity;
import com.xy.admx.common.exception.RestException;
import com.xy.admx.rest.base.BaseRest;
import com.xy.admx.rest.base.ResponseCode;
import com.xy.admx.rest.base.SuccessResponse;
import com.xy.admx.service.OrdersService;

/**
 * 订单销售相关业务
 * 
 * @author dg
 *
 */
@RestController
@RequestMapping("/orders")
public class OrdersRest extends BaseRest {

	@Resource
	private OrdersService ordersService;

	/**
	 * 查询销售定单
	 * 
	 * @param request
	 * @param jsonObject
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public SuccessResponse query(HttpServletRequest request, @RequestBody Map requests, PageQEntity pageEntity)
			throws Exception {
		Map queryMap = new HashMap();
		if(!requests.containsKey("state")){
			throw new RestException(ResponseCode.ILLEGAL_PARAM, "缺少state参数");
		}
		ordersService.queryOrdersPage(pageEntity, requests);
		return new SuccessResponse(pageEntity);
	}
}
