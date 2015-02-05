/*
 * ObjectManager.cpp
 *
 *  Created on: 7 janv. 2015
 *      Author: menard
 */

// Internal include
#include "ObjectManager.h"
#include "Heater.h"
#include "PowerPlug.h"
#include "../tools/exceptions/FormatException.h"
#include "../tools/exceptions/NotFoundException.h"

ObjectManager::ObjectManager()
{
	initializeMapping();
}

void ObjectManager::initializeMapping()
{
	// TODO Complete the list
	m_typeObjet["PRISE"] = PRISE;
	m_typeObjet["CHAUFFAGE"] = CHAUFFAGE;
}

void ObjectManager::zwave_startNetwork()
{
	m_zwaveController.startNetwork();
}

void ObjectManager::mode_inclusion()
{
	m_zwaveController.inclusion_mode_ON();
	sleep(40);
	m_zwaveController.inclusion_mode_OFF();
}

void powerPlug_callback_level(const ZDataRootObject root, ZWDataChangeType type, ZDataHolder data, void *args)
{
	ObjectManager *objectManager = (ObjectManager *)args;
	PowerPlug *powerPlug = (PowerPlug *)objectManager->getObject(objectManager->getObjectToConfigure()->getDeviceId(), objectManager->getObjectToConfigure()->getInstanceNum());
	powerPlug->getLevel();
	objectManager->notifyObjectChanges(powerPlug);
}

void ObjectManager::init_callback2(Object *_object)
{
	switch(m_typeObjet[_object->getType()])
	{
		case CHAUFFAGE:
			break;
		case PRISE:
			m_objectToConfigure = _object;
			m_zwaveController.zdata_set_callback(_object->getDeviceId(), _object->getInstanceNum(), 37, "level", powerPlug_callback_level, this);
			break;
	}
}

void ObjectManager::init_objects()
{
	for(std::size_t i = 0 ; i < m_objects.size() ; i++)
	{
		init_callback2(m_objects[i]);
		m_objects[i]->launch();

	}
}

void ObjectManager::loadDays(json_t *_days)
{
	json_t *jours = json_object_get(_days, "jours");
	/* array is a JSON array */
	size_t index;
	json_t *value;
	json_array_foreach(jours, index, value)
	{
		try
		{
			setDay(value);
		}
		catch(NotFoundException &e)
		{
			std::cout << e.what() << std::endl;
		}
	}
}

void ObjectManager::loadWeeks(json_t *_weeks)
{
	json_t *semaines = json_object_get(_weeks, "semaines");
	/* array is a JSON array */
	size_t index;
	json_t *value;
	json_array_foreach(semaines, index, value)
	{
		try
		{
			setWeek(value);
		}
		catch(NotFoundException &e)
		{
			std::cout << e.what() << std::endl;
		}
	}
}

