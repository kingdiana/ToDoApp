package kingdiana.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditItemActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String oldValue = getIntent().getStringExtra("current_value");

        setContentView(R.layout.activity_edit_item);
        EditText et_item = (EditText) findViewById(R.id.et_edit_value);
        et_item.setText(oldValue);
        et_item.setSelection(oldValue.length());

        Button button = (Button) findViewById(R.id.btn_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSubmit(v);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSubmit(View v) {
        // closes the activity and returns to first screen
        EditText etEditValue = (EditText) findViewById(R.id.et_edit_value);
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("new_value", etEditValue.getText().toString());
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        this.finish();
    }
}
