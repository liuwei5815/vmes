import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xy.admx.core.common.SpringContext;
import com.xy.admx.core.service.base.BaseServiceImpl;
/*import com.xy.apisql.common.ApiSqlParseFactory;*/
import com.xy.apisql.common.ApiSqlResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class ProjectTest extends BaseServiceImpl {

	@Before
	public void before(){
		System.out.println("before");
	}
/*
	@Test
	public void test(){
		String apiSql = "select _user.姓名 as name  from @用户  _user where _user.主键=$request.id";
		apiSql="update  @用户 set @用户.姓名=$request.name where @用户.主键=$request.id";
		ApiSqlParseFactory apiSqlParseFactory = (ApiSqlParseFactory) SpringContext.getBean("apiSqlParseFactory");
		Map<String,String> request= new HashMap();
		request.put("id", "1");
		
		ApiSqlResult sql = apiSqlParseFactory.forMatApiSql(176l,apiSql, request);
		System.out.println(sql.getSql());
		System.out.println(sql.getNamedParam());
	}*/


	

}
