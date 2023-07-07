package shoppingServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.User;


@WebServlet("/confirmcart")
public class ConfirmCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

    Connection con;
	
	public void init(ServletConfig config) throws ServletException {
			super.init(config);
			con=(Connection)config.getServletContext().getAttribute("jdbccon");
			
		
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		PrintWriter out=response.getWriter();
		HttpSession session = request.getSession();
		User u=(User)session.getAttribute("loggedinuser"); 
		float tprice=(Float)session.getAttribute("tprice");
		java.sql.Timestamp ts= new java.sql.Timestamp(new java.util.Date().getTime());
		String uid=u.getUid();
		PreparedStatement ps=null;
		int n;
		try {
		ps = con.prepareStatement("insert into shopping(user_id,shoppingdate,totalprice) values(?,?,?)");
		 ps.setString(1,uid);
		 ps.setTimestamp(2, ts);
		 ps.setFloat(3, tprice);
		 n=ps.executeUpdate();
		 if(n==1)
		 {
			 out.println("<h2>Your order has been Confirmed</h2>");
			 out.println("<h2>Your order's details will be sent to " + u.getEmail() +" and " + u.getContact()+"</h2>");
			 out.println("<a href='home'>Back to Categories</a>");
			 out.println("<a href='delete'>LOGOUT</a>");
		 }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}

}
