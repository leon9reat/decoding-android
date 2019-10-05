package com.medialink.mypreloaddata.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_NAME = "table_mahasiswa";

    static final class MahasiswaColumn implements BaseColumns {
        static String NAMA = "nama";
        static String NIM = "nim";
    }
}
