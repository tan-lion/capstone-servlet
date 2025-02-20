package serivce;

import entity.Role;
import repository.RoleRepository;

public class RoleService {
	private RoleRepository repository=new RoleRepository();
	
	public boolean insertRole(String name, String description) {
		return repository.insertRole(name, description) >0;
	}

	public boolean updateRole(String name, String description, int id) {
		return repository.updateRole(name, description, id) >0;
	}
	
	public void deleteRole(int id) {
		repository.deleteRole(id);
	}
	
	public Role getRoleById(int id) {
		return repository.getRoleById(id);
	}
}
