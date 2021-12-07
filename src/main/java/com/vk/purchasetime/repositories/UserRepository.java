package com.vk.purchasetime.repositories;

import com.vk.purchasetime.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    public User findUserByUsernameAndPassword(String userName,String password);
    public User findUserByResetPassToken(String resetPassToken);
    public User findUserByEmail(String email);

}
