package com.kiassasian.appasian;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.kiassasian.appasian.adapters.ReplyAdapter;
import com.kiassasian.appasian.models.GetCommentsModel;
import com.kiassasian.appasian.models.PostCommentModel;
import com.kiassasian.appasian.network.RetrofitClient;
import com.kiassasian.appasian.utils.PreferenceUtils;
import com.kiassasian.appasian.utils.ApiResources;
import com.kiassasian.appasian.utils.ToastMsg;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class ReplyActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText etComment;
    private Button btnComment;

    private List<GetCommentsModel> list=new ArrayList<>();
    private ReplyAdapter replyAdapter;
    private String strCommentID,strAllReplyURL, videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getSharedPreferences("push", MODE_PRIVATE);
        boolean isDark = sharedPreferences.getBoolean("dark", false);

        if (isDark) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppThemeLight);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Reply");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //---analytics-----------
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "reply_activity");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "activity");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        btnComment=findViewById(R.id.btn_comment);
        etComment=findViewById(R.id.et_comment);
        recyclerView=findViewById(R.id.recyclerView);

        if (!isDark) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            etComment.setBackground(getResources().getDrawable(R.drawable.rounded_black_transparent));
            btnComment.setTextColor(getResources().getColor(R.color.grey_20));
        }


        replyAdapter=new ReplyAdapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(replyAdapter);


        strCommentID = getIntent().getStringExtra("commentId");
        videoId = getIntent().getStringExtra("videoId");

        strAllReplyURL=new ApiResources().getGetAllReply()+"&&id="+strCommentID;


        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new ToastMsg(ReplyActivity.this).toastIconError(getString(R.string.comment_empty));
            }
        });

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
