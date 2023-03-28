package com.security.Repositories;


import com.security.Models.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<PostModel, Long> {



}
