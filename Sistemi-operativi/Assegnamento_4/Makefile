CC = gcc
CFLAGS +=  -std=c17 -Wall -Wextra -Werror
OPTFLAGS = -O3

TARGETS = ./Es_1/main ./Es_2/main ./Es_3/main

.PHONY: all clean

./Es_%/main: ./Es_%/main.c
	$(CC) $< -o $@ $(CFLAGS) $(OPTFLAGS)

all: $(TARGETS)

clean:
	find . \( -name "main" -o -name "mat_dump.*" \) -exec rm -f {} \;
