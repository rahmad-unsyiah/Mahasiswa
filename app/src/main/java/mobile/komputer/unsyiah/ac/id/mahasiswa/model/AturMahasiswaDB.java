package mobile.komputer.unsyiah.ac.id.mahasiswa.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AturMahasiswaDB extends SQLiteOpenHelper {
    // Versi DB
    public static final int VERSI_DB = 1;
    // Nama DB
    public static final String NAMA_DB = "Mahasiswa";
    // Nama Table
    public static final String TABEL_MAHASISWA = "MAHASISWA";
    // Kolom DB
    public static final String MAHASISWA_ID = "_id";
    public static final String MAHASISWA_NIM = "nim";
    public static final String MAHASISWA_NAMA = "nama";

    public AturMahasiswaDB(Context context) {
        super(context, NAMA_DB, null, VERSI_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Perintah untuk buat DB baru jika belum ada
        final String buatDB = "CREATE TABLE IF NOT EXISTS " + TABEL_MAHASISWA + " ( "
                              + MAHASISWA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                              + MAHASISWA_NIM +" TEXT, "
                              + MAHASISWA_NAMA + " TEXT "
                            + ");";
        // Jalankan perintah buat DB
        db.execSQL(buatDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
    }
}