const { use } = require("./utils");
const express = require("express");
const api = express.Router();

// get all orders
api.get("/", use(orderController.get));

// get an order
api.get("/:id", use(orderController.getOne));

// delete an order
api.delete("/:id", use(orderController.delete));

// update an order
api.put("/:id", use(orderController.update));

module.exports = api;
