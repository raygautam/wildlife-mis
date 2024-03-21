package in.gov.forest.wildlifemis.servicePlus.getServicePlusStatus;


import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.common.MessageByLocale;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


@RestController
@CrossOrigin("*")
//@CrossOrigin(origins = "http://127.0.0.1:5173")
public class ServicePlusStatusController {

    @Autowired
    private MessageByLocale messageByLocale;

    @Autowired
    private ServicePlusStatusService servicePlusStatusService;




    @GetMapping("/servicePlusStatus")
    public String serviceDetails() throws MalformedURLException, IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        OutputStream httpStream = null;
        String url = "https://investmeghalaya.gov.in/configure/getServiceWiseData";
        StringBuilder response = new StringBuilder(5200);
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }
                    }
            };
            //Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();

            //add reuqest header
            //forest serviceId -1357
            httpClient.setRequestMethod("POST");
            String content = "{\"serviceId\":\"1301\",\"departmentId\": \"\"}";

            httpClient.setRequestProperty("Content-Type", "application/json");
            httpClient.setRequestProperty("Accept", "application/json");

            httpClient.setDoOutput(true);
            httpStream  = httpClient.getOutputStream();

            DataOutputStream wr = new DataOutputStream(httpStream);
            byte[] input = content.getBytes("utf-8");
            wr.write(input);
            wr.flush();

            int responseCode = httpClient.getResponseCode();
            InputStream inputStream = httpClient.getInputStream();
            in = new BufferedReader(
                    new InputStreamReader(inputStream));

            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            wr.close();


        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        } finally {
            in.close();
            assert httpStream != null;
            httpStream.flush();
        }

        return response.toString();

    }


    @GetMapping("servicePlus/district/data/{districtCode}")
    public ResponseEntity<ApiResponse<?>> getAllDistrictWiseDetail(@PathVariable(value = "districtCode",required = true) Integer districtCode ) throws JSONException, JSONException {

        ApiResponse<?> apiResponse = servicePlusStatusService.getDistrictWiseStatusOfApplication(districtCode);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);


    }

}
