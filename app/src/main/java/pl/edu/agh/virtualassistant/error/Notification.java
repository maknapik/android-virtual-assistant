package pl.edu.agh.virtualassistant.error;

import android.app.AlertDialog;
import android.content.Context;

public class Notification {

    public static void showCommunicationError(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Communication error")
                .setMessage("Communication error occurred. Please try again.")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {})
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
