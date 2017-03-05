package sy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BugTypeDaoI;
import sy.model.Tbugtype;
import sy.service.BugTypeServiceI;

@Service
public class BugTypeServiceImpl implements BugTypeServiceI {

	@Autowired
	private BugTypeDaoI bugType;

	@Override
	public List<Tbugtype> getBugTypeList() {
		return bugType.find("from Tbugtype t");
	}

}
