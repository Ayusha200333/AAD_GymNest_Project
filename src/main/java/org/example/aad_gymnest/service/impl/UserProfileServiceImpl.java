package org.example.aad_gymnest.service.impl;
//
//
//import jakarta.transaction.Transactional;
//import org.example.aad_gymnest.dto.UserProfileDTO;
//import org.example.aad_gymnest.entity.UserProfile;
//import org.example.aad_gymnest.repo.UserProfileRepository;
//import org.example.aad_gymnest.service.UserProfileService;
//import org.example.aad_gymnest.util.VarList;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@Transactional
public class UserProfileServiceImpl  {
//
//    @Autowired
//    private UserProfileRepository userProfileRepository;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
//       UserProfile userProfile = userProfileRepository.findByEmail(email);
//       return new org.springframework.security.core.userdetails.User(userProfile.getEmail(), userProfile.getFirstName(),userProfile.getLastName(),userProfile.getPhone(),getAuthority(userProfile));
//    }
//
//    public UserProfileDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException{
//       UserProfile userProfile = userProfileRepository.findByEmail(username);
//       return modelMapper.map(userProfile, UserProfileDTO.class);
//    }
//
////    private Set<GrantedAuthority> getAuthority(UserProfile userProfile) {
////        Set<GrantedAuthority> authorities = new HashSet<>();
////        authorities.add(new SimpleGrantedAuthority(userProfile.getEmail()))
////    }
//
//    @Override
//    public UserProfileDTO searchUser(String username){
//        if (userProfileRepository.existsByEmail(username)) {
//            UserProfile userProfile = userProfileRepository.findByEmail(username);
//            return modelMapper.map(userProfile, UserProfileDTO.class);
//        }else {
//            return null;
//        }
//    }
//
//    @Override
//    public UserProfileDTO getUserByEmail(String email){
//        UserProfile userProfile = userProfileRepository.findByEmail(email);
//        if(userProfile != null) {
//            return new UserProfileDTO(
//                    userProfile.getFirstName(),
//                    userProfile.getLastName(),
//                    userProfile.getEmail(),
//                    userProfile.getPhone()
//            );
//        }else {
//            return null;
//        }
//    }
//
//    @Override
//    public int saveUser(UserProfileDTO userProfileDTO){
//        if(userProfileRepository.existsByEmail(userProfileDTO.getEmail())) {
//            return VarList.Not_Acceptable;
//        }else {
//            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            UserProfileDTO.s
//        }
//    }
}
