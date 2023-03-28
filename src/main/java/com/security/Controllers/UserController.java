package com.security.Controllers;




import com.security.DataTransferObjects.Requests.Login;
import com.security.DataTransferObjects.Requests.Register;
import com.security.DataTransferObjects.Responses.AuthenticationResponse;
import com.security.Services.ServiceImplementations.LogoutService;
import com.security.Services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserController {


    @Autowired
    private final UserService userService;

    @Autowired
    private final LogoutService logoutService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody Register register){
        return new ResponseEntity<>(userService.register(register), CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody Login login){
        return new ResponseEntity<>(userService.login(login), OK);
    }



}
