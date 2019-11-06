package no.oslomet.cs.algdat.Oblig3;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Iterator;

public class Testklasse {

    @Test
    void oppgave1() {
        Integer[] a = {4, 7, 2, 9, 5, 10, 8, 1, 3, 6};
        ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());
        for (int verdi : a) tre.leggInn(verdi);
        System.out.println(tre.antall());
    }

    @Test
    void oppgave2() {
        Integer[] a = {4, 7, 2, 9, 4, 10, 8, 7, 4, 6};
        ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());
        for (int verdi : a) tre.leggInn(verdi);

        System.out.println(tre.antall()); // Utskrift: 10
        System.out.println(tre.antall(5)); // Utskrift: 0
        System.out.println(tre.antall(4)); // Utskrift: 3
        System.out.println(tre.antall(7)); // Utskrift: 2
        System.out.println(tre.antall(10)); // Utskrift: 1
    }

    @Test
    void oppgave3() {
        int[] a = {4, 7, 2, 9, 4, 10, 8, 7, 4, 6, 1};
        ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());
        for (int verdi : a) tre.leggInn(verdi);
        System.out.println(tre); // [1, 2, 4, 4, 4, 6, 7, 7, 8, 9, 10]
    }

    @Test
    void oppgave4() {
        int[] a = {4, 7, 2, 9, 4, 10, 8, 7, 4, 6, 1};
        ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());
        for (int verdi : a) tre.leggInn(verdi);
        System.out.println(tre.omvendtString()); // [10, 9, 8, 7, 7, 6, 4, 4, 4, 2, 1]
    }

    @Test
    void oppgave8ab() {
        ObligSBinTre<Character> tre = new ObligSBinTre<>(Comparator.naturalOrder());
        char[] verdier = "IATBHJCRSOFELKGDMPQN".toCharArray();
        for (char c : verdier) tre.leggInn(c);
        System.out.println(tre.bladnodeverdier());
        System.out.println(tre.postString());
// [D, E, G, F, C, H, B, A, K, N, M, L, Q, P, O, S, R, J, T, I]

    }

    @Test
    void oppgave9() {
        ObligSBinTre<Character> tre = new ObligSBinTre<>(Comparator.naturalOrder());
        char[] verdier = "IATBHJCRSOFELKGDMPQN".toCharArray();
        for (char c : verdier) tre.leggInn(c);
        System.out.println(tre.bladnodeverdier());
// [D, E, G, F, C, H, B, A, K, N, M, L, Q, P, O, S, R, J, T, I]

    }
}
