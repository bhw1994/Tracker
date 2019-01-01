package com.example.bhw_home.tracker;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.bhw_home.tracker.Firebase.FirebaseFireStoreManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Test;

import java.util.HashMap;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws InterruptedException {
        System.out.print("hello");




        assertEquals(4, 2 + 2);
    }
}