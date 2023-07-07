package shoppingServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/viewcart")
public class ViewCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 Connection con;
	 
	 @Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		con=(Connection)config.getServletContext().getAttribute("jdbccon");
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		List<Integer> sps = (List<Integer>)session.getAttribute("cart");
		if(sps==null)
		{
			out.println("<h2>Cart is Empty</h2>");
		}
		else
		{
			PreparedStatement ps=null;
			ResultSet rs=null;
			try {
				float tprice=0;
				ps = con.prepareStatement("select * from product where p_id=?");
				out.println("<table border=1>");
				for(int n : sps)
				{
					ps.setInt(1, n);
					rs=ps.executeQuery();
					if(rs.next())
					{
					tprice+=rs.getFloat(4);
					out.println("<tr>");
					out.println("<td>" + rs.getInt(1)+ "</td>" + "<td>" + rs.getString(2)+ "</td>" + "<td>" + rs.getFloat(4)+ "</td>");
					out.println("</tr>");
					}
					
				}
				session.setAttribute("tprice", tprice);
				out.println("<tr><td colspan=2>Total Price</td><td>" + tprice + "</td>");
				out.println("</table>");
				out.println("<a href='confirmcart'>Confirm Cart</a>");
				out.println("<a hreg='home'>Back To Categories</a>");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
