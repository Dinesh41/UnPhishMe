package com.stalnobcrs.gounphish;


import  android.app.ProgressDialog;
import  android.content.Context;
import  android.content.Intent;
import  android.os.AsyncTask;
        import  android.os.Build;
        import  android.os.Bundle;
        import  android.support.annotation.RequiresApi ;
        import  android.support.v7.app.AlertDialog;
        import  android.support.v7.app.AppCompatActivity;
import android.util.Log;
import  android.view.View;
        import  android.webkit.WebChromeClient;
        import  android.webkit.WebSettings;
        import  android.webkit.WebView;
        import  android.webkit.WebViewClient;
        import  android.widget.Button;
        import  android.widget.EditText;
        import  android.widget.TextView;
import android.widget.Toast;

import  java.io.IOException;
        import  java.net.HttpURLConnection;
        import  java.net.MalformedURLException;
        import  java.net.URL;

//import android.app.AlertDialog;


public class  MainActivity extends  AppCompatActivity {

    EditText url ;
    private  Session session ;
    private  Button btnLogout ;
    boolean  loadingFinished  = true ;
    boolean  redirect  = false ;
    private  ProgressDialog progress ;
    private  String theWebsite,url_2 ;
    private  String [] x ;
    private  String xy ;
    private  String user , pwd ;
    private  TextView outputView ;
    private int  loadCount ;
    int hashcode1,hashcode2;
    int responsecode;


    @RequiresApi (api = Build.VERSION_CODES.KITKAT )
    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super .onCreate(savedInstanceState);
        setContentView(R.layout.activity_main );
//----------------------------------------------------------------------------------/ Declarations
        session  = new  Session(this );
        if  (!session .loggedin()) {
            logout();
        }

        btnLogout  = (Button) findViewById(R.id.btnLogout );
        btnLogout .setOnClickListener(new  View.OnClickListener() {

            @Override
            public void  onClick(View v) {
                logout();
            }
        });



        final  AppCompatActivity activity = this ;
        final  Button go = (Button) findViewById(R.id.bBack );
        final  WebView ourBrow;
        url  = (EditText) findViewById(R.id.etURL );
        AlertDialog.Builder alert = new  AlertDialog.Builder(this );
        alert.setTitle("Title here" );

        //----------------------------------------------------------------------------------/ Content Web view Load

        ourBrow = (WebView) findViewById(R.id.wvBrowser );
        String customHtml = "<html><body><h1>Hello, WebView</h1></body></html>" ;


        //ourBrow.loadUrl("theWebsite");



        //----------------------------------------------------------------------------------/ Get/Post Elements
        ourBrow.setWebChromeClient(new  WebChromeClient() {
            public void  onProgressChanged(WebView view, int  progress) {


                user  = getIntent().getStringExtra("CurrentUsername" );

                pwd  = getIntent().getStringExtra("CurrentUserPassword" );


                view.loadUrl("javascript: document.getElementById('m_login_email').value = "
                        + "'"  + user  + "';document.getElementById('m_login_password').value='"  + pwd  + "';document.getElementById('u_0_5').click();" );


                //String email = Util.getEmail(this);
                //String pwd = Util.getPassword(this);
                //ourBrow.getSettings().setJavaScriptEnabled(true);
                //ourBrow.setHttpAuthUsernamePassword(Config.SERVER_BASE_URL, "Application", user, pwd);


                WebSettings webSettings = ourBrow .getSettings();
                webSettings.setJavaScriptEnabled(true );
                url.setText(view.getUrl());



            }

        });

        ourBrow.setWebViewClient(new  WebViewClient() {

        });





//-----------------------------------------------------------------------------------/ Listeners and actions
        go.setOnClickListener(new  View.OnClickListener() {

            @Override
            public void  onClick(View v) {
                switch  (v.getId()) {
                    case  R.id.bBack :
                        theWebsite  = url .getText().toString();

                        loadCount  = 0 ;
                        if  (loadCount  == 0 ) {
                            ourBrow .loadUrl(theWebsite );
                            loadCount ++;
                           // outputView  = (TextView) findViewById(R.id.showOutput );
                        }




                        //===================================================================

                }

                //=======================================================================
            }
        });

    }

    private void  logout() {
        session .setLoggedin(false );
        finish();
        startActivity(new  Intent(MainActivity.this , LoginActivity.class ));
    }
    void makeToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    //==================================================
    public void  sendGetRequest(View View) {
        //new  GetClass(this ).execute();
        url_2=url.getText().toString();
        hashcode1=theWebsite.hashCode();
        hashcode2=url_2.hashCode();
        Log.i("HashInfo","Hashcode1: "+Integer.toString(hashcode1)+" Hashcode2 "+Integer.toString(hashcode2));
        if(hashcode1==hashcode2){
            responsecode=401;
            makeToast("Legitimate Website,Continue to login");
        }
        else
        {
            responsecode=200;
            makeToast("Malicious Website,Not suggested to login ");
        }
    }


    private class  GetClass extends  AsyncTask<String, Void, Void> {

        private final  Context context ;

        public  GetClass(Context c) {
            this .context  = c;
        }

        protected void  onPreExecute() {
            progress  = new  ProgressDialog(this .context );
            progress .setMessage("Loading" );
            progress .show();
        }

        @Override
        protected  Void doInBackground(String... params) {


            try  {



                URL url = new  URL(theWebsite );

                //url = new URL(targetURL);
                //connection = (HttpURLConnection)url.openConnection();



                HttpURLConnection connection = (HttpURLConnection) url.openConnection();


                String urlParameters = "fizz=buzz" ;
                connection.setRequestMethod("GET" );
                connection.setRequestProperty("USER-AGENT" , "Mozilla/5.0" );
                connection.setRequestProperty("ACCEPT-LANGUAGE" , "en-US,en;0.5" );

                int  responseCode = connection.getResponseCode();



                final  StringBuilder output = new  StringBuilder();
                output.append(System.getProperty ("line.separator" ) + "Request Parameter: "  + user + "/"  + pwd );
                output.append(System.getProperty ("line.separator" ) + "Response Code "  + responseCode);

                if  (responseCode==200 ) {
                    output.append(System.getProperty ("line.separator" ) + "WARNING: YOU ARE SUCCESSFULLY LOGGED IN WITH FAKE CREDENTIALS " );
                    output.append(System.getProperty ("line.separator" ) + "POTENTIAL MALICIOUS SITE " );
                }

                else if  (responseCode==401 )
                {
                    output.append(System.getProperty ("line.separator" ) + "THIS SITE IS CONFIRMED AS A LEGITIMATE SITE" );
                    output.append(System.getProperty ("line.separator" ) + "PLEASE PROCEED WITH LOGGING IN" );
                }

                else

                    output.append(System.getProperty ("line.separator" ) + "POTENTIAL MALICIOUS SITE " );

                MainActivity.this .runOnUiThread(new  Runnable() {

                    @Override
                    public void  run() {
                        outputView .setText(output );
                        progress .dismiss();



                    }
                });


            } catch  (MalformedURLException e) {
                //  TODO Auto-generated catch block
                e.printStackTrace();
            } catch  (IOException e) {
                //  TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null ;
        }

// protected void onPostExecute() {
        // progress.dismiss();
        // }==================================================


    }
}