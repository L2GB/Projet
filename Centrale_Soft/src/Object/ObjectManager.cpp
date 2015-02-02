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

ObjectManager::ObjectManager() {
	initializeMapping();
}


void ObjectManager::initializeMapping()
{
	// TODO Complete the list
	m_typeObjet["PRISE"] = PRISE;
	m_typeObjet["CHAUFFAGE"] = CHAUFFAGE;
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

void ObjectManager::loadObjects(json_t *_objects)
{
	json_t *objets = json_object_get(_objects, "objets");
	/* array is a JSON array */
	size_t index;
	json_t *value;
	json_array_foreach(objets, index, value)
	{
		try
		{
			setObject(value);
		}
		catch(NotFoundException &e)
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

void ObjectManager::setObject(json_t *_object)
{
	json_t *name = json_object_get(_object, "nomObjet");
	if(!json_is_string(name))
	{
		throw FormatException("setObject : name format not accepted");
	}
	std::string nom = json_string_value(name);

	json_t *planning_json = json_object_get(_object, "planning");
	if(!json_is_string(planning_json))
	{
		throw FormatException("setObject : planning format not accepted");
	}
	std::string planning = json_string_value(planning_json);

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
				PowerPlug *powerPlug = (PowerPlug *) getObject(nom);
				powerPlug->setPlanning(m_planningManager.week_get(planning));
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
				PowerPlug *newPowerPlug = new PowerPlug(nom, m_planningManager.week_get(planning), type, &m_zwaveController, deviceId, instanceNum);
				m_objects.push_back(newPowerPlug);
			}
			break;
		case CHAUFFAGE:
			std::cout << "type : " << type << std::endl;
			try
			{
				Heater *heater = (Heater *) getObject(nom);
				json_t *tConfort = json_object_get(_object, "Tconfort");
				if(json_is_integer(tConfort))
				{
					int tconfort = json_integer_value(tConfort);
					heater->setTconfort(tconfort);
				}

				json_t *tEco = json_object_get(_object, "Teco");
				if(json_is_integer(tEco))
				{
					int teco = json_integer_value(tEco);
					heater->setTeco(teco);
				}

				heater->setPlanning(m_planningManager.week_get(planning));
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
				json_t *tConfort = json_object_get(_object, "Tconfort");
				if(!json_is_integer(tConfort))
				{
					throw FormatException("setObject : tConfort format not accepted");
				}
				int tconfort = json_integer_value(tConfort);

				json_t *tEco = json_object_get(_object, "Teco");
				if(!json_is_integer(tEco))
				{
					throw FormatException("setObject : tEco format not accepted");
				}
				int teco = json_integer_value(tEco);

				Heater *newHeater = new Heater(nom, m_planningManager.week_get(planning), type, &m_zwaveController, deviceId, instanceNum, tconfort, teco);
				m_objects.push_back(newHeater);
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

	try
	{
		Room *room = getRoom(nom);
		room->addObject(getObject(nomObjet));
	}
	catch(NotFoundException &e)
	{
		std::cout << e.what() << std::endl;
		Room *newRoom = new Room(nom);
		newRoom->addObject(getObject(nomObjet));
		m_rooms.push_back(newRoom);
	}
}

Object *ObjectManager::getObject(std::string _name)
{
	for(std::size_t i = 0 ; i < m_objects.size() ; i++)
	{
		if(m_objects[i]->getName().compare(_name.c_str()) == 0)
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


void ObjectManager::powerPlug_switchON(std::string _name)
{

}

void ObjectManager::powerPlug_switchOFF(std::string _name)
{

}

