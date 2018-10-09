package com.lmd.thomas.gamechoice.models;

/**
 * Created by thomas on 06/02/2018.
 */

public class Words {
//
//    public static final String[][] get = {
//        {"gato", "vaca", "bode", "vaga-lume"},
//        {"besouro", "caracol", "galinha", "lapis"},
//        {"joaninha", "lagarta", "pincel", "zebra"}
//    };

    public static final String[][] get(String book) {
        String[][] words;
        if (book.equals("o_artista")) {
            return new String[][] {
                    {"gato", "vaca", "bode", "vaga-lume"},
                    {"besouro", "caracol", "galinha", "lapis"},
                    {"joaninha", "lagarta", "pincel", "zebra"}
                };
        }
        return new String[][] {
                {"gato", "mato", "ovo", "pato"},
                {"bichos", "coruja", "raposa", "tucano"},
                {"família", "filhote", "gambá"},
        };
    }

}
