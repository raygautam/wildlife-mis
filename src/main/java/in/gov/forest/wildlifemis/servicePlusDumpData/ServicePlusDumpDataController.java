package in.gov.forest.wildlifemis.servicePlusDumpData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServicePlusDumpDataController {
    @Autowired
    ServicePlusDumpDataServiceInter servicePlusDumpDataServiceInter;

    @PostMapping("/servicePlusDumpData")
    public void addDumpData(Object dumpData) {
        servicePlusDumpDataServiceInter.addDumpData(dumpData);
    }
}
