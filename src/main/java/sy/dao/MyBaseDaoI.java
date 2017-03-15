package sy.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Created by bjm on 2017/3/14.
 */
public interface MyBaseDaoI<T> {

    /*保存一个对象 @param o @return 对象的ID*/
    public Serializable save(T o);

    /*删除一个对象 @param o 对象*/
    public void Delete(T o);

    /*更新一个对象 @param o 对象*/
    public void update(T o);

    /*保存或更新一个对象 @param o*/
    public void saveOrUpdate(T o);

    /*通过主键获取对象 @param c 类名.class @param id 主键 @return 对象*/
    public T get(Class<T> c, Serializable id);

    /*通过HQL语句获取一个对象 @param hql HQL语句 @return 对象*/
    public T get(String hql);

    /*通过HQL获取一个对象（Map） @param hql HQL语句 @return 对象*/
    public T get(String hql, Map<String, Object> params);

    /*获取对象列表 @param hql HQL语句 @return List*/
    public List<T> find(String hql);

    /*获取对象列表 @param hql HQL语句 @param params 参数 @return List*/
    public List<T> find(String hql, Map<String , Object> params);

    /*获取分页后的对象列表 @param hql HQL语句 @param page 要显示第几页 @param rows 每页显示多少条*/
    public List<T> find(String hql, int page, int rows);

    /*获取分页后的对象列表 @param hql HQL语句 @param params 参数 @param rows 要显示第几页 @rows 每页显示多少条 @return List*/
    public List<T> find(String hql, Map<String, Object> params, int page, int rows);

    /*统计数目 @param hql HQL语句（select count(*) from T) @return long*/
    public Long count(String hql);

    /*统计数目 @param hql HQL 语句 （select count(*) from T where xx = :xx） @param params 参数 @return long*/
    public Long count(String hql, Map<String, Object> params);

    /*执行一条HQL 语句 @param hql HQL语句 @return 响应结果数目*/
    public int executeHql(String hql);

    /*执行一条HQL语句 @param hql HQL语句 @param params 参数 @return 响应结果数目*/
    public int executeHql(String hql, Map<String, Object> params);

    /*获得结果集 @param sql SQL语句 @return 结果集*/
    public List<Object[]> findBySql(String sql);

    /*获取结果集 @param sql SQL语句 @param page 要显示第几页 @param rows每页显示多少条 @return 结果集*/
    public List<Object[]> findBySql(String sql, int page, int rows);

    /*获得结果集 @param sql SQL语句 @param params 参数 @return 结果集*/
    public List<Object[]> findBySql(String sql, Map<String , Object> params);

    /*获得结果集 @param sql SQL语句 @param params 参数 @param page 要显示第几页 @param rows每页显示多少条 @return 结果集*/
    public List<Object[]> findBySql(String sql, Map<String, Object> params, int page, int rows);

    /*执行SQL语句 @param sql return 响应行数*/
    public int executeSql(String sql);

    /*执行SQL语句 @param sql SQL语句 @param params 参数 @return 响应行数*/
    public int executeSql(String sql, Map<String, Object> params);

    /*统计 @param sql SQL语句 @return 数目*/
    public BigInteger countBySql(String sql);

    /*统计 @param sql SQL语句 @return 数目*/
    public BigInteger countBySql(String sql, Map<String , Object> params);

}
