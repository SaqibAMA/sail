const ordersController = require('../controller/ordersController');
const { use } = require("./utils");
const express = require("express");
const api = express.Router();

// get all orders
api.get("/", use(ordersController.get));

// get an order
api.get("/:id", use(ordersController.getOne));

// delete an order
api.delete("/:id", use(ordersController.del));

// update an order
api.put("/:id", use(ordersController.update));

module.exports = api;
