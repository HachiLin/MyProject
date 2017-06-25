package com.example.hp.game2048;

import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 2017/6/23.
 */

public class savedata {
    /**
     * 保存Score
     *
     * @return
     */
    public static boolean saveUserInfo(int BestScore) {

        File BSFile = Environment.getDataDirectory();
        File file = new File(BSFile, "BestScore.txt");
        // String path =
        // "/data/data/com.ldgforever.twozerofourdight/BestScore.txt";
        try {
            FileOutputStream fos = new FileOutputStream(file);
            String data = "##" + BestScore;
            fos.write(data.getBytes());
            fos.flush();
            fos.close();
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读取用户输入文件
     *
     * @return
     */
    public static Map<String, String> getUserInfo() {

        File BSFile = Environment.getDataDirectory();
        File file = new File(BSFile, "BestScore.txt");
        // String path =
        // "/data/data/com.ldgforever.twozerofourdight/BestScore.txt";
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis)); // 构建字符流对象
            String text = reader.readLine();
            reader.close();
            if (!TextUtils.isEmpty(text)) {
                String[] split = text.split("##");
                Map<String, String> userInfoMap = new HashMap<String, String>();
                userInfoMap.put("Score", split[0]);
                userInfoMap.put("BestScore", split[1]);
                return userInfoMap;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
