package service.impl;

import dao.PassengerDao;
import dao.impl.PassengerDaoImpl;
import entity.Order;
import entity.Passenger;
import service.PassengerService;

public class PassengerServiceImpl implements PassengerService {
    PassengerDao passengerDao= new PassengerDaoImpl();
    @Override
    public Passenger login(String name, String password){return passengerDao.login(name,password);}
    @Override
    public int register(Passenger passenger) throws Exception {
        return passengerDao.register(passenger);
    }
    @Override
    public Passenger queryPassenger(String name)
    {
        return passengerDao.queryPassenger(name);
    }
    @Override
    public int createOrder(Order order) throws Exception {
        return passengerDao.createOrder(order);
    }
    @Override
    public int passengerevaluate(int orderid,String evaluate,String evaluateinfo)throws Exception {
        return passengerDao.passengerevaluate(orderid,evaluate,evaluateinfo);
    }
    @Override
    //插入登陆时间
    public int insertlogintime(String name)throws Exception {
        return passengerDao.insertlogintime(name);
    }
}
