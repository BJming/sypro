package sy.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sy.pageModel.Company;
import sy.pageModel.DataGrid;
import sy.pageModel.Json;
import sy.pageModel.SessionInfo;
import sy.pageModel.User;
import sy.service.CompanyServiceI;
import sy.util.ConfigUtil;

@Controller
@RequestMapping("/companyController")
public class CompanyController {
	@Autowired
	private CompanyServiceI companyService;
	@RequestMapping("/datagrid")
	@ResponseBody
	public DataGrid datagrid(){
		return companyService.datagrid();
	}
	@RequestMapping("/manager")
	public String manage(){
		return "admin/company";
	}
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request){
		Company company=new Company();
		company.setId(UUID.randomUUID().toString());
		request.setAttribute("company", company);
		return "/admin/companyAdd";
	}
	@RequestMapping("/add")
	@ResponseBody
	public Json add(Company company) {
		Json j = new Json();
		try {
			companyService.add(company);
			j.setSuccess(true);
			j.setMsg("添加成功！");
			j.setObj(company);
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		Company company = companyService.get(id);
		request.setAttribute("company", company);
		return "/admin/companyEdit";
	}
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(Company company) {
		Json j = new Json();
		try {
			companyService.edit(company);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
			j.setObj(company);
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id, HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		Json j = new Json();
		if (id != null && !id.equalsIgnoreCase(sessionInfo.getId())) {// 不能删除自己
			companyService.delete(id);
		}
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}
}
