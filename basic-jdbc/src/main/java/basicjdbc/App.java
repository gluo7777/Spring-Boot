package basicjdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@SpringBootApplication
public class App implements ApplicationRunner{
	
	private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	@Autowired
    private NamedParameterJdbcTemplate template;

	@SuppressWarnings("unchecked")
	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("Create tables");
		template.getJdbcOperations().execute("CREATE SCHEMA IF NOT EXISTS timer;");
		template.getJdbcOperations().execute("CREATE TABLE IF NOT EXISTS timer.tasks("
				+ "task_id BIGSERIAL PRIMARY KEY,"
				+ "task_name TEXT NOT NULL"
				+ ");");
		log.info("Adding Tasks");
		template.batchUpdate("INSERT INTO timer.tasks (task_name) VALUES (:taskName)", new Map[]{
			taskAsMap("Finish dinner."),
			taskAsMap("Finish JDBC project.")
		});
		List<Task> tasks = template.query("SELECT * FROM timer.tasks;", (rs, rowNum) -> {
			Task task = new Task();
			task.setTaskId(rs.getLong("task_id"));
			task.setTaskName(rs.getString("task_name"));
			return task;
		});
		tasks.forEach(task -> log.info("Retrieved task: " + task.toString()));
	}
	
	private Map<String, Object> taskAsMap(String name){
		Map<String, Object> map = new HashMap<>();
		map.put("taskName" , name);
		return map;
	}
}
