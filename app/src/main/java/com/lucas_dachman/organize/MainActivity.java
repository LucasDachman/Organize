package com.lucas_dachman.organize;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private MyRealmAdapter adapter;
    private RealmChangeListener realmListener;
    private Realm realm;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
        // Realm Listener
        realmListener = new RealmChangeListener() {
            @Override
            public void onChange() {
                ListView listView = (ListView) findViewById(R.id.listView_main);
                listView.invalidate();
            }};
        realm.addChangeListener(realmListener);

        RealmQuery query = realm.where(Note.class);
        RealmResults<Note> results = query.findAllAsync();
        adapter = new MyRealmAdapter(this, R.layout.note_row_layout, results, true);



        listView = (ListView) findViewById(R.id.listView_main);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the listener.
        realm.removeChangeListener(realmListener);
        // Close the realm instance.
        realm.close();
    }

    public void newNote(View view) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra("isNewNote", true);
        startActivity(intent);
    }

    public void editNote(View view) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra("isNewNote", false);
        Note note = (Note)(view.getTag());
        intent.putExtra("note_id", note.getDateCreated());
        startActivity(intent);
    }
}
