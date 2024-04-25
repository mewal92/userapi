package com.bookingbee.userapi.service;

import com.bookingbee.userapi.model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {


    FirestoreOptions firestoreOptions =
            FirestoreOptions.getDefaultInstance().toBuilder()
                    .setProjectId("interns-melinda")
                    .build();
    Firestore db = firestoreOptions.getService();


    public String registerUser(User user) throws Exception {
        if (user.getUid() == null || user.getUid().trim().isEmpty()) {
            return "Registration failed: 'uid' must be a non-empty String";
        }

        DocumentReference docRef = db.collection("users").document(user.getUid());
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return "User already exists.";
        }

        ApiFuture<WriteResult> result = db.collection("users").document(user.getUid()).set(user);
        return "User registered successfully with ID: " + user.getUid();
    }


    public User verifyToken(String idToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();


            DocumentReference docRef = db.collection("users").document(uid);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                return document.toObject(User.class);
            } else {
                return null;
            }
        } catch (FirebaseAuthException | InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }




    public List<User> getAllUsers() {
        ApiFuture<QuerySnapshot> query = db.collection("users").get();
        List<User> users = new ArrayList<>();
        try {
            QuerySnapshot querySnapshot = query.get();
            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                User user = document.toObject(User.class);
                if (user != null) {
                    user.setUid(document.getId());
                    users.add(user);
                }
            }
            System.out.println("Fetched users: " + users);
        } catch (Exception e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }
        return users;
    }
}
