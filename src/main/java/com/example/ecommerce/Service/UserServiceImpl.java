package com.example.ecommerce.Service;

import com.example.ecommerce.DAO.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Transactional
@Service("IUserService")
@RequiredArgsConstructor
@Slf4j

public class UserServiceImpl implements IUserService, UserDetailsService {

    private final IUserRepo userRepo;

    private final IRoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;
    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving new user {} to the database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    @Override
    public void deleteUser(Long cin) {
        log.info("Deleting user with cin {}", cin);
        userRepo.deleteById(cin);
    }
    @Override
    public AppUser updateUser(AppUser user) {
        return userRepo.save(user);
    }
    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }
    @Override
    public AppUser updatePassword(String email, String oldPassword, String newPassword) {
        AppUser user = userRepo.findByEmail(email);
        log.info("the email trying to change password : {}", email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BadCredentialsException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        log.info("The password has been updated");
        return userRepo.save(user);
    }
    @Override
    public void deleteRole(Role role) {
        roleRepo.delete(role);
    }
    @Override
    public void addRoleToUser(String email, TypeRole typeRole) {
        log.info("Adding role {} to user {}", typeRole, email);
        AppUser user = userRepo.findByEmail(email);
        Role role = roleRepo.findById(typeRole).get();
        user.getRoles().add(role);
    }
    @Override
    public AppUser getUserByCin(Long cin) {
        log.info("getting user with cin {}", cin);
        return userRepo.findById(cin).get();
    }
    @Override
    public AppUser getUserByemail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public AppUser getUser(String email) {
        log.info("getting user with email {}", email);
        return userRepo.findByEmail(email);    }
    @Override
    public List<AppUser> getUsers() {
        return userRepo.findAll();    }

    @Override
    public List<Role> getRoles() {
        return roleRepo.findAll();    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepo.findByEmail(email);
        if(user == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("log.error(\"User not found in the database");
        }else {
            log.info("User found in the database : {}", email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRole().toString()));
        });
        log.info("authorities : {}",authorities);
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }
}
