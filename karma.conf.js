module.exports = function(config) {
  config.set({

    basePath: '.',

    frameworks: ['jasmine'],

    files: [
      {pattern: 'node_modules/core-js/client/shim.min.js', included: true, watched: false},
      {pattern: 'node_modules/es6-shim/es6-shim.js', included: true, watched: false},
      {pattern: 'node_modules/zone.js/dist/zone.js', included: true, watched: false},
      {pattern: 'node_modules/zone.js/dist/jasmine-patch.js', included: true, watched: false},
      {pattern: 'node_modules/reflect-metadata/Reflect.js', included: true, watched: false},
      {pattern: 'node_modules/systemjs/dist/system.src.js', included: true, watched: true},
      {pattern: 'node_modules/zone.js/dist/async-test.js', included: true, watched: false},
      {pattern: 'node_modules/zone.js/dist/fake-async-test.js', included: true, watched: false},


      {pattern: 'node_modules/rxjs/**/*', included: false, watched: false},
      {pattern: 'node_modules/rxjs/**/*.js.map', included: false, watched: false },
      {pattern: 'node_modules/@angular/**/*.js', included: false, watched: true},
      {pattern: 'node_modules/@angular/core/testing.js', included: false, watched: true},
      {pattern: 'node_modules/angular2*/*.js', included: false, watched: true},
      {pattern: 'node_modules/systemjs/dist/system-polyfills.js', included: false, watched: false},

      {pattern: 'target/karma/javascripts/test/common/pipes/test.spec.js', included: false, watched: true},
      {pattern: 'target/karma/**/*.js', included: false, watched: true},


      // paths loaded via module imports

      // paths to support debugging with source maps in dev tools
      {pattern: 'app/assets/javascripts/**/*.ts', included: false, watched: false},
      {pattern: 'target/karma/**/*.js.map', included: false, watched: false},
      {pattern: 'target/karma/javascripts/app/**/*.html', included: false, watched: false},
      {pattern: 'target/karma/javascripts/app/**/*.css', included: false, watched: true},
      {pattern: 'target/karma/javascripts/**/*.json', included: false, watched: true},

      {pattern: 'karma-test-shim.js', included: true, watched: true}
    ],

    // proxied base paths
    proxies: {
      // required for component assests fetched by Angular's compiler
      '/base/node_modules/@angular/compiler//bundles/compiler.umd.js': '/base/node_modules/@angular/compiler/bundles/compiler.umd.js',
      '/base/node_modules/@angular/core//bundles/core.umd.js': '/base/node_modules/@angular/core/bundles/core.umd.js',
      '/base/node_modules/@angular/platform-browser//bundles/platform-browser.umd.js': '/base/node_modules/@angular/platform-browser/bundles/platform-browser.umd.js',
      '/base/node_modules/@angular/common//bundles/common.umd.js': '/base/node_modules/@angular/common/bundles/common.umd.js',
      '/assets/': '/base/target/karma',
      '/base/target/karma/javascripts/node_modules': '/base/node_modules'
    },

    exclude: [
      // Vendor packages might include spec files. We don't want to use those.
      'node_modules/**/*.spec.js'
    ],

    port: 9876,

    logLevel: config.LOG_INFO,

    colors: true,

    autoWatch: true,

    browsers: ['PhantomJS'],

    // Coverage reporter generates the coverage
    reporters: ['progress', 'junit'],

    junitReporter : {
      outputFile : "test-results.xml"
    },

    singleRun: true
  })
};
