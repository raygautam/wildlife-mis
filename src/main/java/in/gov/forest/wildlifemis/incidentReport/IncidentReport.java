package in.gov.forest.wildlifemis.incidentReport;

import in.gov.forest.wildlifemis.lgdEntities.entities.District;
import in.gov.forest.wildlifemis.lgdEntities.entities.OperatedBlock;
import in.gov.forest.wildlifemis.lgdEntities.entities.Range;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncidentReport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incident_report_gen")
    @SequenceGenerator(name = "incident_report_gen", sequenceName = "incident_report_seq", allocationSize = 1)
    @Column(name = "incident_report_id", nullable = false)
    private Long incidentReportId;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date incidentReportedDate;

    @Temporal(TemporalType.DATE)
    private Date reportedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operated_block_id", nullable = false)
    private OperatedBlock operatedBlock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "range_id", nullable = false)
    private Range range;

    @Column(nullable = false)
    private String incidentType;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String fullAddress;

    @Column(nullable = false)
    private String informerPhoneNo;

    @Column(nullable = true)
    private String alternatePhoneNo;

//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "incidentReport")
//    @JsonManagedReference
//    private Set<IncidentImage> incidentImage;


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        IncidentReport that = (IncidentReport) o;
        return getIncidentReportId() != null && Objects.equals(getIncidentReportId(), that.getIncidentReportId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
