#include <iostream>
#include <cstdlib>
#include <time.h>
#include <stdlib.h>
#include <sstream>
#include <string>
#include "cdrawer.h"

using namespace std;

struct casilla{
	char bloque;
	int profundidad;
};

class Tesoro{
	private:
		casilla tablero[5][5];	// T tesoro | B bomba | A arena
		int Puntos;
	public:
		void Iniciar();
		int Jugar();
		void MostrarTablero();
};

void Tesoro::Iniciar(){
	Puntos = 15;
	int pos1, pos2;

	srand(time(NULL));

	for(int i = 0; i < 5; i++)
		for(int j = 0; j < 5; j++)
			tablero[i][j].bloque = 'A';

	tablero[rand()%5][rand()%5].bloque = 'T';
	//tablero[0][0].bloque = 'T';

	for(int i = 1; i <= 3; i++){
		for(int j = 0; j < i; j++){
			do{
				pos1 = rand()%5;
				pos2 = rand()%5;
			}while(tablero[pos1][pos2].bloque != 'A');
			tablero[pos1][pos2].bloque = 'B';
			tablero[pos1][pos2].profundidad = i;
		}
	}
}

int Tesoro::Jugar(){
	bool encontrado = false;
	bool error;
	bool continuar;
	std::stringstream mensaje;
	int entrada, fila, columna;
	int intentos = 15;
	casilla vision[5][5];


	for(int i = 0; i < 5; i++)
		for(int j = 0; j < 5; j++)
			vision[i][j].bloque = '-';

	do{
        continuar = intentos > 0 && !encontrado;
		system("cls");

		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				cout << vision[i][j].bloque;
				if(vision[i][j].bloque == 'B')
					cout << "(" << vision[i][j].profundidad << ")";
				cout << "\t";
			}
			cout << "\n";
		}

		cout << "\nPuntos acumulados:\t" << Puntos << "\nIntentos restantes:\t" << intentos << "\n" << mensaje.str() << "\n\n";

        if(continuar){
            do{
                cout << "Indique fila [1,5]: ";
                cin >> entrada;
                if(entrada < 1 or entrada > 5){
                    error = true;
                    cout << "El numero debe estar entre 1 y 5.\n";
                }else{
                    error = false;
                    fila = entrada-1;
                }
            }while(error);

            cout << "\n";

            do{
                cout << "Indique columna [1,5]: ";
                cin >> entrada;
                if(entrada < 1 or entrada > 5){
                    error = true;
                    cout << "El numero debe estar entre 1 y 5.\n";
                }else{
                    error = false;
                    columna = entrada-1;
                }
            }while(error);

            mensaje.str("");
            if(vision[fila][columna].bloque == '-'){
                intentos--;
                switch(tablero[fila][columna].bloque){
                    case 'A':
                        mensaje << "Han encotnrado arena. Ganas un punto.";
                        Puntos++;
                        break;
                    case 'T':
                        mensaje << "Han encotnrado el tesoro. Has ganado la partida.";
                        Puntos += 100;
                        encontrado = true;
                        break;
                    case 'B':
                        mensaje << "Han encotnrado una bomba. La bomba estalla y pierdes " << (4-tablero[fila][columna].profundidad) << " punto" <<+ (tablero[fila][columna].profundidad==3?"":"s") << ".";
                        Puntos -= 4-tablero[fila][columna].profundidad;
                        break;
                }
            }else{
                mensaje << "Ya probaste en ese espacio, no pasa nada.";
            }

            vision[fila][columna] = tablero[fila][columna];
        }else{
            cout << "Partida terminada.\nPulse una tecla para continuar.";
            cin.ignore();
            cin.ignore();
        }
	}while(continuar);

	return encontrado?Puntos:-Puntos;
}

void Tesoro::MostrarTablero(){
	for(int i = 0; i < 5; i++){
		for(int j = 0; j < 5; j++){
			cout << tablero[i][j].bloque;
			if(tablero[i][j].bloque == 'B')
				cout << "(" << tablero[i][j].profundidad << ")";
			cout << "\t";
		}
		cout << "\n";
	}
}

int main(void){
	Tesoro t;

	t.Iniciar();
	int puntos = t.Jugar();
	system("cls");
	if(puntos > 0){
		cout << "Enhorabuena, has ganado.\n\nPuntos:\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
		dibujarN(puntos,20,50);
	}else{
	    cout << "Has perdido, el mapa es el siguiente.\n\n";
	    t.MostrarTablero();
	    cout << "\n\nPulsa una tecla para continuar.";
	    cin.ignore();
	    cin.ignore();
		system("cls");
		cout << "Puntos:\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
		dibujarN(-puntos,20,30);
	}


	system("pause");
	return 0;
}
