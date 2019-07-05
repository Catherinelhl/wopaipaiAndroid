package cn.wopaipai.ui.my;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.catherine.token.http.retrofit.RetrofitFactory;
import cn.catherine.token.tool.json.GsonTool;
import cn.wopaipai.R;
import cn.wopaipai.base.BaseApplication;
import cn.wopaipai.base.BaseAty;
import cn.wopaipai.base.BasePresenterImp;
import cn.wopaipai.bean.ExchangeBean;
import cn.wopaipai.bean.WalletExchangeBean;
import cn.wopaipai.bean.request.ExchangeZBBRequestBean;
import cn.wopaipai.constant.MessageConstants;
import cn.wopaipai.event.ExchangeEventBus;
import cn.wopaipai.gson.ResponseJson;
import cn.wopaipai.http.HttpApi;
import cn.wopaipai.tool.ListTool;
import cn.wopaipai.tool.LogTool;
import cn.wopaipai.tool.StringTool;
import cn.wopaipai.tool.decimal.DecimalTool;
import cn.wopaipai.utils.DecimalFormatUtils;
import cn.wopaipai.utils.MD5;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class ExchangeActivity extends BaseAty implements View.OnClickListener {

    private String coinCode;
    private List<ExchangeBean> exchangeList;
    private TextView coinOneBalance;
    private TextView coinTwoBalance;
    private TextView coinTwoExRate;
    private TextView coinOneExRate;
    private Button btnExchange;
    private EditText etInputNumber;
    private TextView tvExchangeNumber;
    private EditText etTransactionPassword;

    // 兑换
    @Override
    public void getArgs(@NotNull Bundle bundle) {
        coinCode = bundle.getString("CoinCode");
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_exchange;
    }

    @Override
    public void initViews() {
        ImageButton back = (ImageButton) findViewById(R.id.ib_back);
        back.setOnClickListener(this);
        TextView title = (TextView) findViewById(R.id.set_title);
        title.setText(getResString(R.string.exchange));
        coinOneBalance = (TextView) findViewById(R.id.tv_coin_one_balance);
        coinTwoBalance = (TextView) findViewById(R.id.tv_coin_two_balance);
        coinOneExRate = (TextView) findViewById(R.id.tv_coin_one_exRate);
        coinTwoExRate = (TextView) findViewById(R.id.tv_coin_two_exRate);
        etInputNumber = (EditText) findViewById(R.id.et_input_number);
        tvExchangeNumber = (TextView) findViewById(R.id.tv_exchange_number);
        etTransactionPassword = (EditText) findViewById(R.id.et_transaction_password);
        btnExchange = (Button) findViewById(R.id.btn_exchange);
        btnExchange.setOnClickListener(this);
    }

    @Override
    public void initData() {
        // 获取兑换信息
        exchangeList = new ArrayList<>();
        int passportId = BaseApplication.Companion.getPassportId();
        String CoinCode = coinCode;
        String sign = MD5.Md5Sign(passportId + CoinCode);
        Observable<ResponseJson> wallet = RetrofitFactory.INSTANCE.getAPIInstance().create(HttpApi.class).getWalletExchange(CoinCode, passportId, sign);
        wallet.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<ResponseJson>() {
                    @Override
                    public void onNext(ResponseJson responseJson) {
                        int code = responseJson.getStatusCode();
                        if (code == MessageConstants.CODE_200) {
//                            WalletResponsesBean responsesBean = GsonTool.INSTANCE.convert(data,WalletResponsesBean.class);
                            WalletExchangeBean responsesBean = GsonTool.INSTANCE.convert(GsonTool.INSTANCE.string(responseJson.getData()), WalletExchangeBean.class);
                            LogTool.d(getTag(), responsesBean);
                            double exchangeRate = responsesBean.getExchangeRate();
//                            totalcoin.setText(DecimalFormatUtils.getDecimalForma(responsesBean.getTotal()));
                            if (!ListTool.isEmpty(responsesBean.getCoins())) {
                                for (int i = 0; i < responsesBean.getCoins().size(); i++) {
                                    ExchangeBean exchangeBean = new ExchangeBean();
                                    exchangeBean.setBalance(responsesBean.getCoins().get(i).getBalance());
                                    exchangeBean.setCoinCode(responsesBean.getCoins().get(i).getCoinCode());
                                    exchangeBean.setExUsdRate(responsesBean.getCoins().get(i).getExUsdRate());
                                    exchangeList.add(exchangeBean);
                                    double balance = exchangeList.get(i).getBalance();
//                                    LogTool.e(getTag(),exchangeList.get(i).getCoinCode());
                                }
                            }
                            coinOneBalance.setText(DecimalTool.transferDisplay(exchangeList.get(1).getBalance()));
                            coinTwoBalance.setText(DecimalTool.transferDisplay(exchangeList.get(0).getBalance()));
                            coinOneExRate.setText(exchangeList.get(0).getCoinCode() + "($)" + DecimalFormatUtils.getDecimalFormaTwo(exchangeList.get(0).getExUsdRate()));
                            coinTwoExRate.setText(exchangeList.get(1).getCoinCode() + "($)" + DecimalFormatUtils.getDecimalFormaTwo(exchangeList.get(1).getExUsdRate()));
                            LogTool.e(getTag(), exchangeList.get(0).getCoinCode() + "\n" + exchangeList.get(1).getCoinCode());

                            // 计算兑换的数量
                            etInputNumber.addTextChangedListener(new TextWatcher() {
                                private int digits = 4;

                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    //删除“.”后面超过2位后的数据
                                    if (s.toString().contains(".")) {
                                        if (s.length() - 1 - s.toString().indexOf(".") > digits) {
                                            s = s.toString().subSequence(0,
                                                    s.toString().indexOf(".") + digits + 1);
                                            etInputNumber.setText(s);
                                            etInputNumber.setSelection(s.length()); //光标移到最后
                                        }
                                    }
                                    //如果"."在起始位置,则起始位置自动补0
                                    if (s.toString().trim().substring(0).equals(".")) {
                                        s = "0" + s;
                                        etInputNumber.setText(s);
                                        etInputNumber.setSelection(2);
                                    }

                                    //如果起始位置为0,且第二位跟的不是".",则无法后续输入
                                    if (s.toString().startsWith("0")
                                            && s.toString().trim().length() > 1) {
                                        if (!s.toString().substring(1, 2).equals(".")) {
                                            etInputNumber.setText(s.subSequence(0, 1));
                                            etInputNumber.setSelection(1);
                                            return;
                                        }
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    String strNum = etInputNumber.getText().toString().trim();
                                    if (!strNum.equals("")) {
                                        tvExchangeNumber.setText(new DecimalFormat("0.0000").format(Float.parseFloat(strNum) * exchangeRate) + "");
                                    } else {
                                        tvExchangeNumber.setText("");
                                    }
                                }
                            });
                            /*=========================计算结束=======================*/

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
            case R.id.btn_exchange:
                if (etInputNumber.getText().toString().equals("")||etInputNumber.getText().toString().equals("0")) {
                    showToast(getResources().getString(R.string.input_number_please));
                    return;
                }
                if (StringTool.isEmpty(etTransactionPassword.getText().toString())) {
                    showToast(getResources().getString(R.string.transaction_password_please));
                    return;
                } else {
                    // 兑换
                    exchange();
                }
                break;
        }
    }

    private void exchange() {
        // 兑换ZBB
        ExchangeZBBRequestBean zbbRequestBean = new ExchangeZBBRequestBean();
        int PassportId = BaseApplication.Companion.getPassportId();
        zbbRequestBean.setPassportId(PassportId);   // 通行证ID
        String inputCoinCode = exchangeList.get(1).getCoinCode();
        zbbRequestBean.setInputCoinCode(inputCoinCode); // 输入币种
        zbbRequestBean.setInputAmount((Double.parseDouble(etInputNumber.getText().toString())));  // 输入数量
        String outputCoinCode = "ZBB";
        zbbRequestBean.setOutputCoinCode(outputCoinCode);  // 输出币种
        String TradePassword = etTransactionPassword.getText().toString().trim() + ".x2019";
        zbbRequestBean.setTradePassword(TradePassword);  // 交易密码
        zbbRequestBean.setSign(MD5.Md5Sign(PassportId + TradePassword + inputCoinCode));  // 加密
        RequestBody requestBody = GsonTool.INSTANCE.beanToRequestBody(zbbRequestBean);
        Observable<ResponseJson> contact = RetrofitFactory.INSTANCE.getAPIInstance().create(HttpApi.class).exchangeZBB(requestBody);
        contact.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<ResponseJson>() {
                    @Override
                    public void onNext(ResponseJson responseJson) {
                        int code = responseJson.getStatusCode();
                        if (code == MessageConstants.CODE_200) {
                            showToast(getResources().getString(R.string.contact_create_success));
                            EventBus.getDefault().post(new ExchangeEventBus());
                            finish();
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
