package org.example.aad_gymnest.service;

import org.example.aad_gymnest.dto.UserDTO;

import java.util.List;

public interface UserService {
    int saveUser(UserDTO userDTO);
    UserDTO searchUser(String username);
    UserDTO getUserByEmail(String email);
    boolean updateUserRole(String email , String role);
    List<UserDTO> getAllUsers();
    boolean deleteUserByEmail(String email);

    int getTotalMemberCount();
    int getActiveMembershipCount();
}
