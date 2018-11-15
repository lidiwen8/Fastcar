package dao;

import entity.Order;
import entity.Passenger;

public interface PassengerDao {
    Passenger login(String name, String password);
    int register(Passenger passenger) throws Exception;
    Passenger queryPassenger(String name);
    int createOrder(Order order) throws Exception;
    int insertlogintime(String name)throws Exception;//插入登陆时间
    int passengerevaluate(int orderid,String evaluate,String evaluateinfo)throws Exception;
}
