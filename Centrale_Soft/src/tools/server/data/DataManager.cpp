/*
 * DataManager.cpp
 *
 *  Created on: 14 oct. 2014
 *      Author: menard
 */

// Internal include
#include "DataManager.h"
#include "../global/IdClient.h"
#include "../../exceptions/NotFoundException.h"
#include "../../exceptions/NetworkException.h"

// External include
#include <algorithm>
#include <unistd.h>
#include <iostream>
#include <sys/select.h>
#include <sys/socket.h>

DataManager::DataManager()
{
	start();
}

DataManager::~DataManager()
{
	if(isRunning())
	{
		stop();
	}
}

void DataManager::sendToAllClient(const std::string _stringToSend)
{
	for(std::size_t i = 0 ; i < m_socketClients.size() ; i++)
	if(send(m_socketClients[i].getFd(), _stringToSend.c_str(), _stringToSend.size(), 0) < 0)
	{
		throw NetworkException("Error sending data to the client");
	}
}

void DataManager::sendToClient(const std::string _stringToSend, IdClient _idClient)
{
	if(send(_idClient.getFd(), _stringToSend.c_str(), _stringToSend.size(), 0) < 0)
	{
		throw NetworkException("Error sending data to the client");
	}
}

std::string DataManager::readFromClient(IdClient _idClient)
{
	char buffer[SIZE_MAX_DATA];
	int ret;

	if((ret = read(_idClient.getFd(), buffer, SIZE_MAX_DATA - 1)) <= 0)
	{
		throw NetworkException("Error reading data from client");
	}

	std::string stringToRet(buffer);
	return stringToRet;
}

void DataManager::addConnection(const unsigned int _fd)
{
	IdClient client(_fd);
	m_socketClients.push_back(client);
}

void DataManager::closeConnection(IdClient _idClient)
{
	std::vector<IdClient>::iterator it= std::find(m_socketClients.begin(), m_socketClients.end(), _idClient);

	if(it != m_socketClients.end())
	{
		close(_idClient.getFd());
		m_socketClients.erase(it);
		std::cout << "Connection closed" << std::endl;
	}
	else
	{
		throw NotFoundException("Connection to close not found");
	}
}

void DataManager::closeAllConnections()
{
	while(m_socketClients.size() > 0)
	{
		try
		{
			closeConnection(m_socketClients[0]);
		}
		catch(NotFoundException &e)
		{
			std::cout << "Error  closing all connections" << e.what() << std::endl;
		}
	}
}

void DataManager::run()
{
	int ret;
	int i;
	int nbMaxClient = 0;
	int nbClient = 0;

	while(isRunning())
	{
		m_tv.tv_sec = TIMEOUT_SELECT;
		m_tv.tv_usec = 0;
		FD_ZERO(&m_rfds);
		nbClient = (int)m_socketClients.size();

		for(i = 0 ; i < nbClient ; i++)
		{
			FD_SET(m_socketClients[i].getFd(), &m_rfds);
		}

		if(nbClient > 0)
		{
			nbMaxClient = m_socketClients[nbClient - 1].getFd();
		}

		ret = select(nbMaxClient + 1, &m_rfds, NULL, NULL, &m_tv);
		if(ret > 0)
		{
			std::cout << "Something happened" << std::endl;
			for (i = 0; i < nbClient; i++)
			{
				if (FD_ISSET(m_socketClients[i].getFd(), &m_rfds))
				{
					try
					{
						std::string dataReceive = readFromClient(m_socketClients[i]);
						sendData(dataReceive, m_socketClients[i]);
					}
					catch(NetworkException &e)
					{
						closeConnection(m_socketClients[i]); // We close connection with the client
					}
				}
			}
		}
	}
}
