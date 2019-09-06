package ch.hevs.swap.ui.search;

import java.util.ArrayList;

public class Queue {
        private int length ;
        private Knot first ;
        private Knot last ;

        //constructor
        public Queue()
        {
            length = 0 ;
            first = null ;
            last = null ;
        }

        // getters
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

        // setters
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

        // methods

        public boolean isEmpty()
        {
            return (length == 0) ;
        } //check if empty (no knots)

        // add new knot to the queue
        public void file(Knot newKnot)
        {
            if (isEmpty())
                first = newKnot;
            else
                last.setNext(newKnot) ;

            last = newKnot ;

            ++length ;
        }

        // print the content of the queue
        public void print() {

            Knot current = first;
            while(current != null)
            {
                System.out.print(current.getInfo().getValeur() + " ");
                current = current.getNext();
            }
            System.out.println();
        }

        // delete knot
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

        public void fillQueue(ArrayList<String> appartKeys) {

            for(int i = 0; i<appartKeys.size(); i++)
            {
                this.file(new Knot(new Info(appartKeys.get(i))));
                System.out.println(appartKeys.get(i));
            }
        }

    }
