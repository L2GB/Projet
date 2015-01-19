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
		RM_PROFIL_JOUR,
		GET_PROFIL_SEMAINE,
		SET_PROFIL_SEMAINE,
		RM_PROFIL_SEMAINE,
		NEW_OBJET,
		GET_OBJETS,
		SET_OBJET,
		RM_OBJET,
		GET_PIECES,
		SET_PIECE,
		RM_PIECE
};

/**
 * Used to transmit received data to the other devices.
 */
class Transmitter : public Transmission
{
	public:
		Transmitter();
		virtual ~Transmitter(){}

		/**
		 * Execute and transmit the order.
		 */
		void executeOrder(const std::string _order, json_t *_data, IdClient _idClient);

	private :
		/**
		 * Associate a request to an enum.
		 */
		void initializeMapping();

		/**
		 * Create a message on a json format.
		 */
		std::string createMessage(const std::string _order, json_t *_data, std::string _message);

	private:
		std::map<std::string, Type> m_type;
		ObjectManager m_objectManager;
		RACC m_racc; //default TCP/IP port : 2048
};

#endif /* COMMUNICATION_TRANSMISSION_TRANSMITTER_H_ */
