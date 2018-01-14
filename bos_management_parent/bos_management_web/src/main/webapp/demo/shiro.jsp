<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<shiro:hasPermission name="courierManage:delete">
  <button>快递员作废courierManage:delete</button>
</shiro:hasPermission>
<shiro:hasPermission name="courierManage-delete">
  <button>快递员作废courierManage-delete</button>
</shiro:hasPermission>

<shiro:hasRole name="ceo">
  <button>ceo操作按钮</button>
</shiro:hasRole>

<shiro:hasRole name="boss">
  <button>boss操作按钮</button>
</shiro:hasRole>

</body>
</html>