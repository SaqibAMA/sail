import { db } from "../firebase";
import { collection, getDocs } from "firebase/firestore";

export const verifyAdmin = (email, onSuccess, onFailure) => {

  // access the allowed admins db
  getDocs(collection(db, "admin-accounts")).then((querySnapshot) => {
    // check if there are any admins that match the signed-in email
    querySnapshot.forEach((doc) => {
      // if admin email is found, set currentUser to the admin
      if (doc.data().email === email) {
          onSuccess();
      }
    });
  });

  onFailure();

};
