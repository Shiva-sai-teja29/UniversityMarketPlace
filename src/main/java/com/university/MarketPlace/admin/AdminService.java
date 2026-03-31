package com.university.MarketPlace.admin;

import com.university.MarketPlace.security.user.User;

import java.util.List;

public interface AdminService {
//    public User addUser(User user);
//
//    User updateUser(User user,Long id);
//
//    User deleteUser(Long id);

    User getUser(Long id);

    List<User> getAllUsers();

    String approveShop(ApprovalDTO approvalDTO);
}
