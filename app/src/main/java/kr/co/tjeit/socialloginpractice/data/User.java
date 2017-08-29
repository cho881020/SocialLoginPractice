package kr.co.tjeit.socialloginpractice.data;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by user on 2017-08-29.
 */

public class User {

    private String userId;
    private String password;
    private String name;
    private String profileURL;

    public User() {
    }

    public User(String userId, String password, String name, String profileURL) {
        this.userId = userId;

        String changedPw = "";
        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(password.getBytes());
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer();
            for(int i = 0 ; i < byteData.length ; i++){
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }

            changedPw = sb.toString();



        }catch(NoSuchAlgorithmException e){

            e.printStackTrace();

            changedPw = null;

        }

        Log.d("원본비밀번호", password);
        Log.d("변경된비밀번호", changedPw);

        this.password = changedPw;
        this.name = name;
        this.profileURL = profileURL;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }
}
