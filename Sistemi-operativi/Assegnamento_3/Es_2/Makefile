.PHONY: clean cleanall

main: main.o libtokenizer.so
	gcc main.o -o main -L /home/simo/lib -ltokenizer

main.o: main.c tokenizer.h
	gcc main.c -c -I ./

libtokenizer.so: tokenizer.o
	gcc -shared tokenizer.o -o /home/simo/lib/libtokenizer.so

tokenizer.o: tokenizer.c tokenizer.h
	gcc -fPIC -I ./ -c tokenizer.c

clean:
	rm -f main

cleanall: clean
	rm -f main.o tokenizer.o
