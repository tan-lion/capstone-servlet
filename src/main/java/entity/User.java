package entity;

public class User {
	private int id;
	private String fullName;
	private String username;
	private String password;
	private Role role;

	public User(int id, String fullName, String username, String password, Role role) {
		super();
		this.id = id;
		this.fullName=fullName;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public User() {
	}

	
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fullName=" + fullName + ", username=" + username + ", password=" + password
				+ ", role=" + role + "]";
	}


}
