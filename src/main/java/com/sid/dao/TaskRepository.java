package com.sid.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sid.entity.Task;

public interface TaskRepository  extends JpaRepository<Task,Long>{

}
