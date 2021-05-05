CC = gcc
AR = ar
CFLAGS += -std=c17 -Wall -Werror
ARFLAGS =  rvs
INCLUDES = -I .
LDFLAGS = -L .
OPTFLAGS = -O3 

TARGETS	= background  \
		  creazombies \
		  dummyshell  \
		  family      \
		  fib13fork

.PHONY: all clean cleanall

%: %.c
	$(CC) $(CFLAGS) $(INCLUDES) $(OPTFLAGS) -o $@ $< $(LDFLAGS) 

%.o: %.c
	$(CC) $(CFLAGS) $(INCLUDES) $(OPTFLAGS) -c -o $@ $<

all	: $(TARGETS)

clean : 
	rm -f $(TARGETS)
	
cleanall : clean
	rm -f *.o *~ 
