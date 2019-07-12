package ch.hevs.swap.ui.data.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String name;
    public String firstname;
    public String email;
    public String tel;

    public User(String name, String firstname, String email, String tel) {
        this.name = name;
        this.firstname = firstname;
        this.email = email;
        this.tel = tel;
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email) {
        this.email = email;
    }
}
