package com.example.ecommerce.Service;

import com.example.ecommerce.DAO.AppUser;
import com.example.ecommerce.DAO.Role;
import com.example.ecommerce.DAO.TypeRole;

import java.util.List;

public interface IUserService  {

    AppUser saveUser(AppUser user);
    void deleteUser(Long cin);
    AppUser updateUser(AppUser user);
    AppUser updatePassword(String email, String oldPassword, String newPassword);
    Role saveRole(Role role);
    void deleteRole(Role role);
    void addRoleToUser(String userName, TypeRole id);
    AppUser getUser(String userName);
    AppUser getUserByCin(Long cin);
    AppUser getUserByemail(String email);
    List<AppUser> getUsers();
    List<Role> getRoles();





}
