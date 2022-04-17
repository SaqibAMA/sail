const get = (id) => {
    const products = [];
    if (id === undefined) {
        const docs = await db.collection("products").get();
        docs.forEach((doc) => {
          products.push({
            id: doc.id,
            ...doc.data(),
          });
        });
    } else {
        const docs = await db.collection("products").doc(id).get();
        if (docs.exists) {
          products.push({
            id: docs.id,
            ...docs.data(),
          });
        }
    }
    
    return products;
};

const create = (id, data) => {
    return db.collection("products").doc(id).set(data);
};

const del = (id) => {
    return db.collection("products").doc(id).delete();
};

module.exports = {
  get,
  create,
  del,
};
