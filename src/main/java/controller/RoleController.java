package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Role;
import entity.User;
import serivce.RoleService;
import serivce.UserService;

@WebServlet(name = "roleController", urlPatterns = { "/roles", "/add-role", "/update-role", "/delete-role",
		"/edit-role" })
public class RoleController extends HttpServlet {
	private UserService userService = new UserService();
	private RoleService roleService = new RoleService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		switch (path) {
		case "/roles":
			getRoles(req, resp);
			break;
		case "/add-role":
			req.getRequestDispatcher("add-role.jsp").forward(req, resp);
			break;
		case "/edit-role":
			updateUser(req, resp);
			break;
		case "/delete-role":
			deleteRole(req, resp);
			break;
		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		switch (path) {
		case "/add-role":
			String name = req.getParameter("name");
			String description = req.getParameter("description");
			roleService.insertRole(name, description);

			resp.sendRedirect("roles");
			break;
		case "/update-role":
			int id = Integer.parseInt(req.getParameter("id"));
			String nameUpdate = req.getParameter("name");
			String descriptionUpdate = req.getParameter("description");

			roleService.updateRole(nameUpdate, descriptionUpdate, id);

			resp.sendRedirect("roles");
			break;
		default:
			break;
		}
	}

	private void getRoles(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Role> roles = new ArrayList<Role>();
		roles = userService.getRole();

		req.setAttribute("roles", roles);
		req.getRequestDispatcher("roles.jsp").forward(req, resp);
	}
	
	private void deleteRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		roleService.deleteRole(id);

		resp.sendRedirect("roles");
	}
	
	private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		Role existingRole = roleService.getRoleById(id);

		req.setAttribute("role", existingRole);

		req.getRequestDispatcher("edit-role.jsp").forward(req, resp);
	}

}
