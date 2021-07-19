#!/bin/bash

echo mela > file1
echo pesca\nlimone > file2

find . -mmin -2 -type f -exec grep -l limone {} \;
