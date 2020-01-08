package com.example.myapplication.group;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Common;
import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


public class GroupInsertFragment extends Fragment {
    private final static String TAG = "TAG_GroupInsertFragment";
    private FragmentActivity activity;
    private ImageView ivGroup;
    private EditText etName, etUpper, etLower, etNotes;
    private TextView tvDateTime, tvDateTime2, tvDateTime3, tvPeople;
    private static int year, month, day;
    private Date date1, date2, date3;
    private SimpleDateFormat simpleDateFormat;
    private byte[] image;
    private static final int REQ_TAKE_PICTURE = 0;
    private static final int REQ_PICK_PICTURE = 1;
    private static final int REQ_CROP_PICTURE = 2;
    private Uri contentUri;
    private Button btDatePicker, btDatePicker2, btDatePicker3;
    private Button btTakePicture, btPickPicture;
    private Button btCancel, btFinshInsert;
    private SeekBar skPeople;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        activity.setTitle("新增旅遊團");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insert, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        ivGroup = view.findViewById(R.id.ivGroup);

        etName = view.findViewById(R.id.etName);

        tvDateTime = view.findViewById(R.id.tvDateTime);
        tvDateTime2 = view.findViewById(R.id.tvDateTime2);
        tvDateTime3 = view.findViewById(R.id.tvDateTime3);
        tvPeople = view.findViewById(R.id.tvPeople);

        skPeople = view.findViewById(R.id.skPeople);
        btPickPicture = view.findViewById(R.id.btPickPicture);
        btTakePicture = view.findViewById(R.id.btTakePicture);
        btDatePicker = view.findViewById(R.id.btDatePicker);
        btDatePicker2 = view.findViewById(R.id.btDatePicker2);
        btDatePicker3 = view.findViewById(R.id.btDatePicker3);
        btCancel = view.findViewById(R.id.btCancel);
        btFinshInsert = view.findViewById(R.id.btFinishInsert);

        btFinshInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });











        btTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 指定存檔路徑
                File file = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                file = new File(file, "picture.jpg");
                contentUri = FileProvider.getUriForFile(
                        activity, activity.getPackageName() + ".provider", file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

                if (intent.resolveActivity(activity.getPackageManager()) != null) {
                    startActivityForResult(intent, REQ_TAKE_PICTURE);
                } else {
                    Common.showToast(getActivity(), R.string.textNoCameraApp);
                }
            }
        });


        btPickPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_PICK_PICTURE);
            }
        });


        btDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDateTime2.setText(R.string.textDateStart);
                tvDateTime3.setText(R.string.textDateEnd);
                btDatePicker3.setEnabled(false);
                date2 = null;
                date3 = null;
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(
                                activity,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        date1 = new Date(year - 1900, month, dayOfMonth);
                                        tvDateTime.setText(R.string.textDateTime);
                                        tvDateTime.append( ":" + simpleDateFormat.format(date1));
                                        btDatePicker2.setEnabled(true);
                                    }
                                },
                                calendar.getTime().getYear() + 1900, calendar.getTime().getMonth(), calendar.getTime().getDate());

                DatePicker datePicker = datePickerDialog.getDatePicker();

                calendar.add(Calendar.DATE, 3);
                datePicker.setMinDate(calendar.getTimeInMillis());
                calendar.add(Calendar.MONTH, 1);
                calendar.add(Calendar.DAY_OF_MONTH, -7);
                datePicker.setMaxDate(calendar.getTimeInMillis());

                datePickerDialog.show();

            }
        });


        btDatePicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDateTime3.setText(R.string.textDateEnd);
                date3 = null;
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(
                                activity,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        date2 = new Date(year - 1900, month, dayOfMonth);
                                        tvDateTime2.setText(R.string.textDateTime);
                                        tvDateTime2.append( ":" + simpleDateFormat.format(date2));
                                        btDatePicker3.setEnabled(true);
                                    }
                                },
                                calendar.getTime().getYear() + 1900, calendar.getTime().getMonth(), calendar.getTime().getDate());

                DatePicker datePicker = datePickerDialog.getDatePicker();

                datePicker.setMinDate(new Date().getTime());


                calendar.setTime(date1);
                calendar.add(Calendar.DAY_OF_MONTH, -2);
                datePicker.setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        btDatePicker3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(
                                activity,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        date3 = new Date(year - 1900, month, dayOfMonth);
                                        tvDateTime3.setText(R.string.textDateTime);
                                        tvDateTime3.append( ":" + simpleDateFormat.format(date3));

                                    }
                                },
                                calendar.getTime().getYear() + 1900, calendar.getTime().getMonth(), calendar.getTime().getDate());

                DatePicker datePicker = datePickerDialog.getDatePicker();

                calendar.setTime(date2);
                datePicker.setMinDate(calendar.getTimeInMillis());


                calendar.setTime(date1);
                calendar.add(Calendar.DAY_OF_MONTH, -2);
                datePicker.setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });



        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 回前一個Fragment */
                navController.popBackStack();
            }
        });
        skPeople.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvPeople.setText("可報名人數: " + progress + "人");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_TAKE_PICTURE:
                    crop(contentUri);
                    break;
                case REQ_PICK_PICTURE:
                    crop(intent.getData());
                    break;
                case REQ_CROP_PICTURE:
                    Uri uri = intent.getData();
                    Bitmap bitmap = null;
                    if (uri != null) {
                        try {
                            bitmap = BitmapFactory.decodeStream(
                                    activity.getContentResolver().openInputStream(uri));
                            ivGroup.setImageBitmap(bitmap);
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            image = out.toByteArray();
                        } catch (FileNotFoundException e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                    if (bitmap != null) {
                        ivGroup.setImageBitmap(bitmap);
                    } else {
                        ivGroup.setImageResource(R.drawable.no_image);
                    }
                    break;
            }
        }
    }

    private void crop(Uri sourceImageUri) {
        File file = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        file = new File(file, "picture_cropped.jpg");
        Uri uri = Uri.fromFile(file);
        // 開啟截圖功能
        Intent intent = new Intent("com.android.camera.action.CROP");
        // 授權讓截圖程式可以讀取資料
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 設定圖片來源與類型
        intent.setDataAndType(sourceImageUri, "image/*");
        // 設定要截圖
        intent.putExtra("crop", "true");
        // 設定截圖框大小，0代表user任意調整大小
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        // 設定圖片輸出寬高，0代表維持原尺寸
        intent.putExtra("outputX", 0);
        intent.putExtra("outputY", 0);
        // 是否保持原圖比例
        intent.putExtra("scale", true);
        // 設定截圖後圖片位置
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        // 設定是否要回傳值
        intent.putExtra("return-data", true);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            // 開啟截圖activity
            startActivityForResult(intent, REQ_CROP_PICTURE);
        } else {
            Toast.makeText(activity, R.string.textNoImageCropAppFound,
                    Toast.LENGTH_SHORT).show();
        }
    }

}
