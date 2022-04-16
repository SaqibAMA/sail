const use = fn = (req, res, next) =>
    Promise.resolve(fn(req, res, next));

module.exports = {
    use
}