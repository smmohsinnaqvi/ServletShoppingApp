package shoppingServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/VisitCount")
public class VisitCount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		int n=0;
		Cookie[] all=request.getCookies();
		if(all!=null)
		{
			for(Cookie c : all)
			{
				if(c.getName().equals("visitcount"))
				{
					n=Integer.parseInt(c.getValue());
				}
			}
		}
		n++;
		Cookie c = new Cookie("visitcount",""+n);
		response.addCookie(c);
		out.println("Visit Count : " + n);
		out.println("<a href='VisitCount'> REFRESH </a>");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
