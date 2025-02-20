package controller;

import java.io.IOException;
import java.net.Authenticator.RequestorType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.MysqlConfig;
import entity.Role;
import entity.User;
import serivce.UserService;

@WebServlet(name = "userController", urlPatterns = { "/users", "/add-user", "/delete-user", "/edit-user",
		"/update-user", "/detail-user" })
public class UserController extends HttpServlet {
	private UserService userServices = new UserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getServletPath();

		switch (path) {
		case "/users":
			getUsers(req, resp);
			break;
		case "/edit-user":
			updateUser(req, resp);
			break;
		case "/add-user":
			userAdd(req, resp);
			break;
		case "/delete-user":
			deleteUser(req, resp);
			break;
		case "/detail-user":
			detailUser(req, resp);
			break;
		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		switch (path) {
		case "/add-user":
			String fullname = req.getParameter("fullname");
			String emmail = req.getParameter("email");
			String password = req.getParameter("password");
			int idRole = Integer.parseInt(req.getParameter("role"));

			userServices.insertUser(fullname, emmail, password, idRole);
			resp.sendRedirect("users");
			break;
		case "/update-user":
			int id = Integer.parseInt(req.getParameter("id"));
			String fullnameUpdate = req.getParameter("fullname");
			String usernameUpdate = req.getParameter("email");
			String passwordUpdate = req.getParameter("password");
			int roleId = Integer.parseInt(req.getParameter("role"));

			userServices.updateUser(fullnameUpdate, usernameUpdate, passwordUpdate, roleId, id);

			resp.sendRedirect("users");
			break;

		default:
			break;
		}
	}

	private void getUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<User> listUser = new ArrayList<User>();

		String query = "SELECT u.id, u.fullname, u.email, r.name " + "FROM users u " + "JOIN roles r "
				+ "ON u.role_id=r.id";

		Connection connection = MysqlConfig.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				User user = new User();
				user.setId(result.getInt("id"));
				user.setFullName(result.getString("fullname"));
				user.setUsername(result.getString("email"));

				Role role = new Role();
				role.setName(result.getString("name"));

				user.setRole(role);

				listUser.add(user);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		req.setAttribute("users", listUser);
		req.getRequestDispatcher("users.jsp").forward(req, resp);
	}

	private void userAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<Role> roles = userServices.getRole();

		req.setAttribute("roles", roles);
		req.getRequestDispatcher("add-user.jsp").forward(req, resp);
	}

	private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		User existingUser = userServices.getUserById(id);
		List<Role> roles = userServices.getRole();

		req.setAttribute("roles", roles);
		req.setAttribute("user", existingUser);

		req.getRequestDispatcher("edit-user.jsp").forward(req, resp);
	}

	private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		userServices.deleteUser(id);

		resp.sendRedirect("users");

	}

	private void detailUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		User userDetail = userServices.getUserById(id);

		req.setAttribute("userDetail", userDetail);
		req.getRequestDispatcher("detail-user.jsp").forward(req, resp);
	}

}
