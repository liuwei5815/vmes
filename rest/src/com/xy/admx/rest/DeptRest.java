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
import com.xy.admx.service.DeptService;
/**
 * 部门
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/dept")
public class DeptRest extends BaseRest{
	@Resource
	private DeptService deptService;
	
	/**
	 * 查询所有部门 只支持两级
	 * @param request
	 * @param requests
	 * @param pageEntity
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/allDept",method=RequestMethod.GET)
	public SuccessResponse query()
			throws Exception {
		return new SuccessResponse(deptService.getNormalDepartment());
	}

}
