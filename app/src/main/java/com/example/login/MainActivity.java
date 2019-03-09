package com.example.login;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText loginuser;
    private EditText loginpass;
    private Button loginbtn;
    private TextView logintext;
    DatabseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new DatabseHelper(this);
        loginuser=findViewById(R.id.loginmail);
        loginpass=findViewById(R.id.loginpassword);
        loginbtn=findViewById(R.id.loginbutton);
        logintext=findViewById(R.id.logintext);
        String text="Not Registered Yet?Register Here";
        SpannableString ss=new SpannableString(text);
        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent=new Intent(MainActivity.this,register.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState( TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan,19 ,32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        logintext.setText(ss);
        logintext.setMovementMethod(LinkMovementMethod.getInstance());
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=loginuser.getText().toString().trim();
                String pass=loginpass.getText().toString().trim();
                if(user.isEmpty())
                {
                    loginuser.setError("Field can't be empty");
                    return;
                }
                if(pass.isEmpty())
                {
                    loginpass.setError("Field can't be empty");
                    return;
                }
                Boolean res=db.checkuser(user,pass);
                if(res==true)
                {
                    Toast.makeText(MainActivity.this,"Successsful",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,last.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }
}
