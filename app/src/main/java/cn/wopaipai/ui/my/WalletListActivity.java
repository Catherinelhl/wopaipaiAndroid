package cn.wopaipai.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.catherine.token.http.retrofit.RetrofitFactory;
import cn.catherine.token.tool.json.GsonTool;
import cn.wopaipai.R;
import cn.wopaipai.adapter.WalletListAdapter;
import cn.wopaipai.base.BaseApplication;
import cn.wopaipai.base.BaseAty;
import cn.wopaipai.base.BaseContract;
import cn.wopaipai.base.BaseException;
import cn.wopaipai.base.BasePresenterImp;
import cn.wopaipai.bean.CoinsBean;
import cn.wopaipai.bean.GetWalletListBean;
import cn.wopaipai.bean.WalletResponsesBean;
import cn.wopaipai.bean.request.GetWalletListRequestBean;
import cn.wopaipai.constant.Constants;
import cn.wopaipai.constant.MessageConstants;
import cn.wopaipai.event.ExchangeEventBus;
import cn.wopaipai.gson.ResponseAnyJson;
import cn.wopaipai.http.HttpApi;
import cn.wopaipai.manager.AppManagerKt;
import cn.wopaipai.tool.ListTool;
import cn.wopaipai.tool.LogTool;
import cn.wopaipai.tool.StringTool;
import cn.wopaipai.tool.decimal.DecimalTool;
import cn.wopaipai.ui.contract.GetUserContract;
import cn.wopaipai.ui.presenter.GetUserWalletPresenterImp;
import cn.wopaipai.utils.CommonPopupWindow;
import cn.wopaipai.utils.DecimalFormatUtils;
import cn.wopaipai.utils.GlideImgManager;
import cn.wopaipai.utils.MD5;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;

public class WalletListActivity extends BaseAty implements BaseContract.View, GetUserContract.View {

    //
    private RecyclerView rclwalletList;
    private ImageButton back;
    private List<GetWalletListBean> getWalletList;
    private WalletListAdapter adapter;
    private Button btn_into;
    private Button btn_exchange;
    private Button btn_turn_out;
    private Button titleRight;
    private CommonPopupWindow popupWindow;
    private TextView title;
    private View vline;
    private String wallet_name;
    private int FlowType = 0;  // FlowType：1(转入) 2(转出) 3(兑换) 4(竞拍) 5(红包) 6(购买) 7(托管) 8(竞拍成功押金扣除)
    private TextView totalNumber;
    int PageSize = 20; // 每页显示的数据
    int PageIndex = 1;   // 页数
    boolean canLoadingMore = false;   // 是否可以加载更多
    private String logUrl;
    private double balance;
    private double exUsdRate;
    private TextView tvRates;
    private ImageView imgPic;
    private RelativeLayout rlNoData;// 如果当前没有数据显示此界面
    private SwipeRefreshLayout srlData;
    private GetUserContract.Presenter presenter;

    // 资产详情
    @Override
    public void getArgs(@NotNull Bundle bundle) {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.aty_wallet_list;
    }

