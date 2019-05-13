

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			PrintWriter out = response.getWriter();
			
			if(request.getParameter("msg")!=null) {
				if(request.getParameter("msg").equals("logoff")) {
					HttpSession sessao = request.getSession();
					sessao.invalidate();
					out.println("<span style='color: orange'>Deslogado com sucesso!</span>");
				}
			}
			
			if(request.getParameter("msg")!=null) {
				if(request.getParameter("msg").equals("error")) {
					out.println("<span style='color: orange'>Login e/ou senhas incorreto!</span>");
				}
			}
			
			/*
			String login_name = "";
			
			Cookie[] ck2 = request.getCookies();
			
			if(ck2!=null) {
				for(Cookie cookie : ck2) {
					if(cookie.getName().equals("login_name")) {
						cookie.setMaxAge(0);
						response.addCookie(cookie);
						
					}
				}
			}
			*/
			String login_name = "";
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Login</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Preencha as informações do login</h1>");
			out.println("<hr/>");
			out.println("<form method='POST'>");
			out.println("Login: <br><input type='text' name='txtLogin' value='"+login_name+"'>");
			out.println("<br>");
			out.println("Senha: <br><input type='password' name='txtSenha'>");
			out.println("<br>");
			out.println("<input type='submit' value='Logar'>");
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

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		String login = request.getParameter("txtLogin");
		String senha = request.getParameter("txtSenha");
		/*
		Cookie ck = new Cookie("login_name", login);
		ck.setMaxAge(60*60*24*30*2);
		response.addCookie(ck);
		*/
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Novo Chamado</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Preencha as informações do chamado</h1>");
		out.println("<hr/>");			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				try {
					
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chamados?useTimezone=true&serverTimezone=UTC", "root", "");
					String SQL2="SELECT * FROM usuarios WHERE login=? and senha=?";
					PreparedStatement pstm = conn.prepareStatement(SQL2);
					pstm.setString(1, login);
					pstm.setString(2, senha);
					
					ResultSet rs = pstm.executeQuery();
					
					
					if(rs.next()) {
						pstm.close();
						conn.close();
						HttpSession sessao = request.getSession();
						sessao.setAttribute("login", login);
						sessao.setAttribute("info", request.getRemoteAddr());
						response.sendRedirect("http://localhost:8080/Chamados/ListarChamados");
					}else {
						pstm.close();
						conn.close();
						response.sendRedirect("http://localhost:8080/Chamados/Login?msg=error");
						
					}			
					
				}catch(SQLException e) {
					out.println("Erro ao tentar conectar com o banco");
					out.println(e);
				}
			} catch (ClassNotFoundException ex) {
				out.println("Problema ao carregar driver de conexão");
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