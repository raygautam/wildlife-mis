package in.gov.forest.wildlifemis.forestServicePlusTesting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.exception.Error;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/public/application")
@CrossOrigin("*")
public class TestingController {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @GetMapping("/testing")
    public Object testing() throws JsonProcessingException {
        String sql = "SELECT * FROM fish_farmer_details WHERE fish_farmer_id = 'ML-FFIC/2023/00001'";
        // Retrieve the result of the SQL query
        ObjectMapper objectMapper=new ObjectMapper();
        Map<String, Object> result = jdbcTemplate.queryForMap(sql);
        // Convert the result to YourObject using ObjectMapper
        return objectMapper.convertValue(result, Object.class);
    }

    @GetMapping("/districtCount")
    public List<?> getDistrictWiseApplicationCount() throws JsonProcessingException {
//        String sql = "SELECT district_name, count(fish_farmer_id) as application_count FROM fish_farmer_details ffd " +
//                "Inner join districts d on d.district_code=ffd.district_code " +
//                "GROUP BY district_name";

        String sql = "SELECT d.district_code, d.district_name, COALESCE(count(fish_farmer_id)) as application_count FROM districts d\n" +
                " Left join fish_farmer_details ffd  on ffd.district_code=d.district_code\n" +
                " GROUP BY d.district_name, d.district_code";
        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
//        return objectMapper.convertValue(, Object[].class);
    }

