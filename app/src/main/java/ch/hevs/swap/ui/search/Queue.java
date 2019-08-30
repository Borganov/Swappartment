package ch.hevs.swap.ui.search;

public class Queue {
        private int length ;
        private Knot first ;
        private Knot last ;

        //constructeur
        public Queue()
        {
            length = 0 ;
            first = null ;
            last = null ;
        }

        // getteurs
        public int getLength()
        {
            return length ;
        }

        public Knot getFirst()
        {
            return first ;
        }

        public Knot getLast()
        {
            return last ;
        }

        // setteurs
        public void setLength(int length)
        {
            this.length = length ;
        }

        public void setFirst(Knot first)
        {
            this.first = first ;
        }

        public void setLast(Knot last)
        {
            this.last = last;
        }

        // m�thodes

        public boolean isEmpty()
        {
            return (length == 0) ;
        }

        public void file(Knot newKnot)
        {
            if (isEmpty())
                first = newKnot;
            else
                last.setNext(newKnot) ;

            last = newKnot ;

            ++length ;
        }

        public void print() {

            Knot current = first;
            while(current != null)
            {
                System.out.print(current.getInfo().getValeur() + " ");
                current = current.getNext();
            }
            System.out.println();
        }

        public Knot defile()
        {
            if (length == 0)
                return null ;

            Knot debut = first ;
            first = first.getNext() ;

            if (length == 1)
                last = null ;
            else
                debut.setNext(null) ;

            --length ;
            return debut ;
        }

        public void concat(Queue q2) {

            Knot current = q2.getFirst();

            while(current != null)
            {
                this.file(current);
                current = current.getNext();
            }

            q2.setLength(0);
            q2.setFirst(null);
            q2.setLast(null);
        }

        public Knot defileElement(int n)
        {
            if (n<= 0 || n>length)
                return null ;

            Queue travail = new Queue() ;

            for (int i=1 ; i<n ; i++)
                travail.file(defile());

            Knot retour = defile() ;

            travail.concat(this);
            concat(travail) ;
            return retour ;
        }

        public Knot deleteElement(int x) {
            Knot current = first;

            if(current.getInfo().getValeur() == x)
            {
                defile();
                return current;
            }

            while(current != null)
            {
                Knot currentNext = current.getNext();
                if(currentNext.getInfo().getValeur() == x)
                {
                    if(currentNext.getNext() != null)
                        current.setNext(currentNext.getNext());
                    else
                        current.setNext(null);

                    return current;
                }
                current = current.getNext();
            }

            return null;
        }

        // �change de 2 Knots r�f�renc�s dans une file:
        // on �change les r�f�rences des infos.
        public void echange(Knot Knot1, Knot Knot2)
        {
            Info temp = Knot1.getInfo() ;
            Knot1.setInfo(Knot2.getInfo());
            Knot2.setInfo(temp);
        }


        public String toString()
        {
            String chaine = "" ;
            Knot courant = first ;

            while (courant != null)
            {
                chaine += courant.getInfo().toString() + " ";
                courant = courant.getNext() ;
            }
            return chaine ;
        }

    }