    @Override
    public void initViews() {
        presenter = new GetUserWalletPresenterImp(this);
        Bundle bundle = this.getIntent().getExtras();
        wallet_name = bundle.getString("wallet_name");
        logUrl = bundle.getString("logUrl");
        balance = bundle.getDouble("balance");
        exUsdRate = bundle.getDouble("exUsdRate");
        back = (ImageButton) findViewById(R.id.ib_back);
        title = (TextView) findViewById(R.id.set_title);
        title.setText(wallet_name); // 设置标题
        rclwalletList = (RecyclerView) findViewById(R.id.rcl_wallet_list);
        btn_into = (Button) findViewById(R.id.btn_into);
        btn_turn_out = (Button) findViewById(R.id.btn_turn_out);
        vline = findViewById(R.id.view_line);
        btn_exchange = (Button) findViewById(R.id.btn_exchange);
        titleRight = (Button) findViewById(R.id.title_right);
        titleRight.setText(getResString(R.string.switch_wallet_type));
        totalNumber = (TextView) findViewById(R.id.tv_total_number);
        totalNumber.setText(DecimalTool.transferDisplay((balance)));
        imgPic = (ImageView) findViewById(R.id.img_pic);
        GlideImgManager.displayImage(this, logUrl, imgPic);
        tvRates = (TextView) findViewById(R.id.tv_rates);
        srlData = (SwipeRefreshLayout) findViewById(R.id.srl_data);
        rlNoData = (RelativeLayout) findViewById(R.id.rl_no_data);
        tvRates.setText("≈$" + DecimalFormatUtils.getDecimalFormaTwo(exUsdRate));
        if (wallet_name.equals(MessageConstants.AUCTION_TYPE)) {
            vline.setVisibility(View.GONE);
            btn_exchange.setVisibility(View.GONE);
        } else {
            vline.setVisibility(View.VISIBLE);
            btn_exchange.setVisibility(View.VISIBLE);
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        // 设置加载按钮的形态
        srlData.setColorSchemeResources(
                R.color.button_color,
                R.color.button_color

        );
        srlData.setSize(SwipeRefreshLayout.DEFAULT);
        initRecycle();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onEventBusView(ExchangeEventBus exchangeEventBus) {
        refreshView(false);
    }

    private void initRecycle() {
        getWalletList = new ArrayList<>();
        adapter = new WalletListAdapter(getWalletList);
        rclwalletList.setLayoutManager(new LinearLayoutManager(this));
        rclwalletList.setAdapter(adapter);
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                if (getWalletList.get(position).getFlowType() == 1 || getWalletList.get(position).getFlowType() == 2) {
//                    Intent intent = new Intent(WalletListActivity.this, WalletListDetailsActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                rclwalletList.post(new Runnable() {
                    @Override
                    public void run() {
                        LogTool.d(getTag(), "canLoadingMore:" + canLoadingMore);
                        if (canLoadingMore) {
                            refreshView(true);
                        } else {
                            adapter.loadMoreEnd();
                        }
                    }
                });
            }
        }, rclwalletList);
    }


    @Override
    public void initData() {
        refreshView(false);
    }

    private void refreshView(boolean isLoading) {
        showLoading();
        int passportId = BaseApplication.Companion.getPassportId();
        String CoinCode = wallet_name;
        String sign = MD5.Md5Sign(passportId + CoinCode);
        RetrofitFactory.INSTANCE.getAPIInstance().create(HttpApi.class).
                getWalletList(FlowType, passportId, CoinCode, PageSize, PageIndex, sign)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<ResponseAnyJson<GetWalletListRequestBean>>() {
                    @Override
                    public void onNext(ResponseAnyJson<GetWalletListRequestBean> getWalletListRequestBeanResponseAnyJson) {
                        int code = getWalletListRequestBeanResponseAnyJson.getStatusCode();
                        if (code == MessageConstants.CODE_200) {
                            GetWalletListRequestBean responsesBean = (GetWalletListRequestBean) getWalletListRequestBeanResponseAnyJson.getData();
                            if (responsesBean == null) {
                                return;
                            }
                            if (!isLoading) {
                                getWalletList.clear();

                            }
                            getWalletList.addAll(responsesBean.getData());
                            if (ListTool.isEmpty(getWalletList)) {
                                //如果当前的list数据为空，那么就界面显示没有数据的界面
                                rlNoData.setVisibility(View.VISIBLE);
                                srlData.setVisibility(View.GONE);
                            } else {
                                rlNoData.setVisibility(View.GONE);
                                srlData.setVisibility(View.VISIBLE);
                            }
                            GsonTool.INSTANCE.logInfo(getTag(), MessageConstants.LogInfo.RESPONSE_JSON, "getWalletList:", responsesBean);
                            adapter.notifyDataSetChanged();
                            // 判断当前的请求的数据是否已经等于total，否则还有可以继续请求的空间
                            canLoadingMore = (PageIndex * PageSize) < responsesBean.getTotal();
                            if (canLoadingMore) {
                                PageIndex++;
                            } else {
                                if (isLoading) {
                                    showToast(getString(R.string.no_more_data));
                                    adapter.loadMoreEnd();
                                    adapter.setEnableLoadMore(false);
                                }

                            }
                        } else {
                            showToast(new BasePresenterImp().getExceptionInfoByCode(code));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseException.INSTANCE.print(e);
                        hideLoading();
                        LogTool.e(getTag(), e.getMessage());
                        adapter.loadMoreFail();
                        adapter.setEnableLoadMore(true);
                        adapter.loadMoreComplete();

                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                        adapter.loadMoreComplete();
                    }
                });


    }

    @Override
    public void initListener() {
        srlData.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlData.setRefreshing(false);
                resetLoadingParams();
                presenter.getUserWallet();
            }
        });
        RxView.clicks(back).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    AppManagerKt.returnResult(this, true);
                });
        btn_into.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //转入
                Intent intent = new Intent(WalletListActivity.this, WalletIntoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("coin_type", wallet_name);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn_turn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //转出
                Intent intent = new Intent(WalletListActivity.this, WalletTurnOutActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("coin_type", wallet_name);
                bundle.putDouble("balance", balance);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //兑换
                Intent intent = new Intent(WalletListActivity.this, ExchangeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("CoinCode", wallet_name);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        RxView.clicks(titleRight).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        initFilter();

                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseException.INSTANCE.print(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void resetLoadingParams() {
        PageIndex = 1;
        canLoadingMore = false;
        adapter.setEnableLoadMore(true);
        refreshView(false);
    }

    @Override
    public void getUserWalletSuccess(@NotNull WalletResponsesBean walletResponsesBean) {
        if (walletResponsesBean != null) {
            List<CoinsBean> coins = walletResponsesBean.getCoins();
            if (ListTool.noEmpty(coins)) {
                for (CoinsBean coinsBean : coins) {
                    String name = coinsBean.getCoinCode();
                    if (StringTool.equals(name, MessageConstants.AUCTION_TYPE)) {
                        //存储当前的余额

                        BaseApplication.Companion.setBalance(coinsBean.getBalance());
                    }
                    if (StringTool.equals(name, wallet_name)) {
                        // 如果是当前的币种，那么取出余额进行显示
                        if (totalNumber != null) {
                            totalNumber.setText(DecimalTool.transferDisplay((coinsBean.getBalance())));
                        }
                    }
                }
            }
        }


    }

    @Override
    public void getUserWalletFailure(@NotNull String msg) {
        LogTool.e(getTag(), msg);
    }

    private void initFilter() {
        popupWindow = new CommonPopupWindow.Builder(WalletListActivity.this)
                .setView(R.layout.dialog_screen_wallet_list)  // 设置popupwindow布局
                .setBackGroundLevel(0.5f)   // 设置背景深度
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) // 设置显示的宽高
                .setAnimationStyle(R.style.marquee_style)  // 设置动画
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {  // 布局内的点击事件
                    @Override
                    public void getChildView(final View view, int layoutResId) {
                        // FlowType：0(全部) 1(转入) 2(转出) 3(兑换) 4(竞拍) 5(红包) 6(购买) 7(托管) 8(竞拍成功押金扣除)
                        Button btn_all = (Button) view.findViewById(R.id.btn_screen_all);  // 全部
                        Button btn_into = (Button) view.findViewById(R.id.btn_screen_into); // 转入
                        Button btn_turn_out = (Button) view.findViewById(R.id.btn_screen_turn_out); // 转出
                        Button btn_jingpai = (Button) view.findViewById(R.id.btn_screen_jingpai);  // 竞拍
                        Button btn_trusteeship = (Button) view.findViewById(R.id.btn_screen_host); // 托管
                        Button btn_exchange = (Button) view.findViewById(R.id.btn_screen_shop); // 购买
                        Button btn_shop = (Button) view.findViewById(R.id.btn_screen_exchange); // 兑换
                        switch (FlowType) {
                            case 0:
                                btn_all.setBackground(getResources().getDrawable(R.drawable.btn_screen_bg));
                                break;
                            case 1:
                                btn_into.setBackground(getResources().getDrawable(R.drawable.btn_screen_bg));
                                break;
                            case 2:
                                btn_turn_out.setBackground(getResources().getDrawable(R.drawable.btn_screen_bg));
                                break;
                            case 3:
                                btn_exchange.setBackground(getResources().getDrawable(R.drawable.btn_screen_bg));
                                break;
                            case 4:
                                btn_jingpai.setBackground(getResources().getDrawable(R.drawable.btn_screen_bg));
                                break;
                            case 6:
                                btn_shop.setBackground(getResources().getDrawable(R.drawable.btn_screen_bg));
                                break;
                            case 7:
                                btn_trusteeship.setBackground(getResources().getDrawable(R.drawable.btn_screen_bg));
                                break;


                        }
                        // 全部
                        btn_all.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FlowType = 0;
                                resetLoadingParams();
                                popupWindow.dismiss();
                            }
                        });
                        // 转入
                        btn_into.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FlowType = 1;
                                resetLoadingParams();
                                popupWindow.dismiss();
                            }
                        });
                        // 转出
                        btn_turn_out.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FlowType = 2;
                                resetLoadingParams();
                                popupWindow.dismiss();
                            }
                        });
                        // 兑换
                        btn_exchange.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FlowType = 3;
                                resetLoadingParams();
                                popupWindow.dismiss();
                            }
                        });
                        // 竞拍，红包，竞拍成功押金扣除
                        btn_jingpai.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FlowType = 4;
                                resetLoadingParams();
                                popupWindow.dismiss();
                            }
                        });
                        // 购买
                        btn_shop.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FlowType = 6;
                                resetLoadingParams();
                                popupWindow.dismiss();
                            }
                        });

                        // 托管
                        btn_trusteeship.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FlowType = 7;
                                resetLoadingParams();
                                popupWindow.dismiss();
                            }
                        });

                    }
                })
                .create();
        popupWindow.showAsDropDown(titleRight);

    }

    @Override
    public void onBackPressed() {
        AppManagerKt.returnResult(this, true);

    }
}
