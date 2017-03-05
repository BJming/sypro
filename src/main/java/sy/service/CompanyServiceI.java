package sy.service;

import sy.pageModel.Company;
import sy.pageModel.DataGrid;

public interface CompanyServiceI {
//	public void addCompany(Company company);
	public DataGrid datagrid();
	public void add(Company company);
	public Company get(String id);
	public void edit(Company company);
	public void delete(String id);
	public void addCompany(Company company);
}
