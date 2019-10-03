package com.medialink.myreadwritefile;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button_new)
    Button buttonNew;
    @BindView(R.id.button_open)
    Button buttonOpen;
    @BindView(R.id.button_save)
    Button buttonSave;
    @BindView(R.id.edit_title)
    EditText editTitle;
    @BindView(R.id.edit_file)
    EditText editFile;

    Unbinder unbind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbind = ButterKnife.bind(this);
    }

    @OnClick({R.id.button_new, R.id.button_open, R.id.button_save})
    void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button_new:
                newFile();
                break;
            case R.id.button_open:
                showList();
                break;
            case R.id.button_save:
                saveFile();
                break;
        }
    }

    private void newFile() {
        editTitle.setText("");
        editFile.setText("");
        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show();
    }

    private void showList() {
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, getFilesDir().list());
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang diinginkan")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        loadData(items[i].toString());
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void saveFile() {
        if (editTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
            return;
        }
        if (editFile.getText().toString().isEmpty()) {
            Toast.makeText(this, "Kontent harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
            return;
        }

        String title = editTitle.getText().toString();
        String text = editFile.getText().toString();
        FileModel fileModel = new FileModel();
        fileModel.setFilename(title);
        fileModel.setData(text);
        FileHelper.writeToFile(fileModel, this);
        Toast.makeText(this, "Saving " + fileModel.getFilename()  + " file", Toast.LENGTH_SHORT).show();
    }

    private void loadData(String title) {
        FileModel fileModel = FileHelper.readFromFile(this, title);
        editTitle.setText(fileModel.getFilename());
        editFile.setText(fileModel.getData());
        Toast.makeText(this, "Loading " + fileModel.getFilename() + " data", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbind.unbind();
    }
}
