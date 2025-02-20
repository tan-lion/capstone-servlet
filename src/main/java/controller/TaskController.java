package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Job;
import entity.Role;
import entity.Status;
import entity.Task;
import entity.User;
import serivce.JobService;
import serivce.StatusService;
import serivce.TaskService;
import serivce.UserService;

@WebServlet(name = "taskController", urlPatterns = { "/tasks", "/add-task", "/edit-task", "/update-task",
		"/delete-task", "/detail-tasks" })
public class TaskController extends HttpServlet {
	private TaskService taskService = new TaskService();
	private UserService userService=new UserService();
	private JobService jobService=new JobService();
	private StatusService statusService=new StatusService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();

		switch (path) {
		case "/tasks":
			getTasks(req, resp);
			break;
		case "/add-task":
			addTask(req, resp);
			break;
		case "/edit-task":
			editTask(req, resp);
			break;
		case "/delete-task":
			deletetask(req, resp);
			break;
		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		switch (path) {
		case "/add-task":
			String name = req.getParameter("name");
			Date startDate =Date.valueOf(req.getParameter("start-date"));
			Date endDate = Date.valueOf(req.getParameter("end-date"));
			int userId=Integer.parseInt(req.getParameter("userId"));
			int jobId=Integer.parseInt(req.getParameter("jobId"));
			int statusId=Integer.parseInt(req.getParameter("statusId"));
			taskService.insertTask(name, startDate, endDate, userId, jobId, statusId);
			
			resp.sendRedirect("tasks");
			break;
		case "/update-task":
			int id = Integer.parseInt(req.getParameter("id"));
			String nameUpdate = req.getParameter("name");
			Date startDateUpdate =Date.valueOf(req.getParameter("start-date"));
			Date endDateUpdate = Date.valueOf(req.getParameter("end-date"));
			int userIdUpdate=Integer.parseInt(req.getParameter("userId"));
			int jobIdUpdate=Integer.parseInt(req.getParameter("jobId"));
			int statusIdUpdate=Integer.parseInt(req.getParameter("statusId"));

			taskService.updateTask(nameUpdate, startDateUpdate, endDateUpdate, userIdUpdate, jobIdUpdate, statusIdUpdate, id);
			
			resp.sendRedirect("tasks");
			break;
		default:
			break;
		}
	}

	private void getTasks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Task> listTask = new ArrayList<Task>();
		listTask = taskService.getTask();

		req.setAttribute("tasks", listTask);
		req.getRequestDispatcher("tasks.jsp").forward(req, resp);
	}
	
	private void addTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<User> listUser=new ArrayList<User>();
		listUser=userService.findAll();
		
		List<Job> listJob=new ArrayList<Job>();
		listJob=jobService.getJobs();
		
		List<Status> listStatus=new ArrayList<Status>();
		listStatus=statusService.getStatus();
		
		
		req.setAttribute("users", listUser);
		req.setAttribute("jobs", listJob);
		req.setAttribute("status", listStatus);
		
		req.getRequestDispatcher("add-task.jsp").forward(req, resp);
	}
	
	public void editTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		Task existingTask = taskService.getTaskById(id);
		
		List<User> listUser=new ArrayList<User>();
		listUser=userService.findAll();
		
		List<Job> listJob=new ArrayList<Job>();
		listJob=jobService.getJobs();
		
		List<Status> listStatus=new ArrayList<Status>();
		listStatus=statusService.getStatus();
		
		
		req.setAttribute("users", listUser);
		req.setAttribute("jobs", listJob);
		req.setAttribute("status", listStatus);
		req.setAttribute("task", existingTask);
		req.getRequestDispatcher("edit-task.jsp").forward(req, resp);
	}
	
	public void deletetask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id=Integer.parseInt(req.getParameter("id"));
		taskService.deleteTask(id);
		
		resp.sendRedirect("tasks");
	}
}
