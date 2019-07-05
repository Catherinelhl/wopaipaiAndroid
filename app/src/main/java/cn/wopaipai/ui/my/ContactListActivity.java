package cn.wopaipai.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cn.catherine.token.http.retrofit.RetrofitFactory;
import cn.catherine.token.tool.json.GsonTool;
import cn.wopaipai.R;
import cn.wopaipai.adapter.ContactListAdapter;
import cn.wopaipai.base.BaseApplication;
import cn.wopaipai.base.BaseAty;
import cn.wopaipai.base.BasePresenterImp;
import cn.wopaipai.bean.ContactDetailsBean;
import cn.wopaipai.bean.UserResultBean;
import cn.wopaipai.bean.request.ContactResponseBean;
import cn.wopaipai.constant.Constants;
import cn.wopaipai.constant.MessageConstants;
import cn.wopaipai.gson.ResponseJson;
import cn.wopaipai.http.HttpApi;
import cn.wopaipai.tool.ListTool;
import cn.wopaipai.tool.LogTool;
import cn.wopaipai.utils.MD5;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;

public class ContactListActivity extends BaseAty implements View.OnClickListener {

    private ImageButton back;
    private TextView title;
    private ImageButton title_right;
    private List<ContactDetailsBean> contactList;
    private RecyclerView rcl_contact;

    // 联系人列表
    @Override
    public void getArgs(@NotNull Bundle bundle) {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_contact_list;
    }

    @Override
    public void initViews() {
        back = (ImageButton) findViewById(R.id.ib_back);
        back.setOnClickListener(this);
        title = (TextView) findViewById(R.id.set_title);
        title.setText(getResources().getString(R.string.contact));
        title_right = (ImageButton) findViewById(R.id.title_right);
        title_right.setOnClickListener(this);
        rcl_contact = (RecyclerView) findViewById(R.id.rcl_contact);
    }

    @Override
    public void initData() {
        int clickType = 1;
        // 获取(查询)联系人
        contactList = new ArrayList<>();
        ContactListAdapter adapter = new ContactListAdapter(R.layout.recycler_contacts_list, contactList);
        rcl_contact.setLayoutManager(new LinearLayoutManager(ContactListActivity.this));
        rcl_contact.setAdapter(adapter);
        int passportid = BaseApplication.Companion.getPassportId();
        String sign = MD5.Md5Sign(passportid + "");
        UserResultBean userInfoBean = BaseApplication.Companion.getUserInfoBean();
        LogTool.e(getTag(), userInfoBean + "\t\t" + passportid);
        Observable<ResponseJson> contact = RetrofitFactory.INSTANCE.getAPIInstance().create(HttpApi.class).getContact(passportid, sign);
        contact.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<ResponseJson>() {
                    @Override
                    public void onNext(ResponseJson responseJson) {
                        int code = responseJson.getStatusCode();
                        if (code == MessageConstants.CODE_200) {
                            Object data = responseJson.getData();
                            List<ContactResponseBean> responsesBean = GsonTool.INSTANCE.convert(GsonTool.INSTANCE.string(data), new TypeToken<List<ContactResponseBean>>() {
                            }.getType());
                            LogTool.d(getTag(), responsesBean);
                            if (!ListTool.isEmpty(responsesBean)) {
                                for (int i = 0; i < responsesBean.size(); i++) {
                                    ContactDetailsBean detailsBean = new ContactDetailsBean();
                                    detailsBean.setPassportId(responsesBean.get(i).getPassportId());
                                    detailsBean.setCoinCode(responsesBean.get(i).getCoinCode());
                                    detailsBean.setAddress(responsesBean.get(i).getAddress());
                                    detailsBean.setName(responsesBean.get(i).getName());
                                    detailsBean.setId(responsesBean.get(i).getId());
                                    contactList.add(detailsBean);
                                }
                                adapter.notifyDataSetChanged();
                                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        Intent intent = new Intent(ContactListActivity.this, ContactDetailsActivity.class);
                                        int id = contactList.get(position).getId();
                                        String contact_name = contactList.get(position).getName();
                                        String contact_address = contactList.get(position).getAddress();
                                        String contact_coin_type = contactList.get(position).getCoinCode();
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("clicktype", clickType);
                                        bundle.putInt("contact_id", id);
                                        bundle.putString("contact_name", contact_name);
                                        bundle.putString("contact_address", contact_address);
                                        bundle.putString("contact_coin_type", contact_coin_type);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                });
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
            case R.id.ib_back:
                finish();
                break;
            case R.id.title_right:
                Intent intent = new Intent(this, AddContactActivity.class);
                startActivityForResult(intent, Constants.RequestCode.ADD_CONTACT_REQUEST_CODE);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.ADD_CONTACT_REQUEST_CODE:
                initData();
                break;
        }
    }
}
