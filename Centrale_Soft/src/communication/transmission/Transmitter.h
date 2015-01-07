/*
 * Transmitter.h
 *
 *  Created on: 4 déc. 2014
 *      Author: menard
 */

#ifndef COMMUNICATION_TRANSMISSION_TRANSMITTER_H_
#define COMMUNICATION_TRANSMISSION_TRANSMITTER_H_

// Internal include
#include "../android/RACC.h"
#include "../../tools/json/jansson.h"

class Transmitter
{
	public:
		Transmitter();
		virtual ~Transmitter();

		std::string executeOrder(const json_t _json);
		void extractOrder(std::string _stringToParse);

	private:
		RACC appliTransmitter;
};

#endif /* COMMUNICATION_TRANSMISSION_TRANSMITTER_H_ */
