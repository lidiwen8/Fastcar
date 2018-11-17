package service.impl;

import dao.OrderDao;
import dao.impl.OrderDaoImpl;
import entity.Order;
import service.OrderService;
import util.PageBean;

public class OrderServiceImpl implements OrderService {
    OrderDao orderDao= new OrderDaoImpl();
    @Override
    public Order queryOrder(String username)
    {
        return orderDao.queryOrder(username);
    }
    @Override
    public Order queryOrdernoevaluatetime(String drivername){
        return orderDao.queryOrdernoevaluatetime(drivername);
    }
    @Override
    public Order queryOrderBypaexist(String username,int states){
        return orderDao.queryOrderBypaexist(username,states);
    }
    @Override
    public Order queryOrderBydrexist(String drivername,int states){
        return orderDao.queryOrderBydrexist(drivername,states);
    }
    @Override
    public Order queryOrderbydrivername(String drivername){
        return orderDao.queryOrderbydrivername(drivername);
    }
    @Override
    public int deleteOrderByid(int orderid) throws Exception {
        return orderDao.deleteOrderByid(orderid);
    }
    @Override
    public int driverdeleteOrderByid(int orderid)throws Exception {
        return orderDao.driverdeleteOrderByid(orderid);
    }
    @Override
    public int changeOrderByid(int orderid)throws Exception{
        return orderDao.changeOrderByid(orderid);
    }
    @Override
    public int closeorderByid(int orderid)throws Exception{
        return orderDao.closeorderByid(orderid);
    }
    @Override
    public int removeEvaluatetime(int orderid)throws Exception{
        return orderDao.removeEvaluatetime(orderid);
    }

    @Override
    public PageBean<Order> findOrder(int pc, int pr, String username)
    {
        return orderDao.findOrder(pc,pr,username);
    }
    @Override
    public PageBean<Order> findOrderAll(int pc, int pr)
    {
        return orderDao.findOrderAll(pc,pr);
    }
    @Override
    public PageBean<Order> findcloseOrder(int pc, int pr, String username,int states){
        return orderDao.findcloseOrder(pc,pr,username,states);
    }
    @Override
    public PageBean<Order> closeorderbydrivername(int pc, int pr, String drivername,int states){
        return orderDao.closeorderbydrivername(pc,pr,drivername,states);
    }
    @Override
   public Order queryOrderByid(int orderid){return orderDao.queryOrderByid(orderid);}
   @Override
   public int closeorderBypassengerpayment(int orderid)throws Exception{
       return orderDao.closeorderBypassengerpayment(orderid);
   }

}
