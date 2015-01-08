//============================================================================
// Name        : Centrale_Soft.cpp
// Author      : Caillot Peruchette
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

// External include
#include <iostream>
#include <unistd.h>

// Internal include
#include "communication/transmission/Transmitter.h"

int main()
{
	Transmitter transmitter; //default TCP/IP port : 2048

	while(1)
	{
		sleep(1);
	}

	return 0;
}
