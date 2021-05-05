#!/bin/bash

if [ ! $# -ge 2 ]
then
	echo Usage: $(basename $0) SRC-FILE [SRC-FILE]... DEST-FILE
	exit 1
fi

args=("$@")

for i in ${args[@]}
do
	if [ ! -f $i ]
	then
		echo "The $i argument is not a regular file!"
		exit 1
	fi
done

dest=$(($# - 1))
index=$((dest - 1))

while [ $index -ge 0 ]
do
	cat ${args[index]} >> ${args[dest]}
	((index--))
done

exit 0
