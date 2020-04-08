package com.titi.remotbayi.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.titi.remotbayi.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GeneralDoubleButtonDialog {

  @BindView(R.id.caption)
  TextView textContent;
  @BindView(R.id.button_ok)
  Button buttonYes;
  @BindView(R.id.button_cancel)
  Button buttonCancel;
  private Builder alertDialogBuilder;

  public GeneralDoubleButtonDialog(final GeneralDoubleDialogBuilder generalDoubleDialogBuilder) {
    alertDialogBuilder = new Builder(generalDoubleDialogBuilder.context);

    LayoutInflater inflater = generalDoubleDialogBuilder.context.getLayoutInflater();

    final View view = inflater.inflate(R.layout.general_dialog_with_two_button, null);

    ButterKnife.bind(this, view);

    textContent.setText(generalDoubleDialogBuilder.caption);

    buttonYes.setText(generalDoubleDialogBuilder.yes_button);

    buttonCancel.setText(generalDoubleDialogBuilder.cancel_button);

    alertDialogBuilder.setView(view);

    final AlertDialog alertDialog = alertDialogBuilder.create();

    buttonYes.setOnClickListener(view1 -> {
      generalDoubleDialogBuilder.onActionDialog.onYesClickListener();
      alertDialog.dismiss();
    });

    buttonCancel.setOnClickListener(view12 -> {
      generalDoubleDialogBuilder.onActionDialog.onCancelListener();
      alertDialog.dismiss();
    });

    alertDialog.show();
  }

  public interface OnActionDialog {

    void onYesClickListener();

    void onCancelListener();

  }

  public static class GeneralDoubleDialogBuilder {

    private AppCompatActivity context;

    private String caption = "";
    private String yes_button = "OK";
    private String cancel_button = "CANCEL";

    private OnActionDialog onActionDialog = new OnActionDialog() {
      @Override
      public void onYesClickListener() {

      }

      @Override
      public void onCancelListener() {

      }
    };

    public GeneralDoubleDialogBuilder(AppCompatActivity context) {
      this.context = context;
    }


    public GeneralDoubleDialogBuilder setCaption(String caption) {
      this.caption = caption;
      return this;
    }

    public GeneralDoubleDialogBuilder setYesButton(String yes_button) {
      this.yes_button = yes_button;
      return this;
    }

    public GeneralDoubleDialogBuilder setCancelButton(String cancel_button) {
      this.cancel_button = cancel_button;
      return this;
    }

    public GeneralDoubleDialogBuilder setOnActionDialog(
        OnActionDialog onActionDialog) {
      this.onActionDialog = onActionDialog;
      return this;
    }

    public GeneralDoubleButtonDialog build() {
      return new GeneralDoubleButtonDialog(this);
    }
  }

}
