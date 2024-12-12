package com.pixel.PixelSpace.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pixel.PixelSpace.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
