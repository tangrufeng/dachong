<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    This is my JSP page.ddddddddddddddddddddddddddddddddd <br>
    <a href="cgi-bin/ltyqihao/getCurQihaoInfo?ltyCode=ssq">获取双色球当前期号</a><br><br>
    <a href="cgi-bin/ltyqihao/getCurQihaoInfo?ltyCode=dlt">获取大乐透当前期号</a><br><br>
    <a href="cgi-bin/ltyqihao/queryHisKJList?ltyCode=ssq&qihaoId=11&pageSize=5">获取双色球历史开奖记录列表</a><br><br>
    <a href="cgi-bin/ltyqihao/queryHisKJList?ltyCode=dlt&qihaoId=11&pageSize=5">获取大乐透历史开奖记录列表</a><br><br>
    <a href="cgi-bin/order/queryUserOrderList?cId=1&uId=1&offset=0&rows=3">获取用户订单历史</a><br><br>
    <a href="cgi-bin/order/queryUserOrderList?cId=1&uId=1&offset=0&rows=3&status=1">获取用户已中奖订单历史</a><br><br>
    <a href="cgi-bin/order/queryUserOrderList?cId=1&uId=1&offset=0&rows=3&status=-1">获取用户待开奖历史</a><br><br>
    <a href="cgi-bin/order/getOrderById?orderId=1&uid=1">获取订单详情</a><br><br>
    <a href="cgi-bin/order/getPayMsg?orderId=1">获取支付报文（未加密）</a><br><br>
  </body>
</html>
