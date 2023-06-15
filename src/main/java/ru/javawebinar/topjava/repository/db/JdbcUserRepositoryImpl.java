package ru.javawebinar.topjava.repository.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

	private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final SimpleJdbcInsert insertUser;

//	private JdbcTemplate jdbcTemplate
//	private NamedParameterJdbcTemplate namedParameterJdbcTemplate
//	public JdbcUserRepositoryImpl() {
//		this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
//				.withTableName("users")
//				.usingGeneratedKeyColumns("id");
//	}

	@Autowired
	public JdbcUserRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("users")
				.usingGeneratedKeyColumns("id");
	}

	@Override
	public User save(User user) {
		MapSqlParameterSource parameterMap = new MapSqlParameterSource()
				.addValue("id", user.getId())
				.addValue("name", user.getName())
				.addValue("email", user.getEmail())
				.addValue("password", user.getPassword())
				.addValue("registered", user.getRegistered())
				.addValue("enabled", user.isEnabled());

		//TODO: check how it works
		//if (generating keys is not defined in db)
//		KeyHolder keyHolder = new GeneratedKeyHolder();
//		namedParameterJdbcTemplate.update("INSERT INTO users values (:name, :email, :password, "
//				+ ":registered, :enabled, :caloriesPerDay)", parameterMap, keyHolder);
//		int id = keyHolder.getKey().intValue();
//		user.setId(id);

		if(user.isNew()){
			Number newKey = insertUser.executeAndReturnKey(parameterMap);
			user.setId(newKey.intValue());
		}
		else if(namedParameterJdbcTemplate.update("UPDATE users SET name=:name, email=:email, password=:password, " +
			"registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay " +
				"WHERE id=:id", parameterMap) == 0)
					return null;

		return user;
	}

	@Override
	public boolean delete(int id) {
		return jdbcTemplate.update("DELETE FROM users WHERE id=?",id) != 0;
	}

	@Override
	public User get(int id) {
		List<User> queryResult = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
		return DataAccessUtils.singleResult(queryResult);
	}

	@Override
	public User getByEmail(String email) {
		List<User> queryResult = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
		return DataAccessUtils.singleResult(queryResult);
	}

	@Override
	public List<User> getAll() {
		return jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
	}
}
