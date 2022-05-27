package com.petme.app.interfaces;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public interface AlertClicks {

	void positiveClick ( DialogInterface alert );

	void negativeClick ( DialogInterface alert );

}
