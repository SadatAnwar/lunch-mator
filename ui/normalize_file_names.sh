#!/usr/bin/env bash
mv $(pwd)/../public/ui/inline.*.bundle.js $(pwd)/../public/ui/inline.bundle.js
mv $(pwd)/../public/ui/main.*.bundle.js $(pwd)/../public/ui/main.bundle.js
mv $(pwd)/../public/ui/polyfills.*.bundle.js $(pwd)/../public/ui/polyfills.bundle.js
mv $(pwd)/../public/ui/vendor.*.bundle.js $(pwd)/../public/ui/vendor.bundle.js
mv $(pwd)/../public/ui/styles.*.bundle.css $(pwd)/../public/ui/styles.bundle.css
rm $(pwd)/../public/ui/index.html
