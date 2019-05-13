


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NovoChamadoServlet
 */
public class NovoChamadoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NovoChamadoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			PrintWriter out = res.getWriter();
			//out.println("Olá");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Novo Chamado</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Preencha as informações do chamado</h1>");
			out.println("<hr/>");
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
		}catch(IOException e) {
			
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
		/*out.println(titulo);
		out.println(conteudo);
		*/
		out.println("<html>");
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
				String SQL2="INSERT INTO chamados (titulo, conteudo, data) VALUES (?,?,?)";
				
				try {
					
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chamados?useTimezone=true&serverTimezone=UTC", "root", "");
					//Statement stm = conn.createStatement();
					//stm.execute(SQL1);
					//stm.close();
					//conn.close();
					Date data = new Date();
					java.sql.Date dataSQL = new java.sql.Date(data.getTime());
					//SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
					//dt.format(data);
					PreparedStatement pstm = conn.prepareStatement(SQL2);
					pstm.setString(1, titulo);
					pstm.setString(2, conteudo);
					pstm.setDate(3, dataSQL);
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

