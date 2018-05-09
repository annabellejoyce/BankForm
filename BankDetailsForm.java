import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/register")
public class BankDetailsForm extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// write the data of the response
		out.println("<html>" + "<head><title>Enter details</title></head>");
		out.println("<body>"
				+ // web form generation
				"<p>Name: </p>" + "<form method=\"get\">"
				+ "<input type=\"text\" name=\"name\" size=\"25\">"
				+ "<p>Date of Birth: </p>"
				+ "<input type\"text\" name=\"dob\" size=\"25\">"
				+ "<p>Address</p>"
				+ "<input type=\"text\" name=\"address\" size=\"25\"><br><br>"
				+ "<input type=\"submit\" value=\"Submit\">"
				+ "<input type=\"reset\" value=\"Reset\">" + "</form>");

		// get input values
		String name = request.getParameter("name");
		String dob = request.getParameter("dob");
		String address = request.getParameter("address");
		String id = name + dob;
		// dummy balance string
		String balance = "1000.00";
		
		// get the address if saved as scoped variable with id
		String registeredAddress = (String)request.getSession().getAttribute(id);
		// boolean if the address value is/is not null, customer is/is not registered
		Boolean registeredCustomer = registeredAddress != null;

		// if name and dob are not null/empty
		if (name != null && name.length() > 0 && dob != null && dob.length() > 0) {
			// if address is not null/empty
			if (address != null && address.length() > 0) {
				// if there is no registered address, i.e. not registered
				if(!registeredCustomer) {
					// set the id and address
					request.getSession().setAttribute(id, address);
					out.println("<p>You're now registered!</p>");
				} else {
					// if registered already, override the address value for that id
					out.println("<p>Your address has been updated!</p>");
					out.println("<p>Your id: " + id + "</p>");
					request.getSession().setAttribute(id, address);
					out.println("<p>Old Address: " + registeredAddress + "</p>");
					out.println("<p>Address: " + address + "</p>");
				}
			// if address field is empty
			} else {
				// and customer is registered
				if(registeredCustomer) {
					out.println("<p>Already Registered!</p>");
					out.println("<p>Address: " + registeredAddress + "</p>");
					out.println("<p>Balance: " + balance + "</p>");
				//if they are not registered and the address field is empty, error
				} else {
					out.println("<p>Error! Those details were not found.</p>");
				}
			}
			
		//if name and dob fields are empty
		} else {
			out.println("<p>Please enter your details</p>");
		}
		
		// closing html tags
		out.println("</body></html>");
		out.close();
	}

	public String getServletInfo() { // Info about the servlet
		// (optional)
		return "Servlet …, Author …, Creation date … ";
	}
}
