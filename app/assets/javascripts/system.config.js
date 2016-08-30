/**
 * System configuration for Angular 2 samples
 * Adjust as necessary for your application needs.
 */
(function (global) {
    function p(partialPath) {
        return 'assets/' + partialPath;
    }

    // map tells the System loader where to look for things
    var map = {
        'app': p('javascripts/app'), // 'dist',
        '@angular/common': p('lib/angular__common'),
        '@angular/compiler': p('lib/angular__compiler'),
        '@angular/core': p('lib/angular__core'),
        '@angular/forms': p('lib/angular__forms'),
        '@angular/http': p('lib/angular__http'),
        '@angular/platform-browser': p('lib/angular__platform-browser'),
        '@angular/platform-browser-dynamic': p('lib/angular__platform-browser-dynamic'),
        '@angular/router': p('lib/angular__router'),
        '@angular/router-deprecated': p('lib/angular__router-deprecated'),
        '@angular/upgrade': p('lib/angular__upgrade'),
        'angular2-in-memory-web-api': p('lib/angular2-in-memory-web-api'),
        'rxjs': p('lib/rxjs')
    };
    // packages tells the System loader how to load when no filename and/or no extension
    var packages = {
        'app': {main: 'main.js', defaultExtension: 'js'},
        'rxjs': {defaultExtension: 'js'},
        'angular2-in-memory-web-api': {main: 'index.js', defaultExtension: 'js'}
    };
    var ngPackageNames = [
        'common',
        'compiler',
        'core',
        'forms',
        'http',
        'platform-browser',
        'platform-browser-dynamic',
        'router',
        'router-deprecated',
        'upgrade'
    ];
    // Individual files (~300 requests):
    function packIndex(pkgName) {
        packages['@angular/' + pkgName] = {main: 'index.js', defaultExtension: 'js'};
    }

    // Bundled (~40 requests):
    function packUmd(pkgName) {
        packages['@angular/' + pkgName] = {main: '/bundles/' + pkgName + '.umd.js', defaultExtension: 'js'};
    }

    // Most environments should use UMD; some (Karma) need the individual index files
    var setPackageConfig = System.packageWithIndex ? packIndex : packUmd;
    // Add package entries for angular packages
    ngPackageNames.forEach(setPackageConfig);
    var config = {
        map: map,
        packages: packages
    };
    System.config(config);
})(this);
