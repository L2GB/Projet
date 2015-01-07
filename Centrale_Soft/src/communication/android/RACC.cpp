/*
 * RACC.cpp
 *
 *  Created on: 4 déc. 2014
 *      Author: menard
 */

// Internal include
#include "RACC.h"

// External include
#include <iostream>

RACC::RACC(const int _port) : m_tcpServer(_port)
{
	m_tcpServer.attachInterface(this);
}

void RACC::receiveOrder(const std::string _dataReceive, IdClient _idClient)
{
	std::cout << "Coucou from the server" << std::endl;
	std::cout << "Data receive :" << std::endl;
	std::cout << _dataReceive << std::endl;
	m_tcpServer.writeData(_dataReceive, _idClient);
}
