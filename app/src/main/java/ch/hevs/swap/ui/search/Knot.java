package ch.hevs.swap.ui.search;


public class Knot {

    private Info info ;
    private Knot next ;

    // constructeurs
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

    // getteurs

    public Knot getNext()
    {
        return next ;
    }

    public Info getInfo()
    {
        return info ;
    }

    // setteurs

    public void setNext(Knot nouveau)
    {
        next = nouveau ;
    }

    //utile lors d'�changes de Knots, donc d'infos
    public void setInfo(Info nouveau)
    {
        info = nouveau ;
    }

}

