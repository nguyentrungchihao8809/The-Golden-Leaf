// package com.example.datban.service;

// import com.example.datban.model.User;
// import com.example.datban.repository.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// @Service
// public class UserService {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private PasswordEncoder passwordEncoder; // BCryptPasswordEncoder

//     public User register(String ten, String email, String rawPassword) {
//         if(userRepository.findByEmail(email).isPresent()) {
//             throw new RuntimeException("Email đã tồn tại");
//         }
//         User user = new User();
//         user.setTen(ten);
//         user.setEmail(email);
//         user.setPassword(passwordEncoder.encode(rawPassword));
//         user.setRole("CUSTOMER");
//         return userRepository.save(user);
//     }

//     public User login(String email, String rawPassword) {
//         User user = userRepository.findByEmail(email)
//             .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

//         if(!passwordEncoder.matches(rawPassword, user.getPassword())) {
//             throw new RuntimeException("Mật khẩu sai");
//         }
//         return user;
//     }
// }
