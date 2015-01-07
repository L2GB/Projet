/*
 * Transmitter.h
 *
 *  Created on: 4 déc. 2014
 *      Author: menard
 */

#ifndef COMMUNICATION_TRANSMISSION_TRANSMITTER_H_
#define COMMUNICATION_TRANSMISSION_TRANSMITTER_H_

// Internal include
#include "Transmission.h"
#include "../../tools/json/jansson.h"
#include "../../Object/ObjectManager.h"
#include "../android/RACC.h"

enum  Type{
		TYPE1,
		TYPE2
};

class Transmitter : public Transmission
{
	public:
		Transmitter();
		virtual ~Transmitter(){}

		void executeOrder(const std::string _order, json_t *_data, IdClient _idClient);

	private :
		Type getType(std::string _type);

	private:
		ObjectManager m_objectManager;
		RACC m_racc;
		// TODO add instance of requests
};

#endif /* COMMUNICATION_TRANSMISSION_TRANSMITTER_H_ */
