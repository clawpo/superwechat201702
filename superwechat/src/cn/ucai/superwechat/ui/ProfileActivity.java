package cn.ucai.superwechat.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.easeui.domain.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.SuperWeChatHelper;

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
        user = (User) getIntent().getSerializableExtra(I.User.TABLE_NAME);
        if (user!=null){
            showInfo();
        }else{
            finish();
        }
    }

    private void showInfo() {
        mTvUserinfoName.setText(user.getMUserName());
        mTvUserinfoNick.setText(user.getMUserNick()==null?user.getMUserName():user.getMUserNick());
        if(user != null && user.getAvatar() != null){
            try {
                int avatarResId = Integer.parseInt(user.getAvatar());
                Glide.with(ProfileActivity.this).load(avatarResId).into(mProfileImage);
            } catch (Exception e) {
                //use default avatar
                Glide.with(ProfileActivity.this).load(user.getAvatar())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ease_default_avatar).into(mProfileImage);
            }
        }else{
            Glide.with(ProfileActivity.this).load(R.drawable.ease_default_avatar).into(mProfileImage);
        }
        showButton(SuperWeChatHelper.getInstance().getContactList().containsKey(user.getMUserName()));
    }

    private void showButton(boolean isContact) {
        mBtnAddContact.setVisibility(isContact? View.GONE:View.VISIBLE);
        mBtnSendMsg.setVisibility(isContact?View.VISIBLE:View.GONE);
        mBtnSendVideo.setVisibility(isContact?View.VISIBLE:View.GONE);
    }
}