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
import repository.JobRepository;
import serivce.JobService;

@WebServlet(name = "jobController", urlPatterns = { "/jobs", "/add-job", "/edit-job", "/update-job", "/delete-job", "/detail-jobs" })
public class JobController extends HttpServlet {
	private JobService jobService=new JobService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();

		switch (path) {
		case "/jobs":
			getJobs(req, resp);
			break;
		case "/add-job":
			req.getRequestDispatcher("add-job.jsp").forward(req, resp);
			break;
		case "/edit-job":
			editJob(req, resp);
			break;
		case "/delete-job":
			deleteJob(req, resp);
			break;
		case "/detail-jobs":
			req.getRequestDispatcher("detail-job.jsp").forward(req, resp);

		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();

		switch (path) {
		case "/add-job":
			String name = req.getParameter("name");
			Date startDate =Date.valueOf(req.getParameter("start-date"));
			Date endDate = Date.valueOf(req.getParameter("end-date"));
			jobService.insertJob(name, startDate, endDate);

			resp.sendRedirect("jobs");
			break;
		case "/update-job":
			int id = Integer.parseInt(req.getParameter("id"));
			String nameUpdate = req.getParameter("name");
			Date startDateUpdate =Date.valueOf(req.getParameter("start-date"));
			Date endDateUpdate = Date.valueOf(req.getParameter("end-date"));

			jobService.updateJob(nameUpdate, startDateUpdate, endDateUpdate, id);

			resp.sendRedirect("jobs");
			break;

		default:
			break;
		}
	}
	
	private void getJobs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Job> jobs=new ArrayList<Job>();
		jobs= jobService.getJobs();
		
		req.setAttribute("jobs", jobs);
		req.getRequestDispatcher("jobs.jsp").forward(req, resp);
	}
	
	public void editJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		Job existingJob = jobService.getJobById(id);
		
		req.setAttribute("job", existingJob);
		req.getRequestDispatcher("edit-job.jsp").forward(req, resp);
	}
	
	public void deleteJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id=Integer.parseInt(req.getParameter("id"));
		jobService.deleteJob(id);
		
		resp.sendRedirect("jobs");
	}
}
