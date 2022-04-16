const productsController = require('../controller/productsController');
const { use } = require("./utils");
const express = require('express');
const api = express.Router();

// get all products
api.get('/', use(productsController.get));

// get a product
api.get('/:id', use(productsController.getOne));

// create a product
api.post('/', use(productsController.create));

// delete a product
api.delete('/:id', use(productsController.del));

module.exports = api;