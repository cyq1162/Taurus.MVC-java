<%@page import="entity.MyEntity"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
JSP Show：<hr />
Hello : good boy ： 
<select name="">
<%
List<MyEntity> list=(List<MyEntity>)request.getAttribute("list");
for(MyEntity entity: list)
{
	out.print("<option value='"+entity.getId()+"'>"+entity.getName()+"</option>");
}
%>
</select>
  在中国.<br/>
  
  <br/><hr /><br/>
  Go To WebAPI Test：<a href="/index.html" target="_blank">index.html</a>
  <br/><hr />
</body>
</html>