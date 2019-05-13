

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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ListarChamadosServlet
 */
public class ListarChamadosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarChamadosServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter sai = response.getWriter();
		HttpSession sessao = request.getSession();
		if(sessao.getAttribute("login")==null) {
			response.sendRedirect("http://localhost:8080/Chamados/Login");
		}else {
			sai.println("Seu IP: " + sessao.getAttribute("info"));
			sai.println("<br><a href='http://localhost:8080/Chamados/Login?msg=logoff'>Sair</a>");
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			
				
				try {
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chamados?useTimezone=true&serverTimezone=UTC", "root", "");
					
					String SQL3="SELECT * FROM chamados";
					if(request.getParameter("id")!=null) {
						int ID = Integer.parseInt(request.getParameter("id"));
						String SQL4="DELETE FROM chamados WHERE id=?";
						PreparedStatement pstm = conn.prepareStatement(SQL4);
						pstm.setInt(1, ID);
						pstm.execute();
					}
					Statement stm = conn.createStatement();
				
					ResultSet rs = stm.executeQuery(SQL3);
					
					sai.println("<table width='100%'>");
					sai.println("<tr bgcolor='#c0c0c0'>");
					sai.println("<td>ID</td>");
					sai.println("<td>Titulo</td>");
					sai.println("<td>Data</td>");
					sai.println("<td>Editar</td>");
					sai.println("<td>Apagar</td>");
					sai.println("</tr>");
					while(rs.next()) {
						sai.println("<td>"+rs.getInt("id")+"</td>");
						sai.println("<td>"+rs.getString("titulo")+"</td>");
						sai.println("<td>"+rs.getDate("data")+"</td>");
						sai.println("<td><a href='http://localhost:8080/Chamados/EditarChamados?id="+rs.getInt("id")+"'>[Editar]</a></td>");
						sai.println("<td><a href='http://localhost:8080/Chamados/ListarChamados?id="+rs.getInt("id")+"'>[Apagar]</a></td>");
						sai.println("</tr>");
					}	
					sai.println("</table>");
					stm.close();
					conn.close();
				}catch(SQLException e) {
					sai.println("Erro ao tentar conectar com o banco");
					sai.println(e);
				}
			} catch (ClassNotFoundException ex) {
				sai.println("Problema ao carregar driver de conexão");
			}
		}
	}
	
	
}
