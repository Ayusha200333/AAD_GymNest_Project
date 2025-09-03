package org.example.aad_gymnest.service.impl;

import org.example.aad_gymnest.dto.UserDTO;
import org.example.aad_gymnest.entity.UserEntity;
import org.example.aad_gymnest.repo.UserRepository;
import org.example.aad_gymnest.service.UserService;
import org.example.aad_gymnest.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService , UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(userEntity.getEmail(),userEntity.getPassword(),getAuthority(userEntity));
    }

    public UserDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        return modelMapper.map(userEntity, UserDTO.class);
    }

    private Set<GrantedAuthority> getAuthority(UserEntity userEntity) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(userEntity.getRole()));
        return authorities;
    }

    @Override
    public UserDTO searchUser(String username){
        if (userRepository.existsByEmail(username)){
            UserEntity userEntity = userRepository.findByEmail(username);
            return modelMapper.map(userEntity, UserDTO.class);
        }else {
            return null;
        }
    }

    @Override
    public UserDTO getUserByEmail(String email){
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity != null){
            return new UserDTO(
                    userEntity.getName(),
                    userEntity.getEmail(),
                    userEntity.getContact(),
                    userEntity.getPassword(),
                    userEntity.getRole()
            );
        }else {
            return null;
        }
    }

    @Override
    public boolean updateUserRole(String email, String newRole){
        Optional<UserEntity> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));
        if(optionalUser.isPresent()){
            UserEntity userEntity = optionalUser.get();
            userEntity.setRole(newRole);
            userRepository.save(userEntity);
            return true;
        }
        return false;
    }

    @Override
    public int saveUser(UserDTO userDTO){
        if(userRepository.existsByEmail(userDTO.getEmail())){
            return VarList.Not_Acceptable;
        }else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userDTO.setRole("USER");
            userRepository.save(modelMapper.map(userDTO, UserEntity.class));
            return VarList.Created;
        }
    }

    @Override
    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteUserByEmail(String email){
        Optional<UserEntity> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));
        if(optionalUser.isPresent()){
            userRepository.delete(optionalUser.get());
            return true;
        }
        return false;
    }
}
