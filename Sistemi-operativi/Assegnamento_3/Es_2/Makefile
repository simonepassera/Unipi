.PHONY: clean cleanall

a.out: libtokenizer.so tokenizer.h main.o
	gcc main.o -L /home/simo/lib -ltokenizer

main.o: main.c
	gcc -c -I ./ main.c

libtokenizer.so: tokenizer.o
	gcc -shared tokenizer.o -o /home/simo/lib/libtokenizer.so

tokenizer.o: tokenizer.c
	gcc -fPIC -I ./ -c tokenizer.c

clean:
	rm -f a.out

cleanall: clean
	rm -f main.o libtokenizer.so tokenizer.o
