package servlet;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import entity.Driver;
import entity.Order;
import service.DriverService;
import service.OrderService;
import service.impl.DrvierServiceImpl;
import service.impl.OrderServiceImpl;
import util.Md5Encrypt;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet("/driverServlet")
public class DriverServlet extends HttpServlet {
	DriverService driverService = new DrvierServiceImpl();
	OrderService orderService = new OrderServiceImpl();
	/**
     * @see HttpServlet#HttpServlet()
     */
    public DriverServlet() {
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
		String name= (String) request.getSession().getAttribute("driver");
		ServletContext application = this.getServletContext();
		if(request.getSession().getAttribute("driver") != null) {
			request.getSession().invalidate();//使session无效
			application.removeAttribute("driver");
		}
		request.setAttribute("info", name+"退出成功,欢迎你下次继续访问");
		request.getRequestDispatcher("drlogin.jsp").forward(request,response);
	}

	private void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		String name = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmpass = request.getParameter("password2");
		String number=request.getParameter("number");
		String sex=  request.getParameter("sex");
		if (name!=null&&name!=""&&password!=""&&password!=null&&confirmpass!=null&&confirmpass!="") {
			if (sex.equals(null)||sex.equals("")) {
				response.getWriter().print("{\"res\": 28, \"info\":\"性别不能为空！\"}");
				return;
			}
			if (driverService.queryDriver(name)!= null) {
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
					Driver driver = new Driver();
					driver.setName(name);
					driver.setSex(sex);
					driver.setPwd(password);
					driver.setNumber(number);
					try {
						if (driverService.register(driver)==1) {
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

	private void application(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		String driver1= (String) request.getSession().getAttribute("driver");
		if(driver1.equals(null)){
			response.getWriter().print("{\"res\": -2, \"info\":\"你还没登录！\"}");
			return;
		}
		if(driverService.queryDriver(driver1).getExamineStates()==3){
//			Driver driver2=new Driver();
//			if(driver2.getExamineStates()==3){
				response.getWriter().print("{\"res\": -1, \"info\":\"你已经递交过申请信息了，不要重复提交！\"}");
				return;
//			}
		}
		if(driverService.queryDriver(driver1).getExamineStates()==1){
			response.getWriter().print("{\"res\": -1, \"info\":\"你的申请信息已经通过了，不要重复提交！\"}");
			return;
		}
		String relname = request.getParameter("relname");
		String platenumber = request.getParameter("platenumber");
		String idcard = request.getParameter("idcard");
		String carcolor=request.getParameter("carcolor");
		String motorcycle=  request.getParameter("motorcycle");
		String birthday=  request.getParameter("birthday");
		Driver driver =new Driver();
		driver.setName(driver1);
		driver.setRelname(relname);
		driver.setPlatenumber(platenumber);
		driver.setBirthday(birthday);
		driver.setIdcard(idcard);
		driver.setCarcolor(carcolor);
		driver.setMotorcycle(motorcycle);
		if(driverService.application(driver)==1){
			response.getWriter().print("{\"res\": 1, \"info\":\"申请提交成功，请耐心等待平台审核，4个小时以内给你回复！\"}");
			return;
		}else{
			response.getWriter().print("{\"res\": -1, \"info\":\"提交审核失败，请检查输入项是否正确！\"}");
			return;
		}

	}

	private void queryDriver(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = ((HttpServletRequest) request).getSession();
		String driver = (String) session.getAttribute("driver");//获取登陆司机
		if(driverService.queryDriver(driver)!=null){
			Driver driver1=driverService.queryDriver(driver);
			if(driver1.getExamineStates()==0){
				request.setAttribute("cstm",driver1);
				request.getRequestDispatcher("driver/drvierlist2.jsp").forward(request, response);
				return;
			}
			request.setAttribute("cstm",driver1);
			request.getRequestDispatcher("driver/drvierlist.jsp").forward(request, response);
			return;
		}else {
			request.getRequestDispatcher("driver/application.jsp").forward(request, response);
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
				if (driverService.login(name, password) != null) {
					// 获取application对象
					ServletContext application = this.getServletContext();
					// 获取application对象中user
					ArrayList<String> driver = (ArrayList<String>) application.getAttribute("driver");
					if (driver != null && driver.contains(name)) {
                        Driver driver1=driverService.login(name, password);
						SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String time_2 = dfs.format(new Date());
						Date begin = dfs.parse(driver1.getLogintime());
						Date end = dfs.parse(time_2);
						long miao = (end.getTime() - begin.getTime()) / 1000;
						//除以1000是为了转换成秒
						long fen=miao/60;
						if(fen>30){
							ServletContext application1 = this.getServletContext();
							application1.removeAttribute("driver");
							if(request.getSession().getAttribute("driver") != null) {
								request.getSession().invalidate();//使session无效
							}
							login(request,response);
//							request.setAttribute("info", "系统开了点小差，请再尝试一次登录！");
//							request.getRequestDispatcher("drlogin.jsp").forward(request, response);
//							return;
						}else{
							request.setAttribute("info", "你已经登录了，不能重复登录！");
							request.getRequestDispatcher("drlogin.jsp").forward(request, response);
							return;
						}
					} else {
						if (driver == null)// 如果当前application中没有user，初始化user对象
						{
							driver = new ArrayList<String>();
						}
						driver.add(name);
						application.setAttribute("driver", driver);
						HttpSession session = request.getSession();
						session.setAttribute("driver", name);//获取用户名
						driverService.insertlogintime(name);
						Driver us = driverService.login(name, password);
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
//						request.getRequestDispatcher("orderServlet?action=findOrderAll").forward(request, response);
						request.getRequestDispatcher("driver/index.jsp").forward(request, response);
						return;
					}
				} else {
					request.setAttribute("info", "用户名或密码错误！");
					request.getRequestDispatcher("drlogin.jsp").forward(request, response);
					return;
				}
			}else {
				request.setAttribute("info", "验证码错误！");
				request.getRequestDispatcher("drlogin.jsp").forward(request, response);
				return;
			}
		}else {
			request.setAttribute("info", "请输入用户名和密码！");
			request.getRequestDispatcher("drlogin.jsp").forward(request, response);
			return;
		}
	}
       public void  receiptOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		   request.setCharacterEncoding("utf-8");
		   response.setCharacterEncoding("utf-8");
		   String name = (String) request.getSession().getAttribute("driver");
		   int orderid = Integer.parseInt(request.getParameter("id"));
		   if (driverService.queryDriver(name) != null) {
			   Driver driver = driverService.queryDriver(name);
			   if (driver.getExamineStates() == 0) {
				   response.getWriter().print("{\"res\": 3, \"info\":\"你还没有提交司机审核信息给平台哟，不能接单，请你申请并通过平台审核通过后才能接单！\"}");
				   return;
			   }
		   }
		   if (driverService.queryDriver(name) != null) {
			   Driver driver = driverService.queryDriver(name);
			   if (driver.getExamineStates() == 3) {
				   response.getWriter().print("{\"res\": 4, \"info\":\"你提交的司机审核信息正在审核中，请你稍等，等通过后就能接单了！\"}");
				   return;
			   }
		   }
		   if (driverService.queryDriver(name) != null) {
			   Driver driver = driverService.queryDriver(name);
			   if (driver.getExamineStates() == 2) {
				   response.getWriter().print("{\"res\": 3, \"info\":\"你提交的司机审核信息已经审核了，但是由于你填写的信息有误，审核不通过，请重新填写后提交申请！\"}");
				   return;
			   }
		   }
		   if (driverService.queryDriver2(name) != null) {
			   Driver driver = driverService.queryDriver2(name);
			   if (driver.getStates() == 1) {
				   response.getWriter().print("{\"res\": 2, \"info\":\"你当前还有订单正在进行中，不能接新的订单\"}");
				   return;
			   }
		   } else {
			   if (orderService.queryOrdernoevaluatetime(name) != null) {
				   Order order = orderService.queryOrdernoevaluatetime(name);
				   SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				   String time_2 = dfs.format(new Date());
				   Date begin = dfs.parse(order.getEvaluatetime());
				   Date end = dfs.parse(time_2);
				   long miao = (end.getTime() - begin.getTime()) / 1000;
				   //除以1000是为了转换成秒
				   long fen=miao/60;
				   long hour = (miao / 60) / 60;
				   long time=120-fen;
				   if (hour < 2) {
					   response.getWriter().print("{\"res\": -1, \"info\":\"你在最近一次的订单中，有乘客给了你一星级差评，你需要两个小时之后才能接单，现在还剩" + time + "分钟\"}");
					   return;
				   }else {
					   //移除时间
					   if(orderService.removeEvaluatetime(order.getOrderid())==1){
						   response.getWriter().print("{\"res\": -1, \"info\":\"请善待你的每一位乘客，否则乘客会再一次给你差评，让你无法接单！\"}");
						   return;
					   }
				   }
			   } else {
				   Driver driver = driverService.queryDriver(name);
				   if (driverService.receiptOrder(orderid, driver.getName(), driver.getNumber()) == 1) {
					   if (driverService.changedriverstates(name) == 1) {
						   response.getWriter().print("{\"res\": 1, \"info\":\"接单成功\"}");
						   return;
					   } else {//司机状态未改变，订单状态已改变，此时事物回滚，改变订单状态为初始
						   driverService.receiptOrder2(orderid);
						   response.getWriter().print("{\"res\": -1, \"info\":\"接单失败\"}");
						   return;
					   }
				   } else {
					   response.getWriter().print("{\"res\": -1, \"info\":\"接单失败\"}");
					   return;
				   }
			   }
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
