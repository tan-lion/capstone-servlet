package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.MysqlConfig;
import entity.Job;
import entity.Role;

public class RoleRepository {
	public List<Role> findAll(){
		List<Role> list = new ArrayList<Role>();
		String query = "SELECT * FROM roles r";
		try {
			Connection connection = MysqlConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Role entity = new Role();
				entity.setId(resultSet.getInt("id"));
				entity.setName(resultSet.getString("name"));
				entity.setDescription(resultSet.getString("description"));
				
				list.add(entity);
			}
		}catch (Exception e) {
			System.out.println("findAll: " + e.getLocalizedMessage());
		}
		
		return list;
	}
	
	public int insertRole(String name, String description) {
		String query="INSERT INTO roles (name, description) VALUES (?, ?)";
		int count=0;
		
		try {
			Connection connection=MysqlConfig.getConnection();
			PreparedStatement statement=connection.prepareStatement(query);
			
			statement.setString(1, name);
			statement.setString(2, description);
			
			count=statement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return count;
	}
	
	public int updateRole(String name, String description, int id) {
		String query = "UPDATE roles SET name = ?, description= ?  WHERE id=?";
		int count=0;
		try {
			Connection connection=MysqlConfig.getConnection();
			PreparedStatement statement=connection.prepareStatement(query);
			
			statement.setString(1, name);
			statement.setString(2, description);
			statement.setInt(3, id);
			
			count=statement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return count;
	}
	
	public void deleteRole(int id) {
		String query="DELETE FROM roles WHERE id=?";
		 try (Connection connection = MysqlConfig.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {

	            statement.setInt(1, id);
	            statement.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}
	
	public Role getRoleById(int id) {
		String query= "SELECT * FROM roles r WHERE r.id=?";
		Role role=null;
		
		try {
			Connection connection = MysqlConfig.getConnection();
			PreparedStatement statement=connection.prepareStatement(query);
			
			statement.setInt(1, id);
			ResultSet result=statement.executeQuery();
			
			if(result.next()) {
				role=new Role();
				role.setId(result.getInt("id"));
				role.setName(result.getString("name"));
				role.setDescription(result.getString("description"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return role;
	}
}
