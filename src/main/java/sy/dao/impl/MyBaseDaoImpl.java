package sy.dao.impl;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sy.dao.MyBaseDaoI;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/14.
 */
@Repository
public class MyBaseDaoImpl<T> implements MyBaseDaoI<T> {

    @Autowired
    private SessionFactory sessionFactory;

    /*获取当前事物的session*/
    public Session getCurrentSession(){
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public Serializable save(T o) {
        if(o != null){
            return this.getCurrentSession().save(o);
        }
        return null;
    }

    @Override
    public void Delete(T o) {
        if(o!=null){
            this.getCurrentSession().delete(o);
        }
    }

    @Override
    public void update(T o) {
        if(o!=null){
            this.getCurrentSession().update(o);
        }
    }

    @Override
    public void saveOrUpdate(T o) {
        if(o != null){
            this.getCurrentSession().saveOrUpdate(o);
        }
    }

    @Override
    public T get(Class<T> c, Serializable id) {
        return (T) this.getCurrentSession().get(c, id);
    }

    @Override
    public T get(String hql) {
        Query q= this.getCurrentSession().createQuery(hql);
        List<T> l=q.list();
        if(l!=null&&l.size()>0){
            return l.get(0);//如果有多个，则返回首个
        }
        return null;
    }

    @Override
    public T get(String hql, Map<String, Object> params) {
        Query q = this.getCurrentSession().createQuery(hql);
        if(params != null && !params.isEmpty()){
            for(String key : params.keySet()){
                q.setParameter(key, params.get(key));
            }
        }
        List<T> l = q.list();
        if(l != null && l.size()>0){
            return l.get(0);
        }
        return null;
    }

    @Override
    public List<T> find(String hql) {
        Query q = this.getCurrentSession().createQuery(hql);
        return q.list();
    }

    @Override
    public List<T> find(String hql, Map<String, Object> params) {
        Query q = this.getCurrentSession().createQuery(hql);
        if(params !=null && !params.isEmpty()){
            for(String key: params.keySet()){
                q.setParameter(key, params.get(key));
            }
        }
        return q.list();
    }

    @Override
    public List<T> find(String hql, int page, int rows) {
        Query q = this.getCurrentSession().createQuery(hql);
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
        Query q = this.getCurrentSession().createQuery(hql);
        if(params != null && !params.isEmpty()){
            for(String key:params.keySet()){
                q.setParameter(key, params.get(key));
            }
        }
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public Long count(String hql) {
        Query q = this.getCurrentSession().createQuery(hql);
        return (Long) q.uniqueResult();
    }

    @Override
    public Long count(String hql, Map<String, Object> params) {
        Query q = this.getCurrentSession().createQuery(hql);
        if(params != null && !params.isEmpty()){
            for(String key : params.keySet()){
                q.setParameter(key, params.get(key));
            }
        }
        return (Long)q.uniqueResult();
    }

    @Override
    public int executeHql(String hql) {
        Query q = this.getCurrentSession().createQuery(hql);
        return q.executeUpdate();
    }

    @Override
    public int executeHql(String hql, Map<String, Object> params) {
        Query q = this.getCurrentSession().createQuery(hql);
        if(params != null && !params.isEmpty()){
            for(String key : params.keySet()){
                q.setParameter(key, params.get(key));
            }
        }
        return q.executeUpdate();
    }

    @Override
    public List<Object[]> findBySql(String sql) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        return q.list();
    }

    @Override
    public List<Object[]> findBySQL(String sql, int page, int rows) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        return q.setFirstResult((page -1) * rows).setMaxResults(rows).list();
    }
    @Override
    public List<Object[]> findBySQL(String sql, Map<String, Object> params) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        if(params != null && !params.isEmpty()){
            for(String key : params.keySet()){
                q.setParameter(key, params.get(key));
            }
        }
        return q.list();
    }

    @Override
    public List<Object[]> findBySql(String sql, Map<String, Object> params, int page, int rows) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        if(params != null && !params.isEmpty()){
            for(String key : params.keySet()){
                q.setParameter(key, params.get(key));
            }
        }
        return q.setFirstResult((page-1)*rows).setMaxResults(rows).list();
    }

    @Override
    public int executeSql(String sql) {

        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        return q.executeUpdate();
    }

    @Override
    public int executeSql(String sql, Map<String, Object> params) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        if(params!=null && !params.isEmpty()){
            for(String key : params.keySet()){
                q.setParameter(key, params.get(key));
            }
        }
        return q.executeUpdate();
    }

    @Override
    public BigInteger countBySql(String sql) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        return (BigInteger) q.uniqueResult();
    }

    @Override
    public BigInteger countBySql(String sql, Map<String, Object> params) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        if(params!=null && !params.isEmpty()){
            for(String key : params.keySet()){
                q.setParameter(key, params.get(key));
            }
        }
        return (BigInteger)q.uniqueResult();
    }
//    TODO:编写完成，请检查是否有无方法陋写或错误
}
