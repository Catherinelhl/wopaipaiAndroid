package cn.wopaipai.ui.my;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cn.catherine.token.http.retrofit.RetrofitFactory;
import cn.catherine.token.tool.json.GsonTool;
import cn.wopaipai.R;
import cn.wopaipai.base.BaseApplication;
import cn.wopaipai.base.BaseAty;
import cn.wopaipai.base.BaseContract;
import cn.wopaipai.base.BasePresenterImp;
import cn.wopaipai.bean.CoinTypeBean;
import cn.wopaipai.bean.request.ContactResponseBean;
import cn.wopaipai.constant.MessageConstants;
import cn.wopaipai.gson.ResponseJson;
import cn.wopaipai.http.HttpApi;
import cn.wopaipai.manager.AppManagerKt;
import cn.wopaipai.tool.LogTool;
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

public class AddContactActivity extends BaseAty implements View.OnClickListener, EasyPermissions.PermissionCallbacks, BaseContract.View {

    private ImageButton back;
    private TextView title;
    private EditText name;
    private EditText walletAddress;
    private TextView coinType;
    private Button configAddress;
    private ImageButton sao;
    private RelativeLayout rlAddContact;
    private List<String> teStrings;

    // 添加联系人
    @Override
    public void getArgs(@NotNull Bundle bundle) {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_add_contact;
    }

    @Override
    public void initViews() {
        back = (ImageButton) findViewById(R.id.ib_back);
        back.setOnClickListener(this);
        title = (TextView) findViewById(R.id.set_title);
        title.setText(getResString(R.string.add_contact));
        name = (EditText) findViewById(R.id.et_name);
        walletAddress = (EditText) findViewById(R.id.et_wallet_address);
        sao = (ImageButton) findViewById(R.id.add_address_sao);
        sao.setOnClickListener(this);
        coinType = (TextView) findViewById(R.id.tv_coin_type);
        coinType.setOnClickListener(this);
        configAddress = (Button) findViewById(R.id.btn_address_config);
        rlAddContact = (RelativeLayout) findViewById(R.id.rl_add_contact);
        configAddress.setOnClickListener(this);
    }

    @Override
    public void initData() {
        AppManagerKt.hideSoftKeyBoardByTouchView(rlAddContact,this);
        // 获取可用币种
        teStrings = new ArrayList<>();
        Observable<ResponseJson> wallet = RetrofitFactory.INSTANCE.getAPIInstance().create(HttpApi.class).getCoinType();
        wallet.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<ResponseJson>() {
                    @Override
                    public void onNext(ResponseJson responseJson) {
                        int code = responseJson.getStatusCode();
                        if (code == MessageConstants.CODE_200) {
                            List<CoinTypeBean> typeBean = GsonTool.INSTANCE.convert(GsonTool.INSTANCE.string(responseJson.getData()), new TypeToken<List<CoinTypeBean>>() {
                            }.getType());
                            LogTool.d(getTag(), typeBean);
                            for (int i = 0; i < typeBean.size(); i++) {
                                String coinCode = typeBean.get(i).getCoinCode();
                                teStrings.add(coinCode);
                            }
                        } else {
                            showToast(new BasePresenterImp().getExceptionInfoByCode(code));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back: // 返回键
                AppManagerKt.returnResult(this, true);
                break;
            case R.id.add_address_sao: // 扫码
                new IntentIntegrator(this)
                        .setOrientationLocked(false)
                        .setCaptureActivity(ScanActivity.class) // 设置自定义的activity是ScanActivity
                        .initiateScan(); // 初始化扫描
                break;
            case R.id.tv_coin_type:  // 选择类型
                AppManagerKt.hideSoftKeyboard(this);
                OptionsPickerView optionsPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        coinType.setText(teStrings.get(options1));
                    }
                }).build();
                optionsPickerView.setPicker(teStrings);
                optionsPickerView.show();
                break;
            case R.id.btn_address_config: // 确认
                if (StringTool.isEmpty(walletAddress.getText().toString())) {
                    showToast(getResources().getString(R.string.input_wallet_address_please));
                    return;
                }
                if (StringTool.isEmpty(name.getText().toString())) {
                    showToast(getResources().getString(R.string.input_name_please));
                    return;
                }
                if (StringTool.isEmpty(coinType.getText().toString())) {
                    showToast(getResources().getString(R.string.input_coinType_please));
                    return;
                } else {
                    showLoading();
                    createContactRequest();
                }
                break;
        }
    }

    private void createContactRequest() {
        // 添加(创建)联系人
        ContactResponseBean contactResponseBean = new ContactResponseBean();
        int PassportId = BaseApplication.Companion.getPassportId();
        String address = walletAddress.getText().toString().trim();
        contactResponseBean.setPassportId(PassportId);   // 通行证ID
        contactResponseBean.setName(name.getText().toString().trim());   // 姓名
        contactResponseBean.setAddress(address);  // 钱包地址
        contactResponseBean.setCoinCode(coinType.getText().toString());  // 币种类型
        contactResponseBean.setSign(MD5.Md5Sign(PassportId + address));  // 加密
        RequestBody requestBody = GsonTool.INSTANCE.beanToRequestBody(contactResponseBean);
        Observable<ResponseJson> contact = RetrofitFactory.INSTANCE.getAPIInstance().create(HttpApi.class).createContact(requestBody);
        contact.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<ResponseJson>() {
                    @Override
                    public void onNext(ResponseJson responseJson) {
                        int code = responseJson.getStatusCode();
                        if (code == MessageConstants.CODE_200) {
                            hideLoading();
                            showToast(getResString(R.string.contact_create_success));
                            name.setText("");
                            walletAddress.setText("");
                            coinType.setText("");
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
//                showShortToast(GetStringUtils.getString(R.string.title_message_null));
            } else {
                AppManagerKt.showToast(getActivity(), getResources().getString(R.string.title_scan_success));
                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
                walletAddress.setText(ScanResult);
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

    @Override
    public void onBackPressed() {
        AppManagerKt.returnResult(this, true);

    }
}
