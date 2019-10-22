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
  public boolean fjern(T verdi)
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  public int fjernAlle(T verdi)
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
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
    throw new UnsupportedOperationException("Ikke kodet ennå!");
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
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  public String høyreGren()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  public String lengstGren()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  public String[] grener()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  public String bladnodeverdier()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  public String postString()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
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
      throw new UnsupportedOperationException("Ikke kodet ennå!");
    }
    
    @Override
    public boolean hasNext()
    {
      return p != null;  // Denne skal ikke endres!
    }
    
    @Override
    public T next()
    {
      throw new UnsupportedOperationException("Ikke kodet ennå!");
    }
    
    @Override
    public void remove()
    {
      throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

  } // BladnodeIterator

} // ObligSBinTre
