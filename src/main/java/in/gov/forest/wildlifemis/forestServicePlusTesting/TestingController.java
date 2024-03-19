package in.gov.forest.wildlifemis.forestServicePlusTesting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public")
@CrossOrigin("*")
public class TestingController {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/testing")
    public Object testing() throws JsonProcessingException {
        String sql = "SELECT * FROM fish_farmer_details WHERE fish_farmer_id = 'ML-FFIC/2023/00001'";
        // Retrieve the result of the SQL query
        ObjectMapper objectMapper=new ObjectMapper();
        Map<String, Object> result = jdbcTemplate.queryForMap(sql);
        // Convert the result to YourObject using ObjectMapper
        return objectMapper.convertValue(result, Object.class);
    }

    @GetMapping("/getDistrictWiseApplicationCount")
    public List<?> getDistrictWiseApplicationCount() throws JsonProcessingException {
//        String sql = "SELECT district_name, count(fish_farmer_id) as application_count FROM fish_farmer_details ffd " +
//                "Inner join districts d on d.district_code=ffd.district_code " +
//                "GROUP BY district_name";

        String sql = "SELECT district_name, COALESCE(count(fish_farmer_id)) as application_count FROM districts d\n" +
                "Left join fish_farmer_details ffd  on ffd.district_code=d.district_code\n" +
                "GROUP BY district_name";
        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
//        return objectMapper.convertValue(, Object[].class);
    }

    @GetMapping("/getDivisionWiseApplicationCount")
    public Object getDivisionWiseApplicationCount() throws JsonProcessingException {
//        String sql = "SELECT d.name, count(fish_farmer_id) as application_count FROM fish_farmer_details ffd\n" +
//                "Inner join division d on d.id=ffd.division_id\n" +
//                "GROUP BY d.name";
        String sql = "SELECT d.name, COALESCE(count(fish_farmer_id), 0) as application_count FROM division d\n" +
                "Left join fish_farmer_details ffd on ffd.division_id=d.id\n" +
                "GROUP BY d.name\n" +
                "Order By d.name";
        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
    }
    @GetMapping("/getRangeWiseApplicationCount")
    public Object getRangeWiseApplicationCount() throws JsonProcessingException {
//        String sql = "SELECT d.range_name, count(fish_farmer_id) as application_count FROM fish_farmer_details ffd\n" +
//                "Inner join range d on d.range_id=ffd.range_id\n" +
//                "GROUP BY d.range_name";

        String sql="SELECT d.range_name, COALESCE(count(ffd.range_id), 0) as application_count\n" +
                "FROM range d\n" +
                "LEFT JOIN fish_farmer_details ffd on ffd.range_id=d.range_id\n" +
                "GROUP BY d.range_name, d.range_id\n" +
                "ORDER BY d.range_name";

        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
    }

    @GetMapping("/getAllApplicationsByDivisionId/{id}")
    public Object getAllApplicationsByDivisionId(@PathVariable Long id) throws JsonProcessingException {
        String sql = "SELECT * FROM fish_farmer_details ffd\n" +
                "Where ffd.division_id="+id;

        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
    }
    @GetMapping("/getAllApplicationsByRangeId/{id}")
    public Object getAllApplicationsByRangeId(@PathVariable Long id) throws JsonProcessingException {
        String sql = "SELECT * FROM fish_farmer_details ffd\n" +
                "Where ffd.range_id="+id;

        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
    }

    @GetMapping("/getAllApplicationsByDistrictCode/{id}")
    public Object getAllApplicationsByDistrictCode(@PathVariable Long id) throws JsonProcessingException {
        String sql = "SELECT * FROM fish_farmer_details ffd\n" +
                "Where ffd.district_code="+id;

        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
    }


