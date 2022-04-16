const get = (req, res, next)=> {
    res.send('get all orders');
}

const getOne = (req, res, next) => {
    res.send('get an order');
}

const del = (req, res, next) => {
    res.send('delete an order');
}

const update = (req, res, next)=> {
    res.send('update an order');
}

module.exports = {
    get: get,
    getOne: getOne,
    del,
    update
};