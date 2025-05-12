package com.huan.dictionary;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.huan.dictionary.DictionaryDatabaseHelper;

public class MainActivity extends AppCompatActivity {
    private DictionaryDatabaseHelper dbHelper;
    private EditText editTextWord;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DictionaryDatabaseHelper(this);
        editTextWord = findViewById(R.id.editTextWord);
        textViewResult = findViewById(R.id.textViewResult);
        Button buttonLookup = findViewById(R.id.buttonLookup);

        buttonLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lookupWord();
            }
        });
    }

    private void lookupWord() {
        String userInput = editTextWord.getText().toString().trim();
        if (userInput.isEmpty()) {
            textViewResult.setText("Please enter a word");
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DictionaryDatabaseHelper.COLUMN_DEFINITION +
                " FROM " + DictionaryDatabaseHelper.TABLE_NAME +
                " WHERE " + DictionaryDatabaseHelper.COLUMN_WORD + " = ?", new String[]{userInput});

        if (cursor.moveToFirst()) {
            String definition = cursor.getString(0);
            textViewResult.setText("Definition: " + definition);
        } else {
            // If not found, search for substrings
            cursor = db.rawQuery("SELECT " + DictionaryDatabaseHelper.COLUMN_WORD +
                    " FROM " + DictionaryDatabaseHelper.TABLE_NAME +
                    " WHERE " + DictionaryDatabaseHelper.COLUMN_WORD + " LIKE ?", new String[]{"%" + userInput + "%"});

            if (cursor.moveToFirst()) {
                StringBuilder matches = new StringBuilder("Did you mean:\n");
                do {
                    matches.append("- ").append(cursor.getString(0)).append("\n");
                } while (cursor.moveToNext());
                textViewResult.setText(matches.toString());
            } else {
                textViewResult.setText("No matches found.");
            }
        }
        cursor.close();
        db.close();
    }
}