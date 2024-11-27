package me.artemiyulyanov.taskmanager;

import me.artemiyulyanov.taskmanager.models.Status;
import me.artemiyulyanov.taskmanager.repositories.StatusRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StatusTest {
    @Autowired
    private StatusRepository statusRepository;

    @Test
    public void testFindById() {
        Status status = statusRepository.findById(1L).get();
        assertThat(status).isNotNull();
    }
}