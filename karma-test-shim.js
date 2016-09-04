// Test shim for Karma, needed to load files via SystemJS

/*global jasmine, __karma__, window*/
Error.stackTraceLimit = Infinity;
jasmine.DEFAULT_TIMEOUT_INTERVAL = 1000;

__karma__.loaded = function () {
};

var distPath = '/base/target/karma/javascripts/';
var appPaths = ['app', 'test']; //Add all valid source code folders here

function isJsFile(path) {
  return path.slice(-3) == '.js';
}

function isSpecFile(path) {
  return path.slice(-8) == '.spec.js';
}

function isAppFile(path) {
  return isJsFile(path) && appPaths.some(function(appPath) {
      var fullAppPath = distPath + appPath + '/';
      return path.substr(0, fullAppPath.length) == fullAppPath;
    });
}

var allSpecFiles = Object.keys(window.__karma__.files)
  .filter(isSpecFile)
  .filter(isAppFile);

// Load our SystemJS configuration.
System.config({
  baseURL: distPath
});

System.config({
  defaultJSExtensions: true,
  paths: {
    // paths serve as alias
    'npm:': 'node_modules/'
  },
  map: {
    'rxjs': 'node_modules/rxjs',
    '@angular/core': 'npm:@angular/core/bundles/core.umd.js',
    '@angular/common': 'npm:@angular/common/bundles/common.umd.js',
    '@angular/compiler': 'npm:@angular/compiler/bundles/compiler.umd.js',
    '@angular/platform-browser': 'npm:@angular/platform-browser/bundles/platform-browser.umd.js',
    '@angular/platform-browser-dynamic': 'npm:@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js',
    '@angular/http': 'npm:@angular/http/bundles/http.umd.js',
    '@angular/router': 'npm:@angular/router/bundles/router.umd.js',
    '@angular/forms': 'npm:@angular/forms/bundles/forms.umd.js',

    // angular testing umd bundles
    '@angular/core/testing': 'npm:@angular/core/bundles/core-testing.umd.js',
    '@angular/common/testing': 'npm:@angular/common/bundles/common-testing.umd.js',
    '@angular/compiler/testing': 'npm:@angular/compiler/bundles/compiler-testing.umd.js',
    '@angular/platform-browser/testing': 'npm:@angular/platform-browser/bundles/platform-browser-testing.umd.js',
    '@angular/platform-browser-dynamic/testing': 'npm:@angular/platform-browser-dynamic/bundles/platform-browser-dynamic-testing.umd.js',
    '@angular/http/testing': 'npm:@angular/http/bundles/http-testing.umd.js',
    '@angular/router/testing': 'npm:@angular/router/bundles/router-testing.umd.js',
    '@angular/forms/testing': 'npm:@angular/forms/bundles/forms-testing.umd.js'
  }
});

System.import('@angular/core/testing')
  .then(function(coreTesting){
    return System.import('@angular/platform-browser-dynamic/testing')
      .then(function(browserTesting) {
        coreTesting.TestBed.initTestEnvironment(
          browserTesting.BrowserDynamicTestingModule,
          browserTesting.platformBrowserDynamicTesting());
      });
  }).then(function() {
  // Finally, load all spec files.
  // This will run the tests directly.
  return Promise.all(
    allSpecFiles.map(function (moduleName) {
      return System.import(moduleName);
    }));
}).then(function() {
    __karma__.start();
  },
  function(error) {
    __karma__.error(error.stack || error);
  });
