/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import java.io.*; 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.*;
import javax.servlet.ServletOutputStream; 
import Model.*;
import Utilities.*;

/**
 *
 * @author aryner
 */
@WebServlet(name="Controller", urlPatterns={
					"/Controller","/register","/createUser","/home","/logout","/login","/upload_excel",
					"/upload_picture_data","/insert_pictures","/upload_pictures","/img","/assign_right_left",
					"/update_right_left","/grade","/submit_grade"
			       })
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
		User user = (User)session.getAttribute("user");

		//The user is not logged in so is redirected to the index/login page
		if(user == null && !userPath.equals("/register")) {
			response.sendRedirect("/Cornea_Grader/index.jsp");
			return;
		}
		else if(userPath.equals("/home")) {
			request.setAttribute("uploaded",Picture.getUploadedPictures());
			request.setAttribute("not_uploaded",Picture.getNotUploadedPictures());
		}
		else if(userPath.equals("/assign_right_left")) {
			String fileName = request.getParameter("fileName");

			request.setAttribute("picture",Picture.getPicture(fileName));
			request.setAttribute("neighbors",Picture.getNeighbors(fileName));
		}
		else if(userPath.equals("/grade")) {
			//get images to grade
			int patientNumber = Grade.getNextPatient(user);
			int gradeType = Grade.getGradeType(user, patientNumber);
			int side = Grade.getSide(user, patientNumber, gradeType);

			request.setAttribute("patient_number", patientNumber);
			request.setAttribute("grade_type", gradeType);
			request.setAttribute("side", side);
			request.setAttribute("pictures", Picture.getImages(patientNumber, gradeType, side));
		}
		else if (userPath.equals("/img")) {
			String fileName = request.getParameter("fileName");
			response.setContentType("image/jpeg");
			ServletOutputStream outPut = response.getOutputStream(); 
			FileInputStream imgStream = new FileInputStream(Constants.PICTURE_DIR+fileName);

			BufferedInputStream bufferedIn = new BufferedInputStream(imgStream);
			BufferedOutputStream bufferedOut = new BufferedOutputStream(outPut);

			int nextByte;
			while((nextByte = bufferedIn.read()) != -1) {
				bufferedOut.write(nextByte);
			}

			bufferedIn.close();
			imgStream.close();
			bufferedOut.close();
			outPut.close();
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
		User user = (User)session.getAttribute("user");

		if(userPath.equals("/createUser")) {
			String name = request.getParameter("userName");
			String password = request.getParameter("password");
			String rePassword = request.getParameter("rePassword");
			String type = request.getParameter("graderType");

			if(password.equals(rePassword)){
				user = User.register(name, Integer.parseInt(type), password);

				if(user == null) {
					session.setAttribute("error", Constants.TAKEN_USERNAME);
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
				session.setAttribute("error", Constants.PASSWORDS_DONT_MATCH);
				response.sendRedirect("/Cornea_Grader/register"); 
				return;
			}
		}
		else if(userPath.equals("/login")) {
			String name = request.getParameter("userName");
			String password = request.getParameter("password");
			user = User.login(name, password);

			if(user == null) {
				session.setAttribute("error", Constants.INCORRECT_NAME_PASS);
				response.sendRedirect("/Cornea_Grader/"); 
				return;
			}

			session.setAttribute("user",user);
			response.sendRedirect("/Cornea_Grader/home"); 
			return;
		}
		else if(userPath.equals("/logout")) {
			session.removeAttribute("user");
			response.sendRedirect("/Cornea_Grader/"); 
			return;
		}
		else if(userPath.equals("/upload_picture_data")){
			session.setAttribute("errors", Picture.upload(request, Picture.EXCEL_UPLOAD));
			response.sendRedirect("/Cornea_Grader/"); 
			return;
		}
		else if(userPath.equals("/insert_pictures")) {
			session.setAttribute("errors", Picture.upload(request, Picture.PICTURE_UPLOAD));
			response.sendRedirect("/Cornea_Grader/"); 
			return;
		}
		else if(userPath.equals("/update_right_left")) {
			response.sendRedirect("/Cornea_Grader/assign_right_left?fileName="+
						Picture.assign_right_left(request));
			return;
		}
		else if(userPath.equals("/submit_grade")) {
			Grade.submitGrade(request, user);
			response.sendRedirect("/Cornea_Grader/grade"); 
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
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
