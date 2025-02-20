package serivce;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import entity.Task;
import repository.TaskRepository;

public class TaskService {
	private TaskRepository taskRepository = new TaskRepository();

	public List<Task> getTask() {
		List<Task> task=new ArrayList<Task>();
		task=taskRepository.getTasks();
		return task;
	}

	public boolean insertTask(String name, Date startDate, Date endDate, int userId, int jobId, int statusId) {
		return taskRepository.insertTask(name, startDate, endDate, userId, jobId, statusId) > 0;
	}

	public boolean updateTask(String name, Date startDate, Date endDate, int userId, int jobId, int statusId, int id) {
		return taskRepository.updateTask(name, startDate, endDate, userId, jobId, statusId, id) > 0;
	}
	
	public void deleteTask(int id) {
		taskRepository.deleteTask(id);
	}
	
	public Task getTaskById(int id) {
		return taskRepository.getTaskById(id);
	}
}
