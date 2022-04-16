const { db } = require("./firebase");

const get = async (id) => {
  const orders = [];

  if (id === undefined) {
    const docs = await db.collection("orders").get();
    docs.forEach((doc) => {
      orders.push({
        id: doc.id,
        ...doc.data(),
      });
    });
  } else {
    const docs = await db.collection("orders").doc(id).get();
    if (docs.exists) {
      orders.push({
        id: docs.id,
        ...docs.data(),
      });
    }
  }

  return orders;
};

const del = async (id) => {
    return await db.collection("orders").doc(id).delete();
};

const update = async (id, data) => {
    
}

module.exports = {
  get,
  del,
};
