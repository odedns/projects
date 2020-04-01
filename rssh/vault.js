const request = require('request');
const  fs = require('fs');
const log4js = require('log4js');
const config = require('config');


exports.getVaultKey = (id,user,cb)=> {

  var logger = log4js.getLogger();
  let vault_url = config.get('vault.url');
  let vault_token = config.get('vault.token');
  let vault_path = config.get('vault.path');
  
  logger.info("calling api ....on: " + vault_url);

  const options = {
      url: vault_url + id,
      json: true,
      headers: {
        'X-Vault-Token': vault_token
      }
    };

  request(options, (err, res, body) => {
      if (err) {
        return logger.error(err); 
      }
      let fileName = id + '_' + user + '_key.txt';
      if(body.data != null) {
        let pkey = eval(vault_path + '.' + user);
        fs.writeFileSync(fileName,pkey );
        fs.chmodSync(fileName,fs.constants.S_IWUSR |fs.constants.S_IRUSR);
        logger.info("writing key file ..: " + fileName);
        cb(fileName);
      } else {
        cb(null);
      }
  });
 
};


