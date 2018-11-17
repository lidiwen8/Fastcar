package dao;

import entity.Order;
import util.PageBean;

public interface OrderDao {
    Order queryOrder(String username);
    Order queryOrderbydrivername(String drivername);
    Order queryOrdernoevaluatetime(String drivername);//通过司机查找乘客对司机的差评订单
    int deleteOrderByid(int orderid) throws Exception;
    int driverdeleteOrderByid(int orderid)throws Exception;
    int changeOrderByid(int orderid) throws Exception;
    int closeorderByid(int orderid) throws Exception;
    int removeEvaluatetime(int orderid)throws Exception;
    PageBean<Order> findOrder(int pc, int pr, String username);
    PageBean<Order> findOrderAll(int pc, int pr);
    PageBean<Order> findcloseOrder(int pc, int pr, String username,int states);
    PageBean<Order> closeorderbydrivername(int pc, int pr, String drivername,int states);
    Order queryOrderByid(int orderid);
    Order queryOrderBydrexist(String drivername,int states);
    int closeorderBypassengerpayment(int orderid)throws Exception;//乘客支付完成订单真正结束
    Order queryOrderBypaexist(String username,int states);
}
