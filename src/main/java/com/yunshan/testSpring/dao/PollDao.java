package com.yunshan.testSpring.dao;

import java.util.List;

import com.yunshan.testSpring.po.Poll;

public interface PollDao {
	public void insert(Poll poll);
	
	public void update(Poll poll);
	
	public void delete(Poll poll);
	
	public void delete(int id);
	
	public List<Poll> findById(int id);
	
	public int findByQuestion(String question);
	
	public List<Poll> findAll();
	
	public int count();
	
	public void batchInsert(List<Poll> polls);
	
}
