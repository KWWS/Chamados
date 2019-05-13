<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<table>
	<tr>
		<td>ID</td>
		<td>Login</td>
	</tr>
			<%
					//try {
						Class.forName("com.mysql.cj.jdbc.Driver");
		
						String SQL2="SELECT * FROM usuarios";
						
						//try {
							
							Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chamados?useTimezone=true&serverTimezone=UTC", "root", "");
			
							Statement stm = conn.prepareStatement(SQL2);
		
							ResultSet rs = stm.executeQuery(SQL2);
							
							while(rs.next()){
			%>
				<tr>
					<td><% out.println(rs.getString("id")); %></td>
					<td><% out.println(rs.getString("login")); %></td>
				</tr>
			<% 		
							}
						//}catch(SQLException e) {
							out.println("Erro ao tentar conectar com o banco");
							//out.println(e);
						//}
					//} catch (ClassNotFoundException ex) {
						out.println("Problema ao carregar driver de conexão");
					//}		
			%>
	</table>	
</body>
</html>