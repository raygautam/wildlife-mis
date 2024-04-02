package in.gov.forest.wildlifemis.credential.jwt;
;
//import in.gov.forest.wildlifemis.credential.authentication.AppUserServiceImpl;
//import in.gov.forest.wildlifemis.exception.AccessDeniedException;
//import in.gov.forest.wildlifemis.exception.Error;
//import in.gov.forest.wildlifemis.exception.JwtCustomException;
import in.gov.forest.wildlifemis.auditTrail.AuditTrailRepository;
import in.gov.forest.wildlifemis.auditTrail.RequestBodyCachingWrapper;
import in.gov.forest.wildlifemis.credential.authentication.UserDetailsServiceImpl;
import in.gov.forest.wildlifemis.domian.AuditTrail;
import in.gov.forest.wildlifemis.exception.AccessDeniedException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.exception.JwtCustomException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;


@Component
@Slf4j
//@WebFilter("/*")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuditTrailRepository auditTrailRepository;

    private final Bucket bucket;


    public JwtAuthenticationFilter() {
        Bandwidth limit = Bandwidth.classic(50, Refill.intervally(50, Duration.ofSeconds(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtHelper.getUsernameFromToken(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtHelper.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }





//    @Override
//    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
//
//        if(bucket.tryConsume(1)) {
//            if (request.getServletPath().startsWith("/public")) {
//                filterChain.doFilter(request, response);
//            }else{
//                String requestHeader = request.getHeader("Authorization");
//                logger.info(" Header :  {}", requestHeader);
//                String username = null;
//                String token = null;
//                if (requestHeader != null && requestHeader.startsWith("Bearer")) {
//                    //looking good
//                    token = requestHeader.substring(7);
//                    try {
//
//                        username = this.jwtHelper.getUsernameFromToken(token);
//
//                    } catch (IllegalArgumentException e) {
//                        logger.info("Illegal Argument while fetching the username !!"+e.getMessage());
//                        Error error = new Error("",e.getMessage());
//                        throw new JwtCustomException("Illegal Argument while fetching the username !!", error);
//                    } catch (ExpiredJwtException e) {
//                        logger.info("Given jwt token is expired !!");
//                        Error error = new Error("","JWT token has expired");
//                        throw new JwtCustomException("JWT token has expired", error);
//                    } catch (MalformedJwtException e) {
//                        logger.info("Some changed has done in token !! Invalid Token");
//                        Error error = new Error("",e.getMessage());
//                        throw new JwtCustomException("Some changed has done in token !! Invalid Token",error);
//
//                    } catch (Exception e) {
//                        throw new RuntimeException(e.getMessage());
//                    }
//
//                } else {
//                    logger.info("Invalid Header Value !! ");
//                    Error error=new Error("","Invalid Header Value !! ");
//                    throw new JwtCustomException("Invalid JWT token", error);
//                }
//
//                //
//                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//
//                    //fetch user detail from username
//                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//                    Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
//                    if (validateToken) {
//
//                        //set the authentication
//                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//
//                    } else {
//                        logger.info("Validation fails !!");
//                    }
//
//                }else {
//                    throw  new AccessDeniedException("error-message.access-denied");
//                }
//            }
//            try {
//                RequestBodyCachingWrapper requestWrapper = new RequestBodyCachingWrapper(request);
//                String requestBody = requestWrapper.getRequestBody();
//                filterChain.doFilter(requestWrapper, response);
//                AuditTrail auditTrail=AuditTrail.builder()
//                        .url(request.getRequestURI())
//                        .userName(request.getRemoteUser())
//                        .payload(requestBody.isEmpty() ?"[]":requestBody)
//                        .ipAddress(request.getRemoteAddr())
//                        .userAgent(request.getHeader("User-Agent"))
//                        .requestOn(LocalDateTime.now())
//                        .httpMethod(request.getMethod())
//                        .statusCode(response.getStatus())
//                        .build();
//                auditTrailRepository.save(auditTrail);
//            } catch (Exception e) {
//                logger.error("An error occurred while processing the filter chain: {}", e.getMessage());
//            }
//
//        } else {
//            response.getWriter().write("Too many requests. Please try again later.");
//            response.setStatus(429);// HTTP 429 Too Many Requests
//        }
//     }
}



