package service;

import entity.Order;
import util.PageBean;

public interface OrderService {

    Order queryOrder(String username);
    Order queryOrderbydrivername(String drivername);//通过司机查找订单，查看司机当前正在进行的订单
    Order queryOrdernoevaluatetime(String drivername);//通过司机查找乘客对司机的差评订单
    Order queryOrderByid(int orderid);
    int removeEvaluatetime(int orderid)throws Exception;//移除评价时间
    int deleteOrderByid(int orderid) throws Exception;
    int driverdeleteOrderByid(int orderid)throws Exception;//在乘客没有上车前，司机取消订单改变订单状态，设置为未接单状态0
    int changeOrderByid(int orderid)throws Exception;//改变订单状态，确认乘客已上车3
    int closeorderByid(int orderid)throws Exception;//改变订单状态，结束当前订单2
    PageBean<Order> findOrder(int pc, int pr, String username);//分页查找乘客订单
    PageBean<Order> findcloseOrder(int pc, int pr, String username,int states);//分页查找乘客已结束的订单
    PageBean<Order> closeorderbydrivername(int pc, int pr, String drivername,int states);//分页查找司机已结束的订单
    Order queryOrderBypaexist(String username,int states);//根据乘客姓名和订单状态查找订单
    Order queryOrderBydrexist(String drivername,int states);//根据司机姓名和订单状态查找订单
    PageBean<Order> findOrderAll(int pc, int pr);//分页查找全部乘客发起的订单
    int closeorderBypassengerpayment(int orderid)throws Exception;//乘客支付完成订单真正结束

}
