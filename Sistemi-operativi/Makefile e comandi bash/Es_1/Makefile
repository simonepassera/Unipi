CC = gcc
CFLAGS += -std=c17 -Wall

INCDIR = ./include
LIBDIR = ./lib
SRCDIR = ./src
BINDIR = ./bin
TESTDIR = ./testfile

INCLUDES = -I $(INCDIR)
LIBS = -lbt
LDFLAGS = -L $(LIBDIR)
OPTFLAGS = -O3
DBGFLAGS = -g -DNDEBUG
FLAGS = $(DBGFLAGS) $(OPTFLAGS)

TARGET = $(BINDIR)/myprog

.PHONY: all test1 clean cleanall

# pattern da .c ad eseguibile
$(BINDIR)/%: $(SRCDIR)/%.c
	$(CC) $< -o $@ $(INCLUDES) $(LDFLAGS) $(LIBS) $(CFLAGS) $(OPTFLAGS)

# pattern per la generazione di un .o da un .c nella directory SRCDIR
$(SRCDIR)/%.o: $(SRCDIR)/%.c
	$(CC) $< -o $@ $(INCLUDES) -c -fPIC $(CFLAGS) $(OPTFLAGS)

# prima regola
all: $(TARGET)

# dipendenze dell'eseguibile che voglio generare
$(BINDIR)/myprog: $(SRCDIR)/myprog.c  $(LIBDIR)/libbt.so

# dipendenze della libreria
$(LIBDIR)/libbt.so: $(SRCDIR)/binarytree.o $(INCDIR)/binarytree.h $(INCDIR)/utils.h
	$(CC) $< -o $@ -shared

# per eseguire il test1
test1: $(TARGET)
	@echo "running test1"
	@LD_LIBRARY_PATH=$(LIBDIR) $(TARGET) 5 -2 28 > $(TESTDIR)/test1.out
	@diff $(TESTDIR)/test1.out $(TESTDIR)/test1-out_atteso.txt
	@echo "test1 OK"

clean:
	rm -f $(TARGET)

cleanall: clean
	find . \( -name "*~" -o -name "*.o" \) -exec rm -f {} \;
	rm -f $(LIBDIR)/libbt.so $(TESTDIR)/test1.out
