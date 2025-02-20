package repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.MysqlConfig;
import entity.Job;
import entity.Role;
import entity.Status;
import entity.Task;
import entity.User;

public class TaskRepository {
	
	public List<Task> getTasks() {
		List<Task> listTask = new ArrayList<Task>();

		String query = "SELECT t.id, t.name, t.start_date, t.end_date, u.fullname AS userName, j.name AS jobName, s.name AS statusName FROM tasks t "
				+ "JOIN users u ON t.user_id = u.id "
				+ "JOIN jobs j ON t.job_id=j.id "
				+ "JOIN status s ON t.status_id=s.id ";

		
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Task task = new Task();
				task.setId(result.getInt("id"));
				task.setName(result.getString("name"));
				task.setStartDate(result.getDate("start_date"));
				task.setEndDate(result.getDate("end_date"));
				
				User user =new User();
				user.setFullName(result.getString("userName"));
				task.setUser(user);
				
				Job job =new Job();
				job.setName(result.getString("jobName"));
				task.setJob(job);
				
				Status status= new Status();
				status.setName(result.getString("statusName"));
				task.setStatus(status);
				
				listTask.add(task);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listTask;
	}
	
	public int insertTask(String name, Date startDate, Date endDate, int userId, int jobId, int statusId) {
		String query = "INSERT INTO tasks (name, start_date, end_date, user_id, job_id, status_id) VALUES (?, ?, ?, ?, ?, ?)";
		int count = 0;

		try {
			Connection connection = MysqlConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);

			statement.setString(1, name);
			statement.setDate(2, new java.sql.Date(startDate.getTime()));
			statement.setDate(3, new java.sql.Date(endDate.getTime()));
			statement.setInt(4, userId);
			statement.setInt(5, jobId);
			statement.setInt(6, statusId);

			count = statement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return count;
	}

	public int updateTask(String name, Date startDate, Date endDate, int userId, int jobId, int statusId, int id) {
		String query = "UPDATE tasks SET name = ?, start_date = ?, end_date = ?, user_id = ?, job_id=?, status_id=?  WHERE id=?";
		int count = 0;
		try {
			Connection connection = MysqlConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);

			statement.setString(1, name);
			statement.setDate(2, new java.sql.Date(startDate.getTime()));
			statement.setDate(3, new java.sql.Date(endDate.getTime()));
			statement.setInt(4, userId);
			statement.setInt(5, jobId);
			statement.setInt(6, statusId);
			statement.setInt(7, id);

			count = statement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return count;
	}

	public void deleteTask(int id) {
		String query = "DELETE FROM tasks WHERE id=?";
		try (Connection connection = MysqlConfig.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, id);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Task getTaskById(int id) {
		String query = "SELECT t.id, t.name, t.start_date, t.end_date, u.id, j.id, s.id FROM tasks t "
				+ "JOIN users u ON t.user_id = u.id "
				+ "JOIN jobs j ON t.job_id=j.id "
				+ "JOIN status s ON t.status_id=s.id "
				+ "WHERE t.id=?";
		Task task = null;

		try {
			Connection connection = MysqlConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);

			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				task = new Task();
				task.setId(result.getInt("id"));
				task.setName(result.getString("name"));
				task.setStartDate(result.getDate("start_date"));
				task.setEndDate(result.getDate("end_date"));
				
				User user=new User();
				user.setId(result.getInt("id"));
				user.setFullName(result.getString("fullname"));
				task.setUser(user);
				
				Job job=new Job();
				job.setId(result.getInt("id"));
				job.setName(result.getString("name"));
				task.setJob(job);
				
				Status status=new Status();
				status.setId(result.getInt("id"));
				status.setName(result.getString("name"));
				task.setStatus(status);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return task;
	}
}
