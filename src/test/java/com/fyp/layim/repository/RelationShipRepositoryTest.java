package com.fyp.layim.repository;

import com.fyp.layim.domain.RelationShip;
import com.fyp.layim.domain.User;
import org.aspectj.asm.internal.Relationship;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author fyp
 * @crate 2017/11/5 16:37
 * @project SpringBootLayIM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RelationShipRepositoryTest {

    @Autowired
    private RelationShipRepository relationShipRepository;

    @Test
    public void testGetRelationShip(){
        RelationShip relationship = relationShipRepository.findOne(1L);
        User user = relationship.getFriend();
        if(user==null){
            System.out.println("没有获取到用户");
        }else{
            System.out.println("获取到了用户");
        }
    }
}