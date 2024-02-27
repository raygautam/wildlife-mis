//package in.gov.forest.wildlifemis.incidentReport;
//
//import in.gov.forest.wildlifemis.lgdEntities.repository.DistrictRepository;
//import in.gov.forest.wildlifemis.lgdEntities.repository.OperatedBlockRepository;
//import in.gov.forest.wildlifemis.lgdEntities.repository.RangeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//public class IncidentReportInitialization {
//
//    @Autowired
//    DistrictRepository districtRepository;
//    @Autowired
//    OperatedBlockRepository operatedBlockRepository;
//    @Autowired
//    RangeRepository rangeRepository;
//
//
//    public IncidentReportInitialization() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//
////        List<IncidentReport> incidentReports = new ArrayList<>();
//
//        // Parsing dates
//        Date incidentReportedDate1 = null;
//        Date reportedDate1 = null;
//        Date incidentReportedDate2 = null;
//        Date reportedDate2 = null;
//        try {
//            incidentReportedDate1 = dateFormat.parse("20-09-2023");
//            reportedDate1 = dateFormat.parse("20-09-2023");
//            incidentReportedDate2 = dateFormat.parse("30-10-2023");
//            reportedDate2 = dateFormat.parse("01-11-2023");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        List<IncidentReport> incidentReports= Arrays.asList(
//                IncidentReport.builder()
//                        .description("dv efe efe")
//                        .fullAddress("vsvsvvs")
//                        .incidentReportedDate(incidentReportedDate1)
//                        .incidentType("MAN ANIMAL CONFLICT")
//                        .informerPhoneNo("9301204300")
//                        .reportedDate(reportedDate1)
//                        .district(districtRepository.getReferenceById(274L))
//                        .operatedBlock(41)
//                        .rangeId(2360270)
//                        .build(),
//                IncidentReport.builder()
//                        .description("dv efe efe")
//                        .fullAddress("vsvsvvs")
//                        .incidentReportedDate(incidentReportedDate1)
//                        .incidentType("HUMAN ANIMAL CONFLICT")
//                        .informerPhoneNo("9301204300")
//                        .reportedDate(reportedDate1)
//                        .districtId(274)
//                        .operatedBlockId(41)
//                        .rangeId(2360270)
//                        .build(),
//                IncidentReport.builder()
//                        .description("dv efe efe")
//                        .fullAddress("vsvsvvs")
//                        .incidentReportedDate(incidentReportedDate1)
//                        .incidentType("HUMAN WILDLIFE CONFLICT")
//                        .informerPhoneNo("9301204300")
//                        .reportedDate(reportedDate1)
//                        .districtId(274)
//                        .operatedBlockId(41)
//                        .rangeId(2360270)
//                        .build(),
//                IncidentReport.builder()
//                        .description("dv efe efe")
//                        .fullAddress("vsvsvvs")
//                        .incidentReportedDate(incidentReportedDate1)
//                        .incidentType("HUMAN WILDLIFE CONFLICT")
//                        .informerPhoneNo("7005852846")
//                        .reportedDate(reportedDate1)
//                        .districtId(274)
//                        .operatedBlockId(41)
//                        .rangeId(2360270)
//                        .build(),
//                IncidentReport.builder()
//                        .description("dv efe efe")
//                        .fullAddress("vsvsvvs")
//                        .incidentReportedDate(incidentReportedDate1)
//                        .incidentType("HUMAN WILDLIFE CONFLICT")
//                        .informerPhoneNo("7005852846")
//                        .reportedDate(reportedDate1)
//                        .districtId(274)
//                        .operatedBlockId(41)
//                        .rangeId(2360270)
//                        .build(),
//                IncidentReport.builder()
//                        .description("dv efe efe")
//                        .fullAddress("vsvsvvs")
//                        .incidentReportedDate(incidentReportedDate1)
//                        .incidentType("HUMAN WILDLIFE CONFLICT")
//                        .informerPhoneNo("7005852846")
//                        .reportedDate(reportedDate1)
//                        .districtId(274)
//                        .operatedBlockId(41)
//                        .rangeId(2360270)
//                        .build(),
//                IncidentReport.builder()
//                        .description("dv efe efe")
//                        .fullAddress("vsvsvvs")
//                        .incidentReportedDate(incidentReportedDate1)
//                        .incidentType("HUMAN WILDLIFE CONFLICT")
//                        .informerPhoneNo("7005852846")
//                        .reportedDate(reportedDate1)
//                        .districtId(274)
//                        .operatedBlockId(41)
//                        .rangeId(2360270)
//                        .build(),
//                IncidentReport.builder()
//                        .description("dv efe efe")
//                        .fullAddress("vsvsvvs")
//                        .incidentReportedDate(incidentReportedDate2)
//                        .incidentType("HUMAN WILDLIFE CONFLICT")
//                        .informerPhoneNo("7005852846")
//                        .reportedDate(reportedDate2)
//                        .districtId(274)
//                        .operatedBlockId(41)
//                        .rangeId(2360270)
//                        .build()
//
//        );
//
//
//    }
//
//}
