/*
 * UnixConnectionManager.cpp
 *
 *  Created on: 15 oct. 2014
 *      Author: menard
 */

// Internal include
#include "UnixConnectionManager.h"
#include "../../../exceptions/NetworkException.h"

// External include
#include <cstring>
#include <iostream>
#include <unistd.h>

#define PATH_LENGTH_MAX 108

UnixConnectionManager::~UnixConnectionManager()
{
	removeServer();
}

void UnixConnectionManager::initSocket()
{
	m_server.sun_family = AF_UNIX;
	m_domain = AF_UNIX;
	m_type = SOCK_STREAM;

	if(m_pathSocket.size() < PATH_LENGTH_MAX - 1)
	{
		strncpy(m_server.sun_path, m_pathSocket.c_str(), m_pathSocket.size());
	}
	else
	{
		throw NetworkException("Socket path is too long : ");
	}

	std::cout << "Socket has name : " << m_server.sun_path << std::endl;
}

void UnixConnectionManager::removeServer()
{
	if(m_socket != SOCKET_ERR)
	{
		close(m_socket);
		unlink(m_pathSocket.c_str());
	}
}
