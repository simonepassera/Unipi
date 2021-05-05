CC = gcc
AR = ar
CFLAGS += -std=c99 -Wall
ARFLAGS = rvs
INCLUDES = -I. -I./utils/includes
LDFLAGS = -L.
OPTFLAGS = -g -O3 
LIBS = -lpthread

# aggiungere qui altri targets
TARGETS	= pipedec       \
		  testQueue     \
		  mybc


.PHONY: all clean cleanall

%: %.c
	$(CC) $(CFLAGS) $(INCLUDES) $(OPTFLAGS) -o $@ $< $(LDFLAGS) 

%.o: %.c
	$(CC) $(CFLAGS) $(INCLUDES) $(OPTFLAGS) -c -o $@ $<

all: $(TARGETS)

pipedec: dec

testQueue: testQueue.o libQueue.a
	$(CC) $(CCFLAGS) $(INCLUDES) $(OPTFLAGS) $(LDFLAGS) -o $@ $^ $(LIBS)

libQueue.a: queue.o queue.h
	$(AR) $(ARFLAGS) $@ $<

clean		: 
	rm -f $(TARGETS) dec

cleanall	: clean
	\rm -f *.o *~ libQueue.a
