package mobile.komputer.unsyiah.ac.id.mahasiswa.model;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import mobile.komputer.unsyiah.ac.id.mahasiswa.R;

public class MahasiswaCursorAdapter extends CursorAdapter {
    public MahasiswaCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.satu_mahasiswa, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView txtNIM = (TextView) view.findViewById(R.id.txtNIM);
        TextView txtNama = (TextView) view.findViewById(R.id.txtNama);
        // Extract properties from cursor
        String nim = cursor.getString(cursor.getColumnIndexOrThrow(AturMahasiswaDB.MAHASISWA_NIM));
        String nama = cursor.getString(cursor.getColumnIndexOrThrow(AturMahasiswaDB.MAHASISWA_NAMA));
        // Populate fields with extracted properties
        txtNIM.setText(nim);
        txtNama.setText(nama);
    }
}
