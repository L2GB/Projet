/*
 * ZWaveController.h
 *
 *  Created on: 27 janv. 2015
 *      Author: CAILLOT Kilian
 */

#ifndef COMMUNICATION_ZWAVE_ZWAVECONTROLLER_H_
#define COMMUNICATION_ZWAVE_ZWAVECONTROLLER_H_

#ifdef _cplusplus
	extern "C" {
#endif

#include "../../library/libzway/ZWayLib.h"
#include "../../library/libzway/ZLogging.h"


#ifdef _cplusplus
}
#endif


class ZWaveController {
public:
	ZWaveController();
	virtual ~ZWaveController();
	int startNetwork();
};

#endif /* COMMUNICATION_ZWAVE_ZWAVECONTROLLER_H_ */
