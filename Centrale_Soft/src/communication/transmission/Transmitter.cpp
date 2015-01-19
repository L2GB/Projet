/*
 * Transmitter.cpp
 *
 *  Created on: 4 déc. 2014
 *      Author: menard
 */

// Internal include
#include "Transmitter.h"
#include "../../tools/exceptions/FormatException.h"
#include "../../tools/exceptions/NotFoundException.h"

// Extrenal include
#include <iostream>



Transmitter::Transmitter()
{
	initializeMapping();
	m_racc.attach(this);
	m_objectManager.attach(this);
}

void Transmitter::initializeMapping()
{
	// TODO Complete the list
	m_type["GET_PROFIL_JOUR"] = GET_PROFIL_JOUR;
	m_type["SET_PROFIL_JOUR"] = SET_PROFIL_JOUR;
	m_type["RM_PROFIL_JOUR"] = RM_PROFIL_JOUR;
	m_type["GET_PROFIL_SEMAINE"] = GET_PROFIL_SEMAINE;
	m_type["SET_PROFIL_SEMAINE"] = SET_PROFIL_SEMAINE;
	m_type["RM_PROFIL_SEMAINE"] = RM_PROFIL_SEMAINE;
	m_type["NEW_OBJET"] = NEW_OBJET;
	m_type["GET_OBJETS"] = GET_OBJETS;
	m_type["SET_OBJET"] = SET_OBJET;
	m_type["RM_OBJET"] = RM_OBJET;
	m_type["GET_PIECES"] = GET_PIECES;
	m_type["SET_PIECE"] = SET_PIECE;
	m_type["RM_PIECE"] = RM_PIECE;
}

void Transmitter::executeOrder(const std::string _order, json_t *_data, IdClient _idClient)
{
	// TODO call Request Object to execute order
	std::string messageToSend;

	json_t *root = json_object();
	json_t *message = json_object();
	json_t *type = json_string(_order.c_str());

	std::cout << "Going to execute the order" << std::endl;
	switch (m_type[_order])
	{
		case NEW_OBJET:

			json_object_set(message, "type", type);
			json_object_set(message, "data", _data);
			json_object_set(root, "request", message);

			messageToSend = json_dumps(root, 0);

			m_racc.sendData(messageToSend);
			break;
		case GET_OBJETS:
			try
			{
				std::string objets = LocalFileManager::getObjects();
				json_t *data = json_loads(objets.c_str(), 0, NULL);

				json_object_set(message, "type", type);
				json_object_set(message, "data", data);
				json_object_set(root, "response", message);

				messageToSend = json_dumps(root, 0);

				m_racc.sendData(messageToSend);
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
			}
			break;
		case SET_OBJET:
			try
			{
				LocalFileManager::setObject(_data);
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
			}
			catch(FormatException &e)
			{
				std::cout << e.what() << std::endl;
			}
			break;
		case RM_OBJET:
			try
			{
				LocalFileManager::rmObject(_data);
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
			}
			catch(FormatException &e)
			{
				std::cout << e.what() << std::endl;
			}
			break;
		case GET_PROFIL_JOUR:
			try
			{
				std::string jours = LocalFileManager::getDays();
				json_t *data = json_loads(jours.c_str(), 0, NULL);

				json_object_set(message, "type", type);
				json_object_set(message, "data", data);
				json_object_set(root, "response", message);

				messageToSend = json_dumps(root, 0);

				m_racc.sendData(messageToSend);
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
			}
			break;
		case SET_PROFIL_JOUR:
			try
			{
				LocalFileManager::setDay(_data);
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
			}
			catch(FormatException &e)
			{
				std::cout << e.what() << std::endl;
			}
			break;
		case RM_PROFIL_JOUR:
			try
			{
				LocalFileManager::rmDay(_data);
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
			}
			catch(FormatException &e)
			{
				std::cout << e.what() << std::endl;
			}
			break;
		case GET_PROFIL_SEMAINE:
			try
			{
				std::string semaines = LocalFileManager::getWeeks();
				json_t *data = json_loads(semaines.c_str(), 0, NULL);

				json_object_set(message, "type", type);
				json_object_set(message, "data", data);
				json_object_set(root, "response", message);

				messageToSend = json_dumps(root, 0);

				m_racc.sendData(messageToSend);
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
			}
			break;
		case SET_PROFIL_SEMAINE:
			try
			{
				LocalFileManager::setWeek(_data);
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
			}
			catch(FormatException &e)
			{
				std::cout << e.what() << std::endl;
			}
			break;
		case RM_PROFIL_SEMAINE:
			try
			{
				LocalFileManager::rmWeek(_data);
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
			}
			catch(FormatException &e)
			{
				std::cout << e.what() << std::endl;
			}
			break;
		case GET_PIECES:
			try
			{
				std::string pieces = LocalFileManager::getRooms();
				json_t *data = json_loads(pieces.c_str(), 0, NULL);

				json_object_set(message, "type", type);
				json_object_set(message, "data", data);
				json_object_set(root, "response", message);

				messageToSend = json_dumps(root, 0);

				m_racc.sendData(messageToSend);
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
			}
			break;
		case SET_PIECE:
			try
			{
				LocalFileManager::setRoom(_data);
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
			}
			catch(FormatException &e)
			{
				std::cout << e.what() << std::endl;
			}
			break;
		case RM_PIECE:
			try
			{
				LocalFileManager::rmRoom(_data);
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
			}
			catch(FormatException &e)
			{
				std::cout << e.what() << std::endl;
			}
			break;
		default:
			throw FormatException("Request type not identified");
			break;
	}
}
