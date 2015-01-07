/*
 * Communicator.h
 *
 *  Created on: 7 janv. 2015
 *      Author: menard
 */

#ifndef COMMUNICATION_COMMUNICATOR_H_
#define COMMUNICATION_COMMUNICATOR_H_

// Internal include
#include "transmission/Transmission.h"

class Communicator
{
	public:
		Communicator(){}
		virtual ~Communicator(){}

		void attach(Transmission *_transmission);

	protected :
		Transmission *m_transmission;
};

#endif /* COMMUNICATION_COMMUNICATOR_H_ */
