#!/bin/bash
cat users | cut -f 1,6 -d : --output-delimiter=' ' | grep /home | LC_ALL=C sort
