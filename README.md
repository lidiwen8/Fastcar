线上地址：http://www.lidiwen.club/Fastcar/

登录界面：
![image](https://github.com/lidiwen8/Fastcar/blob/cc5da341664302de7767400c92a7a0c42fba9914/web/Images/%E4%B9%98%E5%AE%A2%E7%99%BB%E5%BD%95.png?raw=true) 

乘客创建订单
乘客可以在当前订单没有被任何司机接单前创建新的订单
如果已经有司机接单了，将不能创建新的订单，直接跳转到正在进行中的行程列表页面，知道当前行程结束

司机可以选择接单，接单后再乘客没有上车前可以取消订单；接单后的司机将会直接跳转到正在进行的行程列表页，不能继续再接新的订单了，直到当前订单结单后

司机申请结单：
![image](https://github.com/lidiwen8/Fastcar/blob/cc5da341664302de7767400c92a7a0c42fba9914/web/Images/%E7%94%B3%E8%AF%B7%E7%BB%93%E5%8D%95.png?raw=true) 


取消订单分为两种：
乘客取消订单
司机取消订单
乘客只能在司机没有接单前取消订单
乘客只能在乘客上车以前取消订单，司机如果点击确认了乘客上车以后就不能取消订单了

取消的订单将会重新发布到接单列表里，供其它司机选择接单
乘客可以选择继续等待当前订单被新的司机重新接单
乘客也可以选择取消当前订单，当前订单将会从数据库删除
乘客也可以创建新的订单


如果有乘客给了司机一星的差评话，该司机自当前订单结束两个小时内不能继续接新的订单哟！！！！！
乘客只能对司机评价一次，重复评价是无效的
![image](https://github.com/lidiwen8/Fastcar/blob/cc5da341664302de7767400c92a7a0c42fba9914/web/Images/%E8%AF%84%E4%BB%B7%E9%A1%B5%E9%9D%A2.png?raw=true)

![image](https://github.com/lidiwen8/Fastcar/blob/cc5da341664302de7767400c92a7a0c42fba9914/web/Images/%E6%97%A0%E9%87%8D%E5%A4%8D%E8%AF%84%E4%BB%B7.png?raw=true)
![image](https://github.com/lidiwen8/Fastcar/blob/cc5da341664302de7767400c92a7a0c42fba9914/web/Images/%E5%8F%B8%E6%9C%BA%E6%8E%A5%E5%8D%95.png?raw=true)

乘客页面：
![image](https://github.com/lidiwen8/Fastcar/blob/cc5da341664302de7767400c92a7a0c42fba9914/web/Images/%E4%B9%98%E5%AE%A2%E7%BB%93%E6%9D%9F%E8%AE%A2%E5%8D%95%E9%A1%B5%E9%9D%A2.png?raw=true)
司机页面：



同一用户登录不能重复登录，必须等待当前用户退出后，或者半小时后seession失效后

代码结构：
![image](https://github.com/lidiwen8/Fastcar/blob/cc5da341664302de7767400c92a7a0c42fba9914/web/Images/%E9%A1%B9%E7%9B%AE%E4%BB%A3%E7%A0%81%E7%BB%93%E6%9E%84.png?raw=true)


