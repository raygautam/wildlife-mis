package in.gov.forest.wildlifemis.service.service;

public class ServiceReturnDTO {
    private Long id;
    private String serviceName;

    public ServiceReturnDTO() {
    }

    public ServiceReturnDTO(Long id, String serviceName) {
        this.id = id;
        this.serviceName = serviceName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
