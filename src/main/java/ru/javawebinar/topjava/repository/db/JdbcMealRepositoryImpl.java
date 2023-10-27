package ru.javawebinar.topjava.repository.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Profile("jdbc")
@Qualifier("jdbc")
public class JdbcMealRepositoryImpl implements MealRepository {

	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final SimpleJdbcInsert insertMeal;

	private static final RowMapper<Meal> rowMapper = BeanPropertyRowMapper.newInstance(Meal.class);

	@Autowired
	public JdbcMealRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.insertMeal = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("meals")
				.usingGeneratedKeyColumns("id");
	}

	@Override
	public Meal save(Meal meal, int userId) {
		MapSqlParameterSource map = new MapSqlParameterSource()
				.addValue("id", meal.getId())
				.addValue("date_time", meal.getDateTime())
				.addValue("description", meal.getDescription())
				.addValue("calories", meal.getCalories())
				.addValue("user_id", userId);

		if(meal.isNew()){
			int id = insertMeal.executeAndReturnKey(map).intValue();
			meal.setId(id);
		}
		else if(namedParameterJdbcTemplate.update("UPDATE meals " +
				"SET date_time=:date_time, description=:description, calories=:calories " +
				"WHERE id=:id AND user_id=:user_id",map) == 0)
			return null;

		return meal;
	}

	@Override
	public Meal get(int id, int userId) {
		List<Meal> meal = jdbcTemplate.query("SELECT * FROM meals WHERE id=? AND user_id=?", rowMapper, id, userId);
		return DataAccessUtils.singleResult(meal);
	}

	@Override
	public boolean delete(int id, int userId) {
		return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", id, userId) != 0;
	}

	@Override
	public List<Meal> getAll(int userId) {
		return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=?", rowMapper, userId);
	}

	@Override
	public List<Meal> getInInterval(int userId, LocalDateTime startTime, LocalDateTime endTime) {
		return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? AND (date_time>=? AND date_time<?)",
				rowMapper, userId, startTime, endTime);
	}
}
