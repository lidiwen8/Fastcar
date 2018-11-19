package servlet;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import entity.Order;
import entity.Passenger;
import service.PassengerService;
import service.impl.OrderServiceImpl;
import service.impl.PassengerServiceImpl;
import util.Md5Encrypt;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet("/passengerServlet")
public class PassengerServlet extends HttpServlet {
    PassengerServiceImpl passengerService= new PassengerServiceImpl();
	OrderServiceImpl orderService= new OrderServiceImpl();
	/**
     * @see HttpServlet#HttpServlet()
     */
    public PassengerServlet() {
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
	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String name= (String) request.getSession().getAttribute("passenger");
		ServletContext application = this.getServletContext();
		if(request.getSession().getAttribute("passenger") != null) {
			request.getSession().invalidate();//使session无效
			application.removeAttribute("passenger");//从application中移除
		}
		request.setAttribute("info", name+"退出成功,欢迎你下次继续访问");
		request.getRequestDispatcher("palogin.jsp").forward(request,response);
	}

	private void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		String name = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmpass = request.getParameter("password2");
		String number = request.getParameter("number");//手机号码
		String sex=  request.getParameter("sex");
		if (name!=null&&name!=""&&password!=""&&password!=null&&confirmpass!=null&&confirmpass!="") {
			if (sex.equals(null)||sex.equals("")) {
				response.getWriter().print("{\"res\": 28, \"info\":\"性别不能为空！\"}");
				return;
			}
			if (passengerService.queryPassenger(name)!= null) {
				response.getWriter().print("{\"res\": 19, \"info\":\"尊敬的用户:你输入的账号已经存在!注册失败，请换一个其它账号呗！\"}");
				return;
			}else {
				if (password.equals(confirmpass)) {
					Md5Encrypt md5=new Md5Encrypt();
					try{
						password= md5.Encrypt(password);
					} catch (Exception e) {
						e.printStackTrace();
					}
					Passenger passenger = new Passenger();
					passenger.setName(name);
					passenger.setSex(sex);
					passenger.setPassword(password);
					passenger.setNumber(number);
					try {
						if (passengerService.register(passenger)==1) {
							response.getWriter().print("{\"res\": 1, \"info\":\"注册成功\"}");
							return;
						} else {
							response.getWriter().print("{\"res\": -1, \"info\":\"注册失败\"}");
							return;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					response.getWriter().print("{\"res\": 5, \"info\":\"尊敬的用户:确认密码跟新密码不匹配，请重新输入确认密码！\"}");
					return;
				}
			}
		}else{
			response.getWriter().print("{\"res\": 3, \"info\":\"请输入用户名和密码！\"}");
			return;
		}

	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter("username");
		String password= request.getParameter("password");
		String password1=password;
		String verifyCode = request.getParameter("verifyCode");
		String rememberme = request.getParameter("rememberme");//记住登陆
		if ((name!=""&&password!="")) {
			String vcode = (String) request.getSession().getAttribute("vCode");
			if (verifyCode.equalsIgnoreCase(vcode)) {
				Md5Encrypt md5 = new Md5Encrypt();
				try {
					password = md5.Encrypt(request.getParameter("password"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (passengerService.login(name, password) != null) {
					// 获取application对象
					ServletContext application = this.getServletContext();
					// 获取application对象中user
					ArrayList<String> passenger = (ArrayList<String>) application.getAttribute("passenger");
					if (passenger != null && passenger.contains(name)) {
						Passenger passenger1=passengerService.login(name, password);
						SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String time_2 = dfs.format(new Date());
						Date begin = dfs.parse(passenger1.getLogintime());
						Date end = dfs.parse(time_2);
						long miao = (end.getTime() - begin.getTime()) / 1000;
						//除以1000是为了转换成秒
						long fen=miao/60;
						if(fen>30){
							ServletContext application1 = this.getServletContext();
							application1.removeAttribute("passenger");
							if(request.getSession().getAttribute("passenger") != null) {
								request.getSession().invalidate();//使session无效
							}
							login(request,response);
//							request.setAttribute("info", "系统开了点小差，请再尝试一次登录！");
//							request.getRequestDispatcher("palogin.jsp").forward(request, response);
//							return;
						}else{
							request.setAttribute("info", "你已经登录了，不能重复登录！");
							request.getRequestDispatcher("palogin.jsp").forward(request, response);
							return;
						}
					} else {
						if (passenger == null)// 如果当前application中没有user，初始化user对象
						{
							passenger = new ArrayList<String>();
						}
						passenger.add(name);
						application.setAttribute("passenger", passenger);
						HttpSession session = request.getSession();
						session.setAttribute("passenger", name);//获取用户名
						passengerService.insertlogintime(name);
						Passenger us = passengerService.login(name, password);
						if (us.getSex().equals("男")) {
							request.setAttribute("info", name + "先生登陆成功，欢迎你！");
						} else {
							request.setAttribute("info", name + "女士登陆成功，欢迎你！");
						}
						//存入cookie
						if(rememberme!=null) {
							//创建两个Cookie对象
							Cookie nameCookie = new Cookie("username", name);
							//设置Cookie的有效期为3天
							nameCookie.setMaxAge(60 * 60 * 24 * 3);
							Cookie pwdCookie = new Cookie("password", password1);
							pwdCookie.setMaxAge(60 * 60 * 24 * 3);
							response.addCookie(nameCookie);
							response.addCookie(pwdCookie);
						}
						request.getRequestDispatcher("passenger/index.jsp").forward(request, response);
						//LoginOutUtil.loginOut(this.getServletContext(), session);
						//session.setMaxInactiveInterval(60);//设置过期时间为60秒
						return;
					}
				} else {
					request.setAttribute("info", "用户名或密码错误！");
					request.getRequestDispatcher("palogin.jsp").forward(request, response);
					return;
				}
			}else {
				request.setAttribute("info", "验证码错误！");
				request.getRequestDispatcher("palogin.jsp").forward(request, response);
				return;
			}
		}else {
			request.setAttribute("info", "请输入用户名和密码！");
			request.getRequestDispatcher("palogin.jsp").forward(request, response);
			return;
		}
	}
	private void createOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		String origin = request.getParameter("origin");
		String destination = request.getParameter("destination");
		String taximode = request.getParameter("taximode");
		if(origin.equals(destination)){
			response.getWriter().print("{\"res\": 3, \"info\":\"起点和终点不能相同，创建订单失败\"}");
			return;
		}
		HttpSession session = request.getSession();
		String passenger = (String) session.getAttribute("passenger");//获取登录乘客
		Passenger passenger1 = passengerService.queryPassenger(passenger);
		Date day = new Date();
		SimpleDateFormat da = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//订单创建时间
		Order order = new Order();
		order.setOrigin(origin);
		order.setCreatetime(da.format(day));
		order.setUsername(passenger);
		order.setTaximode(taximode);
		order.setStates(0);
		order.setPassengernumber(passenger1.getNumber());
		order.setDestination(destination);
		if(orderService.queryOrder(passenger)!=null) {
			Order order1 = orderService.queryOrder(passenger);
			if (order1.getStates() == 1||order1.getStates() == 3||order1.getStates() == 0||order1.getStates()==4) {
				response.getWriter().print("{\"res\": 2, \"info\":\"你当前还有订单正在进行中，不能创建新的订单\"}");
				return;
			}
		}
		else {
			try {
				if (passengerService.createOrder(order) == 1) {
					response.getWriter().print("{\"res\": 1, \"info\":\"订单发送成功\"}");
					return;
				} else {
					response.getWriter().print("{\"res\": -1, \"info\":\"订单发送失败\"}");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void passengerpayment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int orderid = Integer.parseInt(request.getParameter("id"));
		try {
			Order order = orderService.queryOrderByid(orderid);
			Date day = new Date();
			SimpleDateFormat da = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//乘客结算当前时间作为最终结单时间
			order.setEndtime(da.format(day));
			if (order != null) {
				request.setAttribute("order", order);
				request.getRequestDispatcher("passenger/payment.jsp").forward(request, response);
				return;
			}
		}catch (NullPointerException e){
			request.getRequestDispatcher("passenger/currentlist.jsp").forward(request, response);
			return;
		}
	}



	public void passengerevaluate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int orderid = Integer.parseInt(request.getParameter("orderid"));
		String evaluate = request.getParameter("evaluate");
		String evaluateinfo = request.getParameter("evaluateinfo");
		if(evaluate.equals("好评")){
			evaluate="五星级好评";
		}
		if(evaluate.equals("差评")){
			evaluate="一星级差评";
		}
		HttpSession session = ((HttpServletRequest) request).getSession();
		String user = (String) session.getAttribute("passenger");
		if (user == null) {
			request.setAttribute("info", "你还没有登录！");
			((HttpServletRequest) request).getRequestDispatcher("palogin.jsp").forward(request, response);
		}
		Order order=orderService.queryOrderByid(orderid);
		try {
			if (!(order.getEvaluate().equals(null) || order.getEvaluate().equals(""))) {
				response.getWriter().print("{\"res\": 2, \"info\":\"你已经对当前订单评价过了，请勿重复提交！\"}");
				return;
			}
		}catch (NullPointerException  e){
			System.out.println("空信息");
		}
		if (passengerService.passengerevaluate(orderid,evaluate,evaluateinfo) == 1) {
			response.getWriter().print("{\"res\": 1, \"info\":\"感谢您的评价！么么哒(* ￣3)(ε￣ *)\"}");
			return;
		} else {
			response.getWriter().print("{\"res\": -1, \"info\":\"评价发送失败\"}");
			return;
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