void ObjectManager::newInstanceObject(json_t *_object)
{
	json_t *typeObjet = json_object_get(_object, "typeObjet");
	if(!json_is_string(typeObjet))
	{
		throw FormatException("setObject : typeObjet format not accepted");
	}
	std::string type = json_string_value(typeObjet);

	json_t *deviceid = json_object_get(_object, "deviceId");
	if(!json_is_integer(deviceid))
	{
		throw FormatException("setObject : deviceId format not accepted");
	}
	int deviceId = json_integer_value(deviceid);

	json_t *instancenum = json_object_get(_object, "instanceNum");
	if(!json_is_integer(instancenum))
	{
		throw FormatException("setObject : instanceNum format not accepted");
	}
	int instanceNum = json_integer_value(instancenum);

	json_t *name;
	std::string nom;
	json_t *planning_json;
	std::string planning;
	json_t *connecte_json;
	bool connecte;
	json_t *inconnu_json;
	bool inconnu;
	json_t *tConfort;
	int tconfort;
	json_t *tEco;
	int teco;

	switch (m_typeObjet[type])
	{
		case PRISE:
			std::cout << "type : " << type << std::endl;
			name = json_object_get(_object, "nomObjet");
			if(!json_is_string(name))
			{
				throw FormatException("setObject : nomObjet format not accepted");
			}
			nom = json_string_value(name);

			planning_json = json_object_get(_object, "planning");
			if(!json_is_string(planning_json))
			{
				throw FormatException("setObject : planning format not accepted");
			}
			planning = json_string_value(planning_json);

			connecte_json = json_object_get(_object, "connecte");
			if(!json_is_boolean(connecte_json))
			{
				throw FormatException("setObject : connecte format not accepted");
			}
			connecte = json_boolean_value(connecte_json);

			inconnu_json = json_object_get(_object, "inconnu");
			if(!json_is_boolean(inconnu_json))
			{
				throw FormatException("setObject : inconnu format not accepted");
			}
			inconnu = json_boolean_value(inconnu_json);

			PowerPlug *powerPlug;
			powerPlug = new PowerPlug(&m_zwaveController, deviceId, instanceNum, nom, m_planningManager.week_get(planning), connecte, inconnu);
			m_objects.push_back(powerPlug);
			break;
		case CHAUFFAGE:
			std::cout << "type : " << type << std::endl;
			tConfort = json_object_get(_object, "Tconfort");
			if(!json_is_integer(tConfort))
			{
				throw FormatException("setObject : tconfort heater format not accepted");
			}
			tconfort = json_integer_value(tConfort);

			tEco = json_object_get(_object, "Teco");
			if(!json_is_integer(tEco))
			{
				throw FormatException("setObject : teco heater format not accepted");
			}
			teco = json_integer_value(tEco);

			name = json_object_get(_object, "nomObjet");
			if(!json_is_string(name))
			{
				throw FormatException("setObject : name heater format not accepted");
			}
			nom = json_string_value(name);

			planning_json = json_object_get(_object, "planning");
			if(!json_is_string(planning_json))
			{
				throw FormatException("setObject : planning heater format not accepted");
			}
			planning = json_string_value(planning_json);

			connecte_json = json_object_get(_object, "connecte");
			if(!json_is_boolean(connecte_json))
			{
				throw FormatException("setObject : connecte heater format not accepted");
			}
			connecte = json_boolean_value(connecte_json);

			inconnu_json = json_object_get(_object, "inconnu");
			if(!json_is_boolean(inconnu_json))
			{
				throw FormatException("setObject : inconnu format not accepted");
			}
			inconnu = json_boolean_value(inconnu_json);

			Heater *heater;
			heater = new Heater(&m_zwaveController, deviceId, instanceNum, nom, m_planningManager.week_get(planning), connecte, inconnu, tconfort, teco);
			m_objects.push_back(heater);
			heater->print();
			break;
	}
}

void ObjectManager::loadObjects(json_t *_objects)
{
	json_t *objets = json_object_get(_objects, "objets");
	if(!json_is_array(objets))
	{
		throw FormatException("loadObjects : _objects format not accepted");
	}

	/* array is a JSON array */
	size_t index;
	json_t *value;
	json_array_foreach(objets, index, value)
	{
		try
		{
			newInstanceObject(value);
		}
		catch(NotFoundException &e)
		{
			std::cout << e.what() << std::endl;
		}
		catch(FormatException &e)
		{
			std::cout << e.what() << std::endl;
		}
	}
}

void ObjectManager::loadRooms(json_t *_rooms)
{
	json_t *pieces = json_object_get(_rooms, "pieces");
	/* array is a JSON array */
	size_t index;
	json_t *value;
	json_array_foreach(pieces, index, value)
	{
		try
		{
			addObjectToRoom(value);
		}
		catch(NotFoundException &e)
		{
			std::cout << e.what() << std::endl;
		}
	}
}

void ObjectManager::setDay(json_t *_day)
{
	json_t *name = json_object_get(_day, "nomProfil");
	if(!json_is_string(name))
	{
		throw FormatException("setDay : name format not accepted");
	}
	std::string nom = json_string_value(name);

	json_t *timeSlot = json_object_get(_day, "creneaux");

	if(!json_is_array(timeSlot))
	{
		throw FormatException("setDay : Time slot format not accepted");
	}

	/* array is a JSON array */
	size_t index;
	json_t *value;
	std::vector<TimeSlot> timeSlots;
	json_array_foreach(timeSlot, index, value)
	{
		json_t *autorisation = json_object_get(value, "autorisation");
		json_t *debut = json_object_get(value, "debut");
		json_t *fin = json_object_get(value, "fin");
		json_t *heureDebut = json_object_get(debut, "heure");
		json_t *minuteDebut = json_object_get(debut, "minute");
		json_t *heureFin = json_object_get(fin, "heure");
		json_t *minuteFin = json_object_get(fin, "minute");
		struct tm tmFin;
		struct tm tmDebut;
		tmDebut.tm_hour = json_integer_value(heureDebut);
		tmDebut.tm_min = json_integer_value(minuteDebut);
		tmFin.tm_hour = json_integer_value(heureFin);
		tmFin.tm_min = json_integer_value(minuteFin);
		TimeSlot creneau(autorisation, tmDebut, tmFin);
		timeSlots.push_back(creneau);
	}
	m_planningManager.day_set(nom, timeSlots);
}

