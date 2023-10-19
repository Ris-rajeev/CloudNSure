const express = require("express");
const { createProxyMiddleware } = require("http-proxy-middleware");
const cors = require("cors");
const app = express();
const proxy = require("express-http-proxy");

app.use(
  cors({
    origin: "*",
  })
);
// app.use('/repos', createProxyMiddleware({ target: 'https://try.gitea.io/api/v1', changeOrigin: true }));
// app.use('/sureconnect', createProxyMiddleware({ target: 'http://43.205.154.152:30174', changeOrigin: true }));
// app.use('/sureops', createProxyMiddleware({ target: 'http://43.205.154.152:31123', changeOrigin: true }));
// app.use('/entityBuilder', createProxyMiddleware({ target: 'http://43.205.154.152:30171', changeOrigin: true }));
// app.use('/surechat', createProxyMiddleware({ target: 'http://43.205.154.152:30172', changeOrigin: true }));
// app.use('/jobpro', createProxyMiddleware({ target: 'http://43.205.154.152:30167', changeOrigin: true }));
// app.use('/Surecommunication', createProxyMiddleware({ target: 'http://43.205.154.152:30187', changeOrigin: true }));
// app.use('/surejob', createProxyMiddleware({ target: 'http://43.205.154.152:30188', changeOrigin: true }));
// app.use('/jobpro', createProxyMiddleware({ target: 'http://localhost:8087', changeOrigin: true }));
// app.use('/Surecommunication', createProxyMiddleware({ target: 'http://localhost:19002', changeOrigin: true }));
// app.use('/sureops', createProxyMiddleware({ target: 'http://localhost:9090', changeOrigin: true }));
// app.use('/', createProxyMiddleware({ target: 'http://localhost:8080', changeOrigin: true }));
app.use(
  "/",
  createProxyMiddleware({ target: "http://localhost:9292", changeOrigin: true })
);
// app.listen(3000);
// console.log('Node server running on port 3000');
app.listen(3000, () => {
  console.log("Server started in port 3000!");
});
