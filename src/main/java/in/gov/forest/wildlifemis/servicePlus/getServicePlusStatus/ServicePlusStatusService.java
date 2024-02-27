//package in.gov.forest.wildlifemis.servicePlus.getServicePlusStatus;
//
//import com.gov.fisheries.fish.farmer.portal.common.ApiResponse;
//import com.gov.fisheries.fish.farmer.portal.district.DistrictRepository;
//import com.gov.fisheries.fish.farmer.portal.districtlocationmap.DistrictLocationMapRepository;
//import com.gov.fisheries.fish.farmer.portal.domains.District;
//import com.gov.fisheries.fish.farmer.portal.domains.DistrictLocationMap;
//import com.gov.fisheries.fish.farmer.portal.serviceplus.dto.ServicePlusDistrictWiseAnalyticsFetchDTO;
//import com.gov.fisheries.fish.farmer.portal.serviceplus.dto.ServicePlusDistrictWiseAnalyticsPostDTO;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//import java.security.cert.X509Certificate;
//
//@Service
//public class ServicePlusStatusService {
//
//    @Autowired
//    private DistrictLocationMapRepository districtLocationMapRepository;
//    @Autowired
//    private DistrictRepository districtRepository;
//
//    public ApiResponse getDistrictWiseStatusOfApplication(Integer districtCode) throws JSONException {
//
//        District district = districtRepository.findByDistrictCode(districtCode);
//
//        DistrictLocationMap byDistrictCode = districtLocationMapRepository.findByDistrict(district);
//
//        try {
//            TrustManager[] trustAllCertificates = new TrustManager[] {
//                    new X509TrustManager() {
//                        public X509Certificate[] getAcceptedIssuers() {
//                            return null;
//                        }
//                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                        }
//                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                        }
//                    }
//            };
//
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());
//            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
//            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        RestTemplate restTemplate = new RestTemplate();
//        ServicePlusDistrictWiseAnalyticsPostDTO districtWiseDetaisFetchDTO = new ServicePlusDistrictWiseAnalyticsPostDTO();
//        districtWiseDetaisFetchDTO.setServiceId("1301");
//
//        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("https://investmeghalaya.gov.in/configure/getLocationWiseData", districtWiseDetaisFetchDTO, String.class);
//
//        String body = stringResponseEntity.getBody();
//        String replace = body.replace("Location id", "locationId").replace("Location Name", "locName");
//
//        JSONArray json = new JSONArray(replace);
//        ServicePlusDistrictWiseAnalyticsFetchDTO servicePlusDistrictWiseAnalyticsFetchDTO = new ServicePlusDistrictWiseAnalyticsFetchDTO();
//
////        System.out.println(json.get(0));
//        for(int i =0;i< json.length();i++){
//
//            JSONObject jsonObject = json.getJSONObject(i);
//
//            if( jsonObject.get("locationId").toString().equals( byDistrictCode.getLocationId().toString())) {
////                System.out.println("inside if ");
////               System.out.println(jsonObject);
//                servicePlusDistrictWiseAnalyticsFetchDTO.setSubmitted(jsonObject.get("Submitted").toString());
//                servicePlusDistrictWiseAnalyticsFetchDTO.setRejected(jsonObject.get("Rejected").toString());
//                servicePlusDistrictWiseAnalyticsFetchDTO.setDelivered(jsonObject.get("Delievered").toString());
//                servicePlusDistrictWiseAnalyticsFetchDTO.setPending(jsonObject.get("Pending").toString());
//
//            }
//
//        }
//
//        if (servicePlusDistrictWiseAnalyticsFetchDTO.getDelivered()==null){
//            servicePlusDistrictWiseAnalyticsFetchDTO.setSubmitted("0");
//            servicePlusDistrictWiseAnalyticsFetchDTO.setPending("0");
//            servicePlusDistrictWiseAnalyticsFetchDTO.setRejected("0");
//            servicePlusDistrictWiseAnalyticsFetchDTO.setDelivered("0");
//        }
//
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setData(servicePlusDistrictWiseAnalyticsFetchDTO);
//        return apiResponse;
//
//    }
//}
