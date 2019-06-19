package edu.edgetech.sb2.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 *  TODO    if you are looking for examples of formatting a table this is your place
 *          @Column     names the column in the actual database table.
 *                      The Java attribute name will just be the name of the class attribute
 *          @NotNull    Must enter something when creating this object
 *          @Email      Do you want to write the validation code for an email address? I don't
 *          @Length     Require at least this many (min=) characters and no more than (max=) this many
 *          @ManyToMany These two annotations will connect this table to another table in the database
 *          @JoinTable
 */
@Entity
@Table(name = "auth_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_user_id")
    private int id;

    @NotNull(message="First name is required")
    @Column(name = "first_name")
    private String name;

    @NotNull(message="Last name is required")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message="Email is required")
    @Email(message = "Email is invalid")
    @Column(name = "email")
    private String email;

    @NotNull(message="Password is compulsory")
    @Length(min=5, message="Password should be at least 5 characters")
    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    //  TODO for all of our entities this is the most interesting feature. A user can have MANY roles.
    //      the record in the database will not have a field for roles. Instead a associating table exists to resolve the Many to Many relationship
    //      when the User record is read this attribute will be a Set of roles owned my the user
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "auth_user_role", joinColumns = @JoinColumn(name = "auth_user_id"), inverseJoinColumns = @JoinColumn(name = "auth_role_id"))
    private Set<Role> roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
