const express = require('express');
const api = express.Router();

api.get('/', (req, res)=> {
    res.json({
        data: {
            status: 200,
            message: 'Welcome to the API!'
        }
    });
});

api.use('/orders', require('./orders'));
api.use('/products', require('./products'));

module.exports = api;