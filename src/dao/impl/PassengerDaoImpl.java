package dao.impl;

import cn.itcast.jdbc.TxQueryRunner;
import dao.PassengerDao;
import entity.Order;
import entity.Passenger;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PassengerDaoImpl implements PassengerDao {
    private QueryRunner mysqlDao = new TxQueryRunner();
    @Override
    public Passenger login(String name, String password) {
        try {
            return mysqlDao.query("select * from passenger where name=? and password=?", new BeanHandler<Passenger>(Passenger.class),name,password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public int register(Passenger passenger)throws Exception{
        int flag=0;
        Date day = new Date();
        SimpleDateFormat da = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注册时间
        String sql ="insert into passenger(name,password,sex,number,registertime) values(?,?,?,?,?)";
        Object[] params={passenger.getName(),passenger.getPassword() ,passenger.getSex(),passenger.getNumber(),da.format(day)};
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
    public int passengerevaluate(int orderid,String evaluate,String evaluateinfo)throws Exception{
        int flag=0;
        String sql;
        Object[] params;
        if(evaluate.equals("一星级差评")){
            SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String evaluatetime= dfs.format(new Date());//插入时间
            sql ="update order1 set evaluate=?,evaluateinfo=?,evaluatetime=? where orderid=?";
            params= new Object[]{evaluate, evaluateinfo, evaluatetime, orderid};
        }else{
             sql ="update order1 set evaluate=?,evaluateinfo=? where orderid=?";
             params= new Object[]{evaluate, evaluateinfo, orderid};
        }
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
        String sql ="update passenger set logintime = ? where name =?";
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
     public Passenger queryPassenger(String name){
         try {
             return mysqlDao.query("select * from passenger where name=?", new BeanHandler<Passenger>(Passenger.class), name);
         } catch (Exception e) {
             throw new RuntimeException(e);
         }

     }
    @Override
    public int createOrder(Order order) throws Exception{
        int flag=0;
        String sql ="insert into order1(username,origin,destination,createtime,states,taximode,passengernumber) values(?,?,?,?,?,?,?)";
        Object[] params={order.getUsername(),order.getOrigin() ,order.getDestination(),order.getCreatetime(),order.getStates(),order.getTaximode(),order.getPassengernumber()};
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
}
