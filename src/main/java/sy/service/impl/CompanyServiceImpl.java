package sy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.CompanyDaoI;
import sy.model.Tcompany;
import sy.pageModel.Company;
import sy.pageModel.DataGrid;
import sy.service.CompanyServiceI;
@Service
public class CompanyServiceImpl implements CompanyServiceI {
	@Autowired
	private CompanyDaoI companyDao;

	@Override
	public void addCompany(Company company) {
		// TODO Auto-generated method stub
		Tcompany tcompany = new Tcompany();
		BeanUtils.copyProperties(company, tcompany);
		companyDao.save(tcompany);
	}

	@Override
	public DataGrid datagrid() {
		// TODO Auto-generated method stub
		DataGrid datagrid = new DataGrid();
		List<Tcompany> tcompanyList = companyDao.find("from Tcompany t");
		List<Company> companyList = new ArrayList<Company>();
		if (tcompanyList == null || tcompanyList.size() == 0) {
			return null;
		}
		for (Tcompany tcompany : tcompanyList) {
			Company c = new Company();
			BeanUtils.copyProperties(tcompany, c);
			companyList.add(c);
		}
		datagrid.setRows(companyList);
		datagrid.setTotal((long) companyList.size());
		return datagrid;
	}

	@Override
	public void add(Company company) {
		Tcompany tcompany = new Tcompany();
		BeanUtils.copyProperties(company, tcompany);
		companyDao.save(tcompany);
	}

	@Override
	public Company get(String id) {
		// TODO Auto-generated method stub
		Company company = new Company();
		Tcompany tcompany = companyDao.get(Tcompany.class, id);
		if (tcompany == null)
			return null;
		BeanUtils.copyProperties(tcompany, company);
		return company;
	}

	@Override
	public void edit(Company company) {
		Tcompany tcompany = companyDao.get(Tcompany.class, company.getId());
		if (tcompany != null) {
			BeanUtils.copyProperties(company, tcompany);
		}
	}

	@Override
	public void delete(String id) {
		Tcompany tcompany = companyDao.get(Tcompany.class, id);
		if (tcompany != null) {
			companyDao.delete(tcompany);
		}
	}
}
