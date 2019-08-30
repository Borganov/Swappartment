package ch.hevs.swap.ui.data.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

import ch.hevs.swap.data.models.Appart;

@IgnoreExtraProperties
public class User {
    public String name;
    public String firstname;
    public String email;
    public String tel;
    ArrayList<Appart> apparts;
    ArrayList<Appart> likeapparts;
    ArrayList<Appart> dislikeapparts;

    public User(String name, String firstname, String email, String tel) {
        this.name = name;
        this.firstname = firstname;
        this.email = email;
        this.tel = tel;
    }
    public User(String name, String firstname, String email, String tel, ArrayList<Appart> apparts,ArrayList<Appart> likeapparts,ArrayList<Appart> dislikeapparts ) {
        this.name = name;
        this.firstname = firstname;
        this.email = email;
        this.tel = tel;
        this.apparts =apparts;
        this.dislikeapparts = dislikeapparts;
        this.likeapparts = likeapparts;
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email) {
        this.email = email;
    }
}
