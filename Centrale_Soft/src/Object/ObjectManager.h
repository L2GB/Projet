/*
 * ObjectManager.h
 *
 *  Created on: 7 janv. 2015
 *      Author: menard
 */

#ifndef OBJECT_OBJECTMANAGER_H_
#define OBJECT_OBJECTMANAGER_H_

// Internal include
#include "../communication/Communicator.h"

class ObjectManager : public Communicator
{
	public:
		ObjectManager();
		virtual ~ObjectManager();
};

#endif /* OBJECT_OBJECTMANAGER_H_ */
