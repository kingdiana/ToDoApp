package kingdiana.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TodoActivity extends ActionBarActivity {
	private ArrayList<String> todoItems;
	private ArrayAdapter<String> todoAdapter;
	private ListView lvItems;
	private EditText etNewItem;
    // REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 20;
    private int editPos;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        
        populateArrayList();
        todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(todoAdapter);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        setupListViewListener();
    }

    protected void onStop() {
        super.onStop();
        saveItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String newValue = data.getStringExtra("new_value");
            todoItems.set(editPos, newValue);
            todoAdapter.notifyDataSetChanged();
        }
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item,
                                           int pos, long id) {
                todoItems.remove(pos);
                todoAdapter.notifyDataSetChanged();
                saveItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int pos, long arg) {
                Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
                i.putExtra("current_value", todoItems.get(pos));
                editPos = pos;
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }
    
    private void populateArrayList() {
		todoItems = new ArrayList<String>();
        readItems();
	}
    
    public void onAddedItem(View v) {
    	todoAdapter.add(etNewItem.getText().toString());
    	etNewItem.setText("");
    }

    public void onSave(View v) {
        this.finish();
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) return true;
        return super.onOptionsItemSelected(item);
    }

    private void readItems() {
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            todoItems = new ArrayList<String>();
            //e.printStackTrace();
        }
    }

    private void saveItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, todoItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
