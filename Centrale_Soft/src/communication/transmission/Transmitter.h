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
#include "../../localFile/LocalFileManager.h"

// External include
#include <map>

//TODO complete the list
/**
 * Request type
 */
enum  Type{
		GET_PROFIL_JOUR,
		SET_PROFIL_JOUR,
		RM_PROFIL_JOUR
};

class Transmitter : public Transmission
{
	public:
		Transmitter();
		virtual ~Transmitter(){}

		void executeOrder(const std::string _order, json_t *_data, IdClient _idClient);

	private :
		void initializeMapping();

	private:
		std::map<std::string, Type> m_type;
		ObjectManager m_objectManager;
		RACC m_racc; //default TCP/IP port : 2048
		// TODO add instance of requests
};

#endif /* COMMUNICATION_TRANSMISSION_TRANSMITTER_H_ */
