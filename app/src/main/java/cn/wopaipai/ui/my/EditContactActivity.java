package cn.wopaipai.ui.my;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cn.catherine.token.http.retrofit.RetrofitFactory;
import cn.catherine.token.tool.json.GsonTool;
import cn.wopaipai.R;
import cn.wopaipai.base.BaseApplication;
import cn.wopaipai.base.BaseAty;
import cn.wopaipai.base.BasePresenterImp;
import cn.wopaipai.bean.CoinTypeBean;
import cn.wopaipai.bean.request.ContactResponseBean;
import cn.wopaipai.constant.MessageConstants;
import cn.wopaipai.gson.ResponseJson;
import cn.wopaipai.http.HttpApi;
import cn.wopaipai.tool.ListTool;
import cn.wopaipai.tool.LogTool;
import cn.wopaipai.tool.StringTool;
import cn.wopaipai.utils.MD5;
import cn.wopaipai.utils.ScanActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class EditContactActivity extends BaseAty implements View.OnClickListener {

    private int id;
    private String name;
    private String address;
    private String coinCode;
    private List<String> coinCodeList;
    private EditText etName;
    private EditText etAddress;
    private TextView tvCoinType;

    // 编辑联系人
    @Override
    public void getArgs(@NotNull Bundle bundle) {
        id = bundle.getInt("id");
        name = bundle.getString("name");
        address = bundle.getString("address");
        coinCode = bundle.getString("coinCode");
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_edit_contact;
    }

    @Override
    public void initViews() {
        ImageButton back = (ImageButton) findViewById(R.id.ib_back);
        back.setOnClickListener(this);
        TextView title = (TextView) findViewById(R.id.set_title);
        title.setText(getResString(R.string.edit_contact));
        ImageButton sao = (ImageButton) findViewById(R.id.add_address_sao);
        sao.setOnClickListener(this);
        Button btn_contact = (Button) findViewById(R.id.btn_address_config);
        btn_contact.setOnClickListener(this);
        etName = (EditText) findViewById(R.id.et_name);
        etAddress = (EditText) findViewById(R.id.et_wallet_address);
        tvCoinType = (TextView) findViewById(R.id.tv_coin_type);
        tvCoinType.setOnClickListener(this);
        etName.setText(name);
        etAddress.setText(address);
        tvCoinType.setText(coinCode);
    }

    @Override
    public void initData() {
        // 获取可用币种
        coinCodeList=new ArrayList<>();
        Observable<ResponseJson> wallet = RetrofitFactory.INSTANCE.getAPIInstance().create(HttpApi.class).getCoinType();
        wallet.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<ResponseJson>() {
                    @Override
                    public void onNext(ResponseJson responseJson) {
                        int code=responseJson.getStatusCode();
                        if ( code== MessageConstants.CODE_200) {
                            List<CoinTypeBean> typeBean =  GsonTool.INSTANCE.convert(GsonTool.INSTANCE.string(responseJson.getData()) ,new TypeToken<List<CoinTypeBean>>(){}.getType());
                            LogTool.d(getTag(),typeBean);
                            if (!ListTool.isEmpty(typeBean)) {
                                for (int i = 0; i < typeBean.size(); i++) {
                                    String coinCode = typeBean.get(i).getCoinCode();
                                    coinCodeList.add(coinCode);
                                }
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
    protected void onResume() {
        super.onResume();
        initData();
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
            case R.id.add_address_sao:
                new IntentIntegrator(this)
                        .setOrientationLocked(false)
                        .setCaptureActivity(ScanActivity.class) // 设置自定义的activity是ScanActivity
                        .initiateScan(); // 初始化扫描
                break;
            case R.id.tv_coin_type:
                OptionsPickerView optionsPickerView=new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvCoinType.setText(coinCodeList.get(options1));
                    }
                }).build();
                optionsPickerView.setPicker(coinCodeList);
                optionsPickerView.show();
            case R.id.btn_address_config:
                // 确定修改接口
                if (StringTool.isEmpty(etAddress.getText().toString())){
                    showToast(getResString(R.string.input_wallet_address_please));
                    return;
                }
                if (StringTool.isEmpty(etName.getText().toString())){
                    showToast(getResources().getString(R.string.input_name_please));
                    return;
                }
                if (StringTool.isEmpty(tvCoinType.getText().toString())){
                    showToast(getResources().getString(R.string.input_coinType_please));
                    return;
                }else {
                    updateContactRequest();
                }
                break;
        }
    }

    private void updateContactRequest() {
        // 编辑(更新)联系人
        ContactResponseBean contactResponseBean=new ContactResponseBean();
        int PassportId= BaseApplication.Companion.getPassportId();
        String contactName=etName.getText().toString().trim();
        contactResponseBean.setPassportId(PassportId);   // 通行证ID
        contactResponseBean.setId(id);  // 标识ID
        contactResponseBean.setName(contactName);   // 姓名
        contactResponseBean.setAddress(etAddress.getText().toString().trim());  // 钱包地址
        contactResponseBean.setCoinCode(tvCoinType.getText().toString());  // 币种类型
        contactResponseBean.setSign(MD5.Md5Sign(PassportId+contactName+id));  // 加密
        RequestBody requestBody = GsonTool.INSTANCE.beanToRequestBody(contactResponseBean);
        Observable<ResponseJson> contact = RetrofitFactory.INSTANCE.getAPIInstance().create(HttpApi.class).updateContact(requestBody);
        contact.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<ResponseJson>() {
                    @Override
                    public void onNext(ResponseJson responseJson) {
                        int code=responseJson.getStatusCode();
                        if (code == MessageConstants.CODE_200) {
                            showToast(getResources().getString(R.string.contact_create_success));
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
}
