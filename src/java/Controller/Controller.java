/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.*;
import Model.*;

/**
 *
 * @author aryner
 */
@WebServlet(name="Controller", urlPatterns={"/Controller","/register","/createUser"})
public class Controller extends HttpServlet {
	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		String userPath = request.getServletPath(); 
		HttpSession session = request.getSession(); 

		//The user is not logged in so is redirected to the index/login page
		if(session.getAttribute("user") == null && !userPath.equals("/register")) {
			response.sendRedirect("/Cornea_Grader/index.jsp");
			return;
		}

		String url = "/WEB-INF/view" + userPath + ".jsp";

		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (IOException ex){
			ex.printStackTrace(System.out);
		}
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		String userPath = request.getServletPath(); 
		HttpSession session = request.getSession(); 

		if(userPath.equals("/createUser")) {
			String name = request.getParameter("userName");
			String password = request.getParameter("password");
			String rePassword = request.getParameter("rePassword");
			String type = request.getParameter("graderType");

			if(password.equals(rePassword)){
				User user = User.register(name, Integer.parseInt(type), password);

				if(user == null) {
					session.setAttribute("error", "That user name has been taken");
					response.sendRedirect("/Cornea_Grader/register");
					return;
				}
				else { 
					session.setAttribute("user", user); 
					response.sendRedirect("/Cornea_Grader/home"); 
					return;
				} 
			}
			else {
				session.setAttribute("error", "Passwords do not match");
				response.sendRedirect("/Cornea_Grader/register"); 
				return;
			}
		}

		String url = "/WEB-INF/view" + userPath + ".jsp";

		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (IOException ex){
			ex.printStackTrace(System.out);
		}
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
