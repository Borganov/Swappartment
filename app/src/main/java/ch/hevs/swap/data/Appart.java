package ch.hevs.swap.data;

public class Appart {
    String designation;
    int nbRooms;
    double price;
    String address;
    //String idUser;

    public Appart(String designation, int nbRooms, double price, String address) {
        this.designation = designation;
        this.nbRooms = nbRooms;
        this.price = price;
        this.address = address;
        //this.idUser = idUser;
    }

    public Appart() {

    }
}
