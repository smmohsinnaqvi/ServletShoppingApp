package shoppingServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.User;


@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Connection con;
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		con=(Connection)config.getServletContext().getAttribute("jdbccon");
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
		}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid= request.getParameter("uid");
		String pwd=request.getParameter("pwd");
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
		    ps = con.prepareStatement("select * from users where u_id=? and password=?");
			ps.setString(1,uid);
			ps.setString(2, pwd);
			rs = ps.executeQuery();
			if(rs.next())
			{
				Cookie[] all = request.getCookies();
				if(all!=null)
				{
					for(Cookie c : all)
					{
						if(c.getName().equals("loginerr"))
							c.setMaxAge(0);
						response.addCookie(c);
					}
				}
				User u = new User(rs.getString(1),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
				HttpSession session = request.getSession();
				session.setAttribute("loggedinuser", u);
				RequestDispatcher rd = request.getRequestDispatcher("/home");
				rd.forward(request, response);
			}
			else
			{
				Cookie c = new Cookie("loginerr","Wrong_UID/PWD");
				response.addCookie(c);
				response.sendRedirect("/ShoppingApp/login.jsp");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				ps.close();		
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
