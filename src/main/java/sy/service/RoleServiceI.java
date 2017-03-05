package sy.service;

import java.util.List;

import sy.pageModel.Role;
import sy.pageModel.Tree;

/**
 * 角色业务逻辑
 * 
 * @author 孙宇
 * 
 */
public interface RoleServiceI {

	/**
	 * 保存角色
	 * 
	 * @param role
	 */
	public void add(Role role);

	/**
	 * 获得角色
	 * 
	 * @param id
	 * @return
	 */
	public Role get(String id);

	/**
	 * 编辑角色
	 * 
	 * @param role
	 */
	public void edit(Role role);

	/**
	 * 获得角色treeGrid
	 * 
	 * @return
	 */
	public List<Role> treeGrid();

	/**
	 * 删除角色
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 获得角色树
	 * 
	 * @return
	 */
	public List<Tree> tree();

	/**
	 * 为角色授权
	 * 
	 * @param role
	 */
	public void grant(Role role);

}
