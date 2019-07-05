package cn.wopaipai.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wopaipai.R;

public class IntelligentHostingOpenDialog extends Dialog implements View.OnClickListener {
    Activity activity;
    @BindView(R.id.et_dialog_pwd)
    EditText etPassword;
    private Button btnCannel;
    private Button btnSure;
    private EditText etPwd;

    public IntelligentHostingOpenDialog(Context context) {
        super(context);
        activity = (Activity) context;
    }

    @Override
    public void create() {
        super.create();
        initDialog();
        etPwd = (EditText) findViewById(R.id.et_dialog_pwd);
        btnCannel = (Button) findViewById(R.id.btn_dialog_cancel);
        btnCannel.setOnClickListener(this);
        btnSure = (Button) findViewById(R.id.btn_dialog_sure);
        btnSure.setOnClickListener(this);
    }

    private void initDialog() {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View viewDialog = inflater.inflate(R.layout.smartost_dialog, null);
        Display display = activity.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
//设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams((int) (width*0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setContentView(viewDialog, layoutParams);
    }

    SureInterface sureInterface;

    public interface SureInterface {
        void getSure(String passWord);
        void max(EditText etNum);
    }

    public void setSure(SureInterface sureInterface) {
        this.sureInterface = sureInterface;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dialog_cancel:
                dismiss();
                break;
            case R.id.btn_dialog_sure:
                dismiss();
                sureInterface.getSure(etPwd.getText().toString().trim());
                break;
        }
    }
}
