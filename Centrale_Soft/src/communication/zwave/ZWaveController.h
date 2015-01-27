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
	void printDeviceInfoLongVersion();
	void printDeviceInfoShortVersion(int deviceNodeId);
	int print_data_tree();
	//void print_zway_terminated(ZWay zway, void* arg);
	//void print_D_I_CC_event(const ZWay zway, ZWDeviceChangeType type, ZWBYTE node_id, ZWBYTE instance_id, ZWBYTE command_id, void *arg);

private:
	ZWay m_zway;
};


#endif /* COMMUNICATION_ZWAVE_ZWAVECONTROLLER_H_ */
