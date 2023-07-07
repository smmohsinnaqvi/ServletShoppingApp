package shoppingServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/getproducts")
public class GetProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection con;
    
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		con=(Connection)config.getServletContext().getAttribute("jdbccon");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=con.prepareStatement("select * from product where cat_id='"+request.getParameter("cid")+"'");
			rs=ps.executeQuery();
			out.println("<p>Select Product : </p>");
			out.println("<form action='/ShoppingApp/addtocart' method='post'>");
			out.println("<select name='product'>");
			while(rs.next())
			{
				out.println("<option value='"+rs.getInt(1)+"'>"+rs.getString(2)+"</option>");
			}
			out.println("</select><br/>");
			out.println("<input type='submit' value='Add To Cart'/>");
			out.println("</form>");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

			
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
