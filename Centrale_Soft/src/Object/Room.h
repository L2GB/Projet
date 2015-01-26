/*
 * Room.h
 *
 *  Created on: 26 janv. 2015
 *      Author: menard
 */

#ifndef OBJECT_ROOM_H_
#define OBJECT_ROOM_H_

// External include
#include <iostream>
#include <vector>

// Internal include
#include "Object.h"

class Room
{
	public:
		Room(std::string _name) : m_name(_name){}
		virtual ~Room(){}
		std::string getName(){return m_name;}

		void addObject(Object *_object);
		void removeObject(Object *_object);

	private:
		std::string m_name;
		std::vector<Object *> m_objects;
};

#endif /* OBJECT_ROOM_H_ */
