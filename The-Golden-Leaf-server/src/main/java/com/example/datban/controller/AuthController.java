// // File: com.example.datban.controller.AuthController.java

// package com.example.datban.controller;

// import com.example.datban.dto.TokenRequest;
// import com.example.datban.service.AuthService;
// import com.example.datban.model.User;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping("/api/auth") // Đặt base path cho dễ quản lý
// public class AuthController {

//     private final AuthService authService;

//     // Dependency Injection (Sử dụng Constructor Injection)
//     public AuthController(AuthService authService) {
//         this.authService = authService;
//     }

//     /**
//      * Endpoint để Client gửi Firebase ID Token lên Backend.
//      * Backend sẽ xác thực Token và đồng bộ thông tin người dùng vào DB.
//      * * @param request Chứa Firebase ID Token
//      * @return User object đã được đồng bộ hóa hoặc lỗi
//      */
//     @PostMapping("/sync-user")
//     public ResponseEntity<User> synchronizeUser(@RequestBody TokenRequest request) {
        
//         // Kiểm tra Token không rỗng
//         if (request.getIdToken() == null || request.getIdToken().isEmpty()) {
//             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//         }

//         try {
//             // Gọi Service để thực hiện đồng bộ hóa
//             User synchronizedUser = authService.synchronizeUser(request.getIdToken());
            
//             // Trả về thông tin User đã được lưu/cập nhật và mã HTTP 200 OK
//             return ResponseEntity.ok(synchronizedUser);

//         } catch (Exception e) {
//             // Xử lý lỗi khi Token không hợp lệ (ví dụ: hết hạn, bị giả mạo)
//             // hoặc lỗi xảy ra trong quá trình lưu DB.
//             System.err.println("Synchronization error: " + e.getMessage());
            
//             // Trả về mã HTTP 401 Unauthorized nếu lỗi liên quan đến xác thực Token
//             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//         }
//     }
// }