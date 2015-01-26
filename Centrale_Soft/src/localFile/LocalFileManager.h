/*
 * LocalFileManager.h
 *
 *  Created on: 12 janv. 2015
 *      Author: menard
 */

#ifndef LOCALFILE_LOCALFILEMANAGER_H_
#define LOCALFILE_LOCALFILEMANAGER_H_

// External include
#include <iostream>

// Internal include
#include "../tools/json/jansson.h"

/**
 * Contains methods useful to write and read in the local configuration files.
 */
class LocalFileManager
{
	public:
		static const std::string PATH_PIECES;
		static const std::string PATH_OBJETS;
		static const std::string PATH_JOURS;
		static const std::string PATH_SEMAINES;
		static const int SIZE = 32000;

		static std::string getDays();
		static std::string getWeeks();
		static std::string getObjects();
		static std::string getRooms();
		static void setDay(json_t *_day);
		static void setWeek(json_t *_week);
		static void setObject(json_t *_object);
		static void addObjectToRoom(json_t *_room);
		static void remObjectToRoom(json_t *_room);
		static void rmDay(json_t *_day);
		static void rmWeek(json_t *_week);
		static void rmObject(json_t *_object);
		static void rmRoom(json_t *_room);
};

#endif /* LOCALFILE_LOCALFILEMANAGER_H_ */
