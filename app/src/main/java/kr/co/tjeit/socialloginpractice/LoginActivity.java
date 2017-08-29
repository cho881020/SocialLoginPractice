package kr.co.tjeit.socialloginpractice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import kr.co.tjeit.socialloginpractice.data.User;
import kr.co.tjeit.socialloginpractice.util.ContextUtil;
import kr.co.tjeit.socialloginpractice.util.GlobalData;

public class LoginActivity extends BaseActivity {

    private android.widget.EditText idEdt;
    private android.widget.EditText pwEdt;
    private android.widget.Button loginBtn;

    CallbackManager cm;
    private com.facebook.login.widget.LoginButton fbLoginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
        setupEvents();
        setValues();

        GlobalData.initGlobalData();
    }

    @Override
    public void setupEvents() {

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                입력된 ID와 비밀번호를 확인해서,
//                로그인이 가능하면 로그인 성공 Toast
//                실패하면 실패했다고 Toast.

//                존재하지 않는 아이디입니다. (아이디 X)
//                비밀번호가 올바르지 않습니다. (아이디 O 비번 X)

                boolean idOk = false;
                boolean pwOk = false;

//                모든 사용자에 대해 검사하기 위한 반복문
                for (User user : GlobalData.allUsers) {
//                    입력된 아이디가 검사하는 사용자의 아이디와 같은지 검사하는 조건문
                    if (user.getUserId().equals(idEdt.getText().toString()) ) {
//                        아이디가 존재하는 상황에서, 비밀번호가 일치하는지 검사하는 조건문

                        idOk = true;

                        if (user.getPassword().equals(pwEdt.getText().toString())) {
//                            아이디도 일치하고, 비밀번호도 일치하므로 로그인 성공 Toast
                            pwOk = true;

                            ContextUtil.login(mContext,
                                    user.getUserId(),
                                    user.getPassword(),
                                    user.getName(),
                                    user.getProfileURL());

                        }
                    }
                }
                
                if (idOk && pwOk) {


                    Toast.makeText(mContext, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                else {
                    if (idOk) {
                        Toast.makeText(mContext, "비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(mContext, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

    }

    @Override
    public void setValues() {

//        로그인 처리가 완료되면, 우리 앱에서도 반영하기 위해
//        콜백을 만들어 등록하는 과정.
//        페이스북 문서 따라함.
        cm = CallbackManager.Factory.create();
        fbLoginBtn.registerCallback(cm, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

//        프로필? 내가 누군지 나타내는 정보. 트래커? 추적기
//        ProfileTracker? 접속한 사용자가 바뀌는 상황을 감지.
//         새로 로그인 / 로그아웃 시에 동작.

        ProfileTracker pt = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if (currentProfile == null) {
//                    현재 접속한 사용자가 없다.
//                    로그아웃 하는 상황.

                    Toast.makeText(mContext, "로그아웃 처리 완료.", Toast.LENGTH_SHORT).show();

                }
                else {
//                    로그인 감지
                    Toast.makeText(mContext, currentProfile.getName()+"님 접속", Toast.LENGTH_SHORT).show();


//                    Log.d("LINK", currentProfile.getLinkUri().toString());
//
//                    Intent webIntent = new Intent(Intent.ACTION_VIEW, currentProfile.getLinkUri());
//                    startActivity(webIntent);

//                    Intent의 기능중 웹페이지 띄워주기.
//                    로그인 한 사람이 제공하는 링크로 넘어가기.

                    ContextUtil.login(mContext, currentProfile.getId(), "없음",
                            currentProfile.getName(), currentProfile.getProfilePictureUri(500,500).toString());

                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);

                }
            }
        };


        idEdt.setText(ContextUtil.getUserId(mContext));
        pwEdt.setText(ContextUtil.getUserPw(mContext));
    }

//    페이스북 로그인 화면을 갔다가 돌아오면 콜백매니저가 자동으로 처리할 수 있도록 코딩
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cm.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void bindViews() {
        this.fbLoginBtn = (LoginButton) findViewById(R.id.fbLoginBtn);
        this.loginBtn = (Button) findViewById(R.id.loginBtn);
        this.pwEdt = (EditText) findViewById(R.id.pwEdt);
        this.idEdt = (EditText) findViewById(R.id.idEdt);

    }
}
