package basicjdbc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TimerTaskRepository {
	private static final Logger LOG = LoggerFactory.getLogger(TimerTaskRepository.class);
	@Autowired
    private NamedParameterJdbcTemplate template;
	
//	public List<Task> getTasksForAccount(Account account){
//		// query db using email address
//	}
}
