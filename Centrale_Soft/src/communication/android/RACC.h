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

/**
 * Default port of the tcp/ip server.
 */
const int DEFAULT_PORT = 2048;

/**
 * Remote Application Communication Controller.
 * Enable the communication between the android application and the Raspberry.
 */
class RACC : public Communicator, DataObserver
{
	public:
		RACC(const int _port = DEFAULT_PORT);
		virtual ~RACC() {}

		/**
		 * Treat data received from the android application.
		 */
		void receiveOrder(const std::string _dataReceive, IdClient _idClient);

		/**
		 * Used to send data to the android application.
		 */
		void sendData(const std::string _data, IdClient _idClient);

	private:
		NetworkServer<TCP> m_tcpServer;
};

#endif /* COMMUNICATION_ANDROID_RACC_H_ */
