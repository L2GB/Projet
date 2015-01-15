/*
 * RACC.h
 *
 *  Created on: 4 déc. 2014
 *      Author: menard
 */

#ifndef COMMUNICATION_ANDROID_RACC_H_
#define COMMUNICATION_ANDROID_RACC_H_

// Internal include
#include "../../tools/server/NetworkServer.h"
#include "../../tools/server/observer/DataObserver.h"
#include "../Communicator.h"


const int DEFAULT_PORT = 2048;

class RACC : public Communicator, DataObserver
{
	public:
		RACC(const int _port = DEFAULT_PORT);
		virtual ~RACC() {}

		void receiveOrder(const std::string _dataReceive, IdClient _idClient);
		void sendData(const std::string _data);

	private:
		NetworkServer<TCP> m_tcpServer;
		IdClient m_client;
};

#endif /* COMMUNICATION_ANDROID_RACC_H_ */
