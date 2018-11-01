package com.bkash.festivalreg.domain.security;

import java.util.Set;

import javax.persistence.*;

/**
 * @author ekansh
 * @since 2/4/16
 */
@Entity
public class FolkFestAppUser {

    @Id
  //  @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "FOLK_FEST_APP_USER_SEQ")
    @SequenceGenerator(name = "FOLK_FEST_APP_USER_SEQ", initialValue = 1, allocationSize = 1)
    private long id;

    @Column(unique=true)
    private String username;

    private String password;

    private boolean enabled;
    
    
    @Column(unique=true)
    private String email;
    

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<FolkFestAppRole> roles;
    
    

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<FolkFestAppRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<FolkFestAppRole> roles) {
        this.roles = roles;
    }
}
