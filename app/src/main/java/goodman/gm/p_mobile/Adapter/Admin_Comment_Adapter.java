package goodman.gm.p_mobile.Adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import goodman.gm.p_mobile.Controller.Admin_Comment;
import goodman.gm.p_mobile.Controller.Admin_Comment_MaBinhLuan;
import goodman.gm.p_mobile.Model.BinhLuan;
import goodman.gm.p_mobile.Model.QuanAn;
import goodman.gm.p_mobile.R;

public class Admin_Comment_Adapter extends BaseAdapter {
    private Admin_Comment context;
    private int layout;
    private List<BinhLuan> lst_BinhLuan;
    private List<QuanAn> lstQuanAn;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("binhluans");

    public Admin_Comment_Adapter(Admin_Comment context, int layout, List<QuanAn> lstQuanAn) {
        this.context = context;
        this.layout = layout;
        this.lstQuanAn = lstQuanAn;
    }

    @Override
    public int getCount() {
        return lstQuanAn.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {
        TextView tvMaQA;
        Button btnXoa;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
            holder = new ViewHolder();
            holder.tvMaQA = convertView.findViewById(R.id.tvMaQABl);
            holder.btnXoa = convertView.findViewById(R.id.btnXoaBl);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        QuanAn quanAn = lstQuanAn.get(position);
//        List<BinhLuan> list_binhLuan = quanAn.getList_BinhLuan();
        holder.tvMaQA.setText(quanAn.getmMaQuanAn());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Admin_Comment_MaBinhLuan.class);
                intent.putExtra("lstQuanans", lstQuanAn.get(position));
                context.startActivity(intent);
            }
        });


        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context,
                        android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setTitle("B???n c?? mu???n x??a kh??ng ?");
                builder.setCancelable(false);
                builder.setPositiveButton("C??", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                         x??a tr??n firebase
                        String maQuanAn = quanAn.getmMaQuanAn();
                        deleteOnFireBase(maQuanAn);


//                         x??a tr??n listview
                        context.DeleteCommnet(position);
                    }
                });
                builder.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();


            }
        });

        return convertView;
    }

    //
    private void deleteOnFireBase(String maQuanAn) {
        reference.child(maQuanAn).removeValue();
        Toast.makeText(context, "X??a " + maQuanAn + " Th??nh C??ng", Toast.LENGTH_SHORT).show();
    }

}
