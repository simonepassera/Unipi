SHELL           = /bin/bash
CC		=  gcc
AR              =  ar
CFLAGS	        += -std=c99 -Wall -Werror -g
ARFLAGS         =  rvs
INCLUDES	= -I. -I ./utils/includes
LDFLAGS 	= -L.
OPTFLAGS	= -O3 
LIBS            = -lpthread

# aggiungere qui altri targets
TARGETS		= bcclient     \
		  bcclient_v2  \
                  bcserver     \
		  bcserver_v2  \
		  toup_server  \
		  toup_client  \
		  toup_server_select

.PHONY: all clean cleanall testtoup
.SUFFIXES: .c .h

%: %.c
	$(CC) $(CFLAGS) $(INCLUDES) $(OPTFLAGS) -o $@ $< $(LDFLAGS) $(LIBS)

%.o: %.c
	$(CC) $(CFLAGS) $(INCLUDES) $(OPTFLAGS) -c -o $@ $<

all		: $(TARGETS)

testtoup: toup_server toup_server_select toup_client
	./toup_server &
	./toup_test.sh toup1.txt
	kill -9 `pidof toup_server`
	@echo "primo test terminato"
	./toup_server_select &
	./toup_test.sh toup2.txt
	kill -9 `pidof toup_server_select`
	@echo "secondo test terminato"


clean		: 
	rm -f $(TARGETS)
cleanall	: clean
	\rm -f *.o *~ *.a ./cs_sock ./toup?.txt



