package servlet;
import entity.Order;
import service.DriverService;
import service.impl.DrvierServiceImpl;
import service.impl.OrderServiceImpl;
import util.JsonUtil;
import util.PageBean;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;

@WebServlet("/orderServlet")
public class OrderServlet extends HttpServlet {
    OrderServiceImpl orderService= new OrderServiceImpl();
    DriverService driverService = new DrvierServiceImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String action=request.getParameter("action");
        try {
            //使用反射定义方法
            Method method=getClass().getDeclaredMethod(action, HttpServletRequest.class,
                    HttpServletResponse.class);
            //调用方法
            method.invoke(this, request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String findOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pc = getPc(request);
        int pr = 5;//给定pr的值，每页5行纪录
        HttpSession session = request.getSession();
        String passenger = (String) session.getAttribute("passenger");//获取登录乘客
        if (passenger.equals(null)) {
            request.setAttribute("info", "你还没登陆！");
            ((HttpServletRequest) request).getRequestDispatcher("palogin.jsp").forward(request, response);
            return "palogin.jsp";
        }
        PageBean<Order> pb = orderService.findOrder(pc, pr, passenger);
        if(pb.equals(null)){
            request.getRequestDispatcher("orderServlet?action=currentorder").forward(request,response);
        }
        pb.setUrl(getUrl(request));
        request.setCharacterEncoding("utf-8");
        request.setAttribute("pb", pb);
        request.getRequestDispatcher("passenger/list.jsp").forward(request,response);
        return "f:passenger/list.jsp";
    }

    public String findOrderbyorderid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderid = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        String passenger = (String) session.getAttribute("passenger");//获取登录乘客
        if (passenger.equals(null)) {
            request.setAttribute("info", "你还没登陆！");
            ((HttpServletRequest) request).getRequestDispatcher("palogin.jsp").forward(request, response);
            return "palogin.jsp";
        }
        Order order = orderService.queryOrderByid(orderid);
        if(order.getStates()==1){
            order.setStatesmean("已接单");
            order.setEndtime("当前订单未结束");
        }else if(order.getStates()==2){
            order.setStatesmean("已结单");
        }else if(order.getStates()==3){
            order.setStatesmean("乘客已上车");
            order.setEndtime("当前订单未结束");
        }else{
            order.setDrivername("当前还没有司机接单");
            order.setDrviernumber("没有司机接单");
            order.setEndtime("没有接单");
            order.setStatesmean("未接单");
        }
        request.setCharacterEncoding("utf-8");
        request.setAttribute("passenger", passenger);
        request.setAttribute("order", order);
        request.getRequestDispatcher("passenger/orderdetail.jsp").forward(request,response);
        return "f:passenger/orderdetail.jsp";
    }


    public void queryOrderbydrivername(HttpServletRequest request, HttpServletResponse response)throws Exception{
        HttpSession session = ((HttpServletRequest) request).getSession();
        String driver = (String) session.getAttribute("driver");//获取登陆司机
        if (driver.equals(null)) {
            request.setAttribute("info", "你还没登陆！");
            ((HttpServletRequest) request).getRequestDispatcher("drlogin.jsp").forward(request, response);
            return;
        }
        if(orderService.queryOrderbydrivername(driver)!=null) {
            Order order = orderService.queryOrderbydrivername(driver);
            if(order.getStates()==1){
                order.setStatesmean("你已接单");
            }
            if(order.getStates()==3){
                order.setStatesmean("乘客已上车");
            }
            request.setAttribute("order", order);
            request.getRequestDispatcher("driver/currentlist.jsp").forward(request,response);
            return;
        }else{
            request.setAttribute("info", "你还没有正在进行中的订单！");
            request.getRequestDispatcher("driver/index.jsp").forward(request,response);
            return;
        }
    }

