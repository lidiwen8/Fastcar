package entity;

public class Order {
    private Integer orderid;//订单id

    private String username;//乘客姓名
    private String drivername;//司机姓名
    private String origin;//起始点

    private String destination;//终点

    private String createtime;//订单创建时间
    private String singletime;//司机接单时间
    private String endtime;//订单结束时间
    private float orderprize;//订单金额
    private Integer states;//订单状态有  -1：取消订单；0：未接单；1：已接单；2：已结单;3:确认乘客已上车4;:等待乘客支付中
    private String statesmean;//订单状态中文含义
    private String taximode;//打车方式：快车，顺风车，专车，商务车
    private String passengernumber;//乘客联系方式
    private String drviernumber;//司机联系方式

    private String evaluate;//评价信息等级：五星级好评，四星级好评，三星级中评，二星级一般，一星级差评
    private String evaluateinfo;//评价详情信息
    private String evaluatetime;//评价时间

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination == null ? null : destination.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime == null ? null : endtime.trim();
    }

    public Integer getStates() {
        return states;
    }

    public void setStates(Integer states) {
        this.states = states;
    }

    public String getTaximode() {
        return taximode;
    }

    public void setTaximode(String taximode) {
        this.taximode = taximode;
    }

    public String getPassengernumber() {
        return passengernumber;
    }

    public void setPassengernumber(String passengernumber) {
        this.passengernumber = passengernumber;
    }

    public String getStatesmean() {
        return statesmean;
    }

    public void setStatesmean(String statesmean) {
        this.statesmean = statesmean;
    }

    public String getDrviernumber() {
        return drviernumber;
    }

    public void setDrviernumber(String drviernumber) {
        this.drviernumber = drviernumber;
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getEvaluateinfo() {
        return evaluateinfo;
    }

    public void setEvaluateinfo(String evaluateinfo) {
        this.evaluateinfo = evaluateinfo;
    }

    public String getEvaluatetime() {
        return evaluatetime;
    }

    public void setEvaluatetime(String evaluatetime) {
        this.evaluatetime = evaluatetime;
    }

    public String getSingletime() {
        return singletime;
    }

    public void setSingletime(String singletime) {
        this.singletime = singletime;
    }

    public float getOrderprize() {
        return orderprize;
    }

    public void setOrderprize(float orderprize) {
        this.orderprize = orderprize;
    }
}