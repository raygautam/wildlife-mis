package in.gov.forest.wildlifemis.config;//package in.gov.wildlife.mis.portal.config;
//
//import com.gov.fisheries.fish.farmer.portal.helper.JwtUtil;
//import io.jsonwebtoken.Claims;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//
///**
// * Custom class to hold all the required information from the request
// *
// *
// * */
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Slf4j
//public class RequestMeta {
//
//    @Value("${authToken}")
//    String serviceAuth;
//
//    private String requestId;
//    private String farmerId;
//    private String requestURL;
//    private String requestMethod;
//    private String protocol;
//    private String authType;
//    private String jwtToken;
//    private String hostUrl;
//    private String remoteHost;
//    private String cookie;
//    private String remoteAddrs;
//    private String xForwardFor;
//
//    private String servicePlusHeader;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//
//    public Boolean isServicePlusUser(){
////       System.out.println("value from "+serviceAuth);
//        if(servicePlusHeader != null && servicePlusHeader.equals(serviceAuth)){
//            return Boolean.TRUE;
//
//        }
//        return Boolean.FALSE;
//    }
//
//
//
//
//    public void auditTrial(){
//
////        log.info("This is the requestURL values{}",requestURL);
////        log.info("This is the hostUrl values{}",hostUrl);
////        log.info("This is the remoteAddrs values{}",remoteAddrs);
////        log.info("This is the remoteHost values{}",remoteHost);
////        log.info("This is the requestMethod values{}",requestMethod);
//    }
//
////    public Boolean isAdminUser(){
////
////    }
//
//    /**
//     * This is to check if it a valid super admin user or not
//     *
//     * */
//
//    public Boolean  isSuperAdmin(){
//
//      Claims claims = jwtUtil.getAllClaimsFromToken(jwtToken.substring(7));
//        String authority = claims.get("authority").toString();
//       return authority.contains("SUPER_ADMIN");
////        if(authority.length() < 23){
////            return  false;
////        }
////    String user = authority.substring(12,23);
////      log.info(claims.get("authority").toString());
////      if(user.equals("SUPER_ADMIN")){
////          return true;
////      }
////      return false;
//    }
//
//    /**
//     * This is to check if he is a district user of not
//     * */
//    public Boolean  isDistrictUser(){
//
//
//        Claims claims = jwtUtil.getAllClaimsFromToken(jwtToken.substring(7));
//        String authority = claims.get("authority").toString();
////        if (authority.length()<26){
////            return false;
////        }
////
////        String user = authority.substring(12,26);
////
////        log.info(claims.get("authority").toString());
////        if(user.equals("DISTRICT_USERS")){
////            return true;
////        }
////        return false;
//      return   authority.contains("DISTRICT_USERS");
//    }
//
//
//    /**
//     * This is to check if he is from the particular district or not if he is a valid district user
//     *
//     * */
//
//    public Boolean isValidDistrictUser(Integer districtId){
//        Claims claims = jwtUtil.getAllClaimsFromToken(jwtToken.substring(7));
//        if(claims.get("district") != null){
//            String district = claims.get("district").toString();
//            if (Integer.parseInt(district) == districtId){
//                return true;
//            }
//        }
//
//       return false;
//
//    }
//
//    public Integer getDistrictCode(){
//        Claims claims = jwtUtil.getAllClaimsFromToken(jwtToken.substring(7));
//
//        String district = claims.get("district").toString();
//        return Integer.parseInt(district);
//    }
//
//    public Boolean isValidAdmin(){
//        Claims claims = jwtUtil.getAllClaimsFromToken(jwtToken.substring(7));
//        String authority = claims.get("authority").toString();
//        return authority.contains("ADMIN");
//
//    }
//}
//
