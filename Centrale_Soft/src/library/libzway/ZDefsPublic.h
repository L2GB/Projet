//
//  ZDefsPublic.h
//  Part of Z-Way.C library
//
//  Created by Alex Skalozub on 1/26/12.
//  Based on Z-Way source code written by Christian Paetz and Poltorak Serguei
//
//  Copyright (c) 2012 Z-Wave.Me
//  All rights reserved
//  info@z-wave.me
//
//  This source file is subject to the terms and conditions of the
//  Z-Wave.Me Software License Agreement which restricts the manner
//  in which it may be used.
//

#ifndef zway_defs_public_h
#define zway_defs_public_h

#include "ZPlatform.h"
#include "ZData.h"
#include "ZMalloc.h"

#ifdef __cplusplus
extern "C" {
#endif

// Broadcast Node Id
#define NODE_BROADCAST 0xFF
// Maximum allowed Node Id
#define NODE_MAX 0xE8

// ZWay defs //

// State of controller
enum _ZWControllerState
{
    Idle = 0,
    AddReady = 1,
    AddNodeFound = 2,
    AddLearning = 3,
    AddDone = 4,
    RemoveReady = 5,
    RemoveNodeFound = 6,
    RemoveLearning = 7,
    LearnStarted = 8,
    LearnReady = 9,
    //LearnNodeFound = 10,
    //LearnLearning = 11,
    //LearnDone = 12,
    ShiftReady = 13,
    ShiftNodeFound = 14,
    ShiftLearning = 15,
    ShiftDone = 16,
    Stopping = 17,
    ReplaceStarted = 18,
    ReplaceReady = 19,
    Resetting = 20,
};

// Use this type to save controller state
typedef int ZWState;

// Z-Way instance holder
struct _ZWay;
typedef struct _ZWay *ZWay;

// Definition of termination callback function
typedef void (*ZTerminationCallback)(const ZWay zway, void* arg);

// ZJob defs //

// Definition of custom callback for Function Classes and Command Classes calls
typedef void (*ZJobCustomCallback)(const ZWay zway, ZWBYTE functionId, void* arg);

// Types of Device change events
enum _ZWDeviceChangeType
{
    DeviceAdded = 0x01,
    DeviceRemoved = 0x02,
    InstanceAdded = 0x04,
    InstanceRemoved = 0x08,
    CommandAdded = 0x10,
    CommandRemoved = 0x20,
    ZDDXSaved = 0x100, // this callback notifies on ZDDX data change (to allow main program to purge buffers to disk/flash). For this event node_id = instance_id = command_id = 0
    EnumerateExisting = 0x200, // this flag makes callback immediately fire for all existing devices/instances/command classes as if they're just added
};

// Use this type to store Device change event type
typedef int ZWDeviceChangeType;

// Definition of device change callback function
typedef void (*ZDeviceCallback)(const ZWay wzay, ZWDeviceChangeType type, ZWBYTE node_id, ZWBYTE instance_id, ZWBYTE command_id, void *arg);

// ChooseXml enumerator
struct _ZGuessedProduct
{
    int score;
    char *vendor;
    char *product;
    char *image_url;
    char *file_name;
};
typedef struct _ZGuessedProduct *ZGuessedProduct;

// List of NULL terminated registered devices Node Id
typedef ZWBYTE * ZWDevicesList;

// List of NULL terminated registered instances Id for device
typedef ZWBYTE * ZWInstancesList;

// List of NULL terminated registered Command Classes Id for instance of device
typedef ZWBYTE * ZWCommandClassesList;

    
#ifdef __cplusplus
}
#endif
    
#endif
