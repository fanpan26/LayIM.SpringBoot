package com.fyp.layim.repository;

import com.fyp.layim.domain.Apply;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplyRepositoryTest {

    @Autowired
    private ApplyRepository applyRepository;

    @Test
    public void countByToidAndTypeAndResult() throws Exception {
    }

    @Test
    public void countByToidAndIsread() throws Exception {
    }

    @Test
    public void findAppliesByToidOrderByCreateAtDesc() throws Exception {
        Pageable pageable = new PageRequest(1,20);
        Page<Apply> pages = applyRepository.findAppliesByToidOrderByCreateAtDesc(276608,pageable);
    }

}