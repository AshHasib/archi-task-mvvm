package com.ashhasib.architask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String TITLE="Title";
    public static final String DESC="Desc";
    public static final String PRIORITY="Priority";
    public static final String ID="Id";
    private EditText inputTitle, inputDesc;
    private NumberPicker priority;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        inputTitle= findViewById(R.id.inputTitle);
        inputDesc= findViewById(R.id.inputDesc);
        priority= findViewById(R.id.numberPicker);

        priority.setMinValue(0);
        priority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if(intent.hasExtra(ID)) {
            setTitle("Edit Note");
            inputTitle.setText(intent.getStringExtra(TITLE));
            inputDesc.setText(intent.getStringExtra(DESC));
            priority.setValue(intent.getIntExtra(PRIORITY,1));
        }
        else {
            setTitle("Add Note");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_note_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();

        switch (id) {
            case R.id.menu_save: {
                saveNote();
                break;
            }
            default: {

            }
        }

        return super.onOptionsItemSelected(item);
    }



    public void saveNote(){
        String title = inputTitle.getText().toString();
        String desc= inputDesc.getText().toString();
        int pr= priority.getValue();

        if(title.trim().isEmpty() || desc.trim().isEmpty()) {
            Toast.makeText(this, "Please fill up properly", Toast.LENGTH_LONG).show();
            return;
        }


        Intent intent= new Intent();
        intent.putExtra(TITLE, title);
        intent.putExtra(DESC, desc);
        intent.putExtra(PRIORITY, pr);

        int id= getIntent().getIntExtra(ID,-1);

        if(id!=-1) {
            intent.putExtra(ID,id);
        }

        setResult(RESULT_OK, intent);
        finish();
    }
}
