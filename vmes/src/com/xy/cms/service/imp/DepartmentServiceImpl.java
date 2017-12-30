package com.xy.cms.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Department;
import com.xy.cms.service.DepartmentService;

public class DepartmentServiceImpl extends BaseDAO implements DepartmentService{

	public List<Department> getNormalDepartment(){
		String hql = " from Department where status=1 order by pid,orderby ";
		return this.find(hql.toString());
	}

	@Override
	public void addDepartment(Department department) throws BusinessException {
		if(CommonFunction.isNotNull(department)){
			Department newDepartment = new Department();

			newDepartment.setPid(department.getPid());
			newDepartment.setType(department.getType());
			if(department.getPid()==0){//一级部门
				newDepartment.setLevel(1);
			}else if(department.getPid()!=0){
				if(this.getDepartmentById(department.getPid()).getPid()==0){
					newDepartment.setLevel(2);//二级部门
				}else{
					newDepartment.setLevel(3);
				}
			}
			
			if(department.getPid()==0){//一级部门
//				newDepartment.setNumber(number);
//				newDepartment.setLongnumber(parentDepartment.getLongnumber()+"_"+number);
//				newDepartment.setName(department.getName());
//				newDepartment.setPath(parentDepartment.getPath()+""+department.getName());	
			}else {
				
			}
			Department parentDepartment = this.getDepartmentById(department.getPid());
//			if() {
//				
//			}
		
			newDepartment.setStatus((short) 1);
			newDepartment.setAddDate(new Date());
			this.save(newDepartment);
			newDepartment.setOrderby(getMaxOrderOfSubnodes(department.getPid()) + 1);
			this.update(newDepartment);
		}
	}
	@Override
	public Long addDepartmentWithId(Department department) throws BusinessException {
		if(CommonFunction.isNotNull(department)){
			Department newDepartment = new Department();
			newDepartment.setName(department.getName());
			newDepartment.setPid(department.getPid());
			newDepartment.setStatus((short) 1);
			newDepartment.setAddDate(new Date());
			this.save(newDepartment);
			newDepartment.setOrderby(getMaxOrderOfSubnodes(department.getPid()) + 1);
			this.update(newDepartment);
			return newDepartment.getId();
		} 
		return null;
	}
	/**
	 * 获取同级下最大的排序号
	 * @param pid
	 * @return
	 */
	private Integer getMaxOrderOfSubnodes(Long pid) {
		String hql = "select max(orderby) from Department where pid=:pid";
		Map map = new HashMap();
		map.put("pid", pid);
		List list = this.getList(hql,map);
		Integer orderby = 0;
		if(list.get(0) != null){
			orderby = Integer.valueOf(list.get(0).toString());
		}
		return orderby;
	}

	@Override
	public Department getDepartmentById(Long id) throws BusinessException {
		return (Department) this.get(Department.class, id);
	}

	@Override
	public void updateDepartment(Department department)
			throws BusinessException {
		if(CommonFunction.isNotNull(department)){
			this.update(department);
		}
		
	}

	@Override
	public List<Department> getDepartmentByPid(Long pid)
			throws BusinessException {
		String hql = " from Department where status=1 and pid=:pid order by pid,orderby ";
		Map<String,Object> mapParam = new HashMap<String, Object>();
		mapParam.put("pid", pid);
		return this.getList(hql.toString(),mapParam);
	}

	@Override
	public void delDepartment(Department department) throws BusinessException {
		if(CommonFunction.isNull(department)){
			throw new BusinessException("参数错误");
		}
		this.delete(department);
		
	}

	@Override
	public void upOrDownDepartment(Long id, String sign)
			throws BusinessException {
		if (CommonFunction.isNull(id)) {
			throw new BusinessException("参数错误");
		}
		if (CommonFunction.isNull(sign)) {
			throw new BusinessException("参数错误");
		}
		Department department = getDepartmentById(id);
		if(sign.equals("up")){
			//找到排在该部门之前的那个部门的排序
			String hql = "from Department where id<>:deptId and pid=:pid and orderby<:orderby order by orderby desc";
			Map map = new HashMap();
			map.put("deptId",id);
			map.put("pid",department.getPid());
			map.put("orderby", department.getOrderby());
			List<Department> preDept = this.getList(hql, map);
			department.setOrderby(preDept.get(0).getOrderby());
			//当同级节点的排序都下移
			String hql2 = "update Department set orderby=orderby+1 where id<>:deptId and pid=:pid and orderby>=:orderby";
			map = new HashMap();
			map.put("deptId",id);
			map.put("pid",department.getPid());
			map.put("orderby", department.getOrderby());
			this.execute(hql2, map);
		}else if(sign.equals("down")){
			//找到排在该部门之后的那个部门的排序
			String hql = "from Department where id<>:deptId and pid=:pid and orderby>:orderby order by orderby";
			Map map = new HashMap();
			map.put("deptId",id);
			map.put("pid",department.getPid());
			map.put("orderby", department.getOrderby());
			List<Department> preDept = this.getList(hql, map);
			department.setOrderby(preDept.get(0).getOrderby());
			//当同级节点的排序都上移
			String hql2 = "update Department set orderby=orderby-1 where id<>:deptId and pid=:pid and orderby<=:orderby";
			map = new HashMap();
			map.put("deptId",id);
			map.put("pid",department.getPid());
			map.put("orderby", department.getOrderby());
			this.execute(hql2, map);
		}
		
	}

	@Override
	public boolean departmentName(String departmentName, Long pId) {
		String hql = "select count(d.id) from Department d where d.pid=:pId and d.name=:departmentName";
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("departmentName", departmentName);
		param.put("pId", pId);
		int sum = this.getQueryCount(hql, param);
		return sum>0?false:true;
	}

	@Override
	public Department getDepartmentByName(String name) {
		String hql="from Department d where d.name=:name";
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("name", name);
		Object de=this.getUniqueResult(hql, param);
		return de==null?null:(Department)de;
	}

	@Override
	public String getDepartmentByEmpId(Long eid) {
		String sql="SELECT dept.name  FROM `sys_department` as dept WHERE dept.id =(SELECT ed.depart_id FROM `sys_employee_deptart` as ed WHERE ed.employee_id=:eid)";
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("eid", eid);
		return (String)this.getUniqueResultSql(sql, param);
	}

	@Override
	public Department getDepartmentByNameAndPid(String name, Long pid) {
		String hql="from Department d where d.name=:name and d.pid=:pid";
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("name", name);
		param.put("pid", pid);
		Object de=this.getUniqueResult(hql, param);
		return de==null?null:(Department)de;
	}
}
