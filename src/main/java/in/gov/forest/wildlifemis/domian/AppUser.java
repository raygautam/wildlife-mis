package in.gov.forest.wildlifemis.domian;

import in.gov.forest.wildlifemis.lgdEntities.entities.Division;
import in.gov.forest.wildlifemis.lgdEntities.entities.Range;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "userName"), schema = "public")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private String password;
    private String name;
    private Boolean isActive; //default value true
    @Column(name = "account_non_locked")
    private Boolean accountNonLocked; ////default value true

    @Column(name = "failed_attempt")
    private Integer failedAttempt; //default value 0

    @Column(name = "lock_time")
    private Date lockTime; // null

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id", nullable = true)
    private Service service;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "division_id", nullable = true)
    private Division division;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "range_id", nullable = true)
    private Range range;


    //    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
//    private Set<Role> roles;




//    @PrePersist
//    public void onCreate() {
//        Calendar cal = Calendar.getInstance();
//        Date date = new Date();
//        this.setCreatedDate(date);
//        this.setUpdatedDate(date);
//    }
//
//    @PreUpdate
//    public void onUpdate() {
//        Calendar cal = Calendar.getInstance();
//        Date date = new Date();
//        this.setUpdatedDate(date);
//    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        AppUser user = (AppUser) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
