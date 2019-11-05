package no.oslomet.cs.algdat.Oblig3;

import java.util.Comparator;

public class Main {

    public static void main(String[] args){

        int[] a = {1, 4, 1, 3, 1, 2, 1, 1};
        ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());
        for (int verdi : a) tre.leggInn(verdi);
        tre.fjernAlle(1);
        System.out.println(tre.antall()); // 5
        System.out.println(tre + " " + tre);

    }
}
