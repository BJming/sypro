package sy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.RoleDaoI;
import sy.dao.UserDaoI;
import sy.model.Tresource;
import sy.model.Trole;
import sy.model.Tuser;
import sy.pageModel.DataGrid;
import sy.pageModel.PageHelper;
import sy.pageModel.SessionInfo;
import sy.pageModel.User;
import sy.service.UserServiceI;
import sy.util.MD5Util;

@Service
public class UserServiceImpl implements UserServiceI {

	@Autowired
	private UserDaoI userDao;

	@Autowired
	private RoleDaoI roleDao;

	@Override
	public User login(User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getName());
		params.put("pwd", MD5Util.md5(user.getPwd()));
		Tuser t = userDao.get("from Tuser t where t.name = :name and t.pwd = :pwd", params);
		if (t != null) {
			BeanUtils.copyProperties(t, user);
			if(t.getTcompany()!=null){
				user.setCompanyId(t.getTcompany().getId());
				user.setCompany(t.getTcompany().getName());
			}
			return user;
		}
		return null;
	}

	@Override
	synchronized public void reg(User user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getName());
		if (userDao.count("select count(*) from Tuser t where t.name = :name", params) > 0) {
			throw new Exception("登录名已存在！");
		} else {
			Tuser u = new Tuser();
			u.setId(UUID.randomUUID().toString());
			u.setName(user.getName());
			u.setPwd(MD5Util.md5(user.getPwd()));
			u.setCreatedatetime(new Date());
			userDao.save(u);
		}
	}

	@Override
	public DataGrid dataGrid(User user, PageHelper ph) {
		DataGrid dg = new DataGrid();
		List<User> ul = new ArrayList<User>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tuser t ";
		List<Tuser> l = userDao.find(hql + whereHql(user, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		if (l != null && l.size() > 0) {
			for (Tuser t : l) {
				User u = new User();
				BeanUtils.copyProperties(t, u);
				if(t.getTcompany()!=null){
					u.setCompanyId(t.getTcompany().getId());
					u.setCompany(t.getTcompany().getName());
				}
				Set<Trole> roles = t.getTroles();
				if (roles != null && !roles.isEmpty()) {
					String roleIds = "";
					String roleNames = "";
					boolean b = false;
					for (Trole tr : roles) {
						if (b) {
							roleIds += ",";
							roleNames += ",";
						} else {
							b = true;
						}
						roleIds += tr.getId();
						roleNames += tr.getName();
					}
					u.setRoleIds(roleIds);
					u.setRoleNames(roleNames);
				}
				ul.add(u);
			}
		}
		dg.setRows(ul);
		dg.setTotal(userDao.count("select count(*) " + hql + whereHql(user, params), params));
		return dg;
	}

	private String whereHql(User user, Map<String, Object> params) {
		String hql = "";
		if (user != null) {
			hql += " where 1=1 ";
			if (user.getName() != null) {
				hql += " and t.name like :name";
				params.put("name", "%%" + user.getName() + "%%");
			}
			if (user.getCreatedatetimeStart() != null) {
				hql += " and t.createdatetime >= :createdatetimeStart";
				params.put("createdatetimeStart", user.getCreatedatetimeStart());
			}
			if (user.getCreatedatetimeEnd() != null) {
				hql += " and t.createdatetime <= :createdatetimeEnd";
				params.put("createdatetimeEnd", user.getCreatedatetimeEnd());
			}
			if (user.getModifydatetimeStart() != null) {
				hql += " and t.modifydatetime >= :modifydatetimeStart";
				params.put("modifydatetimeStart", user.getModifydatetimeStart());
			}
			if (user.getModifydatetimeEnd() != null) {
				hql += " and t.modifydatetime <= :modifydatetimeEnd";
				params.put("modifydatetimeEnd", user.getModifydatetimeEnd());
			}
//			if(user.getCompany()!=null&&user.getCompany().trim().length()>0){
//				hql += " and t.company like :company";
//				params.put("company", "%%" + user.getCompany().trim() + "%%");
//			}
		}
		return hql;
	}

	private String orderHql(PageHelper ph) {
		String orderString = "";
		if (ph.getSort() != null && ph.getOrder() != null) {
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}

	@Override
	synchronized public void add(User user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getName());
		if (userDao.count("select count(*) from Tuser t where t.name = :name", params) > 0) {
			throw new Exception("登录名已存在！");
		} else {
			Tuser u = new Tuser();
			BeanUtils.copyProperties(user, u);
			u.setCreatedatetime(new Date());
			u.setPwd(MD5Util.md5(user.getPwd()));
			userDao.save(u);
		}
	}

	@Override
	public User get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tuser t = userDao.get("select distinct t from Tuser t left join fetch t.troles role where t.id = :id", params);
		User u = new User();
		BeanUtils.copyProperties(t, u);
		if(t.getTcompany()!=null){
			u.setCompanyId(t.getTcompany().getId());
			u.setCompany(t.getTcompany().getName());
		}
		if (t.getTroles() != null && !t.getTroles().isEmpty()) {
			String roleIds = "";
			String roleNames = "";
			boolean b = false;
			for (Trole role : t.getTroles()) {
				if (b) {
					roleIds += ",";
					roleNames += ",";
				} else {
					b = true;
				}
				roleIds += role.getId();
				roleNames += role.getName();
			}
			u.setRoleIds(roleIds);
			u.setRoleNames(roleNames);
		}
		return u;
	}

	@Override
	synchronized public void edit(User user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", user.getId());
		params.put("name", user.getName());
		if (userDao.count("select count(*) from Tuser t where t.name = :name and t.id != :id", params) > 0) {
			throw new Exception("登录名已存在！");
		} else {
			Tuser u = userDao.get(Tuser.class, user.getId());
			BeanUtils.copyProperties(user, u, new String[] { "pwd", "createdatetime" });
			u.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		userDao.delete(userDao.get(Tuser.class, id));
	}

	@Override
	public void grant(String ids, User user) {
		if (ids != null && ids.length() > 0) {
			List<Trole> roles = new ArrayList<Trole>();
			if (user.getRoleIds() != null) {
				for (String roleId : user.getRoleIds().split(",")) {
					roles.add(roleDao.get(Trole.class, roleId));
				}
			}
			for (String id : ids.split(",")) {
				if (id != null && !id.equalsIgnoreCase("")) {
					Tuser t = userDao.get(Tuser.class, id);
					t.setTroles(new HashSet<Trole>(roles));
				}
			}
		}
	}

	@Override
	public List<String> resourceList(String id) {
		List<String> resourceList = new ArrayList<String>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tuser t = userDao.get("from Tuser t join fetch t.troles role join fetch role.tresources resource where t.id = :id", params);
		if (t != null) {
			Set<Trole> roles = t.getTroles();
			if (roles != null && !roles.isEmpty()) {
				for (Trole role : roles) {
					Set<Tresource> resources = role.getTresources();
					if (resources != null && !resources.isEmpty()) {
						for (Tresource resource : resources) {
							if (resource != null && resource.getUrl() != null) {
								resourceList.add(resource.getUrl());
							}
						}
					}
				}
			}
		}
		return resourceList;
	}

	@Override
	public void editPwd(User user) {
		if (user != null && user.getPwd() != null && !user.getPwd().trim().equalsIgnoreCase("")) {
			Tuser u = userDao.get(Tuser.class, user.getId());
			u.setPwd(MD5Util.md5(user.getPwd()));
		}
	}

	@Override
	public boolean editCurrentUserPwd(SessionInfo sessionInfo, String oldPwd, String pwd) {
		Tuser u = userDao.get(Tuser.class, sessionInfo.getId());
		if (u.getPwd().equalsIgnoreCase(MD5Util.md5(oldPwd))) {// 说明原密码输入正确
			u.setPwd(MD5Util.md5(pwd));
			return true;
		}
		return false;
	}
}
