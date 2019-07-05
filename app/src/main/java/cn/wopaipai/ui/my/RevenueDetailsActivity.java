package cn.wopaipai.ui.my;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

import com.jakewharton.rxbinding2.view.RxView;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import cn.wopaipai.R;
import cn.wopaipai.base.BaseAty;
import cn.wopaipai.constant.Constants;
import cn.wopaipai.tool.LogTool;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RevenueDetailsActivity extends BaseAty {

    private ImageButton back;

    // 收益奖励详情
    @Override
    public void getArgs(@NotNull Bundle bundle) {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_revenue_detils;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initViews() {
        back = (ImageButton) findViewById(R.id.ib_back);
        RxView.clicks(back).throttleFirst(Constants.Time.sleep1000,TimeUnit.MICROSECONDS).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                finish();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
