package com.example.login;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class register extends AppCompatActivity {
   private EditText registername;
   private EditText registerpassword;
   private EditText registerconfirmpassword;
   private Button registerbtn;
   private TextView registertext;
   DatabseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db=new DatabseHelper(this);
        registername=findViewById(R.id.registermail);
        registerpassword=findViewById(R.id.registerpassword);
        registerconfirmpassword=findViewById(R.id.registerconfirmpassword);
        registerbtn=findViewById(R.id.registerbutton);
        registertext=findViewById(R.id.registertext);
        String text1="Already Registerd?Login Here";
        SpannableString spannableString=new SpannableString(text1);
        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick( View widget) {
                Intent intent=new Intent(register.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void updateDrawState( TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.GREEN);
                ds.setUnderlineText(false);
            }
        };
        spannableString.setSpan(clickableSpan,18,28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        registertext.setText(spannableString);
        registertext.setMovementMethod(LinkMovementMethod.getInstance());
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String user=registername.getText().toString().trim();
               String pass=registerpassword.getText().toString().trim();
               String cnfmpass=registerconfirmpassword.getText().toString().trim();
               if(!isValidEmail(user))
               {
                   registername.setError("Email is invalid");
                   return;
               }
               if(pass.length()<6)
               {
                   registerpassword.setError("password is too short");
                     return;
               }
               if(pass.equals(cnfmpass))
               {
                   long res;
                   res=db.adduser(user,pass);
                   if(res>0)
                   {
                       Toast.makeText(register.this,"Successfully logged in",Toast.LENGTH_SHORT).show();
                       Intent intent=new Intent(register.this,MainActivity.class);
                       startActivity(intent);
                   }
                   else
                   {
                       Toast.makeText(register.this,"Error",Toast.LENGTH_SHORT).show();
                   }
               }
               else
               {
                    registerconfirmpassword.setError("Password doesn't Matches");
                    return;
               }
            }
        });
    }
    private static boolean isValidEmail(CharSequence target)
    {
          return !TextUtils.isEmpty(target)&& Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
