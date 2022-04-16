import { initializeApp } from "firebase/app";
import { getFirestore } from "firebase/firestore";

const firebaseConfig = {
  apiKey: "AIzaSyAU0i8RWuNg6DKiWIM8rb4cKmVCemP4yYM",
  authDomain: "sail-admin-83c4f.firebaseapp.com",
  projectId: "sail-admin-83c4f",
  storageBucket: "sail-admin-83c4f.appspot.com",
  messagingSenderId: "611139407057",
  appId: "1:611139407057:web:ab3a384edd568df188c9f0",
  measurementId: "G-9HRHF0V08E"
};

export const app = initializeApp(firebaseConfig);
export const db = getFirestore(app);