package com.xy.cms.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.MD5;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Admin;
import com.xy.cms.entity.AppUser;
import com.xy.cms.entity.Company;
import com.xy.cms.entity.Department;
import com.xy.cms.entity.Employee;
import com.xy.cms.entity.EmployeeDeptart;
import com.xy.cms.service.AdminService;
import com.xy.cms.service.AppUserService;
import com.xy.cms.service.EmployeeDeptartService;
import com.xy.cms.service.EmployeeService;
import com.xy.cms.service.SequenceService;

public class EmployeeServiceImpl extends BaseDAO implements EmployeeService{
	private SequenceService sequenceService;
	private AdminService adminService;
	private AppUserService appUserService;
	private EmployeeDeptartService employeeDeptartService;
	
	@Override
	public QueryResult queryEmployeePage(Map param) throws BusinessException {
		QueryResult result = null;
		BaseQEntity qEntity = (BaseQEntity) param.get("qEntity");
		StringBuffer hql = new StringBuffer("from Employee e where 1=1 ");
		Map<String, Object> m = new HashMap<String, Object>();
		result = this.getPageQueryResult(hql.toString(), m, qEntity);
		return result;
	}
	
	@Override
	public void addEmployee(Admin admin,AppUser appUser,Employee employee,Long departmentId) throws BusinessException {
		if(CommonFunction.isNotNull(employee)){
			/*String code=sequenceService.getNewNoByTableColumns(461L);*/
			employee.setAddDate(new Date());
			employee.setRid(4L);
			this.save(employee);
			
			EmployeeDeptart employeeDeptart = new EmployeeDeptart();
			employeeDeptart.setEmployeeId(employee.getId());
			employeeDeptart.setDepartId(departmentId);
			this.save(employeeDeptart);
			
			//保存admin
			if(CommonFunction.isNotNull(admin)){
				if(CommonFunction.isNull(admin.getPwd())){
					admin.setPwd(MD5.MD5("123456"));
				}
				admin.setPwd(MD5.MD5(admin.getPwd()));
				admin.setAccount(employee.getSerialNo());
				admin.setStatus(new Short("1"));
				admin.setAddDate(new Date());
				admin.setEmpId(employee.getId());
				this.save(admin);
			}
			
			//保存appUser
			if(CommonFunction.isNotNull(appUser)){
				if(CommonFunction.isNull(appUser.getPassword())){
					appUser.setPassword(MD5.MD5("123456"));
				}
				appUser.setPassword(MD5.MD5(appUser.getPassword()));
				appUser.setStatus(new Short("1"));
				appUser.setAccount(employee.getSerialNo());
				appUser.setAddDate(new Date());
				appUser.setEmpId(employee.getId());
				this.save(appUser);
			}
		}
	}

	public SequenceService getSequenceService() {
		return sequenceService;
	}

	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	@Override
	public Employee getEmployeeById(Long id)  {
		return (Employee) this.get(Employee.class, id);
	}

	@Override
	public void updateEmployee(Employee employee,Long deptId) throws BusinessException {
		if(CommonFunction.isNotNull(employee)){
			this.update(employee);
		}
		EmployeeDeptart ed=this.getEdById(employee.getId());
		ed.setDepartId(deptId);
		
	}

	@Override
	public void delEmployee(Employee employee) throws BusinessException {
		if(CommonFunction.isNotNull(employee)){
			//得到admin
			Admin admin = adminService.getAdminByEmpId(employee.getId());
			if(CommonFunction.isNotNull(admin)){
				this.delete(admin);
			}
			//得到appUser
			AppUser appUser = appUserService.getAppUserByEmpId(employee.getId()); 
			if(CommonFunction.isNotNull(appUser)){
				this.delete(appUser);
			}
			//去删掉sys_employee_deptart里面的数据
			EmployeeDeptart employeeDeptart = employeeDeptartService.getEmployeeDeptartByEid(employee.getId());
			if(CommonFunction.isNotNull(employeeDeptart)){
				this.delete(employeeDeptart);
			}
			
			this.delete(employee);
		}
	}
	
	@Override
	public boolean provingEmployeeSerialNo(String employeeSerialNo) {
		String hql = "select count(e.id) from Employee e where e.serialNo=:employeeSerialNo";
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("employeeSerialNo", employeeSerialNo);
		int sum = this.getQueryCount(hql, param);
		return sum>0?false:true;
	}
	
