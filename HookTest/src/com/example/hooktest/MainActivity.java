package com.example.hooktest;

import java.io.DataOutputStream;
import java.io.IOException;

import com.example.hooktest.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnHook = (Button) this.findViewById(R.id.btn_hook);
		btnHook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Process process = Runtime.getRuntime().exec("su");

					DataOutputStream writer = new DataOutputStream(process
							.getOutputStream());

					String inject = MainActivity.this.getFilesDir().toString() + "/inject";
					String proc = "com.example.testapp";			
					String lib = MainActivity.this.getFilesDir().toString()
							+ "/libInjectso.so";

					String strCmd = inject + " " + proc + " " + lib + "\n";
					writer.writeBytes(strCmd);
					writer.flush();
					writer.writeBytes("exit\n");
					writer.flush();
					try {
						process.waitFor();
						process.destroy();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