void ObjectManager::setWeek(json_t *_week)
{
	json_t *name = json_object_get(_week, "nomProfil");
	if(!json_is_string(name))
	{
		throw FormatException("setWeek : name format not accepted");
	}
	std::string nom = json_string_value(name);

	json_t *jours = json_object_get(_week, "jours");

	if(!json_is_array(jours))
	{
		throw FormatException("setWeek : Time slot format not accepted");
	}

	/* array is a JSON array */
	size_t index;
	json_t *value;
	std::vector<Day*> days;
	json_array_foreach(jours, index, value)
	{
		std:: string dayName = json_string_value(value);
		Day *day = m_planningManager.day_get(dayName);
		days.push_back(day);
	}
	m_planningManager.week_set(nom, days);
}

void ObjectManager::addObjectToList(int _instanceNum, int _deviceId, const std::string _name, const std::string _typeObject)
{
	switch (m_typeObjet[_typeObject])
	{
		case PRISE:
			PowerPlug *powerPlug;
			powerPlug = new PowerPlug(&m_zwaveController, _deviceId, _instanceNum, _name);
			m_objects.push_back(powerPlug);
			break;
		case CHAUFFAGE:
			Heater *heater;
			heater = new Heater(&m_zwaveController, _deviceId, _instanceNum, _name);
			m_objects.push_back(heater);
			break;
		default:
			break;
	}
}

void ObjectManager::setObject(json_t *_object)
{
	json_t *typeObjet = json_object_get(_object, "typeObjet");
	if(!json_is_string(typeObjet))
	{
		throw FormatException("setObject : typeObjet format not accepted");
	}
	std::string type = json_string_value(typeObjet);

	json_t *deviceid = json_object_get(_object, "deviceId");
	if(!json_is_integer(deviceid))
	{
		throw FormatException("setObject : deviceId format not accepted");
	}
	int deviceId = json_integer_value(deviceid);

	json_t *instancenum = json_object_get(_object, "instanceNum");
	if(!json_is_integer(instancenum))
	{
		throw FormatException("setObject : instanceNum format not accepted");
	}
	int instanceNum = json_integer_value(instancenum);

	switch (m_typeObjet[type])
	{
		case PRISE:
			std::cout << "type : " << type << std::endl;
			try
			{
				PowerPlug *powerPlug = (PowerPlug *) getObject(deviceId, instanceNum);

				json_t *name = json_object_get(_object, "nomObjet");
				if(json_is_string(name))
				{
					std::string nom = json_string_value(name);
					powerPlug->setName(nom);
				}
				else
				{
					json_object_set(_object, "nomObjet", json_string(powerPlug->getName().c_str()));
				}

				json_t *planning_json = json_object_get(_object, "planning");
				if(json_is_string(planning_json))
				{
					std::string planning = json_string_value(planning_json);
					powerPlug->setPlanning(m_planningManager.week_get(planning));
				}
				else
				{
					json_object_set(_object, "planning", json_string(powerPlug->getPlanning()->getName().c_str()));
				}

				json_object_set(_object, "connecte", json_boolean(powerPlug->isConnected()));
				json_object_set(_object, "inconnu", json_boolean(powerPlug->isUnknown()));
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
				throw;
			}
			break;
		case CHAUFFAGE:
			std::cout << "type : " << type << std::endl;
			try
			{
				Heater *heater = (Heater *) getObject(deviceId, instanceNum);

				json_t *tConfort = json_object_get(_object, "Tconfort");
				if(json_is_integer(tConfort))
				{
					int tconfort = json_integer_value(tConfort);
					heater->setTconfort(tconfort);
				}
				else
				{
					json_object_set(_object, "Tconfort", json_integer(heater->getTconfort()));
				}

				json_t *tEco = json_object_get(_object, "Teco");
				if(json_is_integer(tEco))
				{
					int teco = json_integer_value(tEco);
					heater->setTeco(teco);
				}
				else
				{
					json_object_set(_object, "Teco", json_integer(heater->getTeco()));
				}

				json_t *name = json_object_get(_object, "nomObjet");
				if(json_is_string(name))
				{
					std::string nom = json_string_value(name);
					heater->setName(nom);
				}
				else
				{
					json_object_set(_object, "nomObjet", json_string(heater->getName().c_str()));
				}

				json_t *planning_json = json_object_get(_object, "planning");
				if(json_is_string(planning_json))
				{
					std::string planning = json_string_value(planning_json);
					heater->setPlanning(m_planningManager.week_get(planning));
				}
				else
				{
					json_object_set(_object, "planning", json_string(heater->getPlanning()->getName().c_str()));
				}

				json_object_set(_object, "connecte", json_boolean(heater->isConnected()));
				json_object_set(_object, "inconnu", json_boolean(heater->isUnknown()));
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
				throw;
			}
			break;
	}

/*
	Heater *object = (Heater *) getObject(nom);
	std::cout << "\n\n\nOBJET : " << std::endl;
	std::cout << "nom : " << object->getName() << std::endl;
	std::cout << "type : " << object->getType() << std::endl;
	std::cout << "planning : " << object->getPlanning()->getName() << std::endl;
	std::cout << "Tconfort : " << object->getTconfort() << std::endl;
	std::cout << "Tceco : " << object->getTeco() << "\n\n\n" << std::endl;
*/
}

