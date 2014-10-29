package com.example.jeonghwan.dday;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class Main extends Activity {

    String tag = "dDays";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setListView(savedInstanceState);
        setAddBtn(savedInstanceState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setListView(final Bundle savedInstanceState) {
        DBHandler db = DBHandler.open(Main.this);
        Cursor cur = db.selectAll();
        startManagingCursor(cur);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                Main.this,
                android.R.layout.simple_list_item_1,
                cur,
                new String[] { "msg" },
                new int[] { android.R.id.text1 }
        );

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cur = (Cursor) adapterView.getAdapter().getItem(i);
                String selId = cur.getString(cur.getColumnIndex("_id")); // 삭제할 id


                Log.d(tag, i + " " + l + " " + selId);

                DBHandler db = DBHandler.open(Main.this);
                long r = db.delete(selId);

                Log.d(tag, "삭제결과: " + r);

                db.close();
                onCreate(savedInstanceState);
            }
        });


        db.close();
    }

    private void setAddBtn(final Bundle savedInstanceState) {
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(tag, "Click 추가 버튼");

                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout addDialog = (LinearLayout) vi.inflate(R.layout.add_dialog, null);

                final DatePicker date = (DatePicker) addDialog.findViewById(R.id.date);
                final EditText text = (EditText) addDialog.findViewById(R.id.text);

                new AlertDialog.Builder(Main.this)
                        .setTitle("추가하기")
                        .setView(addDialog)
                        .setNeutralButton("추가하기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String strDate = date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDayOfMonth();

                                Log.d(tag, "추가하기 버튼 클릭 " + strDate +
                                        ", " +  text.getText().toString());

                                save(strDate, text.getText().toString());
                                onCreate(savedInstanceState);
                            }
                        })
                        .show();
            }
        });
    }

    private void save(String date, String text) {
        DBHandler db = DBHandler.open(Main.this);
        long r = db.insert(date, text);
        Log.d(tag, "db 입력결과: " + r);

        if (r == -1) {
            Toast.makeText(Main.this, "입력실패", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Main.this, "입력성공 id="+r, Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}
