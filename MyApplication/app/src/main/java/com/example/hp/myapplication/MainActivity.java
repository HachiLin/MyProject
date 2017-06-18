package com.example.hp.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.IOException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.b);
        button.setOnClickListener(new View.OnClickListener() {
            @Override //监听点击事件
            public void onClick(View v) {
                //读取输入的关键字
                EditText word =(EditText) findViewById(R.id.e);
                String thisword = word.getText().toString();
                //记录收索到的结果条数
                int sum=1;
                String line,ans = "";
                String title="";
                try {
                    TextView text = (TextView) findViewById(R.id.t);
                    Scanner scan = new Scanner(getResources().getAssets().open("唐诗三百"));
                    out:
                    while (scan.hasNext()){
                        line = scan.nextLine();
                        for (int i = 0; i < line.length(); i++)
                        {
                            if(line.charAt(i)=='《') {
                                title = line;
                                continue out;
                            }
                            if (line.indexOf(thisword)==i)
                            {
                                ans += sum+":"+line +"\n-------"+title+ "\n";
                                sum++;
                                text.setText(ans);
                                title="";
                                text.setMovementMethod(ScrollingMovementMethod.getInstance());
                            }else if(thisword=="。"||thisword=="？"||thisword=="，"||thisword=="!")
                                break out;
                        }
                        //字体颜色
                        int k=0;
                        SpannableStringBuilder style=new SpannableStringBuilder(ans);
                        while( k >= 0)
                        {
                            int l=ans.indexOf(thisword, k);
                            int r = l + thisword.length();
                            if (l == -1)
                                break;
                            k = l + 1;
                            style.setSpan(new ForegroundColorSpan(Color.RED),l,r, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        }
                        text.setMovementMethod(ScrollingMovementMethod.getInstance());
                        text.setHorizontallyScrolling(true);
                        text.setText(style);
                    }
                }
                catch (IOException e) {
                    TextView text = (TextView) findViewById(R.id.t);
                    ans = "抱歉，你输入的内容不在唐诗三百首当中！";
                    text.setText(ans);
                }


            }
        });
    }
}

