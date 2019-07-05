package cn.wopaipai.ui.my;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import cn.catherine.token.http.retrofit.RetrofitFactory;
import cn.catherine.token.tool.json.GsonTool;
import cn.wopaipai.R;
import cn.wopaipai.base.BaseApplication;
import cn.wopaipai.base.BaseAty;
import cn.wopaipai.base.BaseContract;
import cn.wopaipai.base.BasePresenterImp;
import cn.wopaipai.bean.request.ExchangeZBBRequestBean;
import cn.wopaipai.bean.request.TurnOutRequestBean;
import cn.wopaipai.constant.MessageConstants;
import cn.wopaipai.event.ChoiceContactAddressEventBus;
import cn.wopaipai.event.ExchangeEventBus;
import cn.wopaipai.gson.ResponseJson;
import cn.wopaipai.http.HttpApi;
import cn.wopaipai.manager.AppManagerKt;
import cn.wopaipai.tool.StringTool;
import cn.wopaipai.utils.MD5;
import cn.wopaipai.utils.ScanActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class WalletTurnOutActivity extends BaseAty implements EasyPermissions.PermissionCallbacks, View.OnClickListener, BaseContract.View {

    // 转出
    private ImageButton back;
    private TextView title;
    private ImageButton title_right;
    private EditText et_income_address;
    private ImageButton income_address;
    private ImageButton turnout_total;
    private EditText income_number;
    private EditText transaction_pwd;
    private TextView remarks;
    private Button btnSureTurnout;
    private String coin_type;
    private double balance;

    @Override
    public void getArgs(@NotNull Bundle bundle) {
        coin_type = bundle.getString("coin_type");
        balance = bundle.getDouble("balance");
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onEventBusView(ChoiceContactAddressEventBus choiceContactAddressEventBus) {
        et_income_address.setText(choiceContactAddressEventBus.getAddress());
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_wallet_turn_out;
    }

    @Override
    public void initViews() {
        back = (ImageButton) findViewById(R.id.ib_back);
        back.setOnClickListener(this);
        title = (TextView) findViewById(R.id.set_title);
        title.setText(getResources().getString(R.string.turn_out_address));
        title_right = (ImageButton) findViewById(R.id.title_right);
        title_right.setOnClickListener(this);
        et_income_address = (EditText) findViewById(R.id.et_income_address);
        income_address = (ImageButton) findViewById(R.id.ib_income_address);
        income_address.setOnClickListener(this);
        turnout_total = (ImageButton) findViewById(R.id.ib_turnout_total);
        turnout_total.setOnClickListener(this);
        income_number = (EditText) findViewById(R.id.et_income_number);
        income_number.setHint(getResources().getString(R.string.current_assets)+balance);
        transaction_pwd = (EditText) findViewById(R.id.et_transaction_pwd);
        remarks = (TextView) findViewById(R.id.tv_remarks);
        btnSureTurnout = (Button) findViewById(R.id.btn_sure_turnout);
        btnSureTurnout.setOnClickListener(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }



    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_back:
                finish();
                break;
            case R.id.title_right:
                new IntentIntegrator(WalletTurnOutActivity.this)
                        .setOrientationLocked(false)
                        .setCaptureActivity(ScanActivity.class) // 设置自定义的activity是ScanActivity
                        .initiateScan(); // 初始化扫描
                break;
            case R.id.ib_income_address:
                Intent intent=new Intent(this,ContactListActivity.class);
                startActivity(intent);
                break;
            case R.id.ib_turnout_total:
                income_number.setText(balance+"");
                break;
            case R.id.btn_sure_turnout:
                if (StringTool.isEmpty(et_income_address.getText().toString())){
                    showToast(getResources().getString(R.string.input_income_address));
                    return;
                }
                if (StringTool.isEmpty(income_number.getText().toString())){
                    showToast(getResources().getString(R.string.input_turnout_number));
                    return;
                }
                if (StringTool.isEmpty(transaction_pwd.getText().toString())){
                    showToast(getResources().getString(R.string.transaction_password_please));
                    return;
                }else {
                    showLoading();
                    walletturnOut();
                }
                break;
        }
    }

    private void walletturnOut() {
        // 转出
        TurnOutRequestBean turnOutRequestBean = new TurnOutRequestBean();
        int PassportId = BaseApplication.Companion.getPassportId();
        turnOutRequestBean.setPassportId(PassportId);   // 通行证ID
        String coinCode=coin_type;
        turnOutRequestBean.setCoinCode(coinCode); // 转出币种
        String to=et_income_address.getText().toString();
        turnOutRequestBean.setTo(to); // 转出到哪里
        String TradePassword = transaction_pwd.getText().toString().trim() + ".x2019";
        turnOutRequestBean.setTradePassword(TradePassword);  // 交易密码
        double amount=Double.parseDouble(income_number.getText().toString());
        turnOutRequestBean.setAmount(amount);  // 转出数量
        String remark=remarks.getText().toString();
        turnOutRequestBean.setRemark(remark);  // 备注
        String sign=MD5.Md5Sign(PassportId+TradePassword+coinCode+to);
        turnOutRequestBean.setSign(sign);   // 加密
        RequestBody requestBody = GsonTool.INSTANCE.beanToRequestBody(turnOutRequestBean);
        Observable<ResponseJson> contact = RetrofitFactory.INSTANCE.getAPIInstance().create(HttpApi.class).turnOut(requestBody);
        contact.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<ResponseJson>() {
                    @Override
                    public void onNext(ResponseJson responseJson) {
                        int code = responseJson.getStatusCode();
                        if (code == MessageConstants.CODE_200) {
                            hideLoading();
                            showToast(getResources().getString(R.string.contact_create_success));
                            EventBus.getDefault().post(new ExchangeEventBus());
                            finish();
                        } else {
                            hideLoading();
                            showToast(new BasePresenterImp().getExceptionInfoByCode(code));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
// 通过 onActivityResult的方法获取扫描回来的值
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
//                showShortToast(GetStringUtils.getString(R.string.title_message_null));
            } else {
                AppManagerKt.showToast(getActivity(),getResString(R.string.title_scan_success));
                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
                et_income_address.setText(ScanResult);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        new AppSettingsDialog.Builder(this).build().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
