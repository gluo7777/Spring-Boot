package basicjdbc;

import java.time.LocalDateTime;
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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

@SpringBootApplication
public class App implements ApplicationRunner{
	
	private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	@Autowired
    private NamedParameterJdbcTemplate template;

	@Override
	public void run(ApplicationArguments args) throws Exception {
//		testEmbeddedH2Db();
		log.info("Adding Tasks");
		String timestamp = LocalDateTime.now().toString();
		template.batchUpdate("INSERT INTO timer.tasks (task_name,account_id,task_type) VALUES (:task_name,:account_id,CAST(:task_type AS task_types))", 
			new SqlParameterSource[]{
				newTask("Finish this poc " + timestamp,1)
				,newTask("Hurry up " + timestamp,1)
				,newTask("sleep " + timestamp,2)
		});
		List<Task> tasks = template.query("SELECT * FROM timer.tasks;", (rs, rowNum) -> {
			Task task = new Task();
			task.setId(rs.getLong("id"));
			task.setTaskName(rs.getString("task_name"));
			task.setAccountId(rs.getLong("account_id"));
			return task;
		});
		tasks.forEach(task -> log.info("Retrieved task: " + task.toString()));
	}

	@SuppressWarnings("unchecked")
	public void testEmbeddedH2Db() {
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
	
	private SqlParameterSource newTask(String taskName, long accountId) {
		MapSqlParameterSource source = new MapSqlParameterSource();
		source.addValue("task_name", taskName);
		source.addValue("account_id", accountId);
		source.addValue("task_type", "app");
		return source;
	}
}
