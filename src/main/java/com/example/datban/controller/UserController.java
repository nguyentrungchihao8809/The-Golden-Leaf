// package com.example.datban.controller;

// import com.example.datban.model.User;
// import com.example.datban.repository.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/user")
// @CrossOrigin(origins = "*")
// public class UserController {

//     @Autowired
//     private UserRepository userRepository;

//     @PostMapping("/add")
//     public String addUser(@RequestBody User user) {
//         User savedUser = userRepository.save(user);
//         System.out.println("✅ Da Luu Vao DB: " + savedUser.getFullName() + " (id=" + savedUser.getId() + ")");
//         return "User " + savedUser.getFullName() + " Luu Thanh Cong!";
//     }

//     @GetMapping("/list")
//     public List<User> listUsers() {
//         return userRepository.findAll();
//     }
// }
