package service;

import entity.Order;
import entity.Passenger;

public interface PassengerService {

     Passenger login(String name, String password);
     int insertlogintime(String name);//插入登陆时间
     int register(Passenger passenger);
     Passenger queryPassenger(String name);
     int createOrder(Order order);
     int passengerevaluate(int orderid,String evaluate,String evaluateinfo);//插入乘客对结束订单司机的评价信息

}
