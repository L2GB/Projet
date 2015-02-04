/*
 * ConnectionManager.cpp
 *
 *  Created on: 14 oct. 2014
 *      Author: menard
 */

// Internal include
#include "../connection/ConnectionManager.h"
#include "../../exceptions/NetworkException.h"

// External include
#include <iostream>
#include <sys/select.h>

template <typename T>
ConnectionManager<T>::ConnectionManager() : m_socket(SOCKET_ERR)
{
	start();
}

template <typename T>
ConnectionManager<T>::~ConnectionManager()
{
	stop();
}

template <typename T>
void ConnectionManager<T>::attachInterface(DataObserver *_dataObserver)
{
	m_dataManager.attach(_dataObserver);
}

template <typename T>
void ConnectionManager<T>::detachInterface(DataObserver *_dataObserver)
{
	m_dataManager.detach(_dataObserver);
}

template <typename T>
void ConnectionManager<T>::writeData(std::string _dataTosend, IdClient _idClient)
{
	if(&_idClient != NULL)
		m_dataManager.sendToClient(_dataTosend, _idClient);
	else
		m_dataManager.sendToAllClient(_dataTosend);
}

template <typename T>
void ConnectionManager<T>::createSocket()
{
	m_socket = socket(m_domain, m_type, 0);
	if(m_socket == SOCKET_ERR)
	{
		throw NetworkException("Error creating socket");
	}
}

template <typename T>
void ConnectionManager<T>::bindSocket()
{
	if(bind (m_socket, (sockaddr *) &m_server, sizeof m_server) == SOCKET_ERR)
	{
		throw NetworkException("Error binding socket");
	}

	if(listen(this->m_socket, NB_MAX_CLIENTS) == SOCKET_ERR)
	{
		throw NetworkException("Error binding socket");
	}

}

template <typename T>
void ConnectionManager<T>::acceptClient()
{
	int size = sizeof(T);

	int ret = accept(m_socket, (sockaddr *)&m_server, (socklen_t*)&size);

	if(ret == SOCKET_ERR)
	{
		throw NetworkException("Error accept client");
	}
	else
	{
		m_dataManager.addConnection(ret);
		std::cout << "Connection with the socket " << ret << " established." << std::endl;
	}
}

template <typename T>
void ConnectionManager<T>::run()
{
	int ret;

	try
	{
		initSocket();
		createSocket();
		bindSocket();

		while(isRunning())
		{
			m_tv.tv_sec = TIMEOUT_SELECT;
			m_tv.tv_usec = 0;
			FD_ZERO(&m_rfds);
			FD_SET(m_socket, &m_rfds);
			std::cout << "Waiting for a new client on socket : " << std::endl;
			ret = select(m_socket + 1, &m_rfds, NULL, NULL, &m_tv);

			if(ret > 0 && FD_ISSET(m_socket, &m_rfds))
			{
				acceptClient();
			}
		}
	}
	catch(NetworkException &e)
	{
		std::cout << "Error running ConnectionManager : " << e.what() << std::endl;
	}
}

template class ConnectionManager<UNIX>;
template class ConnectionManager<TCP>;
