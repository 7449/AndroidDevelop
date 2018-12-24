var argscheck = require('cordova/argscheck'),
    exec = require('cordova/exec'),
    Network = require('./NetWork');

var networkExport = {};

for (var key in Network) {
    networkExport[key] = Network[key];
}

networkExport.start = function (successCallback, errorCallback, options) {
    argscheck.checkArgs('fFO', 'Network.start', arguments);
    options = options || {};
    var getValue = argscheck.getValue;
    var networkType = getValue(options.networkType, -1);
    var args = [networkType, userName, passWord];
    console.log(args)
    exec(successCallback, errorCallback, "NetWork", "requestNetWork", args);
};

module.exports = networkExport;
