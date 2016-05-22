package mobile.komputer.unsyiah.ac.id.mahasiswa;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import mobile.komputer.unsyiah.ac.id.mahasiswa.model.AturMahasiswaDB;

public class RincianActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian);

        // Ambil data ID yang dikirim
        Intent pesan = getIntent();
        id = pesan.getLongExtra("id", 0);

        // Query database untuk ambil row yang sesuai
        SQLiteOpenHelper aturMahasiswaDB = new AturMahasiswaDB(this);
        SQLiteDatabase db = aturMahasiswaDB.getReadableDatabase();
        // Bangun query berikut:
        //   SELECT nim, nama
        //   FROM MAHASISWA
        //   WHERE _id=[id yang dikirim]
        Cursor cursor = db.query(AturMahasiswaDB.TABEL_MAHASISWA,                        // FROM
                                 new String[] {AturMahasiswaDB.MAHASISWA_NIM,            // SELECT
                                               AturMahasiswaDB.MAHASISWA_NAMA},
                                 AturMahasiswaDB.MAHASISWA_ID + "=" + Long.toString(id), // WHERE
                                 null,                                                   // Argumen
                                                                                         // WHERE
                                 null,                                                   // GROUP BY
                                 null,                                                   // HAVING
                                 null);                                                  // ORDER BY
        cursor.moveToFirst();
        // Ambil datanya
        nim = cursor.getString(cursor.getColumnIndexOrThrow(AturMahasiswaDB.MAHASISWA_NIM));
        nama = cursor.getString(cursor.getColumnIndexOrThrow(AturMahasiswaDB.MAHASISWA_NAMA));
        // Tutup cursor
        cursor.close();
        // Tutup database
        db.close();

        // Tampilkan NIM
        EditText txtNim = (EditText) findViewById(R.id.txtNIM);
        txtNim.setText(nim);
        // Tampilkan NAMA
        EditText txtNama = (EditText) findViewById(R.id.txtNama);
        txtNama.setText(nama);
    }

    /**
     * Tangani jika tombol btnUbah di-click. Tampilan akan diubah dimana semua EditText menjadi bisa
     * diedit, tombol Hapus hilang, tombol ubah hilang, dan tombol simpan muncul.
     */
    public void clickBtnUbah(View view) {
        // Hidupkan EditText untuk NIM sehingga bisa di-edit
        EditText txtNim = (EditText) findViewById(R.id.txtNIM);
        txtNim.setEnabled(true);
        // Hidupkan EditText untuk Nama sehingga bisa di-edit
        EditText txtNama = (EditText) findViewById(R.id.txtNama);
        txtNama.setEnabled(true);

        // Hilangkan tombol Hapus
        Button btnHapus = (Button) findViewById(R.id.btnHapus);
        btnHapus.setVisibility(View.GONE);
        // Hilangkan tombol Ubah
        Button btnUbah = (Button) findViewById(R.id.btnUbah);
        btnUbah.setVisibility(View.GONE);
        // Tampilkan tombol Simpan, untuk menyimpan perubahan
        Button btnSimpan = (Button) findViewById(R.id.btnSimpan);
        btnSimpan.setVisibility(View.VISIBLE);
    }

    /**
     * Tangani jika tombol btnHapus di-click
     */
    public void clickBtnHapus(View view) {
        // Tampilkan DialogBox menanyakan apa benar mau dihapus
        new AlertDialog.Builder(this)
                       // Judul dialog box
                       .setTitle("Hapus?")
                       // Pesan dalam dialog box
                       .setMessage("Yakin ingin menghapus mahasiswa ini?")
                       // Tombol untuk Ya akan dihapus
                       .setPositiveButton("Ya", // Tulisan pada tombol
                                          // Listener untuk tangani penekanan tombol ini
                                          new DialogInterface.OnClickListener() {
                                              public void onClick(DialogInterface dialog,
                                                                  int which) {
                                                  clickBtnYaHapus(dialog, which);
                                              }
                                          })
                       .setNegativeButton("Tidak", // Tulisan pada tombol
                                          // Listener untuk tangani penekanan tombol ini
                                          new DialogInterface.OnClickListener() {
                                              public void onClick(DialogInterface dialog,
                                                                  int which) {
                                                  // Tidak melakukan apapun
                                              }
                                          })
                       // Muncul Icon hati-hati pada dialog box
                       .setIcon(android.R.drawable.ic_dialog_alert)
                       // Tampilkan dialog box
                       .show();
    }

    /**
     * Tangani jika tombol btnSimpan di-click
     */
    public void clickBtnSimpan(View view) {
        // Ambil NIM yang baru diubah
        EditText txtNim = (EditText) findViewById(R.id.txtNIM);
        String nimBaru = txtNim.getText().toString();
        // Ambil Nama yang baru diubah
        EditText txtNama = (EditText) findViewById(R.id.txtNama);
        String namaBaru = txtNama.getText().toString();

        // Ubah data di database
        SQLiteOpenHelper aturMahasiswaDB = new AturMahasiswaDB(this);
        SQLiteDatabase db = aturMahasiswaDB.getWritableDatabase();
        // Tentukan nilai-nilai baru
        ContentValues mahasiswaBaru = new ContentValues();
        mahasiswaBaru.put(AturMahasiswaDB.MAHASISWA_NIM, nimBaru);
        mahasiswaBaru.put(AturMahasiswaDB.MAHASISWA_NAMA, namaBaru);
        // Jalan SQL Update
        db.update(AturMahasiswaDB.TABEL_MAHASISWA,                        // Tabel yang mau diubah
                  mahasiswaBaru,                                          // Nilai barunya
                  AturMahasiswaDB.MAHASISWA_ID + "=" + Long.toString(id), // WHERE
                  null);                                                  // Argumen WHERE
        // Tutup database
        db.close();

        // Kembali ke Activity MainActivity
        Intent pesan = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(pesan);
        finish();
    }

    /**
     * Tangani jika tombol Ya pada dialog box konfirmasi hapus data di-click
     */
    public void clickBtnYaHapus(DialogInterface dialog,
                                int which) {
        // Hapus data di database
        SQLiteOpenHelper aturMahasiswaDB = new AturMahasiswaDB(this);
        SQLiteDatabase db = aturMahasiswaDB.getWritableDatabase();
        // Jalan SQL Delete
        db.delete(AturMahasiswaDB.TABEL_MAHASISWA,                      // Tabel yang mau dihapus
                AturMahasiswaDB.MAHASISWA_ID + "=" + Long.toString(id), // WHERE
                null);                                                  // Argumen WHERE
        // Tutup database
        db.close();

        // Kembali ke Activity MainActivity
        Intent pesan = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(pesan);
        finish();
    }

    private long id;
    private String nim;
    private String nama;
}