#!/bin/sh

export MEMORANDA_HOME=./data

LCP="./build/memoranda.jar:./lib/xom-1.0.jar:./lib/xercesImpl.jar:./lib/xmlParserAPIs.jar:./lib/nekohtml.jar:./lib/nekohtmlXni.jar:./lib/swexpl.jar:./lib/swag.jar"
echo ${LCP}
java -cp ${LCP} org.swingexplorer.Launcher net.sf.memoranda.Start $1
