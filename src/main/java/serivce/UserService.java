package serivce;

import java.util.List;

import entity.Role;
import entity.User;
import repository.RoleRepository;
import repository.UserRepository;

public class UserService {
	private RoleRepository roleRepository = new RoleRepository();
	private UserRepository userReposityory = new UserRepository();

	public List<Role> getRole() {
		List<Role> roles = roleRepository.findAll();

		return roles;
	}

	public boolean insertUser(String fullname, String email, String password, int role_id) {
		return userReposityory.insertUser(fullname, email, password, role_id) > 0;

	}

	public boolean updateUser(String fullname, String email, String password, int role_id, int id) {
		return userReposityory.updateUser(fullname, email, password, role_id, id) > 0;
	}

	public void deleteUser(int id) {
		userReposityory.deleteUser(id);
	}

	public User getUserById(int id) {
		return userReposityory.getUserById(id);
	}

	public List<User> findAll() {

		return userReposityory.findAll();
	}
}
