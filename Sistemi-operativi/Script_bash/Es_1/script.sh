#!/bin/bash

if [ $# -ne 1 ] # Controllo se non ci sono argomenti 
then
	echo Usa: $(basename $0) nomedirectory # Stampa come si usa lo script
	exit -1 # echo $? -> 255
fi

dir=$1

if [ ! -d $dir ] # Controllo che il primo argomento sia una directory
then
    echo "L'argomento $dir non e' una directory"   
    exit 1
fi

bdir=$(basename $dir)

if [ -w ${bdir}.tar.gz ] # Il file esiste ed è scrivibile
then
    echo -n "Il file $bdir.tar.gz esiste già, sovrascriverlo (S/N)? "
    read yn # Legge una riga dallo stdin e lo memorizza in yn
    if [ $yn != "S" ] # Controllo che yn sia diversa da "S"
    then
    	exit 0
    fi
    rm -f ${bdir}.tar.gz
fi

echo "Creo l'archivio con nome $bdir.tar.gz"

tar cf ${bdir}.tar $dir >>error.txt 2>&1 # Appende l’output sullo std-error nel file error.txt   
if [ $? -ne 0 ] # Controlla che il comando sia andato a buon fine
then
	echo "Errore nella creazione dell'archivio"
    exit 1
fi

gzip ${bdir}.tar >>error.txt 2>&1 # Appende l’output sullo std-error nel file error.txt
if [ $? -ne 0 ] # Controlla che il comando sia andato a buon fine
then
    echo
    echo "Errore nella compressione dell'archivio"
    exit 1
fi

echo "Archivio creato con successo, il contenuto dell’archivio e':"
tar tzvf ${bdir}.tar.gz  2>&1 # Redirige lo std-error sullo std-output
exit 0
