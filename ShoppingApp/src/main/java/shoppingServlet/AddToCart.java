package shoppingServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/addtocart")
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	/**
	 *
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		int pid=Integer.parseInt(request.getParameter("product"));
		
		HttpSession session = request.getSession();
		
		List<Integer> products =(List<Integer>)session.getAttribute("cart");
		if(products == null)
		{
			products=new ArrayList<>();
		}
		products.add(pid);
		session.setAttribute("cart", products);
		
		out.println("<h3>Product " + pid+ " added to Cart</h3>");
		out.println("<h3>There are " + products.size() + " item(s) in Cart</h3>");
		
		out.println("<a href='viewcart'>View Cart</a>");
		out.println("<a href='home'>Back to Categories</a>");
		

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
