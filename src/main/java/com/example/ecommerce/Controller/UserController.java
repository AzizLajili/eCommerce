package com.example.ecommerce.Controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.ecommerce.DAO.*;
import com.example.ecommerce.Service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
public class UserController {

    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @PostMapping("/addUser")
    public ResponseEntity<AppUser> addUser(@RequestParam("user") String userJson,@RequestParam("image") MultipartFile image) {
        AppUser user = new Gson().fromJson(userJson, AppUser.class);
        // Rename and copy the image to a certain path
        String imgName = StringUtils.cleanPath(image.getOriginalFilename());
        log.info(imgName);
        byte[] bytes = imgName.getBytes();
        int image2=bytes.toString().hashCode();
        String imageName = ""+ image2; // Generate a unique name for the image
        Path imagePath = Paths.get("C:\\xampp\\htdocs\\img" + imageName);
        String path="C:/xampp/img";
        try {
            Files.copy(image.getInputStream(), Paths.get(path + File.separator + image2 + ".jpg"));
        }catch (IOException e){
            log.info(e.getMessage());
        }
        user.setImage(imageName);
        // Save the user object to the database
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public String doLogin(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticated = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticated);
        return "redirect:/allusers";
    }
    @GetMapping("/allusers")
    //@PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }
    @PostMapping("/updatePassword/{email}")
    //@PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<AppUser> updatePassword(@RequestBody AuthenticationRequest form,@PathVariable String email) {
        userService.updatePassword(email,form.getEmail(),form.getPassword());

        return ResponseEntity.ok().body(userService.getUserByemail(email));
    }

    @GetMapping("/allroles")
    //@PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok().body(userService.getRoles());
    }



    @GetMapping("/user/{cin}")
    public ResponseEntity<AppUser> getUserById(@PathVariable Long cin) {
        return ResponseEntity.ok().body(userService.getUserByCin(cin));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")

    @DeleteMapping("/deluser/{cin}")
    public void removeUserBycon(@PathVariable Long cin){
        userService.deleteUser(cin);
    }
    //@PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/adduser")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/adduser").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }
    @PostMapping("/addrole")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/addrole").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }
    @GetMapping("/token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] authorizationCookie = request.getCookies();
        String RefreshToken = authorizationCookie[1].getValue();
        log.info("this is the authorization header for the refresh Token {}", authorizationCookie);
        if(authorizationCookie!=null) {

            try{
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(RefreshToken);
                String username = decodedJWT.getSubject();
                log.info("this is the refresh tokens' username : {}", username);
                AppUser user = userService.getUser(username);
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                log.info("this is the reresh tokens' roles : {}", roles);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ 10 * 60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .sign(algorithm);
                Cookie accessCookie = new Cookie("Authorization", access_token);
                response.addCookie(accessCookie);
                log.info("el token tzed {}" , access_token);

            }catch (Exception exception){
                log.error("Error logging in refresh {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                //response.sendError(HttpServletResponse.SC_FORBIDDEN);
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }else{
            throw new RuntimeException("Refresh token is missing");

        }
    }


    @PostMapping("/roletouser")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> roleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(),form.getRolename());
        return ResponseEntity.ok().build();
    }

    }
@Data
class RoleToUserForm{
    private String username;
    private TypeRole rolename;
}
@Data
class AuthenticationRequest{
    private String email;
    private String password;
}
