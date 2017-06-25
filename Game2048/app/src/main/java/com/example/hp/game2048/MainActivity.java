package com.example.hp.game2048;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.Map;

public class MainActivity extends Activity {

    private static TextView mtextview00;
    private static TextView mtextview01;
    private static TextView mtextview02;
    private static TextView mtextview03;

    private static TextView mtextview10;
    private static TextView mtextview11;
    private static TextView mtextview12;
    private static TextView mtextview13;

    private static TextView mtextview20;
    private static TextView mtextview21;
    private static TextView mtextview22;
    private static TextView mtextview23;

    private static TextView mtextview30;
    private static TextView mtextview31;
    private static TextView mtextview32;
    private static TextView mtextview33;

    private static TextView mScore;
    private static TextView mBestScore;

    public static int score = 0;
    public static int bestscore = 0;

    float startX = 0, startY = 0, endX, endY;
    private int[][] a = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score = 0;
        mtextview00 = (TextView) findViewById(R.id.textview00);
        mtextview01 = (TextView) findViewById(R.id.textview01);
        mtextview02 = (TextView) findViewById(R.id.textview02);
        mtextview03 = (TextView) findViewById(R.id.textview03);

        mtextview10 = (TextView) findViewById(R.id.textview10);
        mtextview11 = (TextView) findViewById(R.id.textview11);
        mtextview12 = (TextView) findViewById(R.id.textview12);
        mtextview13 = (TextView) findViewById(R.id.textview13);

        mtextview20 = (TextView) findViewById(R.id.textview20);
        mtextview21 = (TextView) findViewById(R.id.textview21);
        mtextview22 = (TextView) findViewById(R.id.textview22);
        mtextview23 = (TextView) findViewById(R.id.textview23);

        mtextview30 = (TextView) findViewById(R.id.textview30);
        mtextview31 = (TextView) findViewById(R.id.textview31);
        mtextview32 = (TextView) findViewById(R.id.textview32);
        mtextview33 = (TextView) findViewById(R.id.textview33);

        mScore = (TextView) findViewById(R.id.score);
        mBestScore = (TextView) findViewById(R.id.bestscore);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            startX = event.getX();
            startY = event.getY();
        } else if (action == MotionEvent.ACTION_UP) {
            endX = event.getX();
            endY = event.getY();

            int direction = GetSlideDirection(startX, startY, endX, endY);
            int winORlose = Action.Slide(direction, a);
            switch (winORlose) {
                case 0:
                    dialog("您赢了！");
                    break;
                case 1:
                    dialog("您输了");
                default:
                    break;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    // 返回角度
    private double GetSlideAngle(float dx, float dy) {
        return Math.atan2(dy, dx) * 180 / Math.PI;
    }

    private int GetSlideDirection(float startX, float startY, float endX, float endY) {
        float dy = startY - endY;
        float dx = endX - startX;
        int result = 0; // 没有滑动
        // 如果滑动距离太短
        if (Math.abs(dx) < 2 && Math.abs(dy) < 2) {
            return result;
        }
        double angle = GetSlideAngle(dx, dy);
        if (angle >= -45 && angle < 45) {
            return 1; // 向右滑动
        } else if (angle >= 45 && angle < 135) {
            return 2; // 向上滑动
        } else if (angle >= -135 && angle < -45) {
            return 4; // 向下滑动
        } else if ((angle >= 135 && angle <= 180) || (angle >= -180 && angle < -135)) {
            return 3; // 向左滑动
        }
        return result;
    }

    public void reset(int a[][]) {
        int i, j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                a[i][j] = 0;
            }
        }
        score = 0;
        /**
         * 回显数据
         */
        Map<String, String> userInfoMap = savedata.getUserInfo();
        if (userInfoMap != null) {
            mBestScore.setText(userInfoMap.get("BestScore"));
        }
        updateState(a, score, bestscore);
    }

    /**
     * 实时更新textview属性
     * @param a
     * @param Score
     * @param BestScore
     */
    public static void updateState(int[][] a, int Score, int BestScore) {
        updateTextViewState(a[0][0], mtextview00);
        updateTextViewState(a[0][1], mtextview01);
        updateTextViewState(a[0][2], mtextview02);
        updateTextViewState(a[0][3], mtextview03);

        updateTextViewState(a[1][0], mtextview10);
        updateTextViewState(a[1][1], mtextview11);
        updateTextViewState(a[1][2], mtextview12);
        updateTextViewState(a[1][3], mtextview13);

        updateTextViewState(a[2][1], mtextview21);
        updateTextViewState(a[2][2], mtextview22);
        updateTextViewState(a[2][3], mtextview23);
        updateTextViewState(a[2][0], mtextview20);

        updateTextViewState(a[3][0], mtextview30);
        updateTextViewState(a[3][1], mtextview31);
        updateTextViewState(a[3][2], mtextview32);
        updateTextViewState(a[3][3], mtextview33);

        mScore.setText(Score + "");
        mBestScore.setText(BestScore + "");
    }

    public static void updateTextViewState(int role, TextView textview) {
        switch (role) {
            case 2:
                textview.setText("2");
                textview.setBackgroundColor(Color.parseColor("#EEE4DA"));
                break;
            case 4:
                textview.setText("4");
                textview.setBackgroundColor(Color.parseColor("#EDE0C8"));
                break;
            case 8:
                textview.setText("8");
                textview.setBackgroundColor(Color.parseColor("#F2B179"));
                break;
            case 16:
                textview.setText("16");
                textview.setBackgroundColor(Color.parseColor("#F49563"));
                break;
            case 32:
                textview.setText("32");
                textview.setBackgroundColor(Color.parseColor("#F5794D"));
                break;
            case 64:
                textview.setText("64");
                textview.setBackgroundColor(Color.parseColor("#F55D37"));
                break;
            case 128:
                textview.setText("128");
                textview.setBackgroundColor(Color.parseColor("#EEE863"));
                break;
            case 256:
                textview.setText("256");
                textview.setBackgroundColor(Color.parseColor("#EDB04D"));
                break;
            case 512:
                textview.setText("512");
                textview.setBackgroundColor(Color.parseColor("#ECB04D"));
                break;
            case 1024:
                textview.setText("1024");
                textview.setBackgroundColor(Color.parseColor("#EB9437"));
                break;
            case 2048:
                textview.setText("2048");
                textview.setBackgroundColor(Color.parseColor("#EA7821"));
                break;
            default:
                textview.setText("");
                textview.setBackgroundColor(Color.parseColor("#CCC0B3"));
                break;
        }
    }

    /**
     * 对话框
     * @param winorlose
     */
    protected void dialog(String winorlose) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(winorlose);

        builder.setTitle("提示");

        builder.setPositiveButton("重新开始", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                reset(a);
                mScore.setText("0");
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("关闭游戏", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
