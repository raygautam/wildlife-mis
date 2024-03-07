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

    @GetMapping("/public/getDistrictWiseApplicationCount")
    public List<?> getDistrictWiseApplicationCount() throws JsonProcessingException {
        String sql = "SELECT district_name, count(fish_farmer_id) as application_count FROM fish_farmer_details ffd " +
                "Inner join districts d on d.district_code=ffd.district_code " +
                "GROUP BY district_name";
        // Retrieve the result of the SQL query
        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
//        return objectMapper.convertValue(, Object[].class);
    }

    @GetMapping("/public/getDivisionWiseApplicationCount")
    public Object getDivisionWiseApplicationCount() throws JsonProcessingException {
        String sql = "SELECT d.name, count(fish_farmer_id) as application_count FROM fish_farmer_details ffd\n" +
                "Inner join division d on d.id=ffd.division_id\n" +
                "GROUP BY d.name";
        // Retrieve the result of the SQL query
        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
    }
    @GetMapping("/public/getRangeWiseApplicationCount")
    public Object getRangeWiseApplicationCount() throws JsonProcessingException {
        String sql = "SELECT d.range_name, count(fish_farmer_id) as application_count FROM fish_farmer_details ffd\n" +
                "Inner join range d on d.range_id=ffd.range_id\n" +
                "GROUP BY d.range_name";
        // Retrieve the result of the SQL query
        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
    }
}
