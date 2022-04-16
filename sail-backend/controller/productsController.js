const get = (req, res, next) => {
    res.send('get all products');
}

const getOne = (req, res, next) => {
    res.send('get a product');
}

const create = (req, res, next) => {
    res.send('create a product');
}

const del = (req, res, next) => {
    res.send('delete a product');
}

module.exports = {
    get,
    getOne,
    create,
    del
}