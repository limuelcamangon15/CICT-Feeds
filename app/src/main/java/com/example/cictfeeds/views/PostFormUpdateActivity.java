package com.example.cictfeeds.views;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cictfeeds.MainActivity;
import com.example.cictfeeds.R;
import com.example.cictfeeds.controllers.PostFormController;
import com.example.cictfeeds.models.Post;
import com.example.cictfeeds.utils.AppRepository;
import com.example.cictfeeds.utils.Helper;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PostFormUpdateActivity extends AppCompatActivity {

    private static final int PICK_IMAGES_REQUEST_CODE = 101;
    private static final int MAX_IMAGES = 5;
    private ImageButton ibBack;
    private EditText etTitle, etBody, etDate, etLocation;
    private AutoCompleteTextView actvTag;
    private Button btnAddImages, btnSavePost;
    private LinearLayout llSelectedImages;
    private Calendar calendar = Calendar.getInstance();
    private Date date = null;
    private Post postToUpdate;
    private final ArrayList<Uri> selectedImages = new ArrayList<>();
    private final PostFormController controller = new PostFormController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_form_update);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.screen_update_post), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent passedIntent = getIntent();
        String postIdToUpdate = passedIntent.getStringExtra("postId");

        postToUpdate= AppRepository.getPostById(postIdToUpdate);

        initViews(postToUpdate);
        setupTagDropdown();
        setupListeners();
    }

    private void initViews(Post postToUpdate) {
        ibBack = findViewById(R.id.ibBack);
        etTitle = findViewById(R.id.etPostTitle);
        etBody = findViewById(R.id.etPostBody);
        etDate = findViewById(R.id.etPostEventDate);
        etLocation = findViewById(R.id.etPostLocation);
        actvTag = findViewById(R.id.actvTag);
        btnAddImages = findViewById(R.id.btnAddImages);
        btnSavePost = findViewById(R.id.btnSavePost);
        llSelectedImages = findViewById(R.id.llSelectedImages);

        etTitle.setText(postToUpdate.getTitle());
        etBody.setText(postToUpdate.getContentBody());

        Date date = postToUpdate.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String dateString = null;

        if(date != null){
            dateString = sdf.format(date);
        }

        etDate.setText(dateString);

        etLocation.setText(postToUpdate.getLocation());
        actvTag.setText(postToUpdate.getTag());

        List<String> prevImages = postToUpdate.getImagePaths();

        if(!prevImages.isEmpty()){
            for(String path : prevImages){
                Uri uri = Uri.parse(path);
                selectedImages.add(uri);
            }
        }

        showImagePreviews();
    }

    private void setupTagDropdown() {
        String[] tags = {
                "Announcement",
                "Event",
                "Reminder",
                "Other",
                "Update",
                "News",
                "Feedback",
                "Highlight",
                "Community",
                "General"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, tags);
        actvTag.setAdapter(adapter);
        actvTag.setOnClickListener(v -> actvTag.showDropDown());
    }

    private void setupListeners() {
        etDate.setOnClickListener(v -> openDatePicker());
        btnAddImages.setOnClickListener(v -> openImagePicker());

        btnSavePost.setOnClickListener(v -> {
            if(etTitle.getText().toString().isEmpty()){
                Helper.showErrorSnackbar(findViewById(R.id.screen_update_post),"Post title cannot be empty");
            }
            else if(etBody.getText().toString().isEmpty()){
                Helper.showErrorSnackbar(findViewById(R.id.screen_update_post),"Post content body cannot be empty");
            }
            else{
                postToUpdate.setTitle(etTitle.getText().toString());
                postToUpdate.setContentBody(etBody.getText().toString());
                postToUpdate.setDate(date);
                postToUpdate.setTag(actvTag.getText().toString());
                postToUpdate.setLocation(etLocation.getText().toString());

                ArrayList<String> uriStrings = new ArrayList<>();
                if (selectedImages != null) {
                    for (Uri uri : selectedImages) {
                        uriStrings.add(uri.toString());
                    }
                }

                postToUpdate.setImagePaths(uriStrings);

                AppRepository.updatePost(postToUpdate);
                Helper.showSucccessSnackbar(findViewById(R.id.screen_update_post), "Post has been updated successfully");
                new android.os.Handler().postDelayed(() -> {
                    finish();
                    PostFormUpdateActivity.this.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
                }, 1000);
            }

            /*controller.createPost(
                    etTitle.getText().toString(),
                    etBody.getText().toString(),
                    date,
                    actvTag.getText().toString(),
                    etLocation.getText().toString(),
                    selectedImages,
                    new PostFormController.PostCallback() {
                        @Override
                        public void onSuccess(Post post) {
                            Helper.showSucccessSnackbar(findViewById(R.id.screen_create_post), "Post has been uploaded!");
                            new android.os.Handler().postDelayed(() -> {
                                finish();
                                PostFormUpdateActivity.this.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
                            }, 1000);
                        }

                        @Override
                        public void onError(String message) {
                            Helper.showErrorSnackbar(findViewById(R.id.screen_create_post),message);
                        }
                    }
            );*/
        });

        ibBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void openDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(PostFormUpdateActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        etDate.setText((month+1) + "/" + dayOfMonth + "/" + year);

                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(year, month, dayOfMonth, 0, 0, 0);
                        date = selectedCalendar.getTime();
                    }
                }, year, month, day
        );

        datePicker.show();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGES_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGES_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            selectedImages.clear();

            if (data.getClipData() != null) {
                int count = Math.min(data.getClipData().getItemCount(), MAX_IMAGES);
                for (int i = 0; i < count; i++) {
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    Uri savedUri = copyToInternalStorage(uri);
                    if (savedUri != null) selectedImages.add(savedUri);
                }
            } else if (data.getData() != null) {
                Uri savedUri = copyToInternalStorage(data.getData());
                if (savedUri != null) selectedImages.add(savedUri);
            }

            showImagePreviews();
        }
    }

    private Uri copyToInternalStorage(Uri sourceUri) {
        try {
            InputStream input = getContentResolver().openInputStream(sourceUri);
            String filename = "post_img_" + System.currentTimeMillis() + ".jpg";
            File file = new File(getFilesDir(), filename);
            OutputStream output = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.close();
            input.close();

            return Uri.fromFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private void showImagePreviews() {
        llSelectedImages.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i < selectedImages.size(); i++) {
            Uri imageUri = selectedImages.get(i);
            View view = inflater.inflate(R.layout.item_post_image_preview, llSelectedImages, false);

            ImageView imageView = view.findViewById(R.id.imgPreview);
            ImageButton btnRemove = view.findViewById(R.id.btnRemoveImage);

            imageView.setImageURI(imageUri);

            int index = i;
            btnRemove.setOnClickListener(v -> {
                selectedImages.remove(index);
                showImagePreviews();
            });

            llSelectedImages.addView(view);
        }
    }
}
