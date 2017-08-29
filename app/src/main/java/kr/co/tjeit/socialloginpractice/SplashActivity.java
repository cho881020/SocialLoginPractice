package kr.co.tjeit.socialloginpractice;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kr.co.tjeit.socialloginpractice.util.ContextUtil;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "kr.co.tjeit.socialloginpractice",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent intent;
//                if 질문
//                1. 로그인한 사용자의 아이디가 빈 칸인가? O : 로그아웃,  X: 로그인
//                2. (수정안) 로그인한 사용자가 없는가? O : 로그아웃, X : 로그인
                if (ContextUtil.getLoginUser(mContext) == null) {
//                    로그인이 안되어있는 상태
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                else {
//                    로그인이 되어있는 상태.
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(intent);
            }
        }, 2000);

    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {

    }

    @Override
    public void bindViews() {

    }
}
