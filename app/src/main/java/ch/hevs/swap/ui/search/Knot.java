package ch.hevs.swap.ui.search;


public class Knot {

    private Info info ;
    private Knot next ;

    // constructor
    public Knot()
    {
        info = null ;
        next = null ;
    }

    public Knot(Knot n)
    {
        info = n.info ;
        next = n.next ;
    }



    public Knot(Info info)
    {
        this.info = info ;
        next = null ;
    }

    // getters

    public Knot getNext()
    {
        return next ;
    }

    public Info getInfo()
    {
        return info ;
    }

    // setters

    public void setNext(Knot nouveau)
    {
        next = nouveau ;
    }

    public void setInfo(Info nouveau)
    {
        info = nouveau ;
    }

}

