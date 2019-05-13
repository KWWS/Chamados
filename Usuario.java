

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Usuario
 */
public class Usuario extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
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
					
					response.sendRedirect("http://localhost:8080/Chamados/cadastro_usuario2.jsp?msg=sucesso");
				}catch(SQLException e) {
					out.println("Erro ao tentar conectar com o banco");
					out.println(e);
				}
			} catch (ClassNotFoundException ex) {
				out.println("Problema ao carregar driver de conexão");
			}
		}
	}

}
