package dao.impl;

import cn.itcast.jdbc.TxQueryRunner;
import dao.OrderDao;
import entity.Order;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import util.PageBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private QueryRunner mysqlDao = new TxQueryRunner();
    @Override
    public Order queryOrder(String username){
        try {
            return mysqlDao.query("select * from order1 where username=? and (states=? or states=? or states=?)", new BeanHandler<Order>(Order.class), username,1,3,0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    //通过司机查找乘客对司机的差评订单
    @Override
    public Order queryOrdernoevaluatetime(String drivername){
        try {
            return mysqlDao.query("select * from order1 where drivername=? and evaluatetime is not null", new BeanHandler<Order>(Order.class), drivername);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Order queryOrderbydrivername(String drivername){
        try {
            return mysqlDao.query("select * from order1 where drivername=? and (states=? or states=?)", new BeanHandler<Order>(Order.class), drivername,1,3);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order queryOrderBydrexist(String drivername,int states){
        try {
            return mysqlDao.query("select * from order1 where drivername=? and states=?", new BeanHandler<Order>(Order.class), drivername,states);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order queryOrderBypaexist(String username,int states){
        try {
            return mysqlDao.query("select * from order1 where username=? and states=?", new BeanHandler<Order>(Order.class), username,states);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Order queryOrderByid(int orderid){
        try {
            return mysqlDao.query("select * from order1 where orderid=?", new BeanHandler<Order>(Order.class), orderid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @Override
   public int deleteOrderByid(int orderid)throws Exception {
        int flag = 0;
        String sql = "delete from order1 where orderid=?";
        Object[] params = {orderid};
        try {
            //事务开始
            mysqlDao.update(sql, params);
            flag = 1;
            //事务提交
        } catch (Exception e) {
            e.printStackTrace();
            //事务回滚
            flag = 0;
            throw e;
        }
        return flag;
    }
    @Override
    public int removeEvaluatetime(int orderid)throws Exception{
        int flag = 0;
        String sql = "update order1 set evaluatetime=? where orderid=?";
        Object[] params = {null,orderid};
        try {
            //事务开始
            mysqlDao.update(sql, params);
            flag = 1;
            //事务提交
        } catch (Exception e) {
            e.printStackTrace();
            //事务回滚
            flag = 0;
            throw e;
        }
        return flag;
    }
    @Override
    public int changeOrderByid(int orderid)throws Exception {
        int flag = 0;
        String sql = "update order1 set states=? where orderid=?";
        Object[] params = {3,orderid};
        try {
            //事务开始
            mysqlDao.update(sql, params);
            flag = 1;
            //事务提交
        } catch (Exception e) {
            e.printStackTrace();
            //事务回滚
            flag = 0;
            throw e;
        }
        return flag;
    }

    @Override
    public int driverdeleteOrderByid(int orderid)throws Exception {
        int flag = 0;
        String sql = "update order1 set states=?,drivername=?,drviernumber=?,singletime=? where orderid=?";
        Object[] params = {0,null,null,null,orderid};
        try {
            //事务开始
            mysqlDao.update(sql, params);
            flag = 1;
            //事务提交
        } catch (Exception e) {
            e.printStackTrace();
            //事务回滚
            flag = 0;
            throw e;
        }
        return flag;
    }


    @Override
    public int closeorderByid(int orderid)throws Exception {
        int flag = 0;
        Date day = new Date();
        SimpleDateFormat da = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//订单结束时间
        String sql = "update order1 set states=? ,endtime=? where orderid=?";
        Object[] params = {2,da.format(day),orderid};
        try {
            //事务开始
            mysqlDao.update(sql, params);
            flag = 1;
            //事务提交
        } catch (Exception e) {
            e.printStackTrace();
            //事务回滚
            flag = 0;
            throw e;
        }
        return flag;
    }
    @Override
    public PageBean<Order>findOrder(int pc, int pr, String username)//分页查找乘客订单
    {
        try{
            PageBean<Order> pb=new PageBean<>();
            pb.setPc(pc);
            pb.setPr(pr);
            String sql="select count(*) from order1 where username=? and states=?";
            Object[] param={username,0};//查找乘客未接单的订单
            Number number=(Number) mysqlDao.query(sql,new ScalarHandler<>(),param);
            int tr=number.intValue();
            pb.setTr(tr);
            sql="select * from order1 where username=? and states=? order by orderid limit ?,?";
            Object[] params={username,0,(pc-1)*pr,pr};
            List<Order> beanList=mysqlDao.query(sql,new BeanListHandler<>(Order.class),params);
            pb.setBeanList(beanList);
            return pb;
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    @Override
    public PageBean<Order> findcloseOrder(int pc, int pr, String username,int states){
        try{
            PageBean<Order> pb=new PageBean<>();
            pb.setPc(pc);
            pb.setPr(pr);
            String sql="select count(*) from order1 where states=? and username=?";
            Object[] param={states,username};
            Number number=(Number) mysqlDao.query(sql,new ScalarHandler<>(),param);
            int tr=number.intValue();
            pb.setTr(tr);
            sql="select * from order1 where states=? and username=? order by orderid limit ?,?";
            Object[] params={states,username,(pc-1)*pr,pr};
            List<Order> beanList=mysqlDao.query(sql,new BeanListHandler<>(Order.class),params);
            pb.setBeanList(beanList);
            return pb;
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    @Override
    public PageBean<Order>findOrderAll(int pc, int pr)//分页查找全部乘客的订单
    {
        try{
            PageBean<Order> pb=new PageBean<>();
            pb.setPc(pc);
            pb.setPr(pr);
            String sql="select count(*) from order1 where states=?";
            Object[] param={0};
            Number number=(Number) mysqlDao.query(sql,new ScalarHandler<>(),param);
            int tr=number.intValue();
            pb.setTr(tr);
            sql="select * from order1 where states=? order by orderid limit ?,?";
            Object[] params={0,(pc-1)*pr,pr};
            List<Order> beanList=mysqlDao.query(sql,new BeanListHandler<>(Order.class),params);
            pb.setBeanList(beanList);
            return pb;
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    @Override
    public PageBean<Order> closeorderbydrivername(int pc, int pr, String drivername,int states){
        try{
            PageBean<Order> pb=new PageBean<>();
            pb.setPc(pc);
            pb.setPr(pr);
            String sql="select count(*) from order1 where states=? and drivername=?";
            Object[] param={states,drivername};
            Number number=(Number) mysqlDao.query(sql,new ScalarHandler<>(),param);
            int tr=number.intValue();
            pb.setTr(tr);
            sql="select * from order1 where states=? and drivername=? order by orderid limit ?,?";
            Object[] params={states,drivername,(pc-1)*pr,pr};
            List<Order> beanList=mysqlDao.query(sql,new BeanListHandler<>(Order.class),params);
            pb.setBeanList(beanList);
            return pb;
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

}