    @GetMapping("/divisionCount")
    public Object getDivisionWiseApplicationCount() throws JsonProcessingException {
//        String sql = "SELECT d.name, count(fish_farmer_id) as application_count FROM fish_farmer_details ffd\n" +
//                "Inner join division d on d.id=ffd.division_id\n" +
//                "GROUP BY d.name";
        String sql = "SELECT d.id, d.name, COALESCE(count(fish_farmer_id), 0) as application_count FROM division d\n" +
                "Left join fish_farmer_details ffd on ffd.division_id=d.id\n" +
                "GROUP BY d.name, d.id\n" +
                "Order By d.name";
        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
    }
    @GetMapping("/rangeCount")
    public Object getRangeWiseApplicationCount() throws JsonProcessingException {
//        String sql = "SELECT d.range_name, count(fish_farmer_id) as application_count FROM fish_farmer_details ffd\n" +
//                "Inner join range d on d.range_id=ffd.range_id\n" +
//                "GROUP BY d.range_name";

        String sql="SELECT d.range_id, d.range_name, COALESCE(count(ffd.range_id), 0) as application_count\n" +
                "FROM range d\n" +
                "LEFT JOIN fish_farmer_details ffd on ffd.range_id=d.range_id\n" +
                "GROUP BY d.range_name, d.range_id\n" +
                "ORDER BY d.range_name";

        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
    }
//    @SuppressWarnings("unchecked")
    @GetMapping("/division/{id}")
    public Object getAllApplicationsByDivisionId(@PathVariable Long id) throws JsonProcessingException {
//        String sql = "SELECT * FROM fish_farmer_details ffd\n" +
//                "Where ffd.division_id="+id;
//
//        return jdbcTemplate.queryForList(sql);

//        try {
//            String sql = "SELECT * FROM fish_farmer_details ffd WHERE ffd.division_id = ?";
////            Query query = entityManager.createNativeQuery(sql)
////                    .setParameter("id", id);
////            List<Map<String,Object>> result=query.getResultList();
//
//            return ApiResponse.builder()
//                    .status(HttpStatus.OK.value())
//                    .data(jdbcTemplate.queryForList(sql,id))
//                    .build();
//        }catch (Exception e) {
//            return ApiResponse.builder()
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                    .error(List.of(new Error("","INTERNAL_SERVER_ERROR "+e.getMessage())))
//                    .build();
//        }

        String sql = "SELECT * FROM fish_farmer_details ffd WHERE ffd.division_id = ?";
        return jdbcTemplate.queryForList(sql,id);


    }
    @GetMapping("/range/{id}")
    public Object getAllApplicationsByRangeId(@PathVariable Long id) throws JsonProcessingException {
        String sql = "SELECT * FROM fish_farmer_details ffd\n" +
                "Where ffd.range_id = ? ";

        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql,id);
    }

    @GetMapping("/district/{id}")
    public Object getAllApplicationsByDistrictCode(@PathVariable Long id) throws JsonProcessingException {
        String sql = "SELECT * FROM fish_farmer_details ffd\n" +
                "Where ffd.district_code= ?";

        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql, id);
    }


    @GetMapping("/districtMonthWiseCount")
    public Object getApplicationsCountForAllMonthForAllTheDistrict() throws JsonProcessingException {
        String sql = "SELECT \n" +
                "    COALESCE(d.district_name, 'Total') AS DistrictName,\n" +
                "    d.district_code AS DistrictId,\n" +
                "    SUM(CASE WHEN calendar.month = 1 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS January,\n" +
                "    SUM(CASE WHEN calendar.month = 2 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS February,\n" +
                "    SUM(CASE WHEN calendar.month = 3 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS March,\n" +
                "    SUM(CASE WHEN calendar.month = 4 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS April,\n" +
                "    SUM(CASE WHEN calendar.month = 5 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS May,\n" +
                "    SUM(CASE WHEN calendar.month = 6 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS June,\n" +
                "    SUM(CASE WHEN calendar.month = 7 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS July,\n" +
                "    SUM(CASE WHEN calendar.month = 8 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS August,\n" +
                "    SUM(CASE WHEN calendar.month = 9 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS September,\n" +
                "    SUM(CASE WHEN calendar.month = 10 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS October,\n" +
                "    SUM(CASE WHEN calendar.month = 11 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS November,\n" +
                "    SUM(CASE WHEN calendar.month = 12 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS December,\n" +
                "    SUM(COALESCE(Application_Count, 0)) AS Total\n" +
                "FROM \n" +
                "    (SELECT DISTINCT district_code, district_name FROM districts) d\n" +
                "CROSS JOIN \n" +
                "    (SELECT generate_series(1, 12) AS month) calendar\n" +
                "LEFT JOIN \n" +
                "    (SELECT \n" +
                "        EXTRACT(month FROM a.created_at) AS month,\n" +
                "        d.district_name,\n" +
                "        COUNT(a.fish_farmer_id) AS Application_Count\n" +
                "     FROM \n" +
                "        fish_farmer_details a\n" +
                "     JOIN \n" +
                "        districts d ON a.district_code = d.district_code\n" +
                "     GROUP BY \n" +
                "        EXTRACT(month FROM a.created_at), d.district_name) AS counts \n" +
                "ON \n" +
                "    calendar.month = counts.month AND d.district_name = counts.district_name\n" +
                "GROUP BY \n" +
                "    GROUPING SETS ((d.district_code, d.district_name), ())\n" +
                "ORDER BY \n" +
                "    d.district_name;";

        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
    }


    @GetMapping("/divisionMonthWiseCount")
    public Object getApplicationsCountForAllMonthForAllTheDivisions() throws JsonProcessingException {
        String sql = "SELECT \n" +
                "    COALESCE(d.name, 'Total') AS DivisionName, \n" +
                "    d.id AS DivisionID,\n" +
                "    SUM(CASE WHEN calendar.month = 1 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS January, \n" +
                "    SUM(CASE WHEN calendar.month = 2 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS February, \n" +
                "    SUM(CASE WHEN calendar.month = 3 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS March, \n" +
                "    SUM(CASE WHEN calendar.month = 4 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS April, \n" +
                "    SUM(CASE WHEN calendar.month = 5 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS May, \n" +
                "    SUM(CASE WHEN calendar.month = 6 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS June, \n" +
                "    SUM(CASE WHEN calendar.month = 7 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS July, \n" +
                "    SUM(CASE WHEN calendar.month = 8 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS August, \n" +
                "    SUM(CASE WHEN calendar.month = 9 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS September, \n" +
                "    SUM(CASE WHEN calendar.month = 10 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS October, \n" +
                "    SUM(CASE WHEN calendar.month = 11 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS November, \n" +
                "    SUM(CASE WHEN calendar.month = 12 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS December, \n" +
                "    SUM(COALESCE(Application_Count, 0)) AS Total \n" +
                "FROM \n" +
                "    (SELECT DISTINCT id, name FROM division) d \n" +
                "CROSS JOIN \n" +
                "    (SELECT generate_series(1, 12) AS month) calendar \n" +
                "LEFT JOIN \n" +
                "    (SELECT EXTRACT(month FROM a.created_at) AS month, d.name, COUNT(a.fish_farmer_id) AS Application_Count \n" +
                "     FROM fish_farmer_details a \n" +
                "     JOIN division d ON d.id= a.division_id \n" +
                "     GROUP BY EXTRACT(month FROM a.created_at), d.name) AS counts \n" +
                "ON \n" +
                "    calendar.month = counts.month AND d.name = counts.name \n" +
                "GROUP BY \n" +
                "    GROUPING SETS ((d.id, d.name), ()) \n" +
                "ORDER BY \n" +
                "    d.name;";

        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
    }

    @GetMapping("/rangeMonthWiseCount")
    public Object getApplicationsCountForAllMonthForAllTheRanges() throws JsonProcessingException {
        String sql = "SELECT \n" +
                "COALESCE(d.range_name, 'Total') AS RangeName,\n" +
                "d.range_id AS RangeId,\n" +
                "    SUM(CASE WHEN calendar.month = 1 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS January,\n" +
                "    SUM(CASE WHEN calendar.month = 2 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS February,\n" +
                "    SUM(CASE WHEN calendar.month = 3 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS March,\n" +
                "    SUM(CASE WHEN calendar.month = 4 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS April,\n" +
                "    SUM(CASE WHEN calendar.month = 5 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS May,\n" +
                "    SUM(CASE WHEN calendar.month = 6 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS June,\n" +
                "    SUM(CASE WHEN calendar.month = 7 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS July,\n" +
                "    SUM(CASE WHEN calendar.month = 8 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS August,\n" +
                "    SUM(CASE WHEN calendar.month = 9 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS September,\n" +
                "    SUM(CASE WHEN calendar.month = 10 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS October,\n" +
                "    SUM(CASE WHEN calendar.month = 11 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS November,\n" +
                "    SUM(CASE WHEN calendar.month = 12 THEN COALESCE(Application_Count, 0) ELSE 0 END) AS December,\n" +
                "    SUM(COALESCE(Application_Count, 0)) AS Total\n" +
                "FROM \n" +
                "    (SELECT DISTINCT range_id, range_name FROM range) d\n" +
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
                "    GROUPING SETS ((d.range_id, d.range_name), ()) \n" +
                "ORDER BY \n" +
                "    d.range_name;";

        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql);
    }
    @GetMapping("/districtMonthWiseCount/{year}")
    public Object getApplicationsCountForAllMonthForAllTheDistrictsYearWise(@PathVariable Integer year) throws JsonProcessingException {
        String sql = "SELECT \n" +
                "    COALESCE(d.district_name, 'Total') AS DistrictName,\n" +
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
                "\t,SUM(COALESCE(Application_Count, 0)) AS Total\n" +
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
                "         EXTRACT(year FROM a.created_at) = ? \n" +
                "     GROUP BY \n" +
                "         EXTRACT(month FROM a.created_at), d.district_name) AS counts \n" +
                "ON \n" +
                "    calendar.month = counts.month AND d.district_name = counts.district_name\n" +
                "GROUP BY \n" +
                "    GROUPING SETS ((d.district_name), ())\n" +
                "ORDER BY \n" +
                "    d.district_name;";

        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql, year);
    }

    @GetMapping("/divisionMonthWiseCount/{year}")
    public Object getApplicationsCountForAllMonthForAllTheDivisionsYearWise(@PathVariable Integer year) throws JsonProcessingException {
        String sql = "SELECT \n" +
                "    COALESCE(d.name, 'Total') AS DivisionName,\n" +
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
                "    COALESCE(MAX(CASE WHEN calendar.month = 12 THEN Application_Count END), 0) AS December,\n" +
                "    SUM(COALESCE(Application_Count, 0)) AS Total\n" +
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
                "         EXTRACT(year FROM a.created_at) = ?\n"+
                "     GROUP BY \n" +
                "         EXTRACT(month FROM a.created_at), d.name) AS counts \n" +
                "ON \n" +
                "    calendar.month = counts.month AND d.name = counts.name\n" +
                "GROUP BY \n" +
                "    GROUPING SETS ((d.name), ())\n" +
                "ORDER BY \n" +
                "    d.name;";

        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql, year);
    }

    @GetMapping("/rangeMonthWiseCount/{year}")
    public Object getApplicationsCountForAllMonthForAllTheRangesYearWise(@PathVariable Integer year) throws JsonProcessingException {
        String sql = "SELECT \n" +
                "    COALESCE(d.range_name, 'Total') AS RangeName,\n" +
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
                "    COALESCE(MAX(CASE WHEN calendar.month = 12 THEN Application_Count END), 0) AS December,\n" +
                "    SUM(COALESCE(Application_Count, 0)) AS Total\n" +
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
                "         EXTRACT(year FROM a.created_at) = ?\n" +
                "     GROUP BY \n" +
                "         EXTRACT(month FROM a.created_at), d.range_name) AS counts \n" +
                "ON \n" +
                "    calendar.month = counts.month AND d.range_name = counts.range_name\n" +
                "GROUP BY \n" +
                "    GROUPING SETS ((d.range_name), ())\n" +
                "ORDER BY \n" +
                "    d.range_name;";
        // Retrieve the result of the SQL query
//        ObjectMapper objectMapper=new ObjectMapper();
        // Convert the result to YourObject using ObjectMapper
        return jdbcTemplate.queryForList(sql, year);
    }

}
