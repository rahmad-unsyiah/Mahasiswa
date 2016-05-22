package mobile.komputer.unsyiah.ac.id.mahasiswa.model;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import mobile.komputer.unsyiah.ac.id.mahasiswa.R;

/**
 * Adapter untuk menghubungkan Cursor dengan ListView. Satu hasil query akan tampil pada satu baris
 * List.
 */
public class MahasiswaCursorAdapter extends CursorAdapter {
    public MahasiswaCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Pakai layout satu_mahasiswa.xml
        return LayoutInflater.from(context).inflate(R.layout.satu_mahasiswa, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Ambil kolom NIM dari query dan tempatkan ke elemen nim di layout
        String nim = cursor.getString(cursor.getColumnIndexOrThrow(AturMahasiswaDB.MAHASISWA_NIM));
        TextView txtNIM = (TextView) view.findViewById(R.id.txtNIM);
        txtNIM.setText(nim);

        // Ambil kolom Nama dari query  dan tempatkan ke elemen nama di layout
        String nama = cursor.getString(cursor.getColumnIndexOrThrow(AturMahasiswaDB.MAHASISWA_NAMA));
        TextView txtNama = (TextView) view.findViewById(R.id.txtNama);
        txtNama.setText(nama);
    }
}
