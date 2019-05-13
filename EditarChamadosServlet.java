

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EditarChamadosServlet
 */
public class EditarChamadosServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.getWriter().append("Served at: ").append(request.getContextPath());
	PrintWriter out = response.getWriter();
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
	
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chamados?useTimezone=true&serverTimezone=UTC", "root", "");
			
			String SQL3="SELECT * FROM chamados WHERE id=?";
			
			PreparedStatement pstm = conn.prepareStatement(SQL3);
			pstm.setInt(1, Integer.parseInt(request.getParameter("id")));
	
			ResultSet rs = pstm.executeQuery();
			
			if(rs.next()) {
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Novo Chamado</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h1>Preencha as informações do chamado</h1>");
				out.println("<hr/>");
				out.println("<form method='POST'>");
				out.println("ID do Chamado: <br><input type='text' name='txtID' readonly='readonly' value='"+rs.getInt("id")+"'>");
				out.println("<br>");
				out.println("Título: <br><input type='text' name='txtTitulo' value='"+rs.getString("titulo")+"'>");
				out.println("<br>");
				out.println("Conteúdo: <br><textarea name='txtConteudo' rows='10' cols='40'>"+rs.getString("conteudo")+"</textarea>");
				out.println("<br>");
				out.println("<input type='submit' value='Atualizar Chamado'>");
				out.println("</form>");
				out.println("<br>");
				out.println("<a href='http://localhost:8080/Chamados/ListarChamados'>Listar Chamados<a/>");
				out.println("<br>");
				out.println("<a href='http://localhost:8080/Chamados/Logoff'>Sair<a/>");
				out.println("</body>");
				out.println("</html>");
			}	
			else {
				out.println("Este chamado nao existe");
			}
			pstm.close();
			conn.close();
		}catch(SQLException e) {
			out.println("Erro ao tentar conectar com o banco: "+e.getMessage());
		}
	} catch (ClassNotFoundException ex) {
		out.println("Problema ao carregar driver de conexão");
	}
}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		PrintWriter out = response.getWriter();
		String titulo = request.getParameter("txtTitulo");
		String conteudo = request.getParameter("txtConteudo");
		int id = Integer.parseInt(request.getParameter("id"));
		out.println("<head>");
		out.println("<title>Novo Chamado</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Preencha as informações do chamado</h1>");
		out.println("<hr/>");
		if(titulo.trim().length()<4) {
			out.println("Preencha o campo titulo");
		}else if(conteudo.trim().length()<4) {
			out.println("Preencha o campo conteudo");
		}
		else {
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				//String SQL1="INSERT INTO chamados (titulo, conteudo) VALUES (";
				//SQL1 += "'"+titulo+"', '"+conteudo+"')";
				String SQL2="UPDATE chamados SET titulo=?, conteudo=? WHERE id=?";
				
				try {
					
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chamados?useTimezone=true&serverTimezone=UTC", "root", "");
					//Statement stm = conn.createStatement();
					//stm.execute(SQL1);
					//stm.close();
					//conn.close();
					PreparedStatement pstm = conn.prepareStatement(SQL2);
					pstm.setString(1, titulo);
					pstm.setString(2, conteudo);
					pstm.setInt(3, id);
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
		
		out.println("<form method='POST'>");
		out.println("Título: <br><input type='text' name='txtTitulo'>");
		out.println("<br>");
		out.println("Conteúdo: <br><textarea name='txtConteudo' rows='10' cols='40'></textarea>");
		out.println("<br>");
		out.println("<input type='submit' value='Abrir Chamado'>");
		out.println("</form>");
		out.println("<br>");
		out.println("<a href='http://localhost:8080/Chamados/ListarChamados'>Listar Chamados<a/>");
		out.println("<br>");
		out.println("<a href='http://localhost:8080/Chamados/Logoff'>Sair<a/>");
		out.println("</body>");
		out.println("</html>");
		}

}
