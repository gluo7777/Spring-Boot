package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.Jedis;

/**
 * Guide: https://spring.io/projects/spring-data-redis
 *
 */
@SpringBootApplication
public class MainConfig {
	
	private static final Logger LOG = LoggerFactory.getLogger(MainConfig.class);

	public static void main(String[] args) {
		SpringApplication.run(MainConfig.class, args);
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("127.0.0.1", 6379);
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(config);
		return jedisConnectionFactory;
	}
	
	@Bean
	public RedisTemplate<String, String> template(JedisConnectionFactory connection){
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connection);
		return redisTemplate;
	}
	
	@Bean
	public ApplicationRunner run(RedisTemplate<String, String> template, RedisObjRepository repo) {
		return args -> {
			/**
			 * Note that Spring data redis saves your keys in this format:
			 * "\xac\xed\x00\x05t\x00\x05names"
			 */
			Jedis nativeConnection = (Jedis) template.getConnectionFactory().getConnection().getNativeConnection();
			LOG.info("Connection: {}", nativeConnection.info());
			template.opsForValue().set("greeting", "Hello from Spring Data Redis!");
			LOG.info("Greeting: {}",template.opsForValue().get("greeting"));
			ListOperations<String, String> listOperations = template.opsForList();
			listOperations.leftPushAll("names", "William","Ma","David","Luo");
			listOperations.rightPop("names");
			LOG.info("Names: {}", listOperations.range("names", 0, -1));
			/**
			 * Hash stored as RedisObj:asdada
			 */
			repo.save(new RedisObj("asdada","William", "Luo", 23));
			LOG.info("Saved RedisObj: {}", repo.findById("asdada").get());
		};
	}
}
