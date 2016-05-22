package mobile.komputer.unsyiah.ac.id.mahasiswa;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import mobile.komputer.unsyiah.ac.id.mahasiswa.model.AturMahasiswaDB;
import mobile.komputer.unsyiah.ac.id.mahasiswa.model.MahasiswaCursorAdapter;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // List untuk menampilkan semua mahasiswa di database, hanya nama dan nim
        ListView lstDaftarAnggota = (ListView) findViewById(R.id.lstDaftarMahasiswa);

        // Bangun dan jalan query untuk mengambil semua mahasiswa di database, hanya nama dan nim
        SQLiteOpenHelper aturMahasiswaDB = new AturMahasiswaDB(this);
        db = aturMahasiswaDB.getReadableDatabase();
        // Query-nya:
        //   SELECT _id, nim, nama
        //   FROM MAHASISWA
        //   ORDER BY nama
        cursor = db.query(AturMahasiswaDB.TABEL_MAHASISWA,               // FROM
                          new String[] {AturMahasiswaDB.MAHASISWA_ID,    // SELECT
                                        AturMahasiswaDB.MAHASISWA_NIM,
                                        AturMahasiswaDB.MAHASISWA_NAMA},
                          null,                                          // WHERE
                          null,                                          // Argumen untuk WHERE
                          null,                                          // GROUP BY
                          null,                                          // HAVING
                          AturMahasiswaDB.MAHASISWA_NAMA);               // ORDER BY
        // Buat adapter agar bisa menghubungkan cursor dengan list
        MahasiswaCursorAdapter mahasiswaCursorAdapter = new MahasiswaCursorAdapter(this, cursor);

        // Minta list untuk memakai adapter kita agar menampilkan data dari cursor
        lstDaftarAnggota.setAdapter(mahasiswaCursorAdapter);

        // Buat listener untuk tangani click salah satu item di list
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> listView, // Dari list yang mana
                                            View view,               // Dari view mana dalam Item,
                                                                     // karena satu item bisa
                                                                     // terdiri dari banyak jenis
                                                                     // elemen lain, e.g. gambar,
                                                                     // text, dll.
                                            int nomorBerapa,         // Item nomor berapa yang
                                                                     // di-click, mulai dari 0
                                            long id) {               // ID dari elemen yang di-click
                        clickItemLstDaftarMahasiswa(listView, view, nomorBerapa, id);
                    }
                };
        // Tetapkan listerner jika click pada salah satu item di list
        lstDaftarAnggota.setOnItemClickListener(itemClickListener);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Tutup cursor
        cursor.close();
        // Tutup koneksi ke DB
        db.close();
    }

    /**
     * Tangani jika tombol btnTambah di-click
     */
    public void clickBtnTambah(View view) {
        // Kirim ke TambahActivity
        Intent pesan = new Intent(getApplicationContext(), TambahActivity.class);
        startActivity(pesan);
    }

    /**
     * Tangani salah satu item di list di-click
     */
    public void clickItemLstDaftarMahasiswa(AdapterView<?> listView,  // List yang dipakai
                                            View v,                   // View yang dipakai
                                            int position,             // Item ke berapa dalam list
                                            long id) {                // _id dari data yang dipilih
        // Ambil data yang ingin ditampilkan
        String nim = cursor.getString(cursor.getColumnIndexOrThrow(AturMahasiswaDB.MAHASISWA_NIM));
        String nama = cursor.getString(cursor.getColumnIndexOrThrow(AturMahasiswaDB.MAHASISWA_NAMA));

        // Kirim _id dari row yang dipilih ke RincianActivity
        Intent pesan = new Intent(getApplicationContext(), RincianActivity.class);
        pesan.putExtra("id",  id);     // Kirim id mahasiswa yang di-click
        startActivity(pesan);
    }

    private SQLiteDatabase db;
    private Cursor cursor;
}