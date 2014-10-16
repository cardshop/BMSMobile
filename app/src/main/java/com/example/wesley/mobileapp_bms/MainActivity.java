package com.example.wesley.mobileapp_bms;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wesley.mobileapp_bms.DatabaseHandler;
import com.example.wesley.mobileapp_bms.Info;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity  {




    EditText descTxt, lnameTxt, phoneTxt, emailTxt, addressTxt;
    ImageView contactImageImgView;
    List<Info> infos = new ArrayList<Info>();
    ListView contactListView;
    Uri imageUri = Uri.parse("android.resource://org.intracode.contactmanager/drawable/unknown");
    DatabaseHandler dbHandler;
    Uri wesImg = Uri.parse("android.resource://org.intracode.contactmanager/drawable/wesleyimage");
    Uri dadImg = Uri.parse("android.resource://org.intracode.contactmanager/drawable/dadimg");

    Uri DecImg = Uri.parse("android.resource://org.intracode.contactmanager/drawable/decoration1");
    Uri PapImg = Uri.parse("android.resource://org.intracode.contactmanager/drawable/paper1");
    Uri InkImg = Uri.parse("android.resource://org.intracode.contactmanager/drawable/ink");

    String Type = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        setContentView(R.layout.activity_main);

        final String[] decor = {"Decorations","Paper","Ink"};

        descTxt = (EditText) findViewById(R.id.txtDesc);
        lnameTxt = (EditText) findViewById(R.id.txtLastName);
        phoneTxt = (EditText) findViewById(R.id.txtPhone);
        emailTxt = (EditText) findViewById(R.id.txtEmail);
        addressTxt = (EditText) findViewById(R.id.txtAddress);
        contactListView = (ListView) findViewById(R.id.listView);
        contactImageImgView = (ImageView) findViewById(R.id.imgViewContactImage);
        dbHandler = new DatabaseHandler(getApplicationContext());




        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creator");
        tabSpec.setContent(R.id.tabCreator);
        tabSpec.setIndicator("Add Item");

        tabSpec.setIndicator("",getResources().getDrawable(R.drawable.add_icon));

        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.tabContactList);
        tabSpec.setIndicator("List");
        tabSpec.setIndicator("",getResources().getDrawable(R.drawable.list_icon));
        tabHost.addTab(tabSpec);






        final Button addBtn = (Button) findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton rb1 = (RadioButton)findViewById(R.id.radioButton1);
                RadioButton rb2 = (RadioButton)findViewById(R.id.radioButton2);
                RadioButton rb3 = (RadioButton)findViewById(R.id.radioButton3);
                Info info;

                if(rb1.isChecked())
                {
                    imageUri = DecImg;
                }
                if(rb2.isChecked())
                {
                    imageUri = PapImg;
                }
                if(rb3.isChecked())
                {
                    imageUri = InkImg;
                }


                info = new Info(dbHandler.getContactsCount(), String.valueOf(descTxt.getText()), String.valueOf(lnameTxt.getText()), String.valueOf(phoneTxt.getText()), String.valueOf(emailTxt.getText()), String.valueOf(addressTxt.getText()), imageUri);


                if (!contactExists(info)) {

                    dbHandler.createContact(info);
                    infos.add(info);
                    Toast.makeText(getApplicationContext(), String.valueOf(descTxt.getText()) + " has been added to your infos!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), String.valueOf(descTxt.getText()) + " already exists. Please use a different name.", Toast.LENGTH_SHORT).show();
            }
        });



        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Info c =  infos.get(position);
                String desc = c.getDesc();
                String lname = c.getLName();
                final String phone = c.getPhone();
                String email = c.getEmail();
                String address = c.getAddress();
                Uri img = c.getImageURI();
                // When clicked, show a toast with the TextView text
                dlgAlert.setPositiveButton("Back",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        });
                dlgAlert.setMessage("Description:  " +String.valueOf(desc)+"\n\nSize/Length: "+String.valueOf(lname)+"\n\nPrice: "+String.valueOf(phone)+"\n\nQuantity: "+String.valueOf(email)+"\n\nPurchase Location: "+String.valueOf(address));
                dlgAlert.setTitle("Item Info");
                dlgAlert.setNegativeButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:"+String.valueOf(phone)));
                                startActivity(callIntent);

                            }
                        });

                dlgAlert.setNeutralButton("Edit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:"+String.valueOf(phone)));
                                startActivity(callIntent);

                            }
                        });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();




                //code specific to first list item


            }
        });

        descTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                addBtn.setEnabled(String.valueOf(descTxt.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




        contactImageImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Info Image"), 1);
            }

        });

        if (dbHandler.getContactsCount() != 0)
            infos.addAll(dbHandler.getAllContacts());


        populateList();




    }



    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton1:
                if (checked)
                    contactImageImgView.setImageURI(DecImg);
                break;
            case R.id.radioButton2:
                if (checked)
                    contactImageImgView.setImageURI(PapImg);
                // Ninjas rule
                break;

            case R.id.radioButton3:
                if (checked)
                    contactImageImgView.setImageURI(InkImg);
                // Ninjas rule
                break;

        }
    }




    private boolean contactExists(Info info) {
        String name = info.getDesc();
        int contactCount = infos.size();

        for (int i = 0; i < contactCount; i++) {
            if (name.compareToIgnoreCase(infos.get(i).getDesc()) == 0)
                return true;
        }
        return false;
    }

    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == RESULT_OK) {
            if (reqCode == 1) {
                imageUri = data.getData();
                contactImageImgView.setImageURI(data.getData());
            }
        }
    }

    private void populateList() {
        ArrayAdapter<Info> adapter = new ContactListAdapter();
        contactListView.setAdapter(adapter);
    }

    private class ContactListAdapter extends ArrayAdapter<Info>
    {
        public ContactListAdapter()
        {
            super (MainActivity.this, R.layout.listview_item, infos);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

            Info currentInfo = infos.get(position);

            TextView name = (TextView) view.findViewById(R.id.txtLastNameList);
            name.setText(currentInfo.getLName());
            TextView phone = (TextView) view.findViewById(R.id.phoneNumber);
            phone.setText(currentInfo.getPhone());

            ImageView ivContactImage = (ImageView) view.findViewById(R.id.ivContactImage);
            ivContactImage.setImageURI(currentInfo.getImageURI());


            return view;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



}
