#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/msg.h>
#include <errno.h>
#include <signal.h>
#include <fcntl.h>

#include "comun.h"
#include "comunbarcos.h"

int main()
{


 int colagrafica, colaContador, lapipe;
 struct Parametros param;
 int fesclusao, fesclusae, flago, testigo=1;

 srand(getpid());
 signal(10,R10); // me preparo para la senyal 10
 signal(12,R12); // me preparo para la senyal 12

 colaContador = crea_cola(ftok ("./fichcola.txt", 144));
 registrarInicio();

 // Creamos y abrimos la cola de mensajes
 colagrafica=crea_cola(ftok ("./fichcola.txt", 18));
 // Abrimos las fifos
 fesclusae=open("/home/apso/esclusae",O_RDWR);
 fesclusao=open("/home/apso/esclusao",O_RDWR);
 flago=open("/home/apso/lago",O_RDWR);
 // Leemos de la pipe los par�metros
 read(2,&param,sizeof(param));
  //recuperamos la salida de errores
 lapipe=dup(2);
 close(2);
 open("/dev/tty",O_RDWR);

 visualiza(colagrafica, VESTEIN, PINTAR, TIPOESTE);
 // reservo sitio en el lago
 read(flago,&testigo,sizeof(testigo));
 // reservo la esclusa este
 read(fesclusae,&testigo,sizeof(testigo));
 //entro en la esclusa
 visualiza(colagrafica, VESTEIN, BORRAR, TIPOESTE);
 visualiza(colagrafica, VESCLUSAESTE, PINTAR, TIPOESTE);
 sleep(param.tesclusa);
//salgo y voy al lago
 visualiza(colagrafica, VESCLUSAESTE, BORRAR, TIPOESTE);
 visualiza(colagrafica, VLAGOE, PINTAR, TIPOESTE);
 write(fesclusae,&testigo,sizeof(testigo));
 sleep((rand()%(param.lagomax-param.lagomin+1))+param.lagomin);
 // reservo la esclusa oeste
 read(fesclusao,&testigo,sizeof(testigo));
 // salgo del lago y voy a la esclusa
 visualiza(colagrafica, VLAGOE, BORRAR, TIPOESTE);
 visualiza(colagrafica, VESCLUSAOESTE, PINTAR, TIPOESTE);
 write(flago,&testigo,sizeof(testigo));
 sleep(param.tesclusa);
 //salgo de la esclusa y me marcho
 visualiza(colagrafica, VESCLUSAOESTE, BORRAR, TIPOESTE);
 visualiza(colagrafica, VOESTEOUT, PINTAR, TIPOESTE);
 write(fesclusao,&testigo,sizeof(testigo));

 close(fesclusae);
 close(fesclusao);
 close(flago);

 registrarFin(colaContador, TIPOESTE);
}