    @GetMapping("/getApplicationsCountForAllMonthForAllTheDistricts")
    public Object getApplicationsCountForAllMonthForAllTheDistrict() throws JsonProcessingException {
        String sql = "SELECT \n" +
                "    d.district_name AS DistrictName,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 1 THEN Application_Count END), 0) AS January,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 2 THEN Application_Count END), 0) AS February,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 3 THEN Application_Count END), 0) AS March,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 4 THEN Application_Count END), 0) AS April,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 5 THEN Application_Count END), 0) AS May,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 6 THEN Application_Count END), 0) AS June,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 7 THEN Application_Count END), 0) AS July,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 8 THEN Application_Count END), 0) AS August,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 9 THEN Application_Count END), 0) AS September,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 10 THEN Application_Count END), 0) AS October,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 11 THEN Application_Count END), 0) AS November,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 12 THEN Application_Count END), 0) AS December\n" +
                "FROM \n" +
                "    (SELECT DISTINCT district_name FROM districts) d\n" +
                "CROSS JOIN \n" +
                "    (SELECT generate_series(1, 12) AS month) calendar\n" +
                "LEFT JOIN \n" +
                "    (SELECT \n" +
                "         EXTRACT(month FROM a.created_at) AS month,\n" +
                "         d.district_name,\n" +
                "         COUNT(a.fish_farmer_id) AS Application_Count\n" +
                "     FROM \n" +
                "         fish_farmer_details a\n" +
                "     JOIN \n" +
                "         districts d ON a.district_code = d.district_code\n" +
                "     GROUP BY \n" +
                "         EXTRACT(month FROM a.created_at), d.district_name) AS counts \n" +
                "ON \n" +
                "    calendar.month = counts.month AND d.district_name = counts.district_name\n" +
                "GROUP BY \n" +
                "    d.district_name\n" +
                "ORDER BY \n" +
                "    d.district_name;";

        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
    }


    @GetMapping("/getApplicationsCountForAllMonthForAllTheDivisions")
    public Object getApplicationsCountForAllMonthForAllTheDivisions() throws JsonProcessingException {
        String sql = "SELECT \n" +
                "    d.name AS DivisionName,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 1 THEN Application_Count END), 0) AS January,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 2 THEN Application_Count END), 0) AS February,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 3 THEN Application_Count END), 0) AS March,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 4 THEN Application_Count END), 0) AS April,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 5 THEN Application_Count END), 0) AS May,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 6 THEN Application_Count END), 0) AS June,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 7 THEN Application_Count END), 0) AS July,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 8 THEN Application_Count END), 0) AS August,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 9 THEN Application_Count END), 0) AS September,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 10 THEN Application_Count END), 0) AS October,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 11 THEN Application_Count END), 0) AS November,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 12 THEN Application_Count END), 0) AS December\n" +
                "FROM \n" +
                "    (SELECT DISTINCT name FROM division) d\n" +
                "CROSS JOIN \n" +
                "    (SELECT generate_series(1, 12) AS month) calendar\n" +
                "LEFT JOIN \n" +
                "    (SELECT \n" +
                "         EXTRACT(month FROM a.created_at) AS month,\n" +
                "         d.name,\n" +
                "         COUNT(a.fish_farmer_id) AS Application_Count\n" +
                "     FROM \n" +
                "         fish_farmer_details a\n" +
                "     JOIN \n" +
                "         division d ON  d.id= a.division_id\n" +
                "     GROUP BY \n" +
                "         EXTRACT(month FROM a.created_at), d.name) AS counts \n" +
                "ON \n" +
                "    calendar.month = counts.month AND d.name = counts.name\n" +
                "GROUP BY \n" +
                "    d.name\n" +
                "ORDER BY \n" +
                "    d.name;";

        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
    }

