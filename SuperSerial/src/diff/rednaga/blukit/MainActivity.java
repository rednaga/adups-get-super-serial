package diff.rednaga.blukit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import diff.rednaga.superserial.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = (Button) findViewById(R.id.button1);
        b.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

             BluSocket blu = new BluSocket();
             if (blu.execute("sh /data/local/tmp/yay/shell.sh") == 0) {
                Log.v("blukit", "System commands executed");
             } else {
                Log.e("blukit", "Error executing system commands");
             }
            }
        });
    }
}