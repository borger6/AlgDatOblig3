package no.oslomet.cs.algdat.Oblig3;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

public class Testklasse {

    @Test
    void oppgave1(){
        Integer[] a = {4,7,2,9,5,10,8,1,3,6};
        ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator. naturalOrder ());
        for ( int verdi : a) tre.leggInn(verdi);
        System. out .println(tre.antall());
    }

    @Test
    void oppgave2(){
        Integer[] a = {4,7,2,9,4,10,8,7,4,6};
        ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());
        for (int verdi : a) tre.leggInn(verdi);

        System. out .println(tre.antall()); // Utskrift: 10
        System. out .println(tre.antall(5)); // Utskrift: 0
        System. out .println(tre.antall(4)); // Utskrift: 3
        System. out .println(tre.antall(7)); // Utskrift: 2
        System. out .println(tre.antall(10)); // Utskrift: 1
    }

    @Test
    void oppgave3(){
        int [] a = {4,7,2,9,4,10,8,7,4,6,1};
        ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());
        for ( int verdi : a) tre.leggInn(verdi);
        System. out .println(tre); // [1, 2, 4, 4, 4, 6, 7, 7, 8, 9, 10]
    }
}
