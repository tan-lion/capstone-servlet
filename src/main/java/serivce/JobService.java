package serivce;

import java.sql.Date;
import java.util.List;

import entity.Job;
import repository.JobRepository;

public class JobService {
	private JobRepository jobRepository = new JobRepository();

	public List<Job> getJobs() {
		List<Job> jobs = jobRepository.getJobs();
		return jobs;
	}

	public boolean insertJob(String name, Date startDate, Date endDate) {
		return jobRepository.insertJob(name, startDate, endDate) > 0;
	}

	public boolean updateJob(String name, Date startDate, Date endDate, int id) {
		return jobRepository.updateJob(name, startDate, endDate, id) > 0;
	}
	
	public void deleteJob(int id) {
		jobRepository.deleteJob(id);
	}
	
	public Job getJobById(int id) {
		Job job=new Job();
		job=jobRepository.getJobById(id);
		
		return job;
	}
}
