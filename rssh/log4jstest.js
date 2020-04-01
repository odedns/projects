const log4js = require('log4js');
const fs = require('fs');

const conf = (log4js) => {
    let data = fs.readFileSync('config/log4js.json');
    console.log(data);
    let logConfig = JSON.parse(data);
    log4js.configure(logConfig);
};

conf(log4js);
console.log("after config..");
const log = log4js.getLogger('test');

function doTheLogging(x) {
  log.info('Logging something %d', x);
}
let i = 0;
for (; i < 500; i += 1) {
  doTheLogging(i);
}

