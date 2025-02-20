package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.MysqlConfig;
import entity.Role;
import entity.User;

public class UserRepository {
	
	public List<User> findAll(){
		List<User> list = new ArrayList<User>();
		String query = "SELECT u.id, u.fullname FROM users u";
		try {
			Connection connection = MysqlConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				User entity = new User();
				entity.setId(resultSet.getInt("id"));
				entity.setFullName(resultSet.getString("fullname"));
				
				list.add(entity);
			}
		}catch (Exception e) {
			System.out.println("findAll: " + e.getLocalizedMessage());
		}
		
		return list;
	}
	
	public int insertUser(String fullname, String email, String password, int roleId) {
		String query="INSERT INTO users (fullname, email, password, role_id) VALUES (?, ?, ?, ?)";
		int count=0;
		
		try {
			Connection connection=MysqlConfig.getConnection();
			PreparedStatement statement=connection.prepareStatement(query);
			
			statement.setString(1, fullname);
			statement.setString(2, email);
			statement.setString(3, password);
			statement.setInt(4, roleId);
			
			count=statement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return count;
	}
	
	public int updateUser(String fullname, String email, String password, int roleId, int id) {
		String query = "UPDATE users SET fullname = ?, email = ?, password = ?, role_id = ?  WHERE id=?";
		int count=0;
		try {
			Connection connection=MysqlConfig.getConnection();
			PreparedStatement statement=connection.prepareStatement(query);
			
			statement.setString(1, fullname);
			statement.setString(2, email);
			statement.setString(3, password);
			statement.setInt(4, roleId);
			statement.setInt(5, id);
			
			count=statement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return count;
	}
	
	public void deleteUser(int id) {
		String query="DELETE FROM users WHERE id=?";
		 try (Connection connection = MysqlConfig.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {

	            statement.setInt(1, id);
	            statement.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}
	public User getUserById(int id) {
		String query="SELECT u.id, u.fullname, u.email, u.password, r.id FROM users u JOIN roles r ON u.role_id = r.id WHERE u.id=?";
		User user=null;
		
		try {
			Connection connection=MysqlConfig.getConnection();
			PreparedStatement statement=connection.prepareStatement(query);
			
			statement.setInt(1, id);
			ResultSet result=statement.executeQuery();
			
			if(result.next()) {
				user=new User();
				user.setId(result.getInt("id"));
				user.setFullName(result.getString("fullname"));
				user.setUsername(result.getString("email"));
				user.setPassword(result.getString("password"));
				
				Role role=new Role();
				role.setId(result.getInt("id"));
				role.setName(result.getString("name"));
				
				user.setRole(role);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return user;
	}
}
