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
import entity.User;

public class JobRepository {

	public List<Job> getJobs() {
		List<Job> listJob = new ArrayList<Job>();

		String query = "SELECT j.id, j.name, j.start_date, j.end_date FROM jobs j ";

		Connection connection = MysqlConfig.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Job job = new Job();
				job.setId(result.getInt("id"));
				job.setName(result.getString("name"));
				job.setStartDate(result.getDate("start_date"));
				job.setEndDate(result.getDate("end_date"));

				listJob.add(job);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listJob;
	}

	public int insertJob(String name, Date startDate, Date endDate) {
		String query = "INSERT INTO jobs (name, start_Date, end_Date) VALUES (?, ?, ?)";
		int count = 0;

		try {
			Connection connection = MysqlConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);

			statement.setString(1, name);
			statement.setDate(2, new java.sql.Date(startDate.getTime()));
			statement.setDate(3, new java.sql.Date(endDate.getTime()));

			count = statement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return count;
	}

	public int updateJob(String name, Date startDate, Date endDate, int id) {
		String query = "UPDATE jobs SET name = ?, start_Date = ?, end_Date = ?  WHERE id=?";
		int count = 0;
		try {
			Connection connection = MysqlConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);

			statement.setString(1, name);
			statement.setDate(2, startDate);
			statement.setDate(3, endDate);
			statement.setInt(4, id);

			count = statement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return count;
	}

	public void deleteJob(int id) {
		String query = "DELETE FROM jobs WHERE id=?";
		try (Connection connection = MysqlConfig.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, id);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Job getJobById(int id) {
		String query = "SELECT * FROM jobs j WHERE j.id=?";
		Job job = null;

		try {
			Connection connection = MysqlConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);

			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				job = new Job();
				job.setId(result.getInt("id"));
				job.setName(result.getString("name"));
				job.setStartDate(result.getDate("start_date"));
				job.setEndDate(result.getDate("end_date"));

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return job;
	}
}
