package com.example.wesley.mobileapp_bms;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity  {




    EditText descTxt, sizeTxt, priceTxt, quantityTxt, locationTxt, descEditTxt, sizeEditTxt, priceEditTxt, quantityEditTxt, locationEditTxt;
    ImageView contactImageImgView;
    List<Info> infos = new ArrayList<Info>();
    ListView contactListView;
    Uri imageUri = Uri.parse("android.resource://org.intracode.contactmanager/drawable/unknown");
    DatabaseHandler dbHandler;
    ArrayAdapter<Info> adapter;
    final Context context = this;
    Uri DecImg = Uri.parse("android.resource://org.intracode.contactmanager/drawable/decoration1");
    Uri PapImg = Uri.parse("android.resource://org.intracode.contactmanager/drawable/paper1");
    Uri InkImg = Uri.parse("android.resource://org.intracode.contactmanager/drawable/ink");
    boolean checked = false;
    String Type = "";
    String time = "";

        //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        final AlertDialog.Builder confirmAlert  = new AlertDialog.Builder(this);
        final AlertDialog.Builder delAlert  = new AlertDialog.Builder(this);
        //final AlertDialog.Builder editAlert = new AlertDialog.Builder(this);
        final AlertDialog.Builder editAlert = new AlertDialog.Builder(this);
        // Get the layout inflater
        final LayoutInflater inflater = this.getLayoutInflater();

        final String[] decor = {"Decorations","Paper","Ink"};

        //Variables from activity_main
        descTxt = (EditText) findViewById(R.id.txtDesc);
        sizeTxt = (EditText) findViewById(R.id.txtSize);
        priceTxt = (EditText) findViewById(R.id.txtPrice);
        quantityTxt = (EditText) findViewById(R.id.txtQuantity);
        locationTxt = (EditText) findViewById(R.id.txtLocation);
        contactListView = (ListView) findViewById(R.id.listView);
        contactImageImgView = (ImageView) findViewById(R.id.imgViewContactImage);
        dbHandler = new DatabaseHandler(getApplicationContext());



        //Variables from edit_layout


        //sizeEditTxt = (EditText) findViewById(R.id.txtSizeEdit);
        //priceEditTxt = (EditText) findViewById(R.id.txtPriceEdit);
        //quantityEditTxt = (EditText) findViewById(R.id.txtQuantityEdit);
        //locationEditTxt = (EditText) findViewById(R.id.txtLocationEdit);



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
                SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                time = s.format(new Date());



                if(rb1.isChecked())
                {
                    if(checked == false) {
                        imageUri = DecImg;
                    }
                    Type = "Decoration";
                }


                else if(rb2.isChecked() )
                {
                    if(checked == false) {
                        imageUri = PapImg;
                    }

                    Type = "Paper";
                }
                else if(rb3.isChecked())
                {
                    if(checked == false) {
                        imageUri = PapImg;
                    }

                    Type = "Ink";
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please select an item type", Toast.LENGTH_SHORT).show();
                    return;
                }


                info = new Info(dbHandler.getContactsCount(), String.valueOf(descTxt.getText()), String.valueOf(sizeTxt.getText()), String.valueOf(priceTxt.getText()), String.valueOf(quantityTxt.getText()), String.valueOf(locationTxt.getText()), time, Type,  imageUri);
                final String Desc;
                final String Size;
                final String Price;
                final String Loc;
                final String Quant;

                Desc = String.valueOf(descTxt.getText());
                Size = String.valueOf(sizeTxt.getText());
                Price = String.valueOf(priceTxt.getText());
                Loc = String.valueOf(locationTxt.getText());
                Quant = String.valueOf(quantityTxt.getText());

                if(Size.matches(""))
                {
                    Toast.makeText(getApplicationContext(), "Please input a size", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Desc.matches(""))
                {
                    Toast.makeText(getApplicationContext(), "Please input a description", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Price.matches(""))
                {
                    Toast.makeText(getApplicationContext(), "Please input a price", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Loc.matches(""))
                {
                    Toast.makeText(getApplicationContext(), "Please input a location", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Quant.matches(""))
                {
                    Toast.makeText(getApplicationContext(), "Please input a quantity", Toast.LENGTH_SHORT).show();
                    return;
                }


                    dbHandler.createContact(info);
                    infos.add(info);
                    Toast.makeText(getApplicationContext(), String.valueOf(descTxt.getText()) + " has been added to your list!", Toast.LENGTH_SHORT).show();
                    contactImageImgView.setImageURI(Uri.parse("android.resource://org.intracode.contactmanager/drawable/unknown"));
                    checked = false;
                //Send Email

                confirmAlert.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String to = "wesleymartins34@hotmail.com";
                                String subject = "Item Bought: "+time;
                                String message = "Description:  " +Desc+"\n\nSize/Length: "+Size+"\n\nPrice: R"+Price+"\n\nQuantity: "+Quant+"\n\nPurchase Location: "+Loc+"\n\nAdded Date: "+time;


                                Intent email = new Intent(Intent.ACTION_SEND);
                                email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
                                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                                email.putExtra(Intent.EXTRA_TEXT, message);

                                // need this to prompts email client only
                                email.setType("message/rfc822");

                                startActivity(Intent.createChooser(email, "Choose an Email client"));

                            }
                        });

                confirmAlert.setMessage("Do you want to send a record of this via email?" );
                confirmAlert.setTitle("Item Saved!");
                confirmAlert.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        });

                confirmAlert.setCancelable(true);
                confirmAlert.create().show();





                if(rb1.isChecked())
                {
                    rb1.setChecked(false);
                }
                if(rb2.isChecked())
                {
                    rb2.setChecked(false);
                }
                if(rb3.isChecked())
                {
                    rb3.setChecked(false);
                }
                descTxt.setText("");
                sizeTxt.setText("");
                priceTxt.setText("");
                quantityTxt.setText("");
                locationTxt.setText("");
                checked = false;


               // }







                //Toast.makeText(getApplicationContext(), String.valueOf(descTxt.getText()) + " already exists. Please use a different name.", Toast.LENGTH_SHORT).show();
            }
        });



        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, final View view,
                                    final int position, long id) {

                final Info c =  infos.get(position);
                final String desc = c.getDesc();
                final String size = c.getSize();
                final String price = c.getPrice();
                final String quantity = c.getquantity();
                final String location = c.getLocation();
                final String Time = c.getTime();
                final String type = c.getType();
                final String Description = String.valueOf(desc);



                Uri img = c.getImageURI();
                // When clicked, show a toast with the TextView text
                dlgAlert.setPositiveButton("Back",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        });
                dlgAlert.setMessage("Description:  " +String.valueOf(desc)+"\n\nSize/Length: "+String.valueOf(size)+"\n\nPrice: R"+String.valueOf(price)+"\n\nQuantity: "+String.valueOf(quantity)+"\n\nPurchase Location: "+String.valueOf(location)+"\n\nAdded Date: "+Time);
                dlgAlert.setTitle("Item Info: "+type);
                dlgAlert.setNegativeButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                delAlert.setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dbHandler.deleteContact(infos.get(position));
                                                infos.remove(position);
                                                adapter.notifyDataSetChanged();
                                                Toast.makeText(getApplicationContext(), "Item Successfully Deleted!", Toast.LENGTH_SHORT).show();


                                            }
                                        });

                                delAlert.setMessage("Are you sure you want to delete this item?" );
                                delAlert.setTitle("Confirm?");
                                delAlert.setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();

                                            }
                                        });

                                delAlert.setCancelable(true);
                                delAlert.create().show();



                            }
                        });

                dlgAlert.setNeutralButton("Edit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {




                                  editAlert.setView(inflater.inflate(R.layout.edit_layout,parent,false));



                                    editAlert.setPositiveButton("Update",
                                            new DialogInterface.OnClickListener()

                                    {
                                        public void onClick (DialogInterface dialog,int id){

                                            Toast.makeText(getApplicationContext(), "Item Successfully Updated!", Toast.LENGTH_SHORT).show();

                                    }
                                    }

                                    );


                                    editAlert.setTitle("Edit");
                                    editAlert.setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener()

                                            {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();

                                                }
                                            }
                                    );

                                    editAlert.setCancelable(true);
                                    editAlert.create().show();


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
                RadioButton rb1 = (RadioButton)findViewById(R.id.radioButton1);
                RadioButton rb2 = (RadioButton)findViewById(R.id.radioButton2);
                RadioButton rb3 = (RadioButton)findViewById(R.id.radioButton3);
                checked = true;
            }

        });

        if (dbHandler.getContactsCount() != 0)
            infos.addAll(dbHandler.getAllContacts());


        populateList();




    }



    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean check = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton1:
                if (check&&checked==false)
                    contactImageImgView.setImageURI(DecImg);
                break;
            case R.id.radioButton2:
                if (check&&checked==false)
                    contactImageImgView.setImageURI(PapImg);
                // Ninjas rule
                break;

            case R.id.radioButton3:
                if (check&&checked==false)
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
        adapter = new ContactListAdapter();
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

            TextView descr = (TextView) view.findViewById(R.id.txtDescList);
            descr.setText(currentInfo.getDesc());
            TextView price = (TextView) view.findViewById(R.id.priceList);
            price.setText("R"+currentInfo.getPrice());
            TextView date = (TextView) view.findViewById(R.id.DateList);
            date.setText(currentInfo.getTime());


            ImageView ivContactImage = (ImageView) view.findViewById(R.id.ivContactImage);
            ivContactImage.setImageURI(currentInfo.getImageURI());


            return view;
        }
    }
    @Override
    public void onBackPressed() {

        final AlertDialog.Builder exAlert  = new AlertDialog.Builder(this);
        exAlert.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        moveTaskToBack(true);

                    }
                });

        exAlert.setMessage("Are you sure you want to exit?" );
        exAlert.setTitle("Confirm?");
        exAlert.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        exAlert.setCancelable(true);
        exAlert.create().show();




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



}