void ObjectManager::addObjectToRoom(json_t *_room)
{
	json_t *nomPiece = json_object_get(_room, "nomPiece");
	if(!json_is_string(nomPiece))
	{
		throw FormatException("addObjectToRoom : name format not accepted");
	}
	std::string nom = json_string_value(nomPiece);

	json_t *objet = json_object_get(_room, "objet");
	std::string nomObjet = json_string_value(objet);

	if(!json_is_string(objet))
	{
		throw FormatException("addObjectToRoom : object format not accepted");
	}

	json_t *deviceid = json_object_get(_room, "deviceId");
	if(!json_is_integer(deviceid))
	{
		throw FormatException("setObject : deviceId format not accepted");
	}
	int deviceId = json_integer_value(deviceid);

	json_t *instancenum = json_object_get(_room, "instanceNum");
	if(!json_is_integer(instancenum))
	{
		throw FormatException("setObject : instanceNum format not accepted");
	}
	int instanceNum = json_integer_value(instancenum);

	try
	{
		Room *room = getRoom(nom);
		room->addObject(getObject(deviceId, instanceNum));
	}
	catch(NotFoundException &e)
	{
		std::cout << e.what() << std::endl;
		Room *newRoom = new Room(nom);
		newRoom->addObject(getObject(deviceId, instanceNum));
		m_rooms.push_back(newRoom);
	}
}

Object *ObjectManager::getObject(int _deviceId, int _instanceNum)
{
	for(std::size_t i = 0 ; i < m_objects.size() ; i++)
	{
		if(m_objects[i]->getInstanceNum() == _instanceNum && m_objects[i]->getDeviceId())
		{
			return m_objects[i];
		}
	}
	throw NotFoundException("L'objet n'existe pas");
	return NULL;
}

Room *ObjectManager::getRoom(std::string _name)
{
	for(std::size_t i = 0 ; i < m_rooms.size() ; i++)
	{
		if(m_rooms[i]->getName().compare(_name.c_str()) == 0)
		{
			return m_rooms[i];
		}
	}
	throw NotFoundException("L'objet n'existe pas");
	return NULL;
}

void ObjectManager::notifyObjectChanges(Object *_object)
{
	IdClient *id = NULL;
	m_transmission->executeOrder("HASCHANGED_OBJET", _object->json_object(), *id);
}

void ObjectManager::powerPlug_switchON(int _deviceId, int _instanceNum)
{
	PowerPlug *powerPlug = (PowerPlug *) getObject(_deviceId, _instanceNum);
	powerPlug->switchON();
}

void ObjectManager::powerPlug_switchOFF(int _deviceId, int _instanceNum)
{
	PowerPlug *powerPlug = (PowerPlug *) getObject(_deviceId, _instanceNum);
	powerPlug->switchOFF();
}
