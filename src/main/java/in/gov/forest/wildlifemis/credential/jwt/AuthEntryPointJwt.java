package in.gov.forest.wildlifemis.credential.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.exception.Error;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//    PrintWriter writer = response.getWriter();
//    writer.println("Access Denied !! " + authException.getMessage());

//    response.setContentType("application/json");
//    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
////    response.addCookie(new Cookie("Cookie","1353546"));
//    String errorMessage = "Access Denied !! " + authException.getMessage();
//    String jsonBody = "{\"error\": \"" + errorMessage + "\"}";
//
//    response.getWriter().write(jsonBody);

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType("application/json");
    new ObjectMapper().writeValue(
            response.getWriter(),
            ApiResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .error(List.of(new Error("","Access Denied !! " + authException.getMessage())))
                    .build()
    );


//    String errorMessage = null;
//    Integer errorCode = null;
//    int errorStatus = getErrorCode(request);
////    Integer errorStatus= (Integer) request.getAttribute("javax.servlet.error.status_code");
//    switch (errorStatus) {
//      case 400 -> {
//        errorMessage = "Http Error Code: 400. Bad Request <br>The server could not understand the request.";
//        errorCode = 400;
//      }
//      case 401 -> {
//        errorMessage = "Http Error Code: 401. Unauthorized <br>The server requires user authentication.";
//        errorCode = 401;
//      }
//      case 403 -> {
//        errorMessage = "Http Error Code: 403. Forbidden <br>You do not have rights to access the content.";
//        errorCode = 403;
//      }
//      case 404 -> {
//        errorMessage = "Http Error Code: 404. Resource not found";
//        errorCode = 404;
//      }
//      case 500 -> {
//        errorMessage = "Http Error Code: 500. Internal Server Error <br>Please Contact administrator";
//        errorCode = 500;
//      }
//      case 503 -> {
//        errorMessage = "Http Error Code: 503. Service Unavailable <br>The server is currently not ready to handle your request";
//        errorCode = 503;
//      }
//    }
//
//    response.setStatus(errorCode);
//    String jsonBody = "{\"error\": \"" + errorMessage + "\"}";
//    response.getWriter().write(jsonBody);

  }



//  private int getErrorCode(HttpServletRequest httpRequest) {
//    return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
//  }

}

