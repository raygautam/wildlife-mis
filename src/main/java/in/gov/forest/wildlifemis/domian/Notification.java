package in.gov.forest.wildlifemis.domian;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_gen")
    @SequenceGenerator(name = "notification_gen", sequenceName = "notification_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private String title;
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private NotificationType notificationType;
    private String fileUrl;

    //or @CreationTimestamp (provided by Hibernate)
//    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private Boolean isActive;

//    private Boolean archive;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Notification that = (Notification) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
