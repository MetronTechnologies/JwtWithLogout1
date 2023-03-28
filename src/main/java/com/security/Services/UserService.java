package com.security.Services;



import com.security.DataTransferObjects.Requests.Login;
import com.security.DataTransferObjects.Requests.Register;
import com.security.DataTransferObjects.Responses.AuthenticationResponse;
import com.security.Models.TokenType;
import com.security.Models.UserModel;
import com.security.Models.UserRoles;
import com.security.Models.UserToken;
import com.security.Repositories.TokenRepo;
import com.security.Repositories.UserRepo;
import com.security.Services.ServiceImplementations.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final TokenRepo tokenRepo;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;

    private void saveUserToken(UserModel userModel, String jwtToken){
        var token = UserToken
                .builder()
                .userModel(userModel)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepo.save(token);
    }

    private void revokeAllUserTokens(UserModel userModel){
        List<UserToken> allValidTokenByUser = tokenRepo.findAllValidTokenByUser(userModel.getUserid());
        if(allValidTokenByUser.isEmpty()){
            return ;
        }
        allValidTokenByUser.forEach(
                userToken -> {
                    userToken.setRevoked(true);
                    userToken.setExpired(true);
                }
        );
        tokenRepo.saveAll(allValidTokenByUser);
    }


    public String generateUsernameFromEmail(String email){
        int atPosition = email.indexOf("@");
        String username = email.substring(0, atPosition);
        return username;
    }

    public AuthenticationResponse register(Register register){
        var user = UserModel
                .builder()
                .firstName(register.getFirstname())
                .lastName(register.getLastname())
                .email(register.getEmail())
                .role(UserRoles.USER)
                .username(generateUsernameFromEmail(register.getEmail()))
                .password(passwordEncoder.encode(register.getPassword()))
                .created(Instant.now())
                .build();
        UserModel savedUser = userRepo.save(user);
        String jwtToken = jwtService.generateToken(user);
        System.out.println(jwtService.extractExpiration(jwtToken));
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse
                .builder()
                .authToken(jwtToken)
                .build();
    }

    public AuthenticationResponse login(Login login){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
        );
        UserModel userModel = userRepo
                .findByUsername(login.getUsername())
                .orElseThrow();
        String token = jwtService.generateToken(userModel);
        revokeAllUserTokens(userModel);
        saveUserToken(userModel, token);
        Date date = jwtService.extractExpiration(token);
        System.out.println("Current time ---> " + LocalDateTime.now());
        System.out.println("Current time ---> " + new Date(System.currentTimeMillis()));
        System.out.println("Expiration time ---> " + date);
        return AuthenticationResponse
                .builder()
                .authToken(token)
                .build();
    }



}
