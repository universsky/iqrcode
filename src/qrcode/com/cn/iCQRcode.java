package qrcode.com.cn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class iCQRcode extends Activity {

    public RadioGroup mRadioGroup1;
    public RadioButton mRadioname, mRadiosms, mRadioemail, mRadiotext, mRadiourl, mRadiofree;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jumpLayoutMain();

    }

    public void jumpLayoutMain() {
        setContentView(R.layout.main);
        mRadioGroup1 = (RadioGroup) findViewById(R.id.select);
        mRadioname = (RadioButton) findViewById(R.id.select1);
        mRadiosms = (RadioButton) findViewById(R.id.select2);
        mRadioemail = (RadioButton) findViewById(R.id.select3);
        mRadiotext = (RadioButton) findViewById(R.id.select4);
        mRadiourl = (RadioButton) findViewById(R.id.select5);
        mRadiofree = (RadioButton) findViewById(R.id.select6);
        mRadioGroup1.setOnCheckedChangeListener(mChangeRadio);
    }

    private RadioGroup.OnCheckedChangeListener mChangeRadio = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == mRadioname.getId()) {
                jumpLayoutBusinessCard();

            } else if (checkedId == mRadiosms.getId()) {
                jumpLayoutSms();
            } else if (checkedId == mRadioemail.getId()) {
                jumpLayoutEmail();
            } else if (checkedId == mRadiotext.getId()) {
                jumpLayoutText();
            } else if (checkedId == mRadiourl.getId()) {
                jumpLayoutUrl();
            } else {
                jumpLayoutFree();
            }
        }
    };

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, R.string.app_about);
        menu.add(0, 1, 1, R.string.str_exit);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case 0:
                openOptionsDialog();
                break;
            case 1:
                finish();
                break;
        }
        return true;
    }

    private void openOptionsDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_about)
                .setMessage(R.string.app_about_msg)
                .setPositiveButton(R.string.str_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                            }
                        }
                )
                .show();
    }

    //跳到名片界面
    public void jumpLayoutBusinessCard() {
        setContentView(R.layout.businesscard);
        Button back1 = (Button) findViewById(R.id.back);
        Button draw = (Button) findViewById(R.id.drawqr);
        back1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                jumpLayoutMain();
            }
        });
        draw.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //获取名片信息
                EditText name1 = (EditText) findViewById(R.id.name);
                EditText occupation1 = (EditText) findViewById(R.id.occupation);
                EditText fixedphone1 = (EditText) findViewById(R.id.fixedphone);
                EditText mobilephone1 = (EditText) findViewById(R.id.mobilephone);
                String name2 = name1.getText().toString();
                String occupation2 = occupation1.getText().toString();
                String fixedphone2 = fixedphone1.getText().toString();
                String mobilephone2 = mobilephone1.getText().toString();

                String sort = "名片";
                char sort1 = 'N';
                Intent intent = new Intent();
                intent.setClass(iCQRcode.this, DrawQRCode.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name2);
                bundle.putString("occupation", occupation2);
                bundle.putString("fixedphone", fixedphone2);
                bundle.putString("mobilephone", mobilephone2);
                bundle.putString("sort", sort);
                bundle.putChar("sort1", sort1);
                intent.putExtras(bundle);
                startActivity(intent);
                iCQRcode.this.finish();

            }
        });
    }

    //跳到短信界面
    public void jumpLayoutSms() {
        setContentView(R.layout.sms);
        Button back1 = (Button) findViewById(R.id.back);
        Button draw = (Button) findViewById(R.id.drawqr);
        back1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                jumpLayoutMain();
            }
        });
        draw.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //获取短信信息
                EditText phonenumber1 = (EditText) findViewById(R.id.phonenumber);
                EditText text1 = (EditText) findViewById(R.id.text);
                String phonenumber2 = phonenumber1.getText().toString();
                String text2 = text1.getText().toString();
                String sort = "短信";
                char sort1 = 'M';
                Intent intent = new Intent();
                intent.setClass(iCQRcode.this, DrawQRCode.class);
                Bundle bundle = new Bundle();
                bundle.putString("phonenameber", phonenumber2);
                bundle.putString("text", text2);
                bundle.putString("sort", sort);
                bundle.putChar("sort1", sort1);
                intent.putExtras(bundle);
                startActivity(intent);
                iCQRcode.this.finish();
            }
        });
    }

    //跳到电子邮件界面
    public void jumpLayoutEmail() {
        setContentView(R.layout.email);
        Button back1 = (Button) findViewById(R.id.back);
        Button draw = (Button) findViewById(R.id.drawqr);
        back1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                jumpLayoutMain();
            }
        });
        draw.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //获取电子邮件信息
                EditText email1 = (EditText) findViewById(R.id.email);
                EditText theme1 = (EditText) findViewById(R.id.theme);
                EditText text1 = (EditText) findViewById(R.id.text);
                String email2 = email1.getText().toString();
                String theme2 = theme1.getText().toString();
                String text2 = text1.getText().toString();
                String sort = "Email";
                char sort1 = 'E';
                Intent intent = new Intent();
                intent.setClass(iCQRcode.this, DrawQRCode.class);
                Bundle bundle = new Bundle();
                bundle.putString("email", email2);
                bundle.putString("text", text2);
                bundle.putString("theme", theme2);
                bundle.putString("sort", sort);
                bundle.putChar("sort1", sort1);
                intent.putExtras(bundle);
                startActivity(intent);
                iCQRcode.this.finish();
            }
        });
    }

    //跳到文本界面
    public void jumpLayoutText() {
        setContentView(R.layout.text);
        Button back1 = (Button) findViewById(R.id.back);
        Button draw = (Button) findViewById(R.id.drawqr);
        back1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                jumpLayoutMain();
            }
        });
        draw.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //获取文本信息
                EditText title1 = (EditText) findViewById(R.id.title);
                EditText text1 = (EditText) findViewById(R.id.text);
                String title2 = title1.getText().toString();
                String text2 = text1.getText().toString();
                String sort = "便签";
                char sort1 = 'T';
                Intent intent = new Intent();
                intent.setClass(iCQRcode.this, DrawQRCode.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", title2);
                bundle.putString("text", text2);
                bundle.putString("sort", sort);
                bundle.putChar("sort1", sort1);
                intent.putExtras(bundle);
                startActivity(intent);
                iCQRcode.this.finish();
            }
        });
    }

    //跳到网络书签
    public void jumpLayoutUrl() {
        setContentView(R.layout.url);
        Button back1 = (Button) findViewById(R.id.back);
        Button draw = (Button) findViewById(R.id.drawqr);
        back1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                jumpLayoutMain();
            }
        });
        draw.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //获取信息
                EditText title1 = (EditText) findViewById(R.id.title);
                EditText url1 = (EditText) findViewById(R.id.url);
                String title2 = title1.getText().toString();
                String url2 = url1.getText().toString();
                String sort = "链接";
                char sort1 = 'U';
                Intent intent = new Intent();
                intent.setClass(iCQRcode.this, DrawQRCode.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", title2);
                bundle.putString("url", url2);
                bundle.putString("sort", sort);
                bundle.putChar("sort1", sort1);
                intent.putExtras(bundle);
                startActivity(intent);
                iCQRcode.this.finish();
            }
        });
    }


    // 跳到自由文本区
    public void jumpLayoutFree() {
        setContentView(R.layout.free);
        Button back1 = (Button) findViewById(R.id.back);
        Button draw = (Button) findViewById(R.id.drawqr);

        // Back Button Listener
        back1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                jumpLayoutMain();
            }
        });

        //Draw Button Listener

        draw.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //获取信息
                EditText freeEditText = (EditText) findViewById(R.id.free);
                String freeStr = freeEditText.getText().toString();
                //String sort="自由文本";
                char sort1 = 'F';
                Intent intent = new Intent();
                intent.setClass(iCQRcode.this, DrawQRCode.class);
                Bundle bundle = new Bundle();
                bundle.putString("freeContent", freeStr);
                bundle.putChar("sort1", sort1);
                intent.putExtras(bundle);
                startActivity(intent);
                iCQRcode.this.finish();
            }
        });
    }


}