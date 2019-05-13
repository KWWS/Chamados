<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cadastro Usuário 2</title>
</head>
<body>
<%
	if(request.getParameter("msg")!=null){
		out.println("Cadastrado com sucesso");
	}
%>
	<form method="POST" action="Usuario">
		Login: <br><input type="text" name="txtLogin">
		<br>
		Senha: <br><input type="password" name="txtSenha">
		<br>
		<input type="submit" value="Cadastrar">
	</form>
</body>
</html>