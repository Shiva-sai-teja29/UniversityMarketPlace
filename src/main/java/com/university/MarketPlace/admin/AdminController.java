package com.university.MarketPlace.admin;

import com.university.MarketPlace.security.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService userService;

    // See User
    @GetMapping("/User/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        User user1 = userService.getUser(id);
        return ResponseEntity.ok(user1);
    }

    // See User
    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> user1 = userService.getAllUsers();
        return ResponseEntity.ok(user1);
    }

    //    Approve shop
    @PostMapping("/approveShop")
    public ResponseEntity<String> approveShop(@RequestBody ApprovalDTO approvalDTO){
        System.out.println("Entered");
        String approved = userService.approveShop(approvalDTO);
        System.out.println(extractUser());
        return ResponseEntity.ok(approved);
    }

    public User extractUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        return (User) auth.getPrincipal();
    }
}
