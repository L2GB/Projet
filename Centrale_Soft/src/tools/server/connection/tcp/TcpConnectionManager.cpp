/*
 * TcpConnectionManager.cpp
 *
 *  Created on: 21 oct. 2014
 *      Author: menard
 */

// Internal include
#include "TcpConnectionManager.h"
#include "../../../exceptions/NetworkException.h"

// External include
#include <iostream>
#include <unistd.h>

TcpConnectionManager::~TcpConnectionManager()
{
	removeServer();
}

void TcpConnectionManager::initSocket()
{
	m_server.sin_addr.s_addr = htonl(INADDR_ANY);
	m_server.sin_family = AF_INET;
	m_server.sin_port = htons(m_port);
	m_domain = AF_INET;
	m_type = SOCK_STREAM;

	std::cout << "Socket is opened on port : " << m_server.sin_port << std::endl;
}

void TcpConnectionManager::removeServer()
{
	if(m_socket != SOCKET_ERROR)
	{
		close(m_socket);
	}
}
