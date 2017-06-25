package com.example.hp.game2048;

/**
 * Created by HP on 2017/6/23.
 */

public class Action {

    static int t;

    static void up_remove_blank(int[][] a) {
        int i, j, k;
        for (j = 0; j < 4; j++) {
            for (i = 1; i < 4; i++) {
                k = i;
                while (k - 1 >= 0 && a[k - 1][j] == 0) {// 上面的那个为空
                    t = a[k][j];
                    a[k][j] = a[k - 1][j];
                    a[k - 1][j] = t;
                    k--;

                }
            }
        }

    }

    static void down_remove_blank(int a[][]) {
        int i, j, k;
        for (j = 0; j < 4; j++) {
            for (i = 2; i >= 0; i--) {
                k = i;
                while (k + 1 <= 3 && a[k + 1][j] == 0) {// 上面的那个为空
                    t = a[k][j];
                    a[k][j] = a[k + 1][j];
                    a[k + 1][j] = t;
                    k++;
                }
            }
        }
    }

    static void left_remove_blank(int a[][]) {
        int i, j, k;
        for (i = 0; i < 4; i++) {
            for (j = 1; j < 4; j++) {
                k = j;
                while (k - 1 >= 0 && a[i][k - 1] == 0) {// 上面的那个为空
                    t = a[i][k];
                    a[i][k] = a[i][k - 1];
                    a[i][k - 1] = t;
                    k--;
                }
            }
        }
    }

    static void right_remove_blank(int a[][]) {
        int i, j, k;
        for (i = 0; i < 4; i++) {
            for (j = 2; j >= 0; j--) {
                k = j;
                while (k + 1 <= 3 && a[i][k + 1] == 0) {// 上面的那个为空
                    t = a[i][k];
                    a[i][k] = a[i][k + 1];
                    a[i][k + 1] = t;
                    k++;
                }
            }
        }
    }

    static void left(int a[][]) {
        int i, j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 3; j++) {
                if (a[i][j] == a[i][j + 1]) {
                    a[i][j] += a[i][j + 1];
                    if (a[i][j] != 0) {
                        MainActivity.score += 2;
                        updatecore(MainActivity.score, MainActivity.bestscore);
                    }
                    a[i][j + 1] = 0;
                    left_remove_blank(a);
                }
            }
        }
    }

    static void up(int a[][]) {
        int i, j;
        for (j = 0; j < 4; j++) {
            for (i = 0; i < 3; i++) {
                if (a[i][j] == a[i + 1][j]) {
                    a[i][j] += a[i + 1][j];
                    if (a[i][j] != 0) {
                        MainActivity.score += 2;
                        updatecore(MainActivity.score, MainActivity.bestscore);
                    }
                    a[i + 1][j] = 0;
                    up_remove_blank(a);
                }
            }
        }
    }

    static void right(int a[][]) {
        int i, j;
        for (i = 0; i < 4; i++) {
            for (j = 3; j >= 1; j--) {
                if (a[i][j] == a[i][j - 1]) {
                    a[i][j] += a[i][j - 1];
                    if (a[i][j] != 0) {
                        MainActivity.score += 2;
                        updatecore(MainActivity.score, MainActivity.bestscore);
                    }
                    a[i][j - 1] = 0;
                    right_remove_blank(a);
                }
            }
        }
    }

    static void down(int a[][]) {
        int i, j;
        for (j = 0; j < 4; j++) {
            for (i = 3; i >= 1; i--) {
                if (a[i][j] == a[i - 1][j]) {
                    a[i][j] += a[i - 1][j];
                    if (a[i][j] != 0) {
                        MainActivity.score += 2;
                        updatecore(MainActivity.score, MainActivity.bestscore);
                    }
                    a[i - 1][j] = 0;
                    down_remove_blank(a);
                }
            }
        }
    }

    static boolean isFailure(int a[][]) {
        int i, j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                if (a[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean isWin(int a[][]) {
        int i, j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                if (a[i][j] == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void updatecore(int score, int bestscore) {
        if (MainActivity.score > MainActivity.bestscore) {
            MainActivity.bestscore = MainActivity.score;
            savedata.saveUserInfo(MainActivity.bestscore);
        }
    }

    public static int Slide(int direction, int a[][]) {
        if (direction == 3) {
            left_remove_blank(a);
            left(a);
            MainActivity.updateState(a, MainActivity.score, MainActivity.bestscore);
            if (isWin(a))
                return 0;
            else if (isFailure(a))
                return 1;
            else {
                String index = randomCreate.searchforZERO(a);
                randomCreate.randomCreate(a, index);
                MainActivity.updateState(a, MainActivity.score, MainActivity.bestscore);
            }
        } else if (direction == 1) {
            right_remove_blank(a);
            right(a);
            if (isWin(a))
                return 0;
            else if (isFailure(a))
                return 1;
            else {
                String index = randomCreate.searchforZERO(a);
                randomCreate.randomCreate(a, index);
                MainActivity.updateState(a, MainActivity.score, MainActivity.bestscore);
            }
        } else if (direction == 2) {
            up_remove_blank(a);
            up(a);
            if (isWin(a))
                return 0;
            else if (isFailure(a))
                return 1;
            else {
                String index = randomCreate.searchforZERO(a);
                randomCreate.randomCreate(a, index);
                MainActivity.updateState(a, MainActivity.score, MainActivity.bestscore);
            }
        } else if (direction == 4) {
            down_remove_blank(a);
            down(a);
            if (isWin(a))
                return 0;
            else if (isFailure(a))
                return 1;
            else {
                String index = randomCreate.searchforZERO(a);
                randomCreate.randomCreate(a, index);
                MainActivity.updateState(a, MainActivity.score, MainActivity.bestscore);
            }
        }
        return 2;
    }
}
