package cn.ucai.superwechat.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.domain.User;
import com.hyphenate.easeui.utils.EaseUserUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.SuperWeChatHelper;
import cn.ucai.superwechat.utils.MFGT;

/**
 * Created by clawpo on 2017/5/25.
 */

public class ProfileActivity extends BaseActivity {
    @BindView(R.id.profile_image)
    ImageView mProfileImage;
    @BindView(R.id.tv_userinfo_nick)
    TextView mTvUserinfoNick;
    @BindView(R.id.tv_userinfo_name)
    TextView mTvUserinfoName;
    @BindView(R.id.btn_add_contact)
    Button mBtnAddContact;
    @BindView(R.id.btn_send_msg)
    Button mBtnSendMsg;
    @BindView(R.id.btn_send_video)
    Button mBtnSendVideo;
    User user = null;

    @Override
    protected void onCreate(Bundle arg0) {
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        super.onCreate(arg0);
        initData();
        showLeftBack();
    }

    private void initData() {
        String username = getIntent().getStringExtra(I.User.USER_NAME);
        if (username!=null){
            user = SuperWeChatHelper.getInstance().getAppContactList().get(username);
        }
        if (user==null){
            user = (User) getIntent().getSerializableExtra(I.User.TABLE_NAME);
        }
        if (user!=null){
            showInfo();
        }else {
            finish();
        }
    }

    private void showInfo() {
        mTvUserinfoName.setText(user.getMUserName());
        EaseUserUtils.setAppUserNick(user,mTvUserinfoNick);
        EaseUserUtils.setAppUserAvatar(ProfileActivity.this,user,mProfileImage);
        showButton(SuperWeChatHelper.getInstance().getAppContactList().containsKey(user.getMUserName()));
    }

    private void showButton(boolean isContact) {
        mBtnAddContact.setVisibility(isContact? View.GONE:View.VISIBLE);
        mBtnSendMsg.setVisibility(isContact?View.VISIBLE:View.GONE);
        mBtnSendVideo.setVisibility(isContact?View.VISIBLE:View.GONE);
    }

    @OnClick(R.id.btn_add_contact)
    public void sendAddContactMsg(){
        MFGT.gotoSendMsg(ProfileActivity.this,user.getMUserName());
    }

    @OnClick(R.id.btn_send_msg)
    public void onSendMsg(){
        MFGT.gotoChat(ProfileActivity.this,user.getMUserName());
    }

    @OnClick(R.id.btn_send_video)
    public void onSendVideo(){
        MFGT.gotoVideo(ProfileActivity.this,user.getMUserName());
    }

}
