package ru.javawebinar.topjava.model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

@Entity
@Table(name = "USERS", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "unique_email")})
@NamedQueries({
		@NamedQuery(name = User.DELETE, query = "DELETE FROM User u where u.id=:id"),
		@NamedQuery(name = User.BY_EMAIL, query = "SELECT u FROM User u LEFT JOIN FETCH u.roles where u.email=:email"),
		@NamedQuery(name = User.ALL_SORTED, query = "SELECT u FROM User u LEFT JOIN FETCH u.roles ORDER BY u.name, u.email")
})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractNamedEntity {

	public static final String DELETE = "User.delete";
    public static final String BY_EMAIL = "User.getByEmail";
    public static final String ALL_SORTED = "User.getAllSorted";

    @Column(name="email", nullable = false, unique = true)
	@Email
	@NotBlank
	@Size(max = 128)
	private String email;

	@Column(name = "password", nullable = false, unique = true)
	@NotEmpty
	@Length(min = 5)
	private String password;

	@Column(name = "enabled", nullable = false)
	private boolean enabled = true;

	@Column(name = "registered", columnDefinition = "timestamp default now()")
	private Date registered = new Date();

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	@BatchSize(size = 200)
	@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Role> roles;

	@Column(name = "calories_per_day", nullable = false, columnDefinition = "integer default 2000")
	@Range(min = 10, max = 10000)
	private int caloriesPerDay = DEFAULT_CALORIES_PER_DAY;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	@OrderBy("dateTime DESC")
	private List<Meal> meals;

	public User() {
	}

	public User(User user){
		this(user.getId(), user.getName(), user.getEmail(), user.getPassword(),
				user.getCaloriesPerDay(), user.isEnabled(), user.registered, user.getRoles());
	}

	public User(Integer id, String name, String email, String password, Role... roles) {
		   this(id, name, email, password, DEFAULT_CALORIES_PER_DAY, true, new Date(), Arrays.asList(roles));
	}

	public User(Integer id, String name, String email, String password, int caloriesPerDay, boolean enabled, Date registered,
				Collection<Role> roles) {
		super(id, name);
		this.email = email;
		this.password = password;
		this.caloriesPerDay = caloriesPerDay;
		this.registered = registered;
		this.enabled = enabled;
		setRoles(roles);
	}

	public void setRoles(Collection<Role> roles) {
	    this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegistered() {
		return registered;
	}

	public void setRegistered(Date registered) {
		this.registered = registered;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getCaloriesPerDay() {
		return caloriesPerDay;
	}

	public void setCaloriesPerDay(int caloriesPerDay) {
		this.caloriesPerDay = caloriesPerDay;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public String getPassword() {
		return password;
	}

	public List<Meal> getMeals() {
		return meals;
	}

	@Override
	public String toString() {
		return "User {" +
				"id=" + id +
				", email=" + email +
				", name=" + name +
				", enabled=" + enabled +
				", roles=" + roles +
				", caloriesPerDay=" + caloriesPerDay +
				'}';
	}
}