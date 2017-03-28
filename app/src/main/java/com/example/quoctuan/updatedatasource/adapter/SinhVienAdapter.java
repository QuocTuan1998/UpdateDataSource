package com.example.quoctuan.updatedatasource.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.quoctuan.updatedatasource.R;
import com.example.quoctuan.updatedatasource.model.Student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Class dùng để custom Layout item.xml
 */

public class SinhVienAdapter extends ArrayAdapter<Student>{
    Activity context;
    @LayoutRes int resource;
    ArrayList<Student> arrStudent;
    public SinhVienAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull ArrayList<Student> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrStudent = objects;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
        //gắn layout vào activity
        convertView = context.getLayoutInflater().inflate(resource,null);
       }
        //lấy TextView để lưu mã và tên
        TextView txtMaTen = (TextView) convertView.findViewById(R.id.txtMaVaTen);
        //lấy TextView để lưu giới tính năm sinh ,nơi sinh
        TextView txtKhac = (TextView) convertView.findViewById(R.id.txtThongTinKhac);
        //lấy sinh viên thứ position
        Student s = arrStudent.get(position);
        txtMaTen.setText(s.getMa()+" - "+s.getTen());
        //Dùng SinpleDateFormat để định dạng ngày tháng dd/MM/yyy
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        txtKhac.setText((s.isGioiTinh()?"Nữ-":"Nam-")+
                        sdf.format(s.getNgaySinh())+" - "+
                        s.getNoiSinh());

        return convertView;
    }
}
