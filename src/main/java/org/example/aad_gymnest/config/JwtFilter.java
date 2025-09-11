//package org.example.aad_gymnest.config;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.example.aad_gymnest.service.impl.UserServiceImpl;
//import org.example.aad_gymnest.util.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JwtUtil jwtUtil;
//    @Autowired
//    private UserServiceImpl userService;
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        String authorization = httpServletRequest.getHeader("Authorization");
//        String token = null;
//        String email = null;
//
//        if (null != authorization && authorization.startsWith("Bearer ")) {
//
//            token = authorization.substring(7);
//            email = jwtUtil.getUsernameFromToken(token);
//            Claims claims=jwtUtil.getUserRoleCodeFromToken(token);
//            httpServletRequest.setAttribute("email", email);
//            httpServletRequest.setAttribute("role", claims.get("role"));
//        }
//
//        if (null != email && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails
//                    = userService.loadUserByUsername(email);
//
//            if (jwtUtil.validateToken(token, userDetails)) {
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
//                        = new UsernamePasswordAuthenticationToken(userDetails,
//                        null, userDetails.getAuthorities());
//
//                usernamePasswordAuthenticationToken.setDetails(
//                        new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
//                );
//
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            }
//
//        }
//        filterChain.doFilter(httpServletRequest, httpServletResponse);
//    }
//
////    @Override
////    protected boolean shouldNotFilter(HttpServletRequest request) {
////        String path = request.getServletPath();
////        return path.startsWith("/auth/")
////                || path.startsWith("/api/v1/auth/")
////                || path.startsWith("/api/v1/user/register")
////                || path.startsWith("/api/v1/user-reviews/");
////    }
//
//    private Claims getClaimsFromJwtToken(String token) {
//        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
//    }
//}
//


package org.example.aad_gymnest.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.aad_gymnest.service.impl.UserServiceImpl;
import org.example.aad_gymnest.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserServiceImpl userService;
    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = null;
        String email = null;

        if (null != authorization && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            email = jwtUtil.getUsernameFromToken(token);
            Claims claims = jwtUtil.getUserRoleCodeFromToken(token);  // Assuming this method exists in JwtUtil
            httpServletRequest.setAttribute("email", email);
            httpServletRequest.setAttribute("role", claims.get("role"));
        }

        if (null != email && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = userService.loadUserByUsername(email);
                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                    );

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } catch (UsernameNotFoundException e) {
                // User not found (e.g., deleted user or invalid token) - skip authentication, proceed unauthenticated
                // Log if needed: logger.warn("User not found for token: " + email);
            } catch (Exception e) {
                // Other validation errors - skip
                // logger.error("Token validation failed: " + e.getMessage());
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    // Uncomment and update shouldNotFilter to skip public endpoints like register
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/auth/")  // If you have auth endpoints
                || path.startsWith("/api/v1/auth/")
                || path.startsWith("/api/v1/user/register")  // Skip filter for register (public)
                || path.startsWith("/api/v1/user-reviews/");  // Add other public paths if needed
    }

    private Claims getClaimsFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
    }
}
