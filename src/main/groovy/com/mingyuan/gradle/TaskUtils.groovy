package com.mingyuan.gradle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * Created by yanxq on 17/1/10.
 */
public class TaskUtils {

    public static String getBuildDate() {
        return new Date().toString();
    }

    public static String getGitSHA1Value() {
        try {
            Process process = Runtime.getRuntime().exec("git log");
            process.waitFor();
            InputStream input = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = reader.readLine();
            reader.close();
            process.destroy();

            String curGitSHA1Value = "";
            if (line != null && line.length() >= 15) {
                curGitSHA1Value = line.substring(7,14);
            }
            return curGitSHA1Value;
        } catch (Throwable throwable) {
            return "";
        }
    }

}
