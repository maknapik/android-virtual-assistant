package pl.edu.agh.virtualassistant.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import pl.edu.agh.virtualassistant.error.Notification;

public class LocationService {

    @SuppressLint("MissingPermission")
    public static void getLocation(Context context, Consumer<Location> onResponseCallback) {
        FusedLocationProviderClient fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(context);

        Task<Location> lastLocationTask = fusedLocationProviderClient.getLastLocation();
        lastLocationTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onResponseCallback.accept(task.getResult());
            } else {
                Notification.showLocationError(context);
            }
        });
    }

    public static void getLocation(Context context, BiConsumer<String, String> onResponseCallback) {
        getLocation(context, location -> {
            String latitude = String.valueOf(location.getLatitude());
            String longitude = String.valueOf(location.getLongitude());
            onResponseCallback.accept(latitude, longitude);
        });
    }
}
