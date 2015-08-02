package com.yunshan.testSpring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yunshan.testSpring.dao.PollDao;
import com.yunshan.testSpring.po.Poll;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:applicationContext.xml"})  
public class App extends AbstractTransactionalJUnit4SpringContextTests { 
	
    @Autowired 
    private PollDao pollDao;
    
    @Test  
    @Rollback(false) 
    public void testInsert() {  
        Poll poll = new Poll("zy_1", new Date());  
        pollDao.insert(poll); 
    }  
    
    @Test  
    @Rollback(false) 
    public void testFindByQuestion() {  
        int id = pollDao.findByQuestion("zy_1");  
        System.out.println(id);  
    } 
    
    @Test
    @Rollback(false) 
    public void testUpdate() {  
        Poll poll = new Poll(1,"zy_0", new Date());  
        pollDao.update(poll);  
    } 
    
    @Test  
    @Rollback(false) 
    public void testFindById() {  
    	List<Poll> poll = pollDao.findById(2);
        System.out.println(poll);  
    }
    
    @Test 
    @Rollback(false) 
    public void testDeletePoll() {  
    	Poll poll = new Poll();  
        poll.setId(1);  
        pollDao.delete(poll);  
    } 
    
    @Test  
    //@Rollback(false) 
    public void testBatchInsert() {  
        List<Poll> polls = new ArrayList<Poll>();  
        polls.add(new Poll("zy_2", new Date()));  
        polls.add(new Poll("zy_3", new Date()));  
        polls.add(new Poll("zy_4", new Date()));  
        polls.add(new Poll("zy_5", new Date()));  
        pollDao.batchInsert(polls);  
    }
    
    @Test  
    @Rollback(false) 
    public void testDeleteInt() {  
    	pollDao.delete(2);  
    }
    
    @Test  
    @Rollback(false) 
    public void testFindAll() {  
        List<Poll> list = pollDao.findAll();  
        System.out.println(list);  
    } 
    
    @Test  
    @Rollback(false) 
    public void testCount() {  
        int count = pollDao.count();  
        System.out.println(count);  
    }

}
