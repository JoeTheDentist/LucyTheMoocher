#!/bin/sh -e

# Generator of empty level
# $1: height
# $2: width
# $3: output file

cat /dev/null > $3
echo $1 >> $3
echo $2 >> $3

for (( i=0; i<$1; i++ ))
do
    str=''
    for (( j=0; j<$2-1; j++ ))
    do
        str=$str'0 '
    done
    echo $str'0' >> $3
done
