package service;

import entity.Order;
import entity.Passenger;

public interface PassengerService {

     Passenger login(String name, String password)throws Exception ;
     int insertlogintime(String name)throws Exception ;//插入登陆时间
     int register(Passenger passenger)throws Exception ;
     Passenger queryPassenger(String name)throws Exception ;
     int createOrder(Order order)throws Exception ;
     int passengerevaluate(int orderid,String evaluate,String evaluateinfo)throws Exception ;//插入乘客对结束订单司机的评价信息

}
