package ru.evant.favoritefood;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import ru.evant.favoritefood.adapter.ListItem;
import ru.evant.favoritefood.db.MyConstDB;
import ru.evant.favoritefood.db.MyDBManager;

import static android.view.View.VISIBLE;

public class EditActivity extends AppCompatActivity {
    private final int PICK_IMAGE_CODE = 111;

    private MyDBManager myDbManager;

    private ConstraintLayout imgContainer;
    private ImageView imgView;
    private ImageButton imgButtonEdit, imgButtonDelete;

    private EditText edTitle, edRecipe, edDescription;

    private FloatingActionButton addImage;

    private String tempUriImage = "empty";
    private boolean isEditState = true;

    private ListItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        getIntentFromMainAdapter();
    }

    private void init() {
        myDbManager = new MyDBManager(this);

        imgContainer =  findViewById(R.id.imgContainer);
        imgView =  findViewById(R.id.imgView);
        imgButtonEdit =  findViewById(R.id.imgButtonEdit);
        imgButtonDelete =  findViewById(R.id.imgButtonDelete);

        edTitle =  findViewById(R.id.edTitle);
        edRecipe = findViewById(R.id.edRecipe);
        edDescription =  findViewById(R.id.edDescription);

        addImage =  findViewById(R.id.addImage);
    }

    // получить данные с MainActivity
    @SuppressLint("RestrictedApi")
    public void getIntentFromMainAdapter(){
        Intent intent = getIntent();
        if (intent != null){
            item = (ListItem) intent.getSerializableExtra(MyConstDB.LIST_ITEM_INTENT);
            isEditState = intent.getBooleanExtra(MyConstDB.EDIT_STATE, true);

            if (!isEditState){
                edTitle.setText(item.getTitle());
                edRecipe.setText(item.getRecipe());
                edDescription.setText(item.getDescription());
                if (!item.getUriImage().equals("empty")){
                    tempUriImage = item.getUriImage();
                    imgContainer.setVisibility(View.VISIBLE);
                    imgView.setImageURI(Uri.parse(item.getUriImage()));
                    addImage.setVisibility(View.GONE);
                    imgButtonEdit.setVisibility(View.GONE);
                    imgButtonDelete.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        myDbManager.openDB();
    }

    // установить картинку
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE && data != null){
            tempUriImage = Objects.requireNonNull(data.getData()).toString();
            imgView.setImageURI(data.getData());
            getContentResolver().takePersistableUriPermission(data.getData(),Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    // кнопка выбрать картинку из существующих на телефоне
    public void onClickChooseImage(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_CODE);
    }

    // кнопка сохранить заметку
    public void onClickSave(View view) {
        String title = edTitle.getText().toString();
        String recipe = edRecipe.getText().toString();
        String description = edDescription.getText().toString();

        // проверить, не устые ли поля
        if (title.equals("") || description.equals("")) {
            Toast.makeText(this, R.string.edit_activity_text_is_empty, Toast.LENGTH_SHORT).show();
        } else {
            if (isEditState) {
                // записать в ДБ
                myDbManager.insertDB(title, recipe, description, tempUriImage);
            } else {
                // изменить запись в БД
                myDbManager.update(item.getId(), title, recipe, description, tempUriImage);
            }
            myDbManager.closeDB();
            finish();
        }
    }

    // удалить картинку
    @SuppressLint("RestrictedApi")
    public void onClickDeleteImage(View view) {
        imgView.setImageResource(R.drawable.ic_image_white);
        tempUriImage = "empty";
        imgContainer.setVisibility(View.GONE);
        addImage.setVisibility(View.VISIBLE);
    }

    // кнопка добавить картинку
    public void onClickAddImage(View view) {
        imgContainer.setVisibility(VISIBLE);
        view.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDbManager.closeDB();
    }
}
