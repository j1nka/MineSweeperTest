package tinygin.mynesweeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener {

    final String TAG = "MainActivity";
    private EditText nameEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button okButton = (Button)findViewById(R.id.okButton);
        okButton.setOnClickListener(this);

        nameEnter = (EditText)findViewById(R.id.nameBut);

        Log.d(TAG, "Main activity was created");
    }

    public void onClick(View view) {
        Intent intent = GameActivity.makeGameActivityIntent(this, nameEnter.getText().toString());
        startActivity(intent);
    }

    protected void onStop() {
        super.onStop();
        finish();
        Log.d(TAG, "MainActivity Was finished");
    }

}