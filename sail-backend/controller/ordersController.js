const db = require("../model/orders");

const get = (req, res, next) => {
  db.get()
    .then((orders) => {
      res.json({
        status: 200,
        data: { orders },
      });
    })
    .catch((err) => {
      res.json({
        status: 500,
        error: err,
        data: {},
      });
    });
};

const getOne = (req, res, next) => {
  db.get(req.params.id)
    .then((order) => {
      res.json({
        status: 200,
        data: { order },
      });
    })
    .catch((err) => {
      res.json({
        status: 500,
        error: err,
        data: {},
      });
    });
};

const del = (req, res, next) => {
  db.del(req.params.id)
    .then((order) => {
      res.json({
        status: 200,
        data: {
            message: "Order deleted successfully",
        },
      });
    })
    .catch((err) => {
      res.json({
        status: 500,
        error: err,
        data: {
            message: "Error deleting order",
        },
      });
    });
};

const update = (req, res, next) => {
  res.send("update an order");
};

module.exports = {
  get: get,
  getOne: getOne,
  del,
  update,
};
