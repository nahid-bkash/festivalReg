package com.bkash.festivalreg.domain.security;


import javax.persistence.*;
import java.util.Set;

/**
 * @author ekansh
 * @since 2/4/16
 */
@Entity
public class FolkFestAppRole {

    @Id
   // @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "FOLK_FEST_APP_ROLE_SEQ")
    @SequenceGenerator(name = "FOLK_FEST_APP_ROLE_SEQ", initialValue = 1, allocationSize = 1)
    private long id;

    @Column(unique=true)
    private String role;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private Set<FolkFestAppUser> users;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<FolkFestAppUser> getUsers() {
        return users;
    }

    public void setUsers(Set<FolkFestAppUser> users) {
        this.users = users;
    }
}