    @GetMapping("/getApplicationsCountForAllMonthForAllTheRanges")
    public Object getApplicationsCountForAllMonthForAllTheRanges() throws JsonProcessingException {
        String sql = "SELECT \n" +
                "    d.range_name AS RangeName,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 1 THEN Application_Count END), 0) AS January,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 2 THEN Application_Count END), 0) AS February,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 3 THEN Application_Count END), 0) AS March,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 4 THEN Application_Count END), 0) AS April,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 5 THEN Application_Count END), 0) AS May,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 6 THEN Application_Count END), 0) AS June,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 7 THEN Application_Count END), 0) AS July,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 8 THEN Application_Count END), 0) AS August,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 9 THEN Application_Count END), 0) AS September,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 10 THEN Application_Count END), 0) AS October,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 11 THEN Application_Count END), 0) AS November,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 12 THEN Application_Count END), 0) AS December\n" +
                "FROM \n" +
                "    (SELECT DISTINCT range_name FROM range) d\n" +
                "CROSS JOIN \n" +
                "    (SELECT generate_series(1, 12) AS month) calendar\n" +
                "LEFT JOIN \n" +
                "    (SELECT \n" +
                "         EXTRACT(month FROM a.created_at) AS month,\n" +
                "         d.range_name,\n" +
                "         COUNT(a.fish_farmer_id) AS Application_Count\n" +
                "     FROM \n" +
                "         fish_farmer_details a\n" +
                "     JOIN \n" +
                "         range d ON  d.range_id= a.range_id\n" +
                "     GROUP BY \n" +
                "         EXTRACT(month FROM a.created_at), d.range_name) AS counts \n" +
                "ON \n" +
                "    calendar.month = counts.month AND d.range_name = counts.range_name\n" +
                "GROUP BY \n" +
                "    d.range_name\n" +
                "ORDER BY \n" +
                "    d.range_name;";

        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
    }
    @GetMapping("/getApplicationsCountForAllMonthForAllTheDistrictsYearWise/{year}")
    public Object getApplicationsCountForAllMonthForAllTheDistrictsYearWise(@PathVariable Integer year) throws JsonProcessingException {
        String sql = "SELECT \n" +
                "    d.district_name AS DistrictName,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 1 THEN Application_Count END), 0) AS January,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 2 THEN Application_Count END), 0) AS February,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 3 THEN Application_Count END), 0) AS March,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 4 THEN Application_Count END), 0) AS April,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 5 THEN Application_Count END), 0) AS May,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 6 THEN Application_Count END), 0) AS June,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 7 THEN Application_Count END), 0) AS July,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 8 THEN Application_Count END), 0) AS August,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 9 THEN Application_Count END), 0) AS September,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 10 THEN Application_Count END), 0) AS October,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 11 THEN Application_Count END), 0) AS November,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 12 THEN Application_Count END), 0) AS December\n" +
                "FROM \n" +
                "    (SELECT DISTINCT district_name FROM districts) d\n" +
                "CROSS JOIN \n" +
                "    (SELECT generate_series(1, 12) AS month) calendar\n" +
                "LEFT JOIN \n" +
                "    (SELECT \n" +
                "         EXTRACT(month FROM a.created_at) AS month,\n" +
                "         d.district_name,\n" +
                "         COUNT(a.fish_farmer_id) AS Application_Count\n" +
                "     FROM \n" +
                "         fish_farmer_details a\n" +
                "     JOIN \n" +
                "         districts d ON a.district_code = d.district_code\n" +
                "     WHERE \n" +
                "         EXTRACT(year FROM a.created_at) = "+year+"\n" +
                "     GROUP BY \n" +
                "         EXTRACT(month FROM a.created_at), d.district_name) AS counts \n" +
                "ON \n" +
                "    calendar.month = counts.month AND d.district_name = counts.district_name\n" +
                "GROUP BY \n" +
                "    d.district_name\n" +
                "ORDER BY \n" +
                "    d.district_name;";

        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
    }

