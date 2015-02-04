/*
 * ZWaveController.h
 *
 *  Created on: 27 janv. 2015
 *      Author: CAILLOT Kilian
 */

#ifndef COMMUNICATION_ZWAVE_ZWAVECONTROLLER_H_
#define COMMUNICATION_ZWAVE_ZWAVECONTROLLER_H_

#include <vector>
#include <string>

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
	int inclusion_mode_ON();
	int inclusion_mode_OFF();
	int exclusion_mode_ON();
	int exclusion_mode_OFF();
	int basic_set(int deviceNodeId, int instanceId, int valeur);
	ZWay get_zway();
	bool zNetwork_is_working();
	bool zNetwork_is_idle();
	void zdata_mutex_lock();
	void zdata_mutex_unlock();
	bool zNetwork_is_there_device_instance_cc_holder(int deviceNum, int instanceNum, int commandClassNum, std::string dataName);
	std::string zNetwork_get_holder_value_type(int deviceNum, int instanceNum, int commandClassNum, std::string dataName);
	int zNetwork_get_integer(int deviceNum, int instanceNum, int commandClassNum, std::string dataName);
	bool zNetwork_get_boolean(int deviceNum, int instanceNum, int commandClassNum, std::string dataName);
	float zNetwork_get_float(int deviceNum, int instanceNum, int commandClassNum, std::string dataName);
	std::string zNetwork_get_string(int deviceNum, int instanceNum, int commandClassNum, std::string dataName);
	std::string zNetwork_get_device_name(int deviceNum);
	std::string zNetwork_get_device_type(int deviceNum);
	int zNetwork_get_nb_devices_paired();
	bool zNetwork_is_device_paired(int deviceId);
	bool zNeztwork_is_device_connected(int deviceId, int instanceNum);
	void zNetwork_wake_device_up(int deviceId);


private:
	ZWay m_zway;
};


#endif /* COMMUNICATION_ZWAVE_ZWAVECONTROLLER_H_ */
