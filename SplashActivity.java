package comp.example.suutmulyono.latihan4;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    TextView textView;

    protected ProgressBar mProgressBar;
    protected static final int TIMER_RUNTIME = 4000;
    protected boolean mbActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mProgressBar = findViewById(R.id.loading);
        textView = findViewById(R.id.textView);
        //Start progressing

        final Thread TimerThread = new Thread(){
            @Override
            public void run(){
                mbActive = true;
                try {
                    int waited = 0;
                    while (mbActive&& (waited < TIMER_RUNTIME)){
                        sleep(200);
                        if (mbActive){
                            waited += 200;
                            updateProgress(waited);
                        }
                    }
                }catch (InterruptedException e){

                }finally {
                    onContinue();
                }
            }
        };
        TimerThread.start();

    }

    private void onContinue() {
        Intent intd=new Intent(this,MainActivity.class);
        startActivity(intd);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void updateProgress(final int timePassed) {
        if(null != mProgressBar) {
            final int progress = mProgressBar.getMax() * timePassed / TIMER_RUNTIME;
            mProgressBar.setProgress(progress);
        }
    }
}