	/*@Override
	public void importEmpoyee(List<Employee> employeeList,Long departmentId) {
		for(Employee employee : employeeList){
//			employee.setPassword(MD5.MD5("123456"));
			employee.setRid(4L);
			employee.setAddDate(new Date());
			this.save(employee);
			
			EmployeeDeptart employeeDeptart = new EmployeeDeptart();
			employeeDeptart.setEmployeeId(employee.getId());
			employeeDeptart.setDepartId(departmentId);
			this.save(employeeDeptart);
		}
	}*/
	@Override
	public void importEmpoyee(List<Map<Long, Employee>> employeeList) {
		for (Map<Long, Employee> map : employeeList) {
			for (Long deptId : map.keySet()) {
				Employee employee=map.get(deptId);
				employee.setRid(4L);
				employee.setAddDate(new Date());
				System.out.println(this.getEmpBySerNum(employee.getSerialNo()));
				if(this.getEmpBySerNum(employee.getSerialNo())){
					break;
				}
				this.save(employee);
				EmployeeDeptart employeeDeptart = new EmployeeDeptart();
				employeeDeptart.setEmployeeId(employee.getId());
				employeeDeptart.setDepartId(deptId);
				this.save(employeeDeptart);
			}
		}
		
	}
	
	private Boolean getEmpBySerNum(String num){
		String hql="from Employee e where e.serialNo=:num ";
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("num", num);
		return this.getList(hql, map)==null?true:false;
	}
	@Override
	public void batchDeleteEmployee(String[] id) throws BusinessException{
		for(String employeeId : id){
			//得到admin
			Admin admin = adminService.getAdminByEmpId(Long.parseLong(employeeId));
			if(CommonFunction.isNotNull(admin)){
				this.delete(admin);
			}
			//得到appUser
			AppUser appUser = appUserService.getAppUserByEmpId(Long.parseLong(employeeId)); 
			if(CommonFunction.isNotNull(appUser)){
				this.delete(appUser);
			}
			//去删掉sys_employee_deptart里面的数据
			EmployeeDeptart employeeDeptart = employeeDeptartService.getEmployeeDeptartByEid(Long.parseLong(employeeId));
			if(CommonFunction.isNotNull(employeeDeptart)){
				this.delete(employeeDeptart);
			}
			
			Employee employee = (Employee) this.get(Employee.class, Long.parseLong(employeeId));
			this.delete(employee);
		}
		
	}

	@Override
	public List<Employee> getAllEmp() {
		String hql="from Employee e where 1=1";
		return this.getList(hql, null);
	}
	
	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public AppUserService getAppUserService() {
		return appUserService;
	}

	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}

	public EmployeeDeptartService getEmployeeDeptartService() {
		return employeeDeptartService;
	}

	public void setEmployeeDeptartService(
			EmployeeDeptartService employeeDeptartService) {
		this.employeeDeptartService = employeeDeptartService;
	}

	@Override
	public EmployeeDeptart getEdById(Long id) {
		String hql="from EmployeeDeptart ed where ed.employeeId=:id";
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		return (EmployeeDeptart)this.getUniqueResult(hql, map);
	}

	@Override
	public QueryResult queryNoAccountEmployee(Map map) {
		QueryResult result = null;
		BaseQEntity qEntity = (BaseQEntity) map.get("qEntity");
		Map<String, Object> param=new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("");
		sql.append(" select e.*,d.name as depName from sys_employee e,sys_department d,sys_employee_deptart ed WHERE d.id=ed.depart_id and e.Id=ed.employee_id");
		if(CommonFunction.isNotNull(map.get("serialNo"))){
			sql.append(" and  e.serialNo like:serialNo ");
			param.put("serialNo", "%"+map.get("serialNo")+"%");
		}
		if(CommonFunction.isNotNull(map.get("name"))){
			sql.append(" and  e.name like :name");
			param.put("name", "%"+map.get("name")+"%");
		}
		if(CommonFunction.isNotNull(map.get("phoneNum"))){
			sql.append(" and  e.phoneNum like :phoneNum");
			param.put("phoneNum", "%"+map.get("phoneNum")+"%");
		}
		if(CommonFunction.isNotNull(map.get("idcard"))){
			sql.append(" and  e.idcard like :idcard");
			param.put("idcard", "%"+map.get("idcard")+"%");
		}
		if(map.get("type").equals("web")){
			sql.append(" and e.Id NOT IN ( select a.emp_id from sys_admin a where a.emp_id IS NOT NULL ) ");
		}else{
			sql.append(" and e.Id NOT IN ( select a.emp_id from sys_appuser a where a.emp_id IS NOT NULL ) ");
		}
		
		sql.append(" ORDER BY e.id");
		result = this.getPageQueryResultSQLToMap(sql.toString(), param, qEntity);
		return result;
	}

	@Override
	public Employee getEmployeeBySerialNo(String employeeSerialNo) {
		String hql="from Employee e where e.serialNo=:employeeSerialNo";
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("employeeSerialNo", employeeSerialNo);
		return (Employee)this.getUniqueResult(hql, map);
	}

	

	


	
}
