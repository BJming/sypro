package sy.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import sy.pageModel.Company;
import sy.pageModel.DataGrid;
import sy.service.CompanyServiceI;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-hibernate.xml", "classpath:spring-druid.xml" })

public class TestCompany {
	@Autowired
	private CompanyServiceI companyService;
	@Test
	public void testAddCompany(){
//		Company c=new Company();
//		c.setId("1");
//		c.setName("company1");
//		companyService.addCompany(c);
		DataGrid dg=companyService.datagrid();
		System.out.println(JSON.toJSONString(dg));
	}
}
