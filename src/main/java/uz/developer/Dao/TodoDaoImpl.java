package uz.developer.Dao;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import uz.developer.models.Todo;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Service
public class TodoDaoImpl implements TodoDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TodoDaoImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Todo todo) {
        String sql = "insert into todos(title, priority, createdAt) values(:title, :priority, :createdAt);";
        var source = new BeanPropertySqlParameterSource(todo);
        namedParameterJdbcTemplate.update(sql, source);
    }

    @Override
    public void update(Todo todo) {
        var sql = "update todos set title = :title, priority = :priority where id = :id;";
        BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(todo);
        namedParameterJdbcTemplate.update(sql, source);
    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from todos where id = :id;";
        namedParameterJdbcTemplate.update(sql, Map.of("id", id));
    }

    @Override
    public Todo findById(Integer id) {
        String sql = "select * from todos where id = :id;";
        return namedParameterJdbcTemplate.queryForObject(sql, Map.of("id", id), new TodoRowMapper());
    }

    @Override
    public List<Todo> findAll() {
        String sql = "select * from todos";
        return namedParameterJdbcTemplate.query(sql, new TodoRowMapper());
    }
}
