package service;
import entity.Driver;
public interface DriverService {
    Driver login(String name, String password);
    int register(Driver driver) throws Exception;
    int insertlogintime(String name)throws Exception;//插入登陆时间
    Driver queryDriver(String name);
    Driver queryDriver2(String name);//查询司机是否还有定单在进行中，如果有则不能接新订单，必须当前订单接续后，司机状态改变为初始状态才能继续接单
    int receiptOrder(int orderid,String drivername,String drviernumber) throws Exception;//司机接受订单，把司机姓名以及联系方式加到订单列表中
    int receiptOrder2(int orderid) throws Exception;
    int changedriverstates(String name) throws Exception;//改变司机自身状态，1已经接单不可以再接其它订单，0未接单可以接其它订单中的一个
    int changedriverstates2(String name) throws Exception;//改变司机自身状态，1已经接单不可以再接其它订单，0未接单可以接其它订单中的一个
}
