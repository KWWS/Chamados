<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cadastro de Usuários</title>
</head>
<body>
	<%
		if(request.getParameter("txtLogin")!=null){
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");

				String SQL2="INSERT INTO usuarios (login, senha) VALUES (?,?)";
				
				try {
					
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chamados?useTimezone=true&serverTimezone=UTC", "root", "");
	
					PreparedStatement pstm = conn.prepareStatement(SQL2);
					pstm.setString(1, request.getParameter("txtLogin"));
					pstm.setString(2, request.getParameter("txtSenha"));
					pstm.execute();
					pstm.close();
					conn.close();
					
					response.sendRedirect("http://localhost:8080/Chamados/ListarChamados");
				}catch(SQLException e) {
					out.println("Erro ao tentar conectar com o banco");
					out.println(e);
				}
			} catch (ClassNotFoundException ex) {
				out.println("Problema ao carregar driver de conexão");
			}
		}
		
	
	%>
	<form method="POST" action="cadastro_usuario.jsp">
		Login: <br><input type="text" name="txtLogin">
		<br>
		Senha: <br><input type="password" name="txtSenha">
		<br>
		<input type="submit" value="Cadastrar">
	</form>
</body>
</html>