package cn.wopaipai.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import cn.wopaipai.R;

public class ScreenWalletListDialog extends Dialog {
    Activity activity;
    String title;

    public ScreenWalletListDialog(Context context, String title) {
        super(context, R.style.ShareDialog);
        activity = (Activity) context;
        this.title = title;
    }

    @Override
    public void create() {
        super.create();
        initDialog();
//        ButterKnife.bind(this);
//        setTitle.setText(title);
//        titleRight.setText(GetStringUtils.getString(R.string.screen));
    }

    private void initDialog() {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View viewDialog = inflater.inflate(R.layout.dialog_screen_wallet_list, null);
        Display display = activity.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
//设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams((int) (width), ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setContentView(viewDialog, layoutParams);

    }

    ScreenClick click;

//    @OnClick(R.id.ib_back)
//    public void onViewClicked() {
//    }

    public interface ScreenClick {
        void setOnclick(int i);
    }

    public void setScreenClick(ScreenClick click) {
        this.click = click;
    }

//    @OnClick({R.id.btn_screen_all, R.id.btn_screen_into, R.id.btn_screen_turn_out, R.id.btn_screen_trusteeship, R.id.btn_screen_exchange, R.id.ib_back,R.id.btn_candy})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.btn_screen_all:
//                click.setOnclick(0);
//                break;
//            case R.id.btn_screen_into:
//                click.setOnclick(1);
//                break;
//            case R.id.btn_screen_turn_out:
//                click.setOnclick(2);
//                break;
//            case R.id.btn_screen_trusteeship:
//                click.setOnclick(3);
//                break;
//            case R.id.btn_screen_exchange:
//                click.setOnclick(4);
//                break;
////            case R.id.btn_candy:
////                click.setOnclick(5);
////                break;
//        }
//        dismiss();
//    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.TOP;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);

    }
}
