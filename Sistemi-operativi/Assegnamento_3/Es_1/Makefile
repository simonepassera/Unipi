.PHONY: clean cleanall

a.out: main.o libtokenizer.a
	gcc main.o -L ./ -ltokenizer

main.o: main.c tokenizer.h
	gcc -c main.c -I ./

libtokenizer.a: tokenizer.o
	ar rsv libtokenizer.a tokenizer.o

tokenizer.o: tokenizer.c
	gcc -c tokenizer.c

clean:
	rm -f a.out

cleanall: clean
	rm -f main.o libtokenizer.a tokenizer.o
