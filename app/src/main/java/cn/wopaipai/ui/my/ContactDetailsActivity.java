package cn.wopaipai.ui.my;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import cn.catherine.token.http.retrofit.RetrofitFactory;
import cn.catherine.token.tool.json.GsonTool;
import cn.wopaipai.R;
import cn.wopaipai.base.BaseApplication;
import cn.wopaipai.base.BaseAty;
import cn.wopaipai.base.BasePresenterImp;
import cn.wopaipai.bean.request.ContactResponseBean;
import cn.wopaipai.constant.MessageConstants;
import cn.wopaipai.event.ChoiceContactAddressEventBus;
import cn.wopaipai.gson.ResponseJson;
import cn.wopaipai.http.HttpApi;
import cn.wopaipai.utils.MD5;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class ContactDetailsActivity extends BaseAty implements View.OnClickListener {

    private Button delete_contact;
    private TextView name;
    private TextView address;
    private TextView coinType;
    private int contactId;
    private String contactName;
    private String contactAddress;
    private String contactCoinType;
    private Button btnSureContact;

    // 联系人详情
    @Override
    public void getArgs(@NotNull Bundle bundle) {
        bundle=this.getIntent().getExtras();
        contactId = bundle.getInt("contact_id");
        contactName = bundle.getString("contact_name");
        contactAddress = bundle.getString("contact_address");
        contactCoinType = bundle.getString("contact_coin_type");
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_contact_details;
    }

    @Override
    public void initViews() {
        ImageButton back = (ImageButton) findViewById(R.id.ib_back);
        back.setOnClickListener(this);
        TextView title = (TextView) findViewById(R.id.set_title);
        title.setText(getResources().getString(R.string.contact_details));
        Button titleRight = (Button) findViewById(R.id.title_right);
        titleRight.setText(getResources().getString(R.string.edit));
        titleRight.setOnClickListener(this);
        name = (TextView) findViewById(R.id.tv_contact_name);
        address = (TextView) findViewById(R.id.tv_contact_address);
        coinType = (TextView) findViewById(R.id.tv_coin_type);
        delete_contact = (Button) findViewById(R.id.btn_delete_contact);
        btnSureContact = (Button) findViewById(R.id.btn_sure_contact);
        btnSureContact.setOnClickListener(this);
        delete_contact.setOnClickListener(this);
    }

    @Override
    public void initData() {
        name.setText(contactName);
        address.setText(contactAddress);
        coinType.setText(contactCoinType);
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
                Intent intent=new Intent(this,EditContactActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("id",contactId);
                bundle.putString("name",contactName);
                bundle.putString("address",contactAddress);
                bundle.putString("coinCode",contactCoinType);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.btn_delete_contact:
                deleteContactRequest();
                break;
            case R.id.btn_sure_contact:
                intent=new Intent(this,WalletTurnOutActivity.class);
                startActivity(intent);
                EventBus.getDefault().post(new ChoiceContactAddressEventBus(contactAddress));
                finish();
                break;
        }
    }

    private void deleteContactRequest() {
        // 删除联系人
        ContactResponseBean contactResponseBean=new ContactResponseBean();
        int id=contactId;
        int PassportId= BaseApplication.Companion.getPassportId();
        contactResponseBean.setPassportId(PassportId);   // 通行证ID
        contactResponseBean.setId(contactId);  // 标识ID
        contactResponseBean.setName(name.getText().toString());   // 姓名
        contactResponseBean.setAddress(contactAddress);  // 钱包地址
        contactResponseBean.setCoinCode(contactCoinType);  // 币种类型
        contactResponseBean.setSign(MD5.Md5Sign(PassportId+""+id));  // 加密
        RequestBody requestBody = GsonTool.INSTANCE.beanToRequestBody(contactResponseBean);
        Observable<ResponseJson> contact = RetrofitFactory.INSTANCE.getAPIInstance().create(HttpApi.class).deleteContact(requestBody);
        contact.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<ResponseJson>() {
                    @Override
                    public void onNext(ResponseJson responseJson) {
                        int code=responseJson.getStatusCode();
                        if (code== MessageConstants.CODE_200) {
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
