package com.project.controller.admin;

import com.project.auth.CustomerUserDetailService;
import com.project.jwt.model.JwtRequest;
import com.project.jwt.model.JwtResponse;
import com.project.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerUserDetailService customerUserDetailService;

    @GetMapping("/api/test")
    public ResponseEntity<String> test(){
        return new ResponseEntity<>("co quyen khong ?", HttpStatus.OK);
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> loadLogin(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = customerUserDetailService.loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtUtil.generateJwtToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

}
