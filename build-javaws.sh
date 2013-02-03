#!/bin/bash

ant clean
ant jar
mkdir build/javaws
cd build/javaws
find ../../modules -name "*.jar" -exec jar xf {} \; 
jar xf ../../dist/Mach8.jar
mkdir ../../dist/javaws
jar cf ../../dist/javaws/Mach8.jar *
cd ../..
cp etc/javaws/mach8.jnlp dist/javaws
cp etc/icon.png dist/javaws/icon.png
jarsigner -keystore etc/javaws/blackchip.keystore -storepass tomfoolery dist/javaws/Mach8.jar blackchip
rm -rf /var/www/javaws/mach8/* 
cp -r dist/javaws/* /var/www/javaws/mach8
