package com.example.bhw_home.tracker.Firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseFireStoreManager {
    static FirebaseFirestore db;

    static{
        db=null;
    }

    public static void init()
    {
        if(db==null)
        {
            db = FirebaseFirestore.getInstance();
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build();
            db.setFirestoreSettings(settings);
        }

    }
    public static CollectionReference getCollectionReference(String collection)
    {
        init();
        return db.collection(collection);
    }
    public static Task<QuerySnapshot> getAllDocumentFromCollection(String collection)
    {
        init();
        return db.collection(collection).get();
    }
    public static DocumentReference getDocument(String collection, String documentId)
    {
        init();
        return db.collection(collection).document(documentId);
    }


}
