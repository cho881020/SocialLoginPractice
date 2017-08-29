package kr.co.tjeit.socialloginpractice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;

import kr.co.tjeit.socialloginpractice.data.User;
import kr.co.tjeit.socialloginpractice.util.ContextUtil;

public class MainActivity extends BaseActivity {

    User me = null;

    private android.widget.TextView idTxt;
    private android.widget.TextView pwTxt;
    private android.widget.Button logoutBtn;
    private android.widget.ImageView profileImg;
    private TextView nameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        setupEvents();
        setValues();


    }

    @Override
    public void setupEvents() {
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                로그아웃할때, 사용자 정보를 제거
                ContextUtil.logout(mContext);

//                로그아웃버튼이 눌리면, 페이스북에서도 강제로 로그아웃
                LoginManager.getInstance().logOut();

                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void setValues() {

        me = ContextUtil.getLoginUser(mContext);

        idTxt.setText(me.getUserId());
        pwTxt.setText(me.getPassword());
        nameTxt.setText(me.getName());
        Glide.with(this).load(me.getProfileURL()).into(profileImg);


    }

    @Override
    public void bindViews() {
        this.logoutBtn = (Button) findViewById(R.id.logoutBtn);
        this.nameTxt = (TextView) findViewById(R.id.nameTxt);
        this.pwTxt = (TextView) findViewById(R.id.pwTxt);
        this.idTxt = (TextView) findViewById(R.id.idTxt);
        this.profileImg = (ImageView) findViewById(R.id.profileImg);
    }
}
