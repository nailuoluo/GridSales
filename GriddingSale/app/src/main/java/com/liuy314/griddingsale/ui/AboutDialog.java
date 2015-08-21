package com.liuy314.griddingsale.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.liuy314.griddingsale.R;

/**
 * Created by liuy314 on 2015/8/18.
 */
public class AboutDialog extends DialogFragment {

    private View mAboutDialogView;
    private LinearLayout mEasterLayout;
    private ImageView mEasterEggImageView;
    private int mEasterEggSwitchNum = 30;
    private Toast mEasterEggToast;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mAboutDialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_about, null);
        mEasterLayout = (LinearLayout)mAboutDialogView.findViewById(R.id.easter_egg_layout);
        mEasterLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(mEasterEggSwitchNum >=0) {
                    mEasterEggSwitchNum--;
                }
                if(mEasterEggSwitchNum <= 5 && mEasterEggImageView.getVisibility() == View.GONE) {
                    if(mEasterEggToast == null) {
                        mEasterEggToast = Toast.makeText(getActivity(), "Left " + mEasterEggSwitchNum + "", Toast.LENGTH_SHORT);
                    }
                    mEasterEggToast.setText("Left " + mEasterEggSwitchNum + "");
                    mEasterEggToast.show();
                }
                if(mEasterEggSwitchNum == 0 && mEasterEggImageView.getVisibility() == View.GONE) {
                    mEasterEggImageView.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        mEasterEggImageView = (ImageView)mAboutDialogView.findViewById(R.id.easter_egg_imageview);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(mAboutDialogView)
                .setTitle("关于"+getResources().getString(R.string.version)+getResources().getString(R.string.version_num))
                .setPositiveButton(getResources().getText(R.string.btn_name_confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AboutDialog.this.dismiss();
                    }
                });
        return builder.create();
    }


}
