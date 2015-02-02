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

#include "../../Object/Heater.h"
#include "../../Object/PowerPlug.h"

// Extrenal include
#include <iostream>

Transmitter::Transmitter()
{
	initializeMapping();
	m_racc.attach(this);
	m_objectManager.attach(this);
	loadData();
}

void Transmitter::initializeMapping()
{
	// TODO Complete the list
	m_type["GET_PROFIL_JOUR"] = GET_PROFIL_JOUR;
	m_type["LOAD_PROFIL_JOUR"] = LOAD_PROFIL_JOUR;
	m_type["SET_PROFIL_JOUR"] = SET_PROFIL_JOUR;
	m_type["RM_PROFIL_JOUR"] = RM_PROFIL_JOUR;
	m_type["GET_PROFIL_SEMAINE"] = GET_PROFIL_SEMAINE;
	m_type["LOAD_PROFIL_SEMAINE"] = LOAD_PROFIL_SEMAINE;
	m_type["SET_PROFIL_SEMAINE"] = SET_PROFIL_SEMAINE;
	m_type["RM_PROFIL_SEMAINE"] = RM_PROFIL_SEMAINE;
	m_type["NEW_OBJET"] = NEW_OBJET;
	m_type["GET_OBJETS"] = GET_OBJETS;
	m_type["LOAD_OBJETS"] = LOAD_OBJETS;
	m_type["SET_OBJET"] = SET_OBJET;
	m_type["RM_OBJET"] = RM_OBJET;
	m_type["GET_PIECES"] = GET_PIECES;
	m_type["ADD_OBJET_PIECE"] = ADD_OBJET_PIECE;
	m_type["REM_OBJET_PIECE"] = REM_OBJET_PIECE;
	m_type["RM_PIECE"] = RM_PIECE;
	m_type["POWEROFF_PRISE"] = POWEROFF_PRISE;
	m_type["POWERON_PRISE"] = POWERON_PRISE;
}

void Transmitter::loadData()
{
	executeOrder("LOAD_PROFIL_JOUR", NULL, IdClient(0));
	executeOrder("LOAD_PROFIL_SEMAINE", NULL, IdClient(0));
	executeOrder("LOAD_OBJETS", NULL, IdClient(0));
}

std::string Transmitter::createMessage(const std::string _order, json_t *_data, std::string _message)
{
	json_t *root = json_object();
	json_t *message = json_object();
	json_t *type = json_string(_order.c_str());

	json_object_set(message, "type", type);
	json_object_set(message, "data", _data);
	json_object_set(root, _message.c_str(), message);

	return json_dumps(root, 0);
}

void Transmitter::executeOrder(const std::string _order, json_t *_data, IdClient _idClient)
{
	// TODO call Request Object to execute order
	std::string messageToSend;

	std::cout << "Going to execute the order" << std::endl;
	switch (m_type[_order])
	{
		case NEW_OBJET:
			messageToSend = createMessage(_order, _data, "request");
			m_racc.sendData(messageToSend, _idClient);
			break;
		case GET_OBJETS:
			try
			{
				std::string objets = LocalFileManager::getObjects();
				json_t *data = json_loads(objets.c_str(), 0, NULL);

				messageToSend = createMessage(_order, data, "response");
				m_racc.sendData(messageToSend, _idClient);
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
			}
			break;
		case LOAD_OBJETS:
			try
			{
				std::string objets = LocalFileManager::getObjects();
				json_t *data = json_loads(objets.c_str(), 0, NULL);
				m_objectManager.loadObjects(data);
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
				m_objectManager.setObject(_data);
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

				messageToSend = createMessage(_order, data, "response");
				m_racc.sendData(messageToSend, _idClient);
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
			}
			break;
		case LOAD_PROFIL_JOUR:
			try
			{
				std::string jours = LocalFileManager::getDays();
				std::cout << "LOAD JOURS :  " << std::endl;
				std::cout << jours << std::endl;
				json_t *data = json_loads(jours.c_str(), 0, NULL);
				m_objectManager.loadDays(data);
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
				m_objectManager.setDay(_data);
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

				messageToSend = createMessage(_order, data, "response");
				m_racc.sendData(messageToSend, _idClient);
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
			}
			break;
		case LOAD_PROFIL_SEMAINE:
			try
			{
				std::string semaines = LocalFileManager::getWeeks();
				json_t *data = json_loads(semaines.c_str(), 0, NULL);
				m_objectManager.loadWeeks(data);
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
				m_objectManager.setWeek(_data);
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

				messageToSend = createMessage(_order, data, "response");
				m_racc.sendData(messageToSend, _idClient);
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
			}
			break;
		case LOAD_PIECES:
			try
			{
				std::string pieces = LocalFileManager::getRooms();
				json_t *data = json_loads(pieces.c_str(), 0, NULL);
				m_objectManager.loadRooms(data);
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
			}
			break;
		case ADD_OBJET_PIECE:
			try
			{
				LocalFileManager::addObjectToRoom(_data);
				m_objectManager.addObjectToRoom(_data);
				try
				{
					std::cout << "TEST PIECE : " << m_objectManager.getRoom("Chambre de Tutur")->getName() << std::endl;
				}
				catch(NotFoundException &e)
				{
					std::cout << e.what() << std::endl;
				}

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
		case REM_OBJET_PIECE:
			try
			{
				LocalFileManager::remObjectToRoom(_data);
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
		case POWERON_PRISE:
			try{
				// TODO modifier
				//PowerPlug *powerPlug = (PowerPlug *) m_objectManager.getObject("prise1");
				//powerPlug->switchON();
			}
			catch(NotFoundException &e){}

			break;
		case POWEROFF_PRISE:
			try{
				// TODO modifier
//				PowerPlug *powerPlug = (PowerPlug *) m_objectManager.getObject("prise1");
//				powerPlug->switchOFF();
			}
			catch(NotFoundException &e){}
			break;
		default:
			throw FormatException("Request type not identified");
			break;
	}
}
