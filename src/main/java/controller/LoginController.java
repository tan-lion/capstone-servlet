package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.MysqlConfig;
import entity.Role;
import entity.User;

@WebServlet(name = "loginController", urlPatterns = "/login")
public class LoginController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = "";
		String password = "";

		req.setAttribute("email", email);
		req.setAttribute("password", password);
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String remember = req.getParameter("remember-me");
		//Tạo mảng rỗng 
		List<User> listUser = new ArrayList<User>();
		
		//Bước 1 : Chuẩn bị câu truy vấn tương ứng với chức năng đang làm
		String query = "SELECT r.name, u.email, u.password\n"
				+ "FROM users u\n"
				+ "JOIN roles r ON u.role_id = r.id\n"
				+ "WHERE u.email = ? AND u.password = ?";
		//Bước 2 : Mở kết nối CSDL
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			//Truyền giá trị cho các dấu ? ở truy vấn
			statement.setString(1, email);
			statement.setString(2, password);
			
			//excuteQuery : SELECT
			//excuteUpdate: Không phải câu SELECT
			ResultSet result = statement.executeQuery();
			
			
			//Duyệt từng dòng dữ liệu truy vấn được và gán vào listUser
			while(result.next()) {
				User entity = new User();
				entity.setUsername(result.getString("email"));
				
				Role role = new Role();
				role.setName(result.getString("name"));
				
				entity.setRole(role);
				
				listUser.add(entity);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(listUser.size() > 0) {	
			if(remember != null) {
				Cookie ckEmail = new Cookie("email", email);
				Cookie ckPassword = new Cookie("password", password);
				
				resp.addCookie(ckEmail);
				resp.addCookie(ckPassword);
			}
			
			Cookie ckRole = new Cookie("role", listUser.get(0).getRole().getName());
			resp.addCookie(ckRole);
			
			String contextPath = req.getContextPath();
			resp.sendRedirect(contextPath);
		}else {
			req.setAttribute("message", "Đăng nhập thất bại");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}	
	}


}