    public String findOrderAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pc = getPc(request);
        int pr = 5;//给定pr的值，每页5行纪录
        HttpSession session = ((HttpServletRequest) request).getSession();
        String driver = (String) session.getAttribute("driver");//司机获取登录乘客，先要司机登录成功
        if (driver.equals(null)) {
            request.setAttribute("info", "你还没登陆！");
            ((HttpServletRequest) request).getRequestDispatcher("drlogin.jsp").forward(request, response);
            return "drlogin.jsp";
        }
        PageBean<Order> pb = orderService.findOrderAll(pc, pr);
        pb.setUrl(getUrl(request));
        request.setCharacterEncoding("utf-8");
        request.setAttribute("pb2", pb);
        request.getRequestDispatcher("driver/list.jsp").forward(request,response);
        return "f:driver/list.jsp";
    }

    public String findcloseOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pc = getPc(request);
        int pr = 5;//给定pr的值，每页5行纪录
        HttpSession session = request.getSession();
        String passenger = (String) session.getAttribute("passenger");//获取登录乘客
        if (passenger.equals(null)) {
            request.setAttribute("info", "你还没登陆！");
            ((HttpServletRequest) request).getRequestDispatcher("palogin.jsp").forward(request, response);
            return "palogin.jsp";
        }
        if(orderService.queryOrderBypaexist(passenger,2)==null){
            request.setAttribute("info", "你还没有结束的订单，请把第一次的行程献给我们快车系统吧！");
            request.getRequestDispatcher("passenger/index.jsp").forward(request, response);
            return "passenger/index.jsp";
        }
        try {
            PageBean<Order> pb = orderService.findcloseOrder(pc, pr, passenger, 2);//已结束的订单
            pb.setUrl(getUrl(request));
            request.setCharacterEncoding("utf-8");
            request.setAttribute("pb", pb);
        }catch (RuntimeException e){
            request.setAttribute("info", "你还没有结束的订单，请把第一次的行程献给我们快车系统吧！");
            request.getRequestDispatcher("passenger/index.jsp").forward(request, response);
            return "passenger/index.jsp";
        }
        request.setAttribute("passenger", passenger);
        request.getRequestDispatcher("passenger/closeorderlist.jsp").forward(request,response);
        return "f:passenger/closeorderlist.jsp";
    }

    public String  closeorderbydrivername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pc = getPc(request);
        int pr = 5;//给定pr的值，每页5行纪录
        HttpSession session = ((HttpServletRequest) request).getSession();
        String driver = (String) session.getAttribute("driver");//获取登陆司机
        if (driver.equals(null)) {
            request.setAttribute("info", "你还没登陆！");
            ((HttpServletRequest) request).getRequestDispatcher("drlogin.jsp").forward(request, response);
            return "drlogin.jsp";
        }
        if(orderService.queryOrderBydrexist(driver,2)==null){
            request.setAttribute("info", "你还没有结束的订单，加油吧，请努力接单！");
            request.getRequestDispatcher("driver/index.jsp").forward(request, response);
            return "driver/index.jsp";
        }
        try {
            PageBean<Order> pb = orderService.closeorderbydrivername(pc, pr, driver, 2);//已结束的订单
            pb.setUrl(getUrl(request));
            request.setCharacterEncoding("utf-8");
            request.setAttribute("pb", pb);
        }catch (RuntimeException e){
            request.setAttribute("info", "你还没有结束的订单，加油吧，请努力接单！");
            request.getRequestDispatcher("driver/index.jsp").forward(request, response);
            return "driver/index.jsp";
        }
        request.setAttribute("driver", driver);
        request.getRequestDispatcher("driver/closeorderlist.jsp").forward(request,response);
        return "f:driver/closeorderlist.jsp";
    }


    public void deleteOrderByid(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        int orderid = Integer.parseInt(request.getParameter("id"));
        HttpSession session = ((HttpServletRequest) request).getSession();
        String user = (String) session.getAttribute("passenger");
        if (user == null) {
            request.setAttribute("info", "你还没有登录！");
            ((HttpServletRequest) request).getRequestDispatcher("palogin.jsp").forward(request, response);
        }
        Order order = orderService.queryOrderByid(orderid);
        if(order.getStates()==1){
            response.getWriter().print("{\"res\": -1, \"info\":\"你的订单号"+orderid+"已经被"+order.getDrivername()+"司机接单了，不能取消订单了\"}");
            return;
        }else if(order.getStates()==3){
            response.getWriter().print("{\"res\": -1, \"info\":\"你已经上车了，订单正在进行中，不能取消订单了\"}");
            return;
        }else if(order.getStates()==2){
            response.getWriter().print("{\"res\": -1, \"info\":\"订单号为"+orderid+"的订单已经结单了，不能取消订单了\"}");
            return;
        }else if(order.getStates()==4){
            response.getWriter().print("{\"res\": -1, \"info\":\"你的行程已经结束了，请尽快支付你此次行程的车费，做个有信用的人\"}");
            return;
        }
        else {
            if(orderService.deleteOrderByid(orderid)==1){
                response.getWriter().print("{\"res\": 1, \"info\":\"订单号为" + orderid + "的订单已经成功取消！\"}");
                return;
            }else{
                response.getWriter().print("{\"res\": 2, \"info\":\"订单取消失败\"}");
                return;
            }
        }
    }

    public void driverdeleteOrderByid(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        int orderid = Integer.parseInt(request.getParameter("id"));
        HttpSession session = ((HttpServletRequest) request).getSession();
        String user = (String) session.getAttribute("driver");
        if (user == null) {
            request.setAttribute("info", "你还没有登录！");
            ((HttpServletRequest) request).getRequestDispatcher("drlogin.jsp").forward(request, response);
        }
        Order order = orderService.queryOrderByid(orderid);
        if(order.getStates()==3){
            response.getWriter().print("{\"res\": -1, \"info\":\"乘客已经上车了，订单正在进行中，司机不能取消订单了\"}");
            return;
        }else if(order.getStates()==2){
            response.getWriter().print("{\"res\": -1, \"info\":\"订单号为"+orderid+"的订单已经结单了，司机不能取消订单了\"}");
            return;
        }
        else {
            //司机还要改变自身状态为初始
             driverService.changedriverstates2(user);
            if(orderService.driverdeleteOrderByid(orderid)==1){
                response.getWriter().print("{\"res\": 1, \"info\":\"订单号为" + orderid + "的订单已经成功取消！\"}");
                return;
            }else{
                response.getWriter().print("{\"res\": 2, \"info\":\"订单取消失败\"}");
                return;
            }
        }
    }

    public  Order currentorder(HttpServletRequest request, HttpServletResponse response)throws Exception{
        HttpSession session = ((HttpServletRequest) request).getSession();
        String passenger = (String) session.getAttribute("passenger");
        if(orderService.queryOrder(passenger)!=null) {
            Order order = orderService.queryOrder(passenger);
            if(order.getStates()==1){
                order.setStatesmean("已有司机接单");
            }
            if(order.getStates()==3){
                order.setStatesmean("你已上车");
            }
            if(order.getStates()==0){
                order.setStatesmean("未接单");
            }
            if(order.getStates()==4){
                order.setStatesmean("等待你的支付中");
            }
            request.setAttribute("order", order);
            request.getRequestDispatcher("passenger/currentlist.jsp").forward(request,response);
            return  order;
            }else{
            request.setAttribute("info", "你还没有正在进行中的订单！");
            request.getRequestDispatcher("passenger/index.jsp").forward(request,response);
            return null;
        }
    }
     public void  queryOrderByid(HttpServletRequest request, HttpServletResponse response)throws Exception{
         int orderid = Integer.parseInt(request.getParameter("id"));
         if(orderService.queryOrderByid(orderid)!=null) {
             Order order = orderService.queryOrderByid(orderid);
             if(order.getStates()==1){
                 order.setStatesmean("已接单");
                 order.setEndtime("当前订单未结束");
             }else if(order.getStates()==2){
                 order.setStatesmean("已结单");
             }else if(order.getStates()==3){
                 order.setStatesmean("乘客已上车");
                 order.setEndtime("当前订单未结束");
             }else if(order.getStates()==4){
                 order.setStatesmean("等待乘客支付中");
                 order.setEndtime("当前订单未结束");
             }
             else{
                 order.setDrivername("当前还没有司机接单");
                 order.setDrviernumber("没有司机接单");
                 order.setEndtime("没有接单");
                 order.setStatesmean("未接单");
             }
             request.setAttribute("order", order);
             request.getRequestDispatcher("driver/detaillist.jsp").forward(request,response);
             return;
         }else{
             request.setAttribute("info", "查找有误，请核对后重新输入");
             request.getRequestDispatcher("driver/index.jsp").forward(request,response);
             return;
         }
     }

    public void  boardingorder(HttpServletRequest request, HttpServletResponse response)throws Exception{
        int orderid = Integer.parseInt(request.getParameter("id"));
            Order order = orderService.queryOrderByid(orderid);
            if(order!=null){
            if(order.getStates()==3){
                order.setStatesmean("乘客已上车");
            }
            request.setAttribute("order1", order);
            request.getRequestDispatcher("driver/boardingorder.jsp").forward(request,response);
            return;
        }else{
            request.getRequestDispatcher("driver/currentlist.jsp").forward(request,response);
            return;
        }
    }

    public void changeOrderByid(HttpServletRequest request, HttpServletResponse response)throws Exception{
        int orderid = Integer.parseInt(request.getParameter("id"));
        if(orderService.changeOrderByid(orderid)==1) {
            Order order = orderService.queryOrderByid(orderid);
            if(order.getStates()==3){
               order.setStatesmean("乘客已上车");
            }
            request.setAttribute("order1", order);
            JsonUtil.Json(order);//实体对象转json
            response.getWriter().print("{\"res\": 1, \"info\":\""+order.getUsername()+"乘客已上车\"}");
            return;
        }else{
             response.getWriter().print("{\"res\": -1, \"info\":\"操作失败\"}");
            return;
        }
    }

    public String changeOrderByidAndroid(HttpServletRequest request, HttpServletResponse response)throws Exception{
        int orderid = Integer.parseInt(request.getParameter("id"));
        if(orderService.changeOrderByid(orderid)==1) {
            Order order = orderService.queryOrderByid(orderid);
            if(order.getStates()==3){
                order.setStatesmean("乘客已上车");
            }
            request.setAttribute("order1", order);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String jsonStr = JsonUtil.Json(order);
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.write(jsonStr);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            return JsonUtil.Json(order);//实体对象转json

        }else{
            response.getWriter().print("{\"res\": -1, \"info\":\"操作失败\"}");
            return "操作失败";
        }
    }
    public  void closeorder(HttpServletRequest request, HttpServletResponse response)throws Exception {
        int orderid = Integer.parseInt(request.getParameter("id"));
        Order order = orderService.queryOrderByid(orderid);
        if(order.getStates()==2){
            order.setStatesmean("已结单");
            request.setAttribute("order", order);
            request.getRequestDispatcher("driver/closeorder.jsp").forward(request, response);
            return;
        } else if(order.getStates()==4){
            order.setStatesmean("等待乘客支付中");
            request.setAttribute("order", order);
            request.getRequestDispatcher("driver/closeorder.jsp").forward(request, response);
            return;
        }
        else {
            request.setAttribute("order1", order);
            request.getRequestDispatcher("driver/boardingorder.jsp").forward(request, response);
            return;
        }
    }
      //乘客支付订单结算
    public  void orderSettlement(HttpServletRequest request, HttpServletResponse response)throws Exception {
        int orderid = Integer.parseInt(request.getParameter("id"));
        if(orderService.closeorderBypassengerpayment(orderid)==1){
            request.getRequestDispatcher("orderServlet?action=findcloseOrder").forward(request, response);
            return;
        } else {
            Order order=orderService.queryOrderByid(orderid);
            request.setAttribute("order",order);
            request.getRequestDispatcher("passenger/currentlist.jsp").forward(request, response);
            return;
        }
    }


    public  String closeorderAndroid(HttpServletRequest request, HttpServletResponse response)throws Exception {
        int orderid = Integer.parseInt(request.getParameter("id"));
        Order order = orderService.queryOrderByid(orderid);
        if(order.getStates()==2){
            order.setStatesmean("已结单");
            String jsonStr = JsonUtil.Json(order);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.write(jsonStr);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
           return JsonUtil.Json(order);//实体对象转json
        } else {
            PrintWriter out = null;
            out = response.getWriter();
            out.write("数据获取失败");
            return "数据获取失败";
        }
    }
    public  void  closeorderByid(HttpServletRequest request, HttpServletResponse response)throws Exception{
        int orderid = Integer.parseInt(request.getParameter("id"));
        HttpSession session = ((HttpServletRequest) request).getSession();
        String driver = (String) session.getAttribute("driver");//获取登陆司机
        //改变司机自身状态和订单状态
        if(orderService.closeorderByid(orderid)==1&&driverService.changedriverstates2(driver)==1) {
            response.getWriter().print("{\"res\": 1, \"info\":\"订单号为"+orderid+"的订单已经申请结单了，正在等待乘客支付中！\"}");
            return;
        }else{
            response.getWriter().print("{\"res\": -1, \"info\":\"结束订单失败，请重试\"}");
            return;
        }
    }

    private int getPc(HttpServletRequest request) {
        String value = request.getParameter("pc");

        if (value == null || value.trim().isEmpty()) {
            return 1;
        }
        return Integer.parseInt(value);
    }
    private String getUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String queryString = request.getQueryString();

        if (queryString.contains("&pc=")) {
            int index = queryString.lastIndexOf("&pc=");
            queryString = queryString.substring(0, index);
        }

        return contextPath + servletPath + "?" + queryString;
    }
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}

