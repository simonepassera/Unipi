.PHONY: clean cleanall

main: main.o libtokenizer.a
	gcc main.o -o main -L ./ -ltokenizer

main.o: main.c tokenizer.h
	gcc -c main.c -I ./

libtokenizer.a: tokenizer.o
	ar rsv libtokenizer.a tokenizer.o

tokenizer.o: tokenizer.c tokenizer.h
	gcc tokenizer.c -c -I ./

clean:
	rm -f main

cleanall: clean
	rm -f main.o libtokenizer.a tokenizer.o
