var path = require('path');

/*
Defines the entry point as ./src/main/js/app.js
Creates sourcemaps so when debugging JS code in the browser, is able to link back to original source code.
Compile ALL of the JavaScript bits into ./src/main/resources/static/built/bundle.js, which is a JavaScript equivalent to a Spring Boot uber JAR
All your custom code AND the modules pulled in a la require() calls are stuffed into this file.
It hooks into the babel engine, using both es2015 and react presets, in order to compile ES6 React code into a format able to be run in any standard browser.
*/
module.exports = {
    entry: './src/main/js/app.js',
    devtool: 'sourcemaps',
    cache: true,
    mode: 'development',
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            }
        ]
    }
};