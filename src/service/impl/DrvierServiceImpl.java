package service.impl;

import dao.DrvierDao;
import dao.impl.DrvierDaoImpl;
import entity.Driver;
import service.DriverService;

public class DrvierServiceImpl implements DriverService {
    DrvierDao drvierDao= new DrvierDaoImpl();
    @Override
    public Driver login(String name, String password){return drvierDao.login(name,password);}
    @Override
    public int register(Driver driver) throws Exception {
        return drvierDao.register(driver);
    }

    @Override
    public Driver queryDriver(String name)
    {
        return drvierDao.queryDriver(name);
    }
    @Override
    public Driver queryDriver2(String name)
    {
        return drvierDao.queryDriver2(name);
    }
    @Override
    public int receiptOrder(int orderid,String drivername,String drviernumber) throws Exception {
        return drvierDao.receiptOrder(orderid,drivername,drviernumber);
    }
    @Override
    public int receiptOrder2(int orderid) throws Exception {
        return drvierDao.receiptOrder2(orderid);
    }
    @Override
    public int insertlogintime(String name)throws Exception{
        return drvierDao.insertlogintime(name);
    }
    @Override
    public int changedriverstates(String name)throws Exception{
        return drvierDao.changedriverstates(name);
    }
    @Override
    public int changedriverstates2(String name)throws Exception{
        return drvierDao.changedriverstates2(name);
    }
    @Override
    public int application(Driver driver)throws Exception{
        return drvierDao.application(driver);
    }

}
