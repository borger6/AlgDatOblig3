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
}
