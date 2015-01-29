/*
 * ZWaveController.h
 *
 *  Created on: 27 janv. 2015
 *      Author: CAILLOT Kilian
 */

#ifndef COMMUNICATION_ZWAVE_ZWAVECONTROLLER_H_
#define COMMUNICATION_ZWAVE_ZWAVECONTROLLER_H_

#include <vector>

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
	int get_cc_presence();
	int inclusion_mode_ON();
	int inclusion_mode_OFF();
	int exclusion_mode_ON();
	int exclusion_mode_OFF();
	static int basic_set(int deviceNodeId, int instanceId, int valeur);
	ZWay get_zway();
	bool zNetwork_is_working();
	bool zNetwork_is_idle();
	void zdata_mutex_lock();
	void zdata_mutex_unlock();


private:
	ZWay m_zway;
};


#endif /* COMMUNICATION_ZWAVE_ZWAVECONTROLLER_H_ */
