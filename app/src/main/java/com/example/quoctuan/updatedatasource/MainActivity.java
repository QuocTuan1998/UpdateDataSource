package com.example.quoctuan.updatedatasource;

import android.app.DatePickerDialog;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quoctuan.updatedatasource.adapter.SinhVienAdapter;
import com.example.quoctuan.updatedatasource.model.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ListView lvSinhVien;
    SinhVienAdapter adapterSinhVien;
    ArrayList<Student> arrSinhVien = new ArrayList<Student>();

    EditText txtMa,txtTen,txtNamSinh;
    CheckBox chkGioiTinh;
    Button btnOk,btnChon;

    AutoCompleteTextView txtNoiSinh;
    ArrayList<String> arrAuto = new ArrayList<String>();
    ArrayAdapter<String> adapterAuto;

    //xử lý ngày tháng
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_main);

        addControls();
        fakeData();
        addEvents();

    }

    private void addEvents() {

        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyNgayThang();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyNhapSinhVien();
            }
        });

    }

    private void xuLyNhapSinhVien() {

        String ma = txtMa.getText()+"";
        String ten = txtTen.getText()+"";
        String noiSinh = txtNoiSinh.getText()+"";
        String namSinh = txtNamSinh.getText()+"";
        boolean gioiTinh = chkGioiTinh.isChecked();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy",
                                                    Locale.getDefault());
        try {
            //tạo đối tượng mới sau đó thêm vào
            Student s = new Student(ma,ten,gioiTinh,df.parse(namSinh),noiSinh);
            arrSinhVien.add(s);
            //cập nhật lại giao diện
            adapterSinhVien.notifyDataSetChanged();
            xuLyNoiSinh(noiSinh);
        }catch (ParseException e){
            Toast.makeText(MainActivity.this,
                    e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void xuLyNoiSinh(String data) {

        //cho vào vòng lặp
        for (int i = 0;i<arrAuto.size();i++){
            String x = arrAuto.get(i);
        //nếu trùng thì thoát khỏi hàm
            if (x.trim().equalsIgnoreCase(data.trim()))
                return;
        }
        //Nếu xuống đây được tức là chưa tồn tại --> đưa vào arrAuto
        arrAuto.add(data);
        //đưa vào adapter
        adapterAuto = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                arrAuto);
        txtNoiSinh.setAdapter(adapterAuto);
    }
    public void fakeData(){

        Student s1 = new Student("01","Nguyễn Văn A",false,new Date(1980-1900,2,3),"Hồ Chí Minh");
        Student s2 = new Student("02","Nguyễn Văn B",false,new Date(1981-1900,3,8),"Hà Nội");
        Student s3 = new Student("03","Nguyễn Thị C",true,new Date(1982-1900,4,4),"Hải Phòng");
        Student s4 = new Student("04","Nguyễn Văn D",false,new Date(1985-1900,6,2),"Hà Giang");
        Student s5 = new Student("05","Nguyễn Hồng E",true,new Date(1989-1900,8,6),"Quảng Ninh");
        arrSinhVien.add(s1);
        arrSinhVien.add(s2);
        arrSinhVien.add(s3);
        arrSinhVien.add(s4);
        arrSinhVien.add(s5);
        adapterSinhVien.notifyDataSetChanged();

    }

    private void xuLyNgayThang() {

        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(calendar.YEAR,year);
                calendar.set(calendar.MONTH,month);
                calendar.set(calendar.DAY_OF_MONTH,dayOfMonth);
                txtNamSinh.setText(sdf.format(calendar.getTime()));

            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                MainActivity.this,
                callBack,
                calendar.get(calendar.YEAR),
                calendar.get(calendar.MONTH),
                calendar.get(calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    private void addControls() {

        txtMa = (EditText) findViewById(R.id.txtMa);
        txtTen = (EditText) findViewById(R.id.txtTen);

        txtNamSinh = (EditText) findViewById(R.id.txtNgaySinh);
        txtNamSinh.setText(sdf.format(calendar.getTime()));

        txtNoiSinh = (AutoCompleteTextView) findViewById(R.id.txtNoiSinh);
        chkGioiTinh = (CheckBox) findViewById(R.id.chkGioiTinh);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnChon = (Button) findViewById(R.id.btnChon);

        lvSinhVien = (ListView) findViewById(R.id.lvSinhVien);
        adapterSinhVien = new SinhVienAdapter(
                                        MainActivity.this,
                                        R.layout.item,
                                        arrSinhVien);
        lvSinhVien.setAdapter(adapterSinhVien);
    }
}
