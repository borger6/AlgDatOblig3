package no.oslomet.cs.algdat.Oblig3;

////////////////// ObligSBinTre /////////////////////////////////

import java.util.*;

public class ObligSBinTre<T> implements Beholder<T>
{
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder)
        {
            this.verdi = verdi;
            venstre = v; høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString(){ return "" + verdi;}

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public ObligSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    @Override
    public boolean leggInn(T verdi)
    {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier");

        Node<T> p = rot, q = null;
        int cmp = 0;

        while (p != null){
            q = p;
            cmp = comp.compare(verdi,p.verdi);
            p = cmp < 0 ? p.venstre : p.høyre;
        }
        p = new Node<>(verdi, null, null, q);

        if (q == null){
            rot = p;
        }
        else if (cmp < 0){
            q.venstre = p;
        }
        else {
            q.høyre = p;
        }
        antall++;
        endringer++;
        return true;
    }

    @Override
    public boolean inneholder(T verdi)
    {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null)
        {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    @Override
    public boolean fjern(T verdi){

        if (verdi == null){
            return false;
        }
        Node<T> p = rot;
        Node<T> q = null;

        while(p != null){
            int cmp = comp.compare(verdi,p.verdi);
            if(cmp < 0){
                q = p;
                p = p.venstre;
            }
            else if(cmp > 0){
                q = p;
                p = p.høyre;
            }
            else{
                break;
            }
        }
        if(p==null){
            return false;
        }
        Node<T> b;
        if(p.venstre == null || p.høyre == null){
            if(p.venstre != null){
                b = p.venstre;
            }
            else{
                b = p.høyre;
            }
            if(b != null){
                b.forelder = q;
            }
            if(p == rot){
                rot = b;
            }
            else if(p == q.venstre){
                q.venstre = b;
            }
            else{
                q.høyre = b;
            }

        }
        else{
            Node <T> s = p;
            Node <T> r = p.høyre;
            while(r.venstre != null){
                s = r;
                r = r.venstre;

            }
            p.verdi = r.verdi;
            if(s != p){
                s.venstre = r.høyre;
            }
            else{
                s.høyre = r.høyre;
            }
        }
        antall--;
        endringer++;
        return true;
    }

    public int fjernAlle(T verdi) {
        int antallfjernet = 0;
        while(fjern(verdi)){
            antallfjernet++;
        }

        return antallfjernet;
    }

    @Override
    public int antall()
    {
        return antall;
    }

    public int antall(T verdi)
    {
        if (tom()){
            return 0;
        }
        int telle = 0;
        int cmp = 0;
        Kø<Node<T>> kø = new TabellKø<>();
        kø.leggInn(rot);

        while (!kø.tom()){
            if (inneholder(verdi)){
                Node<T> p = kø.taUt();

                cmp = comp.compare(verdi, p.verdi);
                if (cmp == 0){
                    telle++;
                }
                if (p.venstre != null) kø.leggInn(p.venstre);
                if (p.høyre != null) kø.leggInn(p.høyre);
            }
            else {
                return 0;
            }
        }

        return telle;
    }

    @Override
    public boolean tom()
    {
        return antall == 0;
    }

    @Override
    public void nullstill()
    {
        if(!tom()){
            nullstill(rot);
            rot = null;
            endringer = antall();
            antall = 0;
        }
    }
    public static void nullstill(Node p){
        if (p == null){
            return;
        }
        else {
            nullstill(p.venstre);
            nullstill(p.høyre);
            p = null;
        }
    }

    private static <T> Node<T> førstePostorden(Node<T> p){
        while (true){
            if (p.venstre != null){
                p = p.venstre;
            }
            else if (p.høyre != null){
                p = p.høyre;
            }
            else {
                break;
            }
        }
        return p;
    }

    private static <T> Node<T> nestePostorden(Node<T> p){
        if (p.forelder != null && p == p.forelder.venstre) {
            if (p.forelder != null && p.forelder.høyre == null) {
                p = p.forelder;
            }
            else {
                p = p.forelder.høyre;
                while (true) {
                    if (p.venstre != null) {
                        p = p.venstre;
                    }
                    else if (p.høyre != null) {
                        p = p.høyre;
                    }
                    else break;
                }
            }
        }
        else if (p.forelder != null && p == p.forelder.høyre){
            p = p.forelder;
        }
        return p;
    }

    private static <T> Node<T> nesteInorden(Node<T> p) {
        if (p.høyre != null) {
            p = p.høyre;
            while (p.venstre != null) {
                p = p.venstre;
            }
        }
        else {
            while (p.forelder != null && p == p.forelder.høyre) {
                p = p.forelder;
            }
            p = p.forelder;
        }
        return p;
    }

    @Override
    public String toString()
    {
        if (tom()) return "[]";

        Node p = rot;
        while (p.venstre != null){
            p = p.venstre;
        }
        StringBuilder str = new StringBuilder();
        str.append("[");
        if (p != null ){
            str.append(p.verdi);
        }

        while (nesteInorden(p) != null){
            p = nesteInorden(p);
            str.append(", " + p.verdi);
        }
        str.append("]");
        return str.toString();
    }

    public String omvendtString()
    {
        if (tom()) return "[]";

        StringBuilder str = new StringBuilder();
        str.append("[");

        Stakk<Node<T>> stakk = new TabellStakk<>();
        Node<T> p = rot;
        for ( ; p.høyre != null; p = p.høyre){
            stakk.leggInn(p);
        }
        while (true){
            if (str.length() > 1){
                str.append(", " + p.verdi);
            }
            else {
                str.append(p.verdi);
            }
            if (p.venstre != null){
                for (p = p.venstre; p.høyre != null; p = p.høyre){
                    stakk.leggInn(p);
                }
            }
            else if (!stakk.tom()){
                p = stakk.taUt();
            }
            else break; //stakken er tom
        }
        str.append("]");
        return str.toString();
    }

    public String høyreGren()
    {
        if (tom()) return "[]";

        Node<T> p = rot;
        StringBuilder str = new StringBuilder();
        str.append("["+rot.verdi);
        while(p.venstre != null || p.høyre != null){
            if(p.høyre != null){
                p = p.høyre;
                str.append(", " + p.verdi);
            }
            else{
                p = p.venstre;
                str.append(", " + p.verdi);
            }
        }

        str.append("]");
        return str.toString();
    }

    public String lengstGren(){

        if (tom()){
            return "[]";
        }

        StringBuilder str = new StringBuilder();
        str.append("[" + rot.verdi);

        ArrayDeque <Node <T>> kø =  new ArrayDeque<>();
        kø.addLast(rot);
        Node <T> temp = rot;
        while(!kø.isEmpty()){
            temp = kø.removeFirst();
            if(temp.høyre != null){
                kø.addLast(temp.høyre);
            }
            if(temp.venstre != null){
                kø.addLast(temp.venstre);
            }
        }

        Node <T> p = temp;
        Stakk<Node<T>> stakk = new TabellStakk<>();
        while(p.forelder != null){
            stakk.leggInn(p);
            p = p.forelder;
        }
        while(stakk.antall() != 0){
            str.append(", " + stakk.taUt().verdi);
        }

        str.append("]");
        return str.toString();
    }


    public String[] grener() {

        Stakk<Node<T>> stakk = new TabellStakk<>();
        List<String> liste = new ArrayList<String>();
        StringBuilder str = new StringBuilder();
        String[] gren;

        if(tom()){                                //returnerer en tom liste hvis treet er tomt
            String[] tomliste = new String[0];
            return tomliste;
        }

        Node <T> p = rot;
        while(p.venstre != null){
            p = p.venstre;
        }


        while(nesteInorden(p) != null) {

            if (p.venstre == null && p.høyre == null) {
                Node<T> t = p;                  // node for å telle opp fra blad til rot, uten å endre p
                str.append("["+rot);
                while (t.forelder != null) {
                    stakk.leggInn(t);
                    t = t.forelder;
                }

                while(stakk.antall() != 0){
                    str.append(", "+ stakk.taUt().verdi);
                }
                str.append("]");
                liste.add(str.toString());
                str.setLength(0);
            }
            p = nesteInorden(p);
        }
        if(p.venstre == null && p.høyre == null){
            Node<T> t = p;
            str.append("["+rot);
            while (t.forelder != null) {
                stakk.leggInn(t);
                t = t.forelder;
            }

            while(stakk.antall() != 0){
                str.append(", "+ stakk.taUt().verdi);
            }
            str.append("]");
            liste.add(str.toString());
            str.setLength(0);
        }


        gren = new String[liste.size()];
        liste.toArray(gren);

        return gren;
    }

    private static <T> void nesteBladnode(Node<T> p, StringBuilder str){
        if (p.venstre == null && p.høyre == null){
            if (str.length() > 1){
                str.append(", " + p.verdi);
            }
            else
                str.append(p.verdi);
        }
        if (p.venstre != null) nesteBladnode(p.venstre, str);
        if (p.høyre != null) nesteBladnode(p.høyre, str);
    }

    public String bladnodeverdier()
    {
        if (tom()) return "[]";

        StringBuilder str = new StringBuilder();
        str.append("[");
        Node<T> p = rot;
        nesteBladnode(p, str);
        str.append("]");
        return str.toString();
    }

    public String postString()
    {
        if (tom()) return "[]";

        StringBuilder str = new StringBuilder();
        str.append("[");

        Node<T> p = rot;
        p = førstePostorden(p);
        str.append(p.verdi);

        while (true){
            if (p.forelder != null) {
                p = nestePostorden(p);
                str.append(", " + p.verdi);
            }
            else break;
        }
        str.append("]");
        return str.toString();
    }


    @Override
    public Iterator<T> iterator()
    {
        return new BladnodeIterator();
    }

    private class BladnodeIterator implements Iterator<T>
    {
        private Node<T> p = rot, q = null;
        private boolean removeOK = false;
        private int iteratorendringer = endringer;

        private BladnodeIterator()  // konstruktør
        {
            if(rot == null){
                return;
            }
            p = førstePostorden(p);
        }

        @Override
        public boolean hasNext()
        {
            return p != null;  // Denne skal ikke endres!
        }

        @Override
        public T next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            if(endringer != iteratorendringer){
                throw new ConcurrentModificationException();
            }
            removeOK = true;

            q = p;
            p = nesteBladnode(p);

            return q.verdi;
        }

        public Node nesteBladnode(Node node){
            while (node.forelder != null && (node == node.forelder.høyre || node.forelder.høyre == null)){
                node = node.forelder;
            }

            if(node.forelder != null){
                node = node.forelder.høyre;

                while (true){
                    if (node.venstre != null){
                        node = node.venstre;
                    }

                    else if (node.høyre != null){
                        node = node.høyre;
                    } else {
                        break;
                    }
                }
            } else {
                node = null;
            }
            return node;
        }


        @Override
        public void remove()
        {
            if(!removeOK){
                throw new IllegalStateException();
            }
            if(endringer != iteratorendringer){
                throw new ConcurrentModificationException();
            }

            Node forelder = q.forelder;
            removeOK = false;

            if(antall() == 1){
                rot = null;
            } else {
                if (q == forelder.høyre) {
                    forelder.høyre = null;
                } else {
                    forelder.venstre = null;
                }
            }
            antall--;
            iteratorendringer++;
            endringer++;

        }

    } // BladnodeIterator

} // ObligSBinTre
