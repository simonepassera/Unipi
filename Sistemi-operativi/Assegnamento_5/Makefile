CC = gcc
AR = ar
CFLAGS += -std=c99 -O3 -Wall -g
INCLUDES = -I . -I ./utils/includes
LDFLAGS = -L .
OPTFLAGS = -O3

TARGETS	= permessi \
		  lsdir    \
		  myfind   \
		  printdir \
		  mycp_sc  \
		  mycp_std

.PHONY: all clean cleanall test

%: %.c
	$(CC) $(CFLAGS) $(INCLUDES) $(OPTFLAGS) -o $@ $< $(LDFLAGS) 

%.o: %.c
	$(CC) $(CFLAGS) $(INCLUDES) $(OPTFLAGS) -c -o $@ $<

all: $(TARGETS)

test: mycp_sc mycp_std
	./test.sh ./mycp_sc
	./test.sh ./mycp_std
	  
clean: 
	rm -f $(TARGETS)
cleanall: clean
	rm -f *.o *~ filein fileout *.dat plot.eps plot.pdf