    @GetMapping("/getApplicationsCountForAllMonthForAllTheDivisionsYearWise/{year}")
    public Object getApplicationsCountForAllMonthForAllTheDivisionsYearWise(@PathVariable Integer year) throws JsonProcessingException {
        String sql = "SELECT \n" +
                "    d.name AS DivisionName,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 1 THEN Application_Count END), 0) AS January,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 2 THEN Application_Count END), 0) AS February,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 3 THEN Application_Count END), 0) AS March,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 4 THEN Application_Count END), 0) AS April,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 5 THEN Application_Count END), 0) AS May,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 6 THEN Application_Count END), 0) AS June,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 7 THEN Application_Count END), 0) AS July,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 8 THEN Application_Count END), 0) AS August,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 9 THEN Application_Count END), 0) AS September,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 10 THEN Application_Count END), 0) AS October,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 11 THEN Application_Count END), 0) AS November,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 12 THEN Application_Count END), 0) AS December\n" +
                "FROM \n" +
                "    (SELECT DISTINCT name FROM division) d\n" +
                "CROSS JOIN \n" +
                "    (SELECT generate_series(1, 12) AS month) calendar\n" +
                "LEFT JOIN \n" +
                "    (SELECT \n" +
                "         EXTRACT(month FROM a.created_at) AS month,\n" +
                "         d.name,\n" +
                "         COUNT(a.fish_farmer_id) AS Application_Count\n" +
                "     FROM \n" +
                "         fish_farmer_details a\n" +
                "     JOIN \n" +
                "         division d ON  d.id= a.division_id\n" +
                "WHERE \n" +
                "         EXTRACT(year FROM a.created_at) = "+year+"\n"+
                "     GROUP BY \n" +
                "         EXTRACT(month FROM a.created_at), d.name) AS counts \n" +
                "ON \n" +
                "    calendar.month = counts.month AND d.name = counts.name\n" +
                "GROUP BY \n" +
                "    d.name\n" +
                "ORDER BY \n" +
                "    d.name;";

        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
    }

    @GetMapping("/getApplicationsCountForAllMonthForAllTheRangesYearWise/{year}")
    public Object getApplicationsCountForAllMonthForAllTheRangesYearWise(@PathVariable Integer year) throws JsonProcessingException {
        String sql = "SELECT \n" +
                "    d.range_name AS RangeName,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 1 THEN Application_Count END), 0) AS January,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 2 THEN Application_Count END), 0) AS February,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 3 THEN Application_Count END), 0) AS March,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 4 THEN Application_Count END), 0) AS April,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 5 THEN Application_Count END), 0) AS May,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 6 THEN Application_Count END), 0) AS June,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 7 THEN Application_Count END), 0) AS July,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 8 THEN Application_Count END), 0) AS August,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 9 THEN Application_Count END), 0) AS September,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 10 THEN Application_Count END), 0) AS October,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 11 THEN Application_Count END), 0) AS November,\n" +
                "    COALESCE(MAX(CASE WHEN calendar.month = 12 THEN Application_Count END), 0) AS December\n" +
                "FROM \n" +
                "    (SELECT DISTINCT range_name FROM range) d\n" +
                "CROSS JOIN \n" +
                "    (SELECT generate_series(1, 12) AS month) calendar\n" +
                "LEFT JOIN \n" +
                "    (SELECT \n" +
                "         EXTRACT(month FROM a.created_at) AS month,\n" +
                "         d.range_name,\n" +
                "         COUNT(a.fish_farmer_id) AS Application_Count\n" +
                "     FROM \n" +
                "         fish_farmer_details a\n" +
                "     JOIN \n" +
                "         range d ON  d.range_id= a.range_id\n" +
                "     WHERE \n" +
                "         EXTRACT(year FROM a.created_at) = "+year+"\n" +
                "     GROUP BY \n" +
                "         EXTRACT(month FROM a.created_at), d.range_name) AS counts \n" +
                "ON \n" +
                "    calendar.month = counts.month AND d.range_name = counts.range_name\n" +
                "GROUP BY \n" +
                "    d.range_name\n" +
                "ORDER BY \n" +
                "    d.range_name;";
        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
    }

}
