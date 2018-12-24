package xyz.viseator.anonymouscard.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import xyz.viseator.anonymouscard.R;

/**
 * Created by yanhao on 16-12-23.
 */

public class CustomDialog extends Dialog
                            implements View.OnClickListener{
    Context mContext;
    private Button sureBtn,cancelBtn;
    MainFragment fragment;
    public CustomDialog(Context context) {
        super(context);
        mContext=context;
    }

    public CustomDialog(Context context,int theme,MainFragment fragment){
        super(context,theme);
        this.mContext=context;
        this.fragment=fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog);
        LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout=inflater.inflate(R.layout.layout_dialog,null);
        sureBtn=(Button)findViewById(R.id.savebtn);
        cancelBtn=(Button)findViewById(R.id.cancelbtn);
        sureBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.savebtn:
                fragment.sendRequest();
                cancel();
                break;
            case R.id.cancelbtn:
                cancel();
                break;
        }
    }
}
