var admin = require("firebase-admin");
var serviceAccount = require("./serviceAccountKey.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://sail-app-c1585-default-rtdb.firebaseio.com"
});

// export admin and serviceAccount
module.exports = { admin, serviceAccount };