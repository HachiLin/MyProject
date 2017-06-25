package com.example.hp.game2048;

/**
 * Created by HP on 2017/6/23.
 */

public class randomCreate {
    public static int[][] randomCreate(int a[][], String index) {
        String[] b = index.split("####");
        int random = (int) (Math.random() * (b.length - 1));
        String[] c = b[random].split("##");
        if (Math.random() > 0.1) {
            a[Integer.parseInt(c[0])][Integer.parseInt(c[1])] = 2;
        } else {
            a[Integer.parseInt(c[0])][Integer.parseInt(c[1])] = 4;
        }
        return a;
    }

    public static String searchforZERO(int a[][]) {
        int i, j;
        String index = "";
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                if (a[i][j] == 0) {
                    index = i + "##" + j + "####" + index;
                }
            }
        }
        return index;
    }
}
