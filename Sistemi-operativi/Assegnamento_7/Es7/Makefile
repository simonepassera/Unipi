CC = gcc
AR = ar
CFLAGS += -std=c99 -Wall
ARFLAGS = rvs
INCLUDES= -I . -I ./utils/includes
LDFLAGS = -L .
OPTFLAGS= -O3 -DNDEBUG 
LIBS = -lpthread

# aggiungere qui altri targets
TARGETS	= prodcons   \
		  pipeline   \
		  filosofi   \
		  testBQueue

.PHONY: all clean cleanall

%: %.c
	$(CC) $(CFLAGS) $(INCLUDES) $(OPTFLAGS) -o $@ $< $(LDFLAGS) $(LIBS)

%.o: %.c
	$(CC) $(CFLAGS) $(INCLUDES) $(OPTFLAGS) -c -o $@ $<

all: $(TARGETS)

pipeline: pipeline.o icl_hash.o libBQueue.a
	$(CC) $(CCFLAGS) $(INCLUDES) $(OPTFLAGS) $(LDFLAGS) -o $@ $^ $(LIBS)

testBQueue: testBQueue.o libBQueue.a
	$(CC) $(CCFLAGS) $(INCLUDES) $(OPTFLAGS) $(LDFLAGS) -o $@ $^ $(LIBS)

libBQueue.a: boundedqueue.o boundedqueue.h
	$(AR) $(ARFLAGS) $@ $<

clean: 
	rm -f $(TARGETS)

cleanall: clean
	rm -f *.o *~ *.a
