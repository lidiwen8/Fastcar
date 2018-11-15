package dao.impl;

import cn.itcast.jdbc.TxQueryRunner;
import dao.DrvierDao;
import entity.Driver;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DrvierDaoImpl implements DrvierDao {
    private QueryRunner mysqlDao = new TxQueryRunner();
    @Override
    public Driver login(String name, String password) {
        try {
            return mysqlDao.query("select * from driver where name=? and pwd=?", new BeanHandler<Driver>(Driver.class),name,password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public int register(Driver driver)throws Exception{
        int flag=0;
        String sql ="insert into driver(name,pwd,sex,number) values(?,?,?,?)";
        Object[] params={driver.getName(),driver.getPwd() ,driver.getSex(),driver.getNumber()};
        try {
            //事务开始
            mysqlDao.update(sql,params);
            flag=1;
            //事务提交
        } catch (Exception e) {
            e.printStackTrace();
            //事务回滚
            flag=0;
            throw e;
        }
        return flag;
    }
    @Override
    public int insertlogintime(String name)throws Exception{
        int flag=0;
        Date day = new Date();
        SimpleDateFormat da = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//登录时间
        String sql ="update driver set logintime = ? where name =?";
        Object[] params={da.format(day),name};
        try {
            //事务开始
            mysqlDao.update(sql,params);
            flag=1;
            //事务提交
        } catch (Exception e) {
            e.printStackTrace();
            //事务回滚
            flag=0;
            throw e;
        }
        return flag;
    }
    @Override
    public int receiptOrder(int orderid,String drivername,String drviernumber)throws Exception{
        int flag=0;
        Date day = new Date();
        SimpleDateFormat da = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//司机接单时间
        String sql ="update order1 set singletime = ? ,states = ? ,drivername = ?,drviernumber = ? where orderid =?";
        Object[] params={da.format(day),1,drivername,drviernumber,orderid};
        try {
            //事务开始
            mysqlDao.update(sql,params);
            flag=1;
            //事务提交
        } catch (Exception e) {
            e.printStackTrace();
            //事务回滚
            flag=0;
            throw e;
        }
        return flag;
    }
    @Override
    public int receiptOrder2(int orderid)throws Exception{
        int flag=0;
        String sql ="update order1 set states=? where orderid=?";
        Object[] params={0,orderid};
        try {
            //事务开始
            mysqlDao.update(sql,params);
            flag=1;
            //事务提交
        } catch (Exception e) {
            e.printStackTrace();
            //事务回滚
            flag=0;
            throw e;
        }
        return flag;
    }
    @Override
    public int changedriverstates(String name)throws Exception{
        int flag=0;
        String sql ="update driver set states=? where name=?";
        Object[] params={1,name};
        try {
            //事务开始
            mysqlDao.update(sql,params);
            flag=1;
            //事务提交
        } catch (Exception e) {
            e.printStackTrace();
            //事务回滚
            flag=0;
            throw e;
        }
        return flag;
    }
    @Override
    public int changedriverstates2(String name)throws Exception{
        int flag=0;
        String sql ="update driver set states=? where name=?";
        Object[] params={0,name};
        try {
            //事务开始
            mysqlDao.update(sql,params);
            flag=1;
            //事务提交
        } catch (Exception e) {
            e.printStackTrace();
            //事务回滚
            flag=0;
            throw e;
        }
        return flag;
    }
    @Override
    public Driver queryDriver(String name) {
        try {
            return mysqlDao.query("select * from driver where name=?", new BeanHandler<Driver>(Driver.class), name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Driver queryDriver2(String name) {
        try {
            return mysqlDao.query("select * from driver where name=? and states=?", new BeanHandler<Driver>(Driver.class), name,1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
