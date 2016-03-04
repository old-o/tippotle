#!/bin/bash

dir=`dirname "$0"`

java -cp "$dir/lib/*" net.doepner.app.tippotle.Main
