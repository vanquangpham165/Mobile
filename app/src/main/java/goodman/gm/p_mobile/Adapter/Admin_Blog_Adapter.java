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

import goodman.gm.p_mobile.Controller.Admin_Blog;
import goodman.gm.p_mobile.Controller.Admin_ChiTiet_Blog;
import goodman.gm.p_mobile.Model.Blog;
import goodman.gm.p_mobile.R;

public class Admin_Blog_Adapter extends BaseAdapter {
    private Admin_Blog context;
    private int layout;
    private List<Blog> lstBlog;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("blogs");
    public Admin_Blog_Adapter(Admin_Blog context, int layout, List<Blog> lstBlog) {
        this.context = context;
        this.layout = layout;
        this.lstBlog = lstBlog;
    }

    @Override
    public int getCount() {
        return lstBlog.size();
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
        TextView tvMABlog, tvTieuDeBlogAdmin;
        Button btnDele;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
            holder = new ViewHolder();
            holder.tvMABlog = convertView.findViewById(R.id.tvMaBlogAdmin);
            holder.tvTieuDeBlogAdmin = convertView.findViewById(R.id.tvTieuDeBlogAdmin);
            holder.btnDele = convertView.findViewById(R.id.btnXoaBlog);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Blog blog = lstBlog.get(position);
        holder.tvMABlog.setText(blog.getmMaBlog());
        holder.tvTieuDeBlogAdmin.setText(blog.getmTieuDe());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Admin_ChiTiet_Blog.class);
                intent.putExtra("adminBlogs", lstBlog.get(position));
                context.startActivity(intent);
            }
        });

        holder.btnDele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context,
                        android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setTitle("B???n c?? mu???n x??a kh??ng ?");
                builder.setCancelable(false);
                builder.setPositiveButton("C??", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // x??a tr??n firebase
                        String maBlog = lstBlog.get(position).getmMaBlog();
                        deleteOnFireBase(maBlog);

                        // x??a tr??n listview
                        context.DeleteBlog(position);
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

    private void deleteOnFireBase(String maBlog) {
        reference.child(maBlog).removeValue();
        Toast.makeText(context, "X??a " + maBlog + " Th??nh C??ng", Toast.LENGTH_SHORT).show();
    }
}
