/*
 * NetworkServer.cpp
 *
 *  Created on: 16 oct. 2014
 *      Author: menard
 */

// Internal include
#include "NetworkServer.h"

template <>
NetworkServer<UNIX>::NetworkServer(const std::string _pathSocket)
{
	m_connection = new UnixConnectionManager(_pathSocket);
}

template <>
NetworkServer<TCP>::NetworkServer(const int _port)
{
	m_connection = new TcpConnectionManager(_port);
}

template <typename T>
void NetworkServer<T>::attachInterface(DataObserver *_dataObserver)
{
	m_connection->attachInterface(_dataObserver);
}

template <typename T>
void NetworkServer<T>::detachInterface(DataObserver *_dataObserver)
{
	m_connection->detachInterface(_dataObserver);
}

template <typename T>
void NetworkServer<T>::writeData(std::string _dataToSend, IdClient _idClient)
{
	m_connection->writeData(_dataToSend, _idClient);
}

template class NetworkServer<UNIX>;
template class NetworkServer<TCP>;
