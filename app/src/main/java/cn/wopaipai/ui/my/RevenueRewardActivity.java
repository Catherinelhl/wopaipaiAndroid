package cn.wopaipai.ui.my;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import cn.wopaipai.R;
import cn.wopaipai.base.BaseAty;

public class RevenueRewardActivity extends BaseAty implements View.OnClickListener {

    private ImageButton back;
    private TextView title;
    private RelativeLayout trusteeship;
    private RelativeLayout rlInviate;
    private RelativeLayout rl_community;
    private RelativeLayout rlSeniorExecutive;
    private RelativeLayout rlSeniorPeiyu;

    // 收益奖励
    @Override
    public void getArgs(@NotNull Bundle bundle) {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_revenue_reward;
    }

    @Override
    public void initViews() {
        back = (ImageButton) findViewById(R.id.ib_back);
        back.setOnClickListener(this);
        title = (TextView) findViewById(R.id.set_title);
        title.setText(getResString(R.string.income_reward));
        trusteeship = (RelativeLayout) findViewById(R.id.rl_trusteeship);
        trusteeship.setOnClickListener(this);
        rlInviate = (RelativeLayout) findViewById(R.id.rl_inviate);
        rlInviate.setOnClickListener(this);
        rl_community = (RelativeLayout) findViewById(R.id.rl_community);
        rl_community.setOnClickListener(this);
        rlSeniorExecutive = (RelativeLayout) findViewById(R.id.rl_senior_executive);
        rlSeniorExecutive.setOnClickListener(this);
        rlSeniorPeiyu = (RelativeLayout) findViewById(R.id.rl_senior_peiyu);
        rlSeniorPeiyu.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.rl_trusteeship:  // 托管收益

                break;
            case R.id.rl_inviate:  // 推荐加价
                break;
            case R.id.rl_community:  // 社群奖励
                break;
            case R.id.rl_senior_executive:  // 管理佣金
                break;
            case R.id.rl_senior_peiyu:  // 培育佣金
                break;
        }
    }
}
