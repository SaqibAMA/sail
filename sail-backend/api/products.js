const { use } = require("./utils");
const express = require('express');
const api = express.Router();

// get all products
api.get('/', use(productController.get));

// get a product
api.get('/:id', use(productController.getOne));

// create a product
api.post('/', use(productController.create));

// delete a product
api.delete('/:id', use(productController.delete));

module.exports = api;