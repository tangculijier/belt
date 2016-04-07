package com.huang.activity;

import android.app.Activity;
import android.os.Bundle;

import com.huang.belt.Belt;
import com.huang.belt.R;

public class MainActivity extends Activity
{

	Belt belt;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		belt = (Belt)findViewById(R.id.belt);
	}

}
