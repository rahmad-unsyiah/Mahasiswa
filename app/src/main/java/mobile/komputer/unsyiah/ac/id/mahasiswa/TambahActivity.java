package mobile.komputer.unsyiah.ac.id.mahasiswa;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mobile.komputer.unsyiah.ac.id.mahasiswa.model.AturMahasiswaDB;

public class TambahActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
    }

    /**
     * Tangani jika tombol btnTambah di-click
     */
    public void clickBtnTambah(View view) {
        // Ambil data yang diisi
        TextView txtNIM = (TextView) findViewById(R.id.txtNIM);
        String nim = txtNIM.getText().toString();
        TextView txtNama = (TextView) findViewById(R.id.txtNama);
        String nama = txtNama.getText().toString();

        // Tambah data ke database
        SQLiteOpenHelper aturMahasiswaDB = new AturMahasiswaDB(this);
        SQLiteDatabase db = aturMahasiswaDB.getWritableDatabase();
        // Sebutkan nilai baru yang akan ditambahkan
        ContentValues mahasiswaBaru = new ContentValues();
        mahasiswaBaru.put(AturMahasiswaDB.MAHASISWA_NIM, nim);
        mahasiswaBaru.put(AturMahasiswaDB.MAHASISWA_NAMA, nama);
        // Tambahkan ke database
        db.insert(AturMahasiswaDB.TABEL_MAHASISWA, null, mahasiswaBaru);
        // Tutup koneksi ke database
        db.close();

        // Kirim kembali ke Activity MainActivity
        Intent pesan = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(pesan);
        finish();
    }
}