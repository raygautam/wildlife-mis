package in.gov.forest.wildlifemis.testing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TestingController {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/public/testing")
    public Object testing() throws JsonProcessingException {
        String sql = "SELECT * FROM fish_farmer_details WHERE fish_farmer_id = 'ML-FFIC/2023/00001'";
        // Retrieve the result of the SQL query
        ObjectMapper objectMapper=new ObjectMapper();
        Map<String, Object> result = jdbcTemplate.queryForMap(sql);
        // Convert the result to YourObject using ObjectMapper
        return objectMapper.convertValue(result, Object.class);
    }
}
