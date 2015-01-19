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

/**
 * Needed to instance the transmission.
 */
class Communicator
{
	public:
		Communicator(){}
		virtual ~Communicator(){}

		/**
		 * Attach a transmitter to a communicator.
		 */
		void attach(Transmission *_transmission);

	protected :
		Transmission *m_transmission;
};

#endif /* COMMUNICATION_COMMUNICATOR_H_ */
