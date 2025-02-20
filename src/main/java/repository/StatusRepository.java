package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MysqlConfig;
import entity.Role;
import entity.Status;

public class StatusRepository {
	public List<Status> findAll(){
		List<Status> list = new ArrayList<Status>();
		String query = "SELECT * FROM status s";
		try {
			Connection connection = MysqlConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Status status = new Status();
				status.setId(resultSet.getInt("id"));
				status.setName(resultSet.getString("name"));
				
				list.add(status);
			}
		}catch (Exception e) {
			System.out.println("findAll: " + e.getLocalizedMessage());
		}
		
		return list;
	}
}
