package ru.evant.favoritefood;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.evant.favoritefood.adapter.ListItem;
import ru.evant.favoritefood.adapter.MainAdapter;
import ru.evant.favoritefood.db.AppExecutor;
import ru.evant.favoritefood.db.MyDBManager;
import ru.evant.favoritefood.db.OnDataReceived;

import static androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class MainActivity extends AppCompatActivity implements OnDataReceived {
    private MyDBManager myDbManager;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.id_search);
        SearchView sv = (SearchView) item.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // фильтровать по нажатию на кнопку поиск
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // фильтровать после каждого нажатия на букву
            @Override
            public boolean onQueryTextChange(final String newText) {
                readFromDB(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void init() {
        myDbManager = new MyDBManager(this);

        RecyclerView rv = findViewById(R.id.rv);
        adapter = new MainAdapter(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        getItemTouchHelper().attachToRecyclerView(rv);
        rv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myDbManager.openDB();
        readFromDB("");
    }

    // получить данные из БД во втором потоке
    private void readFromDB(final String text) {
        AppExecutor.getInstance().getSubIO().execute(new Runnable() {
            @Override
            public void run() {
                myDbManager.getFromDB(text, MainActivity.this);
            }
        });
    }

    // кнопка сохранить
    public void onClickAdd(View view) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        startActivity(intent);
    }

    // удаление свайпом из RecyclerView
    private ItemTouchHelper getItemTouchHelper() {
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder, @NonNull ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull ViewHolder viewHolder, int direction) {
                adapter.removeItem(viewHolder.getAdapterPosition(), myDbManager); //
            }
        });
    }

    @Override
    public void onReceived(final List<ListItem> list) {
        AppExecutor.getInstance().getMainIO().execute(new Runnable() {
            @Override
            public void run() {
                adapter.updateAdapter(list);
            }
        });
    }

    @Override
    protected void onDestroy() {
        myDbManager.closeDB();
        super.onDestroy();
    }
}
