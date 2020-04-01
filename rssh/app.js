
const express = require('express');
const https = require('https');
const path = require('path');
const pty = require('node-pty');
const vault = require('./vault');
const log4js = require('log4js');
const config = require('config');
const fs = require('fs');

const conf = (log4js) => {
  let data = fs.readFileSync('config/log4js.json');
  let logConfig = JSON.parse(data);
  log4js.configure(logConfig);
};

conf(log4js);
var logger = log4js.getLogger();
pPhrase = config.get('certificate.passphrase');
var keyFile = config.get('certificate.key');
var certFile = config.get('certificate.cert');



const app = express();

var options = {
  key: fs.readFileSync(keyFile),
  cert: fs.readFileSync(certFile),
  timeout: 10000
};
if(pPhrase != null) {
  options.passphrase = pPhrase;
}
var server = https.createServer(options,app);
const expressWs = require('express-ws')(app,server);

// Serve static assets and resources
app.use(express.static(path.join(__dirname, 'public')));
app.use('/scripts', express.static(path.join(__dirname, 'node_modules')));
logger.info("rssh server started...");

app.delete('/keyfile/:user',function(req,res) {
  
    var fName = req.params.user;
    if(fName == null) {
      logger.info("delete keyfile: no user given");
      res.send("delete keyfile: no user given");
      res.status(404).end();
      return;
    } 
    fName = fName + "_key.txt";
    logger.info("deleting: " + fName);
    try { 
      fs.unlinkSync("./" + fName);
    } catch(err) {
      logger.error(err);
      res.send("Error deleting keyfile: " + err);
      res.status(500).end();
      return;
    }
    log.info("succesfully deleted :" + fName);
    res.status(200).end();
});

expressWs.app.ws('/shell', (ws, req) => {
 
  var sshPort = req.query.sshPort;
  var user = req.query.user;
  var id = req.query.id;
  logger.debug("got ws request id = " + id + " sshPort = " + sshPort + " user=" + user);
  if(sshPort == "null") {
    logger.error("port is null..");
    ws.send("Error: port is null");
    return;
  }
  if(user == "null") {
    logger.error("user is null..");
    ws.send("Error: user is null");
    return;
  }
  if(id == "null") {
    logger.error("id is null..");
    ws.send("Error: id is null");
    return;
  }

  vault.getVaultKey(id,user,(fname) => {

    logger.debug("fname= " + fname);
    if(fname == null) {
      logger.error("no vault key for user: " + user);
      return;
    }
    var cmd = '/usr/bin/ssh';
    logger.info('spawning: ' + cmd + ' -i ' + fname + ' -p ' + sshPort + ' ' + user + '@localhost');
    const shell = pty.spawn(cmd, ['-i',fname, '-p',sshPort,user+ '@localhost'], {
      name: 'xterm-color',
      cwd: process.env.PWD,
      env: process.env
    });
    
    // For all shell data send it to the websocket
    shell.on('data', (data) => {
      ws.send(data);
    });
    shell.on('exit', (data) => {
      logger.info("session closed..");
      ws.close();
    });
    // For all websocket data send it to the shell
    ws.on('message', (msg) => {
      shell.write(msg);
    });
    ws.on('error', (err) => {
      logger.error("got error:" + err);
    });
    ws.on('close', (ev) => {
      logger.info("socket closed...");
      shell.kill();
    });
  });
  
  

  });
  
server.listen(3000);


