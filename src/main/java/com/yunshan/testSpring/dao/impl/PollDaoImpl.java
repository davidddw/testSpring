package com.yunshan.testSpring.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.yunshan.testSpring.dao.PollDao;
import com.yunshan.testSpring.po.Poll;

public class PollDaoImpl extends JdbcDaoSupport implements PollDao {

	private final String GET_POLL_FROM_ID = "SELECT * FROM poll WHERE id=?";
	private final String GET_POLL_FROM_QUESTION = "SELECT id FROM poll WHERE question=?";
	private final String ADD_POLL = "INSERT INTO poll (id, question, pub_date) VALUES (?, ?, ?)";
	private final String UPDATE_POLL = "UPDATE poll SET question=?, pub_date=? WHERE id=?";
	private final String DELETE_POLL_FROM_ID = "DELETE FROM poll WHERE id=?";
	private final String COUNT = "SELECT COUNT(*) FROM poll";
	private final String SELECT_ALL = "SELECT * FROM poll";

	public void insert(final Poll poll) {
		getJdbcTemplate().update(ADD_POLL, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, poll.getId());
				ps.setString(2, poll.getQuestion());
				ps.setTimestamp(3, new java.sql.Timestamp(poll.getPub_date()
						.getTime()));
			}
		});
	}

	public void update(final Poll poll) {
		getJdbcTemplate().update(UPDATE_POLL, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, poll.getQuestion());
				ps.setTimestamp(2, new java.sql.Timestamp(poll.getPub_date()
						.getTime()));
				ps.setInt(3, poll.getId());
			}
		});
	}

	public void delete(Poll poll) {
		getJdbcTemplate().update(DELETE_POLL_FROM_ID, poll.getId());
	}

	public void delete(int id) {
		getJdbcTemplate().update(DELETE_POLL_FROM_ID, id);

	}

	public List<Poll> findById(int id) {
		return getJdbcTemplate().query(GET_POLL_FROM_ID,new Object[]{ id},
				new BeanPropertyRowMapper<Poll>(Poll.class));
	}

	public int findByQuestion(String question) {
		try {
			return getJdbcTemplate().queryForObject(GET_POLL_FROM_QUESTION,
				new Object[] { question }, Integer.class);
		} catch (EmptyResultDataAccessException e) {
			return 0;
		}	
	}

	public List<Poll> findAll() {
		return getJdbcTemplate().query(SELECT_ALL,
				new BeanPropertyRowMapper<Poll>(Poll.class));
	}

	public int count() {
		return getJdbcTemplate().queryForObject(COUNT, new Object[] {},
				Integer.class);
	}

	public void batchInsert(final List<Poll> polls) {
		List<Object> parameters = new ArrayList<Object>();
		for (Poll poll : polls) {
			parameters.add(new Object[] { poll.getId() });
		}
		getJdbcTemplate().batchUpdate(ADD_POLL,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						Poll poll = polls.get(i);
						ps.setInt(1, poll.getId());
						ps.setString(2, poll.getQuestion());
						ps.setTimestamp(3, new java.sql.Timestamp(poll
								.getPub_date().getTime()));
					}

					public int getBatchSize() {
						return polls.size();
					}
				});
	}
}
