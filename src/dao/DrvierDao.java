package dao;

import entity.Driver;

public interface DrvierDao {
    Driver login(String name, String password);
    int register(Driver driver) throws Exception;
    int insertlogintime(String name)throws Exception;
    Driver queryDriver(String name);
    Driver queryDriver2(String name);
    int receiptOrder(int orderid,String drivername,String drviernumber) throws Exception;
    int receiptOrder2(int orderid) throws Exception;
    int changedriverstates(String name) throws Exception;
    int changedriverstates2(String name) throws Exception;
    int application(Driver driver)throws Exception;//司机申请审核
}
