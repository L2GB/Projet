/*
 * Transmission.h
 *
 *  Created on: 7 janv. 2015
 *      Author: menard
 */

#ifndef COMMUNICATION_TRANSMISSION_TRANSMISSION_H_
#define COMMUNICATION_TRANSMISSION_TRANSMISSION_H_

// External include
#include <iostream>

// Internal include
#include "../../tools/server/global/IdClient.h"
#include "../../tools/json/jansson.h"

class Transmission
{
	public:
		Transmission(){}
		virtual ~Transmission(){}

		/**
		 * Transmit a request from the application to the system.
		 *
		 * @param _order the request.
		 * @param _data a json structure or null if no data were in the message.
		 * @param _idClient the identification of the sender.
		 */
		virtual void executeOrder(std::string _order, json_t *_data, IdClient _idClient) = 0;
};

#endif /* COMMUNICATION_TRANSMISSION_TRANSMISSION_H_ */
