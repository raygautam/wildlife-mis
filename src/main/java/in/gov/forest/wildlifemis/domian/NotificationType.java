package in.gov.forest.wildlifemis.domian;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_type_gen")
    @SequenceGenerator(name = "notification_type_gen", sequenceName = "notification_type_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        NotificationType that = (NotificationType) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public enum Notification_Type{
         Advertisements_and_Results, Notifications, Tenders, Notices,
    }
}
